<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

<!DOCTYPE HTML>
<html>
<head>


<title>更新发货客户信息</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>

</head>
<%
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
	String flg = (String) request.getAttribute("flg");
	String cid=request.getAttribute("cid").toString();
%>
<script type="text/javascript">
	var customernum='';
	var customername='';
	$(function() {
	
		var cid='<%=cid%>';
			$.ajax({
				url : 'remarkMap.do?method=getCustomerbyid',
				data : {
					cid : cid
				},
				success : function(data) {
					$('#Regform').form('load', data);
					customernum = data.customer_num;
					customername = data.customer_name;
					
				}
			});
		
		var $target = $('input,textarea,select');
		$target.bind('keydown', function(e) {
			var key = e.which;
			if (key == 13) {
				e.preventDefault();
				var nxtIdx = $target.index(this) + 1;
				$target.eq(nxtIdx).focus();
			}
		});
		$("#Regform").validationEngine('attach', {
			promptPosition : 'topRight:-15,0'
		});
	});
	var submitForms = function($dialog, $grid, $pjq, $mainMenu) {
		var customer_num = $.trim($("#customer_num").val());
		var customer_name = $.trim($("#customer_name").val());
		var numflag = 0;
		var nameflag = 0;
		 $.ajax({
			type : "POST",
			async: false,
			url : 'remarkMap.do?method=getcustomer_num',
			data : {
				customer_num : customer_num
			},
			success : function(data) { 
				  if (data.flag== false) {
						numflag = 0;
				} else { 
						numflag = 1;
				}
			}});
			 $.ajax({
			type : "POST",
			async: false,
			url : 'remarkMap.do?method=getcustomer_name',
			data : {
				customer_name : customer_name
			},
			success : function(data) { 
				  if (data.flag== false) {
						nameflag = 0;
				}else{ 
						nameflag = 1;
				}
			}});
			if(nameflag==0&&customer_num!=customernum){
			$pjq.messager.alert('警告', '客户姓名重复', 'info');
			}else if(numflag==0&&customername!=customer_name){
			$pjq.messager.alert('警告', '客户编码重复', 'info');
			}else{
			if ($("#Regform").validationEngine('validate')) {
						//可提交
						$pjq.messager.confirm('修改客户', '确定要修改吗?', function(r) {
							if (r) {
								$.ajax({
									type : "POST",
									url : 'remarkMap.do?method=savefroupdateCustomer',
									data : $('#Regform').serialize(),
									success : function(msg) {
										if (msg.flag) {
											$pjq.messager.alert('修改客户',
													'客户修改成功', 'info');
											$dialog.dialog('close');
											$grid.datagrid('reload');
										}else{
											$pjq.messager.alert('修改客户',
													'客户修改失败', 'info');
										};
									}
								});
							}
						});
					} else {
						$pjq.messager.alert('修改客户', '必填信息不可为空', 'info');
					}
			}
	};
</script>
<body>
	<form action="" method="post" id="Regform">
		<fieldset>
			<table class="tableclass">
				<tr>
					<th colspan="4">基本信息</th>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>客户编码:</td>
					<td class="td2"><input type="text" class="validate[required]"
						id="customer_num" name="customer_num">
						<input type="hidden" id="customer_id" name="customer_id" value="<%=cid%>"></td>
					<td><font color="red" style="margin-right:10px">*</font>客户名称:</td>
					<td class="td2"><input type="text" class="validate[required]"
						id="customer_name" name="customer_name"></td>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>联系人：</td>
					<td class="td2"><input type="text" class="validate[required]"
						id="customer_people" name="customer_people"></td>
					<td><font color="red" style="margin-right:10px">*</font>联系方式：</td>
					<td class="td2"><input type="text" class="validate[required]"
						id="customer_tel" name="customer_tel"></td>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>地址:</td>
					<td colspan="3" class="td2">
					<textarea class="easyui-textarea validate[required]" id="customer_address"
							cols="45" rows="2" name="customer_address"></textarea> 
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>主要业务:</td>
					<td colspan="3" class="td2">
					<textarea class="easyui-textarea validate[required]" id="customer_business"
							cols="60" rows="6" name="customer_business"></textarea> 
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>简介:</td>
					<td colspan="3" class="td2">
					<textarea class="easyui-textarea validate[required]" id="customer_profile"
							cols="60" rows="6" name="customer_profile"></textarea> 
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>合作经历:</td>
					<td colspan="3" class="td2">
					<textarea class="easyui-textarea validate[required]" id="customer_experience"
							cols="60" rows="6" name="customer_experience"></textarea> 
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
