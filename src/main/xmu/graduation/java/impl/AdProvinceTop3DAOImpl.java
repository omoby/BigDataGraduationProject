package graduation.java.impl;

import graduation.java.dao.IAdProvinceTop3DAO;
import graduation.java.domain.AdProvinceTop3;
import graduation.java.jdbc.JDBCHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName: AdProvinceTop3DAOImpl
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 下午4:37
 * Description:
 *
 * 各省份热门广告top3DAO实现类
 */
public class AdProvinceTop3DAOImpl implements IAdProvinceTop3DAO {
    @Override
    public void updateBatch(List<AdProvinceTop3> adProvinceTop3List) {

        JDBCHelper jdbcHelper = JDBCHelper.getInstance();

        // 先做一次去重（date_province）

        List<String> dateProvinces  = new ArrayList<String>();

        for (AdProvinceTop3 adProvinceTop3 : adProvinceTop3List){
            String date = adProvinceTop3.getDate();
            String province = adProvinceTop3.getProvince();
            String key = date + "_"+ province;
            if (!dateProvinces.contains(key)){
                dateProvinces.add(key);
            }
        }

        //根据去重后的date和province进行批量删除操作
        String deleteSQL = "DELETE FROM ad_province_top3 WHERE date=? AND province=?";
        List<Object[]> deleteParamsList = new ArrayList<Object[]>();

        for (String dateProvince : dateProvinces){

            String[] keySplited = dateProvince.split("_");
            String date = keySplited[0];
            String province = keySplited[1];

            Object[] params = new Object[]{date,province};
            deleteParamsList.add(params);

        }

        jdbcHelper.executeBatch(deleteSQL,deleteParamsList);



        // 批量插入传入进来的所有数据

        String insertSQL = "INSERT INTO ad_province_top3 VALUES(?,?,?,?)";

        List<Object[]> insertParamsList = new ArrayList<Object[]>();

        for (AdProvinceTop3 adProvinceTop3 : adProvinceTop3List){
            Object[] params = new Object[]{
                    adProvinceTop3.getDate(),
                    adProvinceTop3.getProvince(),
                    adProvinceTop3.getAdid(),
                    adProvinceTop3.getClickCount()};
            insertParamsList.add(params);
        }

        jdbcHelper.executeBatch(insertSQL,insertParamsList);
    }

    /**
     * 查询各省份热门广告数据
     * @param dates
     * @return
     */
    @Override
    public List<AdProvinceTop3> select(String dates) {
        dates = "2019-04-19";
        List<AdProvinceTop3> adProvinceTop3s = new ArrayList<>();
        String select = "SELECT province,ad_id,click_count FROM ad_province_top3 WHERE date=? ORDER BY province";
        Object[] params = new Object[]{dates};
        JDBCHelper jdbcHelper = JDBCHelper.getInstance();
        String finalDates = dates;
        jdbcHelper.executeQuery(select, params, new JDBCHelper.QueryCallback() {
            @Override
            public void process(ResultSet rs) throws Exception {
                while (rs.next()){
                    String province = rs.getString(1);
                    int adId = rs.getInt(2);
                    int clickCount = rs.getInt(3);
                    AdProvinceTop3 adProvinceTop3 = new AdProvinceTop3();
                    adProvinceTop3.setDate(finalDates);
                    adProvinceTop3.setProvince(province);
                    adProvinceTop3.setAdid(adId);
                    adProvinceTop3.setClickCount(clickCount);
                    adProvinceTop3s.add(adProvinceTop3);
                }

            }
        });


        return adProvinceTop3s;
    }
}
