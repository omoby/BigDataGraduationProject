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
import java.util.List;

/**
 * FileName: ServletSessionRandomExtract
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-10 下午8:36
 * Description:
 * session随机抽取servlet类
 */
@WebServlet(name="ServletSessionRandomExtract",value = "/ServletSessionRandomExtract")
public class ServletSessionRandomExtract extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ISessionRandomExtractDAO sessionRandomExtractDAO = DAOFactory.getSessionRandomExtractDAO();
            List<SessionRandomExtract> sessionRandomExtracts = sessionRandomExtractDAO.select(new SessionRandomExtract());
            request.setAttribute("sessionRandomExtracts", sessionRandomExtracts);
            request.getRequestDispatcher("/sessionRandomExtract.jsp").forward(request, response);
        }
}
