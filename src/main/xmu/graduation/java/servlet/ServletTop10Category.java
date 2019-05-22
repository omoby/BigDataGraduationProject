package graduation.java.servlet;

import graduation.java.dao.ITop10CategoryDAO;
import graduation.java.domain.Top10Category;
import graduation.java.factory.DAOFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * FileName: ServletTop10Category
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-28 下午3:55
 * Description:
 * 实现Top10Category可视化
 */
@WebServlet(name="ServletTop10Category",value = "/ServletTop10Category")
public class ServletTop10Category extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws  ServletException,IOException{
        ITop10CategoryDAO top10CategoryDAO = DAOFactory.getTop10CategoryDAO();
        Top10Category top10Category = new Top10Category();
        Map<String,Object> mapTopCategory;
        System.out.println("==============================");
        mapTopCategory = top10CategoryDAO.select(top10Category);
        request.setAttribute("mapTopCategory", mapTopCategory);
        request.getRequestDispatcher("/WEB-INF/jsp/top10Category.jsp").forward(request, response);
    }
}
