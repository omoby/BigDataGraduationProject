package graduation.java.impl;

import graduation.java.dao.ISessionAggrStatDAO;
import graduation.java.dao.ISessionRandomExtractDAO;
import graduation.java.domain.SessionRandomExtract;
import graduation.java.jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: SessionRandomExtractImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午8:02
 * Description:
 *随机抽取session出入mysql
 */
public  class SessionRandomExtractDAOImpl implements ISessionRandomExtractDAO {
    /**
     * 随机抽取session插入mysql数据库方法实现
     * @param sessionRandomExtract
     */
    public void insert(SessionRandomExtract sessionRandomExtract){
        String sql = "insert into session_random_extract values(?,?,?,?,?)";
        Object[] param = new Object[]{
                sessionRandomExtract.getTaskid(),
                sessionRandomExtract.getSessionid(),
                sessionRandomExtract.getStartTime(),
                sessionRandomExtract.getSerachKeyWords(),
                sessionRandomExtract.getClickCategoryIds()
        };
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql,param);
    }

    /**
     * 查询随机抽取session
     * @param sessionRandomExtract
     * @return
     */

    @Override
    public List<SessionRandomExtract> select(SessionRandomExtract sessionRandomExtract) {
        String selectSQL = "SELECT * FROM session_random_extract";
        List<SessionRandomExtract> sessionRandomExtracts = new ArrayList<>();

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        Object[] params = new Object[]{};
        jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    SessionRandomExtract session = new SessionRandomExtract();
                    long taskid = rs.getLong(1);
                    String sessionid = rs.getString(2);
                    String startTime = rs.getString(3);
                    String searchKeyWords = rs.getString(4);
                    String clickCategoryids = rs.getString(5);

                    session.setTaskid(taskid);
                    session.setSessionid(sessionid);
                    session.setStartTime(startTime);
                    session.setSerachKeyWords(searchKeyWords);
                    session.setClickCategoryIds(clickCategoryids);

                    sessionRandomExtracts.add(session);
                }


            }
        });
        return sessionRandomExtracts;
    }
}
