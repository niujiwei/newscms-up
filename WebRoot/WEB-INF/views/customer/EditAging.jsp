<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

<!DOCTYPE HTML>
<html>
<head>


<title>修改时效</title>
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
	var threeid =0;
	$(function() {
		$("#Regform").validationEngine('attach', { 
			 promptPosition:'topRight:-15,0'
			 });
			 var $target = $('input,textarea,select');
	 	$target.bind('keydown', function (e) {
    	var key = e.which;
         	if (key == 13) {
             e.preventDefault();
             var nxtIdx = $target.index(this) + 1;
                 $target.eq(nxtIdx).focus();
        	}
 		});
		 $('#province').combobox({
            url : 'driver.do?method=getNewFinalPositionCounty',
            valueField : 'id',
            textField : 'text',
            width:120,
            editable:false,
            onSelect:function(record){
                oneid = record.citye_parent_id;
                twoid=0;
                $('#city').combobox("clear");
                $('#county').combobox("clear");
                $('#city').combobox('reload','driver.do?method=getNewFinalpositionCity&&citye_parent_id='+oneid);
                $('#county').combobox('reload','driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid);
            }
        });
        $('#city').combobox({
            url : 'driver.do?method=getNewFinalpositionCity&&citye_parent_id='+oneid,
            valueField : 'id',
            width:120,
            textField : 'text',
            editable:false,
            onSelect:function(record){
                twoid = record.citye_parent_id;
                $('#county').combobox("clear");
                $('#county').combobox('reload','driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid);
            }
        });
        $('#county').combobox({
            url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid,
            valueField : 'id',
            width:120,
            textField : 'text',
            editable:false

        });
		$("#custoid").select2({
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
		});
		
		$.ajax({
			url : 'remarkMap.do?method=getsetAging',
			data : {
				aid : cid
			},
			success : function(data) {
				
				
						 $("#province").combobox("setValue",
                       data.aging_province);
                $("#city").combobox(
                        'reload',
                        'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
                        + data.aging_province);
                $("#city").combobox("setValue", data.aging_city);
                $("#county").combobox(
                        'reload',
                        'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
                        + data.aging_city);
                $("#county").combobox("setValue", data.aging_county);
						
						/* $("#province").combobox("setValue",data.aging_province);
						$("#city").combobox('reload','driver.do?method=getFinalpositionCity&&oneid='+da.oneid);
						$("#city").combobox("setValue",data.aging_city);
						$("#county").combobox('reload','driver.do?method=getFinalpositionCounty&&oneid='+da.oneid+'&&twoid='+da.twoid);
						$("#county").combobox("setValue",data.aging_county); */
						//oneid=da.oneid;
					$('#Regform').form('load', data);
				
				$("#province2").val(data.aging_province);
				$("#city2").val(data.aging_city);
				$("#county2").val(data.aging_county);
				$("#custoid").select2("data", {id:data.aging_cutomer_id, text:data.cutomer_name});
			}
		});
	});
	var submitForms = function($dialog, $grid, $pjq, $mainMenu) {
		//var customer_num = $.trim($("#customer_num").val());
		var poin = $("#province").combobox('getText');
		var city = $("#city").combobox('getText');
		var county = $("#county").combobox('getText');
		$("#driver_address").val(poin+city+county);
		if(!isNaN($("#province").combobox('getValue'))){
			$("#province2").val($("#province").combobox('getValue'));
			$("#city2").val($("#city").combobox('getValue'));
			$("#county2").val($("#county").combobox('getValue'));	
		}
		if($("#custoid").select2("val")!=""&&$("#custoid").select2("val")!=null){
			if($("#province").combobox('getText')!=""&&$("#province").combobox('getText')!=null){
				if ($("#Regform").validationEngine('validate')) {
					//可提交
					$pjq.messager.confirm('修改时效', '确定要修改吗?', function(r) {
						if (r) {
							$.ajax({
								type : "POST",
								url : 'remarkMap.do?method=updateAging',
								data : $('#Regform').serialize(),
								success : function(msg) {
									if (msg.flag) {
										$pjq.messager.alert('修改时效',
												'时效修改成功', 'info');
										$dialog.dialog('close');
										$grid.datagrid('reload');
									}else{
										$pjq.messager.alert('修改时效',
												'时效修改失败', 'info');
									};
								}
							});
						}
					});
				} else {
					$pjq.messager.alert('修改时效', '必填信息不可为空', 'info');
				}
			}else{
				$pjq.messager.alert('修改时效', '终到位置不可为空', 'info');
			}
		}else{
			$pjq.messager.alert('修改时效', '客户不能为空', 'info');
		}
		/* $.ajax({
			type : "POST",
			url : 'remarkMap.do?method=getcustomer_num',
			data : {
				customer_num : customer_num
			},
			success : function(data) { 
				  if (data.flag== false) {
					$pjq.messager.alert('新增客户', '此客户编码已经存在', 'info');
				} else { */
					
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
					<th colspan="4">时效管理</th>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>客户:</td>
					<td class="td2"><input id="custoid" class="search-text"
						name="aging_cutomer_id" type="text" class="search-text"> <input
						id="aging_id" name="aging_id" type="hidden"></td>
					<td><font color="red" style="margin-right:10px">*</font>标准时效:</td>
					<td class="td2"><select class="easyui-combobox"
						style="width:152px" data-options="editable:false ,panelHeight:65"
						name="aging_day">
							<option value="0">当日</option>
							<option value="1">次日</option>
							<option value="2">隔日</option>
					</select> <input class="Wdate validate[required] search-text"
						onfocus="WdatePicker({isShowWeek:true,dateFmt:'HH:mm:ss'})" id=""
						name="aging_time" />
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>触发时效:</td>
					<td class="td2" colspan="3" style="height:40px"><input
						id="star_time" name="star_time" class="Wdate validate[required] search-text"
						 onfocus="WdatePicker({isShowWeek:true,dateFmt:'HH:mm:ss'})">-
						<input id="end_time" name="end_time" class="Wdate validate[required]"
						onfocus="WdatePicker({isShowWeek:true,dateFmt:'HH:mm:ss'})">
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>终到位置:</td>
					<td class="td2" colspan="3" style="height:40px"><input
						id="province" class="validate[required]">省 <input
						id="city">市 <input id="county">区 <input
						id="province2" type="hidden" name="aging_province"> <input
						id="city2" type="hidden" name="aging_city"> <input
						id="county2" type="hidden" name="aging_county"></td>
					<input type="hidden" id="driver_address" name="aging_address">
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
