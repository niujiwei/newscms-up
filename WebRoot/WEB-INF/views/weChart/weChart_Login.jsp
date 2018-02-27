<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.jy.model.UserInfo"%>
<%@page import="com.jy.common.SessionInfo"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="WeChart/mui/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="WeChart/mui/css/iconfont.css" />
<link rel="stylesheet" href="WeChart/wechart/css/weui.css" />
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>DMS 微信平台</title>
</head>

<body>
	<header class="mui-bar mui-bar-nav">
	<h1 class="mui-title">DMS 微信公众平台</h1>
	</header>
	<div class="mui-content">
		<div class="weui-cells__title">用户的个人信息</div>

		<div class="weui-cells">
			<a href="javascript:void(0);"
				class="weui-media-box weui-media-box_appmsg">
				<div class="weui-media-box__hd">
					<img class="weui-media-box__thumb"
						src='WeChart/mui/images/orderimg.png' id="headimgurl" alt="">
				</div>
				<div class="weui-media-box__bd">
					<h4 class="weui-media-box__title" id="nickname"></h4>
					<p class="weui-media-box__desc" id="province"></p>
				</div> </a>
		</div>

		<div class="weui-cells__title">客户订单联系信息绑定(暂定)</div>
		<form action="weChart.do?method=weChartUpdateUserInfo" method="post"
			id="weChartSaveForm">
			<div class="weui-cells">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">姓名</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="text" name="username" style="margin-bottom: 0px;"
							id="username" placeholder="请输入姓名">
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">手机号</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="tel" id="phone" name="phone"
							placeholder="请输入手机号" style="margin-bottom: 0px;">
					</div>
				</div>

				<input type="hidden" name="openid" id="openid" value="">
			</div>
		</form>

		<div class="weui-btn-area">
			<a class="weui-btn weui-btn_primary" href="javascript:"
				id="showTooltips">确定</a>
		</div>

		<div class="weui-footer">
			<p class="weui-footer__text">Copyright &copy; 2008-2016 weui.io</p>
		</div>
		<!-- 这里是弹出框 -->
		<div id="loadingToast" style="display:none;">
			<div class="weui-mask_transparent"></div>
			<div class="weui-toast">
				<i class="weui-loading weui-icon_toast"></i>
				<p class="weui-toast__content">数据加载中</p>
			</div>
		</div>

		<div class="js_dialog" id="androidDialog1" style="display: none;">
			<div class="weui-mask"></div>
			<div class="weui-dialog weui-skin_android">
				<div class="weui-dialog__hd">
					<strong class="weui-dialog__title">提示信息</strong>
				</div>
				<div class="weui-dialog__bd"></div>
				<div class="weui-dialog__ft">
					<a href="javascript:;"
						class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
				</div>
			</div>
		</div>
	</div>
	<script src="WeChart/wechart/wechart.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="WeChart/mui/js/mui.min.js"></script>
	<%
		UserInfo user = (UserInfo) session.getAttribute(SessionInfo.weChartUserInfo);
		JSONObject jsonUser = JSONObject.fromObject(user);
	%>
	<script type="text/javascript">
		var user =<%=jsonUser%>;
//
		$(function() {
			var headimgurl = $('#headimgurl');
			var nickname = $('#nickname');
			var province = $('#province');
			var openid = $('#openid');
			if (user) {
				headimgurl.attr('src', user.headimgurl);
				nickname.text(user.nickname);
				province.text(user.province + user.city);
				openid.val(user.openid);
			}
			var $loadingToast = $('#loadingToast');
			var $androidDialog1 = $('#androidDialog1');
			var $dialog_bd = $('.weui-skin_android .weui-dialog__bd');

			$('#androidDialog1').on('click', '.weui-dialog__btn', function() {
				$(this).parents('.js_dialog').fadeOut(200);
			});

			$('#showTooltips').on('click', function() {
				var username = $.trim($('#username').val());
				var phone = $.trim($('#phone').val());
				if (username == null || username == "") {
					$dialog_bd.html("姓名不能为空");
					$androidDialog1.fadeIn(200);

				} else if (phone == null || phone == "") {
					$dialog_bd.html("手机号不能为空");
					$androidDialog1.fadeIn(200);

				} else {
					if ($loadingToast.css('display') != 'none')
						return;
					$loadingToast.fadeIn(100);
					$('#weChartSaveForm').submit();

				}
			});

		});
	</script>

</body>
</html>
