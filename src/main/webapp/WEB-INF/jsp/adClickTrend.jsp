<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-22
  Time: 下午8:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html style="height: 100%">
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>广告点击趋势</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>

    <style type="text/css">
        #container {
        }
        .div-a{ float:left;width:49%;border:0px solid }
        .div-b{ float:left;width:49%;border:0px solid }
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
<body style="height: 100%; margin: 0;background-color:#0f375f">
<div class="top" style="color: aliceblue">近一小时广告广告趋势</div>
<div id="container" style="height: 90%"></div>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    // Generate data
    <%
    String[] datehourminute = (String[])request.getAttribute("datehourminute");
    double[] clickcount = (double[]) request.getAttribute("clickcount");
    double[] avercount = (double[])request.getAttribute("avercount");
    %>
    var datehourminute = <%=Arrays.toString(datehourminute)%>;
    var clickcount =<%=Arrays.toString(clickcount)%>;
    var avercount = <%=Arrays.toString(avercount)%>;
    var category = datehourminute;
    var lineData = avercount;
    var barData = clickcount;

    // option
    option = {
        backgroundColor: '#0f375f',
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data: ['line', 'bar'],
            textStyle: {
                color: '#ccc'
            }
        },
        //工具框，可以选择
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            data: category,
            axisLine: {
                lineStyle: {
                    color: '#ccc'
                }
            }
        },
        yAxis: {
            splitLine: {show: false},
            axisLine: {
                lineStyle: {
                    color: '#ccc'
                }
            }
        },
        series: [{
            name: 'line',
            type: 'line',
            smooth: true,
            showAllSymbol: true,
            symbol: 'emptyCircle',
            symbolSize: 15,
            data: lineData
        }, {
            name: 'bar',
            type: 'bar',
            barWidth: 10,
            itemStyle: {
                normal: {
                    barBorderRadius: 5,
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#14c8d4'},
                            {offset: 1, color: '#43eec6'}
                        ]
                    )
                }
            },
            data: barData
        }, {
            name: 'line',
            type: 'bar',
            barGap: '-100%',
            barWidth: 10,
            itemStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: 'rgba(20,200,212,0.5)'},
                            {offset: 0.2, color: 'rgba(20,200,212,0.2)'},
                            {offset: 1, color: 'rgba(20,200,212,0)'}
                        ]
                    )
                }
            },
            z: -12,
            data: lineData
        }, {
            name: 'dotted',
            type: 'pictorialBar',
            symbol: 'rect',
            itemStyle: {
                normal: {
                    color: '#0f375f'
                }
            },
            symbolRepeat: true,
            symbolSize: [12, 4],
            symbolMargin: 1,
            z: -10,
            data: lineData
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
</body>
</html>
