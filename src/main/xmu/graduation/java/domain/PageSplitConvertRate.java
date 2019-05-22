package graduation.java.domain;

/**
 * FileName: PageSplitConvertRate
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-31 下午3:27
 * Description:
 * 页面切片转化率实体类
 */
public class PageSplitConvertRate {
    private long taskid ;
    private String convertRate;


    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public String getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(String convertRate) {
        this.convertRate = convertRate;
    }

    @Override
    public String toString() {
        return "PageSplitConvertRate{" +
                "taskid=" + taskid +
                ", convertRate='" + convertRate + '\'' +
                '}';
    }
}
