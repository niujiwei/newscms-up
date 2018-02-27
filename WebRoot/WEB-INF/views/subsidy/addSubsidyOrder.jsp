<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

 <% User user=(User)request.getSession().getAttribute(SessionInfo.SessionName); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>补助申请查询运单信息</title>
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
	<script type="text/javascript" src="./js/dateUtils.js"></script>



</head>
<script type="text/javascript">
	var dialog;
	var grid;
	var editval;
	$(function() {
        var begin=new Date();
        var end= new Date();
        begin.setMonth((new Date().getMonth()-1),26);
        end.setDate(25);
		grid = $("#dg").datagrid({
			url : 'subsidyInfo.do?method=getAddSubsidyInfo',
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
				field : 'goods_num',
				title : '总件数',
				width : 100,
				align : 'center'
			}

			] ],
            queryParams : {
                //shipping_order_state : type,
                send_time : begin.Format("yyyy-MM-dd"),
                end_time : end.Format("yyyy-MM-dd")
            },
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
        var begin=new Date();
        var end= new Date();
        begin.setMonth((new Date().getMonth()-1),26);
        end.setDate(25);
		$("#dg").datagrid('load', {
			send_time :  begin.Format("yyyy-MM-dd"),
			end_time : end.Format("yyyy-MM-dd"),
			shiping_order_num : $.trim($("#ddId").val()),
            shipperorder_id : $.trim($("#shipperorder_id").val()),//出货订单号*/
			send_mechanism : $.trim($("#send_mechanism").val()),//受理机构
			end_address : $.trim($("#end_address").val())//终到位置
            /*receipt_name : $.trim($("#receipt_name").val()),//到货联系人
            end_mechanism: $.trim($("#end_mechanism").val())*//*custom_name : $.trim($("#custom_name").val()),//发货客户名称

			receipt_tel : $.trim($("#receipt_tel").val()),//到货联系人电话
			topordernumber : $.trim($("#topordernumber").val()),//上游订单
			downordernumber : $.trim($("#downordernumber").val()),//下游订单
			shipperorder_id : $.trim($("#shipperorder_id").val()),//出货订单号*/
		});

	}
	//
	function addSubsidyInfo() {
	var row = $("#dg").datagrid('getSelections');
	if (row.length != 1) return $.messager.alert("补助申请", "请选择一行信息", "info");
	//if (row[0].waybill_state=='1') return $.messager.alert("补助申请", "该运单已经进行了调整申请，请等待处理", "info");
	    var shiping_order_id =row[0].shiping_order_id;
		dialog = parent.jy.modalDialog({
			title : '补助申请',
			url : 'subsidyInfo.do?method=toAddSubsidyInfo&shiping_order_id='+shiping_order_id,
			width : 750,
			height : 600,
			buttons : [ {
				text : '<input type="button" class="btncss" value="申请"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ],
			onDestroy:function(){
                $("#dg").datagrid('reload');
            }
		});
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
			<%--<lable>起运日期:</lable>
			<input id="startDate" name="send_time" class="Wdate" readonly="readonly"
				style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" /> - 
		    <input id="endDate" name="end_time" class="Wdate" readonly="readonly"
				style="width:150px;height:22px;"
				onfocus="WdatePicker({skin:'twoer'})" />--%>
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
					id="end_address" name="end_address">
				<%--label class="divlabel">收货联系人:</label>
				<input type="text" class="search-text" style="width:150px"
					   id="receipt_name" name="receipt_name">
				<label class="divlabel">收货客户地址:</label>
				<input type="text"
					   class="search-text" style="width:150px" id="end_mechanism"
					   name="end_mechanism">--%>
			</div>
		</div>
		<div region="center">
			<table id="dg" title="运单信息"></table>
		</div>
		<div id="tb">
		   <a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true"
			onclick="addSubsidyInfo()">补助申请添加</a>
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