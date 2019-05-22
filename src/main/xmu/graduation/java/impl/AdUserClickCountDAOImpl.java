package graduation.java.impl;

import com.alibaba.fastjson.JSONObject;
import graduation.java.dao.IAdUserClickCountDAO;
import graduation.java.domain.AdUserClickCount;
import graduation.java.jdbc.JDBCHelper;
import graduation.java.model.AdUserClickCountQueryResult;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * FileName: AdUserClickCountDAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-3 上午10:44
 * Description:
 * 广告用户点击量DAO实现类
 */
public class AdUserClickCountDAOImpl  implements IAdUserClickCountDAO {
    @Override
    public void updateBatch(List<AdUserClickCount> adUserClickCounts) {
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        //首先将用户广告点击量进行分类，分成待插入和已插入
        List<AdUserClickCount> insertAdUserClickCounts = new ArrayList<AdUserClickCount>();
        List<AdUserClickCount> updateAdUserClickCounts = new ArrayList<AdUserClickCount>();

        String selectSQL = "SELECT count(*) FROM ad_user_click_count " +
                "WHERE date=? AND user_id=? AND ad_id=?";
        Object[] selectParam = null;

        for (AdUserClickCount adUserClickCount : adUserClickCounts){
            final AdUserClickCountQueryResult queryResult = new AdUserClickCountQueryResult();

            selectParam = new Object[]{ adUserClickCount.getDate(),
            adUserClickCount.getUserid(),
            adUserClickCount.getAdid()};

            jdbcHelper.executeQuery(selectSQL, selectParam, new JDBCHelper.QueryCallback() {
                @Override
                public void process(ResultSet rs) throws Exception {
                    if (rs.next()){
                        int clickCount  = rs.getInt(1);
                        queryResult.setClickCount(clickCount);
                    }
                }
            });

            int count  = queryResult.getClickCount();

            if (count > 0){
                updateAdUserClickCounts.add(adUserClickCount);
            }else{
                insertAdUserClickCounts.add(adUserClickCount);
            }
        }

        //执行批量插入
        String insertSQL = "INSERT INTO ad_user_click_count VALUES(?,?,?,?)";
        List<Object[]> insertParamList = new ArrayList<Object[]>();

        for (AdUserClickCount adUserClickCount : insertAdUserClickCounts){
            Object[] insertParam = new Object[]{
                    adUserClickCount.getDate(),
                    adUserClickCount.getUserid(),
                    adUserClickCount.getAdid(),
                    adUserClickCount.getClickCount()};

            insertParamList.add(insertParam);
        }

        jdbcHelper.executeBatch(insertSQL,insertParamList);


        //执行批量更新操作
        String updateSQL = "UPDATE ad_user_click_count SET click_count=? " +
                "WHERE date=? AND user_id=? AND ad_id=?";

        List<Object[]> updateParamList = new ArrayList<Object[]>();
        for (AdUserClickCount adUserClickCount:updateAdUserClickCounts){
            Object[] updateParam = new Object[]{
                    adUserClickCount.getClickCount(),
                    adUserClickCount.getDate(),
                    adUserClickCount.getUserid(),
                    adUserClickCount.getAdid()
            };

            updateParamList.add(updateParam);
        }

        jdbcHelper.executeBatch(updateSQL,updateParamList);

    }

    @Override
    public int findClickCountByMultiKey(String date, long userid, long adid) {

        String sql = "SELECT click_count " +
                "FROM ad_user_click_count " +
                "WHERE date=? " +
                "AND user_id=? " +
                "AND ad_id=?";
        Object[] params = new Object[]{date,userid,adid};

        final AdUserClickCountQueryResult queryResult = new AdUserClickCountQueryResult();

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(sql, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    int clickCount = rs.getInt(1);
                    queryResult.setClickCount(clickCount);
                }
            }
        });

        int clickCount = queryResult.getClickCount();
        return clickCount;
    }

    /**
     * 查询个广告的点击量
     * @param dates
     * @return
     */
    @Override
    public JSONObject selectAdid2Count(String dates) {
        dates ="2019-04-19";
        JSONObject adid2clickCount = new JSONObject();
        Object[] param = new Object[]{dates};
        String select ="SELECT ad_id,sum(click_count) FROM ad_user_click_count WHERE date=? GROUP BY ad_id";

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(select, param, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    String adid = String.valueOf(rs.getInt(1));
                    long clickCount = rs.getInt(2);
                    adid2clickCount.fluentPut("广告"+adid,clickCount);
                }
            }
        });
        return adid2clickCount;
    }


}
