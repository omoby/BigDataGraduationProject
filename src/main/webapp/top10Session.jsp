<%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-16
  Time: 下午4:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html" import="java.sql.*" pageEncoding="utf-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>点击前10session</title>
    <style>
        .div-a{ float:left;width:100%;border:0px solid }
        .top{width:100%;
            height:30px;
            line-height:30px;
            text-align:center;
            /*border:1px solid #F00;*/
            font-weight:bold;
            font-size: 20px;
        }

    </style>
    <style type="text/css">
        table
        {
            border-collapse: collapse;
            margin: 0 auto;
            text-align: center;
        }
        table td, table th
        {
            border: 1px solid #cad9ea;
            color: #666;
            height: 23px;
        }
        table thead th
        {
            background-color: #CCE8EB;
            /* // width: 100px;*/
        }
        table tr:nth-child(odd)
        {
            background: #fff;
        }
        table tr:nth-child(even)
        {
            background: #F5FAFA;
        }

        .taskid{
            width:15% ;
        }
        .categoryid{
            width: 15%;
        }
        .sessionid{
            width: 30%;
        }

        .clickcount{
            width: 15%;
        }
        .detail{
            width: 15%;
        }
    </style>
</head>

<body>
<center class="top">
    <caption>
        <h3>用户访问session top10</h3>
    </caption>
    <table width="90%" class="table">
        <tr>
            <th class="taskid">任务id</th>
            <th class="categoryid">品类id</th>
            <th class="sessionid">回话id</th>
            <th class="clickcount">点击次数</th>
            <th class="detail">详情</th>
        </tr>

        <%
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/spark_project";
            String user = "root";
            String password = "123456";
            Class.forName(driver);
            Connection dbconn = DriverManager.getConnection(url, user, password);

            int intPageSize;  //一页显示的记录数
            int intRowCount;  //记录总数
            int intPageCount; //总页数
            int intPage;      //待显示页码

            String strPage;
            int i;
            intPageSize = 20; //设置一页显示的记录数
            strPage = request.getParameter("page");  //取得待显示页码
            if(strPage == null){
                intPage = 1;
                /*表明在QueryString中没有page这一个参数，此时显示第一页数据 */
            }else{
                //将字符串转换成整型
                intPage = java.lang.Integer.parseInt(strPage);
                if(intPage<1)
                    intPage = 1;
            }

            Statement stmt = dbconn.createStatement();
            String sql = "select * from top10_session ";
            ResultSet rs = stmt.executeQuery(sql);
            rs.last();  //光标指向查询结果集中最后一条记录
            intRowCount = rs.getRow();  //获取记录总数
            intPageCount = (intRowCount+intPageSize-1) / intPageSize; //记算总页数
            if(intPage>intPageCount)
                intPage = intPageCount; //调整待显示的页码
            if(intPageCount>0){
                rs.absolute((intPage-1) * intPageSize + 1);
                //将记录指针定位到待显示页的
                //显示数据
                i = 0;
                while(i<intPageSize && !rs.isAfterLast()){%>

        <tr>
            <td class="taskid"><%=rs.getInt("task_id") %> </td>
            <td class="categoryid"><%=rs.getString("category_id") %> </td>
            <td class="sessionid"><%=rs.getString("session_id") %> </td>
            <td class="clickcount"><%=rs.getString("click_count") %> </td>
            <td class="detail"><a href="">详情</a></td>
        </tr>

        <%
                    rs.next();
                    i++;
                }
            }
        %>

    </table>
    <br/>
    <div align="center">
        第 <%=intPage%> 页   共 <%=intPageCount%> 页
        <%
            if(intPage<intPageCount){
        %>
        <a href="top10Session.jsp?page=<%=intPage+1%>">下一页</a>
        <%
            }
            if(intPage>1){%>
        <a href="top10Session.jsp?page=<%=intPage-1%>">上一页</a>
        <%
            }
            rs.close();
            stmt.close();
            dbconn.close();
        %>
    </div>

</center>
</body>
</html>
