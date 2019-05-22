package graduation.java.test;

import graduation.java.dao.ITop10CategoryDAO;
import graduation.java.domain.Top10Category;
import graduation.java.factory.DAOFactory;

/**
 * FileName: Top10CategoryTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-23 上午10:48
 * Description:
 */
public class Top10CategoryTest {

    public static void main(String[] args){
        Top10Category top10Category = new Top10Category();
        ITop10CategoryDAO top10CategoryDAO = DAOFactory.getTop10CategoryDAO();
        long taskid = 3L;
        long categoryid = 132L;
        long clickCount = 1000L;
        long orderCount = 100L;
        long payCount = 19L;
        top10Category.setTaskid(taskid);
        top10Category.setCategoryid(categoryid);
        top10Category.setClickCount(clickCount);
        top10Category.setOrderCount(orderCount);
        top10Category.setPayCount(payCount);

        top10CategoryDAO.insert(top10Category);


    }
}
