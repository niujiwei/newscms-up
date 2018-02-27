<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


    <title>My JSP 'south.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="./js/jquery.cookie.js"></script>

    <script src="./js/jquery.MyDigitClock.js" type="text/javascript"></script>
    <%
        int len = 0;
        ServletContext applications = session.getServletContext();
        if (application.getAttribute("onlineUserList") != null) {
            List UserList = (List) application
                    .getAttribute("onlineUserList");
            len = UserList.size();
        }
    %>

    <script type="text/javascript">
        $(function () {
            $("#clock2").MyDigitClock({
                fontSize: 17,
                width: 250,
                height: 40,
                fontFamily: "Century gothic",
                fontColor: "#A3A3A3",
                fontWeight: "bold",
                bAmPm: true,
                bShowHeartBeat: true
            });
        });
    </script>
</head>

<body style="overflow: hidden;">

<div
        style="height: 30px;border: 1px solid #95B8E7;/* background-image: url('./images/footbgi.png'); */">

    <div id="" style="text-align: center;">
        <span style="padding-left: 30%;font-size: 2rem;">
            DMS 配送管理平台 V2.0
        </span>
        <span style="float: right;padding: 1px 1px;max-height: 3px;">
             <span id="date" style="color: #A3A3A3;font-size: 17px;font-family: 'Century gothic';  font-weight: bold; ">
             </span>
            <span id="clock2"></span>
        </span>



    </div>

    <!-- <div id="demo"	style="font-size: 18px;font-family: 'Century gothic'; font-weight: bold;color: blue;float: left;  margin-top: 2px;  float: left;  margin-left: 600;"></div> -->
</div>
<script>
    var c = 0;
    function timedCount(id) {
        //document.getElementById(id).innerHTML = "用户已登录：" + c + "&nbsp;分钟";
        $.cookie("usertime", c, {
            path: '/',
            expires: 10
        });
        c = c + 1;
        t = setTimeout('timedCount("' + id + '")', 60000);

    }
    window.onload = function () {
        if ($.cookie("usertime") != null) {

            c = parseInt($.cookie("usertime"));
        }
        timedCount('demo');
    };

    var show_day = new Array('星期日', '星期一', '星期二', '星期三', '星期四', '星期五',
        '星期六');
    var time = new Date();
    var year = time.getFullYear();
    var month = time.getMonth();
    var date = time.getDate();
    var day = time.getDay();
    var hour = time.getHours();
    var minutes = time.getMinutes();
    var second = time.getSeconds();
    month < 10 ? month = '0' + month : month;
    month = parseInt(month) + 1;
    hour < 10 ? hour = '0' + hour : hour;
    minutes < 10 ? minutes = '0' + minutes : minutes;
    second < 10 ? second = '0' + second : second;
    var now_time = year
        + '年'
        + month
        + '月'
        + date
        + '日'
        + '<span style="color:red;font-size:15px;  margin-left: 10px;">'
        + show_day[day] + '</span>';
    document.getElementById("date").innerHTML = now_time;
</script>
</body>
</html>
