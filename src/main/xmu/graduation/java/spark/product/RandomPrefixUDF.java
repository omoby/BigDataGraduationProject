package graduation.java.spark.product;


import org.apache.spark.sql.api.java.UDF2;

import java.util.Random;

/**
 * FileName: RandomPrefixUDF
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-2 上午10:52
 * Description:
 * random_profix()
 * 拼接一个随机前缀
 *
 */
public class RandomPrefixUDF implements UDF2<String,Integer,String> {
    private static final long serialVersionUID =1L;
    @Override
    public String call(String val, Integer integer) throws Exception {
        Random random = new Random();
        int randNum = random.nextInt(integer);
        return randNum+"_"+val;
    }
}
