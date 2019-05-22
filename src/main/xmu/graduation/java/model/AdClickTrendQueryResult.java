package graduation.java.model;

/**
 * FileName: AdClickQueryResult
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 下午8:42
 * Description:
 * 1一小时广告点击查询结果保存类
 */
public class AdClickTrendQueryResult {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AdClickTrendQueryResult{" +
                "count=" + count +
                '}';
    }
}
