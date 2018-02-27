<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html>
<head>


<title>司机信息修改</title>
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
<%
	String flg = (String) request.getAttribute("flg");
%>
</head>
<script type="text/javascript">
    var oneid = 0;
    var twoid = 0;
    var threeid =0;
    var pid='<%=flg%>';
	$(function() {

		$("#Regform").validationEngine('attach', {
			promptPosition : 'topRight:-15,0'
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
		/*  $('#county')
		         .combobox(
		                 {
		                     url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
		                     + twoid,
		                     valueField : 'id',
		                     width : 120,
		                     textField : 'text',
		                     editable : false

		                 }); */
		$('#county')
				.combotree(
						{
							multiple : true,
							url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
									+ twoid,

						});
		$('#carType').combobox({
			url : 'suppliers.do?method=carType',
			valueField : 'id',
			textField : 'text'
		});

		$("#carid").select2({
			placeholder : "请输入供应商", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			//  maximumSelectionSize: 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "driver.do?method=getSuppliers",
					data : {
						num : query.term
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
							;
						});
						query.callback(data);
					}
				});
			}
		});
	});
	$.ajax({
		url : 'driver.do?method=getUpdateDriver',
		data : {
			driver_id : pid
		},
		success : function(data) {
			if (data.driver_suppliersname == null) {
				data.driver_suppliersname = '';
			}
			$("#province").combobox("setValue", data.driver_province);
			$("#city").combobox(
					'reload',
					'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
							+ data.driver_province);
			$("#city").combobox("setValue", data.driver_city);
			$("#carType").combobox("setValue", data.driver_cartype);
			$("#county").combotree(
					'reload',
					'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
							+ data.driver_city);
			if (data.driver_countys[0] == null) {
				data.driver_countys[0] = "";
			}
			$("#county").combotree("setValues", data.driver_countys);

			$('#Regform').form('load', data);
			/* $.ajax({
			 url:"driver.do?method=getNum",
			 data:{province:data.driver_province,city:data.driver_city,count:data.driver_county},
			 success:function(da){
			 $("#province").combobox("setValue",data.driver_province);
			 $("#city").combobox('reload','driver.do?method=getFinalpositionCity&&oneid='+da.oneid);
			 $("#city").combobox("setValue",data.driver_city);
			 $("#county").combobox('reload','driver.do?method=getFinalpositionCounty&&oneid='+da.oneid+'&&twoid='+da.twoid);
			 $("#county").combobox("setValue",data.driver_county);
			 oneid=da.oneid;
			 twoid=da.twoid;
			 }
			 }); */
			/*   $("#province2").val(data.driver_province);
			  $("#city2").val(data.driver_city);
			  $("#county2").val(data.driver_countys); */
			$("#carid").prev("div").find("span.select2-chosen").text(
					data.driver_suppliersname);
		}
	});
	var submitFormEdit = function($dialog, $grid, $pjq, $mainMenu) {
		var poin = $("#province").combobox('getText');
		var city = $("#city").combobox('getText');
		var county = $("#county").combobox('getText');
		var cartype = $("#carType").combobox('getText');
		$("#driver_cartype").val(cartype);
		$("#driver_address").val(poin + city + county);
		/*  if(!isNaN($("#province").combobox('getValue'))){
		     $("#province2").val($("#province").combobox('getValue'));
		     $("#city2").val($("#city").combobox('getValue'));
		     $("#county2").val($("#county").combobox('getValues'));
		 } */
		if ($("#Regform").validationEngine('validate')) {
			//可提交
			$pjq.messager.confirm('修改司机', '确定要修改吗?', function(r) {
				if (r) {
					$.ajax({
						type : "POST",
						url : 'driver.do?method=updateDriver',
						data : $('#Regform').serialize(),
						success : function(msg) {
							if (msg.flag) {
								$pjq.messager.alert('修改司机', '司机修改成功', 'info');
								$dialog.dialog('close');
								$grid.datagrid('reload');
							} else {
								$pjq.messager.alert('修改司机', '司机修改失败', 'info');
							}
							;
						}
					});
				}
			});
		} else {
			$pjq.messager.alert('修改司机', '必填信息不可为空', 'info');
		}

	};
</script>
<body>
	<form action="" method="post" id="Regform">
		<input type="hidden" id="driver_address" name="driver_address">
		<input type="hidden" name="driver_id"> <input type="hidden"
			id="driver_cartype" name="driver_cartype">
		<fieldset>
			<table class="tableclass">
				<tr>
					<th colspan="4">基本信息</th>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>终到位置:</td>
					<td class="td2" colspan="3" style="height:40px"><input
						id="province" name='driver_province'>省 <input id="city"
						name="driver_city">市 <input id="county"
						name="driver_countys">区 <!--  <input id="province2" type="hidden" name='driver_province'>
                    <input  id="city2"  type="hidden" name="driver_city">
                    <input  id="county2" type="hidden" name="driver_countys"> -->
					</td>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>姓名:</td>
					<td class="td2"><input type="text" class="validate[required]"
						id="" name="driver_name"></td>
					<td>供应商:</td>
					<td class="td2"><input type="hidden" id="carid"
						name="driver_suppliers" style="width:150px">
					</td>
				<tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>性别:</td>
					<td class="td2">
						<select id="driver_sex" data-options="width:'120',panelHeight:50,editable:false," style=""  class="easyui-combobox" name="driver_sex">
							<option value="1">男</option>
							<option value="0">女</option>
						</select>
					</td>
					<td>年龄:</td>
					<td class="td2">
						<input type="text" class="validate[custom[number],max[200]]" id="driver_age" name="driver_age">
					</td>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>联系方式:</td>
					<td class="td2"><input type="text"
						class="validate[required]" id="" name="driver_phone">
					</td>
					<td><font color="red" style="margin-right:10px">*</font>身份证:</td>
					<td class="td2"><input type="text" class="validate[required]"
						id="" name="driver_cardnumber" /></td>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px"></font>手机品牌:</td>
					<td class="td2"><input type="text" class="" id=""
						name="driver_phonebrand"></td>
					<td><font color="red" style="margin-right:10px"></font>手机型号:</td>
					<td class="td2"><input type="text" class="" id=""
						name="driver_phonemodel"></td>
				</tr>
				<tr>
					<td>车辆类型:</td>
					<td class="td2"><input id="carType" type="text"></td>
					<td><font color="red" style="margin-right:10px"></font>车牌号:</td>
					<td class="td2"><input type="text"
						class="validate[minSize[7],maxSize[7],custom[carnumber]]" id=""
						name="driver_carnumber"></td>
				</tr>
			<%--	<tr>
					<td>开始配送无限极时间:</td>
					<td    class="td2">
						<input id="start_time" type="text"  name="start_time" class="Wdate" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" ></td>
				</tr>--%>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>备注:</td>
					<td colspan="3" class="td2"><textarea class="easyui-textarea "
							id="driver_remarks" cols="45" rows="3" name="driver_remarks"></textarea>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
