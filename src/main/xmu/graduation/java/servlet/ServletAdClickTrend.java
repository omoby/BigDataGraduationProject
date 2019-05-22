package graduation.java.servlet;

import graduation.java.dao.IAdClickTrendDAO;
import graduation.java.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;

/**
 * FileName: ServletAdClickTrend
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-22 下午8:09
 * Description:
 */
@WebServlet(name="ServletAdClickTrend",value = "/ServletAdClickTrend")
public class ServletAdClickTrend extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IAdClickTrendDAO adClickTrendDAO = DAOFactory.getAdClickTrendADO();
        Map<String,Object> resultMap = adClickTrendDAO.selectLast60min();
        request.setAttribute("datehourminute",resultMap.get("datehoutminut"));
        request.setAttribute("clickcount",resultMap.get("clickcount"));
        request.setAttribute("avercount",resultMap.get("avercount"));

        request.getRequestDispatcher("/WEB-INF/jsp/adClickTrend.jsp").forward(request, response);
    }
}
