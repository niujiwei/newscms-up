<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运单导入页面</title>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<script type="text/javascript" src="./js/jquery.form.js"></script>
<style>
.uploader {
	position: relative;
	display: inline-block;
	overflow: hidden;
	cursor: default;
	padding: 0;
	margin: 10px 0px;
	-moz-box-shadow: 0px 0px 5px #ddd;
	-webkit-box-shadow: 0px 0px 5px #ddd;
	box-shadow: 0px 0px 5px #ddd;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}

.filename {
	float: left;
	display: inline-block;
	outline: 0 none;
	height: 32px;
	width: 250px;
	margin: 0;
	padding: 8px 10px;
	overflow: hidden;
	cursor: default;
	border: 1px solid;
	border-right: 0;
	font: 9pt/100% Arial, Helvetica, sans-serif;
	color: #777;
	text-shadow: 1px 1px 0px #fff;
	text-overflow: ellipsis;
	white-space: nowrap;
	-moz-border-radius: 5px 0px 0px 5px;
	-webkit-border-radius: 5px 0px 0px 5px;
	border-radius: 5px 0px 0px 5px;
	background: #f5f5f5;
	background: -moz-linear-gradient(top, #fafafa 0%, #eee 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #fafafa),
		color-stop(100%, #f5f5f5) );
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#fafafa',
		endColorstr='#f5f5f5', GradientType=0 );
	border-color: #ccc;
	-moz-box-shadow: 0px 0px 1px #fff inset;
	-webkit-box-shadow: 0px 0px 1px #fff inset;
	box-shadow: 0px 0px 1px #fff inset;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}

.button {
	float: left;
	height: 32px;
	display: inline-block;
	outline: 0 none;
	padding: 8px 12px;
	margin: 0;
	cursor: pointer;
	border: 1px solid;
	font: bold 9pt/100% Arial, Helvetica, sans-serif;
	-moz-border-radius: 0px 5px 5px 0px;
	-webkit-border-radius: 0px 5px 5px 0px;
	border-radius: 0px 5px 5px 0px;
	-moz-box-shadow: 0px 0px 1px #fff inset;
	-webkit-box-shadow: 0px 0px 1px #fff inset;
	box-shadow: 0px 0px 1px #fff inset;
}

.uploader input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	border: 0;
	padding: 0;
	margin: 0;
	height: 30px;
	cursor: pointer;
	filter: alpha(opacity =         0);
	-moz-opacity: 0;
	-khtml-opacity: 0;
	opacity: 0;
}

input[type=button]::-moz-focus-inner {
	padding: 0;
	border: 0 none;
	-moz-box-sizing: content-box;
}

input[type=button]::-webkit-focus-inner {
	padding: 0;
	border: 0 none;
	-webkit-box-sizing: content-box;
}

input[type=text]::-moz-focus-inner {
	padding: 0;
	border: 0 none;
	-moz-box-sizing: content-box;
}

input[type=text]::-webkit-focus-inner {
	padding: 0;
	border: 0 none;
	-webkit-box-sizing: content-box;
}

/* White Color Scheme ------------------------ */
.white .button {
	color: #555;
	text-shadow: 1px 1px 0px #fff;
	background: #ddd;
	background: -moz-linear-gradient(top, #eeeeee 0%, #dddddd 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #eeeeee),
		color-stop(100%, #dddddd) );
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#eeeeee',
		endColorstr='#dddddd', GradientType=0 );
	border-color: #ccc;
}

.white:hover .button {
	background: #eee;
	background: -moz-linear-gradient(top, #dddddd 0%, #eeeeee 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #dddddd),
		color-stop(100%, #eeeeee) );
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#dddddd',
		endColorstr='#eeeeee', GradientType=0 );
}
</style>



<script type="text/javascript">
    var win_w =  window.screen.width;//获取宽度
    var win_h =window.screen.height;//获取高度
    var dlg_w = 600;
    var dlg_h =400;
	$(function() {
		$("input[type=file]").change(function() {
			$(this).parents(".uploader").find(".filename").val($(this).val());
		});
		$("input[type=file]").each(
				function() {
					if ($(this).val() == "") {
						$(this).parents(".uploader").find(".filename").val(
								"没有选择文件... ...");
					}
				});
	   
	    $('#imp_false').dialog({
			title : '未导入订单的信息原因',
			top:10,
			width : 500,
			height : 300,
			closed : true,
			cache : false,
			modal : true,
			onClose:function(){
			}
			
		});
		
		$('#imp_fal_table').datagrid({
			border : false,
			rownumbers : true,
			fit : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'shiping_order_num',
				title : '订单号',
				width : 100,
				align : 'center'
			}, {
				field : 'shiping_order_goid',
				title : '出货订单号',
				width : 100,
				align : 'center'
			}, {
				field : 'message',
				title : '未导入原因',
				width : 210,
				align : 'center'
			}, ] ],
			pagination : true,
			pageSize : 5,
			pageList : [ 5, 10,],
			
		//toolbar : '#tb'
		});
	});

	var submitForm = function($dialog, $dg, $pjq) {

		$pjq.messager.confirm('导入订单', '确定要导入订单?', function(r) {
			if (r) {
				var files = $('#filename').val();
				var getdian = files.lastIndexOf(".");
				if (getdian > 0) {
					var hz = files.substring(getdian, files.length);
					if (hz == ".xlsx" || hz == ".xlsm" || hz == ".xltx"
							|| hz == ".xltm" || hz == ".xlsb" || hz == ".xlam"
							|| hz == ".xls") {
						$("#impform").ajaxSubmit({
							url : "shipOrder.do?method=startimplinplan",
							type : "post",
							enctype : 'multipart/form-data',
							// iframe: true,
							dataType : 'json',
							success : function(data) {
								var filename = data.filesname;
								upload_success(filename);
							},
							error : function(data) {
								parent.$.messager.alert('导入订单', '导入出错了', 'info');
							}
						});

					} else {
						$pjq.messager.alert('导入订单', '请选择正确的文件格式', 'info');
					}
				} else {
					$pjq.messager.alert('导入订单', '没有后缀名的文件', 'info');
				}
			}
		});

	
	
	function upload_success(filename) {
			if (filename != null || filename != "") {
				var win = $.messager.progress({
					title : '请稍等',
					msg : '正在导入...',
					text : '正在导入订单信息，分配订单信息中...',
					interval : 500
				});
				$.ajax({
					type : "POST",
					url : 'shipOrder.do?method=implinplan',
					data : {
						filename : filename,
					//usersname : usersname
					},
					success : function(msg) {

						var mssage = eval('(' + msg + ')');
						var zs = mssage.zs;//
						var cg = mssage.num;
						var list = mssage.list;
						if (list.length > 0) {
							$dialog.dialog('resize', {
								width : dlg_w,
								height : dlg_h

							});
							$dialog.dialog('move', {
								left : (win_w - dlg_w) / 2,
								top : (win_h - dlg_h) / 2,
							});
							window.$('#imp_false').dialog('open');
							openMessage(list, $dialog);
						}
						var ms = "<span>导入完成！<br>总数据为 ：" + zs + "<br>导入成功数："
								+ cg + "</span>";
						$.messager.progress('close');
						parent.$.messager.alert('导入订单成功', ms, 'info');

						//$dialog.dialog('close');
					},
					error : function(data) {
						$.messager.progress('close');
						parent.$.messager.alert('导入订单', '导入出错了', 'info');
					}
				});
			}
			;
		}
	};

	function openMessage(data, dialog) {
		$('#imp_fal_table').datagrid('loadData', data);

	}
</script>

</head>
<body style="overflow: hidden;">

	<div>
		<div style="text-align: center;  margin-top: 40px;">
			<div class="uploader white">
				<form action="shipOrder.do?method=startimplinplan" id="impform">
					<input type="text" id="filename" class="filename"
						readonly="readonly" /> <input type="button" name="file"
						class="button" value="选择文件" /> <input type="file" name="files"
						size="30" />
				</form>
			</div>
		</div>
		<br>
		<div
			style="text-align: center;font-family: 'Microsoft YaHei', 微软雅黑, Arial, Helvetica, sans-serif; font-size: 13px;  color: blue;">
			提示：请选择正确的文件格式（.xlsx或者.xls ）<br>
			<!-- <span style="color:red">导入失败原因：请检查excel金额列是否有非数字数据</span> 
			<br>-->
		</div>
	</div>
	<div id="imp_false">
	    <div class="easyui-layout" style="width:100%;height:100%;">  
		<div data-options="region:'center',">
		<table id="imp_fal_table">
		
		</table>
		</div>
	    </div>
	</div>
</body>
</html>
