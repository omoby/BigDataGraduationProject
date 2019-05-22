package graduation.java.dao;

import graduation.java.domain.AreaTop3Product;

import java.util.List;
import java.util.Map;

/**
 * FileName: IAreaTop3ProductDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-1 下午11:39
 * Description:
 */
public interface IAreaTop3ProductDAO {
    /**
     * 各区域top3热门商品插入msqyl DAO
     * @param areaTop3Products
     */
    void insertBatch(List<AreaTop3Product> areaTop3Products);

    /**
     * 各区域top3热门商品可视化展示
     * @param areaTop3Product
     * @return
     */

    Map<String,String> select(AreaTop3Product areaTop3Product);
}
