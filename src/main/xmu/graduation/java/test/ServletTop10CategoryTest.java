package graduation.java.test;

import graduation.java.dao.ITop10CategoryDAO;
import graduation.java.domain.Top10Category;
import graduation.java.factory.DAOFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * FileName: ServletTop10CategoryTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-28 下午4:21
 * Description:
 */
public class ServletTop10CategoryTest {
    public static void main(String[] args){
        Top10Category top10Category = new Top10Category();
        ITop10CategoryDAO iTop10CategoryDAO  = DAOFactory.getTop10CategoryDAO();
        Map<String,Object> map = iTop10CategoryDAO.select(top10Category);
        long[] categoryid = (long[]) map.get("categoryid");
        long[] clickcount = (long[]) map.get("clickcount");
        long[] ordercount = (long[]) map.get("ordercount");
        long[] paycount = (long[]) map.get("paycount");
        System.out.println("categoryid: "+ Arrays.toString(categoryid));
        System.out.println("clickCount: "+ Arrays.toString(clickcount));
        System.out.println("orderCount: "+ Arrays.toString(ordercount));
        System.out.println("payCount: "+ Arrays.toString(paycount));
    }
}
