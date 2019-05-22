package graduation.java.dao;

import com.alibaba.fastjson.JSONObject;
import graduation.java.domain.AdStat;

import java.util.List;
import java.util.Map;

/**
 * FileName: IAdStatDAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 上午10:28
 * Description:
 *
 * 广告点击实时状态DAO接口
 */
public interface IAdStatDAO {
    /**
     * 批量插入广告实时状态信息
     * @param adStats
     */
    void updateBatch(List<AdStat> adStats);

    /**
     * 查询广告实时状态信息
     * @param date
     * @return
     */
    Map<String,Object> select(String date);

    /**
     * 查询指定时间日期内的各省份的广告点击数
     * @param dates
     * @return
     */
    Map<String,Integer> selectProvinceClick(String dates);

    /**
     * 查询个省份的广告点击占比
     * @param dates
     * @return
     */
    JSONObject selectProvinceClickPercent(String dates);



}
