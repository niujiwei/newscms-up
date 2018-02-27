<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>已分配运单信息管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">


<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>


</head>
<%
	String flg = (String) request.getAttribute("flg");
%>
<script type="text/javascript">
	  	var dialog;
 		var grid;
 		var editval;
 		var pid='<%=flg%>';
	
	$(function() {
		grid = $("#dg").datagrid({
			url : 'shipOrder.do?method=getShipOrderOnePage&pid=' + pid,
			border : false,
			rownumbers : true,
			fit : true,
			/* sortName:'transport_pay,check_time,shipping_order',
			sortOrder:'desc', */
			singleSelect : false,
			selectOnCheck : true,
			checkOnSelect : true,
			multiSort : true,
			/* 	remoteSort:true, */
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'shiping_order_num',
				title : '货运编号',
				width : 60,
				align : 'center'
			}, {
				field : 'send_mechanism',
				title : '受理机构',
				width : 120,
				align : 'center'
			}, {
				field : 'shiping_order_goid',
				title : '出货订单号',
				width : 80,
				align : 'center'
			}, {
				field : 'send_station',
				title : '起运地',
				width : 120,
				align : 'center'
			}, {
				field : 'send_time',
				title : '起运时间',
				width : 80,
				align : 'center',
				sortable : 'true'
			}, {
				field : 'end_address',
				title : '终到位置',
				width : 120,
				align : 'center'
			}, {
				field : 'aging_time',
				title : '标准时效',
				width : 120,
				align : 'center'
			},{
				field : 'end_mechanism',
				title : '收货客户地址',
				width : 150,
				align : 'center'
			}, {
                field: 'receipt',
                title: '收货客户名称',
                width: 110,
                align: 'center'
            }, {
				field : 'receipt_name',
				title : '收货联系人',
				width : 100,
				align : 'center'
			}, {
				field : 'receipt_tel',
				title : '收货客户电话',
				width : 100,
				align : 'center'
			}, {
				field : 'goods_name',
				title : '货物名称',
				width : 100,
				align : 'center'
			}, {
				field : 'goods_num',
				title : '总件数',
				width : 100,
				align : 'center'
			}, {
				field : 'goods_weight',
				title : '总重量',
				width : 100,
				align : 'center'
			}, {
				field : 'goods_vol',
				title : '总体积',
				width : 100,
				align : 'center'
			},{
				field : 'is_recept',
				title : '回单份数',
				width : 100,
				align : 'center'
			}, {
				field : 'trade_agency',
				title : '代收货款',
				width : 100,
				align : 'center'
			}, {
				field : 'remarks',
				title : '发货备注',
				width : 150,
				align : 'center',

			}/* , {
				field : 'shipping_order',
				title : '制单人',
				width : 100,
				align : 'center'
			} */

			] ],

			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});
		//	回车查询
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchCar_owner();
			}
		};
		//能更多条件
		var btnMoreSearch = $("#btnMoreSearch");
		btnMoreSearch.click(function() {
			if (btnMoreSearch.attr("state") == "close") {
				//主要代码 
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 110
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#end_address").val("");
				$("#custom_name").val("");
				$("#receipt_name").val("");
				$("#receipt_tel").val("");

			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 60
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#end_address").val("");
				$("#custom_name").val("");
				$("#receipt_name").val("");
				$("#receipt_tel").val("");
			}
		});
		$('#cc3')
				.combotree(
						{
							url : "depn.do?method=getTree&&id=",
							width : 150,
							onBeforeLoad : function(node, param) {

								if (node == null) {
									$('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
								} else {

									$('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id="
											+ node.id;//加载下层节点
								}

							}
						});
		$("#carid").select2({
			placeholder : "请输入车主电话", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "shipOrder.do?method=getPhoneLength",
					data : {
						phones : query.term
					}, //传递你输入框中的值
					success : function(msg) {
						var msg = $.parseJSON(msg);
						var data = {
							results : []
						};
						$.each(msg, function(x, y) {
							data.results.push({
								id : y.id,
								text : y.name
							});
							;
						});
						query.callback(data);
					}
				});
			}
		});
	});

	//查询
	function searchCar_owner() {
		var startDate = document.getElementById("startDate").value;

		$("#dg").datagrid('load', {
			send_time : startDate,
			shiping_order_num : $.trim($("#ddId").val()),//货运编号
			send_mechanism : $.trim($("#send_mechanism").val()),//受理机构
			end_address : $.trim($("#end_address").val()),//终到位置
			custom_name : $.trim($("#custom_name").val()),//发货客户名称
			receipt_name : $.trim($("#receipt_name").val()),//到货联系人
			receipt_tel : $.trim($("#receipt_tel").val()),//到货联系人电话
			shipperorder_id : $.trim($("#shipperorder_id").val()),//出货订单号
			end_mechanism:$.trim($("#end_mechanism").val()),
			type:"0",// 是否显示签收(0 不显示,1显示)
		});

	}
	//删除
	function deleteShipOrder() {
		var checkarray = [];
		var num = 0;
		var array = $('#dg').datagrid('getSelections');
		if (array != "") {
			for ( var i = 0; i < array.length; i++) {
				//checkarray.push(array[i].shiping_order_id);
				checkarray.push(array[i].shiping_order_id);

			}
			if (checkarray.length > 0) {
				$.messager
						.confirm(
								'确认',
								'您选择的运单已经分配到对应的司机，您确定要取消这些分配吗？',
								function(r) {
									if (r) {
										$
												.ajax({
													url : "shipOrder.do?method=deleteShipOrderPage",
													type : "POST",
													data : {
														pid : pid,
														del : checkarray
																.join(",")
													},
													success : function(data) {
														if (data.flag) {
															parent.$.messager
																	.alert(
																			"运单信息删除",
																			"运单信息删除成功",
																			'info',
																			function(
																					r) {
																				$(
																						'#dg')
																						.datagrid(
																								'reload');
																			});
														}
													}
												});
									}
								});
			}
		} else {
			$.messager.alert("运单录入", "请选择一行信息", "info");
		}
	}
	function add() {
		/* alert(document.getElementById("addbuttons"));
		alert(document.getElementById("upbuttons")); */
		dialog = parent.jy.modalDialog({
			title : '运单新增',
			url : 'shipOrder.do?method=addShipOrder',
			/* 				 buttons :'#addbuttons'
			 */
			width : 680,
			buttons : [ {
				text : '<input type="button" class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	};

	//重置
	function reset() {
		$("#startDate").val("");
		$("#ddId").val("");
		$("#shipperorder_id").val("");
		$("#send_mechanism").val("");
		$("#end_address").val("");
		$("#custom_name").val("");
		$("#receipt_name").val("");
		$("#receipt_tel").val("");
		$("#end_mechanism").val("");
	}
</script>
<body class="easyui-layout">
	<form action="shipOrder.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			height="60px" collapsible="false">
			<lable>起运日期:</lable>
			<input id="startDate" class="Wdate" readonly="readonly"
				style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" />
			<lable class="divlabel">货运编号:</lable>
			<input type="text" class="search-text" id="ddId" style="width:100px">
			<lable class="divlabel"> 出货订单号: </lable>
			<input type="text" class="search-text" id="shipperorder_id"
				style="width: 100px">
			<lable>受理机构:</lable>
			<input type="text" class="search-text" id="send_mechanism"> <a
				class="easyui-linkbutton" onclick="searchCar_owner()"
				data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
				class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
				href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">
				<lable>终到位置:</lable>
				<input type="text" class="search-text" style="width:150px"
					id="end_address"> <%--<label class="divlabel"
					style="padding-left: 0px;">发货客户名称:</label> <input type="text"
					class="search-text" style="width:150px" id="custom_name">--%> <label
					class="divlabel">收货联系人:</label> <input type="text"
					class="search-text" style="width:100px" id="receipt_name">

				<label class="divlabel">收货客户电话:</label> <input type="text"
					class="search-text" style="width:100px" id="receipt_tel">
				<label >收货客户地址:</label> <input type="text"
											   class="search-text" style="width:150px" id="end_mechanism">
			</div>
			<%--	<div  style="padding:inherit">

			</div>--%>
		</div>
		<div region="center">
			<table id="dg" title="运单信息"></table>
		</div>
		<div id="tb">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true"
				onclick="deleteShipOrder()">删除</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-reset',plain:true" onclick="reset()">重置</a>
		</div>
		<input type="hidden" name="method" value="outShipOrder"> <input
			type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle">
	</form>
	<form action="shipOrder.do" target="_blank" id="printywm" method="post">
		<input type="hidden" name="method" value="getShipOrderPrintYwm">
		<input type="hidden" id="checkarray" name="checkarray">
	</form>
</body>
</html>