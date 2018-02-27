<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.model.Agreement"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>欢迎使用</title>

<link rel="stylesheet" href="./css/light-theme.css" type="text/css"></link>
<link rel="stylesheet" href="./css/style.css" type="text/css"></link>

</head>
<body style="overflow: hidden;">

	<div class='row-fluid'>

		<div class='span6 box'>

			<div class='box-header'>

				<div class='title'>

					<div class='icon-inbox'></div>

					订单信息

				</div>

				<div class='actions'>

					<a href="#" class="btn box-remove btn-mini btn-link"><i
						class='icon-remove'></i> </a> <a href="#"
						class="btn box-collapse btn-mini btn-link"><i></i> </a>

				</div>

			</div>

			<div class='row-fluid'>

				<div class='span6'>

					<div class='box-content box-statistic'>

						<small><a href="#" myAttr="shiping_order" myOrder="day" 
							onclick="openTab(this)">当日录入单量</a> </small>

						<h3 class='title text-error' id="shiping_order_day">0</h3>

						<div class='text-error icon-inbox align-right'></div>

					</div>

					<div class='box-content box-statistic'>

						<small><a href="#" myAttr="allocation_order"  myOrder="month,no" onclick="openTab(this)">当月未签收量</a> </small>

						<h3 class='title text-warning' id="drivertoorder_day">0</h3>

						<div class='text-warning icon-time align-right'></div>

					</div>

					<div class='box-content box-statistic'>

						<small><a href="#" myAttr="sign_order" myOrder="daySign,yes" onclick="openTab(this)">当日签收量</a> </small>

						<h3 class='title text-info' id="sign_order_day">0</h3>

						<div class='text-info icon-check align-right'></div>

					</div>

				</div>

				<div class='span6'>
					<div class='box-content box-statistic red-background'>

						<small><a href="#" myAttr="shiping_order" myOrder="month" onclick="openTab(this)">当月录入单量</a> </small>

						<h3 class='title text-primary' id="shiping_order_month">0</h3>

						<div class='text-primary icon-truck align-right'></div>

					</div>

					<div class='box-content box-statistic green-background'>

						<small><a href="#" myAttr="sign_order" myOrder="month,yes" onclick="openTab(this)">当月签收量</a> </small>

						<h3 class='title text-success' id="sign_order_month">0</h3>

						<div class='text-success icon-flag align-right'></div>

					</div>

					<div class='box-content box-statistic muted-background'>

						<h3 class='title muted'>0</h3>
						<h3 id="abnormalReportCount" style="display: none">0</h3>
						<h3 id='shippingTime' style="display: none">0</h3>

						<small>待开发中</small>

						<div class='muted  icon-question-sign align-right'></div>

					</div>

				</div>
				<!-- 配送时效信息 -->
				<div class='span11'>
					<div class='box-header'>

						<div class='title'>

							<div class='icon-inbox'></div>

							配送时效信息

						</div>

						<div class='actions'>
							<div id="shipp_timePagination" class="pagination">
								<!-- 这里显示分页 -->
							</div>
						</div>

					</div>
					<div class="span6">
						<div id="pj-lunbo">
							<div class="pj-Carousel">
								<div class="pj-Carousel-box" id="ca_box_info"></div>
								<!--状态-->
								<div class="pj-Carousel-active"></div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>

		<!-- 右侧问题件信息 -->
		<div class='span6 box'>
			<div class='box-header'>

				<div class='title'>

					<div class='icon-comments'></div>

					问题件信息

				</div>

				<div class='actions'>
					<div id="Pagination" class="pagination">
						<!-- 这里显示分页 -->
					</div>

				</div>

			</div>
			<div class='box-content box-statistic'>
				<div class="list_lh">
					<ul id="ul_list">
					</ul>
				</div>
			</div>

		</div>
	</div>
	<script type="text/javascript" src="./js/jquery.js"></script>
	<script type="text/javascript" src="./js/scroll.js"></script>
	<script type="text/javascript" src="./js/jquery.pj.js"></script>
	<script type="text/javascript" src="./js/jquery.pagination.js"></script>
	<script type="text/javascript" src="./js/dateUtils.js"></script>
	<script type="text/javascript">
	
	 $(function() { 
	     remainTime();
	 }); 
		function remainTime() {
			var now = new Date().Format("yyyy-MM-dd");
			var date = new Date(new Date().getTime() - 1 * 24 * 3600 * 1000)
					.Format("yyyy-MM-dd");
			$.ajax({
				type : "POST",
				async : true,
				url : 'login.do?method=getOrderCountMessage',
				data : {
					result : '延迟',
					abnormal_result : '0',
					startDate : date,
				},
				success : function(data) {
					$.each(data, function(n, value) {
						if (value) {
							$("#" + value.type).text(value.number);
						}
					});
					createMessage();
				}
			});
			
		}

		function createMessage() {
			var abCount = $("#abnormalReportCount").text();
			var shppTime = $("#shippingTime").text();
			var timesclea = null;
			var initPagination = function() {
				// 创建分页
				$("#Pagination").pagination(abCount, {//异常信息的分页
					num_edge_entries : 2, //边缘页数
					num_display_entries : 2, //主体页数
					callback : pageselectCallback,
					items_per_page : 20
				//每页显示1项
				});
				$("#shipp_timePagination").pagination(shppTime, {//时效分页
					num_edge_entries : 2, //边缘页数
					num_display_entries : 2, //主体页数
					callback : shipptimepageselectCallback,
					items_per_page : 10
				//每页显示1项
				});

			}();

			$("div.list_lh").myScroll({
				speed : 40, //数值越大，速度越慢
				rowHeight : 280
			//li的高度
			});
			function pageselectCallback(page_index, jq) {
				var row = this.items_per_page;
				var page = page_index;
				getAbnormalReportInfo(row, page);
				return false;
			}
			function shipptimepageselectCallback(page_index, jq) {
				clearInterval(timesclea); //清楚定时器
				var row = this.items_per_page;
				var page = page_index;
				timesclea = getShippingTimes(row, page);
				return false;
			}
		};

		// 异常上报信息获取
		function getAbnormalReportInfo(row, page) {
		
			$.ajax({
				type : "POST",
				async : true,
				url : 'abnormalreport.do?method=getAbnromalReportInfo',
				data : {
					rows : row,
					page : page + 1,
					abnormal_result : '0',

				},
				success : function(data) {
					$('#ul_list li').remove();
					$.each(data.rows, function(n, value) {
						if (value && value.shippingOrder) {
							createDom(value.shippingOrder.shiping_order_num,
									value.shippingOrder.shiping_order_goid,
									value.shippingOrder.end_address,
									value.shippingOrder.send_mechanism,
									value.abnormal_message,
									value.abnormal_name, value.abnormal_time);
						}
					});
				}

			});
			$('.list_lh li:even').addClass('lieven');

		};
		//时效信息数据
		function getShippingTimes(row, page) {
            var times = null;
            var date = new Date(new Date().getTime() - 1 * 24 * 3600 * 1000)
					.Format("yyyy-MM-dd");
			$.ajax({
				type : "POST",
				async : true,
				url : 'shippingTimes.do?method=getshippingTimes',
				data : {
					ship_state : '1',
					/* rows : row,
					pages : page + 1 */
					rows : '10',
					page : '1',
					startDate : date,
				},
				success : function(data) {
					$('#ca_box_info').empty();
					$('.pj-Carousel-active').empty();
					$.each(data.rows, function(n, value) {
						if (value) {
							createDom2(value.shiping_order_num,
									value.shiping_order_goid,
									value.end_address, value.send_mechanism,
									value.aging_time, value.sign_time,
									value.result);
						}
					});
					$(".pj-Carousel-active").siblings(".pj-Carousel-color")
							.removeClass("active");
					times = $("#pj-lunbo").Carousel({
						'play' : 'true', //是否循环播放
						'prevButton' : '#prev', //左按钮
						'nextButton' : '#next', //右按钮
						'auto' : 'true', //是否自动播放
						'playTimer' : 3500,
					});
				}
			});
			return times;
		}
		//创建异常信息dom
		function createDom(shiping_order_num, shiping_order_goid, end_address,
				send_mechanism, abnormal_message, abnormal_name, abnormal_time) {
			var str = '<li>'
					+ '<table style="width: 100%; table-layout:fixed;" onclick="openTab(this)" myAttr="abnormal_order" myOrder='
			        +shiping_order_num
			        +','
			        +shiping_order_goid
			        +'>'
					+ '<tr>' + '<td>货运编号：'
					+ shiping_order_num
					+ '</td>'
					+ '<td align="center">出货订单号:'
					+ shiping_order_goid
					+ '</td>'
					+ '<td align="right">状态</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td>终到位置:'
					+ end_address
					+ '</td>'
					+ '<td align="center">受理机构：'
					+ send_mechanism
					+ '</td>'
					+ '<td align="right">未处理</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td style="word-break: break-all; word-wrap:break-word;">上报原因：'
					+ abnormal_message
					+ '</td>'
					+ '<td align="center">上报人：'
					+ abnormal_name
					+ '</td>'
					+ '<td align="right">'
					+ abnormal_time + '</td>' + '</tr>' + '</table></li>';
			$("#ul_list").append($(str));
		};
		//创建时效信息dom
		function createDom2(shiping_order_num, shiping_order_goid, end_address,
				send_mechanism, aging_time, sign_time, result) {
			var str = '<div class="pj-Carousel-item" onclick="openTab(this)" myAttr="shipping_time" myOrder='
			        +shiping_order_num
			        +','
			        +shiping_order_goid
			        +'>'
					+ '<div class="box-content box-statistic">'
					+ '<div class="pad_class">'
					+ '<small class="pad_class">货运编号:'
					+ shiping_order_num
					+ '</small>'
					+ '<small class="pad_class">出货订单号:'
					+ shiping_order_goid
					+ '</small></div>'
					+ '<div class="pad_class">'
					+ '<small class="pad_class">受理机构:'
					+ send_mechanism
					+ '</small>'
					+ ' <small class="pad_class">终到位置：'
					+ end_address
					+ '</small>'
					+ '</div>'
					+ '<div class="pad_class">'
					+ '<small class="pad_class">签收时间:'
					+ sign_time
					+ '</small>'
					+ '<small class="pad_class">标准时效:'
					+ aging_time
					+ '</small>'
					+ '</div>'
					+ '<h3 class="title text-error">延时配送：'
					+ result
					+ '</h3>'
					+ '<div class="text-error icon-warning-sign align-right"></div>'
					+ '</div></div></div>';
			$("#ca_box_info").append($(str));

		};
		function openTab(obj) {
			var type = $(obj).attr("myAttr");
			var order = $(obj).attr("myOrder");
			var web = getWeb(type);
			if (web) {
				if (web.title) {
				$.ajax({
				type : "POST",
				async : true,
				url : 'login.do?method=centerAddTabInfo',
				data : {
					tittle :web.title,
				},
				success : function(data) {
				    
				     if(data){
				     var menu = data.menu[0];
				     if(menu){
				         var url = menu.pageurl+"&order="+order+"&menu_id="+menu.id;
				         var title = menu.text;
				          parent.addTab(title,url);
				       }
				     }
				 }
				});
				    
					
				}

			}

		}
		function getWeb(type) {//shiping_order 运单录入 //allocation_order 运单分配   sign_order 运单签收 shipping_time 配送时效
			var web = new Object();
			switch (type) {
			case 'shiping_order':
				web.title = '运单录入';
				//web.url = 'orderC.do?method=getShipOrderInfo&menu_id=110';
				break;
			case 'allocation_order':
				web.title = '运单签收';
				//web.url = 'driver.do?method=getDriverInfoNews&menu_id=210';
				break;
			case 'sign_order':
				web.title = '运单签收';
				//web.url = 'shipOrder.do?method=getSignShipOrderInfo&menu_id=218';
				break;
			case 'shipping_time':
				web.title = '配送时效';
				//web.url = 'shippingTimes.do?method=shippingTimes&menu_id=196';
				break;
			case 'abnormal_order':
				web.title = '异常反馈';
				//web.url = 'abnormalreport.do?method=abnormalReportInfo&menu_id=212';
				break;
			default:
				alert('你点哪里呢？');
			}
			return web;
		};
	</script>
</body>
</html>
