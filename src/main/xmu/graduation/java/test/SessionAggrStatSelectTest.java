package graduation.java.test;

import graduation.java.dao.ISessionAggrStatDAO;
import graduation.java.domain.SessionAggrStat;
import graduation.java.factory.DAOFactory;

import java.util.Map;

/**
 * FileName: SessionAggrStatSelectTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-18 下午9:07
 * Description:测试session聚合数据的单条查询
 */
public class SessionAggrStatSelectTest {
    public static void main(String[] args){
        SessionAggrStat sessionAggrStat = new SessionAggrStat();
        ISessionAggrStatDAO sessionAggrStatDAO = DAOFactory.getSessionAggrStatDAO();
        Map<String,Object> map = sessionAggrStatDAO.select(sessionAggrStat);
        double[] lenghtArrays = new double[9];
        double[] stepArrays = new double[6];
        lenghtArrays = (double[]) map.get("visitLength");
        stepArrays = (double[]) map.get("visitStep");
        long taskid = (long)map.get("taskid");
        long sessionCount = (long)map.get("sessionCount");
        System.out.println("taskId: "+ taskid);
        System.out.println("sessionCount: "+ sessionCount);
        System.out.print("visitLength: ");
        for (int i = 0; i < lenghtArrays.length; i++){
            System.out.print(lenghtArrays[i] + "\t");
        }
        System.out.println();
        System.out.print("visitStep: ");
        for (int i = 0; i < stepArrays.length; i++){
            System.out.print(stepArrays[i] + "\t");
        }
    }
}
