<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>司机管理</title>
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
%>
<%
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
</head>
<script type="text/javascript">
 		var dialog;
 		var grid;
 		var id;
  		$(function(){
  		   id = '<%=user.getId()%>';
		grid = $("#dg").datagrid({
			url : 'driver.do?method=getDriver',
			border : false,
			rownumbers : true,
			fit : true,
			singleSelect : false,
			selectOnCheck : true,
			checkOnSelect : true,
			//显示的司机列表变化了，只有姓名、供应商、终到位置
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'driver_name',
				title : '姓名',
				width : 70,
				align : 'center'
			}, {
				field : 'driver_suppliersname',
				title : '供应商姓名',
				width : 100,
				align : 'center'
			}, {
				field : 'supplie_company',
				title : '供应商公司',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_phone',
				title : '手机号',
				width : 90,
				align : 'center'
			}, {
				field : 'driver_cartype',
				title : '车辆类型',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_carnumber',
				title : '车牌号',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_phonebrand',
				title : '手机品牌',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_phonemodel',
				title : '手机型号',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_cardnumber',
				title : '身份证号',
				width : 120,
				align : 'center'
			}, {
				field : 'driver_address',
				title : '终到位置',
				width : 180,
				align : 'center'
			}, {
				field : 'driver_updatedate',
				title : '更新时间',
				width : 100,
				align : 'center',
				formatter : function(val, row, index) {
					if (val != null) {
						return val.substring(0, 19);
					}
				}
			}, {
				field : 'driver_updatepeople',
				title : '更新人',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_createtime',
				title : '创建时间',
				width : 100,
				align : 'center'
			}, ] ],
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});
		$("#suppliers_").select2({
			placeholder : "请输入供应商", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			//  maximumSelectionSize: 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "driver.do?method=getSuppliers",
					data : {
						num : query.term
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
		//更多
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
				$("#driver_address").val("");
				$("#driver_cardnumber").val("");
				$("#shiping_order_num").val("");
				$("#shiping_order_goid").val("");
			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 62
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#driver_address").val("");
				$("#driver_cardnumber").val("");
				$("#shiping_order_num").val("");
				$("#shiping_order_goid").val("");
			}
		});
		//	回车查询
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchDriver();
			}
		};

	});
	//查询
	function searchDriver() {
		/*var suppliers = "";*/
		/*if ($.trim($("#suppliers_").select2("data")) != "") {
			suppliers =$("#suppliers_").select2('data').text;
		}*/
		$("#dg").datagrid('load', {
			driver_name : $.trim($("#driver_name").val()),
			driver_suppliers : $("#suppliers_").val(),
			driver_address : $.trim($("#driver_address").val()),
			driver_phone : $.trim($("#driver_phone").val()),
			driver_cardnumber : $.trim($("#driver_cardnumber").val()),
			shiping_order_num : $.trim($("#shiping_order_num").val()),//货运编号
			shiping_order_goid : $.trim($("#shiping_order_goid").val())
		//出货订单号
		});
	}
	//分配订单
	function fenpei() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			for ( var i = 0; i < row.length; i++) {
				var id = row[i].driver_id;
			}
			dialog = parent.jy
					.modalDialog({
						title : '分配运单',
						url : 'orderC.do?method=getShipOrderInfoPageTwo&pid='
								+ id,
						resizable : true,
						height : 580,
						width : 1100,
						buttons : [ {
							text : '<input type="button" class="btncss" value="确认分配"/>',
							handler : function() {
								dialog.find('iframe').get(0).contentWindow
										.submitForm(dialog, grid, parent.$);
							}
						} ],
						onClose : function() {
							$('#dg').datagrid('reload');
						}
					});
		} else {
			parent.$.messager.alert("信息提示", "请选择一个司机", 'info');
		}
	};

	//删除
	function deleteEver() {
		var checkarray = [];
		var array = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < array.length; i++) {
			checkarray.push(array[i].driver_id);
		}
		if (array != "") {
			$.messager.confirm('确认', '您确定要删除选中的记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : "driver.do?method=deleteDriver",
						type : "POST",
						data : {
							del : checkarray.join(",")
						},
						success : function(data) {
							if (data.flag) {
								parent.$.messager.alert("信息删除", "信息删除成功",
										'info', function(r) {
											$('#dg').datagrid('reload');
										});
							}
						}
					});
				}
			});
		} else {
			parent.$.messager.alert("信息提示", "请选择要删除的信息", 'info');

		}
	}
	/*
	修改查询
	 */
	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			for ( var i = 0; i < row.length; i++) {
				var id = row[i].driver_id;
			}
			dialog = parent.jy.modalDialog({
				title : '信息修改',
				url : 'driver.do?method=EditDriverPage&pid=' + id,
				width : 680,
				height : 420,
				buttons : [ {
					text : '<input type="button" class="btncss" value="修改"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow
								.submitFormEdit(dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			parent.$.messager.alert("信息提示", "请选择一个司机", 'info');
		}
	}
	//修改
	function updateUser() {
		var myform = document.forms[1];
		myform.action = "driver.do?method=updateUser";
		myform.submit();

	}

	//导出
	/* function putoutfile(){
	var allRows = $("#dg").datagrid("getRows");
	alert(JSON.stringify(allRows));
	var allRows2 = $("#dg").datagrid("getColumnFields");
	var colName=[];
	for(i=1;i<allRows2.length;i++)
	{
		var col = $('#dg').datagrid( "getColumnOption" , allRows2[i] );
		colName.push(col.title);//把title列名到数组里去 
	} 
		$("#datas").val(JSON.stringify(allRows));
		$("#headtitle").val(colName.join(","));
		var outputform=$("#outputform");
		outputform.submit();
	} */
	//新增信息
	function add() {
		dialog = parent.jy.modalDialog({
			title : '新增信息',
			url : 'driver.do?method=addDriver',
			/* 				 buttons :'#addbuttons'
			 */
			width : 680,
			height : 420,
			buttons : [ {
				text : '<input type="button" class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
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
			colName.push(col.title);//把title列名到数组里去 
			fieldName.push(col.field); //把field列属性名到数组里去 	

		}
		for (i = 0; i < row.length; i++) {
			dataIds.push(row[i].driver_id);

		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		$("#dataIds").val(dataIds.join(","));
		var outPutForm = $("#outputform");
		outPutForm.submit();
	}
	//查看运单
	function searchMsg() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var pid = row[0].driver_id;
			dialog = parent.jy.modalDialog({
				title : '分配运单查看',
				url : 'orderC.do?method=getShipOrderInfoPage&pid=' + pid,
				width : 1200,
				heght : 700,
				buttons : [ {
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForm(
								dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			parent.$.messager.alert("信息提示", "请选择一个司机", 'info');
		}
	}

	//重置
	function reset() {
		$("#driver_name").val("");
		$("#suppliers_").select2("val", "");
		$("#driver_phone").val("");
		$("#driver_address").val("");
		$("#driver_cardnumber").val("");
		$("#shiping_order_num").val("");
		$("#shiping_order_goid").val("");
	}
	function putintfile() {
		dialog = parent.jy.modalDialog({
			title : '司机的导入',
			url : 'driver.do?method=imp',
			width : 600,
			height : 300,
			buttons : [ {
				text : '<input type="button" value="导入" class="btncss">',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ],
			onDestroy : function() {
				$("#dg").datagrid('reload');
			}
		});
	}
</script>
<body class="easyui-layout">
	<form action="driver.do" id="outputform" method="post">
		<div region="north" title="检索条件" class="st-north" border="false"
			data-options="collapsible:false" style="height:62px">
			<label class="divlabel">司机姓名：</label> <input type="text"
				class="search-text" id="driver_name" name="driver_name"> <label>供应商姓名：<input
				type="hidden" id="suppliers_" style="width:150px"
				name="driver_suppliers"> </label> <label class="divlabel">联系方式：</label><input
				type="text" class="search-text" id="driver_phone"
				name="driver_phone"> <a class="easyui-linkbutton"
				onclick="searchDriver()" data-options="iconCls:'icon-search'"
				style="margin-left: 10px;">查询</a> <a id="btnMoreSearch"
				class="btn btn-sm btn-link" type="button" href="javascript:void(0)"
				state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">
				<label class="divlabel">终到位置：</label><input type="text"
					class="search-text" id="driver_address" name="driver_address">
				<label class="divlabel">身份证：</label><input type="text"
					class="search-text" id="driver_cardnumber" name="driver_cardnumber">
				<!-- <lable class="divlabel">货运编号:</lable>
			<input type="text" class="search-text" id="shiping_order_num" name="shiping_order_num" style="width:140px">
			<lable class="divlabel"> 出货订单号: </lable>
			<input type="text" class="search-text" id="shiping_order_goid" name="shiping_order_goid"
				style="width: 140px"> -->
			</div>
		</div>
		<div region="center">
			<table id="dg" title="信息管理"></table>
		</div>
		<div id="tb">
			<!--  <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"onclick="addDriver()">新增</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" id="getUpdateDriver" data-options="iconCls:'icon-edit',plain:true"onclick="getUpdateDriver()">修改</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="deleteDriver()">删除</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"  onclick="add()">分配订单</a>  -->
			<!--  <a href="javascript:void(0)" class="easyui-linkbutton" id="getInputDriver" data-options="iconCls:'icon-input',plain:true"onclick="inputfile()">导入</a> -->
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" id="outputDriver" data-options="iconCls:'icon-output',plain:true" onclick="putoutfile()">导出</a> -->
		</div>


		<input type="hidden" name="method" value="outDriverExcel"> <input
			type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle"><input
			type="hidden" id="dataIds" name="dataIds">
	</form>

</body>
</html>