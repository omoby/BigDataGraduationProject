package graduation.java.dao;

import graduation.java.domain.Goods;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * FileName: GoodsDao
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-19 上午11:14
 * Description:
 * 测试可视化环境所用的Goods表查询借口
 */
public interface GoodsDao {
    List<Goods> queryAllGoods() throws SQLException;
}
