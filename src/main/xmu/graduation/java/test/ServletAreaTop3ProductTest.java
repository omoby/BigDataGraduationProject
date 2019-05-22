package graduation.java.test;

import graduation.java.dao.IAreaTop3ProductDAO;
import graduation.java.domain.AreaTop3Product;
import graduation.java.factory.DAOFactory;

import java.util.Map;

/**
 * FileName: ServletAreaTop3ProductTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-10 上午11:10
 * Description:
 */
public class ServletAreaTop3ProductTest {
    public static void main(String[] args) {
        IAreaTop3ProductDAO areaTop3ProductDAO = DAOFactory.getAreaTop3ProductDAO();
        AreaTop3Product areaTop3Product = new AreaTop3Product();
        Map<String,String> map  = areaTop3ProductDAO.select(areaTop3Product);
        for (Map.Entry entry : map.entrySet()){
            String area = (String)entry.getKey();
            String value = (String)entry.getValue();
            System.out.println(area +" " +value);
        }
    }
}
