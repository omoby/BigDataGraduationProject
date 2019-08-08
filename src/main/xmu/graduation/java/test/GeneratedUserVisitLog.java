package graduation.java.test;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * FileName: GeneratedUserVisitLog
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-8-5 上午12:00
 * Description:
 * 用户活跃度模块数据生成程序
 */
public class GeneratedUserVisitLog {
    public static void main(String[] args){
        //用户基本信息Json数据文件
        String userBaseLogName = "user_base_info.json";
        //用户访问日志JSON数据文件
        String userActionLogName = "user_action_log.json";
        // 文件保存的路径
        String logPath = "/home/hadoop/IdeaProjects/BigDataGraduationProject/log/";
        //生成用户基本信息的方法
        createJsonFileInfoLog(logPath,userBaseLogName);
        //生成用户访问日志数据方法
        createJsonFileActionLog(logPath,userActionLogName);

    }
    /**
     * 获取现在时间
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 生成用户基本信息的方法
     * @param logPath 文件保存的路径
     * @param userBaseLogName 文件名
     */
    public static void createJsonFileInfoLog(String logPath,String userBaseLogName){
        //文件保存的完整路径
        String baseInfoFile = logPath+userBaseLogName;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(baseInfoFile);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }

            file.createNewFile();

            int userId = -1; //用户ID
            String userName = null; //用户名
            String registTime = null; //注册时间
            String logString= null; //日志数据
            StringBuffer buffer = new StringBuffer();

            //生成用户基本信息
            for(int i = 0 ; i < 100; i++){
                JsonObject object = new JsonObject();
                userId = (int)(Math.random()*99+1);
                userName = "userName"+userId;
                registTime = getStringDate();
                object.addProperty("userId",userId);
                object.addProperty("userName",userName);
                object.addProperty("registTime",registTime);
                buffer.append(object.toString()+"\n");
            }

            logString = buffer.toString();
            logString = logString.substring(0,logString.length()-1);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(logString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户访问日志生成方法
     * @param logPath 文件保存路径
     * @param userActionLogName 文件名
     */
    public static void createJsonFileActionLog(String logPath,String userActionLogName){
        //完整的文件路径
        String actionLogFile = logPath + userActionLogName;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            File file = new File(actionLogFile);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            int logId = -1; //日志ID
            int userId = -1; //用户ID
            String actionTime = null; //访问时间
            int actionType = -1; //访问类型
            double purchaseMoney = -1; //消费金额
            StringBuffer buffer = new StringBuffer();
            String logString = null;

            //生成日志数据
            for(int i = 0 ; i < 10000; i++){
                JsonObject object = new JsonObject();
                logId = (int)(Math.random()*10000);
                userId = (int)(Math.random()*99+1);
                actionTime = getStringDate();
                actionType = (int)(Math.random()*2);
                purchaseMoney  = ((int)((Math.random()*1000)*100)) / 100.0;
                object.addProperty("logId",logId);
                object.addProperty("userId",userId);
                object.addProperty("actionTime",actionTime);
                object.addProperty("actionType",actionType);
                object.addProperty("purchaseMoney",purchaseMoney);
                buffer.append(object.toString()+"\n");
            }
            logString = buffer.toString();
            logString = logString.substring(0,logString.length()-1);

            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(logString);
            write.flush();
            write.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
