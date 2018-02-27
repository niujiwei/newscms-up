<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
</head>
<script type="text/javascript">
	var dialog;
    var grid;
	$(function() {
		var da = "";
		$('#dlg3').dialog({
			title : '修改密码',
			width : 360,
			height : 280,
			closed : true,
			cache : false,
			modal : true,
			buttons : '#buttons3'
		});
		/* $('#cc3')
				.combotree(
						{
							url : "depn.do?method=getTree&&id=",
							width : 150,
							onBeforeLoad : function(node, param) {

								if (node == null) {
									$('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
								} else {

									$('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id="
											+ node.id;//加载下层节点
								}

							}
						}); */
		$('#cc3')
				.combotree(
						{
							url : "depn.do?method=getDepartmentTree&&id=",
							width : 150,
							onBeforeLoad : function(node, param) {

								if (node == null) {
									$('#cc3').combotree('tree').tree('options').url = "depn.do?method=getDepartmentTree&&id=";//加载顶层节点
								} else {

									$('#cc3').combotree('tree').tree('options').url = "depn.do?method=getDepartmentTree&&id="
											+ node.id;//加载下层节点
								}

							}
						});
		//键盘回车事件
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchUser();
			}
		};
		//查询部门列表
		$("#cc1").combobox({
			panelHeight : 'auto',
			panelWidth :'auto',
			onChange : function(n, o) {
				$('#dg').datagrid('load', {
					user_did : n
				});
			}

		});
        $("#flag").combobox("select", '');
		grid = $("#dg").datagrid({
			url : 'user.do?method=getUser',
			fit : true,
			rownumbers : true,
			singleSelect : false,
			selectOnCheck : true,
			checkOnSelect : true,
			toolbar : '#tb',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'username',
				title : '用户名',
				width : 200
			}, {
				field : 'realName',
				title : '真实姓名',
				width : 200,
				align : 'left'
			}, {
				field : 'did',
				title : '所在部门',
				width : 200,
				align : 'left',
				formatter : function(val, row, index) {
					$.ajax({
						type : "POST",
						async : false,
						url : 'user.do?method=getDepartment',
						data : {
							did : val
						},
						success : function(data) {
							da = data;
						}
					});
					val = da;
					return val;
				}
			}, {	
			    field : 'user_address',
				title : '终到位置',
				width : 200,
				align : 'left',
			},{
				field : 'flag',
				title : '类型',
				width : 200,
				align : 'left',
				formatter : function(val, row, index) {
					if (val == "0") {
						return "管理员";
					} else if (val == "1") {
						return "司机";
					} else if (val == "2"){
					    return "供应商";
					}else if (val == "3"){
					    return "省内项目部";
					}else if (val == "4"){
					    return "省外项目部";
					}
				}
			},
			{
				field : 'lastTime',
				title : '最后登入时间',
				width : 200,
				align : 'left',
				formatter : function(val, row, index) {
					if (val != null) {
						return val.substring(0, 19);
					}
				}
			} ] ],
			pagination : true,
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ]
		});
	});
	//修改
	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var userid = row[0].id;
			var flag = row[0].flag;
			var flag2;
			
		dialog = parent.jy.modalDialog({
			title : '添加',
			url : 'user.do?method=editUserInfo&id='+ userid,
			width :680,
			height : 350,
			buttons : [{
				text : '<input type="button"  class="btncss" value="更新"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}] 
		
		});
		} else {
			$.messager.alert("用户管理","请选择一行信息","info");	
		}
		
	}
	function closeps(data) {
		if (data == 'save') {
			$("#dlg1").dialog('close');
		} else if (data == 'update') {
			$("#dlg2").dialog('close');
		} else if (data == 'password') {
			$("#dlg3").dialog('close');
		}
	}
	//删除用户
	function deleteEver() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length > 0) {
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							var flag = row[0].flag;
								for ( var i = 0; i < row.length; i++) {
									var user = row[i].id;
									$.ajax({
										type : "POST",
										async : false,
										url : 'user.do?method=deleteUser',
										data : {
											id : user
										},
										success : function(data) {
										}
									});
								}
								$("#dg").datagrid('reload');
							
						} else {
						}
					});
		} else {
			$.messager.alert("用户管理","请选择一行信息","info");
		}

	}
	//添加
	function add() {
			dialog = parent.jy.modalDialog({
			title : '添加',
			url : 'user.do?method=addUserInfo',
			width :680,
			height : 350,
			buttons : [{
				text : '<input type="button"  class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}] 
		});
	}
	
	//重置
	function reset() {
		document.getElementById("user_name").value = "";
		document.getElementById("user_realName").value = "";
		$("#cc3").combotree('setValue', "");
	}
	
	//重置密码
	function resetpassword() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
		$.messager.confirm('确认', '您确认想要重置密码吗？',
					function(r) {
						if (r) {
						var userid = row[0].id;
			$.ajax({
				type : "POST",
				async : false,
				url : 'user.do?method=resetpassword',
				data : {
					id : userid
				},
				success : function(data) {
					$.messager.alert("用户管理","密码重置成功，为666666","info");
				}
			});
						}
					});
			
		} else {
			$.messager.alert("用户管理","请选择一行信息","info");

		}

	}
	function searchUser() {
		$('#dg').datagrid('load', {
			user_name : $.trim($("#user_name").val()),
			user_realName : $.trim($("#user_realName").val()),
			department_id : $.trim($("#cc3").combotree("getValue")),
			flag:$.trim($("#flag").combotree("getValue"))

		});
	}
	function putoutfile() {
	    var allRows2 = $("#dg").datagrid("getColumnFields");
	     var array = $('#dg').datagrid('getSelections');
		var colName=[];
		var fieldName=[];
		var checkarray=[];
		for(i=1;i<allRows2.length;i++)
		{
			var col = $('#dg').datagrid( "getColumnOption" , allRows2[i] );
			colName.push(col.title);//把title列名到数组里去 
			fieldName.push(col.field); //把field列属性名到数组里去 
		} 
		 for(var i=0;i<array.length;i++){
            checkarray.push(array[i].id);
        }
		$("#fieldName").val(fieldName.join(","));
  		$("#headtitle").val(colName.join(","));
        $("#ids").val(checkarray.join(","));
  		var outputform=$("#outputform");
  		outputform.submit(); 
	/* var allRows;
	$.ajax({
				type : "POST",
				async : false,
				url : 'user.do?method=getAllUser',
				success : function(data) {
					allRows=data;
				}
			});
		//var allRows = $("#dg").datagrid("getData");
		//alert(json2str(xxoo));
		//var allData = $("#dg").datagrid("getData");
		//alert(allData.rows);
		//alert(allRows);
		var allRows2 = $("#dg").datagrid("getColumnFields");
		var colName = [];
		for (i = 1; i < allRows2.length; i++) {
			var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
			colName.push(col.title);//把title列名到数组里去 
		}
		$("#datas").val(JSON.stringify(allRows));
		$("#headtitle").val(colName.join(","));
		var outputform = $("#outputform");
		outputform.submit(); */
	}
	function json2str(o) { 
	var arr = []; 
	var fmt = function(s) { 
	if (typeof s == 'object' && s != null) return json2str(s); 
	return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s; 
	} ;
	for (var i in o) arr.push("'" + i + "':" + fmt(o[i])); 
	return '{' + arr.join(',') + '}'; 
} 
	function modifypassword() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var userid = row[0].id;
			$("#hiddenid3").val(userid);
			$("#dlg3").dialog('open');
		} else {
			$.messager.alert("用户管理","请选择一行信息","info");

		}
	}
	function updatepassword() {
		var myform = document.getElementById("form3");
		var password = document.getElementById("user_password3").value;

		if (password == "") {
			parent.$.messager.alert('警告!', '请输入修改密码!', 'info');
		} else {
			myform.action = "user.do?method=modifypassword";
			myform.submit();
		}
	}
	
</script>
<body class="easyui-layout">
	<form action="user.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			data-options="collapsible:false">
			<label class="divlabel">用户名：</label><input id="user_name"
				name="user_name" type="text" class="search-text"> <label>真实姓名：</label><input
				id="user_realName" type="text" name="user_realName"
				class="search-text"> <label>所在部门：</label><input id="cc3"
				name="department_id"> <label>所属类型：</label><select id="flag"
				class="easyui-combobox" name="flag"
				data-options="panelHeight : 'auto',editable:false"
				style="width:150px;">
				<option value="" selected="selected">全部</option>
				<option value="1">司机</option>
				<option value="2">供应商</option>
				<option value="3">省内项目部</option>
				<option value="4">省外项目部</option>
				<option value="0">管理员</option>
			</select> &nbsp;<a class="easyui-linkbutton" onclick="searchUser()"
				data-options="iconCls:'icon-search'" id="searchId">查询</a>
		</div>
		<input type="hidden" name="ids" id="ids">
		<input type="hidden" name="method" value="outputUser"> <input
			type="hidden" id="fieldName" name="fieldName"> <input
			type="hidden" id="headtitle" name="headtitle">
		<div region="center" title="用户信息展示" region="center"
			style="background: #eee; overflow-y:hidden">
			<table id="dg"></table>
		</div>
	</form>
	<div id="dlg3">
		<form action="" method="post" id="form3">
			<fieldset>
				<input type='hidden' id="hiddenid3" name='id'>
				<table class="tableclass">
					<tr>
						<th colspan="4">修改密码</th>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>修改密码:</td>
						<td class="td2"><input id="user_password3" type="password"
							name="password" class="search-text"></td>
					</tr>
				</table>
			</fieldset>
			<div id="buttons3">
				<input type="button" onclick="updatepassword()" value="更新"
					class="btncss" id="update" />
			</div>
		</form>
	</div>
	<div id="tb"></div>
	<input id="functionname" type="hidden" value="${function}">
</body>
</html>
