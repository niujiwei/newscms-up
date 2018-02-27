<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>逆向运单信息</title>

<link href="./images/print.ico" type=image/x-icon rel="shortcut icon" />
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" href="./css/viewer.css" type="text/css"></link>
<script type="text/javascript" src="./easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="./js/jqprint/jquery-migrate-1.1.0.js"></script>
<script type="text/javascript" src="./js/jqprint/jquery.jqprint-0.3.js"></script>
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<script type="text/javascript" src="./js/viewer.js"></script>
<style type="text/css">
.STYLE3 {
	font-size: x-small
}

.bgurang {
	background: rgb(242, 242, 242);
	font-size: 10px;
	text-align: center;
	border-color: rgb(110, 110, 110);
}

.window {
	background-color: #EFF3F7;
}

.bgurangtable {
	background: rgb(242, 242, 242);
	font-size: 10px;
	text-align: right;
	border-color: rgb(110, 110, 110);
}

.bgurangtb {
	background: rgb(242, 242, 242);
	font-size: 10px;
	color: blue;
	text-align: left;
	border-color: rgb(110, 110, 110);
}

.bgurangtbs {
	background: rgb(242, 242, 242);
	color: blue;
	font-size: 13px;
	text-align: center;
	border-color: rgb(110, 110, 110);
}

.sixes {
	font-size: 14px;
}

.btncss {
	background-color: #B7D9F5;
	border: 1px solid #CFDFEC;
	display: inline-block;
	cursor: pointer;
	color: #060606;
	font-family: Arial;
	font-size: 14px;
	padding: 5px 28px;
	text-decoration: none;
	transition: all 0.5s;
	-moz-transition: all 0.5s;
	-webkit-transition: all 0.5s;
	-o-transition: all 0.5s;
}
</style>


</head>
<%
	ReverserOrderModel sp = (ReverserOrderModel) request.getAttribute("sp");
	User user = (User) request.getSession().getAttribute(
	SessionInfo.SessionName);
	      List<ShipperOrderImg> listOrderImg=new ArrayList<ShipperOrderImg>();
	List<ShipperOrderImg> takingOrderImg=new ArrayList<ShipperOrderImg>();
	if(!request.getAttribute("pid").equals("")){
	listOrderImg = (List<ShipperOrderImg>) request.getAttribute("pid");
		takingOrderImg = (List<ShipperOrderImg>) request.getAttribute("takingOrderImg");
	}
%>
<script type="text/javascript">
$(function(){
$('.images img').error(function() { 
    $(this).remove(); 
});
$('.images img').viewer();   

});
</script>
<style index="index" id="css_index">
#details {
	bottom: 44px;
	text-align: center;
	width: 100%;
	padding-top: 50px;
	z-index: 0;
	overflow: hidden;
}

#deta {
	bottom: 44px;
	text-align: left;
	width: 100%;
	z-index: 0;
	overflow: hidden;
}
</style>
<script type="text/javascript">

	var orders_id = '<%=sp.getShiping_order_id()%>';

	$("#details").show();
	$("#deta").html("");
	if (orders_id != "" && orders_id != null) {
		$.ajax({
					type : "post",
					url : 'reverse.do?method=getReverseOrderHistoryInfo',
					data : {
						id : orders_id
					},
					success : function(msg) {

						$("#deta").html("");
						if (msg == "") {
							$("#deta")
									.append(
											"<tr><td id='error'>*抱歉！未查到此运单"
													+ pid
													+ "信息，请确认运单号码是否正确，或致电终端配送项目组业务合作。</td></tr>");
						} else {
							$.each(msg, function(x, y) {
								$("#deta").append(
										'<tr><td>' + y.goods_arrive_remakes
												+ '</td></tr>');
							});
						}
					}

				});
	}
</script>

<body>
	<!-- 查看运单页面显示的顺序依次调整为：回单照片、运单状态、详细信息 -->
	<div class="easyui-tabs" id="tt">
		<div title="接单发运图片" class="queueList" style="height:200px;">
			<ul class="images">
				<%
					for (ShipperOrderImg shipperOrderImg : takingOrderImg) {
				%>
				<li><img style="width:500px;" class="imsty"
						 src=<%=shipperOrderImg.getImageUrl()%>>
				</li>
				<%
					}
				%>
			</ul>

		</div>
		<div title="签收图片" class="queueList" style="height:200px;">
			<ul class="images">
				<%
					for (ShipperOrderImg shipperOrderImg : listOrderImg) {
				%>
				<li><img style="width:500px;" class="imsty"
					src=<%=shipperOrderImg.getImageUrl()%>>
				</li>
				<%
					}
				%>
			</ul>

		</div>
		<div title="运单状态" style="overflow:auto;padding:5px;" id="dddddd">
			<div id="details" style="text-align:left">
				<div id="loading"></div>
				<table id="deta">
				</table>
			</div>
		</div>
		<div title="运单详细信息" id="order" style="padding-left: 30px">
			<table width="720" border="0" class="sixes">
				<tr>
					<td width="100" height="25">运单状态：</td>
					<td width="120"><b style="color: red;font-size: 16px;">${sp.shipping_order_statestr}</b>
					</td>
					<td width="100" height="25">运单类型：</td>
					<td width="120"><b style="color: red;font-size: 16px;">${sp.order_type_name}</b>
					</td>
					<td width="100">货运编号：</td>
					<td width="170">&nbsp;<b style="color: red;font-size: 16px;">${sp.shiping_order_num}</b>
					</td>
				</tr>
			</table>
			<table class="bgurang " width="710" height="261" border="1"
				   bordercolor="#000000" cellspacing="0" cellpadding="0">
				<tr>
					<td width="75" height="20">起运时间</td>
					<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.send_time}</td>
					<td width="75" height="20">调拨/退货始发位置</td>
					<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.end_address}</td>
					<td width="75" height="20">调拨/退货终到位置</td>
					<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.end_mechanism}</td>
				</tr>

				<tr>
					<td  height="24">发货客户名称</td>
					<td  colspan="" class="bgurangtbs">&nbsp;${sp.custom_name}</td>
					<td>发货联系人</td>
					<td  class="bgurangtbs">&nbsp;${sp.custom_contact}</td>
					<td  height="24">发货客户电话</td>
					<td  colspan="" class="bgurangtbs">&nbsp;${sp.custom_tel}</td>

				</tr>
				<tr>
					<td>收货客户名称</td>
					<td  class="bgurangtbs">&nbsp;${sp.receipt_name}</td>
					<td>收货联系人</td>
					<td  class="bgurangtbs">&nbsp;${sp.receipt_contact}</td>
					<td  height="24">收货客户电话</td>
					<td  colspan="" class="bgurangtbs">&nbsp;${sp.receipt_tel}</td>
				</tr>
				<tr>

					<td height="31">受理机构</td>
					<td width="">总件数</td>
					<td width="">实际件数</td>
					<td height="">总重量</td>
					<td width="">总体积</td>
					<td>通知时间</td>
				</tr>
				<tr>
					<td height="29" class="bgurangtbs">${sp.send_mechanism}</td>
					<td class="bgurangtbs">${sp.goods_num }</td>
					<td class="bgurangtbs">${sp.real_num }</td>
					<td class="bgurangtbs">${sp.goods_weight }</td>
					<td class="bgurangtbs">${sp.goods_vol }</td>
					<td class="bgurangtbs">${sp.updatetime }</td>


				</tr>
				<tr>
					<td height="31">&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td height="28" colspan="16">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						业务员： ${sp.shipping_order
							}&nbsp;&nbsp;&nbsp;&nbsp;制单人：${sp.shipping_order }</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
