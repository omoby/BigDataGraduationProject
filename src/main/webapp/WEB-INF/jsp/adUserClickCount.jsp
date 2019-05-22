<%@ page import="com.alibaba.fastjson.JSONObject" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-21
  Time: 下午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>各广告点击情况</title>
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
<body >
<div class="top">各广告点击情况</div>
<div id="container" style="height: 500px"></div>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById("container"));
    var app = {};
    option = null;
    app.title = '水印 - ECharts 下载统计'

    var adStatProvincePercent = <%=request.getAttribute("adStatProvincePercent")%>
    var builderJson = {
        "all": 100,
        "charts": adStatProvincePercent
    };

    var downloadJson  = <%=request.getAttribute("adid2count")%>;

    var waterMarkText = '广告流实时统计';

    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    canvas.width = canvas.height = 100;
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';
    ctx.globalAlpha = 0.08;
    ctx.font = '20px Microsoft Yahei';
    ctx.translate(50, 50);
    ctx.rotate(-Math.PI / 4);
    ctx.fillText(waterMarkText, 0, 0);

    option = {
        backgroundColor: {
            type: 'pattern',
            image: canvas,
            repeat: 'repeat'
        },

        tooltip: {},
        title: [{
            text: '各省份广告点击占比',
            subtext: '总计 ' + builderJson.all,
            x: '25%',
            textAlign: 'center'
        }, {
            text: '各广告点击占比',
            subtext: '总计 ' + Object.keys(downloadJson).reduce(function (all, key) {
                return all + downloadJson[key];
            }, 0),
            x: '75%',
            textAlign: 'center'
        }],
        grid: [{
            top: 50,
            width: '50%',
            height:'90%',
            bottom: '45%',
            left: 10,
            containLabel: true
        }, {
            top: '55%',
            width: '50%',
            bottom: 0,
            left: 10,
            containLabel: true
        }],
        xAxis: [{
            type: 'value',
            max: builderJson.all,
            splitLine: {
                show: false
            }
        }],
        yAxis: [{
            type: 'category',
            data: Object.keys(builderJson.charts),
            axisLabel: {
                interval: 0,
                rotate: 30
            },
            splitLine: {
                show: false
            }
        }],
        series: [{
            type: 'bar',
            stack: 'chart',
            z: 3,
            label: {
                normal: {
                    position: 'right',
                    show: true
                }
            },
            data: Object.keys(builderJson.charts).map(function (key) {
                return builderJson.charts[key];
            })
        }, {
            type: 'bar',
            stack: 'chart',
            silent: true,
            itemStyle: {
                normal: {
                    color: '#eee'
                }
            },
            data: Object.keys(builderJson.charts).map(function (key) {
                return builderJson.all - builderJson.charts[key];
            })
        }, {
            type: 'pie',
            radius: [0, '70%'],
            center: ['75%', '50%'],
            data: Object.keys(downloadJson).map(function (key) {
                return {
                    name: key.replace('.js', ''),
                    value: downloadJson[key]
                }
            })
        }]
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
</body>
</html>


