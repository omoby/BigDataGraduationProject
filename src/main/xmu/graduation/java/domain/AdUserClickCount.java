package graduation.java.domain;

/**
 * FileName: AdUserClickCount
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-3 上午10:39
 * Description:
 * 广告用户点击量实体类
 */
public class AdUserClickCount {

    private  String date;
    private long userid ;
    private long adid;
    private long clickCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getAdid() {
        return adid;
    }

    public void setAdid(long adid) {
        this.adid = adid;
    }

    public long getClickCount() {
        return clickCount;
    }

    public void setClickCount(long clickCount) {
        this.clickCount = clickCount;
    }

    @Override
    public String toString() {
        return "AdUserClickCount{" +
                "date='" + date + '\'' +
                ", userid=" + userid +
                ", adid=" + adid +
                ", clickCount=" + clickCount +
                '}';
    }
}
