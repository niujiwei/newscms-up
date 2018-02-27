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
	String pid = (String) request.getAttribute("pid");
%>
<script type="text/javascript">
  var grid;
  //登入用户绑定客户id
  var pid ='<%=pid%>';
	$(function() {
		$.ajax({
			type : "POST",
			url : 'messageManage.do?method=getLookMessageInfo',
			data : {
				pid : pid,
			},
			success : function(msg) {
				if(msg.success){
				   var data = msg.success;
				   $('#person').val(data.sendUserName);
				   $('#orderMsgTitle').val(data.orderMsgTitle);
				   $('#ordeMsgs').val(data.orderMsg);
				   
				}
			}
		});
		$.ajax({
			type : "POST",
			url : 'messageManage.do?method=updateMessageState',
			data : {
				pid : pid,
				state:'2'
			},
			success : function(msg) {
				if(msg.success){
				 console.info(msg.success);
				   
				}
			}
		});
	});
</script>

<body class="easyui-layout" style="width: 1140px; height: 570px">
	<div id="mainPanle" region="center" style="width: 570px;"
		data-options="region:'west',title:'信息内容',split:false">

		<form action="" method="post" id="Regform">
			<fieldset>
				<table class="tableclass">
					<tr>
						<th colspan="4">发送平台</th>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>发件人:</td>
						<td class="td2"><textarea class=""
								id="person" name="person" cols="20" rows="2" readonly ="readonly"></textarea> <br>
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>标题:</td>
						<td class="td2"><textarea readonly ="readonly"
								id="orderMsgTitle" name="orderMsgTitle" cols="70" rows="2"></textarea>
							<br></td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>消息内容:</td>
						<td class="td2"><textarea readonly ="readonly"
								id="ordeMsgs" name="ordeMsgs" cols="70" rows="15"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>

</body>
</html>
