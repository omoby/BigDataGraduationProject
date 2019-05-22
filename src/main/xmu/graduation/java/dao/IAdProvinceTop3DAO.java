package graduation.java.dao;

import graduation.java.domain.AdProvinceTop3;

import java.util.List;

/**
 * FileName: IAdProvinceTop3DAO
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 下午4:34
 * Description:
 * 各省份热门广告top3DAO接口类
 */
public interface IAdProvinceTop3DAO {

    /**
     * 批量更新各省份热门top3广告
     * @param adProvinceTop3List
     */
    void updateBatch(List<AdProvinceTop3> adProvinceTop3List);

    /**
     * 查询各省份热门广告数据
     * @param dates
     */
    List<AdProvinceTop3> select(String dates);
}
