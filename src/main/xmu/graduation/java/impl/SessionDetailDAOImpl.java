package graduation.java.impl;

import graduation.java.dao.ISessionDetailDAO;
import graduation.java.domain.SessionDetail;
import graduation.java.jdbc.JDBCHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName: SessionDetailImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午9:39
 * Description:
 * session明细DAO实现类
 */
/**
 * session明细DAO实现类
 * @author Administrator
 *
 */
public class SessionDetailDAOImpl implements ISessionDetailDAO {

    /**
     * 插入一条session明细数据
     * @param sessionDetail
     */
    public void insert(SessionDetail sessionDetail) {
        String sql = "insert into session_detail values(?,?,?,?,?,?,?,?,?,?,?,?)";

        Object[] params = new Object[]{sessionDetail.getTaskid(),
                sessionDetail.getUserid(),
                sessionDetail.getSessionid(),
                sessionDetail.getPageid(),
                sessionDetail.getActionTime(),
                sessionDetail.getSeachKeyWord(),
                sessionDetail.getClickCategoryId(),
                sessionDetail.getClickProductId(),
                sessionDetail.getOrderCategoryIds(),
                sessionDetail.getOrderProductIds(),
                sessionDetail.getPayCategoryIds(),
                sessionDetail.getPayProductIds()};

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql, params);
    }

    /**
     * 批量插入session明细数据
     * @param sessionDetails
     */
    public void insertBatch(List<SessionDetail> sessionDetails) {
        String sql = "insert into session_detail values(?,?,?,?,?,?,?,?,?,?,?,?)";

        List<Object[]> paramsList = new ArrayList<Object[]>();
        for(SessionDetail sessionDetail : sessionDetails) {
            Object[] params = new Object[]{sessionDetail.getTaskid(),
                    sessionDetail.getUserid(),
                    sessionDetail.getSessionid(),
                    sessionDetail.getPageid(),
                    sessionDetail.getActionTime(),
                    sessionDetail.getSeachKeyWord(),
                    sessionDetail.getClickCategoryId(),
                    sessionDetail.getClickProductId(),
                    sessionDetail.getOrderCategoryIds(),
                    sessionDetail.getOrderProductIds(),
                    sessionDetail.getPayCategoryIds(),
                    sessionDetail.getPayProductIds()};
            paramsList.add(params);
        }

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeBatch(sql, paramsList);
    }

}
