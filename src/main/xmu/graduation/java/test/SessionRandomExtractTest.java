package graduation.java.test;

import graduation.java.dao.ISessionRandomExtractDAO;
import graduation.java.domain.SessionRandomExtract;
import graduation.java.factory.DAOFactory;
import graduation.java.jdbc.JDBCHelper;

import java.util.List;

/**
 * FileName: SessionRandomExtractTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午8:45
 * Description:
 * 测试随机插入session表
 */
public class SessionRandomExtractTest {
    public static void main(String[] args){
       // insertTest();
        selectTest();



    }

    private static  void insertTest(){
        String sql ="insert into session_random_extract values(?,?,?,?,?)";
        SessionRandomExtract sessionRandomExtract = new SessionRandomExtract();
        long taskid =1;
        String sessionid = "123";
        String startTime = "2019-03-05";
        String serachKeyWords = "milk";
        String clickCategoryIds = "1,34,5";

        Object[] param = new Object[]{taskid, sessionid,
                startTime,serachKeyWords,clickCategoryIds
        };
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql,param);
    }

    private static void selectTest(){
        ISessionRandomExtractDAO sessionRandomExtractDAO = DAOFactory.getSessionRandomExtractDAO();
        List<SessionRandomExtract> sessionRandomExtracts =  sessionRandomExtractDAO.select(new SessionRandomExtract());
        for (SessionRandomExtract sessionRandomExtract : sessionRandomExtracts){
            System.out.println(sessionRandomExtract.toString());
        }
    }
}
