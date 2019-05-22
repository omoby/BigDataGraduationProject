package graduation.java.domain;

/**
 * FileName: SessionRandomExtract
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-21 下午7:55
 * Description:
 * 随机抽取session表实体类
 */
public class SessionRandomExtract {
    private long taskid;
    private String sessionid;
    private String startTime;
    private String serachKeyWords;
    private String clickCategoryIds;

    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSerachKeyWords() {
        return serachKeyWords;
    }

    public void setSerachKeyWords(String serachKeyWords) {
        this.serachKeyWords = serachKeyWords;
    }

    public String getClickCategoryIds() {
        return clickCategoryIds;
    }

    public void setClickCategoryIds(String clickCategoryIds) {
        this.clickCategoryIds = clickCategoryIds;
    }

    @Override
    public String toString() {
        return "SessionRandomExtract{" +
                "taskid=" + taskid +
                ", sessionid='" + sessionid + '\'' +
                ", startTime='" + startTime + '\'' +
                ", serachKeyWords='" + serachKeyWords + '\'' +
                ", clickCategoryIds='" + clickCategoryIds + '\'' +
                '}';
    }
}
