package graduation.java.util;

import com.alibaba.fastjson.JSONObject;
import graduation.java.conf.ConfigurationManager;
import graduation.java.constant.Constants;
import graduation.java.test.MockData;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.hive.HiveContext;

/**
 * FileName: SparkUtils
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-30 下午7:46
 * Description:
 * Spark工具类
 */
public class SparkUtils {
    /**
     * 根据当前是否是本地测试模式配置来决定
     * 如果设置SparkConf的master
     * @param conf
     */

    public static  void setMaster(SparkConf conf){
        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
        if (local){
            //conf.setMaster("local[2]");
            conf.setMaster("spark://Master:7077");
        }
    }
    /**
     * 获取SQLContext
     * 如果是在本地测试环境的话，那么就生成SQLContext对象
     * 如果是在生产环境运行的话，那么就生成HiveContext对象
     * @param sc SparkContext
     * @return SQLContext
     */
    public static SQLContext getSQLContext(SparkContext sc) {
        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
        if(local) {
            return new SQLContext(sc);
        } else {
            return new HiveContext(sc);
        }
    }
    /**
     * 生成模拟数据（只有本地模式，才会去生成模拟数据）
     * @param sc
     * @param sqlContext
     */
    public static void mockData(JavaSparkContext sc, SQLContext sqlContext) {
        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
        if(local) {
            MockData.mock(sc, sqlContext);
        }
    }

    /**
     * 获取指定日期范围内的用户访问行为数据
     * @param sqlContext SQLContext
     * @param taskParam 任务参数
     * @return 行为数据RDD
     */
    public static JavaRDD<Row> getActionRDDByDateRange(
            SQLContext sqlContext, JSONObject taskParam) {
        String startDate = ParamUtils.getParam(taskParam, Constants.PARAM_START_DATE);
        String endDate = ParamUtils.getParam(taskParam, Constants.PARAM_END_DATE);

        String sql =
                "select * "
                        + "from user_visit_action "
                        + "where dates>='" + startDate + "' "
                        + "and dates<='" + endDate + "'";

        Dataset actionDF = sqlContext.sql(sql);

        System.out.println("actionDF");
        actionDF.show(10);
        /**
         * 这里就很有可能发生上面说的问题
         * 比如说，Spark SQl默认就给第一个stage设置了20个task，但是根据你的数据量以及算法的复杂度
         * 实际上，你需要1000个task去并行执行
         *
         * 所以说，在这里，就可以对Spark SQL刚刚查询出来的RDD执行repartition重分区操作
         */
        //return  actionDF.javaRDD().repartition(100);

        return actionDF.javaRDD();
    }
}
