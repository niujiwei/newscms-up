<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="WeChart/wechart/css/weui.css" />
<script src="WeChart/mui/js/mui.min.js"></script>
<link href="WeChart/mui/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="WeChart/mui/css/iconfont.css" />
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>DMS 微信平台</title>
</head>

<body>

	<header class="mui-bar mui-bar-nav">
		<a id="back"
			class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">DMS 运单地图信息</h1>
	</header>
	<div class="mui-content">
		<div id="baiduMap" style="height:500px;width:100%;"></div>
	</div>
	<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=3ktrkqQ4iISBiphzz8mkrZ68"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/jquery.tmplPlus.js"></script>
	<script type="text/javascript" src="./baidumap/baidumap.js"></script>
    <script src="WeChart/wechart/weChartBaiduMap.js"></script>
	<script type="text/javascript">
	<%String orderid = (String) request.getAttribute("orderid");%>
	var orderid = '<%=orderid%>';
		//
		$(function() {
		     $baidumap.method.init($baidumap.defaultOptions);//初始化地图
		    $.ajax({
					type : "post",
					url : 'weChart.do?method=getShippingOrderHistoryInfo',
					data : {
						shiping_order_id : orderid
					},
					success : function(msg) {
					  
					     weChartBMap(msg);
					}
				});
		   
		    
			
		});
	</script>
	<script type="text/x-jquery-tmpl" id="infoWindow">
	<div class="infoWindowClass">
	<table class="infoWindowTb">
		<tr>
			<td>用户名:</td>
			<td>${'${'}username}</td>
		</tr>
		<tr>
			<td>所属机构 :</td>
			<td>${'${'}depement}</td>
		</tr>
		<tr>
			<td>定位来源： </td>
			<td>${'${'}state}</td>
		</tr>
		<tr>
			<td>当前位置：</td>
			<td>${'${'}address}</td>
		</tr>
		<tr>
			<td>定位时间：</td>
			<td>${'${'}time}</td>
		</tr>
		<tr>
			<td>天气状况：</td>
			<td>${'${'}weather_data}</td>
		</tr>
		<tr>
			<td>经纬度：</td>
			<td>${'${'}point}</td>
		</tr>
		
	</table>
	</div>
</script>
</body>
</html>
