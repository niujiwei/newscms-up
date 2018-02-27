<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

 <% User user=(User)request.getSession().getAttribute(SessionInfo.SessionName); %>
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



</head>
<script type="text/javascript">
	var dialog;
	var grid;
	var editval;
	$(function() {
		grid = $("#dg").datagrid({
			url : 'abnormalreport.do?method=getShipOrder',
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
			},
			{
				field : 'end_address',
				title : '终到位置',
				width : 160,
				align : 'center'
			}, {
				field : 'end_mechanism',
				title : '收货客户地址',
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
				title : '发货联系人',
				width : 110,
				align : 'center'
			}, {
				field : 'custom_tel',
				title : '发货客户电话',
				width : 120,
				align : 'center'
			}, {
                    field: 'receipt',
                    title: '收货客户名称',
                    width: 110,
                    align: 'center'
				},{
				field : 'receipt_name',
				title : '收货联系人',
				width : 110,
				align : 'center'
			}, {
				field : 'receipt_tel',
				title : '收货客户电话',
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
			},{
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
			},{
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
				$("#topordernumber").val("");
				$("#downordernumber").val("");
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
				$("#topordernumber").val("");
				$("#downordernumber").val("");
				$("#receipt_name").val("");
				$("#receipt_tel").val("");
				$("#cn").select2("val", "");
				$("#custom_name").val("");
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
        var endDate = document.getElementById("endDate").value;
		$("#dg").datagrid('load', {
			send_time : startDate,
			end_time : endDate,
			shiping_order_num : $.trim($("#ddId").val()),
			send_mechanism : $.trim($("#send_mechanism").val()),//受理机构
			end_address : $.trim($("#end_address").val()),//终到位置
			custom_name : $.trim($("#custom_name").val()),//发货客户名称
			receipt_name : $.trim($("#receipt_name").val()),//到货联系人
			receipt_tel : $.trim($("#receipt_tel").val()),//到货联系人电话
			topordernumber : $.trim($("#topordernumber").val()),//上游订单
			downordernumber : $.trim($("#downordernumber").val()),//下游订单
			shipperorder_id : $.trim($("#shipperorder_id").val()),//出货订单号
		});

	}
	//异常反馈
	function addAbnormalFeedback() {
	var row = $("#dg").datagrid('getSelections');
	if (row.length == 1) {
	    var shiping_order_id =row[0].shiping_order_id;
        if(row[0].is_abnormal==1||row[0].is_abnormal==2) return $.messager.alert("异常反馈", "已经异常上报未处理", "info");
		dialog = parent.jy.modalDialog({
			title : '异常反馈',
			url : 'abnormalreport.do?method=addAbnormalReport&shiping_order_id='+shiping_order_id,
			width : 750,
			height : 600,
			buttons : [ {
				text : '<input type="button" class="btncss" value="上报"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ],
			onDestroy:function(){
                $("#dg").datagrid('reload');
            }
		});
	} else {
		$.messager.alert("异常反馈", "请选择一行信息", "info");
	}
	};
	
	
	
	//重置
	function reset() {
		$("#startDate").val("");
		$("#ddId").val("");
		$("#end_address").val("");
		$("#topordernumber").val("");
		$("#downordernumber").val("");
		$("#shipperorder_id").val("");
		$("#receipt_name").val("");
		$("#receipt_tel").val("");
		$("#send_mechanism").val("");
		$("#cn").select2("val", "");
		$("#custom_name").val("");
		$("#endDate").val("");
	}

	//查看订单
	function searchMsg() {
		var row = $("#dg").datagrid("getSelections");
		if (row.length == 1) {
			var pid = row[0].shiping_order_id;
			var shiping_order_num = row[0].shiping_order_num;
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
			$.messager.alert("运单录入", "请选择一行信息", "info");
		}
	}


</script>
<body class="easyui-layout">
	<form action="shipOrder.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			height="60px" collapsible="false">
			<lable>起运日期:</lable>
			<input id="startDate" name="send_time" class="Wdate" readonly="readonly"
				style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" /> - 
		    <input id="endDate" name="end_time" class="Wdate" readonly="readonly"
				style="width:150px;height:22px;"
				onfocus="WdatePicker({skin:'twoer'})" />
			<lable class="divlabel">货运编号:</lable>
			<input type="text" class="search-text" id="ddId" style="width:100px" name="shiping_order_num">
			<lable class="divlabel"> 出货订单号: </lable>
			<input type="text" class="search-text" id="shipperorder_id"
				style="width: 100px" name="shipperorder_id">
			<lable>受理机构:</lable>
			<input type="text" class="search-text" id="send_mechanism" name="send_mechanism"> <a
				class="easyui-linkbutton" onclick="searchCar_owner()"
				data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
				class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
				href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">
				<lable>终到位置:</lable>
				<input type="text" class="search-text" style="width:150px"
					id="end_address" name="end_address"> <label class="divlabel"
					style="padding-left: 0px;">发货客户名称:</label> <input type="hidden"
					id="cn" style="width: 150px;" /> <input type="hidden"
					id="custom_name" name="custom_name"> <label
					class="divlabel">收货联系人:</label> <input type="text"
					class="search-text" style="width:150px" id="receipt_name" name="receipt_name">

				<label class="divlabel">收货客户电话:</label> <input type="text"
					class="search-text" style="width:150px" id="receipt_tel" name="receipt_tel">
				<div id="" style="padding:inherit">
					<label class="divlabel">上游订单:</label> <input type="text"
						class="search-text" style="width:150px" id="topordernumber" name="topordernumber">
					<label class="divlabel">下游订单:</label> <input type="text"
						class="search-text" style="width:150px" id="downordernumber" name="downordernumber">
				</div>
			</div>
		</div>
		<div region="center">
			<table id="dg" title="运单信息"></table>
		</div>
		<div id="tb">
		   <a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true"
			onclick="addAbnormalFeedback()">异常反馈</a>
		</div>
		<input type="hidden" name="method" value="someMessageOutPut"
			id="method"> <input type="hidden" id="fieldName"
			name="fieldName"> <input type="hidden" id="headtitle"
			name="headtitle"> <input type="hidden" id="dataIds"
			name="dataIds">
	</form>
	<form action="shipOrder.do" target="_blank" id="printywm" method="post">
		<input type="hidden" name="method" value="getShipOrderPrintYwm">
		<input type="hidden" id="checkarray" name="checkarray">
	</form>
</body>
</html>