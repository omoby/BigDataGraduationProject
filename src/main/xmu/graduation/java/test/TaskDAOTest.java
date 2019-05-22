package graduation.java.test;

import graduation.java.dao.ITaskDAO;
import graduation.java.domain.Task;
import graduation.java.factory.DAOFactory;

/**
 * FileName: TaskDAOTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-27 下午5:35
 * Description:任务管理DAO测试类
 */
public class TaskDAOTest {
    public static void main(String[] args){
        ITaskDAO taskDAO = DAOFactory.getTaskDAO();
        Task task = taskDAO.findById(1);
        System.out.println(task.getTaskName());
    }
}
