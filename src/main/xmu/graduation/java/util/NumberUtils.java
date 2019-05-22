package graduation.java.util;

import java.math.BigDecimal;

/**
 * FileName: NumberUtils
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-25 下午8:11
 * Description:数字格式工具类
 */
public class NumberUtils {
    /**
     * 格式化小数
     * @param num 字符串
     * @param scale 四舍五入的位数
     * @return 格式化小数
     */

    public static double formatDouble(double num,int scale){
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
