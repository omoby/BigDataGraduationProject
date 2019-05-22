<%@ page import="java.util.List" %>
<%@ page import="graduation.java.domain.Goods" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-3-19
  Time: 下午4:19
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%

    List<Goods> list = (List<Goods>) request.getAttribute("goodsList");
    List<String> goodsName = new ArrayList<>();
    int[] sales  = new int[list.size()];
    for (int i = 0; i < list.size();i++){
        goodsName.add("\'"+list.get(i).getName()+"\'");
        //goodsName[i] = "\""+list.get(i).getName()+"\"";
        sales[i] = list.get(i).getSales();
        System.out.println("name: "+ list.get(i).getName()+" , sales: "+ list.get(i).getSales());
    }
    System.out.println(goodsName.toString());
    System.out.println(sales.toString());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;">1111111111</div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var name = <%=goodsName.toString()%>;
    window.alert(5555);
    var arr = <%=Arrays.toString(sales)%>;
    // 指定图表的配置项和数据
    var option = {
        title: {
            text: 'ECharts 入门示例'
        },
        tooltip: {},
        legend: {
            data:['销量']
        },
        xAxis: {
            data: ['短袖', '茶叶', '矿泉水', '香飘飘', '王老吉']
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: arr
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>

