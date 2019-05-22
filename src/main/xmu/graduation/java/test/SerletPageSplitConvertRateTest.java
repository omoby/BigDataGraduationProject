package graduation.java.test;

import graduation.java.dao.IPageSplitConvertRateDAO;
import graduation.java.domain.PageSplitConvertRate;
import graduation.java.factory.DAOFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * FileName: PageSplitConvertRateTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-8 下午4:23
 * Description:
 */
public class SerletPageSplitConvertRateTest {

    public static void main(String[] args) {

        PageSplitConvertRate pageSplitConvertRate = new PageSplitConvertRate();
        IPageSplitConvertRateDAO pageSplitConvertRateDAO = DAOFactory.getPageSplitConvertRateDAO();
        Map<Long,String> map = pageSplitConvertRateDAO.select(pageSplitConvertRate);
        String[] valueArray = null;
        for (Map.Entry entry : map.entrySet()) {
            long taskid = (long) entry.getKey();
            String values = (String) entry.getValue();
            System.out.println("taskid: " + taskid + " values: " + values);
            valueArray = values.split("\\|");
            System.out.println(Arrays.toString(valueArray));
        }

        Map<String,Double> convertRate = new TreeMap<>();
        for (String value : valueArray){
            String[] pageid2rate = value.split("=");
            String pageid = pageid2rate[0];
            double rate = Double.valueOf(pageid2rate[1]);
            convertRate.put(pageid,rate);
        }

        for (Map.Entry entry1 : convertRate.entrySet()){
            System.out.println(entry1.getKey() + " " + entry1.getValue());
        }


    }
}
