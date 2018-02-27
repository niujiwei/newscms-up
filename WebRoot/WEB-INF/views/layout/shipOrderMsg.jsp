<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.model.Delivery"%>
<%@ page import="com.jy.model.ShippingOrder"%>
<%@ page import="com.jy.model.ShipperOrderImg"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运单信息</title>
<!-- 复制orderImg.jsp的插件 -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="./images/print.ico" type=image/x-icon rel="shortcut icon" />
<link rel="stylesheet" href="./css/viewer.css" type="text/css"></link>
</head>


<!-- 结束 -->

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
	ShippingOrder sp = (ShippingOrder) request.getAttribute("sp");
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
      List<ShipperOrderImg> listOrderImg=null;
	if(!request.getAttribute("pid").equals("")){
	listOrderImg = (List<ShipperOrderImg>) request.getAttribute("pid");
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
#details{
	bottom: 44px;
	text-align: center;
	width: 100%;
	padding-top:50px;
	z-index: 0;
	overflow: hidden;
	
}
#deta{
	bottom: 44px;
	text-align: left;
	width: 100%;
	z-index: 0;
	overflow: hidden;
}
</style>
<script type="text/javascript" language=JavaScript charset="UTF-8">

			
			$("#loading").ajaxStart(function(){ 
		       $(this).html("<img src='./images/loading.gif' />"); 
		     }); 
		     $("#loading").ajaxSuccess(function(){ 
		        $(this).html(""); 
		        // $(this).empty(); // 或者直接清除 
		      });   
					
			var orders_id = '<%=sp.getShiping_order_id()%>';
				$("#details").show();
				$("#deta").html("");				
				if(orders_id!=""&&orders_id!=null){
					$.ajax({
						   type: "post",
						   url: 'shipOrder.do?method=getShowOrder',
						   data: {orders_id:orders_id},
						   success: function(msg){
						     
							   $("#deta").html("");
							   if(msg==""){
								   $("#deta").append("<tr><td id='error'>*抱歉！未查到此运单"+pid+"信息，请确认运单号码是否正确，或致电终端配送项目组业务合作。</td></tr>");
							   }else{
								   $.each(msg,function(x,y){
									   $("#deta").append('<tr><td>'+y.goods_arrive_remakes+'</td></tr>');
									});
							   }
						   }
						   
						});
					}
			
				
	</script>

<body>
    <!-- 查看运单页面显示的顺序依次调整为：回单照片、运单状态、详细信息 -->
	<div class="easyui-tabs" id="tt">
		<div title="签收图片" class="queueList" style="height:300px;">
		<ul class="images">
		<%
			for (ShipperOrderImg shipperOrderImg : listOrderImg) {
		%>
			<li><img style="width:680px;" class="imsty" src=<%=shipperOrderImg.getImageUrl()%>>
			</li>
		<%
	      }
		%>						
							
			</ul>
		
	 </div>
	 <div title="运单状态"  style="overflow:auto;padding:5px;" id="dddddd">  
			<div id="details" style="text-align:left">
				<div id="loading"></div> 
				<table id="deta">
			</table>
		    </div>
         </div>
		<div title="运单详细信息" id="order" style="padding-left: 50px">
			<table width="900" border="0" class="sixes">
				<tr>
					<td width="100" height="25">运单状态：</td>
					<td width="150">&nbsp;<b style="color: red;font-size: 16px;">${sp.order_state}</b>
					</td>
					<td width="100">终到位置：</td>
					<td width="300">&nbsp;<b style="color: red;font-size: 16px;">
							${sp.end_address}</b>
					</td>
					<td width="100">货运编号：</td>
					<td width="170">&nbsp;<b style="color: red;font-size: 16px;">${sp.shiping_order_num}</b>
					</td>
					<td width="120">出货订单号：</td>
					<td width="">&nbsp;<b style="color: red;font-size: 16px;">${sp.shiping_order_goid}</b>
					</td>
				</tr>
			</table>
			<table class="bgurang " width="850" height="261" border="1"
				bordercolor="#000000" cellspacing="0" cellpadding="0">
				<tr>
					<td width="75" height="20">起运时间</td>
					<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.send_time}</td>
					<td width="75" height="20">运行区间</td>
					<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.send_station}至${sp.end_address}</td>
					<td height="24">受理机构</td>
					<td colspan="" class="bgurangtbs">&nbsp;${sp.send_mechanism }</td>
				</tr>
				<tr>
					<td width="60" height="20">月结编号</td>
					<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.shiping_yueid}</td>
					<td width="100" height="20">收货客户地址</td>
					<td height="20" colspan="3" class="bgurangtbs">&nbsp;${sp.end_mechanism}</td>

				</tr>
				<tr>

					<td height="24">发货客户名称</td>
					<td class="bgurangtbs">&nbsp;${sp.custom_name }</td>
						<td width="60" height="20">发货联系人</td>
						<td height="20" colspan="" class="bgurangtbs">&nbsp;${sp.custom_contact}</td>
						<td>发货客户电话</td>
						<td  class="bgurangtbs">&nbsp;${sp.custom_tel}</td>
				</tr>

				<tr>
					<td height="24">收货客户名称</td>
					<td colspan="" class="bgurangtbs">&nbsp;${sp.receipt }</td>
					<td height="">收货联系人</td>
					<td colspan="" class="bgurangtbs">&nbsp;${sp.receipt_name }</td>

					<td>收货客户电话</td>
					<td colspan="" class="bgurangtbs">&nbsp;${sp.receipt_tel }</td>
				</tr>
			   <tr>
				<td height="20" >发货备注</td>
				<td class="bgurangtbs" colspan="5">${sp.remarks}</td>
					

				
				</tr>
				<tr>
					

					<td height="31">货物名称</td>
					<td width="">回单份数</td>
					<td width="">总件数</td>
					<td width="">总重量</td>
					<td>总体积</td>
					<td width="">代收货款</td>
					
				</tr>
				<tr>
					<td height="29" class="bgurangtbs">${sp.goods_name }</td>
					<td class="bgurangtbs">${sp.is_recept}</td>
					<td class="bgurangtbs">${sp.goods_num }</td>
					<td class="bgurangtbs">${sp.goods_weight }</td>
					<td class="bgurangtbs">${sp.goods_vol }</td>
					<td class="bgurangtbs">${sp.trade_agency }</td>
					

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
					<td height="28" colspan="16">货运单票导入时间
						:${sp.updatetime}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;制单人：${sp.shipping_order }</td>
				</tr>
			</table>
		</div>
		
	</div>
	
</body>
</html>
