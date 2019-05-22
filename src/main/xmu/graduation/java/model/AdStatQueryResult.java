package graduation.java.model;

/**
 * FileName: AdStatQueryResult
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 上午10:39
 * Description:
 * 广告实时点击查询结果
 */
public class AdStatQueryResult {
    private int count;
    private int sum;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public AdStatQueryResult(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "AdStatQueryResult{" +
                "count=" + count +
                ", sum=" + sum +
                '}';
    }
}
