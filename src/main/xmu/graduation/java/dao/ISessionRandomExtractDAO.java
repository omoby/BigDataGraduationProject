package graduation.java.dao;

import graduation.java.domain.SessionRandomExtract;

import java.util.List;

/**
 * FileName: ISessionRandomExtractDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午7:59
 * Description:
 */
public interface ISessionRandomExtractDAO {
    /**
     * 插入随机抽取的session
     * @param sessionRandomExtract
     */
    void insert(SessionRandomExtract sessionRandomExtract);

    /**
     * 查询随机抽取的session
     * @param sessionRandomExtract
     * @return
     */
    List<SessionRandomExtract> select(SessionRandomExtract sessionRandomExtract);
}
