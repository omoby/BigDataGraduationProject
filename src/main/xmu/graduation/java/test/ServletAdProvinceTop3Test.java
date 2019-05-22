package graduation.java.test;

import graduation.java.dao.IAdProvinceTop3DAO;
import graduation.java.domain.AdProvinceTop3;
import graduation.java.factory.DAOFactory;

import java.util.List;

/**
 * FileName: ServletAdProvinceTop3Test
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-21 下午5:00
 * Description:
 */
public class ServletAdProvinceTop3Test {
    public static void main(String[] args) {
        IAdProvinceTop3DAO iAdProvinceTop3DAO = DAOFactory.getAdProvinceDAO();
        List<AdProvinceTop3> adProvinceTop3s = iAdProvinceTop3DAO.select(null);
        for (AdProvinceTop3 adProvinceTop3 : adProvinceTop3s){
            System.out.println(adProvinceTop3.toString());
        }
    }
}
