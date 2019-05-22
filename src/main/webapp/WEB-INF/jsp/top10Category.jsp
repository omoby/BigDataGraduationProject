<%@ page import="java.util.List" %>
<%@ page import="graduation.java.domain.Goods" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-3-19
  Time: 下午4:19
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Map<String,Object> map  = (Map<String,Object>)request.getAttribute("mapTopCategory");
    long[] categoryids = (long[]) map.get("categoryid");
    long[] clickCounts = (long[]) map.get("clickcount");
    long[] orderCounts = (long[]) map.get("ordercount");
    long[] payCounts = (long[]) map.get("paycount");
    String[] categoryidStr = new String[categoryids.length];
    for (int i = 0 ;i < categoryids.length;i++){
        String data = "\'"+categoryids[i] +"\'";
        categoryidStr[i] = data;
    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>热门品类分析</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <style>
        .div-a{ float:left;width:100%;border:0px solid }
        .top{width:100%;
            height:50px;
            line-height:50px;
            text-align:center;
            /*border:1px solid #F00;*/
            font-weight:bold;
            font-size: 30px;
        }
    </style>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="top">前十热门品类</div>
<div style="margin-top:10px;margin-left: 180px">
    <div id="top10Categorys" class="div-a" style="width: 1000px;height:560px;"></div>
</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('top10Categorys'));
    // 指定图表的配置项和数据
    var categoryids = <%=Arrays.toString(categoryidStr)%>
    var clickCounts = <%=Arrays.toString(clickCounts)%>
    var orderCounts = <%=Arrays.toString(orderCounts)%>
    var payCounts = <%=Arrays.toString(payCounts)%>;

    option = {
        title: {
            text: ''
        },
        color: ['#B22222', '#36a9ce', '#2E8B57'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        legend:{
            data:['点击量','下单量','支付量']
        },
        grid: {
            left: '0%',
            right: '0%',
            bottom: '10%',
            top:'10%',
            containLabel: true
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {show: false},
                data: categoryids
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        //工具框，可以选择
       /* toolbox: {
            feature: {
                saveAsImage: {}
            }
        },*/
        series: [
            {
                name: '点击量',
                type: 'bar',
                barGap: 0,
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                data: clickCounts
            },
            {
                name: '下单量',
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                data: orderCounts
            },
            {
                name: '支付量',
                type: 'bar',
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                data: payCounts
            },
        ]
    };
    myChart.setOption(option);
</script>
</body>
</html>

