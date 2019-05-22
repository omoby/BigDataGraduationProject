<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-21
  Time: 上午9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Map<String,Object> map  = (HashMap<String,Object>)request.getAttribute("adStatMap");
    String[] city = (String[])map.get("city");
    String[] eCity = new String[city.length];
    for (int index = 0; index < city.length;index++){
        eCity[index] = "'"+city[index]+"'";
    }
    int adIdNum = ((int[])map.get(city[0])).length;
    String[] adId2Str = new String[adIdNum];
    for (int j = 0; j < adIdNum;j++){
        adId2Str[j] = "'广告"+j+"'";
    }


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>广告流点击状态统计</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <style>
        .div-a{ float:left;width:100%;border:0px solid }
        .top{width:100%;
            height:30px;
            line-height:30px;
            text-align:center;
            /*border:1px solid #F00;*/
            font-weight:bold;
            font-size: 30px;
        }
    </style>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="top">广告流点击状态</div>
<div style="margin-top:10px;margin-left: 160px">
    <div id="adStat" class="div-a" style="width: 1000px;height:530px;"></div>
</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('adStat'));
    // 指定图表的配置项和数据
    var eCity =<%=Arrays.toString(eCity)%>
    var adId2Str =<%=Arrays.toString(adId2Str)%>
        option = {
            title: {
                text: '\n折线图堆叠'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:eCity,
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                show : true,
                orient:'vertical',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: true},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: adId2Str
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: 'Changsha',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Changsha"))%>
                },
                {
                    name: 'Jingzhou',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Jingzhou"))%>
                },
                {
                    name: 'Luoyang',
                    type: 'line',
                    stack: '总量',
                    data:<%=Arrays.toString((int[])map.get("Luoyang"))%>
                },
                {
                    name: 'Nanjing',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Nanjing"))%>
                },
                {
                    name: 'Shijiazhuang',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Shijiazhuang"))%>
                },
                {
                    name: 'Suzhou',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Suzhou"))%>
                },
                {
                    name: 'Tangshan',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Tangshan"))%>
                },
                {
                    name: 'Wuhan',
                    type: 'line',
                    stack: '总量',
                    data:<%=Arrays.toString((int[])map.get("Wuhan"))%>
                },
                {
                    name: 'Xiangtan',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Xiangtan"))%>
                },
                {
                    name: 'Zhengzhou',
                    type: 'line',
                    stack: '总量',
                    data: <%=Arrays.toString((int[])map.get("Zhengzhou"))%>
                }
            ]
        };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>

</body>
</html>
