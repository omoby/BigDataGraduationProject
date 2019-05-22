package graduation.java.test;

import graduation.java.dao.IAdClickTrendDAO;
import graduation.java.factory.DAOFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * FileName: ServletAdClickTrendTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-22 下午7:50
 * Description:
 */
public class ServletAdClickTrendTest {
    public static void main(String[] args) {
        IAdClickTrendDAO trendDAO = DAOFactory.getAdClickTrendADO();
        Map<String,Object> map = trendDAO.selectLast60min();
       String[] datehourminut =(String[]) map.get("datehoutminut");
           double[] clickCount = (double[])map.get("clickcount");
        double[] avercount =(double[])map.get("avercount");
        System.out.println("datehourminute: "+Arrays.toString(datehourminut));
        System.out.println("clickcount: "+Arrays.toString(clickCount));
        System.out.println("avercount: "+Arrays.toString(avercount));
    }
}
