<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

<!DOCTYPE HTML>
<html>
<head>


<title>设置时效</title>
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
	String flg = (String) request.getAttribute("flg");
%>
<script type="text/javascript">
	var cid='<%=flg%>';
	var oneid = 0;
	var twoid = 0;
	var threeid = 0;
	$(function() {
		$("#Regform").validationEngine('attach', {
			promptPosition : 'topRight:-15,0'
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
						$('#county').combotree("clear");

						$('#city').combobox(
								'reload',
								'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
										+ oneid);
						$('#county').combotree(
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
								$('#county').combotree("clear");
								$('#county').combotree(
										'reload',
										'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
												+ twoid);
							}
						});
		/* $('#county').combobox({
		    url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid,
		    valueField : 'id',
		    width:120,
		    textField : 'text',
		    editable:false

		}); */
		$('#county')
				.combotree(
						{
							multiple : true,
							url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
									+ twoid,

						});
		$("#custoid").combotree({
			multiple : true,
			url : 'shipOrder.do?method=getNoveltCustomName',
			panelWidth : '220',

		});
		/* 	$("#custoid").select2({
				placeholder : "请输入客户名称", //默认文本框显示信息
				minimumInputLength : 1, //最小输入字符长度
				allowClear : true,
				multiple : false, //设置多项选择，false则只能单选
				maximumSelectionSize : 5, //最大选择个数
				query : function(query) {
					$.ajax({
						type : "POST",
						url : "remarkMap.do?method=getCustomers",
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
									text : y.name
								});
							});
							query.callback(data);
						}
					});
				}
			}).on("select2-selecting", function(f) {
				$("#aging_cutomer_id").val(f.object.id);
			}); */
		//结束
	});
	
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		var poin = $("#province").combobox('getText');
		var city = $("#city").combobox('getText');
		var county = $("#county").combobox('getText');
		$("#driver_address").val(poin + city + county);
		if ($("#custoid").select2("val") != ""
				&& $("#custoid").select2("val") != null) {
			if ($("#province").combobox('getText') != ""
					&& $("#province").combobox('getText') != null) {
				if ($("#Regform").validationEngine('validate')) {
					//可提交
					$pjq.messager
							.confirm(
									'新增时效',
									'确定要新增吗?',
									function(r) {
										if (r) {
											var win = $.messager.progress({
												title : '请稍等',
												msg : '正在添加...',
												text : '正在匹配时效，添加时效信息...',
												interval : 500
											});
											$
													.ajax({
														type : "POST",
														url : 'remarkMap.do?method=newSaveAgingInfo',
														data : $('#Regform')
																.serialize(),
														success : function(msg) {
															var message = JSON
																	.parse(msg)[0];
															var inum = message.inum;
															var snum = message.snum;
															var ms = "添加完成！<br/>应添加为 ："
																	+ inum
																	+ "<br/>实际添加："
																	+ snum;

															if (message.message != null) {
																var noinsert = message.message
																		.split("#");
																ms += "<br/>已存在信息：";
																for ( var i = 0; i < noinsert.length; i++) {
																	ms += noinsert[i]
																			+ "<br/>";
																}
															}

															parent.$.messager
																	.alert(
																			'时效添加成功',
																			ms,
																			'info');
															$.messager
																	.progress('close');
															$dialog
																	.dialog('close');
															$grid
																	.datagrid('reload');

														}
													});

										} else {

											$pjq.messager
													.alert(
															'新增时效',
															'时效添加失败,客户名称和地址已经存在，点击修改即可',
															'info');
										}

									});
				} else {
					$pjq.messager.alert('新增客户', '必填信息不可为空', 'info');
				}
			} else {
				$pjq.messager.alert('修改时效', '终到位置不可为空', 'info');
			}
		} else {
			$pjq.messager.alert('修改时效', '客户不能为空', 'info');
		}
		/*}
		}
		}); */

	};
</script>
<body>
	<form action="" method="post" id="Regform">
		<input type="hidden" name="shiping_order_id">
		<fieldset>
			<table class="tableclass">
				<tr>
					<th colspan="4">新时效管理</th>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>客户:</td>
					<td class="td2"><input id="custoid" name="aging_cutomer_id"
						type="text"></td>
					<td><font color="red" style="margin-right:10px">*</font>标准时效:</td>
					<td class="td2"><select class="easyui-combobox"
						style="width:152px" data-options="editable:false ,panelHeight:65"
						name="aging_day">
							<option selected="selected" value="0">当日</option>
							<option value="1">次日</option>
							<option value="2">隔日</option>
					</select> <input class="Wdate validate[required] search-text"
						onfocus="WdatePicker({isShowWeek:true,dateFmt:'HH:mm:ss'})" id=""
						name="aging_time" />
				</tr>
				<tr align="center">
					<td><font color="red" style="margin-right:10px">*</font>触发时效:</td>
					<td class="td2" colspan="3" style="height:40px"><input
						id="star_time" name="star_time"
						class="Wdate validate[required] search-text"
						onfocus="WdatePicker({isShowWeek:true,dateFmt:'HH:mm:ss'})">-
						<input id="end_time" name="end_time"
						class="Wdate validate[required]"
						onfocus="WdatePicker({isShowWeek:true,dateFmt:'HH:mm:ss'})">
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>终到位置:</td>
					<td class="td2" colspan="3" style="height:40px"><input
						id="province" name="aging_province" class="validate[required]">省
						<input id="city" name="aging_city">市 <input id="county"
						name="aging_countyname">区</td>

				</tr>
			</table>
		</fieldset>
		<input type="hidden" id="driver_address" name="aging_address" />
	</form>
</body>
</html>
