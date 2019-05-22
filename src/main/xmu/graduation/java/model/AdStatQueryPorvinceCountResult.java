package graduation.java.model;

/**
 * FileName: AdStatQueryPorvinceCountResult
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-22 上午11:37
 * Description:
 */
public class AdStatQueryPorvinceCountResult {
    private String province;
    private long count;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }



    @Override
    public String toString() {
        return "AdStatQueryPorvinceCountResult{" +
                "province='" + province + '\'' +
                ", count=" + count +
                '}';
    }
}
