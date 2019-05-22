package graduation.java.domain;

/**
 * FileName: AdBlacklist
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-3 下午3:38
 * Description:
 * 用户黑名单实体类
 */
public class AdBlacklist {
    private long userid ;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "AdBlacklist{" +
                "userid=" + userid +
                '}';
    }
}
