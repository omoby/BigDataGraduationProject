<%@ page import="java.util.Map" %>
<%@ page import="java.util.Arrays" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-10
  Time: 上午11:24
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Map<String,String> map  = (Map<String,String>)request.getAttribute("areaTop3ProductMap");
    String[] areaArray = new String[map.size()];
    long[] top1 = new long[map.size()];
    long[] top2 = new long[map.size()];
    long[] top3 = new long[map.size()];
    String[] top1Product = new String[map.size()];
    String[] top2Product = new String[map.size()];
    String[] top3Product = new String[map.size()];
    int i =0;
    for (Map.Entry entry : map.entrySet()){
        String area = (String) entry.getKey();
        areaArray[i] = "'"+area+"'";

        String values = (String) entry.getValue();
        String[] valueSplited = values.split(",");

        for (int j = 0;j < valueSplited.length; j++){
            String[] splited = valueSplited[j].split("\\|");
            if (j == 0){
                top1[i] = Long.valueOf(splited[0]);
                top1Product[i] = splited[1];
            }
            if (j == 1){
                top2[i] = Long.valueOf(splited[0]);
                top2Product[i] = splited[1];
            }
            if (j == 2){
                top3[i] = Long.valueOf(splited[0]);
                top3Product[i] = splited[1];
            }
        }
        i++;

    }

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>各地区热门商品分析</title>
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
<div class="top">各区域热门商品Top3</div>
<div style="margin-top:10px;margin-left: 180px">
    <div id="areaTop3Product" class="div-a" style="width: 1050px;height:540px;"></div>
</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('areaTop3Product'));
    // 指定图表的配置项和数据
    var area = <%=Arrays.toString(areaArray)%>
    var top1 = <%=Arrays.toString(top1)%>
    var top2 = <%=Arrays.toString(top2)%>
    var top3 = <%=Arrays.toString(top3)%>;

    option = {
        color: ['#B22222', '#36a9ce', '#2E8B57'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            data:['top1','top2','top3']
        },
        //工具框，可以选择
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
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
                data: area
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: 'top1',
                type: 'bar',
                barGap: 0,
                label: {
                    normal: {
                        show: false,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                data: top1
            },
            {
                name: 'top2',
                type: 'bar',
                label: {
                    normal: {
                        show: false,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                data: top2
            },
            {
                name: 'top3',
                type: 'bar',
                label: {
                    normal: {
                        show: false,
                        position: 'top',
                        textStyle: {
                            color: 'black'
                        }
                    }
                },
                data: top3
            },
        ]
    };
    myChart.setOption(option);
</script>
</body>
</html>


