
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="scala.collection.SortedMap" %>
<%@ page import="java.util.TreeMap" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-3-19
  Time: 下午4:19
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Map<Long,String> map  = (HashMap<Long,String>)request.getAttribute("mapPageSpliteConvertRate");
    long taskid = 0l;
    String result = null;
    for (Map.Entry entry : map.entrySet()){
        taskid = (long) entry.getKey();
        result = (String) entry.getValue();
    }

    Map<String,Double> convertRate = new TreeMap<>();
    String[] resultArray = result.split("\\|");
    for (String value : resultArray){
        String[] valueArray = value.split("=");
        String pageid = valueArray[0];
        double rate = Double.valueOf(valueArray[1]) *100;
        convertRate.put(pageid,rate);
    }

    String[] pageidArrays = new String[convertRate.size()];
    double[] rateArrays = new double[convertRate.size()];
    int i =0;
    for (Map.Entry entrySet : convertRate.entrySet()){
        pageidArrays[i] = "'"+entrySet.getKey()+"'";
        rateArrays[i] = (double)entrySet.getValue();
        i++;
    }


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>页面单跳转化率分析</title>
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
<div class="top">页面单跳转化率</div>
<div style="margin-top:-10px;margin-left: 180px">
    <div id="pageSpliteConvertRate" class="div-a" style="width: 1000px;height:580px;"></div>
</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('pageSpliteConvertRate'));
    // 指定图表的配置项和数据
    var taskid = <%=taskid%>;
    var task = "                          任务"+taskid;
    var pageid = <%=Arrays.toString(pageidArrays)%>;
    var rate = <%=Arrays.toString(rateArrays)%>;
    var option = {
        // 标题
        title: {
            text: '页面单跳转化率',
            subtext: '数据来源：模拟数据'
        },
        tooltip: {
            trigger: 'axis'
        },
        //图例名
        legend: {
            data:['跳转率']
        },
        grid: {
            left: '3%',   //图表距边框的距离
            right: '4%',
            bottom: '3%',
            containLabel: true
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
        //x轴信息样式
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: pageid,
            //坐标轴颜色
            axisLine:{
                lineStyle:{
                    color:'red'
                }
            },
            //x轴文字旋转
            axisLabel:{
                rotate:30,
                interval:0
            },
        },

        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} %'
                }
            }
        ],
        series: [
            //实线
            {
                name:'跳转率',
                type:'line',
                symbol:'circle',
                symbolSize:4,
                itemStyle:{
                    normal:{
                        color:'red',
                        borderColor:'red'  //拐点边框颜色
                    }
                },
                data:rate
            }

        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>

</body>
</html>

