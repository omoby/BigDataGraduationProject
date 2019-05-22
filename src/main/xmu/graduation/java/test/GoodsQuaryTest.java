package graduation.java.test;

import graduation.java.dao.GoodsDao;
import graduation.java.domain.Goods;
import graduation.java.factory.DAOFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * FileName: GoodsQuaryTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-19 上午11:32
 * Description:
 * 测试测试可视化环境中数据库是否连接成功
 */
public class GoodsQuaryTest {
    public static void main(String[] args){
        Goods goods = new Goods();
        GoodsDao goodsDao = DAOFactory.getGoodsDAO();
        try {
            List<Goods>  list   = goodsDao.queryAllGoods();
            for (Goods goods1: list){
                System.out.println(goods1.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
