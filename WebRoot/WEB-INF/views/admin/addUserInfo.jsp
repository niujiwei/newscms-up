<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="js/json2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<script type="text/javascript" src="./js/function.js"></script>
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="./js/select2/select2_expand.js"></script>
<script type="text/javascript">
	$(function() {
		var da = "";
		/* 查询司机信息 */
		$("#number_search2").select2({
			placeholder : "请选择司机", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			multiple : false, //设置多项选择，false则只能单选
			allowClear : true,
			maximumSelectionSize : 5,
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "user.do?method=getDrivers",
					data : {
						search : query.term
					}, //传递输入框中的值
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
						});
						query.callback(data);
					}
				});
			}
		});
		$('#flag').combobox({
			onChange : function(newValue, oldValue) {
				if (newValue == "1") {

					$("#td").removeAttr("style");
					$("#tds").removeAttr("style");
					$("#d2").removeAttr("style");

					$("#custom_search").select2("val", "");

					$("#newtds").attr("style", "display:none");
					$("#newtd").attr("style", "display:none");

					$("#othertds2").attr("style", "display:none");
					$("#othertds").attr("style", "display:none");
					$("#othertdstd1").attr("style", "display:none");
				} else if (newValue == "0") {

					$("#number_search2").select2("val", "");
					$("#td").attr("style", "display:none");
					$("#tds").attr("style", "display:none");

					$("#newtds").attr("style", "display:none");
					$("#newtd").attr("style", "display:none");
					$("#custom_search").select2("val", "");

					$("#othertds2").attr("style", "display:none");
					$("#othertds").attr("style", "display:none");
					$("#othertdstd1").attr("style", "display:none");

				} else if (newValue == "2") {
					$("#number_search2").select2("val", "");
					/*   $("#td").attr("style", "display:none");
					  $("#tds").attr("style", "display:none"); */
					$("#sutrid").removeAttr("style");
					$("#td").removeAttr("style");
					$("#tds").removeAttr("style");
					$("#d2").removeAttr("style");
					$("#newtds").removeAttr("style");
					$("#newtd").removeAttr("style");
					$("#newd2").removeAttr("style");
					$("#othertds2").attr("style", "display:none");
					$("#othertds").attr("style", "display:none");
					$("#othertdstd1").attr("style", "display:none");
				} else if (newValue == "3" ||newValue=="4") {

					$("#othertds").removeAttr("style");
					$("#othertds2").removeAttr("style");
					$("#othertdstd1").removeAttr("style");
					$("#number_search2").select2("val", "");
					$("#td").attr("style", "display:none");
					$("#tds").attr("style", "display:none");

					$("#newtds").attr("style", "display:none");
					$("#newtd").attr("style", "display:none");
					$("#custom_search").select2("val", "");
				}

			}
		});
	/* $('#cc')
				.combotree(
						{
							url : "depn.do?method=getTree&&id=",
							width : 150,
							onBeforeLoad : function(node, param) {
								 console.info(node);
							    console.info(param);
								if (node == null) {
									$('#cc').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
								} else {

									$('#cc').combotree('tree').tree('options').url = "depn.do?method=getTree&&id="
											+ node.id;//加载下层节点
								}

							}
						});  */
	 $('#cc')
				.combotree(
						{
							url : "depn.do?method=getDepartmentTree&&id=",
							width : 150,
							valueField:'id',   
                            textField:'text' ,
							onBeforeLoad : function(node, param) {
							    if (node == null) {
									$('#cc').combotree('tree').tree('options').url = "depn.do?method=getDepartmentTree&&id=";//加载顶层节点
								} else {

									$('#cc').combotree('tree').tree('options').url = "depn.do?method=getDepartmentTree&&id="
											+ node.id;//加载下层节点
								}

							}
						});  
		$("#flag").combobox("setValue", "");
		var oneid = 0;
		var twoid = 0;
		var threeid = 0;
		$('#province').combobox(
				{
					url : 'driver.do?method=getNewFinalPositionCounty',
					valueField : 'id',
					textField : 'text',
					width : 120,
					editable : false,
					onSelect : function(record) {
						oneid = record.citye_parent_id;
						twoid = 0;
						$('#city').combobox("clear");
						$('#county').combobox("clear");
						$('#city').combobox(
								'reload',
								'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
										+ oneid);
						$('#county').combobox(
								'reload',
								'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
										+ twoid);
					}
				});
		$('#city')
				.combobox(
						{
							url : 'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
									+ oneid,
							valueField : 'id',
							width : 120,
							textField : 'text',
							editable : false,
							onSelect : function(record) {
								twoid = record.citye_parent_id;
								$('#county').combobox("clear");
								$('#county').combobox(
										'reload',
										'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
												+ twoid);
							}
						});
		$('#county')
				.combobox(
						{
							url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
									+ twoid,
							valueField : 'id',
							width : 120,
							textField : 'text',
							editable : false

						});
		//绑定供应商信息
		$("#custom_search").select2({
			placeholder : "请选择供应商", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			multiple : false, //设置多项选择，false则只能单选
			allowClear : true,
			maximumSelectionSize : 5,
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "user.do?method=getJySuppliers",
					data : {
						search : query.term
					}, //传递输入框中的值
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
						});
						query.callback(data);
					}
				});
			}
		});
		
		//绑定项目部信息
		$("#other_coustomer").select2({ //自动完成
			placeholder : "请选择发货客户名称",
			minimumInputLength : 1,
			multiple : false,
			allowClear : true,
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "shipOrder.do?method=getCustomName",
					data : {
						name : query.term
					}, //传递你输入框中的值
					success : function(msg) {
						var msg = $.parseJSON(msg);
						var data = {
							results : []
						};
						$.each(msg, function(x, y) {
							data.results.push({
								id : y.id,
								text : y.name,
								people : y.people,
								tel : y.tel,
								cusotmerid : y.customerid
							});
						});
						query.callback(data);
					}
				});
			},
		}).on("select2-selecting", function(f) {
            $("#customerid").val(f.object.cusotmerid);
        });
	});
	
	//添加用户信息
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		var flag = $("#flag").combobox('getValue');
		var myform = document.getElementById("form");
		var name = document.getElementById("user_name1").value;
		var password = document.getElementById("password").value;
		var realName = document.getElementById("user_realName1").value;
		var did = $("#cc").combotree('getValue');
		var gpnumberd = $.trim($("#number_search2").select2("val"));
		var b = "";
		var custom_search = $.trim($("#custom_search").select2("val"));
		var poin = $("#province").combobox('getText');
		var city = $("#city").combobox('getText');
		var county = $("#county").combobox('getText');
		$("#user_address").val(poin + city + county);
		var customerId = $.trim($("#customerid").val());
		$.ajax({
			type : "POST",
			async : false,
			url : 'user.do?method=checkUser',
			data : {
				name : name
			},
			success : function(data) {
				b = data;
			}
		});
		if (name == "") {
			parent.$.messager.alert('警告!', '请输入用户名称!', 'info');
		} else if (password == "") {
			parent.$.messager.alert('警告!', '请输入密码!', 'info');
		} else if (realName == "") {
			parent.$.messager.alert('警告!', '请输入真实姓名!', 'info');
		} else if (did == "") {
			parent.$.messager.alert('警告!', '请选择部门!', 'info');
		} else if (b == true) {
			parent.$.messager.alert('警告!', '用户名重复!', 'info');
		} else if (flag == null || flag == "") {
			parent.$.messager.alert('警告!', '请选择类型!', 'info');
		} else if (flag == 1 && gpnumberd == "") {
			parent.$.messager.alert('警告!', '请绑定司机!', 'info');
		} else if (flag == 2 && custom_search == "") {
			parent.$.messager.alert('警告!', '请绑定供应商!', 'info');
		} else if (flag == 2 && gpnumberd == "") {
			parent.$.messager.alert('警告!', '请绑定司机!', 'info');
		} else if (poin == "") {
			parent.$.messager.alert('警告!', '请选择所属区域!', 'info');
		} else if (flag == 3 && customerId == "") {
			parent.$.messager.alert('警告!', '请选择发货客户!', 'info');
		}else if (flag == 4 &&  customerId == "") {
			parent.$.messager.alert('警告!', '请选择发货客户!', 'info');
		} 
		else {
			$.ajax({
				type : "POST",
				url : 'user.do?method=saveUser',
				data : $('#form').serialize(),
				success : function() {
					$pjq.messager.alert('添加用户', '用户添加成功', 'info');
					$dialog.dialog('close');
					$grid.datagrid('reload');

				}
			});

		}
	};
</script>
</head>

<body>
	<div id="dlg1">
		<form action="" method="post" id="form">
			<fieldset>
				<input type='hidden' id="hiddenid" name='id'> <input
					type='hidden' id="user_address" name="user_address">
				<table class="tableclass">
					<tr>
						<th colspan="4">添加用户</th>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>用户名称:</td>
						<td class="td2"><input id="user_name1" type="text"
							name="username" class="search-text">
						</td>
						<td><font color="red" style="margin-right:10px">*</font>用户密码:</td>
						<td class="td2"><input id="password" type="password"
							name="password" class="search-text">
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>真实姓名:</td>
						<td class="td2"><input class="search-text" name="realName"
							id="user_realName1">
						</td>
						<td><font color="red" style="margin-right:10px">*</font>所在部门:</td>
						<td class="td2"><input id="cc" name="did">
						</td>
					</tr>
					<!-------------------------------------------- start 新增 njw start----------------------------------->
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>所属区域:</td>
						<td class="td2" colspan="3" style="height:40px"><input
							id="province" name="user_province">省 <input id="city"
							name="user_city">市 <input id="county" name="user_county">区</td>
					</tr>

					<!------------------------------------------------- end 新增 njw end ---------------------------------->
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>类型:</td>
						<td class="td2"><select id="flag" class="easyui-combobox"
							name="flag" data-options="panelHeight : 'auto',editable:false"
							style="width:150px;">
								<option value="1">司机</option>
								<option value="2">供应商</option>
								<option value="3">省内项目部</option>
								<option value="4">省外项目部</option>
						</select></td>
						<td id="tds" style="display: none"><font color="red"
							style="margin-right:10px">*</font>绑定司机:</td>
						<td class="td2" id="td" style="display: none">
							<div id="d2" style="display: none">
								<input type="hidden" style="width:150px;" id="number_search2"
									name="car" />
							</div>
						</td>

						<!-----------------------------------------------------  start 新增 njw start------------------------------------>
						<td id="othertds" style="display: none"><font color="red"
							style="margin-right:10px">*</font>绑定发货客户:</td>
						<td class="td2" id="othertdstd1" style="display: none">
							<div id="othertds2" style="display: none">
								<input type="hidden" style="width:150px;" id="other_coustomer"
									/>
								<input type="hidden" style="width:150px;" id="customerid"
									name="customerid" />
							</div>
						</td>
					</tr>
					<tr style="display: none" id="sutrid">
						<td id="newtds" style="display: none"><font color="red"
							style="margin-right:10px">*</font>绑定供应商:</td>
						<td class="td2" id="newtd" style="display: none">
							<div id="newd2" style="display: none">
								<input type="hidden" style="width:150px;" id="custom_search"
									name="suppliers_id" />
							</div>
						</td>
					</tr>

					<!-----------------------------------------------------  start 新增 njw start------------------------------------>
				</table>
			</fieldset>
			<!-- <div id="buttons">
            <input type="button" onclick="save()" value="保存" class="btncss"
                id="save" />
            <input type="button" onclick="closeps('save');"
                value="关闭" class="btncss" />
        </div>-->
		</form>
	</div>
</body>
</html>
