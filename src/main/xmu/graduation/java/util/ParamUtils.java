package graduation.java.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import graduation.java.conf.ConfigurationManager;
import graduation.java.constant.Constants;

/**
 * FileName: ParamUtils
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-25 下午8:16
 * Description:参数工具类
 */
public class ParamUtils {
    /**
     * 从命令行中获取任务id
     * @param args 命令行参数
     * @return 任务id
     */

    public static Long getTaskIdFromArgs(String[] args,String taskType){
        boolean local = ConfigurationManager.getBoolean(Constants.SPARK_LOCAL);
        if (local){
            return ConfigurationManager.getLong(taskType);
        }else{
            try{
                if (args != null && args.length > 0){
                    return Long.valueOf(args[0]);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 从JSON对象中提取参数
     * @param jsonObject JSON对象
     * @param field
     * @return 参数
     */
    public static String getParam(JSONObject jsonObject, String field) {
        JSONArray jsonArray = jsonObject.getJSONArray(field);
        if(jsonArray != null && jsonArray.size() > 0) {
            return jsonArray.getString(0);
        }
        return null;
    }

}
