<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

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
<%
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
<script type="text/javascript">
	var dialog;
	var grid;
	var name;
	$(function() {
	    var id ;
		var da = "";
		var fs = $("#functionname").val().split(",");
		for (var i = 0; i < fs.length; i++) {
			if (fs[i] == "添加") {
				$("#tbadd").removeAttr("style");
			} else if (fs[i] == "修改") {
				$("#tbmodify").removeAttr("style");
			} else if (fs[i] == "删除") {
				$("#tbdel").removeAttr("style");
			}else if(fs[i]=="导出"){
			    $("#putoutfile").removeAttr("style");
			}

		}
		
       /* $("#suppliers_customer").select2({  //自动完成
            placeholder: "请选择发货客户名称",
            minimumInputLength: 1,
            multiple:false,
            allowClear : true,
            query: function(query) {
                $.ajax({
                    type: "POST",
                    url: "shipOrder.do?method=getCustomName",
                    data: {name:query.term},   //传递你输入框中的值
                    success: function(msg){
                        var msg=$.parseJSON(msg);
                        var data = {results: []};
                        $.each(msg,function(x,y){
                            data.results.push({id: y.customerid, text: y.name});
                        });
                        query.callback(data);
                    }
                });
            },
        });*/
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
		
        id = '<%=user.getId()%>';
		grid = $("#dg").datagrid({
			url : 'suppliers.do?method=getSuppliersList',
			border : false,
			rownumbers : true,
			fit : true,
			singleSelect : false,
			selectOnCheck : true,
			checkOnSelect : true,
			multiSort : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true
			},
			//{field : 'supplier_customer_name',title : '发货客户名称',width : 100,align:'center'},
			{
				field : 'suppliersName',
				title : '供应商姓名',
				width : 100,
				align : 'center'
			}, {
				field : 'supplie_company',
				title : '供应商公司名称',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersAddress',
				title : '终到位置',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersPeople',
				title : '公司法人',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersPerson',
				title : '联系人',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersPhone',
				title : '联系电话',
				width : 100,
				align : 'center'
			}, {
				field : 'phone_brand',
				title : '手机品牌',
				width : 100,
				align : 'center'
			}, {
				field : 'phone_model',
				title : '手机型号',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliers_cartype',
				title : '车辆类型',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliers_carnumber',
				title : '车牌号',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersService',
				title : '服务范围',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersDeadline',
				title : '合同期限',
				width : 100,
				align : 'center'
			}, {
				field : 'suppliersStartDate',
				title : '签定时间',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			}, {
				field : 'suppliersEndDate',
				title : '结束时间',
				width : 140,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			},
			/* {field : 'suppliersProve',title : '资质',width : 140,align:'center'}, */
			{
				field : 'suppliersOperator',
				title : '操作人',
				width : 140,
				align : 'center'
			}, {
				field : 'suppliersOperatorDate',
				title : '操作日期',
				width : 140,
				align : 'center',
				formatter : function(value, row, index) {
					if (value != undefined) {
						value = value.substr(0, 19);
					}
					return value;
				}
			} ] ],
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
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
			name : $.trim($("#serchname").val()),
			phone : $.trim($("#serchtel").val()),
			address : $.trim($("#serchaddress").val()),
		//suppliers_customer:$.trim($("#suppliers_customer").val())
		});

	}

	function add() {
		dialog = parent.jy.modalDialog({
			title : '供应商新增',
			url : 'suppliers.do?method=addSuppliers',
			width : 800,
			height : 600,
			buttons : [ {
				text : '<input type="button" class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	}

	function modify() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 1) {
			var remarkid = row[0].suppliersId;
			dialog = parent.jy.modalDialog({
				title : '供应商修改',
				url : 'suppliers.do?method=toUpdateSuppliers&cid=' + remarkid,
				width : 800,
				height : 600,
				buttons : [ {
					text : '<input type="button" class="btncss" value="保存"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow
								.submitFormEdit(dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			$.messager.alert('修改供应商', '请选择一行!', 'info');
		}
	}

	function deleteRemark() {
		var row = $("#dg").datagrid('getSelections');
		var array = [];
		for ( var i = 0; i < row.length; i++) {
			if (row[i].suppliersId != 0) {
				array.push(row[i].suppliersId);
			}
		}
		if (row.length > 0) {
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							$.ajax({
								type : "POST",
								async : false,
								url : 'suppliers.do?method=deletesuppliers',
								data : {
									pid : array.join(",")
								},
								success : function(data) {
									if (data.flag) {
										$.messager.alert("删除供应商", "供应商删除成功!",
												'info');
										$("#dg").datagrid('reload');
									} else {
										$.messager.alert("删除供应商", "供应商删除失败!",
												"info");
									}
								}
							});
						}
					});
		} else {
			$.messager.alert("删除供应商", "请选择数据!", "info");
		}
	}
	function reset() {
		$("#serchname").val("");
		$("#serchtel").val("");
		$("#serchaddress").val("");
		//$("#suppliers_customer").select2("val", "");
	}

	/*function deleteRemark() {
		var row = $("#dg").datagrid('getSelections');
		var b;
		if (row.length > 0) {
			$.messager.confirm('确认', '您确认想要删除这' + row.length + '条记录吗？',
					function(r) {
						if (r) {
							for (var i = 0; i < row.length; i++) {
								var remarkid = row[i].customer_id;
								$.ajax({
									type : "POST",
									async : false,
									url : 'remarkMap.do?method=deleteRemark',
									data : {
										id : remarkid
									},
									success : function(data) {

									}
								});
								$("#dg").datagrid('reload');
							}
						}
					});
		} else {
			alert("请选择数据");
		}

	}
	
	 */

	//导出供应商信息
	function putoutfile() {
		var allRows2 = $("#dg").datagrid("getColumnFields");
		var row = $("#dg").datagrid("getSelections");
		var colName = [];
		var fieldName = [];
		var dataIds = [];
		for (i = 1; i < allRows2.length; i++) {
			var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
			colName.push(col.title);//把title列名到数组里去 
			fieldName.push(col.field); //把field列属性名到数组里去 	 
		}
		for (i = 0; i < row.length; i++) {
			dataIds.push(row[i].suppliersId);
		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		$("#dataIds").val(dataIds.join(","));
		var outPutForm = $("#outputform");
		outPutForm.submit();
	}
</script>
<body class="easyui-layout">
	<form action="suppliers.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			height="60px" collapsible="false">
			<lable class="divlabel">供应商姓名：</lable>
			<input type="text" id="serchname" name="name" style="width:150px" />
			<!-- <lable class="divlabel">发货客户名称：</lable>
			<input type="text" id="suppliers_customer" style="width:250px" name="suppliers_customer" /> -->
			<lable class="divlabel">联系方式：</lable>
			<input type="text" id="serchtel" name="phone" style="width:150px" />
			<lable class="divlabel">终到位置：</lable>
			<input type="text" id="serchaddress" name="address"
				style="width:150px;" /> &nbsp <a class="easyui-linkbutton"
				onclick="searchRemark()" data-options="iconCls:'icon-search'"
				id="searchId">查询</a>
		</div>
		<div region="center" title="供应商管理" region="center"
			style="background: #eee; overflow-y:hidden">
			<table id="dg"></table>
		</div>
		<div id="tb">
			<a href="#" class="easyui-linkbutton" id="tbadd" onclick="add()"
				data-options="iconCls:'icon-add',plain:true" style="display: none">新增</a>
			<a href="#" class="easyui-linkbutton" id="tbmodify"
				onclick="modify()" data-options="iconCls:'icon-edit',plain:true"
				style="display: none">修改</a> <a href="#" class="easyui-linkbutton"
				id="tbdel" onclick="deleteRemark()"
				data-options="iconCls:'icon-remove',plain:true"
				style="display: none">删除</a> <a href="#" class="easyui-linkbutton"
				id="tbreset" onclick="reset()"
				data-options="iconCls:'icon-reset',plain:true">重置</a> <a href="#"
				class="easyui-linkbutton" id="putoutfile" onclick="putoutfile()"
				data-options="iconCls:'icon-output',plain:true">导出</a> <input
				type="hidden" name="method" value="outPutSendSuppliers"> <input
				type="hidden" id="fieldName" name="fieldName"> <input
				type="hidden" id="headtitle" name="headtitle"> <input
				type="hidden" id="dataIds" name="dataIds">
		</div>
	</form>
	<input id="functionname" type="hidden" value="${function}">
</body>
</html>
