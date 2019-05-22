package graduation.java.impl;

import graduation.java.dao.IAreaTop3ProductDAO;
import graduation.java.domain.AreaTop3Product;
import graduation.java.jdbc.JDBCHelper;
import org.netlib.lapack.Iparmq;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName: AreaTop3ProductDAOImp
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-1 下午11:41
 * Description:
 * 各区域top3热门商品DAO实现类
 */
public class AreaTop3ProductDAOImp  implements IAreaTop3ProductDAO {
    @Override
    public void insertBatch(List<AreaTop3Product> areaTop3Products) {
        String sql = "insert into area_top3_product values(?,?,?,?,?,?,?,?)";
        List<Object[]> paramList = new ArrayList<Object[]>();
        for (AreaTop3Product areaTop3Product: areaTop3Products){
            Object[] param = new Object[8];

            param[0]= areaTop3Product.getTaskid();
            param[1] = areaTop3Product.getArea();
            param[2] = areaTop3Product.getAreaLevel();
            param[3] = areaTop3Product.getProductid();
            param[4] = areaTop3Product.getCityInfos();
            param[5] = areaTop3Product.getClickCount();
            param[6] = areaTop3Product.getProductName();
            param[7] = areaTop3Product.getProductStatus();

            paramList.add(param);
        }


        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeBatch(sql,paramList);

    }

    @Override
    public Map<String, String> select(AreaTop3Product areaTop3Product) {
        Map<String,String> resultMap = new HashMap<>();
        String selectSQL = "SELECT area,click_count, product_name FROM area_top3_product";

        Object[] params = new Object[]{};
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {

            @Override
            public void process(ResultSet rs) throws Exception {
                int i= 1;
                String area = null;
                String click2product = "";
                while (rs.next()){
                    if (i % 3 == 1){
                        area = rs.getString(1);
                        click2product = "";
                    }
                    long clickCount = rs.getLong(2);
                    String productName = rs.getString(3);
                    click2product += clickCount+"|"+productName+",";
                    if (i % 3 == 0){
                        click2product = click2product.substring(0,click2product.length()-1);
                    }
                    resultMap.put(area,click2product);
                    i++;
                }

            }
        });
        return resultMap;
    }
}
