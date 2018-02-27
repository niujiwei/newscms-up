<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="WeChart/mui/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="WeChart/mui/css/iconfont.css" />
<link rel="stylesheet" href="WeChart/wechart/css/weui.css" />
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
</head>

<body>
	<header class="mui-bar mui-bar-nav">
	<a id="back"
			class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	<h1 class="mui-title">DMS 司机评价</h1>
	</header>
	<div class="mui-content">
		<ul class="mui-table-view">
			<li class="mui-table-view-cell mui-media"><a href="javascript:;">
					<img class="mui-media-object mui-pull-left"
					src="WeChart/mui/images/sj.png">
					<div class="mui-media-body">
						司机信息
						<p class='mui-ellipsis'>
							司机姓名： <span id="driver_name"></span>
						</p>
						<p class='mui-ellipsis'>
							司机电话： <span id="driver_phone"></span>
						</p>
					</div> </a>
			</li>
			<li class="mui-table-view-cell mui-media"><a href="javascript:;">
					<img class="mui-media-object mui-pull-left"
					src="WeChart/mui/images/icon_my_car.png">
					<div class="mui-media-body">
						货物信息
						<p class='mui-ellipsis'>
							货运编号： <span id="order_num"></span>
						</p>
						<p class='mui-ellipsis'>
							货物名称： <span id="good_name"></span>
						</p>
						<p class='mui-ellipsis'>
							货物件数： <span id="good_num"></span>
						</p>
					</div> </a>
			</li>
		</ul>
	</div>
	
		<div class="mui-card">
			<!--页眉，放置标题-->
			<div class="mui-card-header mui-card-media">
				<img src="WeChart/mui/images/pingjia.png" />
				<div class="mui-media-body">评价内容</div>
			</div>
			<!--内容区-->
			<div class="mui-card-content">
			    <div>
			    <ul id="star">
					<li class="mui-icon iconfont icon-star-demo"></li>
					<li class="mui-icon iconfont icon-star-demo"></li>
					<li class="mui-icon iconfont icon-star-demo"></li>
					<li class="mui-icon iconfont icon-star-demo"></li>
					<li class="mui-icon iconfont icon-star-demo"></li>
					<span id="num_span"></span>
				</ul>
				
			    </div>
				
				
				<div class="mui-input-row" style="margin: 10px 5px;">
					<textarea id="textarea" rows="5" placeholder="请输入评价内容"></textarea>
				</div>
				<div class="mui-card-footer">
					<button type="button"
						class="mui-btn mui-btn-success mui-btn-outlined mui-btn-block"
						onclick="pingjia()">评价</button>
				</div>
			</div>
		</div>
	
<script type="text/javascript" src="js/jquery.js"></script>
<script src="WeChart/wechart/wechart.js"></script>
<script src="WeChart/mui/js/mui.min.js"></script>
<script type="text/javascript" >
   <%String orderid = (String) request.getAttribute("orderid");%>
            var orderid = '<%=orderid%>';
			var num = 0;
			var datas = "";
				mui.init({
					swipeBack: false
				});
				window.onload = function() {
					start();
					mui.ajax('weChart.do?method=weChartGetOrderMessage', {
						data: {
							//shiping_order_goid: order_shipper,
							orderid: orderid
						},
						type: 'post', //HTTP请求类型 
						// timeout:10000,//超时时间设置为10秒；
						success: function(data) {
							message(data);
							datas=data.order;

							//服务器返回响应，根据响应结果，分析是否登录成功； ... 
						},
						error: function(xhr, type, errorThrown) {
							//异常处理；
							console.log(type);
							mui.alert("系统异常", '提示', '确认');
						}
					});

				};
			
			function message(message){
			   var mess = message.order;
			    document.getElementById("driver_name").innerHTML=mess.driver_name;
			    document.getElementById("driver_phone").innerHTML=mess.driver_phone;
			    document.getElementById("order_num").innerHTML=mess.shiping_order_num;
			    document.getElementById("good_name").innerHTML=mess.goods_name;
			    document.getElementById("good_num").innerHTML=mess.goods_num;
			}
			
			//获取url中的参数
			function getUrlParam(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
				var r = window.location.search.substr(1).match(reg);
				if(r != null) {
					return unescape(r[2]);
				} else {
					return null;
				}
			}

			function pingjia() {
			    var datamess = JSON.stringify(datas); 
			    var message = document.getElementById("textarea").value;
			    if(num==0){
			        mui.alert("请选择星星分数", '提示', '确认');
			    }else if(message==""){
			        mui.alert("请填写评价内容", '提示', '确认');
			    }else{
			    var mask = mui.createMask();
			    mui.ajax('weChart.do?method=weChartsSaveEvaluationMssage', {
						data: {
							//shiping_order_goid: order_shipper,
							message: message,
							num:num,
							datas:datamess
						},
						dataType: 'json', //服务器返回json格式数据 
						type: 'post', //HTTP请求类型 
						// timeout:10000,//超时时间设置为10秒；
						success: function(data) {
						    mask.close();//关闭遮罩
						    var state = data.state;
						    var message = data.message;
						    mui.alert(message, '提示', '确认',function(){
						         if(state){
						               mui.back();
						         }
						    });
						   
						  
							//showMessge(data);

							//服务器返回响应，根据响应结果，分析是否登录成功； ... 
						},
						error: function(xhr, type, errorThrown) {
							//异常处理；
							console.log(type);
							 mui.alert("系统异常", '提示', '确认');
						}
					});
			   }	//mui.back();
			}

			function start() {
				var star = document.getElementById("star");
				var star_li = star.getElementsByTagName("li");
				var i = 0;
				var j = 0;
				var len = star_li.length;

				for(i = 0; i < len; i++) {
					star_li[i].index = i;
					star_li[i].onclick = function() {
						var st = this.index + 1;
						num = this.index + 1;
						for(i = 0; i < st; i++) {
							star_li[i].style.color = "gold";
						}

						for(j = len; j > st; j--) {

							star_li[j - 1].style.color = "";
						}
						 document.getElementById("num_span").innerHTML="(已选择"+num+"分)";
					};
				}
			}
			
		</script>
</body>
</html>
