package graduation.java.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * FileName: FastjsonTest
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-27 下午8:16
 * Description:JSON格式测试类
 */
public class FastjsonTest {
    public static void main(String[] args){
        /**
         * 测试多个JSON格式的数据
         */
        String json = "[{'学生':'张三', '班级':'一班', '年级':'大一', '科目':'高数', '成绩':90}, {'学生':'李四', '班级':'二班', '年级':'大一', '科目':'高数', '成绩':80}]";
        JSONArray jsonArray = JSONArray.parseArray(json);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String name = jsonObject.getString("学生");
        String classs = jsonObject.getString("班级");
        String grade = jsonObject.getString("年级");
        String subject = jsonObject.getString("科目");
        int score = Integer.valueOf(jsonObject.getInteger("成绩"));
        System.out.println("name: "+name +"\n"+"classs: "+classs +"\n"
        +"grade: "+grade+"\n"+"subject: "+subject+"\n" +"score: "+score);


        /**
         * 测试单条数据的JSON格式数据
         */
        System.out.println();
        String json2 = "{'学生':'张三', '班级':'一班', '年级':'大一', '科目':'高数', '成绩':90}";
        JSONObject js = JSONObject.parseObject(json2);
        System.out.println("name: "+ js.getString("学生"));
    }
}
