package graduation.java.domain;

/**
 * FileName: SessionDetail
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午9:31
 * Description:
 * session_detail表实体类
 */
public class SessionDetail {
    private long taskid;
    private long userid;
    private String sessionid;
    private long pageid;
    private String actionTime;
    private String seachKeyWord;
    private long clickCategoryId;
    private long clickProductId;
    private String orderCategoryIds;
    private String orderProductIds;
    private String payCategoryIds;
    private String payProductIds;

    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public long getPageid() {
        return pageid;
    }

    public void setPageid(long pageid) {
        this.pageid = pageid;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getSeachKeyWord() {
        return seachKeyWord;
    }

    public void setSeachKeyWord(String seachKeyWord) {
        this.seachKeyWord = seachKeyWord;
    }

    public long getClickCategoryId() {
        return clickCategoryId;
    }

    public void setClickCategoryId(long clickCategoryId) {
        this.clickCategoryId = clickCategoryId;
    }

    public long getClickProductId() {
        return clickProductId;
    }

    public void setClickProductId(long clickProductId) {
        this.clickProductId = clickProductId;
    }

    public String getOrderCategoryIds() {
        return orderCategoryIds;
    }

    public void setOrderCategoryIds(String orderCategoryIds) {
        this.orderCategoryIds = orderCategoryIds;
    }

    public String getOrderProductIds() {
        return orderProductIds;
    }

    public void setOrderProductIds(String orderProductIds) {
        this.orderProductIds = orderProductIds;
    }

    public String getPayCategoryIds() {
        return payCategoryIds;
    }

    public void setPayCategoryIds(String payCategoryIds) {
        this.payCategoryIds = payCategoryIds;
    }

    public String getPayProductIds() {
        return payProductIds;
    }

    public void setPayProductIds(String payProductIds) {
        this.payProductIds = payProductIds;
    }

    @Override
    public String toString() {
        return "SessionDetail{" +
                "taskid=" + taskid +
                ", userid=" + userid +
                ", sessionid='" + sessionid + '\'' +
                ", pageid=" + pageid +
                ", actionTime='" + actionTime + '\'' +
                ", seachKeyWord='" + seachKeyWord + '\'' +
                ", clickCategoryId=" + clickCategoryId +
                ", clickProductId=" + clickProductId +
                ", orderCategoryIds='" + orderCategoryIds + '\'' +
                ", orderProductIds='" + orderProductIds + '\'' +
                ", payCategoryIds='" + payCategoryIds + '\'' +
                ", payProductIds='" + payProductIds + '\'' +
                '}';
    }
}
