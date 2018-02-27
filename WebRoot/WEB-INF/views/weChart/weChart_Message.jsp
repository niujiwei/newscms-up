<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.jy.model.UserInfo"%>
<%@page import="com.jy.common.SessionInfo"%>
<head>
<link rel="stylesheet" href="WeChart/wechart/css/weui.css" />
<link href="WeChart/mui/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="WeChart/mui/css/iconfont.css" />
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>DMS 微信平台</title>
<style type="text/css">
.mui-bar-nav~.mui-content {
	text-align: center;
	 padding-top: 75px;
}
</style>
</head>

<body>

	<header class="mui-bar mui-bar-nav">
		<h1 id="title" class="mui-title">错误页面</h1>
	</header>
	<div class="mui-content">
		<div class="icon-box">
			<i class="weui-icon-warn weui-icon_msg"></i>
			<div class="icon-box__ctn">
				<h3 class="icon-box__title">强烈警告</h3>
				<p class="icon-box__desc" id="message"></p>
			</div>
		</div>
		 <div id=messagesx style="display: none"><span id="jumpTo">3</span>秒后自动跳转到用户登录</div>
	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/jquery.tmplPlus.js"></script>
	<script src="WeChart/mui/js/mui.min.js"></script>
	<%
		String type = (String) request.getAttribute("type");
		String message = (String) request.getAttribute("message");
	%>
	<script type="text/javascript">
	
	    
		var type ='<%=type%>';
		var message ='<%=message%>';
		
		if(message!='null'){
		   $('#message').text(message);
		}
		if(type=='sx'){
		   $('#messagesx').css('display','block'); 
		   var surl = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9834a157619c2111&redirect_uri=http://159i34629v.51mypc.cn/scms/weChart.do?method=getCode&response_type=code&scope=snsapi_base&state=1#wechat_redirect';
		   countDown(3,surl);
		}

		function countDown(secs, surl) {
			var jumpTo = document.getElementById('jumpTo');
			jumpTo.innerHTML = secs;
			if (--secs > 0) {
				setTimeout("countDown(" + secs + ",'" + surl + "')", 1000);
			} else {
				location.href = surl;
			}
		}
	</script>
</body>
</html>
