package graduation.java.servlet;

import com.alibaba.fastjson.JSONObject;
import graduation.java.dao.IAdStatDAO;
import graduation.java.dao.IAdUserClickCountDAO;
import graduation.java.factory.DAOFactory;
import graduation.java.test.FastjsonTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FileName: ServletAdUserClickCount
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-21 下午10:52
 * Description:
 */
@WebServlet(name="ServletAdUserClickCount",value = "/ServletAdUserClickCount")
public class ServletAdUserClickCount extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IAdUserClickCountDAO adUserClickCountDAO = DAOFactory.getAdUserClickCountDAO();
        JSONObject adid2count = adUserClickCountDAO.selectAdid2Count(null);
        request.setAttribute("adid2count", adid2count);

        IAdStatDAO adStatDAO = DAOFactory.getAdStatDAO();
        JSONObject adStatProvincePercent = adStatDAO.selectProvinceClickPercent(null);
        //System.out.println("Servlet: "+adStatProvincePercent.toString());
        request.setAttribute("adStatProvincePercent", adStatProvincePercent);
        request.getRequestDispatcher("/WEB-INF/jsp/adUserClickCount.jsp").forward(request, response);
    }
}
