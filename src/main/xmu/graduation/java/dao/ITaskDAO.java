package graduation.java.dao;

import graduation.java.domain.Task;

/**
 * FileName: ITaskDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-27 下午4:21
 * Description:任务管理DAO接口
 */
public interface ITaskDAO {
    /**
     * 根据主键查询任务
     * @param taskid 主键
     * @return 任务
     */
    Task findById(long taskid);
}
