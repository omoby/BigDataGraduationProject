package graduation.java.impl;

import com.alibaba.fastjson.JSONObject;
import graduation.java.dao.IAdStatDAO;
import graduation.java.domain.AdStat;
import graduation.java.jdbc.JDBCHelper;
import graduation.java.model.AdStatQueryPorvinceCountResult;
import graduation.java.model.AdStatQueryResult;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName: AdStatDAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 上午10:31
 * Description:
 * 广告实时点击状态DAO实现类
 */
public class AdStatDAOImpl implements IAdStatDAO {
    @Override
    public void updateBatch(List<AdStat> adStats) {

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        AdStatQueryResult queryResult = new AdStatQueryResult(0);

        List<AdStat> updateAdStatList = new ArrayList<AdStat>();
        List<AdStat> insertAdStatList = new ArrayList<AdStat>();

        String selectSQL = "SELECT count(*) FROM ad_stat " +
                "WHERE date=? " +
                "AND province=? " +
                "AND city=? " +
                "AND ad_id=?";

        for (AdStat adStat : adStats){
            Object[] params = new Object[]{
                    adStat.getDate(),
                    adStat.getProvince(),
                    adStat.getCity(),
                    adStat.getAdid()};
            jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {
                @Override
                public void process(ResultSet rs) throws Exception {
                    while (rs.next()){
                        int count = rs.getInt(1);
                        queryResult.setCount(count);
                    }
                }
            });

            int count = queryResult.getCount();

            if (count > 0){
                updateAdStatList.add(adStat);
            }else{
                insertAdStatList.add(adStat);
            }
        }

        //对首次进入的信息进行插入

        String insertSQL = "INSERT INTO ad_stat VALUES(?,?,?,?,?)";

        List<Object[]> iinsertParamsList = new ArrayList<Object[]>();

        for (AdStat adStat : insertAdStatList){
            Object[] params = new Object[]{
                    adStat.getDate(),
                    adStat.getProvince(),
                    adStat.getCity(),
                    adStat.getAdid(),
                    adStat.getClickCount()};

            iinsertParamsList.add(params);
        }
        jdbcHelper.executeBatch(insertSQL,iinsertParamsList);


        //对已有的信息进行更新操作

        String updateSQL = "UPDATE ad_stat SET click_count=? " +
                "WHERE date=? " +
                "AND province=? " +
                "AND city=? " +
                "AND ad_id=?";
        List<Object[]> updateParamsList = new ArrayList<Object[]>();

        for (AdStat adStat : updateAdStatList){
            Object[] params = new Object[]{
                    adStat.getClickCount(),
                    adStat.getDate(),
                    adStat.getProvince(),
                    adStat.getCity(),
                    adStat.getAdid()};
            updateParamsList.add(params);
        }

        jdbcHelper.executeBatch(updateSQL,updateParamsList);

    }

    /**
     * 查询指定时间日期内的所有记录
     * @param dates
     * @return
     */
    @Override
    public Map<String, Object> select(String dates) {
        dates = "20190419";
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        AdStatQueryResult cityAdStatQueryResult = new AdStatQueryResult(0);
        String selectCityNum = "SELECT COUNT(1) FROM ad_stat where date=? GROUP BY ad_id LIMIT 1";
        Object[] selectCityNumParams = new Object[]{dates};
        jdbcHelper.executeQuery(selectCityNum, selectCityNumParams, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    cityAdStatQueryResult.setCount(rs.getInt(1));
                }
            }
        });
        int cityNum = cityAdStatQueryResult.getCount();

        AdStatQueryResult adStatQueryResult  = new AdStatQueryResult(0);

        String selectadIdNum = "SELECT COUNT(1) FROM (SELECT DISTINCT(ad_id) FROM ad_stat WHERE date=? GROUP BY ad_id) tmp";

        jdbcHelper.executeQuery(selectadIdNum, selectCityNumParams, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    adStatQueryResult.setCount( rs.getInt(1));
                }
            }
        });

        int adIdNum = adStatQueryResult.getCount();

        Map<String,Object> map = new HashMap<>();
        String[] city = new String[cityNum];
        int[] city2Adid = new int[cityNum*adIdNum];




        String selectSQL = "SELECT city,ad_id,click_count FROM ad_stat WHERE date=? ORDER BY city,ad_id";
        Object[] params = new Object[]{"20190419"};

        jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                int adidindex = 0;
                int cityindex = 0;
                while (rs.next()){
                    String cityName = rs.getString("city");
                    int ad_id = rs.getInt("ad_id");
                    int clickCount = rs.getInt(3);
                    if (ad_id == 0){
                        city[cityindex] = cityName;
                        cityindex++;
                    }
                    city2Adid[adidindex] = clickCount;
                    adidindex++;
                }

            }
        });
        map.put("city",city);
        for (int n = 0;n < cityNum; n++){
            String cityName = city[n];
            int[] adId = subArray(city2Adid,n * adIdNum,adIdNum);
            map.put(cityName,adId);
        }

        return map;
    }

    /**
     * 查询个省份广告点击数据
     * @param dates
     * @return
     */
    @Override
    public Map<String, Integer> selectProvinceClick(String dates) {
        dates = "20190419";
        Map<String,Integer> map = new HashMap<>();
        String select = "SELECT  province,SUM(click_count) count FROM ad_stat WHERE date=? GROUP BY province ORDER BY province";
        Object[] params = new Object[]{dates};

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(select, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    String province = rs.getString(1);
                    int clickCount = rs.getInt(2);
                    map.put(province,clickCount);
                }
            }
        });
        return map;
    }

    /**
     * 查询JSON格式的广告点击状态
     * @param dates
     * @return
     */
    @Override
    public JSONObject selectProvinceClickPercent(String dates) {
        dates="20190419";
        String select ="SELECT province,SUM(click_count) FROM  ad_stat WHERE date=? GROUP BY province ";
        Object[] params = new Object[]{dates};
        JSONObject jsonObject = new JSONObject();
        AdStatQueryResult adStatQueryResult = new AdStatQueryResult(0);
        List<AdStatQueryPorvinceCountResult> provinceCounts = new ArrayList<>();

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(select, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    String province = rs.getString(1);
                    long count = rs.getLong(2);
                    AdStatQueryPorvinceCountResult result = new AdStatQueryPorvinceCountResult();
                    result.setProvince(province);
                    result.setCount(count);
                    provinceCounts.add(result);
                    count += adStatQueryResult.getSum();
                    adStatQueryResult.setSum((int)count);


                }
            }
        });
        for (AdStatQueryPorvinceCountResult result : provinceCounts){
            String province = result.getProvince();
            double percent = (double)(int)((double)(result.getCount())/(double)adStatQueryResult.getSum()*10000)/100;
            jsonObject.put(province,percent);
        }
        return jsonObject;
    }

    /**
     * 切分数组
     * @param arr
     * @param startIndex
     * @param num
     * @return
     */
    public static int[] subArray(int[] arr,int startIndex,int num){
        int[] newArr = new int[num];
        for (int i = 0; i <num;i++){
            newArr[i] = arr[startIndex+i];
        }
        return newArr;

    }



}
