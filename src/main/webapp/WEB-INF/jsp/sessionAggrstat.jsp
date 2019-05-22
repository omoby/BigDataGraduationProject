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
    Map<String,Object> map  = (Map<String,Object>)request.getAttribute("sessionAggrStat");
    long taskid = (long) map.get("taskid");
    long sessionCount = (long) map.get("sessionCount");
    double[] lengthArrays = (double[])map.get("visitLength");
    double[] stepArrays = (double[])map.get("visitStep");
    String[] stepname = {"1-3","4-6","7-9","10-30","30-60","60"};
    JSONObject step = new JSONObject();
    int eachStep = 0;
    String str = "";
    for (int j = 0; j < stepArrays.length;j++){
        eachStep = (int)(sessionCount * stepArrays[j]);
        str += "{value:"+eachStep+", "+ "name:\'"+stepname[j]+"\'},";
        step.put(stepname[j],eachStep);
    }
    str = str.substring(0,str.length()-1);
   /* System.out.println("====="+step.toJSONString());
    System.out.println("String； "+str);


    System.out.println("taskId: "+ taskid);
    System.out.println("sessionCount: "+ sessionCount);
    System.out.print("visitLength: ");
    for (int i = 0; i < lengthArrays.length; i++){
        System.out.print(lengthArrays[i] + "\t");
    }
    System.out.println();
    System.out.print("visitStep: ");
    for (int i = 0; i < stepArrays.length; i++){
        System.out.print(stepArrays[i] + "\t");
    }*/

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户访问Session聚合分析</title>
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
<div class="top">用户访问Session聚合分析</div>
<div style="margin-top:50px;margin-left: 40px">
    <div id="visitlength" class="div-a" style="width: 600px;height:400px;"></div>
    <div id="visitstep" class="div-b" style="width: 600px;height:400px;margin-left: 70px;"></div>
</div>

<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('visitlength'));
    // 指定图表的配置项和数据
    var taskid = <%=taskid%>;
    var task = "            任务"+taskid;
    var visitLength = <%=Arrays.toString(lengthArrays)%>;
    var visitStep = <%=Arrays.toString(stepArrays)%>;
    var length = ['1s-3s','4s-6s','7s-9s','10s-30s','30s-60s','1m-3m','3m-10m','10m-30','30m'];
    var step = ['1-3','4-6','7-9','10-30','30-60','60'];
    var option = {
        title: {
            text: '用户访问时长统计',
            subtext: task
        },
        tooltip: {},
        legend: {
            data:['访问量']
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
        xAxis: {
            data: length,
            axisLabel : {
                show:true,
                interval: 0//,    // {number}
                //rotate: 45,
                // margin: 8

            }

        },
        yAxis: {},
        series: [{
            name: '访问量',
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
            data: visitLength
        }]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('visitstep'));
    // 指定图表的配置项和数据

    var stepJson =[<%=str%>];
    var task = "任务"+taskid;
    var step = ['1-3','4-6','7-9','10-30','30-60','60'];
    option = {
        title : {
            text: '页面访问步长比例',
            subtext: task,
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: step
        },
        //工具框，可以选择
        toolbox: {
            feature: {
                dataView: {show: true, readOnly: false},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:stepJson,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>

