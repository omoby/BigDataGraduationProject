package graduation.java.dao;

import graduation.java.domain.SessionAggrStat;

import java.util.Map;

/**
 * FileName: ISessionAggrStatDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-4 下午5:28
 * Description:
 * session聚合统计模块DAO接口
 */
public interface ISessionAggrStatDAO {

    /**
     * 插入session聚合统计结果
     * @param sessionAggrStat
     */
    void insert(SessionAggrStat sessionAggrStat);

    /**
     * 查询session聚合类的统计结果
     * @param sessionAggrStat
     * @return
     */
    Map<String,Object> select(SessionAggrStat sessionAggrStat);

}
