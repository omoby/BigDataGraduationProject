<%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-10
  Time: 下午9:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html" import="java.sql.*" pageEncoding="utf-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>分页浏览记录</title>
</head>

<body>
<center>
    分页显示记录内容
    <hr>
    <table align="center" border="1" bgcolor="#38B0DE">
        <tr>
            <th>任务id</th>
            <th>回话id</th>
            <th>开始时间</th>
            <th>搜索关键字</th>
            <th>点击品类ids</th>
        </tr>

        <%
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/spark_project";
            String user = "root";
            String password = "123456";
            Class.forName(driver);
            Connection dbconn = null;
            try {
                dbconn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int intPageSize;  //一页显示的记录数
            int intRowCount=0;  //记录总数
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

            Statement stmt = null;
            try {
                stmt = dbconn != null ? dbconn.createStatement() : null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sql = "select * from session_random_extract ";
            ResultSet rs =null;
            try {
                rs = stmt.executeQuery(sql);
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                if (rs.next()){
                    System.out.println(rs.getInt("task_id")+"22222");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                rs.last();  //光标指向查询结果集中最后一条记录
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            try {
                intRowCount = rs.getRow();  //获取记录总数
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
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
            <td><%=rs.getInt("task_id") %> </td>
            <td><%=rs.getString("session_id") %> </td>
            <td><%=rs.getString("start_time") %> </td>
            <td><%=rs.getString("search_keywords") %> </td>
            <td><%=rs.getInt("click_category_ids") %> </td>
        </tr>

        <%
                    rs.next();
                    i++;
                }
            }
        %>

    </table>
    <hr>
    <div align="center">
        第 <%=intPage%> 页   共 <%=intPageCount%> <页></页>
        <%
            if(intPage<intPageCount){
        %>
        <a href="/WEB-INF/jsp/indexs.jsp?page=<%=intPage+1%>">下一页</a>
        <%
            }
            if(intPage>1){%>
        <a href="/WEB-INF/jsp/indexs.jsp?page=<%=intPage-1%>">上一页</a>
        <%
            }
            if (rs !=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null){
                try{
                    stmt.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (dbconn != null){
                try{
                    dbconn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        %>
    </div>

</center>
</body>
</html>
