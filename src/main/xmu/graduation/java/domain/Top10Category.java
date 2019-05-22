package graduation.java.domain;

/**
 * FileName: Top10Category
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-23 上午10:34
 * Description:
 *
 * top10品类实例
 */
public class Top10Category {
    private long taskid;
    private long categoryid;
    private long clickCount;
    private long orderCount;
    private long payCount;


    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }

    public long getPayCount() {
        return payCount;
    }

    public void setPayCount(long payCount) {
        this.payCount = payCount;
    }

    @Override
    public String toString() {
        return "Top10Category{" +
                "taskid=" + taskid +
                ", categoryid=" + categoryid +
                ", clickCount=" + clickCount +
                ", orderCount=" + orderCount +
                ", payCount=" + payCount +
                '}';
    }
}
