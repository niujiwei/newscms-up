<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="/inc/incbootstrap.jsp"></jsp:include>
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="./js/select2/select2_expand.js"></script>
<script type="text/javascript" src="./js/function.js"></script>
<title></title>
</head>
<%
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
	String orders = (String)request.getAttribute("order");
%>

<script type="text/javascript">
	var dialog;
	var grid;
	var orders ='<%=orders%>';
	$(function() {
		//var da = "";
		/* var fs = $("#functionname").val().split(",");
		for ( var i = 0; i < fs.length; i++) {
			if (fs[i] == "添加") {
				$("#tbadd").removeAttr("style");
			} else if (fs[i] == "修改") {
				$("#tbmodify").removeAttr("style");
			} else if (fs[i] == "删除") {
				$("#tbdel").removeAttr("style");
			} else if (fs[i] == "导出") {
				$("#outputTimes").removeAttr("style");
			}

		} */
		var btnMoreSearch = $("#btnMoreSearch");
		btnMoreSearch.click(function() {
			if (btnMoreSearch.attr("state") == "close") {
				//主要代码 
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 85
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#sendMession").val("");
				$("#startDate").val("");
				$("#endDate").val("");
			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 61
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#sendMession").val("");
				$("#startDate").val("");
				$("#endDate").val("");
			}
		});

		grid = $("#dg").datagrid({
			url : 'shippingTimes.do?method=getshippingTimes',
			border : false,
			rownumbers : true,
			fit : true,
			singleSelect : false,
			selectOnCheck : true,
			checkOnSelect : true,
			multiSort : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'shiping_order_num',
				title : '货运编号',
				width : 100,
				align : 'center'

			}, {
				field : 'shiping_order_goid',
				title : '出货订单号编号',
				width : 100,
				align : 'center'

			}, {
				field : 'send_mechanism',
				title : '受理机构',
				width : 150,
				align : 'center'
			}, {
				field : 'end_address',
				title : '终到位置',
				width : 150,
				align : 'center'
			}, {
				field : 'receipt_name',
				title : '收货联系人',
				width : 150,
				align : 'center'
			}, {
				field : 'updatetime',
				title : '运单录入时间',
				width : 140,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			}, {
				field : 'receivetime',
				title : '司机接单时间',
				width : 140,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			}, {
				field : 'auto_sign_time',
				title : '围栏签收时间',
				width : 140,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			}, {
				field : 'sign_time',
				title : '运单签收时间',
				width : 140,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			},
			/* {field : 'suppliersProve',title : '资质',width : 140,align:'center'}, */
			{
				field : 'aging_time',
				title : '标准时效',
				width : 140,
				align : 'center'
			}, {
				field : 'result',
				title : '结果',
				width : 140,
				align : 'center',

			}, {
				field : 'sign_remarks',
				title : '签收备注',
				width : 150,
				align : 'center',

			}, {
				field : 'abnormal_name',
				title : '异常情况',
				width : 250,
				align : 'center',

			} ] ],
			rowStyler : function(rowIndex, rowData) {
				//任务完成100%， 并且已审核通过，背景色置灰  
				if (rowData.result != "正常配送") {
					return 'color:blue';
				}
			},
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});
		dropOrder();
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchRemark();
			}
		};
	});
	/* 	function formatDate(date, format) {
	 if (!date)
	 return;
	 if (!format)
	 format = "yyyy-MM-dd";
	 switch (typeof date) {
	 case "string":
	 date = new Date(date.replace(/-/, "/"));
	 break;
	 case "number":
	 date = new Date(date);
	 break;
	 }
	 if (!date instanceof Date)
	 return;
	 var dict = {
	 "yyyy" : date.getFullYear(),
	 "M" : date.getMonth() + 1,
	 "d" : date.getDate(),
	 "H" : date.getHours(),
	 "m" : date.getMinutes(),
	 "s" : date.getSeconds(),
	 "MM" : ("" + (date.getMonth() + 101)).substr(1),
	 "dd" : ("" + (date.getDate() + 100)).substr(1),
	 "HH" : ("" + (date.getHours() + 100)).substr(1),
	 "mm" : ("" + (date.getMinutes() + 100)).substr(1),
	 "ss" : ("" + (date.getSeconds() + 100)).substr(1)
	 };
	 return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {
	 return dict[arguments[0]];
	 });
	 } */
	 function dropOrder() {
		if (orders == "null")
			return;
		var data = orders.split(",");
		var shiping_order_num = data[0];
		var shiping_order_goid = data[1];
		$("#orderId").val(shiping_order_num);
		$("#shiping_order_goid").val(shiping_order_goid);
		searchRemark();

	}
	 
	function searchRemark() {
		$('#dg').datagrid('load', {
			orderId : $.trim($("#orderId").val()),
			orderAddress : $.trim($("#orderAddress").val()),
			shiping_order_goid : $.trim($("#shiping_order_goid").val()),
			startDate : $.trim($("#startDate").val()),
			endDate : $.trim($("#endDate").val()),
			sendMession : $.trim($("#sendMession").val()),
			ship_state : $.trim($("#ship_state").combobox("getValue")),

		});
	}

	/* function add() {
		dialog = parent.jy.modalDialog({
			title : '客户新增',
			url : 'suppliers.do?method=addSuppliers',
			width : 800,
			height : 500,
			buttons : [ {
				text : '<input type="button" class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	}

	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var remarkid = row[0].suppliersId;
			dialog = parent.jy.modalDialog({
				title : '供应商修改',
				url : 'suppliers.do?method=toUpdateSuppliers&cid=' + remarkid,
				width : 800,
				height : 600,
				buttons : [ {
					text : '<input type="button" class="btncss" value="保存"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow
								.submitFormEdit(dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			$.messager.alert('修改供应商', '请选择一行!', 'info');
		}
	}

	function deleteRemark() {
		var row = $("#dg").datagrid('getSelections');
		var array = [];
		for ( var i = 0; i < row.length; i++) {
			if (row[i].suppliersId != 0) {
				array.push(row[i].suppliersId);
			}
		}
		if (row.length > 0) {
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							$.ajax({
								type : "POST",
								async : false,
								url : 'suppliers.do?method=deletesuppliers',
								data : {
									pid : array.join(",")
								},
								success : function(data) {
									if (data.flag) {
										$.messager.alert("删除供应商", "供应商删除成功!",
												'info');
										$("#dg").datagrid('reload');
									} else {
										$.messager.alert("删除供应商", "供应商删除失败!",
												"info");
									}
								}
							});
						}
					});
		} else {
			alert("请选择数据");
		}
	} */
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
		var outputform = $("#outputform");
		outputform.submit();
	}
	//重置
	function reset() {
		$("#orderId").val("");
		$("#orderAddress").val("");
		$("#shiping_order_goid").val("");
		$("#sendMession").val("");
		$("#startDate").val("");
		$("#endDate").val("");
		$("#ship_state").combobox("setValue", "");
		$("#ship_state").combobox("setText", "全部");
	}
</script>
<body class="easyui-layout">
	<form action="shippingTimes.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			height="60px" collapsible="false">
			<lable class="divlabel">货运编号：</lable>
			<input type="text" id="orderId" name="orderId" style="width:150px" />
			<lable class="divlabel">出货订单号：</lable>
			<input type="text" id="shiping_order_goid" name="shiping_order_goid"
				style="width:150px" />
			<lable class="divlabel">终到位置：</lable>
			<input type="text" id="orderAddress" name="orderAddress"
				style="width:150px;" />
			<lable class="divlabel">时效结果:</lable>
			<select id='ship_state' name="ship_state" class="easyui-combobox"
				name="ship_state" style="width:150px;height:22px"
				data-options="panelHeight : 'auto',editable:false">
				<option value="" selected="selected">全部</option>
				<option value="0">正常配送</option>
				<option value="1">延时配送</option>
			</select> <a class="easyui-linkbutton" onclick="searchRemark()"
				data-options="iconCls:'icon-search'" id="searchId">查询</a> <a
				id="btnMoreSearch" class="btn btn-sm btn-link" type="button"
				style="margin-top:0px;" href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">
				<lable class="divlabel">受理机构：</lable>
				<input type="text" id="sendMession" name="sendMession"
					style="width:150px;" />
				<lable>签收开始日期:</lable>
				<input id="startDate" name="startDate" class="Wdate"
					readonly="readonly" style="width:150px;height:22px; "
					onfocus="WdatePicker({skin:'twoer'})" /> <input id="endDate"
					name="endDate" class="Wdate" readonly="readonly"
					style="width:150px;height:22px; "
					onfocus="WdatePicker({skin:'twoer'})" />&nbsp;
			</div>
		</div>
		<div region="center" title="时效管理" region="center"
			style="background: #eee; overflow-y:hidden">
			<table id="dg"></table>
		</div>
		<div id="tb">
		<!-- 	<a href="javascript:void(0)" class="easyui-linkbutton"
				id="outputTimes" data-options="iconCls:'icon-output',plain:true"
				onclick="putoutfile()">导出</a> <a href="#" class="easyui-linkbutton"
				id="tbreset" onclick="reset()"
				data-options="iconCls:'icon-reset',plain:true">重置</a> -->
		</div>
		<input type="hidden" name="method" value="outTimesFile"> <input
			type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle"> <input
			type="hidden" id="dataIds" name="dataId">
	</form>
	<input id="functionname" type="hidden" value=${function}>
</body>
</html>
