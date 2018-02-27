<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.jy.model.UserInfo"%>
<%@page import="com.jy.common.SessionInfo"%>
<head>

<link href="WeChart/mui/css/mui.min.css" rel="stylesheet" />
<link rel="stylesheet" href="WeChart/mui/css/iconfont.css" />
<link rel="stylesheet" href="WeChart/wechart/css/weui.css" />
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<title>DMS 微信平台</title>
<style type="text/css">.mui-content {
	/*避免导航边框和列表背景边框重叠，看起来像两条边框似得；*/
	margin-top: 45px;
}

.weui-label {
	width: 120px;
}</style>
</head>
<script type="text/javascript">
   
</script>
<body>

	<header class="mui-bar mui-bar-nav">
		<a id="back"
			class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<!-- <a id="btnSerach" class="mui-icon mui-icon-search mui-pull-right" style="color:red;">查询</a> -->
		<h1 id="title" class="mui-title">DMS 运单信息列表</h1>
	</header>
	<div class="mui-content">
		<!--下拉刷新容器-->
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<!--数据列表-->
				<ul class="mui-table-view mui-table-view-chevron" id="orderMessage">


				</ul>
			</div>
		</div>
	</div>
	
	<div class="js_dialog" id="dlgOrderSearch" style="display: none;">
			<div class="weui-mask"></div>
			<div class="weui-dialog weui-skin_android">
				<div class="weui-dialog__bd">
				
				<input class="weui-input" type="text" 
					placeholder="货运编号" id="textOrderNum">
				<input class="weui-input" type="text"
					placeholder="出货订单号" id="textOrderGoid">
				</div>
				 <div class="weui-dialog__ft">
					 <a
						href="javascript:;" id="btnOrderSearch"
						class="weui-dialog__btn weui-dialog__btn_primary">查询</a>
				</div>
			</div>
		</div>
	
	
	<!--底部显示弹出框  <div>
        <div class="weui-mask" id="iosMask" style="display: none"></div>
        <div class="weui-actionsheet" id="iosActionsheet">
            <div class="weui-actionsheet__menu">
                <div class="weui-actionsheet__cell">示例菜单</div>
                <div class="weui-actionsheet__cell">示例菜单</div>
                <div class="weui-actionsheet__cell">示例菜单</div>
                <div class="weui-actionsheet__cell">示例菜单</div>
            </div>
            <div class="weui-actionsheet__action">
                <div class="weui-actionsheet__cell" id="iosActionsheetCancel">取消</div>
            </div>
        </div>
    </div> -->

	<script type="text/x-jquery-tmpl" id="messageTemplate">
		   <div class="weui-cells__title"></div>
					<div class="weui-cells">
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">起运时间：</label>
							</div>
							<div class="weui-cell__bd"></div>
							<div class="weui-cell__ft">
								<label class="weui-label">${'${'}send_time}</label>
							</div>
						</div>

						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">货物名称：</label>
							</div>
							<div class="weui-cell__bd"></div>
							<div class="weui-cell__ft">
								<label class="weui-label">${'${'}goods_name}</label>
							</div>
						</div>

						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">货物件数：</label>
							</div>
							<div class="weui-cell__bd"></div>
							<div class="weui-cell__ft">
								<label class="weui-label">${'${'}goods_num}</label>
							</div>
						</div>

						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">发货联系人：</label>
							</div>
							<div class="weui-cell__bd"></div>
							<div class="weui-cell__ft">
								<label class="weui-label">${'${'}custom_contact}</label>
							</div>
						</div>

						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">发货联系电话：</label>
							</div>
							<div class="weui-cell__bd"></div>
							<div class="weui-cell__ft">
								<label class="weui-label">${'${'}custom_tel}</label>
							</div>
						</div>
						<div class="weui-cell">
						    <div class="weui-cell__hd">
								
							</div>
							<div class="weui-cell__bd"></div>
							<div class="weui-cell__ft">
								  {{wrap "#buttonTemplate"}}
                                  {{/wrap}}
								
							</div>
						</div>
					</div>
		</script>
	<script type="text/x-jquery-tmpl" id="buttonTemplate">
             <div class="button-sp-area" >
            <a href="javascript:;" class="myOrder weui-btn weui-btn_mini weui-btn_primary" data-order="${'${'}shiping_order_id}">查看</a>
            
			{{if shipping_order_state>=4}}
			  {{if comment_state==0}}
               <a href="javascript:;" class="driverEvaluation weui-btn weui-btn_mini weui-btn_warn" data-order="${'${'}shiping_order_id}">评价</a>
			  {{/if}}
			{{/if}}
            <div></div>
            </div>
		</script>
	<script src="WeChart/wechart/wechart.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.tmpl.js"></script>
	<script type="text/javascript" src="js/jquery.tmplPlus.js"></script>
	<script src="WeChart/mui/js/mui.min.js"></script>
	<script type="text/javascript">
	 // 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
	<%String openid = (String) request.getAttribute("openid");
	 String type = (String) request.getAttribute("type");
	 String textOrderNum = (String) request.getAttribute("textOrderNum");
	 String textOrderGoid = (String) request.getAttribute("textOrderGoid");
	 
	%>
		var openid ='<%=openid%>';
		var type ='<%=type%>';
		var textOrderNum = '<%=textOrderNum%>';
		var textOrderGoid =  '<%=textOrderGoid%>';
		$(function() {
			mui.init({
				pullRefresh : {
					container : '#pullrefresh',
					down : {
						callback : pulldownRefresh
					},
					up : {
						contentrefresh : '正在加载...',
						callback : pullupRefresh
					}
				},
			});
			var page = 1;
			var size = 5;

			/**
			 * 下拉刷新具体业务实现
			 */
			function pulldownRefresh() {
				mui('#pullrefresh').pullRefresh().endPulldownToRefresh();
				window.location.reload();
			}

			/**
			 * 上拉加载具体业务实现
			 */
			function pullupRefresh() {
			    if(type==1){
			    	getData(page, size);
				    page++;
			    }
			    if(type==2){
			       
			       searchOrder(textOrderNum,textOrderGoid);
			    }
			

			}

			if (mui.os.plus) {
				mui.plusReady(function() {
					setTimeout(function() {
						mui('#pullrefresh').pullRefresh().pullupLoading();
					}, 1000);

				});
			} else {
				mui.ready(function() {
					mui('#pullrefresh').pullRefresh().pullupLoading();

				});
			}
			
		    var $dlgOrderSearch = $('#dlgOrderSearch');
			var $androidMask = $dlgOrderSearch.find('.weui-mask');
			$("#btnSerach").on("click", function() {
			    if ($dlgOrderSearch.css('display') != 'none')return;
				    $dlgOrderSearch.fadeIn(100);
					$androidMask.on('click', function() {
					    $dlgOrderSearch.fadeOut(200);
				    });
				    
				    $('#btnOrderSearch').on('click', function() {
					  var textOrderNum = $('#textOrderNum').val();
					  var textOrderGoid = $('#textOrderGoid').val();
					  if(textOrderNum==''&&textOrderGoid=='') return  $dlgOrderSearch.fadeOut(200);
					  $('#orderMessage').empty();
					   searchOrder(textOrderNum,textOrderGoid);
					   $dlgOrderSearch.fadeOut(200);
				    });
				    
			}); 
		
			
			
			function getData(page, size) {
				mui.ajax('weChart.do?method=weChartOrderInfo', {
					data : {
						openid : openid,
						page : page,
						size : size,
					},
					type : 'post',//HTTP请求类型
					success : function(data) {
						var list = data.list;
						var num = data.count;
						mui.each(list, function(index, item) {
							$('#messageTemplate').tmpl(item).appendTo('#orderMessage');
							$('.myOrder').on('click touchend', function() {
								toOrder(this);
						 	});
							$('.driverEvaluation').on('click touchend',
									function() {
										toDriverEvaluation(this);
									});

						});
						mui('#pullrefresh').pullRefresh().endPullupToRefresh(
								((page * size) >= num)); //参数为true代表没有更多数据了。
						//服务器返回响应，根据响应结果，分析是否登录成功；
					},
					error : function(xhr, type, errorThrown) {
						//异常处理；
						console.log(type);
					}
				});
			}
			
			
	
		 /*    var $iosActionsheet = $('#iosActionsheet');
			var $iosMask = $('#iosMask');

			function hideActionSheet() {
				$iosActionsheet.removeClass('weui-actionsheet_toggle');
				$iosMask.fadeOut(200);
			}

			$iosMask.on('click', hideActionSheet);
			$('#iosActionsheetCancel').on('click', hideActionSheet);
			$("#btnSerach").on("click", function() {
				$iosActionsheet.addClass('weui-actionsheet_toggle');
				$iosMask.fadeIn(200);
			}); */

		});
		

		function searchOrder(textOrderNum, textOrderGoid) {
		 
			mui.ajax('weChart.do?method=weChartSearchOrderInfo', {
				data : {
					orderNum : textOrderNum,
					orderGoid : textOrderGoid
				},
				type : 'post',//HTTP请求类型
				success : function(data) {
				    mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
					var list = data.list;
					
					if(list.length==0) return $("#orderMessage").text("没有运单信息");
					//var num = data.count;
					mui.each(list, function(index, item) {
						$('#messageTemplate').tmpl(item).appendTo(
								'#orderMessage');
						$('.myOrder').on('click touchend', function() {
							toOrder(this);
						});
						$('.driverEvaluation').on('click touchend', function() {
							toDriverEvaluation(this);
						});
						
					});
					
					//服务器返回响应，根据响应结果，分析是否登录成功；
				},
				error : function(xhr, type, errorThrown) {
					//异常处理；
					console.log(type);
				}
			});

		}
		function toOrder(obj) {
			var orderid = obj.getAttribute('data-order');
			mui.openWindow({
				url : 'weChart.do?method=weChartGetOrderInfo&orderid='
						+ orderid,
				id : 'orderlist',
			});
		};
		function toDriverEvaluation(obj) {
			var orderid = obj.getAttribute('data-order');
			mui.openWindow({
				url : 'weChart.do?method=weChartDriverEvaluation&orderid='
						+ orderid,
				id : 'driverEvaluation',
			});
		}
	</script>
</body>
</html>
