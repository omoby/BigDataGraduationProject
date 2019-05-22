package graduation.java.servlet;

import graduation.java.dao.IAdProvinceTop3DAO;
import graduation.java.dao.IAdStatDAO;
import graduation.java.dao.ISessionRandomExtractDAO;
import graduation.java.domain.AdProvinceTop3;
import graduation.java.domain.SessionRandomExtract;
import graduation.java.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * FileName: ServletAdProvinceTop3
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-21 下午5:10
 * Description:
 */
@WebServlet(name="ServletAdProvinceTop3",value = "/ServletAdProvinceTop3")
public class ServletAdProvinceTop3 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IAdProvinceTop3DAO adProvinceTop3DAO = DAOFactory.getAdProvinceDAO();
        List<AdProvinceTop3> adProvinceTop3s = adProvinceTop3DAO.select(null);
        request.setAttribute("adProvinceTop3s", adProvinceTop3s);
        IAdStatDAO adStatDAO = DAOFactory.getAdStatDAO();
        Map<String,Integer> adStatProvinceCount = adStatDAO.selectProvinceClick(null);
        request.setAttribute("adStatProvinceCount",adStatProvinceCount);
        request.getRequestDispatcher("/WEB-INF/jsp/adProvinceTop3.jsp").forward(request, response);
    }
}
