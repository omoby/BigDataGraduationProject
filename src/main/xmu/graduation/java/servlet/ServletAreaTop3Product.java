package graduation.java.servlet;

import graduation.java.dao.GoodsDao;
import graduation.java.dao.IAreaTop3ProductDAO;
import graduation.java.domain.AreaTop3Product;
import graduation.java.domain.Goods;
import graduation.java.factory.DAOFactory;
import graduation.java.impl.GoodsDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * FileName: ServletAreaTop3Product
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-4-10 上午11:20
 * Description:
 * 各地区热门商品top3可视化
 */
@WebServlet(name="ServletAreaTop3Product",value = "/ServletAreaTop3Product")
public class ServletAreaTop3Product extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IAreaTop3ProductDAO areaTop3ProductDAO = DAOFactory.getAreaTop3ProductDAO();
        Map<String,String> areaTop3ProductMap = areaTop3ProductDAO.select(new AreaTop3Product());

        request.setAttribute("areaTop3ProductMap", areaTop3ProductMap);
        request.getRequestDispatcher("/WEB-INF/jsp/areTop3Product.jsp").forward(request, response);


    }
}
