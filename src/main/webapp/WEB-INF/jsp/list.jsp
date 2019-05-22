<%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-21
  Time: 下午10:55
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户广告点击分析</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <style>
        .div-a{ float:left;width:49%;border:0px solid }
        .div-b{ float:left;width:49%;border:0px solid }
        .top{width:100%;
            height:100px;
            line-height:100px;
            text-align:center;
            /*border:1px solid #F00;*/
            font-weight:bold;
            font-size: 30px;
        }
    </style>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="top">用户广告点击分析</div>
<div style="margin-top:50px;margin-left: 40px">
    <div id="container" class="div-a" style="width: 600px;height:400px;"></div>
</div>

<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    app.title = '水印 - ECharts 下载统计'

    var builderJson = {
        "all": 10887,
        "charts": {
            "map": 3237,
            "lines": 2164,
            "bar": 7561,
            "line": 7778,
            "pie": 7355,
            "scatter": 2405,
            "candlestick": 1842,
            "radar": 2090,
            "heatmap": 1762,
            "treemap": 1593,
            "graph": 2060,
            "boxplot": 1537,
            "parallel": 1908,
            "gauge": 2107,
            "funnel": 1692,
            "sankey": 1568
        },
        "ie": 9743
    };

    var downloadJson  = <%=request.getAttribute("adid2count")%>;

    var waterMarkText = 'ECHARTS';

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
            text: '在线构建',
            subtext: '总计 ' + builderJson.all,
            x: '25%',
            textAlign: 'center'
        }, {
            text: '各版本下载',
            subtext: '总计 ' + Object.keys(downloadJson).reduce(function (all, key) {
                return all + downloadJson[key];
            }, 0),
            x: '75%',
            textAlign: 'center'
        }],
        grid: [{
            top: 50,
            width: '50%',
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
            radius: [0, '30%'],
            center: ['75%', '25%'],
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

