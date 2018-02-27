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
	/* 	var fs = $("#functionname").val().split(",");
		for (var i = 0; i < fs.length; i++) {
			if (fs[i] == "添加") {
				$("#tbadd").removeAttr("style");
			} else if (fs[i] == "修改") {
				$("#tbmodify").removeAttr("style");
			} else if (fs[i] == "删除") {
				$("#tbdel").removeAttr("style");
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
							url : 'remarkMap.do?method=getAging',
							fit : true,
							rownumbers : true,
							selectOnCheck : true,
							checkOnSelect : true,
							toolbar : '#tb',
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'cutomer_name',
										title : '客户名称',
										width : 130
									},
									{
										field : 'aging_address',
										title : '地址',
										width : 200,
										align : 'left',
										formatter : function(value, row, index) {
											var content = '<div  title="' + value + '">'
													+ value + '</div>';
											return content;
										}
									},{
										field : 'star_time',
										title : '触发时间',
										width : 110,
										align : 'left',
										formatter : function(value, row, index) {
											return value+'-'+row.end_time;
										}
									},
									{
										field : 'aging_time',
										title : '标准时效',
										width : 110,
										align : 'left',
										formatter : function(value, row, index) {
											var day= row.aging_day;
											if(day==0){
											return value+'(当日)';
											}else if(day==1){
											return value+'(次日)';
											}else if(day==2){
											return value+'(隔日)';
											}
										}
									},
									{
										field : 'aging_operator',
										title : '操作人',
										width : 110,
										align : 'left'
									}, {
										field : 'aging_operator_date',
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
							] ]/* ,
							onDblClickRow:function(rowIndex, rowData){
					  			var pid=rowData.customer_id;
								dialog = parent.jy.modalDialog({
										title : '时效设置',
										url : 'remarkMap.do?method=setTime&cid='+pid,
										width : 500,
										height: 160,
										buttons : [{
											handler : function() {
												dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
											}
										}] 
									}); 		
	  						} */,
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
			customer_name : $.trim($("#cutomer_name").val()),
			customer_address : $.trim($("#driver_address").val())
		});
	}
	function reset() {
		$("#cutomer_name").val("");
		$("#driver_address").val("");
	}

	function add() {
		dialog = parent.jy.modalDialog({
			title : '时效新增',
			url : 'remarkMap.do?method=newAddAging',
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
	}
	function deleteEver() {
		var row = $("#dg").datagrid('getSelections');
		var b;
		var agingid=[];
		if (row.length > 0) {
			for (var i = 0; i < row.length; i++) {
				agingid.push(row[i].aging_id);
			}
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							$.ajax({
								type : "POST",
								async : false,
								url : 'remarkMap.do?method=deleteAging',
								data : {
									id : agingid.join(",")
								},
								success : function(data) {
									if(data.flag){
										$.messager.alert('删除时效', '删除时效成功!', 'info');
										$("#dg").datagrid('reload');
									}else{
										$.messager.alert('删除时效', '删除时效失败!', 'info');
									}
								}
							});
						}
					});
		} else {
			$.messager.alert('删除时效', '请选择一行!', 'info');
		}

	}
	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var pid = row[0].aging_id;
			dialog = parent.jy.modalDialog({
				title : '时效修改',
				url : 'remarkMap.do?method=setAging&cid='+pid,
				width : 680,
				height : 350,
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
</script>
<body class="easyui-layout">
    <form action="" id="cform" method="post">
			<input id="cid" type="hidden" name='cid' value="">
		
	<a href="remarkMap.do?method=getRemarkMap"></a>
	<div region="north" title="检索" class="st-north" border="false"
		style="height:61px" data-options="collapsible:false">
		<lable class="divlabel">客户名称：</lable>
		<input type="text" id="cutomer_name" style="width:150px" />
		<lable class="divlabel">地址：</lable>
		<input type="text" id="driver_address" style="width:150px;" /> &nbsp <a
			class="easyui-linkbutton" onclick="searchRemark()"
			data-options="iconCls:'icon-search'" id="searchId">查询</a>

	</div>
	<div region="center" title="客户时效信息" region="center"
		style="background: #eee; overflow-y:hidden">
		<table id="dg"></table>
	</div>
	<div id="tb">
<!-- 		<a href="#" class="easyui-linkbutton" id="tbadd" onclick="add()"
			data-options="iconCls:'icon-add',plain:true" style="display: none">新增</a>
		<a href="#" class="easyui-linkbutton" id="tbmodify" onclick="modify()"
			data-options="iconCls:'icon-edit',plain:true" style="display: none">修改</a>
		<a href="#" class="easyui-linkbutton" id="tbdel"
			onclick="deleteRemark()"
			data-options="iconCls:'icon-remove',plain:true" style="display: none">删除</a>
		<a href="#" class="easyui-linkbutton" id="tbreset" onclick="reset()"
			data-options="iconCls:'icon-reset',plain:true">重置</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="openAging"  data-options="iconCls:'icon-ok'" onclick="oAging()">打开时效提醒</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" id="closeAging"  style="display: none" data-options="iconCls:'icon-no'" onclick="cAging()"><span>关闭时效提醒</span></a>
			 -->
		
	</div>
	<input id="functionname" type="hidden" value="${function}">
	</form>
	<script type="text/javascript" src="./js/function.js"></script>
</body>
</html>
