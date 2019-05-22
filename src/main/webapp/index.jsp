<%@ page language="java" contentType="text/html"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

    <title>用户行为分析系统</title>
    <style type="text/css">
        .top{width:100%;
            height:60px;
            line-height:60px;
            text-align:center;
            /*border:1px solid #F00;*/
            font-weight:bold;
            font-size: 30px;
        }
        *{
            margin: 0;
            padding: 0;
        }
        .main{
            width: 60%;
            height: 90%;
            position: absolute;
            margin-left: 20%;
        }
        .quarter-div{
            width: 50%;
            height: 50%;
            float: left;
        }
        .blue{
            /*background-color: #5BC0DE;*/
            background-image:url(./img/session.jpg);
            background-repeat: no-repeat;
            background-size: 100% 290px;


        }
        .green{
            /*background-color: #5CB85C;*/
            background-image:url(./img/page.jpg);
            background-repeat: no-repeat;
            background-size: 100% 290px;

        }
        .orange{
            /*background-color: #F0AD4E;*/
            background-image:url(./img/adp.jpeg);
            background-repeat: no-repeat;
            background-size: 100% 290px;

        }
        .yellow{
            /*background-color: #FFC706;*/
            background-image:url(./img/product.jpeg);
            background-repeat: no-repeat;
            background-size: 100% 290px;

        }
    </style>
</head>

<body>
<div class="top">用户行为分析系统</div>
<div class="main">
    <div class="quarter-div blue" ></div>
    <div class="quarter-div green"></div>
    <div class="quarter-div orange"></div>
    <div class="quarter-div yellow"></div>
</div>
</body>
</html>
