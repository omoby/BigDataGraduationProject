package graduation.java.servlet;

import graduation.java.dao.ISessionRandomExtractDAO;
import graduation.java.domain.SessionRandomExtract;
import graduation.java.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FileName: ServletTop10Session
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-16 下午4:56
 * Description:
 */
@WebServlet(name="ServletTop10Session",value = "/ServletTop10Session")
public class ServletTop10Session extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/top10Session.jsp").forward(request, response);
    }
}
