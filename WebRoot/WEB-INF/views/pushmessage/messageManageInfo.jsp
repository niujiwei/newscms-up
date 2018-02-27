<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>消息管理页面</title>
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="/inc/incbootstrap.jsp"></jsp:include>
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="./js/function.js"></script>
</head>
<%
	
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
	
%>
<script type="text/javascript">
    var dialog;
    var grid;
    var userId='<%=user.getId()%>';
	$(function() {
		grid = $("#dg").datagrid({
			url : 'messageManage.do?method=getMessage',
			border : false,
			rownumbers : true,
			fit : true,
			singleSelect : false,
			selectOnCheck : true,
			checkOnSelect : true,
			multiSort : true,
			rowStyler : function(rowIndex, rowData) {
				//任务完成100%， 并且已审核通过，背景色置灰  
				
				if (rowData.orderMsgState == "1"&&rowData.receiveUserId==userId) {
					return 'color:red';
				}
				if (rowData.orderMsgState == "1") {
					return 'color:blue';
				}
			},
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'sendUserName',
				title : '发件人',
				width : 130,
				align : 'center'
			}, {
				field : 'updatetime',
				title : '发件时间',
				width : 130
			}, {
				field : 'receiveUserName',
				title : '接收人',
				width : 130,
				align : 'center'
			}, {
				field : 'orderMsgTitle',
				title : '标题',
				width : 130,
				align : 'center'
			},

			{
				field : 'orderMsgState',
				title : '读取状态',
				width : 130,
				align : 'center',
				formatter : function(val, row, index) {
					if (val == 1) {
						return "未读";
					} else if (val == 2) {
						return "已读";
					}
				}

			}, {
				field : 'receivetime',
				title : '阅读时间',
				width : 130,
				align : 'center'
			},
			
			 {
				field : 'orderMsg',
				title : '信息内容',
				width : 400
			},

			] ],
			pagination : true,
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchMsg();
			}
		};
		//能更多条件
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
				$("#sendUserName").val("");

			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 60
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
				$("#sendUserName").val("");

			}
		});

	});
	function deleteEver() {
		var row = $("#dg").datagrid('getSelections');
		var orderMsgId2 = [];
		if (row.length > 0) {
			for ( var i = 0; i < row.length; i++) {
				orderMsgId2.push(row[i].orderMsgId);
			}
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							$.ajax({
								type : "POST",
								async : false,
								url : 'messageManage.do?method=deleteMsg',
								data : {
									id : orderMsgId2.join(",")
								},
								success : function(data) {
									if (data != 0) {
										$.messager.alert('信息删除', '删除信息成功!',
												'info');
										$("#dg").datagrid('reload');
									} else {
										$.messager.alert('信息删除', '删除信息成功!',
												'info');
									}
								}
							});
						}
					});
		} else {
			$.messager.alert('删除信息', '请选择一行!', 'info');
		}
	}
	function searchMsg() {
		$("#dg").datagrid('load', {
			receiveUserName : $.trim($("#receiveUserName").val()),
			orderMsgState : $.trim($("#orderMsgState").combobox("getValue")),
			startDate : $.trim($("#startDate").val()),
			endDate : $.trim($("#endDate").val()),
			//sendUserName : $.trim($("#sendUserName").val()),
			orderMsgTitle : $.trim($("#orderMsgTitle").val()),
		});
	};
	function reset() {
		$("#receiveUserName").val("");
		$("#orderMsgState").combobox("setValue", "");
		$("#startDate").val("");
		$("#endDate").val("");
		$("#sendUserName").val("");
		$("#orderMsgTitle").val("");
		searchMsg();
	}
	function sendMessageManage() {
		dialog = parent.jy.modalDialog({
			title : '消息发送',
			url : 'messageManage.do?method=sendMessageManage',
			width : 1150,
			height : 620,
			onClose : function() {
				$("#dg").datagrid('reload');
			}
		});
	}
	function lookMessage(){
	    var row = $("#dg").datagrid('getSelections');
	    if(row.length!=1) return 	$.messager.alert('查看消息', '请选择一行!', 'info');
		dialog = parent.jy.modalDialog({
			title : '消息发送',
			url : 'messageManage.do?method=toLookMessageInfo&pid='+row[0].orderMsgId,
			width : 800,
			height : 620,
			onClose : function() {
				$("#dg").datagrid('reload');
			}
		});
	
	}
	
</script>
<body class="easyui-layout">
	<a href="remarkMap.do?method=getRemarkMap"></a>
	<div region="north" title="检索" class="st-north" border="false"
	 collapsible="false">
		<lable>发件时间:</lable>
		<input id="startDate" class="Wdate" readonly="readonly"
			style="width:120px;height:22px; "
			onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" />
		- <input id="endDate" class="Wdate" readonly="readonly"
			style="width:120px;height:22px;"
			onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
		<lable class="divlabel">接收人：</lable>
		<input type="text" id="receiveUserName" style="width:150px" />
		<lable class="divlabel">读取状态：</lable>
		<select id="orderMsgState" class="easyui-combobox"   name="dept"
			style="width:100px;" data-options="panelHeight : 'auto',editable:false">
			<option value="" selected="selected">全部</option>
			<option id="" value="2">已读</option>
			<option id="" value="1">未读</option>
		</select> &nbsp; <a class="easyui-linkbutton" onclick="searchMsg()"
			data-options="iconCls:'icon-search'" id="searchId">查询</a> <a
			id="btnMoreSearch" class="btn btn-sm btn-link" type="button"
			style="margin-top:0px;" href="javascript:void(0)" state="close">更多条件</a>
		<div id="searchInfoId" style="display: none">
		    <lable >信息标题：</lable>
		    <input type="text" id="orderMsgTitle" style="width:150px" />
			<lable class="divlabel">发件人：</lable>
			<input type="text" id="sendUserName" style="width:150px" />
		</div>
	</div>
	<div region="center" title="信息接收" region="center"
		style="background: #eee; overflow-y:hidden">
		<table id="dg">
		</table>
	</div>
	<div id="tb">
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" id="tbdel"
			onclick="deleteEver()" data-options="iconCls:'icon-remove',plain:true">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="tbdel"
			onclick="lookMessage()" data-options="iconCls:'icon-remove',plain:true">查看消息</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" id="tbdel"
			onclick="sendMessageManage()"
			data-options="iconCls:'icon-remove',plain:true">发送信息</a> <a
			href="javascript:void(0)" class="easyui-linkbutton" id="tbreset"
			onclick="reset()" data-options="iconCls:'icon-reset',plain:true">重置</a> -->
		<form action="" id="cform" method="post"></form>
	</div>
	<!-- <input id="functionname" type="hidden" value=${function}> -->
</body>
</html>
