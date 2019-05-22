package graduation.java.impl;

import graduation.java.dao.IPageSplitConvertRateDAO;
import graduation.java.domain.PageSplitConvertRate;
import graduation.java.jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName: PageSplitConvertRateImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-31 下午3:31
 * Description:
 * 页面切片转化率DAO接口实现类
 */
public class PageSplitConvertRateDAOImpl  implements IPageSplitConvertRateDAO {

    /**
     * 插入页面切片转化率的计算结果
     * @param pageSplitConvertRate
     */
    @Override
    public void insert(PageSplitConvertRate pageSplitConvertRate) {
        String sql = "insert into page_split_convert_rate values(?,?)";
        Object[] param = new Object[]{
                pageSplitConvertRate.getTaskid(),
                pageSplitConvertRate.getConvertRate()
        };

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeUpdate(sql,param);

    }

    /**
     * 查询页面切片转化率的计算结果用于展示
     * @param pageSplitConvertRate
     * @return
     */
    @Override
    public Map<Long, String> select(PageSplitConvertRate pageSplitConvertRate) {
        Map<Long,String> result = new HashMap<Long,String>();

        String selectSQL = "SELECT * FROM page_split_convert_rate WHERE task_id=?";

        Object[] params= new Object[]{2};

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        jdbcHelper.executeQuery(selectSQL, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    long taskid = rs.getLong(1);
                    String rateString = rs.getString(2);
                    result.put(taskid,rateString);
                }

            }
        });
        return result;
    }
}
