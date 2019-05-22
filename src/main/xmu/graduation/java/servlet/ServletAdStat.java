package graduation.java.servlet;

import graduation.java.dao.IAdStatDAO;
import graduation.java.factory.DAOFactory;
import graduation.java.impl.AdStatDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * FileName: ServletAdStat
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-21 上午9:45
 * Description:
 */
@WebServlet(name = "ServletAdStat",value = "/ServletAdStat")
public class ServletAdStat extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IAdStatDAO iAdStatDAO = DAOFactory.getAdStatDAO();
        Map<String,Object> adStatMap = iAdStatDAO.select(null);

        request.setAttribute("adStatMap", adStatMap);
        request.getRequestDispatcher("/WEB-INF/jsp/adStat.jsp").forward(request, response);


    }
}
