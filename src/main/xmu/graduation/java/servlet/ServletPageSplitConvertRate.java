package graduation.java.servlet;

import graduation.java.dao.IPageSplitConvertRateDAO;
import graduation.java.domain.PageSplitConvertRate;
import graduation.java.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * FileName: SerletPageSplitConvertRate
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-8 下午4:30
 * Description:
 * 页面单跳转化率可视化
 */

@WebServlet(name="ServletPageSplitConvertRate",value = "/ServletPageSplitConvertRate")
public class ServletPageSplitConvertRate  extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws  ServletException,IOException{
        IPageSplitConvertRateDAO pageSplitConvertRateDAO = DAOFactory.getPageSplitConvertRateDAO();
        PageSplitConvertRate pageSplitConvertRate = new PageSplitConvertRate();
        Map<Long,String> mapPageSpliteConvertRate = pageSplitConvertRateDAO.select(pageSplitConvertRate);

        request.setAttribute("mapPageSpliteConvertRate", mapPageSpliteConvertRate);
        request.getRequestDispatcher("/WEB-INF/jsp/pageSplitConvertRate.jsp").forward(request, response);
    }
}
