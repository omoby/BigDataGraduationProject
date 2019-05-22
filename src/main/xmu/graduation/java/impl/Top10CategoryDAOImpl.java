package graduation.java.impl;

import graduation.java.dao.ITop10CategoryDAO;
import graduation.java.domain.Top10Category;
import graduation.java.jdbc.JDBCHelper;
import org.apache.commons.collections.map.HashedMap;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: Top10CategoryImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-23 上午10:41
 * Description:
 *
 * Top10品类DAO接口实现
 */
public class Top10CategoryDAOImpl implements ITop10CategoryDAO {
    /**
     * 插入点击、下单、支付前十名的品类
     * @param top10Category
     */

    @Override
    public void insert(Top10Category top10Category) {
        String sql = "insert into top10_category values(?,?,?,?,?)";
        if (top10Category.getCategoryid() == Long.MAX_VALUE){
            System.out.println("Insert Category:"+ top10Category.getCategoryid());
            top10Category.setCategoryid(Long.valueOf(10240));
        }
        Object[] param = new Object[]{ top10Category.getTaskid(),
                top10Category.getCategoryid(),
                top10Category.getClickCount(),
                top10Category.getOrderCount(),
                top10Category.getPayCount()
        };

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql,param);
    }

    @Override
    public Map<String, Object> select(Top10Category top10Category) {
        String sql = "select * from top10_category order by click_count desc limit 10";
        Map<String,Object> map = new HashMap<String,Object>();
        long[] categoryid = new long[10];
        long[] clickcount = new long[10];
        long[] ordercount = new long[10];
        long[] paycount = new long[10];

        Object[] param = new Object[]{};
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(sql, param, new JDBCHelper.QueryCallback() {
            int i = 0;
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    categoryid[i] = rs.getLong(2);
                    clickcount[i] = rs.getLong(3);
                    ordercount[i] = rs.getLong(4);
                    paycount[i] = rs.getLong(5);
                    i++;
                }
            }
        });
        map.put("categoryid",categoryid);
        map.put("clickcount",clickcount);
        map.put("ordercount",ordercount);
        map.put("paycount",paycount);

        return map;
    }
}
