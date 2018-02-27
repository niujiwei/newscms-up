<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>消息发送</title>

<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="/inc/incbootstrap.jsp"></jsp:include>
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>

</head>
<%
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
<script type="text/javascript">
  var grid;
  //登入用户绑定客户id
  var pid ='<%=user.getId()%>';
	$(function() {
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchUser();
			}
		};
		grid = $("#dg")
				.datagrid(
						{
							url : 'messageManage.do?method=getMessagePeople&pid='
									+ pid,
							border : false,
							rownumbers : true,
							singleSelect : false,
							selectOnCheck : true,
							checkOnSelect : true,
							multiSort : true,
							/*onCheck : function(index, data) {
								selectData(data);
								delectData(data);
							},*/
							toolbar : '#tb',
							columns : [ [ {
								field : 'ck',
								checkbox : true
							}, {
								field : 'realName',
								title : '联系人',
								width : 120,
								align : 'center'
							}, {
								field : 'user_address',
								title : ' 地址',
								width : 120,
								align : 'center'
							}, {
								field : 'userPhone',
								title : '联系电话',
								width : 120,
								align : 'center'
							}, ] ],
						/* 	pagination : true,
							pageSize : 500,
							pageList : [ 500, 1000 ], */

						});

	});
	function addMessage() {
		var id = $("#id").val();
		var person = $("#person").val();
		var ordeMsg = $("#ordeMsgs").val();
		var orderMsgTitle = $("#orderMsgTitle").val();
		if (person == "") {
			$.messager.confirm('错误提醒', '您没有选择联系人', 'info');
		} else {
			$.messager
					.confirm(
							'发送消息',
							'确定要发送该条消息吗?',
							function(r) {
								if (r) {
									$
											.ajax({
												type : "POST",
												url : 'messageManage.do?method=sendOrderMsg&pid='
														+ pid,
												data : {
													id : id,
													person : person,
													ordeMsg : ordeMsg,
													orderMsgTitle : orderMsgTitle
												},
												success : function(msg) {
													if (msg != 0) {
														$.messager.alert(
																'发送消息',
																'发送消息成功',
																'info');
													} else {
														$.messager.alert(
																'发送消息',
																'发送消息失败',
																'info');
													}
													;
												}
											});
								}
							});
		}
		;
	}
	function searchUser() {
		$("#dg").datagrid('load', {
			realname : $.trim($("#order_num").val()),
			user_address : $.trim($("#user_address").val()),

		});
	};

	function delectData(data) {
		var index = $('#dg').datagrid('getRowIndex', data);
		$('#dg').datagrid('deleteRow', index);

	}
	function selectData(data) {
		var selectphone = $("#person").text();
		var selectId=$("#id").text();
		
		var pIndex = selectphone.indexOf(data.realName);
		var idIndex = selectId.indexOf(data.id);
	    if(pIndex=='-1') $("#person").append(data.realName);
		if(idIndex=='-1') $("#id").append(data.id);
		
		
		if ($("#person").text() != "" && $("#person").text() != null &&pIndex=='-1') {
			$("#person").append(",");
		}
		if ($("#id").text() != "" && $("#id").text() != null && idIndex=='-1' ) {
			$("#id").append(",");
		}
		
		$("#person").val($("#person").text());
		$("#id").val($("#id").text());
	}
	function determine() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length ==0) 	return $.messager.alert(
            '发送消息',
            '请选择要发送的消息',
            'info');
            $.each(row, function(i, d) {
                selectData(d);
            });
            $.each(row, function(i, d) {
                delectData(d);

            });
    }
</script>

<body class="easyui-layout" style="width: 1140px; height: 570px">
	<div region="east" border="true" title="检索" style="width: 570px;"
		id="eastId" data-options="collapsed:false">
		<div style="padding:10px;">
			<label>联系人：</label>
			<input id="order_num" name="user_name" type="text"
				class="search-text1">
			<label>地址：</label>
			<input id="user_address" name="user_address" type="text"
				class="search-text1"> 
				<a class="easyui-linkbutton"
				onclick="searchUser()" data-options="iconCls:'icon-search'"
				id="searchId">查询</a>
			<a class="easyui-linkbutton"
			   onclick="determine()" data-options="iconCls:'icon-edit'"
			   id="determine">确定</a>

		</div>
		<div style="height: auto">
			<table id="dg" title="联系人"></table>
		</div>
	</div>
	<div id="mainPanle" region="center" style="width: 570px;"
		data-options="region:'west',title:'信息内容',split:false">

		<form action="" method="post" id="Regform">
			<fieldset>
				<table class="tableclass">
					<tr>
						<th colspan="4">发送平台</th>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>联系人:</td>
						<td class="td2"><textarea class="validate[required]"
								id="person" name="person" cols="20" rows="2"></textarea> <br>
							<font color="red" style="margin-top:10px">*（可同时选择多个联系人，之间用逗号隔开）</font>
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>标题:</td>
						<td class="td2"><textarea class="validate[required]"
								id="orderMsgTitle" name="orderMsgTitle" cols="70" rows="2"></textarea>
							<br></td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>消息内容:</td>
						<td class="td2"><textarea class="validate[required]"
								id="ordeMsgs" name="ordeMsgs" cols="70" rows="15"></textarea> <br>
							<a href="javascript:void(0)" class="easyui-linkbutton"
							style="float: right" data-options="iconCls:'icon-add',plain:true"
							onclick="addMessage()" id="tbadd">发送短信消息</a> <input type="hidden"
							id="id" name="userID">
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>

</body>
</html>
