<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.jy.model.UserInfo"%>
<%@page import="com.jy.common.SessionInfo"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<link rel="stylesheet" href="WeChart/wechart/css/weui.css" />
<link href="WeChart/mui/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="WeChart/mui/css/iconfont.css" />
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>DMS 微信平台</title>

</head>
<style>
.myOrder {
	font-weight: bold;
	color: red;
	font-size: 16px;
}
</style>

<body>
	<header class="mui-bar mui-bar-nav">
	<h1 class="mui-title">DMS 微信公众平台</h1>
	</header>
	<div class="mui-content">
		<div class="weui-cells__title">客户订单常用联系信息</div>
		<div class="weui-cells">
			<div class="weui-cell" id="updUserName">
				<div class="weui-cell__hd">姓名</div>
				<div class="weui-cell__bd"></div>
				<div class="weui-cell__ft" id="username"></div>
			</div>
			<div class="weui-cell" id="updUserPhone">
				<div class="weui-cell__hd">手机号</div>
				<div class="weui-cell__bd"></div>
				<div class="weui-cell__ft" id="phone"></div>
			</div>
		</div>
		<div class="weui-cells__title">点击下面进行操作</div>
		<div class="weui-cells">
			<a class="weui-cell weui-cell_access" href="javascript:;"
				id="weChartOrder">
				<div class="weui-cell__bd">
					<p class="myOrder">我的运单</p>
				</div>
				<div class="weui-cell__ft">点击查看我的运单</div> </a>
		</div>
		<div class="weui-cells">
			<a class="weui-cell weui-cell_access" href="javascript:;"
				id="btnSerach">
				<div class="weui-cell__bd">
					<p class="myOrder">运单号查询</p>
				</div>
				<div class="weui-cell__ft">点击输入运单号进行查询</div> </a>
		</div>
	</div>
	<div id="dialogs">
		<!-- 这里是弹出框 -->
		<div id="loadingToast" style="display:none;">
			<div class="weui-mask_transparent"></div>
			<div class="weui-toast">
				<i class="weui-loading weui-icon_toast"></i>
				<p class="weui-toast__content">数据加载中</p>
			</div>
		</div>

		<div class="js_dialog" id="dlgUpdateUserName" style="display: none;">
			<div class="weui-mask"></div>
			<div class="weui-dialog weui-skin_android">
				<div class="weui-dialog__bd">
					修改姓名<input class="weui-input" type="text" id="textUserName">
				</div>
				<div class="weui-dialog__ft">
					<a href="javascript:;"
						class="weui-dialog__btn weui-dialog__btn_default">取消</a> <a
						href="javascript:;" id="btnUpdateUserName"
						class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
				</div>
			</div>
		</div>

		<div class="js_dialog" id="dlgUpdateUserPhone" style="display: none;">
			<div class="weui-mask"></div>
			<div class="weui-dialog weui-skin_android">
				<div class="weui-dialog__bd">
					修改手机号<input class="weui-input" type="text" id="textUserPhone">
				</div>
				<div class="weui-dialog__ft">
					<a href="javascript:;"
						class="weui-dialog__btn weui-dialog__btn_default">取消</a> <a
						href="javascript:;" id="btnUpdateUserPhone"
						class="weui-dialog__btn weui-dialog__btn_primary">确认</a>
				</div>
			</div>
		</div>
		<div class="js_dialog" id="dlgOrderSearch" style="display: none;">
			<div class="weui-mask"></div>
			<div class="weui-dialog weui-skin_android">
				<div class="weui-dialog__bd">

					<input class="weui-input" type="text" placeholder="货运编号"
						id="textOrderNum"> <input class="weui-input" type="text"
						placeholder="出货订单号" id="textOrderGoid">
				</div>
				<div class="weui-dialog__ft">
					<a href="javascript:;" id="btnOrderSearch"
						class="weui-dialog__btn weui-dialog__btn_primary">查询</a>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="openid" id="openid" value="">
	<script src="WeChart/wechart/wechart.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="WeChart/mui/js/mui.min.js"></script>
	<script type="text/javascript">
			<%
		UserInfo user = (UserInfo) session.getAttribute(SessionInfo.weChartUserInfo);
		JSONObject jsonUser = JSONObject.fromObject(user);
           %>
			var user = <%=jsonUser%>;

			$(function() {
				$('#dialogs').on('click', '.weui-dialog__btn', function() {
					$(this).parents('.js_dialog').fadeOut(200);
				});

				var headimgurl = $('#headimgurl');
				var nickname = $('#nickname');
				var sex = $('#sex');
				var province = $('#province');
				var username = $('#username');
				var phone = $('#phone');
				var openid = $('#openid');
				if(user == null || user == 'null') {
					var option = {
						message: '请先登录！！！3秒自动跳转获取登录',
						type: 'sx',
					};
					wechart.yanzheng(option);
					return;
				}
				headimgurl.attr('src', user.headimgurl);
				nickname.text(user.nickname);
				province.text(user.province + user.city);
				sex.text(user.sex);
				username.text(user.username);
				phone.text(user.phone);
				openid.val(user.openid);

				var $loadingToast = $('#loadingToast');

				$('#weChartOrder').on('click', function() {
					if($loadingToast.css('display') != 'none')
						return;
					$loadingToast.fadeIn(100);
					openOrder(openid);
				});

				//修改用户名
				var $dlgUpdateUserName = $('#dlgUpdateUserName');
				var $androidMask = $dlgUpdateUserName.find('.weui-mask');
				$('#updUserName').on(
					'click',
					function() {
						if($dlgUpdateUserName.css('display') != 'none')
							return;
						$dlgUpdateUserName.fadeIn(100);
						$androidMask.on('click', function() {
							$dlgUpdateUserName.fadeOut(200);
						});
						$('#textUserName').val(username.text());

						$('#btnUpdateUserName').on(
							'click',
							function() {
								username.text($('#textUserName').val());
								updateUserNamePhone($('#textUserName').val(), phone.text());

							});
					});

				//修改手机号
				var $dlgUpdateUserPhone = $('#dlgUpdateUserPhone');
				$androidMask = $dlgUpdateUserPhone.find('.weui-mask');

				$('#updUserPhone').on(
					'click',
					function() {
						if($dlgUpdateUserPhone.css('display') != 'none')
							return;
						$dlgUpdateUserPhone.fadeIn(100);
						$androidMask.on('click', function() {
							$dlgUpdateUserPhone.fadeOut(200);
						});
						$('#textUserPhone').val(phone.text());

						$('#btnUpdateUserPhone').on(
							'click',
							function() {
								phone.text($('#textUserPhone').val());
								updateUserNamePhone(username.text(), $('#textUserPhone').val());

							});
					});

				var $dlgOrderSearch = $('#dlgOrderSearch');
				var $androidMask = $dlgOrderSearch.find('.weui-mask');
				$("#btnSerach").on("click", function() {
					if($dlgOrderSearch.css('display') != 'none') return;
					$dlgOrderSearch.fadeIn(100);
					$androidMask.on('click', function() {
						$dlgOrderSearch.fadeOut(200);
					});

					$('#btnOrderSearch').on('click', function() {
						var textOrderNum = $('#textOrderNum').val();
						var textOrderGoid = $('#textOrderGoid').val();
						if(textOrderNum == '' && textOrderGoid == '') return $dlgOrderSearch.fadeOut(200);
						$('#orderMessage').empty();
						searchOrder(textOrderNum, textOrderGoid);
						$dlgOrderSearch.fadeOut(200);
					});

				});
			});

			function openOrder(openid) {
				var id = openid.val();
				mui.openWindow({
					url: 'weChart.do?method=weChartToOrder&type=1&openid=' + id,
					id: 'orderlist',
				});
			};

			function searchOrder(textOrderNum, textOrderGoid) {
				mui.openWindow({
					url: 'weChart.do?method=weChartToOrder&type=2&textOrderNum=' + textOrderNum + '&textOrderGoid=' + textOrderGoid,
					id: 'orderlist',
				});
			}

			function updateUserNamePhone(userName, phone) {
				mui.ajax('weChart.do?method=weChartUpdateUserInfoByCookie', {
					data: {
						openid: user.openid,
						username: userName,
						phone: phone,
					},
					type: 'post', //HTTP请求类型
					success: function(data) {
						document.location.reload();
					},
					error: function(xhr, type, errorThrown) {
						//异常处理；
						console.log(type);
					}
				});
			};
		</script>
</body>

</html>