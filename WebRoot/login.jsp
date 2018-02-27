<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <style type="text/css">
        @charset "UTF-8";

        .ng-hide {
            display: none !important;
        }

        .ng\:form {
            display: block;
        }

        .ng-animate-block-transitions {
            transition: 0s all !important;
            -webkit-transition: 0s all !important;
        }

        .ng-hide-add-active,
        .ng-hide-remove {
            display: block !important;
        }
    </style>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <link type="text/css" rel="stylesheet" href="./css/newlogin.css"/>
    <link type="text/css" rel="stylesheet" href="./css/slide-unlock.css"/>
    <title class="ng-binding">DMS 配送管理平台 </title>
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.slideunlock.js"></script>


</head>

<body>
<div class="container">
    <!-- ngIf: !noGlobalWrap -->
    <header class="account-header ng-scope">
        <a href="http://www.56dms.com"></a>
        <div style="font-size: -webkit-xxx-large;font-family: FangSong_GB2312;
				     font-style: italic;text-align: inherit;vertical-align: middle;">

        </div>
    </header>
    <!-- end ngIf: !noGlobalWrap -->

    <!-- ngView:  -->
    <div class="account-container ng-scope">
        <div class="login ng-scope">
            <img class="account-illustration" src="images/login_left.jpg">
            <!-- ngInclude:  -->
            <div class="account-main aside ng-scope">
                <div class="account-line ng-scope">
                    <h3 class="account-title line-left">登录</h3>
                    <!-- ngIf: normalMode -->

                    <!-- end ngIf: normalMode -->
                    <!-- ngIf: !normalMode -->
                </div>
                <!-- ngInclude:  -->
                <div class="login-form ng-scope">
                    <form name="form1" method="post" action="login.do"
                          class="account-form ng-scope ng-pristine ng-invalid ng-invalid-required">
                        <div class="form-group error-group ng-hide">
                            <div class="account-errtip">该用户没有指定权限</div>

                        </div>
                        <div class="form-group compact"><input name="username" id="username"
                                                               class="account-input withicon ng-pristine ng-invalid ng-invalid-required"
                                                               type="text" placeholder="用户名">
                            <span class="account-inputicon icon icon-user"></span></div>
                        <div class="form-group"><input name="password" id="password"
                                                       class="account-input withicon ng-pristine ng-invalid ng-invalid-required ng-valid-minlength"
                                                       type="password" placeholder="密码">
                            <span class="account-inputicon icon icon-lock"></span></div>
                        <div class="form-group compact">
                            <div id="slider">
                                <div id="slider_bg"></div>
                                <span id="label">>></span> <span id="labelTip">拖动滑块验证</span> </div>
                        </div>
                        <input type="hidden" value="login" name="method">

                        <div class="form-group">
                            <button id="btn_Login" class="account-btn submit ng-binding" type="submit">登录</button>
                        </div>

                    </form>
                    <div>
                        <lable style="float:left">二维码下载</lable>
                        <img style="width: 140px; height: 140px" alt="二维码" title="APP下载"
                             src="login.do?method=getQrcode&content=http://192.168.5.20:8080/scms/apk/appDown.jsp">
                    </div>

                </div>
                <div class="account-line ng-scope">

                </div>
                <!-- ngInclude: '/app/templates/_auth_links.tpl.html' -->
                <div class="account-authlink ng-scope">

                </div>
            </div>
        </div>
    </div>

    <!-- ngIf: !noGlobalWrap -->
    <footer class="account-footer ng-scope" ng-if="!noGlobalWrap">
        <p class="footer-line">
            <a class="footer-link" href="#">运维QQ群号:280276918</a> |
            <a class="footer-link" href="#">24小时运维电话:400-690-5882</a> |
            <!-- <a class="footer-link" href="http://ele.me/support/about/agreement">服务条款和协议</a> |
            <a class="footer-link" href="http://jobs.ele.me">加入我们</a> -->
        </p>
        <p class="footer-line line-under">
            <!-- 	增值电信业务许可证 :
                <a class="footer-link" target="_blank" rel="nofollow" href="http://www.shca.gov.cn/">沪B2-20150033</a> |
                <a class="footer-link" target="_blank" rel="nofollow" href="http://www.miibeian.gov.cn">沪ICP备 09007032</a> |
                <a class="footer-link" target="_blank" rel="nofollow" href="http://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&amp;entyId=20120305173227823">上海工商行政管理</a>
                Copyright ©2008-2015 ele.me, All Rights Reserved. -->
        </p>
    </footer>
    <!-- end ngIf: !noGlobalWrap -->
</div>
<% String errornote =request.getParameter("errornote");
    String erroruseragin =request.getParameter("erroruseragin");
    String erroruser =request.getParameter("erroruser");
%>
<script type="text/javascript">
    $(function () {
        var errornote = '<%=errornote%>';
        var erroruseragin = '<%=erroruseragin%>';
        var erroruser = '<%=erroruser%>';
        if (errornote != "null" || erroruseragin != "null" || erroruser != "null") {
            $('.ng-hide').css('cssText', 'display:block!important');
            if (erroruser != "null") $('.account-errtip').text("用户名或密码不正确!");
            if (erroruseragin != "null") $('.account-errtip').text("该用户没有指定权限!");

        }
    });
</script>
<script type="text/javascript" src="js/login.js"></script>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "No-cache");
    response.setDateHeader("Expires", -1);
    response.setHeader("Cache-Control", "No-store");
%>
<%session.invalidate();%>
</body>
</html>
