package graduation.java.dao;

import graduation.java.domain.Top10Category;

import java.util.Map;

/**
 * FileName: ITop10CategoryDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-23 上午10:38
 * Description:
 *
 * Top10品类DAO
 *
 */
public interface ITop10CategoryDAO {
    /**
     * 插入点击、下单、支付前十的品类
     * @param top10Category
     */
    void  insert(Top10Category top10Category);

    /**
     * 查询排名前十的品类的点击次数，下单次数和支付次数
     * @param top10Category
     * @return
     */
    Map<String,Object> select(Top10Category top10Category);
}
