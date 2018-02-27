<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>收货客户信息</title>


</head>
<script type="text/javascript">
	var grid;
	$(function() {

		var fs = $("#functionname").val().split(",");
		for ( var i = 0; i < fs.length; i++) {
			if (fs[i] == "添加") {
				$("#tbadd").removeAttr("style");
			} else if (fs[i] == "修改") {
				$("#tbmodify").removeAttr("style");
			} else if (fs[i] == "删除") {
				$("#tbdel").removeAttr("style");
			} else if (fs[i] == "定位") {
				$("#tblocation").removeAttr("style");
			} else if (fs[i] == "导出") {
				$("#putoutfile").removeAttr("style");
			} else if (fs[i] == "导入") {
				$("#putinfile").removeAttr("style");
			}
			;

		}
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
			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 61
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
			}
		});
		grid = $("#dg")
				.datagrid(
						{
							url : 'deliveryCustomer.do?method=getDeliveryCustomers',
							fit : true,
							rownumbers : true,
							//singleSelect: true,
							selectOnCheck : true,
							checkOnSelect : true,
							toolbar : '#tb',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'delivery_cus_name',
										title : '发货客户名称',
										fitColumns : true
									},
									{
										field : 'delivery_num',
										title : '客户编码',
										fitColumns : true,
									},
									{
										field : 'delivery_name',
										title : '收货客户名称',
										fitColumns : true,
									},
									{
										field : 'delivery_people',
										title : '联系人',
										fitColumns : true,
										align : 'left'
									},
									{
										field : 'delivery_tel',
										title : '联系方式',
										fitColumns : true,
										align : 'left'
									},
									{
										field : 'delivery_address',
										title : '地址',
										fitColumns : true,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									},
									/* {
										field : 'delivery_business',
										title : '主要业务',
										width : 110,
										align : 'left',
									    formatter: function(value, row, index) {
									           var content = '<div  title="' + value + '">' + value + '</div>';
									            return content;
									        }
									}, */
									{
										field : 'delivery_profile',
										title : '备注',
										width : 110,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									},/* ,																				} */
							/*   {field:'user_name',title:'修改人',width:100,align:'left'} */
							] ],
							pagination : true,
							pageSize : 10,
							pageList : [ 5, 10, 15, 20 ]
						});
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchRemark();
			}
		};
	});
	function add() {
		dialog = parent.jy.modalDialog({
			title : '客户新增',
			url : 'deliveryCustomer.do?method=getDeliveryCustomer',
			width : 680,
			height : 600,
			buttons : [ {
				text : '<input type="button" class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	}
	function searchRemark() {
		$('#dg').datagrid('load', {
			customer_name : $.trim($("#serchname").val()),
			customer_tel : $.trim($("#serchtel").val()),
			customer_address : $.trim($("#serchaddress").val()),
			delivery_cus_name : $.trim($("#delivery_cus_name").val()),
		});
	}
	function reset() {
		$("#serchname").select2("val", "");
		$("#serchtel").select2("val", "");
		$("#serchaddress").select2("val", "");
		$("#delivery_cus_name").val("");
	}
	function deleteEver() {
		var row = $("#dg").datagrid('getSelections');
		var b;
		if (row.length > 0) {
			$.messager
					.confirm(
							'确认',
							'您确认想要删除这' + row.length + '条记录吗？',
							function(r) {
								if (r) {

									for ( var i = 0; i < row.length; i++) {
										var remarkid = row[i].remark_id;
										$
												.ajax({
													type : "POST",
													async : false,
													url : 'remarkMap.do?method=checkRemark',
													data : {
														id : remarkid
													},
													success : function(data) {
														b = data;
													}
												});
										if (b) {
											alert("该节点已被绑定,解除绑定之后才能删除");
										} else {
											$
													.ajax({
														type : "POST",
														async : false,
														url : 'remarkMap.do?method=deleteRemark',
														data : {
															id : remarkid
														},
														success : function(data) {

														}
													});
										}

										$("#dg").datagrid('reload');
									}
								}
							});
		} else {
			$.messager.alert('修改客户', '请选择一行!', 'info');
		}

	}
	function modifyrrrr() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var remarkid = row[0].remark_id;
			location.href = "remarkMap.do?method=remarkMapmodify&&id="
					+ remarkid;
		} else {
			$.messager.alert('修改客户', '请选择一行!', 'info');
		}
	}
	function deleteRemark() {
		var row = $("#dg").datagrid('getSelections');
		var b;
		if (row.length > 0) {
			$.messager
					.confirm(
							'确认',
							'您确认想要删除这' + row.length + '条记录吗？',
							function(r) {
								if (r) {
									var remarkid = [];
									for ( var i = 0; i < row.length; i++) {
										remarkid.push(row[i].delivery_id);

									}
									$
											.ajax({
												type : "POST",
												async : false,
												url : 'deliveryCustomer.do?method=deleteDeliveryCustomer',
												data : {
													id : remarkid.join(",")
												},
												success : function(data) {
													if (data) {
														$.messager
																.alert(
																		'删除客户',
																		'删除成功!',
																		'info');
														$("#dg").datagrid(
																'reload');
													} else {
														$.messager
																.alert(
																		'删除客户',
																		'删除失败!',
																		'info');
														$("#dg").datagrid(
																'reload');
													}
												}
											});

								}
							});
		} else {
			$.messager.alert('删除客户', '请选择一行!', 'info');
		}
	}
	function Location() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var delivery_address = row[0].delivery_address;
			var remarkid = row[0].delivery_id;
			location.href = "deliveryCustomer.do?method=DeliveryCustomerMapmodify&&id="
					+ remarkid;
		} else {
			$.messager.alert('修改客户', '请选择一行!', 'info');
		}
	}
	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var remarkid = row[0].delivery_id;
			dialog = parent.jy.modalDialog({
				title : '客户修改',
				url : 'deliveryCustomer.do?method=UpdateDeliverycustomer&cid='
						+ remarkid,
				width : 680,
				height : 600,
				buttons : [ {
					text : '<input type="button" class="btncss" value="保存"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitForms(
								dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			$.messager.alert('修改客户', '请选择一行!', 'info');
		}
	}
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
			dataIds.push(row[i].delivery_id);
		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		$("#dataIds").val(dataIds.join(","));
		var outPutForm = $("#cform");
		outPutForm.submit();
	}
	function putinfile() {
		dialog = parent.jy.modalDialog({
			title : '发货客户的导入',
			url : 'deliveryCustomer.do?method=imp',
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
	<form action="deliveryCustomer.do" id="cform" method="post">
		<!-- <a href="remarkMap.do?method=getRemarkMap"></a> -->
		<div region="north" title="检索" class="st-north" border="false"
			style="height:61px" data-options="collapsible:false">
			<lable class="divlabel">发货客户名称：</lable>
			<input type="text" id="delivery_cus_name" style="width:150px" name="delivery_cus_name"/>
			<lable class="divlabel">收货客户名称：</lable>
			<input type="text" id="serchname" style="width:150px" name="customer_name"/>

			<lable class="divlabel">联系方式：</lable>
			<input type="text" id="serchtel" name="customer_tel" style="width:150px" />
			<lable class="divlabel">地址：</lable>
			<input type="text" id="serchaddress" name="customer_address" style="width:150px;" /> &nbsp <a
				class="easyui-linkbutton" onclick="searchRemark()"
				data-options="iconCls:'icon-search'" id="searchId">查询</a>
		</div>
		<div region="center" title="收货客户信息展示" region="center"
			style="background: #eee; overflow-y:hidden">
			<table id="dg"></table>
		</div>
		<div id="tb">
			<a href="#" class="easyui-linkbutton" id="tbadd" onclick="add()"
				data-options="iconCls:'icon-add',plain:true" style="display: none">新增</a>
			<a href="#" class="easyui-linkbutton" id="tbmodify"
				onclick="modify()" data-options="iconCls:'icon-edit',plain:true"
				style="display: none">修改</a> <a href="#" class="easyui-linkbutton"
				id="tblocation" onclick="Location()"
				data-options="iconCls:'icon-remove',plain:true"
				style="display: none">定位</a> <a href="#" class="easyui-linkbutton"
				id="tbdel" onclick="deleteRemark()"
				data-options="iconCls:'icon-remove',plain:true"
				style="display: none">删除</a> <a href="#" class="easyui-linkbutton"
				id="tbreset" onclick="reset()"
				data-options="iconCls:'icon-reset',plain:true">重置</a> <a href="#"
				class="easyui-linkbutton" id="putoutfile" onclick="putoutfile()"
				data-options="iconCls:'icon-output',plain:true"
				style="display: none">导出</a> <a href="#" class="easyui-linkbutton"
				id="putinfile" onclick="putinfile()"
				data-options="iconCls:'icon-input',plain:true" style="display: none">导入</a>

			<input id="cid" type="hidden" name='cid' value=""> <input
				type="hidden" name="method" value="outPutDeliveryCustomers">
			<input type="hidden" id="fieldName" name="fieldName"> <input
				type="hidden" id="headtitle" name="headtitle"> <input
				type="hidden" id="dataIds" name="dataIds">
	</form>
	</div>
	<input id="functionname" type="hidden" value="${function}">
</body>
</html>
