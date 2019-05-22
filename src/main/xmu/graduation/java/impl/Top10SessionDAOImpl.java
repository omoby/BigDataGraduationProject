package graduation.java.impl;

import graduation.java.dao.ITop10SessionDAO;
import graduation.java.domain.Top10Session;
import graduation.java.jdbc.JDBCHelper;

/**
 * FileName: Top10SessionDAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-24 下午5:04
 * Description:
 * top10品类session点击top10DAO实现
 */
public class Top10SessionDAOImpl implements ITop10SessionDAO {

    @Override
    public void insert(Top10Session top10Session) {
        String sql = "insert into top10_session values(?,?,?,?)";
        Object[] param = new Object[]{
                top10Session.getTaskid(),
                top10Session.getCategoryid(),
                top10Session.getSessionid(),
                top10Session.getClickCount()
        };

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql,param);

    }
}
