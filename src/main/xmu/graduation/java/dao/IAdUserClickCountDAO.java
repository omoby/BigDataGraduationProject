package graduation.java.dao;


import com.alibaba.fastjson.JSONObject;
import graduation.java.domain.AdUserClickCount;

import java.util.List;

/**
 * FileName: IAdUserClickCountDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-3 上午10:42
 * Description:
 * 广告用户点击量DAO接口类
 */
public interface IAdUserClickCountDAO {
    /**
     * 批量更新广告用户点击量
     * @param adUserClickCounts
     */
    void updateBatch(List<AdUserClickCount> adUserClickCounts);

    /**
     * 根据多个key查询用户广告点击量
     * @param date 日期
     * @param userid 用户id
     * @param adid 广告id
     * @return
     */
    int findClickCountByMultiKey(String date,long userid,long adid);

    /**
     * 查询个广告的点击量
     * @param date
     * @return
     */

    JSONObject selectAdid2Count(String date);
}
