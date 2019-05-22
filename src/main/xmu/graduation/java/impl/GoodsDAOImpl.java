package graduation.java.impl;

import graduation.java.dao.GoodsDao;
import graduation.java.domain.Goods;
import graduation.java.jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * FileName: GoodsDAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-19 上午11:20
 * Description:
 * 测试可视化环境所用的Goods表查询借口实现类
 */
public class GoodsDAOImpl implements GoodsDao {
    @Override
    public List<Goods> queryAllGoods() throws SQLException {
        String sql = "select * from goods where id >=1";
        Object[] param = new Object[]{};
        List<Goods> list = new ArrayList<Goods>();
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(sql, param, new JDBCHelper.QueryCallback() {

            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    Goods goods = new Goods();
                    goods.setId(rs.getInt("id"));
                    goods.setName(rs.getString("name"));
                    goods.setPrice(rs.getInt("price"));
                    goods.setSales(rs.getInt("sales"));
                    goods.setStock(rs.getInt("stock"));
                    goods.setDetail(rs.getString("detail"));
                    list.add(goods);
                }
            }
        });
        return list;

    }
}
