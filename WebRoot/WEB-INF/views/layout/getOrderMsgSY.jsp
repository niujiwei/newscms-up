<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>运单信息管理</title>
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
<!-- <script type="text/javascript" src="./js/function.js"></script>命名按钮方法  -->

<style type="text/css">
.div2 {
	height: 230px;
	margin: 0px;
}
</style>
</head>
<%
	String flg = (String) request.getAttribute("flg");
%>
<script type="text/javascript">
	  	var dialog;
 		var grid;
 		var pid='<%=flg%>';
	var inum = 0;
	$(function() {
		grid = $("#dg").datagrid(
				{
					url : 'shipOrder.do?method=getShipOrder',
					border : false,
					rownumbers : true,
					fit : true,
					/* sortName:'transport_pay,check_time,shipping_order',
					sortOrder:'desc', */
					singleSelect : true,
					selectOnCheck : true,
					checkOnSelect : true,
					multiSort : true,
					/* 	remoteSort:true, */
					onDblClickRow : function(rowIndex, rowData) {

						var pid = rowData.shiping_order_id;
						var shiping_order_num = rowData.shiping_order_num;

						dialog = parent.jy.modalDialog({
							title : '运单信息',
							url : 'orderC.do?method=getShipOrderPage&pid='
									+ pid + '&shiping_order_num='
									+ shiping_order_num,

							width : 1100,
							height : 550,
							buttons : [ {
								handler : function() {
									dialog.find('iframe').get(0).contentWindow
											.submitFormEdit(dialog, grid,
													parent.$);
								}
							} ]
						});
					},
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
					},

					{
						field : 'end_address',
						title : '终到位置',
						width : 160,
						align : 'center'
					}, {
						field : 'end_mechanism',
						title : '客户详细目的地',
						width : 160,
						align : 'center'
					}, {
						field : 'shiping_yueid',
						title : '月结编号',
						width : 100,
						align : 'center'
					}, {
						field : 'custom_name',
						title : '发货客户名称',
						width : 140,
						align : 'center'
					}, {
						field : 'custom_contact',
						title : '发货客户联系人',
						width : 110,
						align : 'center'
					}, {
						field : 'custom_tel',
						title : '发货客户联系电话',
						width : 120,
						align : 'center'
					},
					//{field : 'receipt',title : '收货客户名称',width : 100,align:'center'},
					{
						field : 'receipt_name',
						title : '到货联系人',
						width : 110,
						align : 'center'
					}, {
						field : 'receipt_tel',
						title : '到货联系电话',
						width : 120,
						align : 'center'
					}, {
						field : 'goods_name',
						title : '货物名称',
						width : 100,
						align : 'center'
					}, {
						field : 'is_recept',
						title : '回单份数',
						width : 100,
						align : 'center'
					},
					//{field : 'transport_pay',title : '总运费',width : 100,align:'center'},
					{
						field : 'trade_agency',
						title : '代收货款',
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
					},

					{
						field : 'topordernumber',
						title : '上游订单',
						width : 100,
						align : 'center'
					}, {
						field : 'downordernumber',
						title : '下游订单',
						width : 100,
						align : 'center'
					}, {
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
					} ] ],
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
					height : 92
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#receipt_name").val("");
				$("#receipt_tel").val("");
				$("#shipperorder_id").val("");
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
				$("#receipt_name").val("");
				$("#receipt_tel").val("");
				$("#shipperorder_id").val("");
				$("#cn").select2("val", "");
				$("#custom_name").val("");
			}
		});

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

	});

	//查询
	function searchCar_owner() {
		var startDate = document.getElementById("startDate").value;

		$("#dg").datagrid('load', {
			send_time : startDate,
			shiping_order_num : $.trim($("#ddId").val()),
			send_mechanism : $.trim($("#send_mechanism").val()),//受理机构
			end_address : $.trim($("#end_address").val()),//终到位置
			custom_name : $.trim($("#custom_name").val()),//发货客户名称
			receipt_name : $.trim($("#receipt_name").val()),//到货联系人
			receipt_tel : $.trim($("#receipt_tel").val()),//到货联系人电话
			shipperorder_id : $.trim($("#shipperorder_id").val())
		//出货订单号
		});
	}
	//查看详细
	function xiangxixinxi() {
		var data = $("#dg").datagrid("getSelections");
		
		if (data.length == 1) {
			var pid = data[0].shiping_order_id;
			var shiping_order_num = data[0].shiping_order_num;
			dialog = parent.jy.modalDialog({
				title : '运单信息',
				url : 'orderC.do?method=getShipOrderPage&pid=' + pid
						+ '&shiping_order_num=' + shiping_order_num,

				width : 1100,
				height : 550,
				buttons : [ {
					handler : function() {
						dialog.find('iframe').get(0).contentWindow
								.submitFormEdit(dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			alert('请选择一条信息');
		}

	}
	//重置
	function reset() {
		$("#startDate").val("");
		$("#ddId").val("");
		$("#send_mechanism").val("");
		$("#end_address").val("");

	}
</script>

<body class="easyui-layout">
	<form action="shipOrder.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			height="60px" collapsible="false">
			<lable> 起运日期: </lable>
			<input id="startDate" class="Wdate" readonly="readonly"
				style="width: 150px; height: 22px;"
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" />
			<lable class="divlabel"> 货运编号: </lable>
			<input type="text" class="search-text" id="ddId" style="width: 100px">
			<lable class="divlabel"> 出货订单号: </lable>
			<input type="text" class="search-text" id="shipperorder_id"
				style="width: 100px">
			<lable> 受理机构: </lable>
			<input type="text" class="search-text" id="send_mechanism">
			<lable> 终到位置: </lable>
			<input type="text" class="search-text" style="width: 150px"
				id="end_address"> <a class="easyui-linkbutton"
				onclick="searchCar_owner()" data-options="iconCls:'icon-search'">查询</a>
			<a id="btnMoreSearch" class="btn btn-sm btn-link" type="button"
				style="margin-top: 16px;" href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">

				<label class="divlabel" style="padding-left: 0px;"> 发货客户名称:
				</label> <input type="hidden" id="cn" style="width: 150px;" /> <input
					type="hidden" id="custom_name" name="custom_name"> <label
					class="divlabel"> 到货联系人: </label> <input type="text"
					class="search-text" style="width: 150px" id="receipt_name">

				<label class="divlabel"> 到货联系人电话: </label> <input type="text"
					class="search-text" style="width: 150px" id="receipt_tel">

			</div>
		</div>


		<div region="center">
			<table id="dg" title="运单信息"></table>
		</div>
		<div id="tb">
			<!--    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"   onclick="add()">添加</a>  
   		      <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="xiangxixinxi()">详细</a>
		    <a href="javascript:void(0)" class="easyui-linkbutton" id="getUpdateCar_owner" data-options="iconCls:'icon-edit',plain:true"onclick="modify()">修改</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="deleteEver()">删除</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-print',plain:true"onclick="printYWM()">一维码打印</a>
		   	<a href="javascript:void(0)"	class="easyui-linkbutton" data-options="iconCls:'icon-input',plain:true" onclick="isArrive()" id="tbru" >到达</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" id="outputCar_owner" data-options="iconCls:'icon-output',plain:true" onclick="isNotArrive()">取消到达</a> -->
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true"
				onclick="xiangxixinxi()">查看详细</a> <a href="javascript:void(0)"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-reset',plain:true" onclick="reset()">重置</a>
		</div>
	</form>

</body>
</html>