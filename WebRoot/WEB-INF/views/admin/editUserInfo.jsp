<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
	String userId = (String) request.getAttribute("userId");
%>
<title></title>
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
        var oneid = 0;//定义三级联动的省节点
        var twoid = 0;//定义三级联动的市节点
        var threeid =0;//定义三级联动的区节点
        $(function() {
           //取出用户id
        var userid ='<%=userId%>';
		var flag;//定义标记
		var flag2;
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

		/* //定义部门树
		$('#cc2')
				.combotree(
						{
							url : "depn.do?method=getDepartmentTree",
							width : 150,
							onBeforeLoad : function(node, param) {
								if (node == null) {
									$('#cc2').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
								} else {

									$('#cc2').combotree('tree').tree('options').url = "depn.do?method=getTree&&id="
											+ node.id;//加载下层节点
								}

							},
							onSelect : function(rowIndex, rowData) {
								$("#department_id").val(rowIndex.id);
							}
						}); */
	    //定义部门树
		$('#cc2')
				.combotree(
						{
							url : "depn.do?method=getDepartmentTree&id=",
							width : 150,
							onBeforeLoad : function(node, param) {
								if (node == null) {
									$('#cc2').combotree('tree').tree('options').url = "depn.do?method=getDepartmentTree&id=";//加载顶层节点
								} else {

									$('#cc2').combotree('tree').tree('options').url = "depn.do?method=getDepartmentTree&id="
											+ node.id;//加载下层节点
								}

							},
							onSelect : function(rowIndex, rowData) {
								$("#department_id").val(rowIndex.id);
							}
						});
						
		//查询司机的select2
		$("#number_search4").select2({
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
						search : query.term,
						xxx : 'hahaha'
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
		//当切换类型的时候
		$('#flag2').combobox({
			onChange : function(newValue, oldValue) {
				if (newValue == "1") {//司机
					$("#td2").removeAttr("style");
					$("#td2s").removeAttr("style");
					$("#d4").removeAttr("style");
					$("#newtds1").attr("style", "display:none");
					$("#newtd1").attr("style", "display:none");
					$("#othertds").attr("style", "display:none");
					$("#othertds2").attr("style", "display:none");
					$("#othertdstd1").attr("style", "display:none");

				} else if (newValue == "0") {//管理员
					$("#td2").attr("style", "display:none");
					$("#td2s").attr("style", "display:none");
					$("#newtds1").attr("style", "display:none");
					$("#newtd1").attr("style", "display:none");
					$("#othertds").attr("style", "display:none");
					$("#othertds2").attr("style", "display:none");
					$("#othertdstd1").attr("style", "display:none");
				} else if (newValue == "2") {//供应商
					$("#td2").removeAttr("style");
					$("#td2s").removeAttr("style");
					$("#d4").removeAttr("style");
					$("#newtds1").removeAttr("style");
					$("#newtd1").removeAttr("style");
					$("#newd21").removeAttr("style");
					$("#othertds").attr("style", "display:none");
					$("#othertds2").attr("style", "display:none");
					$("#othertdstd1").attr("style", "display:none");
				} else if (newValue == "3" || newValue == "4") {//省内外项目部
					$("#othertds").removeAttr("style");
					$("#othertds2").removeAttr("style");
					$("#othertdstd1").removeAttr("style");
					$("#td2").attr("style", "display:none");
					$("#td2s").attr("style", "display:none");
					$("#newtds1").attr("style", "display:none");
					$("#newtd1").attr("style", "display:none");
				}
			}
		});

		//省下拉框
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
								'driver.do?getNewFinalpositionCounty&&citye_parent_id='
										+ twoid);
					}
				});
		
		//市下拉框
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
						
		//县下拉框
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
		//发货客户的select2
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
		//司机的select2清空值
		$("#number_search4").select2("val", "");
		//标记清空值
		$("#flag2").combobox("setValue", "");
		//根据id取查询信息
		$.ajax({
			type : "POST",
			async : false,
			url : 'user.do?method=getOneUser',
			data : {
				id : userid
			},
			success : function(data2) {
				if (data2.flag == "1") {
					if (data2.plate_number == null) {
						data2.plate_number = "";
					}
					$("#number_search4").select2('data', {
						id : data2.driver_id,
						text : data2.plate_number
					});
				} else {

				}
				if (data2.did == null) {
					data2.did = "";
				}
				if (data2.department_name == null) {
					data2.department_name = "";
				}
				if (data2.flag == "2") {
					//问题-----------------------------------
					$("#custom_search").select2('data', {
						id : data2.suppliers_id,
						text : data2.suppliers_name
					});
					$("#number_search4").select2('data', {
						id : data2.driver_id,
						text : data2.plate_number
					});

				}
				if (data2.flag == "3" || data2.flag == "4") {

					$("#other_coustomer").select2('data', {
						id : data2.customer_id,
						text : data2.customer_name
					});
					$("#customerid").val(data2.customer_id);
				}

				flag = data2.flag;

				$("#province").combobox("setValue", data2.user_province);
				$("#city").combobox(
						'reload',
						'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
								+ data2.user_province);
				$("#city").combobox("setValue", data2.user_city);
				$("#county").combobox(
						'reload',
						'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
								+ data2.user_city);
				$("#county").combobox("setValue", data2.user_county);
				$('#form2').form('load', data2);

			}
		});

	});
	//提交信息
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		var myform = document.getElementById("form2");
		var realName = document.getElementById("user_realName2").value;
		var did = $("#cc2").combobox('getValue');
		var gpnumberd = $.trim($("#number_search4").select2("val"));
		var flag = $("#flag2").combobox('getValue');
		var custom_search = $.trim($("#custom_search").select2("val"));
		var poin = $("#province").combobox('getText');
		var city = $("#city").combobox('getText');
		var county = $("#county").combobox('getText');
		$("#user_address").val(poin + city + county);
		var customerId = $.trim($("#customerid").val());
		if (realName == "") {
			parent.$.messager.alert('警告!', '请输入真实姓名!', 'info');
		} else if (did == "") {
			parent.$.messager.alert('警告!', '请选择部门!', 'info');
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
		} else if (flag == 4 && customerId == "") {
			parent.$.messager.alert('警告!', '请选择发货客户!', 'info');
		} else {
			$.ajax({
				type : "POST",
				url : 'user.do?method=modifyUser',
				data : $('#form2').serialize(),
				success : function() {
					$pjq.messager.alert('更新用户', '用户信息更新成功', 'info');
					$dialog.dialog('close');
					$grid.datagrid('reload');
				}
			});

		}
	};
</script>
</head>

<body>
	<div id="dlg2">
		<form action="" method="post" id="form2">
			<fieldset>
				<input type='hidden' id="hiddenid2" name='id'> <input
					type='hidden' id="user_address" name="user_address">
				<table class="tableclass">
					<tr>
						<th colspan="4">修改用户</th>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>用户名称:</td>
						<td class="td2"><input id="user_name2" type="text"
							name="username" disabled="disabled" class="search-text">
						</td>
						<td><font color="red" style="margin-right:10px">*</font>真实姓名：</td>
						<td class="td2"><input class="search-text" name="realName"
							id="user_realName2">
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>所在部门：</td>
						<td class="td2" colspan="3"><input id="cc2"
							name="department_name"> <input type="hidden"
							id="department_id" name="did">
						</td>
					</tr>

					<tr>
						<td><font color="red" style="margin-right:10px">*</font>所属区域:</td>
						<td class="td2" colspan="3" style="height:40px"><input
							id="province" name="user_province">省 <input id="city"
							name="user_city">市 <input id="county" name="user_county">区
						</td>
					</tr>


					<tr>
						<td><font color="red" style="margin-right:10px">*</font>类型:</td>
						<td class="td2"><select id="flag2" class="easyui-combobox"
							name="flag" data-options="panelHeight : 'auto',editable:false"
							style="width:150px;">
								<option value="1">司机</option>
								<option value="2">供应商</option>
								<option value="3">省内项目部</option>
								<option value="4">省外项目部</option>

						</select></td>
						<td id="td2s" style="display: none"><font color="red"
							style="margin-right:10px">*</font>绑定司机:</td>
						<td class="td2" id="td2" style="display: none">
							<div id="d4" style="display: none">
								<input type="hidden" style="width:150px;" id="number_search4"
									name="car" />
							</div>
						<td id="othertds" style="display: none"><font color="red"
							style="margin-right:10px">*</font>绑定发货客户:</td>
						<td class="td2" id="othertdstd1" style="display: none">
							<div id="othertds2" style="display: none">
								<input type="hidden" style="width:150px;" id="other_coustomer" />
								<input type="hidden" style="width:150px;" id="customerid"
									name="customerid" />
							</div>
						</td>
					</tr>
					<tr>
						<td id="newtds1" style="display: none"><font color="red"
							style="margin-right:10px">*</font>绑定供应商:</td>
						<td class="td2" id="newtd1" style="display: none">
							<div id="newd21" style="display: none">
								<input type="hidden" style="width:150px;" id="custom_search"
									name="suppliers_id" class="custom_search" />
							</div>
						</td>
					</tr>

				</table>
			</fieldset>
			<!-- <div id="buttons2">
            <input type="button" onclick="update()" value="更新" class="btncss"
                id="update" /> <input type="button" onclick="closeps('update');"
                value="关闭" class="btncss" />
        </div> -->
		</form>
	</div>
</body>
</html>
