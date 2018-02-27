<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>


<title>设备信息</title>
<meta charset="utf-8">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<%
	String flg = (String) request.getAttribute("flg");
%>
</head>
<script type="text/javascript">
	//$dialog  关闭digkog
 	//$grid 数据显示  刷新
 	// $pjq 弹出提示
 	//$mainMenu 预留传的参数
	$(function(){
	 $("#Regform").validationEngine('attach', { 
				 promptPosition:'topRight:-15,0'
				 });
		 /* 根据车牌号查询 */
  		  $("#user_id").select2({
    		 placeholder: "用户名",	//默认文本框显示信息
   			 minimumInputLength: 1,	//最小输入字符长度
   			 multiple: false,				//设置多项选择，false则只能单选
   			 allowClear:true,
    		 maximumSelectionSize: 5,
    		 query: function (query) {
			    	$.ajax({
					   type: "POST",
					   url: "equipment.do?method=getUserName",
					   data: {username:query.term},   //传递输入框中的值
					   success: function(msg){
			 		   	var msg=$.parseJSON(msg);		
			 			var data = {results: []};		
						$.each(msg,function(x,y){
							data.results.push({id: y.name, text: y.name,drivername:y.dn});
						});
				        query.callback(data);
					   }
					});
			    }
			    }).on("select2-selecting", function(e) {
			    	$("#drivername").val(e.choice.drivername);
			    });
		var pid='<%=flg%>';
		$.ajax({
			url : 'equipment.do?method=getUpdateEquipment',
			data : {
				pid : pid
			},
			success : function(data) {
				if (data.plate_number == null) {
					data.plate_number = '';
				}
				if (data.department_Name == null) {
					data.department_Name = '';
				}
				//	$("#carid").select2('data',{id:data.car_id,text:data.plate_number});\
				$("#user_id").prev("div").find("span.select2-chosen").text(
						data.username);
				$('#Regform').form('load', data);
				$('#cc').combotree('setValue', data.department_Name);
			}
		});
		$('#cc')
				.combotree(
						{
							url : "depn.do?method=getTree&&id=",
							panelHeight : 100,
							onBeforeLoad : function(node, param) {
								if (node == null) {
									$('#cc').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
								} else {

									$('#cc').combotree('tree').tree('options').url = "depn.do?method=getTree&&id="
											+ node.id;//加载下层节点
								}
							}
						});
	});

	var submitFormEdit = function($dialog, $grid, $pjq, $mainMenu) {
		if ($("#Regform").validationEngine('validate')) {
			$pjq.messager.confirm('修改设备', '确定要修改吗?', function(r) {
				if (r) {

					$.ajax({
						type : "POST",
						url : 'equipment.do?method=updateEquipment',
						data : $('#Regform').serialize(),
						success : function(msg) {
							if (msg.ans != null && msg.ans != '') {
								$pjq.messager.alert('新增设备', '设备号已存在', 'info');
								return;
							} else {
								if (msg.flag) {
									$pjq.messager.alert('修改设备', '设备修改成功',
											'info');
									$dialog.dialog('close');
									$grid.datagrid('reload');
								}
							}
						}
					});
				}
			});
		}
	};
</script>
<body style="padding: 0px;margin:0px;">
	<form action="" method="post" id="Regform">
		<input type="hidden" name="equipment_id" />
		<fieldset>
			<table class="tableclass">
				<tr>
					<th colspan="4">基本信息</th>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>设备号:</td>
					<td class="td2" style="padding-right: 10px;"><input
						class="validate[required]" id="" name="equipment_number" readonly="readonly"/>
					</td>
						<td><font color="red" style="margin-right:10px">*</font>车牌号:</td>
				<td class="td2" style="padding-right: 10px;"><input
						name="car_number" id="car_number" class="validate[required]"
						style="width: 150px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>用户名:</td>
					<td class="td2" style="padding-right: 10px;"><input
						name="username" id="user_id" style="width: 150px;" /> <!-- <input type="hidden" name="username" id="username"> -->
					</td>

					<td><font color="red" style="margin-right:10px">*</font>司机姓名:</td>
					<td class="td2" style="padding-right: 10px;"><input
						name="drivername" id="drivername" style="width: 150px;" />
					</td>

				</tr>
				<tr>
					
					<td><font color="red" style="margin-right:10px">*</font>所属机构:</td>
					<td class="td2" style=""><input class="validate[required]"
						name="institutions_id" id="cc" />
					</td>
					<td><font color="red" style="margin-right:10px">*</font>服务到期时间:</td>
					<td class="td2" style="padding-right: 10px;"><input id="fxz"
						class="Wdate validate[required]" name="end_date"
						readonly="readonly" onfocus="WdatePicker({isShowWeek:true})" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>