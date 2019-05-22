package graduation.java.dao;

import graduation.java.domain.PageSplitConvertRate;

import java.util.Map;

/**
 * FileName: IPageSplitConvertRateDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-31 下午3:29
 * Description:
 * 页面切片转化率DAO借口类
 */
public interface IPageSplitConvertRateDAO {

    /**
     * 插入页面切片转化率的计算结果
     * @param pageSplitConvertRate
     */
    void insert(PageSplitConvertRate pageSplitConvertRate);

    /**
     * 查询页面切片转化率的计算结果用于展示
     * @param pageSplitConvertRate
     * @return
     */

    Map<Long,String> select(PageSplitConvertRate pageSplitConvertRate);
}
