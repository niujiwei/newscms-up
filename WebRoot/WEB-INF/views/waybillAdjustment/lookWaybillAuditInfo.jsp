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
.ul_message {
	white-space: normal;
	line-height: normal;
	font-weight: normal;
	font-size: x-large;
	font-variant: normal;
	font-style: normal;
	color: blue;
	border-collapse: separate;
}
.tableclass tr th {
	font-size: 20px;
}
</style>
</head>
<% String id = (String) request.getAttribute("id");%>

<script type="text/javascript">
	var id ='<%=id%>';
	$(function () {
        $.ajax({
            url : 'waybillAdjustment.do?method=getWaybillAuditInfo',
            type : 'post',
            data : {
                id:id
			},
            success : function(data) {
                if(data.success){
                    var successData = data.success;
                    var order = successData.shippingOrder;
                    $("#shiping_order_id").val(order.shiping_order_id);
                    $("#shiping_order_num").val(order.shiping_order_num);
                    $("#shiping_order_goid").val(order.shiping_order_goid);
                    $("#send_time").val(order.send_time);
                    $("#send_mechanism").val(order.send_mechanism);
                    $("#receipt_name").val(order.receipt_name);
                    $("#receipt_tel").val(order.receipt_tel);
                    $("#goods_name").val(order.goods_name);
                    $("#goods_num").val(order.goods_num);
                    $("#goods_weight").val(order.goods_weight);
                    $("#goods_vol").val(order.goods_vol);
                    $("#applicant_type").val(successData.applicant_type);
                    if(successData.applicant_type==1) {
                        successData.applicant_type="取消签收";
                        $("#tr_tittle").css("display","none");
                        $("#tr_message").css("display","none");
                    }
                    if(successData.applicant_type==0) {
                        successData.applicant_type="运单调整";
                        $("#tr_tittle").css("display","");
                        $("#tr_message").css("display","");
                    }
                    if(successData.applicant_state==1){
                        $("#tr_auditor_tittle").css("display","");
                        $("#tr_auditor_message").css("display","");
                        $("#tr_auditor_message2").css("display","");
					}
                    $("#applicant_type_name").val(successData.applicant_type);
                    $("#applicant_message").val(successData.applicant_message);
                    $("#applicant_time").val(successData.applicant_time);
                    $("#applicant_name").val(successData.applicant_name);

                    $("#auditor_time").val(successData.auditor_time);
                    $("#auditor_name").val(successData.auditor_name);
                    $("#auditor_message").text(successData.auditor_message);
				}
            }
        });

        $.ajax({
            url : 'waybillAdjustment.do?method=getWaybillAuditHistoryListInfo',
            type : 'post',
            data : {
                id:id
            },
            success : function(data) {
                var successData = data.success;
                if(successData){
                    $.each(successData,function(index,data){
                        $("#ul_mess_id").append("<li>"+(index+1)+":"+data.waybill_adjustment_message+"</li>");
                    });
				}

            }
        });

    });
	var submitHandling = function($dialog, $grid, $pjq) {
		$.messager.prompt('运单调整审核', '请输入审核信息', function(r) {
			if (r) {
               var shiping_order_id = $("#shiping_order_id").val();
               var applicant_type = $("#applicant_type").val();
               var shiping_order_num =$("#shiping_order_num").val();
                $.messager.progress();
				$.ajax({
					url : 'waybillAdjustment.do?method=doWaybillAuditInfo',
					type : 'post',
					data : {
					    id:id,
                        shiping_order_id:shiping_order_id,
                        auditor_message:r,
                        applicant_type:applicant_type,
                        shiping_order_num:shiping_order_num
					},
					success : function(data) {
						if (data.success) {
							$pjq.messager
									.alert('运单调整', '运单调整审核成功', 'info');
							$dialog.dialog('close');
							$grid.datagrid('reload');
						} else {
							$pjq.messager.alert('运单调整', '运单调整审核失败', 'info');
						}
					},
                    error:function () {
                        $.messager.alert('运单调整', '系统出错了', 'info');
                    }
				});
			}
		});

	};
</script>
<body>
	<form action="" method="post" id="">
	<%--	<input type="hidden" id="shiping_order_id" name="shiping_order_id"> <input
			type="hidden" id="abnormalId" name="id">
		<input type="hidden" id="abnormal_supperid" name="abnormal_supperid"> <input
			type="hidden" id="abnormal_driverid" name="abnormal_driverid"> <input
			type="hidden" id="abnormal_resultremark" name="abnormal_resultremark">
			<input type="hidden" id="order_num" name="order_num">--%>
		<input type="hidden" id="shiping_order_id" name="shiping_order_id">
	</form>
	<fieldset>
		<table class="tableclass">
			<tr>
				<th colspan="4">运单信息</th>
			</tr>
			<tr>
				<td>货运编号：</td>
				<td class="td2"><input type="text" id="shiping_order_num" name="shiping_order_num"
					readonly="readonly">
				</td>
				<td>出货订单号：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="shiping_order_goid" name="shiping_order_goid">
				</td>
			</tr>
			<tr>
				<td>起运时间：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="send_time" name="send_time">
				</td>
				<td>受理机构：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="send_mechanism" name="send_mechanism">
				</td>
			</tr>
			<tr>
				<td>收货联系人：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="receipt_name" name="receipt_name">
				</td>
				<td>收货客户电话：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="receipt_tel" name="receipt_tel">
				</td>
			<tr>
			<tr>
				<td>货物名称：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="goods_name" name="goods_name">
				</td>
				<td>总件数：</td>
				<td class="td2"><input readonly="readonly" id="goods_num"
					name="goods_num"/>
				</td>
			</tr>
			<tr>
				<td>总重量：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="goods_weight" name="goods_weight">
				</td>
				<td>总体积：</td>
				<td class="td2"><input type="text" readonly="readonly"
					id="goods_vol" name="goods_vol">
				</td>
			</tr>
		</table>
		<table class="tableclass">
			<tr>
				<th colspan="4">申请信息</th>
			</tr>
			<tr>
				<td>申请类型：</td>
				<td class="td2"><input type="text" name="applicant_type"
					id="applicant_type_name" readonly="readonly"/>
					<input type="hidden" id="applicant_type">
				</td>
				<td>申请原因：</td>
				<td class="td2"><input type="text" name="applicant_message"
					id="applicant_message" readonly="readonly"/></td>
			</tr>
			<tr>
				<td>申请时间：</td>
				<td class="td2"><input type="text" name="applicant_time"
					id="applicant_time" readonly="readonly"/></td>
				<td>申请人：</td>
				<td class="td2"><input type="text" id="applicant_name"
					name="applicant_name" readonly="readonly">
				</td>
			</tr>
			<tr align="center" id="tr_tittle" style="display: none">
				<th colspan="4">运单调整信息</th>
			</tr>
			<tr align="center" id="tr_message" style="display: none">
				<td colspan="4">
					<ul class="ul_message" style="list-style-type:none;" id="ul_mess_id">
					</ul>
				</td>
			</tr>
			<tr align="center" id="tr_auditor_tittle" style="display: none">
				<th colspan="4">审核信息</th>
			</tr>
			<tr align="center" id="tr_auditor_message" style="display: none">
				<td>审核时间：</td>
				<td class="td2"><input type="text" id="auditor_time" name="auditor_time"
									   readonly="readonly"/>
					<input type="hidden" >
				</td>
				<td>审核人 ：</td>
				<td class="td2"><input type="text" id="auditor_name" name="auditor_name"
									  readonly="readonly"/></td>
			</tr>
			<tr id="tr_auditor_message2" style="display: none">
				<td>审批内容：</td>
				<td class="td2" colspan="3">
					<textarea type="text" id="auditor_message"  style="width: 400px;resize: none;" name="auditor_message" readonly="readonly"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>

</body>
</html>
