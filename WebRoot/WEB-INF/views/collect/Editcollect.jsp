<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%


%>
<%
	String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() +"/surmax/"; 
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>费用核销</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
	<jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
	<script type="text/javascript" src="./js/select2/select2.js"></script>
	<script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>
	
  
  </head>
  <%String flg = (String) request.getAttribute("flg"); %>
  <script type="text/javascript">
	  	var dialog;
 		var grid;
  		$(function(){
  			$("#Regform").validationEngine('attach', { 
				 promptPosition:'topRight:-15,0'
				 });
				
		var pid='<%=flg%>';
		
		/* $.ajax({
			url : 'payable.do?method=getUpdate3',
			data : {
				id : pid
			},
			success : function(data) {
			alert(data.custom_id)
				$('#Regform').form('load', data);
				
			} 
		});*/
		$("#customid").val(pid);
		
  	}); 		
  var submitFormEdit = function($dialog, $grid, $pjq, $mainMenu) {
		if($("#Regform").validationEngine('validate')){
		$pjq.messager.confirm('费用核销', '确定要核销吗?', function(r) {
			if (r) {
				$.ajax({
					type : "POST",
					url : 'collect.do?method=update3',
					data : $('#Regform').serialize(),
					success : function(msg) {		
						if (msg.flag==false) {
							$pjq.messager.alert('费用核销', '费用核销成功', 'info');
							$dialog.dialog('close');
							$grid.datagrid('reload');
						};
					}
				});   
			} 
		});
		} else{
				
			    $pjq.messager.alert('费用核销', '必填信息不可为空', 'info');
			  }
	};

  </script>
  <body class="easyui-layout">
    <form action="" method="post"  id="Regform">
    <input type="hidden" id="customid"  name="custom_id"  >
 	<table class="tableclass">
			<tr>
					<td>			
					<font color="red" style="margin-right:10px">*</font>付款方式：</td>
					<td class="td2" colspan="3">	
					<input type="radio" name="write_off3" value="1"  style="width:14px" /><label style="margin-left: 10px">现金</label>
					<input type="radio"  name="write_off3" value="0"  style="width:14px" /><label  style="margin-left: 10px">刷卡</label>
					</td> 
				</tr>
				<tr>
				<td style="color: blue;">备注:</td>
				<td colspan="3" class="td2"><textarea class="easyui-textarea" id="remarks_fee1"
						cols="40" rows="2" name="remarks_fee1"></textarea></td>
			</tr>			
			</table>
		  </form>	
  </body>
</html>