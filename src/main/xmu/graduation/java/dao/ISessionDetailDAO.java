package graduation.java.dao;

import graduation.java.domain.SessionDetail;

import java.util.List;

/**
 * FileName: ISessionDetailDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午9:38
 * Description:
 * Session明细DAO接口
 */
/**
 * Session明细DAO接口
 * @author Administrator
 *
 */
public interface ISessionDetailDAO {

    /**
     * 插入一条session明细数据
     * @param sessionDetail
     */
    void insert(SessionDetail sessionDetail);

    /**
     * 批量插入session明细数据
     * @param sessionDetails
     */
    void insertBatch(List<SessionDetail> sessionDetails);

}
