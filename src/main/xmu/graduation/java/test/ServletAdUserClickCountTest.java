package graduation.java.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import graduation.java.dao.IAdUserClickCountDAO;
import graduation.java.factory.DAOFactory;

/**
 * FileName: ServletAdUserClickCount
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-21 下午10:49
 * Description:
 */
public class ServletAdUserClickCountTest {
    public static void main(String[] args) {
        IAdUserClickCountDAO adUserClickCountDAO = DAOFactory.getAdUserClickCountDAO();
        JSONObject result = adUserClickCountDAO.selectAdid2Count(null);
        System.out.println(result.toString());
    }
}
