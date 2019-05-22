
package graduation.java.spark.product;

import com.alibaba.fastjson.JSONObject;
import graduation.java.conf.ConfigurationManager;
import graduation.java.constant.Constants;
import graduation.java.dao.IAreaTop3ProductDAO;
import graduation.java.dao.ITaskDAO;
import graduation.java.domain.AreaTop3Product;
import graduation.java.domain.Task;
import graduation.java.factory.DAOFactory;
import graduation.java.util.ParamUtils;
import graduation.java.util.SparkUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;

import java.util.*;


/**
 * FileName: AreaTop3ProductSpark
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-1 下午4:17
 * Description:
 *
 * 各区域top3热门商品统计Spark作业
 */

public class AreaTop3ProductSpark {

    public static void main(String[] args) {
        Logger.getLogger("org").setLevel(Level.ERROR);
        //1.创建SparkConf
        SparkConf conf = new SparkConf()
                .setAppName(Constants.SPARK_APP_NAME_PRODUCT);
        SparkUtils.setMaster(conf);

        //2.构建spark上下文、
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = SparkUtils.getSQLContext(sc.sc());
        sqlContext.setConf("spark.sql.shuffle.partitions", "1000");
		sqlContext.setConf("spark.sql.autoBroadcastJoinThreshold", "20971520");

        //3.注册字符串拼接函数
        sqlContext.udf().register("concat_long_string",new ConcatLongStringUDF(),DataTypes.StringType);
        sqlContext.udf().register("get_json_object",new GetJsonObjectUDF(),DataTypes.StringType);
        sqlContext.udf().register("random_prefix",new RandomPrefixUDF(),DataTypes.StringType);
        sqlContext.udf().register("remove_random_prefix",new RemoveRandomPrefixUDF(),DataTypes.StringType);

        sqlContext.udf().register("group_concat_distinct",new GroupConcatDistinctUDAF());


        //4.模拟数据
        SparkUtils.mockData(sc,sqlContext);

        //5.获取命令行传入的taskid，查询对应的任务参数
        ITaskDAO iTaskDAO = DAOFactory.getTaskDAO();
        long taskid = ParamUtils.getTaskIdFromArgs(args,Constants.SPARK_LOCAL_TASKID_PRODUCT);
        Task task = iTaskDAO.findById(taskid);

        JSONObject taskParam = JSONObject.parseObject(task.getTaskParam());
        
        String startDate = ParamUtils.getParam(taskParam,Constants.PARAM_START_DATE);
        String endDate = ParamUtils.getParam(taskParam,Constants.PARAM_END_DATE);

        // 查询用户指定日期范围内的点击行为数据（city_id，在哪个城市发生的点击行为）
        //技术点1：hive表中数据使用
        JavaPairRDD<Long,Row> cityid2clickActionRDD = getCityid2ClickActionRDDByDate(sqlContext,startDate,endDate);

        //从mysql数据库中获取城市的数据信息
        //技术点2：异构数据源mysql的使用
        JavaPairRDD<Long,Row> cityid2cityInfoRDD = getCityid2CityInfoRDD(sqlContext);


        //生成点击商品基础信息临时表
        //技术点3：将RDD转换为Dataset，并注册临时表
        generateTempClickProductBasicTable(sqlContext, cityid2clickActionRDD,cityid2cityInfoRDD);


        //生成个区域各商品点击次数的临时表
        genrateTempAreaProductClickCountTable(sqlContext);

        //生成包含完整商品信息的各商品个区域的点击次数的临时表
        generateTempAreaFullProductClickCountTable(sqlContext);

        // 使用开窗函数获取各个区域内点击次数排名前3的热门商品
        JavaRDD<Row> areaTop3ProductRDD = getAreaTop3ProductRDD(sqlContext);
        // 这边的写入mysql和之前不太一样
        // 因为实际上，就这个业务需求而言，计算出来的最终数据量是比较小的
        // 总共就不到10个区域，每个区域还是top3热门商品，总共最后数据量也就是几十个
        // 所以可以直接将数据collect()到本地
        // 用批量插入的方式，一次性插入mysql即可
        List<Row> rows = areaTop3ProductRDD.collect();
        persistAreaTop3Product(taskid,rows);
        sc.close();
        


    }




    /**查询指定日期范围内的点击行为数据
     *
     * @param sqlContext
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return
     */

    private static JavaPairRDD<Long,Row> getCityid2ClickActionRDDByDate(SQLContext sqlContext, String startDate, String endDate) {
        // 从user_visit_action中，查询用户访问行为数据
        // 第一个限定：click_product_id，限定为不为空的访问行为，那么就代表着点击行为
        // 第二个限定：在用户指定的日期范围内的数据

        String sql = "SELECT " +
                "city_id," +
                "click_product_id product_id " +
                "FROM user_visit_action " +
                "WHERE click_product_id != " +
                Long.MAX_VALUE+
//                "AND click_product_id != 'NULL' " +
//                "AND click_product_id != 'null' " +
                " AND dates >='" + startDate + "' " +
                " AND dates <= '"+ endDate + "'" ;
        System.out.println("sql:++ "+ sql);

        Dataset clickActionDs = sqlContext.sql(sql);
        System.out.println("getCityid2ClickActionRDDByDate:111");
        clickActionDs.show();
        JavaRDD<Row> clickActionRDD =  clickActionDs.javaRDD();

        JavaPairRDD<Long,Row> cityid2clickActionRDD = clickActionRDD.mapToPair(
                new PairFunction<Row, Long, Row>() {
                    private static  final long serialVersionUID = 1L;
                    @Override
                    public Tuple2<Long, Row> call(Row row) throws Exception {
                        long cityid = row.getLong(0);
                        return  new Tuple2<Long,Row>(cityid,row);
                    }
                });
        return cityid2clickActionRDD;
    }


    private static JavaPairRDD<Long,Row> getCityid2CityInfoRDD(SQLContext sqlContext){
        //构建Mysql连接配置信息(从配置文件中获取)
        String url = null;
        String user = null;
        String password = null;
        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);


        if (local){
            url = ConfigurationManager.getProperty(Constants.JDBC_URL);
            user = ConfigurationManager.getProperty(Constants.JDBC_USER);
            password = ConfigurationManager.getProperty(Constants.JDBC_PASSWORD);
        }else {
            url = ConfigurationManager.getProperty(Constants.JDBC_URL_PROD);
            user = ConfigurationManager.getProperty(Constants.JDBC_USER_PROD);
            password = ConfigurationManager.getProperty(Constants.JDBC_PASSWORD_PROD);
        }

        Map<String,String> options = new HashMap<String,String>();
        options.put("url",url);
        options.put("dbtable","city_info");
        options.put("user",user);
        options.put("password",password);

        //通过SQLContext去从Mysql中查询数据
        Dataset cityInfoDs = sqlContext.read().format("jdbc")
                .options(options).load();
        System.out.println("getCityid2CityInfoRDD:2222");
        cityInfoDs.show();

        //返回RDD
        JavaRDD<Row> cityInfoRDD = cityInfoDs.javaRDD();

        JavaPairRDD<Long,Row> cityid2cityInfoRDD = cityInfoRDD.mapToPair(new PairFunction<Row, Long, Row>() {
            private static  final long serialVersionUID = 1L;
            @Override
            public Tuple2<Long, Row> call(Row row) throws Exception {
                long cityid = Long.valueOf(row.getInt(0));
                return new Tuple2<Long,Row>(cityid,row);
            }
        });

        return  cityid2cityInfoRDD;

    }

    /**
     *生成点击商品信息基础临时表
     * @param sqlContext
     * @param cityid2clickActionRDD 商品点击信息
     * @param cityid2cityInfoRDD 城市信息
     */

    private static void generateTempClickProductBasicTable(
            SQLContext sqlContext,
            JavaPairRDD<Long,Row> cityid2clickActionRDD,
            JavaPairRDD<Long,Row> cityid2cityInfoRDD){
        //执行join操作，进行点击行为数据和城市数据进行关联
        JavaPairRDD<Long,Tuple2<Row,Row>> joinedRDD = cityid2clickActionRDD.join(cityid2cityInfoRDD);

        //将上面的JavaPairRDD转换为一个JavaRDD<Row> 才能将RDD转换为Dataset
        JavaRDD<Row> mappedRDD = joinedRDD.map(
                new Function<Tuple2<Long, Tuple2<Row, Row>>, Row>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Row call(Tuple2<Long, Tuple2<Row, Row>> tuple) throws Exception {
                        long cityid = tuple._1;
                        Row clickAction = tuple._2._1;
                        Row cityInfo = tuple._2._2;

                        long productid= clickAction.getLong(1);
                        String cityName = cityInfo.getString(1);
                        String area = cityInfo.getString(2);

                        return RowFactory.create(cityid,cityName,area,productid);
                    }
                });

        //基于JavaRDD<Row>的格式，就可以将其转化为Dataset
        List<StructField> structFields = new ArrayList<StructField>();
        structFields.add(DataTypes.createStructField("city_id", DataTypes.LongType,true));
        structFields.add(DataTypes.createStructField("city_name", DataTypes.StringType,true));
        structFields.add(DataTypes.createStructField("area", DataTypes.StringType,true));
        structFields.add(DataTypes.createStructField("product_id", DataTypes.LongType,true));

        StructType schema = DataTypes.createStructType(structFields);

        Dataset ds = sqlContext.createDataFrame(mappedRDD,schema);
        System.out.println("generateTempClickProductBasicTable:333");
        ds.show();

        //将Dataset中的数据注册为临时表
        ds.registerTempTable("tmp_click_product_basic");

    }


        /**
         * 生成各区域各商品点击次数的临时表
         * @param sqlContext
         */

        private static void genrateTempAreaProductClickCountTable(SQLContext sqlContext) {
            // 按照area和product_id两个字段进行分组
            // 计算出各区域各商品的点击次数
            // 可以获取到每个area下的每个product_id的城市信息拼接起来的串
            String sql = "SELECT " +
                        "area," +
                        "product_id, " +
                        "count(*) click_count, " +
                        "group_concat_distinct(concat_long_string(city_id,city_name,':')) city_infos " +
                    "FROM tmp_click_product_basic " +
                    "GROUP BY area,product_id ";


            /**
             * 双重group by
             */


//		String _sql =
//				"SELECT "
//					+ "product_id_area,"
//					+ "count(click_count) click_count,"
//					+ "group_concat_distinct(city_infos) city_infos "
//				+ "FROM ( "
//					+ "SELECT "
//						+ "remove_random_prefix(product_id_area) product_id_area,"
//						+ "click_count,"
//						+ "city_infos "
//					+ "FROM ( "
//						+ "SELECT "
//							+ "product_id_area,"
//							+ "count(*) click_count,"
//							+ "group_concat_distinct(concat_long_string(city_id,city_name,':')) city_infos "
//						+ "FROM ( "
//							+ "SELECT "
//								+ "random_prefix(concat_long_string(product_id,area,':'), 10) product_id_area,"
//								+ "city_id,"
//								+ "city_name "
//							+ "FROM tmp_click_product_basic "
//						+ ") t1 "
//						+ "GROUP BY product_id_area "
//					+ ") t2 "
//				+ ") t3 "
//				+ "GROUP BY product_id_area ";

            // 使用Spark SQL执行这条SQL语句
            Dataset ds = sqlContext.sql(sql);

            System.out.println("genrateTempAreaProductClickCountTable:444");
            ds.show();
            // 再次将查询出来的数据注册为一个临时表
            // 各区域各商品的点击次数（以及额外的城市列表）
                ds.registerTempTable("tmp_area_product_click_count");
        }


        /**
         * 生成区域商品点击次数临时表（包含了商品的完整信息）
         * @param sqlContext
         */

        private static void generateTempAreaFullProductClickCountTable(SQLContext sqlContext){
            // 将之前得到的各区域各商品点击次数表，product_id
            // 去关联商品信息表，product_id，product_name和product_status
            // product_status要特殊处理，0，1，分别代表了自营和第三方的商品，放在了一个json串里面
            // get_json_object()函数，可以从json串中获取指定的字段的值
            // if()函数，判断，如果product_status是0，那么就是自营商品；如果是1，那么就是第三方商品
            // area, product_id, click_count, city_infos, product_name, product_status

            // 为什么要费时费力，计算出来商品经营类型
            // 你拿到到了某个区域top3热门的商品，那么其实这个商品是自营的，还是第三方的
            // 其实是很重要的一件事

            // 技术点：内置if函数的使用
            String sql  =  "SELECT " +
                    "tapcc.area, " +
                    "tapcc.product_id, " +
                    "tapcc.click_count, " +
                    "tapcc.city_infos, " +
                    "pi.product_name, " +
                    "if(get_json_object(pi.extend_info,'product_status ')=0,'自营商品','第三方商品') product_status " +
                    "FROM tmp_area_product_click_count tapcc " +
                    "JOIN product_info pi ON tapcc.product_id = pi.product_id";

//            JavaRDD<Row> rdd = sqlContext.sql("select * from product_info").javaRDD();
//            JavaRDD<Row> flattedRDD = rdd.flatMap(new FlatMapFunction<Row, Row>() {
//
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public Iterator<Row> call(Row row) throws Exception {
//                    List<Row> list = new ArrayList<Row>();
//
//                    for(int i = 0; i < 10; i ++) {
//                        long productid = row.getLong(0);
//                        String _productid = i + "_" + productid;
//
//                        Row _row = RowFactory.create(_productid, row.get(1), row.get(2));
//                        list.add(_row);
//                    }
//
//                    return list.iterator();
//                }
//
//            });
//
//		StructType _schema = DataTypes.createStructType(Arrays.asList(
//				DataTypes.createStructField("product_id", DataTypes.StringType, true),
//				DataTypes.createStructField("product_name", DataTypes.StringType, true),
//				DataTypes.createStructField("product_status", DataTypes.StringType, true)));
//
//		Dataset _df = sqlContext.createDataFrame(flattedRDD, _schema);
//		_df.registerTempTable("tmp_product_info");

//		String _sql =
//				"SELECT "
//					+ "tapcc.area,"
//					+ "remove_random_prefix(tapcc.product_id) product_id,"
//					+ "tapcc.click_count,"
//					+ "tapcc.city_infos,"
//					+ "pi.product_name,"
//					+ "if(get_json_object(pi.extend_info,'product_status')=0,'自营商品','第三方商品') product_status "
//				+ "FROM ("
//					+ "SELECT "
//						+ "area,"
//						+ "random_prefix(product_id, 10) product_id,"
//						+ "click_count,"
//						+ "city_infos "
//					+ "FROM tmp_area_product_click_count "
//				+ ") tapcc "
//				+ "JOIN tmp_product_info pi ON tapcc.product_id=pi.product_id ";



            Dataset ds = sqlContext.sql(sql);

            System.out.println("generateTempAreaFullProductClickCountTable:555");
            ds.show();

            ds.registerTempTable("tmp_area_fullprod_click_count");
        }


    /**
     * 获取各区域top3热门商品
     * @param sqlContext
     * @return
     */


    private static JavaRDD<Row> getAreaTop3ProductRDD(SQLContext sqlContext){

        // 技术点：开窗函数

        // 使用开窗函数先进行一个子查询
        // 按照area进行分组，给每个分组内的数据，按照点击次数降序排序，打上一个组内的行号
        // 接着在外层查询中，过滤出各个组内的行号排名前3的数据
        // 其实就是咱们的各个区域下top3热门商品

        // 华北、华东、华南、华中、西北、西南、东北
        // A级：华北、华东
        // B级：华南、华中
        // C级：西北、西南
        // D级：东北

        // case when
        // 根据多个条件，不同的条件对应不同的值
        // case when then ... when then ... else ... end
        String sql = "SELECT " +
                        "area, " +
                        "CASE " +
                            "WHEN area='华北' or area='华东' THEN 'A级' " +
                            "WHEN area='华南' or area='华中' THEN 'B级' " +
                            "WHEN area='西南' or area='西北' THEN 'C级' " +
                            "ELSE 'D级' " +
                        "END area_level, " +
                        "product_id, " +
                        "click_count," +
                        "city_infos," +
                        "product_name, " +
                        "product_status " +
                    "FROM (" +
                        "SELECT " +
                            "area, " +
                            "product_id, " +
                            "click_count," +
                            "city_infos," +
                            "product_name, " +
                            "product_status," +
                            "ROW_NUMBER() OVER(PARTITION BY area ORDER BY click_count DESC) rank " +
                    "FROM tmp_area_fullprod_click_count " +
                    ") t " +
                "WHERE rank <=3";
        Dataset ds = sqlContext.sql(sql);

        System.out.println("getAreaTop3ProductRDD:666");
        ds.show();

        return ds.javaRDD();
    }

    /**
     * 将计算出来的各区域top3热门商品写入mysql中
     * @param taskid
     * @param rows
     */

    private static void persistAreaTop3Product(long taskid, List<Row> rows) {
        List<AreaTop3Product> areaTop3Products = new ArrayList<AreaTop3Product>();
        int i = 0;
        for (Row row:rows){
            AreaTop3Product areaTop3Product = new AreaTop3Product();
            areaTop3Product.setTaskid(taskid);
            areaTop3Product.setArea(row.getString(0));
            areaTop3Product.setAreaLevel(row.getString(1));
            areaTop3Product.setProductid(row.getLong(2));
            areaTop3Product.setClickCount(Long.valueOf(String.valueOf(row.get(3))));
            areaTop3Product.setCityInfos(row.getString(4));
            areaTop3Product.setProductName(row.getString(5));
            areaTop3Product.setProductStatus(row.getString(6));
            areaTop3Products.add(areaTop3Product);
        }

        IAreaTop3ProductDAO areaTop3ProductDAO = DAOFactory.getAreaTop3ProductDAO();
        areaTop3ProductDAO.insertBatch(areaTop3Products);

    }


}

