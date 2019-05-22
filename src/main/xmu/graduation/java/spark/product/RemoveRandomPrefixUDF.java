package graduation.java.spark.product;

import org.apache.spark.sql.api.java.UDF1;

/**
 * FileName: RemoveRandomPrefixUDF
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-2 上午10:49
 * Description:
 * 去除随机前缀
 */
public class RemoveRandomPrefixUDF  implements UDF1<String,String> {
    private static final long serialVersionUID = 1L;
    @Override
    public String call(String s) throws Exception {
        String[] valSplited = s.split("_");
        return valSplited[1];
    }
}
