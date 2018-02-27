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
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
</head>
<script type="text/javascript">
 		var dialog;
 		var grid;
 		var id;
  		$(function(){
  		$('#gps_dlg').dialog({
			title : '选择gps对应的车辆信息',
			width : 500,
			height : 400,
			closed : true,
			cache : false,
			modal : true,
			buttons : '#gps_buttons'
		});
  		
  		
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
				title : '供应商',
				width : 100,
				align : 'center'
			}, {
				field : 'driver_address',
				title : '终到位置',
				width : 180,
				align : 'center'
			},

			] ],
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
		var suppliers = "";
		if ($.trim($("#suppliers_").select2("data")) != "") {
			suppliers = $.trim($("#suppliers_").val());
		}
		$("#dg").datagrid('load', {
			driver_name : $.trim($("#driver_name").val()),
			driver_suppliers : suppliers,
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
		if (row.length != 1)
			return parent.$.messager.alert("信息提示", "请选择一个司机", 'info');
		$.ajax({
			url : "driver.do?method=driverISHaveGps",
			type : "POST",
			data : {
				driverId : row[0].driver_id
			},
			success : function(data) {
				if(data){
				   
				    if(data.map.num==0) return openFenPeiDialog("",row[0].userName);
				     gps_table(data.map.username);
		            $('#gps_dlg').dialog('open');
				}
			}
		});

		

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
	// 所有司机导出
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
		console.info($("#method").val());
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

	function gps_table(inData) {
		gps_grid = $("#gps_dg").datagrid({
			url : 'equipment.do?method=getEquipmentManlist',
			border : false,
			rownumbers : true,
			fit : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			rowStyler : function(index, row) {
				if (row.is_binding == 0) {
					return 'color:blue;';
				}
			},
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'car_number',
				title : '车牌号',
				width : 100,
				align : 'center'
			}, {
				field : 'drivername',
				title : '司机姓名',
				width : 100,
				align : 'center'
			}, {
				field : 'username',
				title : '用户名',
				width : 100,
				align : 'center'
			}, ] ],
			pagination : true,
			pageSize : 5,
			pageList : [ 5, 10, 15, 20 ],
			onLoadSuccess:function(data){
			   $.each(data.rows,function(i,d){
			      if(d.username!=inData) return true;
			       var index =  gps_grid.datagrid('getRowIndex', d);  
			       gps_grid.datagrid('selectRow', index);  
			   });
			}
			
			
			
		//toolbar : '#tb'
		});
		return gps_grid;
	}
	function getGPSSelect() {
	    var gps_row =  $("#gps_dg").datagrid('getSelections');
	    if (gps_row.length != 1) return parent.$.messager.alert("信息提示", "请选择一个GPS 设备信息", 'info');
	       console.info(gps_row);
	       var data = gps_row[0];
	       openFenPeiDialog(data.equipment_number,data.username);
	        $('#gps_dlg').dialog('close');
	
	}
	
	function openFenPeiDialog(equipmentNum,userName) {
		//var gps_row = gps_grid.datagrid('getSelections');
		equipmentNum=equipmentNum==undefined?"":equipmentNum;
		userName=userName==undefined?"":userName;
		var row = $("#dg").datagrid('getSelections');
		var id ="";
		
		for ( var i = 0; i < row.length; i++) {
			 id =row[i].driver_id;
		}
		dialog = parent.jy.modalDialog({
			title : '分配运单',
			url : 'orderC.do?method=getShipOrderInfoPageTwo&pid=' + id+'&equipmentNum='+equipmentNum+'&userName='+userName,
			resizable : true,
			height : 580,
			width : 1100,
			buttons : [ {
				text : '<input type="button" class="btncss" value="确认分配"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ],
			onClose : function() {
				$('#dg').datagrid('reload');
			}
		});

	}
	function searchGPSEqu(){
	    $('#gps_dg').datagrid('load',{
  		/* 	equipment_number : $.trim($("#equipment_numberId").val()),
  			carid:username,
  			department_id:$.trim($("#cc").combotree('getValue')), */
  			car_number:$.trim($("#car_number").val())
  		});
	}
</script>
<body class="easyui-layout">
	<form action="driver.do" id="outputform" method="post">
		<div region="north" title="检索条件" class="st-north" border="false"
			data-options="collapsible:false" style="height:62px">
			<label class="divlabel">司机姓名：</label> <input type="text"
				class="search-text" id="driver_name" name="driver_name"> <label>供应商：<input
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
				<lable class="divlabel">货运编号:</lable>
				<input type="text" class="search-text" id="shiping_order_num"
					name="shiping_order_num" style="width:140px">
				<lable class="divlabel"> 出货订单号: </lable>
				<input type="text" class="search-text" id="shiping_order_goid"
					name="shiping_order_goid" style="width: 140px">
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
			type="hidden" id="headtitle" name="headtitle">
	</form>
	<div id="gps_dlg">
	    <div class="easyui-layout" style="width:100%;height:100%;">  
	    <div data-options="region:'north',title:'检索条件',split:true"
			style="height:80px;">
			<label>车牌号:</label><input type="text" class="search-text" id="car_number"/>
			<a class="easyui-linkbutton" onclick="searchGPSEqu()" data-options="iconCls:'icon-search'" style="margin-left: 10px;">查询</a>
		</div>
		<div data-options="region:'center',title:'设备信息'">
		<table id="gps_dg"></table>
		</div>
	    </div>
	    <div id="gps_buttons">
				<input type="button" onclick="getGPSSelect()" value="确定"
					class="btncss" id="gps_ok" />
	    </div>
	</div>
</body>
</html>