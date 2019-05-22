package graduation.java.impl;

/**
 * FileName: AdClickTrendDAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-18 下午2:42
 * Description:
 */

import graduation.java.dao.IAdClickTrendDAO;
import graduation.java.domain.AdClickTrend;
import graduation.java.jdbc.JDBCHelper;
import graduation.java.model.AdClickTrendQueryResult;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告点击趋势DAO实现类
 * @author Administrator
 *
 */
public class AdClickTrendDAOImpl implements IAdClickTrendDAO {

    @Override
    public void updateBatch(List<AdClickTrend> adClickTrends) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        // 区分出来哪些数据是要插入的，哪些数据是要更新的
        // 提醒一下，比如说，通常来说，同一个key的数据（比如rdd，包含了多条相同的key）
        // 通常是在一个分区内的
        // 一般不会出现重复插入的

        // 但是根据业务需求来
        // 各位自己在实际做项目的时候，一定要自己思考，不要生搬硬套
        // 如果说可能会出现key重复插入的情况
        // 给一个create_time字段

        // j2ee系统在查询的时候，直接查询最新的数据即可（规避掉重复插入的问题）

        List<AdClickTrend> updateAdClickTrends = new ArrayList<AdClickTrend>();
        List<AdClickTrend> insertAdClickTrends = new ArrayList<AdClickTrend>();

        String selectSQL = "SELECT count(*) "
                + "FROM ad_click_trend "
                + "WHERE date=? "
                + "AND hour=? "
                + "AND minute=? "
                + "AND ad_id=?";

        for(AdClickTrend adClickTrend : adClickTrends) {
            final AdClickTrendQueryResult queryResult = new AdClickTrendQueryResult();

            Object[] params = new Object[]{adClickTrend.getDate(),
                    adClickTrend.getHour(),
                    adClickTrend.getMinute(),
                    adClickTrend.getAdid()};

            jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {

                @Override
                public void process(ResultSet rs) throws Exception {
                    if(rs.next()) {
                        int count = rs.getInt(1);
                        queryResult.setCount(count);
                    }
                }

            });

            int count = queryResult.getCount();
            if(count > 0) {
                updateAdClickTrends.add(adClickTrend);
            } else {
                insertAdClickTrends.add(adClickTrend);
            }
        }

        // 执行批量更新操作
        String updateSQL = "UPDATE ad_click_trend SET click_count=? "
                + "WHERE date=? "
                + "AND hour=? "
                + "AND minute=? "
                + "AND ad_id=?";

        List<Object[]> updateParamsList = new ArrayList<Object[]>();

        for(AdClickTrend adClickTrend : updateAdClickTrends) {
            Object[] params = new Object[]{adClickTrend.getClickCount(),
                    adClickTrend.getDate(),
                    adClickTrend.getHour(),
                    adClickTrend.getMinute(),
                    adClickTrend.getAdid()};
            updateParamsList.add(params);
        }

        jdbcHelper.executeBatch(updateSQL, updateParamsList);

        // 执行批量更新操作
        String insertSQL = "INSERT INTO ad_click_trend VALUES(?,?,?,?,?)";

        List<Object[]> insertParamsList = new ArrayList<Object[]>();

        for(AdClickTrend adClickTrend : insertAdClickTrends) {
            Object[] params = new Object[]{adClickTrend.getDate(),
                    adClickTrend.getHour(),
                    adClickTrend.getMinute(),
                    adClickTrend.getAdid(),
                    adClickTrend.getClickCount()};
            insertParamsList.add(params);
        }

        jdbcHelper.executeBatch(insertSQL, insertParamsList);
    }

    @Override
    public Map<String, Object> selectLast60min() {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        String selectNum = "SELECT count(1) FROM ad_click_trend GROUP BY date,hour,minute ORDER BY date,hour,minute LIMIT 60";
        AdClickTrendQueryResult result = new AdClickTrendQueryResult();
        Object[] params = new Object[]{};

        jdbcHelper.executeQuery(selectNum, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                int rowNum = 0;
                while (rs.next()){
                    rowNum++;
                }
                result.setCount(rowNum);
            }
        });

        int rowCount = result.getCount();

        String selectSQL="SELECT date,hour,minute ,SUM(click_count) FROM ad_click_trend GROUP BY date,hour,minute ORDER BY date,hour,minute  ASC LIMIT 60";

        Map<String,Object> map = new HashMap<>();
        String[] dateHourMin = new String[rowCount];
        double[] clickcount = new double[rowCount];
        double[] avercount = new double[rowCount];


        jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                long preclick = 0;
                int index = 0;
                String lasthour="";
                while (rs.next()){
                    String date = rs.getString(1);
                    String hour = rs.getString(2);
                    String minute = rs.getString(3);
                    long click = rs.getLong(4);
                    if (index==0){
                        dateHourMin[index] = "'"+hour+":"+minute+"'";
                    }else if (index!=0 && !hour.equals(lasthour)){
                        dateHourMin[index] = "'"+hour+":"+minute+"'";
                    }else{
                        dateHourMin[index] = "'"+minute+"'";
                    }
                    clickcount[index] = (double)click;
                    if (index==0){
                        avercount[index] = (double)click/click*100;
                    }else{
                        avercount[index] = (double)click/preclick*100;
                    }
                    preclick = click;
                    lasthour = hour;
                    index++;
                }
            }
        });
        map.put("datehoutminut",dateHourMin);
        map.put("clickcount",clickcount);
        map.put("avercount",avercount);
        return map;
    }

}
