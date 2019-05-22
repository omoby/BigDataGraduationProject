package graduation.java.dao;

import graduation.java.domain.AdClickTrend;

import java.util.List;
import java.util.Map;

/**
 * FileName: IAdClickTrendDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 下午8:36
 * Description:
 * 一小时内广告点击DAO接口类
 */
public interface IAdClickTrendDAO {
    /**
     * 批量更新或插入一小时内广告点击趋势数据
     * @param adClickTrendList
     */
    void updateBatch(List<AdClickTrend> adClickTrendList);

    /**
     * 查询最近60分钟广告点击率
     * @return
     */

    Map<String,Object> selectLast60min();
}
