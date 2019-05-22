package graduation.java.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * FileName: DateUtils
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-25 下午7:09
 * Description:日期时间工具类
 */
public class DateUtils {
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATEKEY_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 判断一个时间是否是在另一个时间之前
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 返回判断结果
     */
    public static boolean before(String time1,String time2){
        try {
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);
            if (dateTime1.before(dateTime2)){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断time1是否在time2的后面
     * @param time1 第一个时间
     * @param time2 第二个时间
     * @return 返回判断结果
     */
    public static boolean after(String time1,String time2){
        try{
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);
            if (dateTime1.after(dateTime2)){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算两个时间的差值（单位为秒）"
     * @param time1 时间1
     * @param time2 时间2
     * @return 返回两个时间的差值，单位为秒
     */
    public static int minus(String time1,String time2){
        try{
            Date dateTime1 = TIME_FORMAT.parse(time1);
            Date dateTime2 = TIME_FORMAT.parse(time2);
            long millisecond = dateTime1.getTime() - dateTime2.getTime();
            return Integer.valueOf(String.valueOf(millisecond));
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取年月日和小时
     * @param datetime 传入的时间格式（yyyy-MM-dd HH:mm:ss）
     * @return 结果时间
     */

    public static String getDateHour(String datetime){
        String date = datetime.split(" ")[0];
        String hourMinuteSecond = datetime.split(" ")[1];
        String hour = hourMinuteSecond.split(":")[0];
        return date+"_"+hour;
    }

    /**
     * 获取丹田日期（yyyy-MM-dd）
     * @return 当天日期
     */
    public static String getTodayDate(){
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 获取昨天的日期（yyyy-MM-dd）
     * @return 昨天的日期
     */
    public static String getYesterdayDate(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR,-1);
        Date date = cal.getTime();
        return DATE_FORMAT.format(date);
    }

    /**
     * 格式化日期（yyyy-MM-dd）
     * @param date DAte对象
     * @return 格式化后的对象
     */
    public static String formatDate(Date date){
        return DATE_FORMAT.format(date);
    }

    /**
     * 格式化时间（yyyy-MM-dd HH:mm:ss）
     * @param date date Date 对象
     * @return 格式化后的时间
     */
    public static  String formatTime(Date date){
        return TIME_FORMAT.format(date);
    }

    /**
     * 转化时间字符串
     * @param time 时间字符串
     * @return Date
     */

    public static Date parseTime(String time){
        try {
            return DATE_FORMAT.parse(time);
        } catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    /**
     * 格式化日期key
     * @param date
     * @return
     */

   public static String formatDateKey(Date date){
        return DATEKEY_FORMAT.format(date);
   }

    /**
     * 格式化日期key
     * @param dateKey
     * @return
     */
   public synchronized static Date parseDateKey(String dateKey){

       try{
           //SimpleDateFormat DATEKEY_FORMAT = new SimpleDateFormat("yyyyMMdd");
           return DATEKEY_FORMAT.parse(dateKey);
       }catch(ParseException e){
           e.printStackTrace();
       }


       return null;
   }

    /**
     * 格式化时间，保留到分钟级别
     * @param date
     * @return
     */

    public static String formatTimeMinute(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        return sdf.format(date);
    }

/*
    public static void main(String[] args){
         String t1 = "1551095261";
         String t2 = "1551095298";
        try {
            Date time1 = TIME_FORMAT.parse(t1);
            Date time2  = TIME_FORMAT.parse(t2);
            System.out.println("Time1: "+t1 + " time1:"+time1);
            System.out.println("Time2: "+t2 +" time2: "+time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        boolean beforeResult = before(t2,t1);
        System.out.println("The result of before:"+beforeResult);
    }
*/

}
