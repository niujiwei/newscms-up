<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
<title>异常处理</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="js/json2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<script type="text/javascript" src="./js/function.js"></script>
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="./js/select2/select2_expand.js"></script>
<script type="text/javascript" src="./webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css"
	href="./webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="./webuploader/style.css" />
<script type="text/javascript" src="./webuploader/$webupload.js"></script>
<style type="text/css">
.imgesty {
	max-width: 600px;
	max-height: 800px;
}
</style>
</head>
<script type="text/javascript">
	var submitHandling = function($dialog, $grid, $pjq) {
		$.messager.prompt('异常处理', '请输入处理信息', function(r) {
			if (r) {
				$("#abnormal_resultremark").val(r);
				$.ajax({
					url : 'abnormalreport.do?method=doExceptionHandling',
					type : 'post',
					data : $("#abnormal").serialize(),
					success : function(data) {
						if (data) {
							$pjq.messager
									.alert('异常处理', '异常处理成功', 'info');
							$dialog.dialog('close');
							$grid.datagrid('reload');
						} else {
							$pjq.messager.alert('异常处理', '异常处理失败', 'info');
						}

					},
				});
			}
		});

	};
</script>
<body>
	<form action="" method="post" id="abnormal">
		<input type="hidden" id="shiping_order_id" name="shiping_order_id"
			value="${abnormalReport.shippingOrder.shiping_order_id}"> <input
			type="hidden" id="abnormalId" name="id" value="${abnormalReport.id}">
		<input type="hidden" id="abnormal_supperid" name="abnormal_supperid"
			value="${abnormalReport.abnormal_supperid}"> <input
			type="hidden" id="abnormal_driverid" name="abnormal_driverid"
			value="${abnormalReport.abnormal_driverid}"> <input
			type="hidden" id="abnormal_resultremark" name="abnormal_resultremark">
			<input
			type="hidden" id="order_num" name="order_num" value="${abnormalReport.shippingOrder.shiping_order_num}">
			
	</form>
	<fieldset>
		<table class="tableclass">
			<tr>
				<th colspan="4">运单信息</th>

			</tr>
			<tr>
				<td>货运编号：</td>
				<td class="td2"><input type="text" id="shiping_order_num"
					class="validate[required]" name="shiping_order_num"
					readonly="readonly"
					value='<c:out value="${abnormalReport.shippingOrder.shiping_order_num}"/>'>
				</td>
				<td>出货订单号：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="shiping_order_goid" class="validate[required]"
					name="shiping_order_goid"
					value='<c:out value="${abnormalReport.shippingOrder.shiping_order_goid}"/>'>
				</td>
			</tr>
			<tr>
				<td>起运时间：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="send_time" class="validate[required]" name="send_time"
					value='<c:out value="${abnormalReport.shippingOrder.send_time}"/>'>
				</td>
				<td>受理机构：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="send_mechanism" name="send_mechanism"
					value='<c:out value="${abnormalReport.shippingOrder.send_mechanism}"/>'>
				</td>
			</tr>
			<tr>
				<td>收货联系人：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="receipt_name" name="receipt_name"
					value='<c:out value="${abnormalReport.shippingOrder.receipt_name}"/>'>
				</td>
				<td>收货客户电话：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="receipt_tel" name="receipt_tel"
					value='<c:out value="${abnormalReport.shippingOrder.receipt_tel}"/>'>
				</td>
			<tr>
			<tr>
				<td>货物名称：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="goods_name" name="goods_name"
					value='<c:out value="${abnormalReport.shippingOrder.goods_name}"/>'>
				</td>
				<td>总件数：</td>
				<td class="td2"><input readonly="readonly" id="goods_num"
					name="goods_num"
					value='<c:out value="${abnormalReport.shippingOrder.goods_num}"/>' />
				</td>
			</tr>
			<tr>
				<td>总重量：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="goods_weight" name="goods_weight"
					value='<c:out value="${abnormalReport.shippingOrder.goods_weight}"/>'>
				</td>
				<td>总体积：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="goods_vol" name="goods_vol"
					value='<c:out value="${abnormalReport.shippingOrder.goods_vol}"/>'>
				</td>
			</tr>
		</table>
		<table class="tableclass">
			<tr>
				<th colspan="4">异常信息</th>
			</tr>
			<tr>
				<td>异常类型：</td>
				<td class="td2"><input type="text" name="abnormal_type"
					id="abnormal_type" readonly="readonly"
					value='<c:out value="${abnormalReport.abnormal_type}"/>' /></td>
				<td>异常原因：</td>
				<td class="td2"><input type="text" name="abnormal_realson"
					id="abnormal_realson" readonly="readonly"
					value='<c:out value="${abnormalReport.abnormal_message}"/>' /></td>
			</tr>
			<tr>
				<td>异常件数：</td>
				<td class="td2"><input type="text" name="abnormal_num"
					id="abnormal_num" readonly="readonly"
					value='<c:out value="${abnormalReport.abnormal_num}"/>' /></td>
				<td>上报人姓名：</td>
				<td class="td2"><input type="text" id="abnormal_name"
					name="abnormal_name" readonly="readonly"
					value='<c:out value="${abnormalReport.abnormal_name}"/>'>
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td class="td2" colspan="3"><textarea
						style="width: 400px;resize: none;" name="abnormal_remark"
						readonly="readonly">
							<c:out value="${abnormalReport.abnormal_remark}" />
						</textarea>
				</td>
			</tr>
			<tr align="center">
				<th colspan="4">异常图片</th>
			</tr>
			<tr align="center">
				<td colspan="4">
					<ul class="images" style="list-style-type:none;">
						<c:forEach items="${abnormalReport.abnormalImages}" var="image">
							<li><img class="imgesty"
								src='<c:out value="${image.imageUrl}"/>'>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
		</table>

	</fieldset>

</body>
</html>
