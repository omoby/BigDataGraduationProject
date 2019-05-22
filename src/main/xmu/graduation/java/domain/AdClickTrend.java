package graduation.java.domain;

/**
 * FileName: AdClickTrend
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 下午8:33
 * Description:
 * 一小时内广告点击趋势实体类
 */
public class AdClickTrend {

    private String date;
    private String hour;
    private String minute;
    private long adid;
    private long clickCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
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
        return "AdClickTrend{" +
                "date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                ", adid=" + adid +
                ", clickCount=" + clickCount +
                '}';
    }
}
