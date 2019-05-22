package graduation.java.domain;

/**
 * FileName: AdProvinceTop3
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-4 下午4:32
 * Description:
 * 各省份广告点击top3热门广告
 */
public class AdProvinceTop3 {

    private String date;
    private String province;
    private long adid;
    private long clickCount;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
        return "AdProvinceTop3{" +
                "date='" + date + '\'' +
                ", province='" + province + '\'' +
                ", adid=" + adid +
                ", clickCount=" + clickCount +
                '}';
    }
}
