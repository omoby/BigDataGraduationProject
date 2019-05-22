package graduation.java.test;

import com.alibaba.fastjson.JSONObject;
import graduation.java.dao.IAdStatDAO;
import graduation.java.factory.DAOFactory;

import java.util.Arrays;
import java.util.Map;

/**
 * FileName: ServletAdStatTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-21 上午9:24
 * Description:
 */
public class ServletAdStatTest {
    public static void main(String[] args) {
        //selectAdStat();
        selectProvinceCount();
    }

    public static void selectAdStat(){
        IAdStatDAO adStatDAO = DAOFactory.getAdStatDAO();
        Map<String,Object> map = adStatDAO.select(null);
        String[] cityName = (String[]) map.get("city");
        System.out.println(Arrays.toString(cityName));
        for (String city : cityName){
            int[] city2adid = (int[])map.get(city);
            System.out.println(city + " "+ Arrays.toString(city2adid));
        }

        Map<String,Integer> provinceCount = adStatDAO.selectProvinceClick(null);
        for (Map.Entry entry: provinceCount.entrySet()){
            System.out.println(entry.getKey() +" === "+ entry.getValue());
        }
    }

    public static void selectProvinceCount(){
        IAdStatDAO adStatDAO = DAOFactory.getAdStatDAO();
        JSONObject jsonObject = adStatDAO.selectProvinceClickPercent(null);
        System.out.println(jsonObject.toString());
    }
}
