<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title></title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<jsp:include page="../map/allMap.jsp"></jsp:include>
<link rel="stylesheet" type="text/css"
	href="WeChart/mui/css/mui.min.css" />
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=3ktrkqQ4iISBiphzz8mkrZ68"></script>

<style type="text/css">
</style>

<style type="text/css">
.infoWindowTb {
	border-collapse: collapse;
	border-spacing: 0;
	line-height: 18px;
	white-space: nowrap;
	color: #333;
	font-size: 12px;
}
</style>

<script type="text/javascript">
	$(function() {
		$baidumap.method.init($baidumap.defaultOptions);//初始化地图
	});
	//查询
	function searchcarno() {
		$("#historyList").empty();
		$("#driverMessage").css('display', 'none');
		$("#listMessage").css('display', 'none');
		var shiping_order_num = $("#shiping_order_num").val();
		var shiping_order_goid = $("#shiping_order_goid").val();
		if (shiping_order_num == "" && shiping_order_goid == "")
			return $.messager.alert('提示', '请输入货运编号或者出货订单号');

		$.ajax({
			type : "post",
			url : 'shipOrder.do?method=quickSearch',
			data : {
				shiping_order_goid : shiping_order_goid,
				shiping_order_num : shiping_order_num
			},
			success : function(msg) {
				var data = JSON.parse(msg);
				var length = data.length;
				if (length != 1)
					return $.messager.alert('快速查询', '您搜索的运单不唯一或者不存在', 'info');
				$.ajax({
					type : "post",
					url : 'baiduMap.do?method=getShippingOrderHistoryInfo',
					data : {
						shiping_order_id : data[0].id
					},
					success : function(msg) {
						showMessage(msg);
					}
				});

			}

		});

	}
    var type = true;
	function showMessage(obj) {
		var data = obj;
		var history = data.history;//运单节点信息
		var driver = data.driver;//司机信息
		var user = data.user;//用户信息
		var time = data.time;//时间信息
		if (history == undefined || history == null)
			return $.messager.alert('快速查询', '您搜索的运单,未找到节点信息', 'info');
		if (driver == undefined || driver == null)
			return $.messager.alert('快速查询', '您搜索的运单,司机信息不存在', 'info');
		$.each(history, function(i, n) {
			$("#historyList").append(
					'<li class="mui-table-view-cell">' + n.goods_arrive_remakes
							+ '</li>');
		});
		$("#driverName").val(driver.driver_name);
		$("#driverPhone").val(driver.driver_phone);
		$("#driverMessage").css('display', 'block');
		$("#listMessage").css('display', 'block');
		$baidumap.method.init($baidumap.defaultOptions);//初始化地图 
		
		type=true;
		myBMap(obj);

	}
	
	function huifang() {
		if (type) {
		    type = false;
			myBMap.run();
			
			//$("#huifang").text("暂停");
		} else {
			myBMap.pause();
			type = true;
			//$("#huifang").text("回放");
		}
		//$("#huifang").text("回放");
	}

	function cleanall() {
		$("#shiping_order_num").val("");
		$("#shiping_order_goid").val("");
		$("#historyList").empty();
		$("#driverMessage").css('display', 'none');
		$("#listMessage").css('display', 'none');
		$baidumap.method.init($baidumap.defaultOptions);//初始化地图 
	}
</script>

</head>

<body>
	<div class="easyui-layout" data-options="fit: true">

		<div data-options="region:'west',title:'检索条件',split:true"
			style="width:350px;">
			<!-- 车辆列表信息 -->

			<!--左侧上部  -->
			<div data-options="region:'north'" style="height:160px">
				<!-- 查询条件 -->
				<div class="mui-content">
					<div class="mui-input-group">
						<div class="mui-input-row">
							<label>货运编号：</label> <input type="text" class="mui-input-clear"
								id="shiping_order_num" placeholder="请输入货运编号">
						</div>
						<div class="mui-input-row">
							<label>出货订单号：</label> <input type="text"
								class="mui-input-clear mui-input-password"
								placeholder="请输入出货订单号" id="shiping_order_goid">
						</div>
						<div class="mui-button-row">
							<button type="button" class="mui-btn mui-btn-primary"
								onclick="searchcarno()">搜索</button>
							<button type="button" class="mui-btn mui-btn-danger"
								onclick="cleanall()">清空</button>
						</div>
					</div>
				</div>
				<div id="driverMessage" style="display: none" class="mui-content">
					<!-- 司机信息展示 -->
					<div class="mui-input-group">
						<div class="mui-input-row">
							<label>司机姓名：</label> <input type="text" class="mui-input-clear"
								id="driverName" readonly="readonly">
						</div>
						<div class="mui-input-row">
							<label>司机电话：</label> <input type="text" class="mui-input-clear"
								id="driverPhone" readonly="readonly">
						</div>
						<div class="mui-button-row">
						<button type="button"
							class="mui-btn mui-btn-primary mui-btn-outlined" id="huifang"
							onclick="huifang()">回放</button>
						<button type="button"
							class="mui-btn mui-btn-success mui-btn-outlined" id="refeach"
							onclick="searchcarno()">刷新</button>
					</div>
					</div>
				</div>
				<div id="listMessage" style="display: none">
					<!--司机信息展示  -->
					<ul class="mui-table-view" id="historyList">

					</ul>
					
				</div>


			</div>


		</div>


		<!-- 右侧地图 -->
		<div data-options="region:'center'"
			style="padding:5px;background:#eee;">
			<!-- 地图信息 -->
			<div id="baiduMap" style="height:-webkit-fill-available;width:100%;"></div>
		</div>
	</div>


	<!-- <form action="map.do?method=outjsontoexcel" method="post"
		id="outjsonfrom">
		<input id="outjson" name="outjson" value="" />
	</form> -->
	<script type="text/javascript" src="js/dateUtils.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/jquery.tmplPlus.js"></script>
	<script type="text/javascript" src="./baidumap/baidumap.js"></script>

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
