package graduation.java.servlet;

import graduation.java.dao.GoodsDao;
import graduation.java.domain.Goods;
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
 * FileName: ServletQuaryAllGoods
 * Author:   hadoop
 * Email:    3165845957@qq.com
 * Date:     19-3-19 下午3:30
 * Description:
 * servlet用来实现数据的提取，并发送到前端 jsp进行显示
 */

@WebServlet(name = "ServletQueryAllGoods", value = "/ServletQueryAllGoods")
public class ServletQueryAllGoods extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doGet(request, response);
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            GoodsDao goodsDao = new GoodsDAOImpl();
            List<Goods> goodsList;
            try {
                goodsList = goodsDao.queryAllGoods();
                System.out.println(goodsList.get(0).toString());
                request.setAttribute("goodsList", goodsList);
                request.getRequestDispatcher("/WEB-INF/jsp/goodssales.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
