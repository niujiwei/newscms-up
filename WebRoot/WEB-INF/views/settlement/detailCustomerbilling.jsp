<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>详细客户结算</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="./js/function.js"></script>
<%
	String send_time = (String) request.getAttribute("send_time");
%>
<%
	String end_time = (String) request.getAttribute("end_time");
%>
<%
	String custom_code = (String) request.getAttribute("custom_code");
%>
</head>
<script type="text/javascript">
	  	var dialog;
 		var grid;
 		var editval;
 		var heji=0;
  		$(function(){
  		    var send_time ='<%=send_time%>';
 		    var end_time = '<%=end_time%>';
 		    var custom_code='<%=custom_code%>';
		grid = $("#dg")
				.datagrid(
						{
							url : 'shipOrder.do?method=getDtailCustomerbilling&custom_code='
									+ custom_code
									+ '&send_time1='
									+ send_time
									+ '&end_time=' + end_time,
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
							rowStyler : function(index, row) {
								if (row.customer_settlement_state == 1) {
									return 'color:blue;';
								}
							},
							columns : [ [
							//{field : 'ck',checkbox : true},
							{
								field : 'custom_code',
								title : '客户编号',
								width : 60,
								align : 'center'
							}, {
								field : 'send_mechanism',
								title : '受理机构',
								width : 120,
								align : 'center'
							}, {
								field : 'custom_name',
								title : '发货客户名称',
								width : 140,
								align : 'center'
							}, {
								field : 'send_time',
								title : '起运时间',
								width : 80,
								align : 'center',
								sortable : 'true'
							}, {
								field : 'shiping_order_num',
								title : '货运编号',
								width : 80,
								align : 'center',
								sortable : 'true'
							}, {
								field : 'shiping_order_goid',
								title : '出货订单号',
								width : 80,
								align : 'center',
								sortable : 'true'
							}, {
								field : 'goods_name',
								title : '货物名称',
								width : 100,
								align : 'center'
							}, {
								field : 'goods_num',
								title : '件数',
								width : 100,
								align : 'center'
							}, {
								field : 'goods_weight',
								title : '重量',
								width : 100,
								align : 'center'
							}, {
								field : 'goods_vol',
								title : '体积',
								width : 100,
								align : 'center'
							}, {
								field : 'transport_pay',
								title : '运费',
								width : 100,
								align : 'center'
							}, {
								field : 'trade_agency',
								title : '代收货款',
								width : 100,
								align : 'center'
							}, {
								field : 'deliver_fee',
								title : '配送费',
								width : 100,
								align : 'center'
							}, {
								field : 'upstairs_fee',
								title : '上楼费',
								width : 100,
								align : 'center'
							}, {
								field : 'added_fee',
								title : '附加费',
								width : 100,
								align : 'center'
							}, {
								field : 'other_fee',
								title : '其他费用',
								width : 100,
								align : 'center'
							}, {
								field : 'remarks',
								title : '备注',
								width : 100,
								align : 'center'
							}, {
								field : 'shipping_order_state',
								title : '运单状态',
								width : 100,
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
										return "已返回";
									} else if (val == 6) {
										return "已收到";
									}
								}

							}, {
								field : 'end_address',
								title : '终到位置',
								width : 120,
								align : 'center'
							}, {
								field : 'end_mechanism',
								title : '收货客户地址',
								width : 120,
								align : 'center'
							}, {
								field : 'custom_code',
								title : '客户编号',
								width : 100,
								align : 'center'
							}, {
								field : 'custom_contact',
								title : '发货联系人',
								width : 100,
								align : 'center'
							}, {
								field : 'custom_tel',
								title : '发货客户电话',
								width : 100,
								align : 'center'
							}, {
								field : 'receipt',
								title : '收货客户名称',
								width : 100,
								align : 'center'
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
								field : 'customer_settlement_state',
								title : '结算状态',
								width : 100,
								align : 'center',
								formatter : function(val, row, index) {
									if (val == 0) {
										return "未结算";
									} else if (val == 1) {
										return "已结算";
									}
								}

							}, {
								field : 'is_recept',
								title : '回单份数',
								width : 100,
								align : 'center'
							},{
                                    field : 'remarks',
                                    title : '发货备注',
                                    width : 150,
                                    align : 'center'
							}/* {
								field : 'updatetime',
								title : '货运单票导入时间',
								width : 140,
								align : 'center',
								formatter : function(value, row, index) {
									if (value != undefined) {
										value = value.substr(0, 19);
									}
									return value;
								}
							}, {
								field : 'shipping_order',
								title : '制单人',
								width : 100,
								align : 'center'
							}*/

							] ],
							pagination : true,//分页
							pageSize : 10,
							pageList : [ 5, 10, 15, 20 ],
							toolbar : '#tbb'
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
					height : 92
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#end_address").val("");
				$("#receipt_name").val("");
				$("#receipt_tel").val("");
				$("#cn").select2("val", "");
				$("#custom_name").val("");

			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 60
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#end_address").val("");
				$("#receipt_name").val("");
				$("#receipt_tel").val("");
				$("#cn").select2("val", "");
				$("#custom_name").val("");
			}
		});
		//select框
		$("#cn").select2({ //自动完成
			placeholder : "请选择发货客户名称",
			minimumInputLength : 1,
			multiple : false,
			allowClear : true,
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "shipOrder.do?method=getCustomName",
					data : {
						name : query.term
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
						});
						query.callback(data);
					}
				});
			},
		}).on("select2-selecting", function(f) {
			$("#custom_name").val(f.object.text);
		});
		$("#customer_settlement_state").combobox("setValue", "");
		$("#customer_settlement_state").combobox("setText", "全部");
	});
	//查询
	function searchCar_owner() {
		var startDate = document.getElementById("startDate").value;
		$("#dg").datagrid('load', {
			send_time : startDate,
			//end_time:endDate,
			shiping_order_num : $.trim($("#ddId").val()),
			send_mechanism : $.trim($("#send_mechanism").val()),//受理机构
			end_address : $.trim($("#end_address").val()),//终到位置
			custom_name : $.trim($("#custom_name").val()),//发货客户名称
			receipt_name : $.trim($("#receipt_name").val()),//到货联系人
			receipt_tel : $.trim($("#receipt_tel").val()),//到货联系人电话
			shipperorder_id : $.trim($("#shipperorder_id").val()),//出货订单号
			customer_settlement_state: $.trim($("#customer_settlement_state").combobox("getValue")),//结算状态
		});
	}
	//重置
	function reset() {
		$("#startDate").val("");
		$("#ddId").val("");
		$("#send_mechanism").val("");
		$("#shipperorder_id").val("");
		$("#end_address").val("");
		$("#receipt_name").val("");
		$("#receipt_tel").val("");
		$("#cn").select2("val", "");
		$("#custom_name").val("");
		$("#customer_settlement_state").combobox("setValue", "");
		$("#customer_settlement_state").combobox("setText", "全部");
	}
</script>
<body class="easyui-layout">
	<div region="north" title="检索" class="st-north" border="false"
		height="60px" collapsible="false">
		<lable>起运日期:</lable>
		<input id="startDate" class="Wdate" readonly="readonly"
			style="width:150px;height:22px; "
			onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" />
		<!--  至<input  id="endDate" class="Wdate"  readonly="readonly" style="width:150px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})">-->
		<lable class="divlabel">货运编号:</lable>

		<input type="text" class="search-text" id="ddId" style="width:100px">
		<lable class="divlabel"> 出货订单号: </lable>
		<input type="text" class="search-text" id="shipperorder_id"
			style="width: 100px">

		<lable>受理机构:</lable>
		<input type="text" class="search-text" id="send_mechanism">
		<lable class="divlabel">结算状态:</lable>
				<select id='customer_settlement_state' class="easyui-combobox"
					name="customer_settlement_state" style="width:150px;height:22px"
					data-options="panelHeight : 'auto',editable:false">
					<option value="" selected="selected">全部</option>
					<option value="0">未接算</option>
					<option value="1">已结算</option>
		</select>
		 <a class="easyui-linkbutton" onclick="searchCar_owner()"
			data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
			class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
			href="javascript:void(0)" state="close">更多条件</a>
		<div id="searchInfoId" style="display: none">

			<lable>终到位置:</lable>
			<input type="text" class="search-text" style="width:150px"
				id="end_address"> <label class="divlabel"
				style="padding-left: 0px;">发货客户名称:</label> <input type="hidden"
				id="cn" style="width: 150px;" /> <input type="hidden"
				id="custom_name" name="custom_name"> <label class="divlabel">收货联系人:</label>
			<input type="text" class="search-text" style="width:150px"
				id="receipt_name"> <label class="divlabel">收货客户电话:</label>
			<input type="text" class="search-text" style="width:150px"
				id="receipt_tel">
		</div>
	</div>
	<div region="center">
		<table id="dg" title="运单信息"></table>
	</div>
	<div id="tbb">
		<!--    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"   onclick="add()">添加</a>  
   		      <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="xiangxixinxi()">详细</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" id="getUpdateCar_owner" data-options="iconCls:'icon-edit',plain:true"onclick="modify()">修改</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="deleteEver()">删除</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true"onclick="printYWM()">一维码打印</a>
		   	<a href="javascript:void(0)"	class="easyui-linkbutton" data-options="iconCls:'icon-input',plain:true" onclick="isArrive()" id="tbru" >到达</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" id="outputCar_owner" data-options="iconCls:'icon-output',plain:true" onclick="isNotArrive()">取消到达</a> -->
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-reset',plain:true" onclick="reset()">重置</a>
		
	</div>
</body>
</html>
