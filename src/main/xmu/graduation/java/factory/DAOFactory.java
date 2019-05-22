package graduation.java.factory;

import graduation.java.dao.*;
import graduation.java.impl.*;


/**
 * FileName: DAOFactory
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-2-27 下午5:32
 * Description:DAO工厂类
 */
public class DAOFactory {
    /**
     * 获取任务管理DAO
     * @return
     */
    public static ITaskDAO getTaskDAO(){
        return new TaskDAOImpl();
    }

    /**
     * 获取页面统计时长和步长管理DAO
     * @return
     */

    public static ISessionAggrStatDAO getSessionAggrStatDAO(){
        return new SessionAggrStatDAOImpl();
    }

    /**
     * 获取测试可视化环境Goods管理DAO
     * @return
     */
    public static GoodsDao getGoodsDAO(){
        return new GoodsDAOImpl();
    }

    /**
     * 获取随机抽取session管理的DAO
     * @return
     */
    public static ISessionRandomExtractDAO getSessionRandomExtractDAO(){
        return new SessionRandomExtractDAOImpl();
    }

    /**
     * session明细管理DAO
     * @return
     */
    public static ISessionDetailDAO getSessionDetailDAO(){
        return new SessionDetailDAOImpl();
    }

    /**
     * 获取top10品类管理DAO
     * @return
     */

    public static ITop10CategoryDAO getTop10CategoryDAO(){
        return new Top10CategoryDAOImpl();
    }

    /**
     * 获取top10 session管理DAO
     * @return
     */
    public static ITop10SessionDAO getTop10SessionDAO(){
        return new Top10SessionDAOImpl();
    }

    /**
     *获取页面切片转化率管理DAO
     * @return
     */

    public static IPageSplitConvertRateDAO getPageSplitConvertRateDAO(){
        return  new PageSplitConvertRateDAOImpl();
    }

    /**
     * 获取top3热门商品管理DAO
     * @return
     */
    public static IAreaTop3ProductDAO getAreaTop3ProductDAO(){
        return new AreaTop3ProductDAOImp();
    }


    /**
     * 获取广告用户点击量管理DAO
     * @return
     */
    public static IAdUserClickCountDAO getAdUserClickCountDAO(){
        return new AdUserClickCountDAOImpl();
    }

    /**
     * 获取广告用户黑名单管理DAO
     * @return
     */
    public static IAdBlacklistDAO getAdBlacklistDAO(){
        return new AdBlacklistDAOImpl();
    }

    /**
     * 广告实时点击管理DAO
     * @return
     */
    public static IAdStatDAO getAdStatDAO(){
        return new AdStatDAOImpl();
    }

    /**
     * 各省份热门广告top3 管理DAO
     * @return
     */

    public static IAdProvinceTop3DAO getAdProvinceDAO(){
        return new AdProvinceTop3DAOImpl();
    }

    /**
     * 一小时广告点击趋势管理DAO
     * @return
     */
    public static IAdClickTrendDAO getAdClickTrendADO() {
        return new AdClickTrendDAOImpl();
    }
}
