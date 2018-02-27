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
<title></title>
</head>
<script type="text/javascript">
	var dialog;
	var grid;
	$(function() {
		var da = "";
		var fs = $("#functionname").val().split(",");
		for ( var i = 0; i < fs.length; i++) {
			if (fs[i] == "添加") {
				$("#tbadd").removeAttr("style");
			} else if (fs[i] == "修改") {
				$("#tbmodify").removeAttr("style");
			} else if (fs[i] == "删除") {
				$("#tbdel").removeAttr("style");
			}else if(fs[i]=="导出"){
			    $("#putoutfile").removeAttr("style");
			}else if(fs[i]=="导入"){
			    $("#putinfile").removeAttr("style");
			}

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
							url : 'remarkMap.do?method=getRemarks',
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
										field : 'customer_num',
										title : '客户编码',
										width : 130
									},
									{
										field : 'customer_name',
										title : '受理机构',
										width : 130
									},
									{
										field : 'customer_people',
										title : '联系人',
										width : 110,
										align : 'left'
									},
									{
										field : 'customer_tel',
										title : '联系方式',
										width : 110,
										align : 'left'
									},
									{
										field : 'customer_address',
										title : '地址',
										width : 200,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									},
									{
										field : 'customer_business',
										title : '主要业务',
										width : 110,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									},
									{
										field : 'customer_profile',
										title : '简介',
										width : 110,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									},
									{
										field : 'customer_experience',
										title : '合作经历',
										width : 110,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									}, {
										field : 'customer_operation',
										title : '操作人',
										width : 110,
										align : 'left'
									}, {
										field : 'customer_operationtime',
										title : '操作时间',
										width : 160,
										align : 'left',
										formatter : function(val, row, index) {
											if (val != null) {
												return val.substring(0, 19);
											}
										}
									}
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
	function searchRemark() {
		$('#dg').datagrid('load', {
			customer_name : $.trim($("#serchname").val()),
			customer_tel : $.trim($("#serchtel").val()),
			customer_address : $.trim($("#serchaddress").val())
		});
	}
	function reset() {
		$("#serchname").val("");
		$("#serchtel").val("");
		$("#serchaddress").val("");
	}

	function add() {
		dialog = parent.jy.modalDialog({
			title : '客户新增',
			url : 'remarkMap.do?method=getRemarkMap',
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
	function deleteRemark() {
		var row = $("#dg").datagrid('getSelections');
		var b;
		if (row.length > 0) {
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							var remarkid =[];
							for ( var i = 0; i < row.length; i++) {
							  remarkid.push(row[i].customer_id);
								
							}
						}
						$.ajax({
									type : "POST",
									async : false,
									url : 'remarkMap.do?method=deleteRemark',
									data : {
										id : remarkid.join(",")
									},
									success : function(data) {
                                         if(data){
                                          $.messager.alert('删除客户', '删除成功', 'info');
                                          $("#dg").datagrid('reload');
                                         }else{
                                         $.messager.alert('删除客户', '删除失败', 'info');
                                          $("#dg").datagrid('reload');
                                         }
                                        
									}
								});
								
					});
		} else {
			$.messager.alert('删除客户', '请选择一行!', 'info');
		}

	}
	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var remarkid = row[0].customer_id;
			dialog = parent.jy.modalDialog({
				title : '客户修改',
				url : 'remarkMap.do?method=updateCustomer&cid=' + remarkid,
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
			dataIds.push(row[i].customer_id);
		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		$("#dataIds").val(dataIds.join(","));
		var outPutForm = $("#cform");
		outPutForm.submit();
	}
	function putinfile(){
			dialog = parent.jy.modalDialog({
			title : '发货客户的导入',
			url : 'remarkMap.do?method=imp',
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

	<a href="remarkMap.do?method=getRemarkMap"></a>
	<form action="remarkMap.do" id="cform" method="post">
	<div region="north" title="检索" class="st-north" border="false"
		style="height:61px" data-options="collapsible:false">
		<lable class="divlabel">客户名称：</lable>
		<input type="text" id="serchname" style="width:150px" name="customer_name" />
		<lable class="divlabel">联系方式：</lable>
		<input type="text" id="serchtel" style="width:150px" name="customer_tel"/>
		<lable class="divlabel">地址：</lable>
		<input type="text" id="serchaddress" style="width:150px;" name="customer_address"  /> &nbsp <a
			class="easyui-linkbutton" onclick="searchRemark()"
			data-options="iconCls:'icon-search'" id="searchId">查询</a>

	</div>
	<div region="center" title="发货客户信息展示" region="center"
		style="background: #eee; overflow-y:hidden">
		<table id="dg"></table>
	</div>
	<div id="tb">
		<a href="#" class="easyui-linkbutton" id="tbadd" onclick="add()"
			data-options="iconCls:'icon-add',plain:true" style="display: none">新增</a>
		<a href="#" class="easyui-linkbutton" id="tbmodify" onclick="modify()"
			data-options="iconCls:'icon-edit',plain:true" style="display: none">修改</a>
		<a href="#" class="easyui-linkbutton" id="tbdel"
			onclick="deleteRemark()"
			data-options="iconCls:'icon-remove',plain:true" style="display: none">删除</a>
		<a href="#" class="easyui-linkbutton" id="tbreset" onclick="reset()"
			data-options="iconCls:'icon-reset',plain:true">重置</a>
			<a href="#" class="easyui-linkbutton" id="putoutfile"
			onclick="putoutfile()"
			data-options="iconCls:'icon-output',plain:true" style="display: none">导出</a>
		<a href="#" class="easyui-linkbutton" id="putinfile"
			onclick="putinfile()"
			data-options="iconCls:'icon-input',plain:true" style="display: none">导入</a>
		
			<input id="cid" type="hidden" name='cid' value="">
			<input type="hidden" name="method" value="outPutSendCustomers">
		<input type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle"> <input
			type="hidden" id="dataIds" name="dataIds">
		</div>
	</form>
	<input id="functionname" type="hidden" value="${function}">
</body>
</html>
