<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="graduation.java.domain.AdProvinceTop3" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="org.apache.commons.math3.analysis.function.Max" %><%--
  Created by IntelliJ IDEA.
  User: hadoop
  Date: 19-4-21
  Time: 下午5:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Map<String,Integer> map  = (HashMap<String,Integer>)request.getAttribute("adStatProvinceCount");
    List<AdProvinceTop3> adProvinceTop3s = (List<AdProvinceTop3>) request.getAttribute("adProvinceTop3s");
    String[] province = new String[map.size()];
    String[] eProvince = new String[map.size()];
    long[] provinceCount = new long[map.size()];
    int index = 0;
    for (Map.Entry entry : map.entrySet()){
        province[index] = (String)entry.getKey();
        eProvince[index] = "'"+province[index]+"'";
        provinceCount[index] = (int)entry.getValue();
        index++;
    }
    long[] top1 = new long[map.size()];
    long[] top2 = new long[map.size()];
    long[] top3 = new long[map.size()];
    double[] percent = new double[map.size()];
    int mk = 0;
    int matchTime = 0;
    long maxClick = 0;

    for (int i = 0; i < province.length;i++){

            for (AdProvinceTop3 adProvinceTop3 : adProvinceTop3s){
                boolean match = province[i].equals(adProvinceTop3.getProvince());
                if (match){
                    System.out.println(province[i] + " "+ adProvinceTop3.getProvince()+ " "+ match + " "+i);
                    mk = matchTime % 3;
                    if (match && mk == 0){

                        top1[i] = adProvinceTop3.getClickCount();
                    }
                    if (match&& mk == 1){

                        top2[i] = adProvinceTop3.getClickCount();
                    }
                    if (match && mk == 2){
                        top3[i] = adProvinceTop3.getClickCount();
                        long totalCount = top1[i] + top2[i]+top3[i];

                        percent[i] = (double)((long)((double)(totalCount)/(double)provinceCount[i] * 10000))/100;
                        long maxCount = top1[i];
                        if (maxCount < top2[i]){
                            maxCount = top2[i];
                        }
                        if (maxCount < top3[i]){
                            maxCount = top3[i];
                        }
                        if (maxCount > maxClick){
                            maxClick = maxCount;
                        }
                    }
                    matchTime++;
                }
        }

    }
    System.out.println(Arrays.toString(top1));
    System.out.println(Arrays.toString(top2));
    System.out.println(Arrays.toString(top3));
    System.out.println(Arrays.toString(percent));
    System.out.println(Arrays.toString(province));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html style="height: 100%">
<head>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>各省份热门广告</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts-all-3.js"></script>
    <style>
        .div-a{ float:left;width:59%;border:0px solid }
        .div-b{ float:left;width:35%;border:0px solid }
        .top{width:100%;
            height:50px;
            line-height:50px;
            text-align:center;
            /*border:1px solid #F00;*/
            font-weight:bold;
            font-size: 30px;
        }
    </style>
    <style type="text/css">
        table
        {
            border-collapse: collapse;
            margin: 0 auto;
            text-align: center;
        }
        table td, table th
        {
            border: 1px solid #cad9ea;
            color: #666;
            height: 23px;
        }
        table thead th
        {
            background-color: #CCE8EB;
            /* // width: 100px;*/
        }
        table tr:nth-child(odd)
        {
            background: #fff;
        }
        table tr:nth-child(even)
        {
            background: #F5FAFA;
        }

        .province{
            width:25% ;
        }
        .adid{
            width: 25%;
        }


        .clickcount{
            width: 40%;
        }

    </style>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="top">各省份热门广告</div>
<div style="margin-top:30px;margin-left: 40px">
    <div id="container" class="div-a" style="width:800px;height:480px;color: aqua"></div>
    <div id="etable" class="div-b" style="margin-top:0px;width:400px;height:480px;margin-left: 50px;color: brown">
        <table width="90%" class="table">
            <tr>
                <th class="province">省份</th>
                <th class="adid">广告id</th>
                <th class="clickcount">点击次数</th>
            </tr>

            <%
                int i =0;
                int num = 0;
                for (;i < province.length;) {
                    for (AdProvinceTop3 adProvinceTop3 : adProvinceTop3s) {
                        boolean match = province[i].equals(adProvinceTop3.getProvince());
                        if (match){

            %>

            <tr>
                <td class="province"><%=adProvinceTop3.getProvince() %> </td>
                <td class="adid"><%=adProvinceTop3.getAdid() %> </td>
                <td class="clickcount"><%=adProvinceTop3.getClickCount() %> </td>

            </tr>
            <%
                        }

                    }
                    i++;
                }
            %>

        </table>
    </div>
</div>

<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    var eProvince = <%=Arrays.toString(eProvince)%>;
    var top1 = <%=Arrays.toString(top1)%>;
    var top2 = <%=Arrays.toString(top2)%>;
    var top3 = <%=Arrays.toString(top3)%>;
    var percent = <%=Arrays.toString(percent)%>;
    var maxClick = <%=(long)maxClick*1.2%>
    /*option = null;
    app.title = '';*/

    option = {
        title: {
        text: '         折柱混合'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
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
        legend: {
            data:['top1','top2','top3','百分比']
        },
        xAxis: [
            {
                type: 'category',
                data: eProvince,
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '点击量',
                min: 0,
                max: maxClick,
                interval: <%=(int)maxClick/10%>,
                axisLabel: {
                    formatter: '{value} 次'
                }
            },
            {
                type: 'value',
                name: '百分比',
                min: 0,
                max: 100,
                interval: 10,
                axisLabel: {
                    formatter: '{value} %'
                }
            }
        ],
        series: [
            {
                name:'top1',
                type:'bar',
                data:top1
            },
            {
                name:'top2',
                type:'bar',
                data:top2
            },
            {
                name:'top3',
                type:'bar',
                data:top3
            },
            {
                name:'百分比',
                type:'line',
                yAxisIndex: 1,
                data:percent
            }
        ]
    };
    ;
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>
</body>
</html>
