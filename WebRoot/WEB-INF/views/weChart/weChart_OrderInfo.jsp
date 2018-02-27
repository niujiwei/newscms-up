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
<style>
.myOrder{
font-weight:bold;
color:red;
font-size:16px;
}
</style>
</head>

<body>

	<header class="mui-bar mui-bar-nav">
		<a id="back"
			class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">DMS 运单信息</h1>
	</header>
	<div class="mui-content">
		<div class="weui-panel weui-panel_access">
			<div class="weui-panel__bd">
				<div class="weui-cells">
					<div class="weui-form-preview">
					    <div class="weui-form-preview__hd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">起运时间:</label> 
								<span id="send_time"></span>
							</div>
						</div>
						<div class="weui-form-preview__hd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">货物名称</label> <em
									class="weui-form-preview__value" id="goods_name"></em>
							</div>
						</div>
						<div class="weui-form-preview__hd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">件数/重量/体积</label> <em
									class="weui-form-preview__value" id="goods_packing"></em>
							</div>
						</div>
						<div class="weui-form-preview__bd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">发货客户联系人</label> <span
									class="weui-form-preview__value" id="custom_contact"></span>
							</div>
						</div>
						<div class="weui-form-preview__bd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">发货客户联系人电话</label> <span
									class="weui-form-preview__value" id="custom_tel">联系人电话</span>
							</div>
						</div>
						<div class="weui-form-preview__bd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">司机姓名</label> <span
									class="weui-form-preview__value" id="driver_name">司机姓名</span>
							</div>
						</div>
						<div class="weui-form-preview__bd">
							<div class="weui-form-preview__item">
								<label class="weui-form-preview__label">司机电话</label> <span
									class="weui-form-preview__value" id="driver_phone">司机电话</span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="weui-panel__ft">
				<a href="javascript:void(0);"
					class="weui-cell weui-cell_access weui-cell_link"
					id="showAndroidActionSheet">
					<div class="weui-cell__bd"><p class="myOrder">查看更多</p></div> <span class="weui-cell__ft">点击选择进行操作</span>
				</a>
			</div>
		</div>

		<div class="weui-cells" id="orderHistoryList"></div>
		<script type="text/x-jquery-tmpl" id="orderHistoryTemplate">
          <div class="weui-cell">
                <div class="weui-cell__hd"><p></p></div>
                <div class="weui-cell__bd">
                    <p>${'${'}goods_arrive_remakes}</p>
                </div>
                <div class="weui-cell__ft"></div>
            </div>
		</script>

		<div class="weui-skin_android" id="androidActionsheet"
			style="display: none">
			<div class="weui-mask"></div>
			<div class="weui-actionsheet">
				<div class="weui-actionsheet__menu" id="wechartMenu">
					<div class="weui-actionsheet__cell" id="orderHistory">
						运单节点信息查询</div>
					  <div class="weui-actionsheet__cell" id="orderBaiDuMap">轨迹信息</div>

					<!-- <div class="weui-actionsheet__cell">示例菜单</div> -->
				</div>
			</div>
		</div>
	</div>
	<script type="text/x-jquery-tmpl" id="menuTemplate">
         <div class="weui-actionsheet__cell" id="driverEvalation">司机评价</div>
         <div class="weui-actionsheet__cell" id="singOrderImg">查看签收图片</div>
    </script>

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/jquery.tmplPlus.js"></script>
    <script src="WeChart/wechart/wechart.js"></script>
	<script type="text/javascript">
	<%String orderid = (String) request.getAttribute("orderid");%>
	var orderid = '<%=orderid%>';
		//
		$(function() {
			mui.ajax('weChart.do?method=weChartGetOrderMessage', {
				data : {
					orderid :orderid,
				},
				type : 'post',//HTTP请求类型
				success : function(data) {
				    var order = data.order;
				    if(order){
				    $('#goods_name').text(order.goods_name);
				    var goods_packing=order.goods_num+'/'+order.goods_weight+'/'+order.goods_vol;
				    $('#goods_packing').text(goods_packing);
				    $('#custom_contact').text(order.custom_contact);
				    $('#custom_tel').text(order.custom_tel);
				    $('#driver_name').text(order.driver_name);
				    $('#driver_phone').text(order.driver_phone);
				    $('#send_time').text(order.send_time);
				    mui.scrollTo($('.mui-content').height(),10);
				    if(order.comment_state==0&&order.shipping_order_state>3){
				         //将来扩展用
				        $('#menuTemplate').tmpl('').appendTo(
									'#wechartMenu');
						$('#driverEvalation').on('click',function(){
			    			$androidActionSheet.fadeOut(200);
			     			mui.openWindow({
							url : 'weChart.do?method=weChartDriverEvaluation&orderid='+orderid,
							id : 'driverEvaluation',
							});
						});	
						
						$('#singOrderImg').on('click',function(){//签收图片点击事件
			               
			                $androidActionSheet.fadeOut(200);
			                mui.openWindow({
					            url : 'weChart.do?method=weChartSignOrderImage&orderid='+orderid,
					            id : 'weChartSignOrderImage',
				            });
			            });	
				    }
				    }
				},
				error : function(xhr, type, errorThrown) {
					//异常处理；
					console.log(type);
				}
			});


			var $androidActionSheet = $('#androidActionsheet');
			var $androidMask = $androidActionSheet.find('.weui-mask');

			$('#showAndroidActionSheet').on('click', function() {
				$androidActionSheet.fadeIn(200);
				 $androidMask.on('click',function () {
                    $androidActionSheet.fadeOut(200);
                 });
			});
			
			
			$('#orderBaiDuMap').on('click',function(){//地图点击事件
			    $androidActionSheet.fadeOut(200);
			    mui.openWindow({
					url : 'weChart.do?method=weChartOrderInfoMap&orderid='+orderid,
					id : 'weChartOrderMap',
				});
			});	
						    
			$('#orderHistory').on('click',function(){//节点信息点击事件
			   
			    mui.ajax('weChart.do?method=weChartGetOrderHistoryList', {
				data : {
					orderid :orderid,
				},
				type : 'post',//HTTP请求类型
				success : function(data) {
				   $('#orderHistoryList').empty();
				    var historyList=data.historyList;
				    mui.each(historyList, function(index, item) {
							$('#orderHistoryTemplate').tmpl(item).appendTo(
									'#orderHistoryList');
						});
				    
				    
				     mui.scrollTo($('.mui-content').height(),10);
				},
				error : function(xhr, type, errorThrown) {
					//异常处理；
					console.log(type);
				}
			});
			      $androidActionSheet.fadeOut(200);
			   
			});
			
			
		});
	</script>
</body>
</html>
