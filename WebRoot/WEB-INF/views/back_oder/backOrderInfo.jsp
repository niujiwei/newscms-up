<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>回单信息管理</title>
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
<!--命名按钮方法  -->


</head>
<script type="text/javascript">
	var dialog;
	var grid;
	$(function() {
		grid = $("#dg").datagrid({
			url : 'ChangeO.do?method=getBackOrderInfo',
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
				if (row.is_receive == 1) {
					return 'color:blue;';
				} else if (row.is_receive == 0) {
					return 'color:black;';
				}
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
				field : 'end_address',
				title : '终到位置',
				width : 120,
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
				field : 'is_recept',
				title : '回单份数',
				width : 80,
				align : 'center',
				sortable : 'true'
			}, {
				field : 'shipping_order_state',
				title : '回单状态',
				width : 80,
				align : 'center',
				formatter : function(val, row, index) {
					if (val == 0) {
						return "已发运";
					} else if (val == 1) {
						return "已分配";
					} else if (val == 2) {
						return "已接受";
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
				field : 'shipping_order_statestr',
				title : '回单状态',
				hidden : true
			}, {
				field : 'send_time',
				title : '起运时间',
				width : 120,
				align : 'center',
				sortable : 'true',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			}, {
				field : 'is_recept_time',
				title : '回单返回日期',
				width : 120,
				align : 'center',
				sortable : 'true',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			}, {
				field : 'is_receive',
				title : '是否接收',
				width : 80,
				align : 'center',
				formatter : function(val, row, index) {
					if (val == 0 || val == null) {
						return "未接收";
					} else if (val == 1) {
						return "已接收";
					}
				}
			}, {
				field : 'other_recept_time',
				title : '接收日期',
				width : 120,
				align : 'center',
				sortable : 'true',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			},

			{
				field : 'end_mechanism',
				title : '收货客户地址',
				width : 140,
				align : 'center'
			}, {
				field : 'goods_name',
				title : '货物名称',
				width : 100,
				align : 'center'
			},
			//{field : 'custom_code',title : '客户编号',width : 100,align:'center'},
			//{field : 'custom_name',title : '发货客户名称',width : 140,align:'center'},
			//{field : 'custom_contact',title : '发货客户联系人',width : 100,align:'center'},
			//{field : 'custom_tel',title : '发货客户联系人',width : 100,align:'center'},
			//{field : 'receipt',title : '收货客户名称',width : 100,align:'center'},

			//{field : 'receipt_tel',title : '到货联系电话',width : 100,align:'center'},

			//{field : 'transport_pay',title : '总运费',width : 100,align:'center'},
			//{field : 'trade_agency',title : '代收货款',width : 100,align:'center'},
			{
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
			//{field : 'shipping_order',title : '制单人',width : 60,align:'center',sortable:'true'},

			] ],
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'

		//结束
		});

		//能更多条件
		var btnMoreSearch = $("#btnMoreSearch");
		btnMoreSearch.click(function() {
			if (btnMoreSearch.attr("state") == "close") {
				//主要代码 
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 120
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#iswrite").combobox("setValue", "");
				$("#iswrite").combobox("setText", "全部");
				$("#shipping_order_state").combobox("setValue", "");
				$("#shipping_order_state").combobox("setText", "全部");
				$("#startDate1").val("");
				$("#endDate1").val("");
				$("#custom_name").val("");
				$("#receipt").val("");
				$("#address").val("");

			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 70
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#iswrite").combobox("setValue", "");
				$("#iswrite").combobox("setText", "全部");
				$("#shipping_order_state").combobox("setValue", "");
				$("#shipping_order_state").combobox("setText", "全部");
				$("#startDate1").val("");
				$("#endDate1").val("");
				$("#custom_name").val("");
				$("#receipt").val("");
				$("#address").val("");

			}
		});
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchCar_owner();
			}
		};
	});

	//查询
	function searchCar_owner() {
		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;
		var startDate1 = document.getElementById("startDate1").value;
		var endDate1 = document.getElementById("endDate1").value;

		$("#dg").datagrid(
				'load',
				{
					start_date : startDate,
					end_date : endDate,
					name : $.trim($("#ddId").val()),
					type : $.trim($("#iswrite").combobox("getValue")),//是否接收
					start_date1 : startDate1,
					end_date1 : endDate1,
					custom_name : $.trim($("#custom_name").val()),
					receipt : $.trim($("#receipt").val()),
					address : $.trim($("#address").val()),
					shipping_order_state : $.trim($("#shipping_order_state")
							.combobox("getValue")),
					shipperorder_id : $.trim($("#shipperorder_id").val())
				//出货订单号
				});

	}
	//返回处理
	function dealBack() {

		var checkarraynum = [];
		var checkarray = [];
		var checkip = '';
		var i = 0;
		var sos;
		var array = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < array.length; i++) {
			if (array[i].shipping_order_state == 6
					|| array[i].shipping_order_state == 5) {
				//array="";
			} else if(array[i].shipping_order_state ==4) {
				checkarray.push(array[i].shiping_order_id);
				checkarraynum.push(array[i].shiping_order_num);

			}
			var ar = array[i].shipping_order_state;

			/* if(ar==0){
			$.messager.alert("客户回单","该订单尚未出库暂不能签收!","info");		    
			return;
			}else if(ar==2||ar==3){
			 $.messager.alert("客户回单","该订单尚未到达暂不能签收!","info");
			 return;
			} */

		}
		/* if(array[i].is_send==0 ){
			checkarray.push(array[i].shiping_order_id);
		}else{
			checkip=checkip+array[i].shiping_order_num+"  ";
			i++;
			}
		}
			if(checkip!=""){
				$.messager.confirm('提示','您选中的订单有'+i+'条，且单号'+checkip+'不能执行接收！')	
				} */

		if (array != "") {

			/*  if(checkarray.length==0&&checkarraynum.length==0){
			   
			     parent.$.messager.alert("订单接收","您选择的订单信息已接收",'info');
			     return;  
			 } */

			var shipping_order_state = 2;

			$.messager.confirm('确认', '您确定要返回选中的记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : "ChangeO.do?method=dealBackOrder",
						type : "POST",
						data : {
							deal : checkarray.join(","),
							dealnum : checkarraynum.join(",")
						},
						success : function(data) {
							if (data.flag) {
								parent.$.messager.alert("运单返回", "运单信息返回成功",
										'info', function(r) {

											$('#dg').datagrid('reload');
										});
							} else {
								parent.$.messager.alert("运单接收", "您选择的运单信息已返回",
										'info');
							}
						}
					});
				}
			});
		} else {
			$.messager.alert("客户回单", "请选择要返回的信息", "info");
		}
	}

	//接收处理
	function otherDealBack() {
	  
		var checkarraynum = [];
		var checkarray = [];
		var checkip = '';
		var i = 0;
		var sos;
		var array = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < array.length; i++) {
			if (array[i].is_receive == 1 || array[i].shipping_order_state == 6
					|| array[i].shipping_order_state == 4) {
				//array="";

			} else if(array[i].shipping_order_state==5) {
				checkarray.push(array[i].shiping_order_id);
				checkarraynum.push(array[i].shiping_order_num);

			}
			var ar = array[i].shipping_order_state;

			/* if(ar==0){
			$.messager.alert("客户回单","该订单尚未出库暂不能签收!","info");		    
			return;
			}else if(ar==2||ar==3){
			 $.messager.alert("客户回单","该订单尚未到达暂不能签收!","info");
			 return;
			} */

		}
		/* if(array[i].is_send==0 ){
			checkarray.push(array[i].shiping_order_id);
		}else{
			checkip=checkip+array[i].shiping_order_num+"  ";
			i++;
			}
		}
			if(checkip!=""){
				$.messager.confirm('提示','您选中的订单有'+i+'条，且单号'+checkip+'不能执行接收！')	
				} */

		if (array != "") {

			/*  if(checkarray.length==0&&checkarraynum.length==0){
			   
			     parent.$.messager.alert("订单接收","您选择的订单信息已接收",'info');
			     return;  
			 } */

			var shipping_order_state = 2;

			$.messager.confirm('确认', '您确定要接受选中的记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : "ChangeO.do?method=dealBackOrder",
						type : "POST",
						data : {
							deal : checkarray.join(","),
							dealnum : checkarraynum.join(",")
						},
						success : function(data) {
							if (data.flag) {
								parent.$.messager.alert("运单接收", "运单信息接收成功",
										'info', function(r) {

											$('#dg').datagrid('reload');
										});
							} else {
								parent.$.messager.alert("运单接收",
										"您选择的运单信息未返回或者已接收", 'info');
							}
						}
					});
				}
			});
		} else {
			$.messager.alert("客户回单", "请选择要接收的信息", "info");
		}
	}

	//处理接收变成未接收
	function dealNotBack() {
		var checkarray = [];
		var checkip = '';
		var i = 0;
		var array = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < array.length; i++) {
			checkarray.push(array[i].shiping_order_id);
		}
		/* 	if(array[i].is_receive==1){
				checkarray.push(array[i].shiping_order_id);
				}else{
						checkip=checkip+array[i].shiping_order_num+"  ";
						i++;
				}
			}
		if(checkip!=""){
				$.messager.confirm('提示','您选中的订单有'+i+'条，且单号'+checkip+'不能执行寄出！')	
				} */
		if (array != "") {
			$.messager.confirm('确认', '您确定要寄出选中的记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : "ChangeO.do?method=dealNotBackOrder",
						type : "POST",
						data : {
							deal : checkarray.join(",")
						},
						success : function(data) {
							if (data.flag) {
								parent.$.messager.alert("运单接收", "运单信息寄出成功",
										'info', function(r) {
											$('#dg').datagrid('reload');
										});
							}
						}
					});
				}
			});
		} else {
			$.messager.alert("客户回单", "请选择要寄出的信息", "info");
		}
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
				url : 'ChangeO.do?method=getEditChangeOrderPage&pid=' + id,
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
			$.messager.alert("客户回单", "请选择一行信息", "info");
		}
	}
	//修改
	function updateUser() {
		var myform = document.forms[1];
		myform.action = "car_owner.do?method=updateCar_owner";
		myform.submit();

	}

	/* //导出
	function putoutfile() {
		
		var allRows2 = $("#dg").datagrid("getColumnFields");
		var colName = [];
		var fieldName = [];
		for (i = 1; i < allRows2.length; i++) {
			var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
			if (col.field != "check_type" && col.field != "send_type"
					&& col.field != "shipping_order_state") {
				colName.push(col.title);//把title列名到数组里去 
				fieldName.push(col.field); //把field列属性名到数组里去 
			}
		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		var outputform = $("#outputform");
		outputform.submit();
	}
	 */
	function eidtChange() {
		/* alert(document.getElementById("addbuttons"));
		alert(document.getElementById("upbuttons")); */
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			for ( var i = 0; i < row.length; i++) {
				var id = row[i].shiping_order_id;
			}
			dialog = parent.jy.modalDialog({
				title : '收费明细',
				url : 'ChangeO.do?method=AddchangeOrder&pid=' + id,
				/* 				 buttons :'#addbuttons'
				 */
				width : 680,
				height : 350,
				buttons : [ {
					text : '<input type="button" class="btncss" value="保存"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(
								dialog, grid, parent.$);
					}
				} ]
			});

		} else {
			$.messager.alert("客户回单", "请选择一行信息", "info");
		}
	};
	//打印
	function dy() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			for ( var i = 0; i < row.length; i++) {
				var pid = row[i].shiping_order_id;
			}
			var url = "ChangeOrder.do?method=getShipOrderPrint&pid=" + pid;
			ow(url);
		} else {
			$.messager.alert("客户回单", "请选择一行信息", "info");
		}

	}
	function ow(owurl) {
		var tmp = window.open("about:blank", "", "fullscreen=1");
		tmp.moveTo(0, 0);
		tmp.resizeTo(screen.width + 20, screen.height);
		tmp.focus();
		tmp.location = owurl;
	}
	//导入
	function putintfile() {
		dialog = parent.jy.modalDialog({
			title : '导入运单',
			url : 'ChangeOrder.do?method=imp',
			width : 600,
			height : 300,
			buttons : [ {
				text : '<input type="button" value="导入" class="btncss">',
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
		$("#endDate").val("");
		$("#ddId").val("");
		$("#iswrite").combobox("setValue", "");
		$("#iswrite").combobox("setText", "全部");

		$("#shipping_order_state").combobox("setValue", "");
		$("#shipping_order_state").combobox("setText", "全部");
		$("#startDate1").val("");
		$("#endDate1").val("");
		$("#custom_name").val("");
		$("#receipt").val("");
		$("#address").val("");
	}

	//导出回单信息
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
	<form action="ChangeO.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			collapsible="false">
			<lable>起运日期:</lable>
			<input id="startDate" name="start_date" class="Wdate"
				readonly="readonly" style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" /> - <input
				id="endDate" name="end_date" class="Wdate" readonly="readonly"
				style="width:150px;height:22px;"
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" />
			<lable class="divlabel">货运编号:</lable>
			<input type="text" class="search-text" id="ddId" name="name"
				style="width:150px">
			<lable class="divlabel"> 出货订单号: </lable>
			<input type="text" class="search-text" id="shipperorder_id"
				name="shipperorder_id" style="width: 100px"> <a
				class="easyui-linkbutton" onclick="searchCar_owner()"
				data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
				class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
				href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">
				<div id="" style="padding-top: 3px;">
					<lable class="">回单状态:</lable>
					<select id='shipping_order_state' name="shipping_order_state"
						class="easyui-combobox" name="shipping_order_state"
						style="width:150px;height:22px"
						data-options="panelHeight : 'auto',editable:false">
						<option value="" selected="selected">全部</option>
						<option value="4">已签收</option>
						<option value="5">已返回</option>
						<option value="6">已收到</option>
					</select>
					<lable>是否接收:</lable>
					<select class="easyui-combobox" id="iswrite" name="type"
						style="width:150px;height:25px"
						data-options="panelHeight : 'auto',editable:false">
						<option value="">全部</option>
						<option value="1">已接收</option>
						<option value="0">未接收</option>
					</select>

					<lable>受理机构:</lable>
					<input type="text" class="search-text" style="width:150px"
						id="custom_name" name="custom_name">
					<lable>收货联系人:</lable>
					<input type="text" class="search-text" style="width:150px"
						id="receipt" name="receipt">
					<div id="" style="padding-top: 5px;">
						<lable class="">终到位置:</lable>
						<input type="text" class="search-text" id="address" name="address">

						<lable class="divlabel">接收日期:</lable>
						<input id="startDate1" name="start_date1" class="Wdate"
							readonly="readonly" style="width:150px;height:22px; "
							onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						- <input id="endDate1" name="end_date1" class="Wdate"
							readonly="readonly" style="width:150px;height:22px;"
							onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					</div>
				</div>
			</div>
		</div>
		<div region="center">
			<table id="dg" title="运单信息"></table>
		</div>
		<div id="tb">
			<!--  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"onclick="add()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="getUpdateCar_owner" data-options="iconCls:'icon-edit',plain:true"onclick="getUpdateCar_owner()">修改</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="deleteShipOrder()">删除</a>
		 <a href="javascript:void(0)"	class="easyui-linkbutton" data-options="iconCls:'icon-input',plain:true" onclick="showIcons()" id="tbru"style="display: none">导入</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" id="outputCar_owner" data-options="iconCls:'icon-output',plain:true" onclick="putoutfile()">导出</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="getShipOrderPage()">详细</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="daShipOrder()">打印</a> -->
		</div>
		<input type="hidden" name="method" value="outBackShipOrderFile">
		<input type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle"> <input
			type="hidden" id="dataIds" name="dataIds">
	</form>
</body>
</html>