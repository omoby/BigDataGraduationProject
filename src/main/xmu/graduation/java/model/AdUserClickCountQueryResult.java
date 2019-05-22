package graduation.java.model;

/**
 * FileName: AdUserClickCountQueryResult
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-3 上午10:55
 * Description:
 * 用户广告点击量查询结果
 */
public class AdUserClickCountQueryResult {
    private int clickCount;

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "AdUserClickCountQueryResult{" +
                "clickCount=" + clickCount +
                '}';
    }
}
