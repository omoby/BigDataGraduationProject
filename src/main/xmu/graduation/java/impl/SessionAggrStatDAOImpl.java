package graduation.java.impl;

import graduation.java.dao.ISessionAggrStatDAO;
import graduation.java.domain.SessionAggrStat;
import graduation.java.jdbc.JDBCHelper;
import org.apache.commons.collections.map.HashedMap;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: SessionAggrStatDAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-4 下午7:35
 * Description:
 * session聚合统计DAO实现类
 */
public class SessionAggrStatDAOImpl implements ISessionAggrStatDAO {
    public void insert(SessionAggrStat sessionAggrStat){
        String sql = "insert into session_aggr_stat "
                +"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] params = new Object[]{
                sessionAggrStat.getTaskid(),
                sessionAggrStat.getSession_count(),
                sessionAggrStat.getVisit_length_1s_3s_ratio(),
                sessionAggrStat.getVisit_length_4s_6s_ratio(),
                sessionAggrStat.getVisit_length_7s_9s_ratio(),
                sessionAggrStat.getVisit_length_10s_30s_ratio(),
                sessionAggrStat.getVisit_length_30s_60s_ratio(),
                sessionAggrStat.getVisit_length_1m_3m_ratio(),
                sessionAggrStat.getVisit_length_3m_10m_ratio(),
                sessionAggrStat.getVisit_length_10m_30m_ratio(),
                sessionAggrStat.getVisit_length_30m_ratio(),

                sessionAggrStat.getStep_length_1_3_ratio(),
                sessionAggrStat.getStep_length_4_6_ratio(),
                sessionAggrStat.getStep_length_7_9_ratio(),
                sessionAggrStat.getStep_length_10_30_ratio(),
                sessionAggrStat.getStep_length_30_60_ratio(),
                sessionAggrStat.getStep_length_60_ratio()
        };
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql,params);

    }

    /**
     * session聚合分析可视化
     * @param sessionAggrStat
     * @return 返回一个session访问数最多的一条记录
     */
    public Map<String,Object> select(SessionAggrStat sessionAggrStat){
        String sql = "select * from session_aggr_stat "
                +" order by session_count desc "
                +" limit 1";
        Map<String,Object> map = new HashMap<String,Object>();
        double[] visitLength = new double[9];
        double[] visitStep = new double[6];
        long[] taskid = new long[1];
        long[] sessionCount = new long[1];

        Object[] params = new Object[]{};
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(sql, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    taskid[0] = rs.getLong("task_id");
                    sessionCount[0] = rs.getLong("session_count");
                    visitLength[0] = rs.getDouble("1s_3s");
                    visitLength[1] = rs.getDouble("4s_6s");
                    visitLength[2] = rs.getDouble("7s_9s");
                    visitLength[3] = rs.getDouble("10s_30s");
                    visitLength[4] = rs.getDouble("30s_60s");
                    visitLength[5] = rs.getDouble("1m_3m");
                    visitLength[6] = rs.getDouble("3m_10m");
                    visitLength[7] =rs.getDouble("10m_30m");
                    visitLength[8]= rs.getDouble("30m");

                    visitStep[0]= rs.getDouble("1_3");
                    visitStep[1] = rs.getDouble("4_6");
                    visitStep[2] = rs.getDouble("7_9");
                    visitStep[3] = rs.getDouble("10_30");
                    visitStep[4] = rs.getDouble("30_60");
                    visitStep[5] = rs.getDouble("60");

                }

            }
        });
        /*System.out.println("visitLength: "+ visitLength.toString());
        System.out.println("visitStep: " + visitStep.toString());*/
        map.put("taskid",taskid[0]);
        map.put("sessionCount",sessionCount[0]);
        map.put("visitLength",visitLength);
        map.put("visitStep",visitStep);

        return map;
    }

}
