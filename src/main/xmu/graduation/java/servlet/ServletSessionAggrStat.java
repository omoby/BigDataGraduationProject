package graduation.java.servlet;

import graduation.java.dao.ISessionAggrStatDAO;
import graduation.java.domain.SessionAggrStat;
import graduation.java.impl.SessionAggrStatDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * FileName: ServletSessionAggrStat
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-18 下午9:37
 * Description:
 * 实现session聚合分析数据的提取，并展示在jsp上
 */
@WebServlet(name="ServletSessionAggrStat",value = "/ServletSessionAggrStat")
public class ServletSessionAggrStat extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws  ServletException,IOException{
        ISessionAggrStatDAO sessionAggrStatDAO = new SessionAggrStatDAOImpl();
        SessionAggrStat sessionAggrStats = new SessionAggrStat();
        Map<String,Object> sessionAggrStat;
        System.out.println("==============================");
        sessionAggrStat = sessionAggrStatDAO.select(sessionAggrStats);
        request.setAttribute("sessionAggrStat", sessionAggrStat);
        request.getRequestDispatcher("/WEB-INF/jsp/sessionAggrstat.jsp").forward(request, response);
    }
}
