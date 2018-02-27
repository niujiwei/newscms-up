<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>已签收信息管理</title>
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
<script type="text/javascript" src="./js/function.js"></script>
<%
	String realPath1 = "http://" + request.getServerName() + ":"
			+ request.getServerPort() + "/truck_cloud/";
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
</head>
<script type="text/javascript">
  	var username='<%=user.getUsername()%>';
	var dialog;
	var grid;
	$(function() {
		
	grid = $("#dg")
				.datagrid(
						{
							url : 'shipOrder.do?method=getShipOrderOld',
							border : false,
							rownumbers : true,
							fit : true,
							singleSelect : false,
							selectOnCheck : true,
							checkOnSelect : true,
							toolbar : '#tb',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									//{field : 'shiping_order_num',title : '订单号',width : 70,align:'center'},
									{
										field : 'shiping_order_num',
										title : '货运编号',
										width : 60,
										align : 'center'
									},
									{
										field : 'send_mechanism',
										title : '受理机构',
										width : 120,
										align : 'center'
									},
									{
										field : 'shiping_order_goid',
										title : '出货订单号',
										width : 80,
										align : 'center'
									},
									{
										field : 'end_address',
										title : '终到位置',
										width : 120,
										align : 'center'
									},

									{
										field : 'shipping_order_state',
										title : '运单状态',
										width : 70,
										align : 'center',
										formatter : function(val, row, index) {
											if (val == 0) {
												return "已发运";
											} else if (val == 1) {
												return "已分配";
											} else if (val == 2) {
												return "已接单";
											} else if (val == 3) {
												return "电子围栏签收";
											} else if (val == 4) {
												return "已签收";
											} else if (val == 5) {
												return "已签收";
											} else if (val == 6) {
												return "已签收";
											}
										}
									},
									{
										field : 'shipping_order_statestr',
										title : '运单状态',
										hidden : true
									},
									{
										field : 'send_time',
										title : '起运时间',
										width : 80,
										align : 'center',
										sortable : 'true'
									},
									{
										field : 'sign_time',
										title : '签收日期',
										width : 120,
										align : 'center'
									},
									{
										field : 'imageUrl',
										title : '签收图片',
										width : 70,
										align : 'center',
										formatter : function(val, row, index) {
											if (row.shipping_order_state < 4) {
												return "运单未签收或无图片";
											} else {
												return "<a href='javascript:void(0)' style='color:red' title='点击查看签收图片' onclick=\"showimg('"
														+ row.shiping_order_id
														+ "')\">点击查看</a>";
											}
										}
									}, {
										field : 'receipt_name',
										title : '到货联系人',
										width : 100,
										align : 'center'
									}, {
										field : 'sign_user',
										title : '收货人',
										width : 70,
										align : 'center'
									}, {
										field : 'sign_usernumber',
										title : '收货人证件',
										width : 80,
										align : 'center'
									}, {
										field : 'sign_remarks',
										title : '签收备注',
										width : 70,
										align : 'center'
									}, {
										field : 'is_abnormal',
										title : '异常情况',
										width : 70,
										align : 'center',
										formatter : function(val, row, index) {
											if (row.is_abnormal == 0) {
												return "运单暂无异常情况";
											} else {
												return "<a href='javascript:void(0)' style='color:red' title='点击查看异常信息' onclick=\"showimg('"
														+ row.shiping_order_id
														+ "')\">点击查看</a>";
											}
										}
										
									}, {
										field : 'goods_num',
										title : '总件数',
										width : 100,
										align : 'center'
									},

									{
										field : 'sign_name',
										title : '收货代理人',
										width : 80,
										align : 'center'
									}, {
										field : 'sign_number',
										title : '收货代理人证件',
										width : 140,
										align : 'center'
									}, {
										field : 'sign_username',
										title : '签收经办人',
										width : 70,
										align : 'center',
										formatter : function(val, row, index) {
											if (val == null) {
												return "";
											}
										}
									},
							//{field : 'end_mechanism',title : '终到机构',width : 120,align:'center'},
							//{field : 'custom_code',title : '客户编号',width : 100,align:'center'},
							//{field : 'custom_name',title : '发货客户名称',width : 140,align:'center'},
							//{field : 'custom_contact',title : '发货客户联系人',width : 100,align:'center'},
							//	{field : 'custom_tel',title : '发货客户联系人',width : 100,align:'center'},
							//{field : 'receipt',title : '收货客户名称',width : 100,align:'center'},

							//{field : 'receipt_tel',title : '到货联系电话',width : 100,align:'center'},
							//{field : 'goods_name',title : '货物名称',width : 100,align:'center'},
							//{field : 'is_recept',title : '回单份数',width : 100,align:'center'},
							//{field : 'transport_pay',title : '总运费',width : 100,align:'center'},
							//{field : 'trade_agency',title : '代收货款',width : 100,align:'center'},
							//{field : 'goods_weight',title : '总重量',width : 100,align:'center'},
							//{field : 'goods_vol',title : '总体积',width : 100,align:'center'},

							] ],
							pagination : true,//分页
							pageSize : 10,
							pageList : [ 5, 10, 15, 20 ],
							toolbar : '#tb'
						});

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
					height : 92
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#custom_name").val("");
				$("#receipt").val("");
				$("#address").val("");
				$("#shipping_order_state").combobox("setValue", "");
				$("#shipping_order_state").combobox("setText", "全部");
			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 60
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#custom_name").val("");
				$("#receipt").val("");
				$("#address").val("");
				$("#shipping_order_state").combobox("setValue", "");
				$("#shipping_order_state").combobox("setText", "全部");
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
		/*
		select框
		 */

		/* $("#carid").select2({
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
		}); */
	});
	//删除
	function deleteEver() {
		var checkarray = [];
		var array = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < array.length; i++) {
			checkarray.push(array[i].shiping_order_id);
		}
		if (array != "") {
			$.messager.confirm('确认', '您确定要删除选中的记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : "shipOrder.do?method=deleteShipOrder",
						type : "POST",
						data : {
							del : checkarray.join(",")
						},
						success : function(data) {
							if (data.flag) {
								parent.$.messager.alert("运单信息删除", "运单信息删除成功",
										'info', function(r) {
											$('#dg').datagrid('reload');
										});
							}
						}
					});
				}
			});
		} else {
			$.messager.alert("运单签收", "请选择一行信息", "info");
		}
	}

	function showimg(obj) {
		dialog = parent.jy.modalDialog({
			title : '签收图片详情',
			url : 'shipOrder.do?method=getimgurl&iurl=' + obj,
			width : 680,
			buttons : [ {
				text : '',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.qs(dialog, grid,
							parent.$);
				}
			} ]
		});
	}
	/*
	修改查询 
	 */
	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			for ( var i = 0; i < row.length; i++) {
				var id = row[i].shiping_order_id;
			}
			dialog = parent.jy.modalDialog({
				title : '运单修改',
				url : 'shipOrder.do?method=getEditShipOrderPage&pid=' + id,
				width : 680,
				buttons : [ {
					text : '<input type="button" class="btncss" value="修改"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow
								.submitFormEdit(dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			$.messager.alert("运单签收", "请选择一行信息", "info");
		}
	}
	//签收
	function qianshou() {
		var row = $("#dg").datagrid('getSelections');
		var isok = 0;
		var id = 0;
		for ( var i = 0; i < row.length; i++) {
			id = row[i].shiping_order_id;
			isok = row[i].shipping_order_state;
		}
		if (row.length == 1) {
		   

			if (isok == 4 || isok == 5 || isok == 6) {
				dialog = parent.jy.modalDialog({
					title : '运单详情',
					url : 'shipOrder.do?method=getEditSignShipOrderPage&pid='
							+ id + '&isok=4',
					width : 680,
					buttons : [ {
						text : '',
						handler : function() {
							dialog.find('iframe').get(0).contentWindow.qs(
									dialog, grid, parent.$);
						}
					} ]
				});
			
		} else {

				if (row[0].is_abnormal == 0) {
				    $.messager.alert("运单签收", "此运单已经异常上报,请联系项目部进行处理之后在签收", 'info');
				} else {
					dialog = parent.jy
							.modalDialog({
								title : '运单详情',
								url : 'shipOrder.do?method=getEditSignShipOrderPage&pid='
										+ id + '&isok=0',
								width : 680,
								buttons : [ {
									text : '<input type="button" id="btncss"  class="btncss" value="签收"/>',
									handler : function() {
										dialog.find('iframe').get(0).contentWindow
												.qs(dialog, grid, parent.$);
									}
								} ]
							});
				}

			}
		} else {
			$.messager.alert("运单签收", '请选择一行信息', 'info');
		}
	}
	//修改
	function updateUser() {
		var myform = document.forms[1];
		myform.action = "car_owner.do?method=updateCar_owner";
		myform.submit();

	}
	//
	function addShipOrder() {
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

	//查询
	function searchCar_owner() {
		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;
		$("#dg").datagrid(
				'load',
				{
					start_date : startDate,
					end_date : endDate,
					name : $.trim($("#ddId").val()),
					shipping_order_state : $.trim($("#shipping_order_state")
							.combobox("getValue")),
					custom_name : $.trim($("#custom_name").val()),
					receipt : $.trim($("#receipt").val()),
					address : $.trim($("#address").val()),
					shipperorder_id : $.trim($("#shipperorder_id").val())
				//出货订单号
				});
	}

	//重置
	function reset() {
		$("#startDate").val("");
		$("#endDate").val("");
		$("#ddId").val("");
		$("#custom_name").val("");
		$("#receipt").val("");
		$("#address").val("");
		$("#shipperorder_id").val("");
		$("#shipping_order_state").combobox("setValue", "");
		$("#shipping_order_state").combobox("setText", "全部");
	}

	//查看订单信息
	function searchMsg() {
		var row = $("#dg").datagrid('getSelections');
		var isok = 0;
		var id = 0;
		for ( var i = 0; i < row.length; i++) {
			id = row[i].shiping_order_id;
			isok = row[i].shipping_order_state;
		}
		if (row.length == 1) {

			/* if (isok == 4 || isok == 5 || isok == 6) { */
			dialog = parent.jy.modalDialog({
				title : '运单详情',

				url : 'orderC.do?method=getShipOrderPage&pid='
						+ row[0].shiping_order_id + '&shiping_order_num='
						+ row[0].shiping_order_num,

				width : 1100,
				height : 550,
				buttons : [ {
					text : '',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.qs(dialog,
								grid, parent.$);
					}
				} ]
			});
			/* 	} else {
					dialog = parent.jy
							.modalDialog({
								title : '运单详情',
								url : 'shipOrder.do?method=getEditSignShipOrderPage&pid='
										+ id + '&isok=0',
								width : 680,
								buttons : [ {
									text : '<input type="button" id="btncss"  class="btncss" value="签收"/>',
									handler : function() {
										dialog.find('iframe').get(0).contentWindow
												.qs(dialog, grid, parent.$);
									}
								} ]
							});
				} */
		} else {
			$.messager.alert("运单签收", '请选择一行信息', 'info');
		}
	};

	//导出签收信息
	function putoutfile() {
		var allRows2 = $("#dg").datagrid("getColumnFields");
		var row = $("#dg").datagrid("getSelections");
		var colName = [];
		var fieldName = [];
		var dataIds = [];
		for (i = 1; i < allRows2.length; i++) {
			var col = $('#dg').datagrid("getColumnOption", allRows2[i]);

			if (col.field != "check_type" && col.field != "send_type"
					&& col.field != "shipping_order_state") {
				colName.push(col.title);//把title列名到数组里去 
				fieldName.push(col.field); //把field列属性名到数组里去 	
			}
		}
		for (i = 0; i < row.length; i++) {
			dataIds.push(row[i].shiping_order_id);
		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		$("#dataIds").val(dataIds.join(","));
		var outPutForm = $("#outputform");
		outPutForm.submit();
	}
</script>
<body class="easyui-layout">
	<form action="shipOrder.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			collapsible="false">
			<lable>起运日期:</lable>
			<input id="startDate" name="start_date" class="Wdate"
				readonly="readonly" style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer'})" /> - <input id="endDate"
				name="end_date" class="Wdate" readonly="readonly"
				style="width:150px;height:22px;"
				onfocus="WdatePicker({skin:'twoer'})" />
			<lable class="divlabel">货运编号:</lable>
			<input type="text" class="search-text" id="ddId" name="name"
				style="width:150px">
			<lable class="divlabel"> 出货订单号: </lable>
			<input type="text" class="search-text" id="shipperorder_id"
				style="width: 100px" name="shipperorder_id"><a
				class="easyui-linkbutton" onclick="searchCar_owner()"
				data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
				class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
				href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">
				<lable class="divlabel">运单状态:</lable>
				<select id='shipping_order_state' class="easyui-combobox"
					name="shipping_order_state" style="width:150px;height:22px"
					data-options="panelHeight : 'auto',editable:false">
					<option value="" selected="selected">全部</option>
					<option value="0">已发运</option>
					<option value="1">已分配</option>
					<option value="2">已接单</option>
					<option value="3">围栏签收</option>
					<option value="4">已签收</option>
				</select>
				<lable>受理机构:</lable>
				<input type="text" class="search-text" style="width:150px"
					id="custom_name" name="custom_name">
				<lable>到货联系人:</lable>
				<input type="text" class="search-text" style="width:150px"
					id="receipt" name="receipt">
				 <lable class="divlabel">终到位置:</lable>
				<input type="text" class="search-text" id="address" name="address">
			</div>
		</div>
		<div region="center">
			<table id="dg" title="运单信息"></table>
		</div>

		<div id="tb"></div>
		<input type="hidden" name="method" value="outSignShipOrderFile">
		<input type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle"> <input
			type="hidden" id="dataIds" name="dataIds">
	</form>
</body>
</html>