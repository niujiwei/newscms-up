<%@page import="com.jy.common.SessionInfo"%>
<%@page import="com.jy.model.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>运单签收信息</title>


<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="./webuploader/jquery.js"></script>
<script type="text/javascript" src="./webuploader/webuploader.js"></script>
<script type="text/javascript"
	src="./webuploader/singshiporder_uplaod.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<%-- <jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include> --%>
<link rel="stylesheet"
	href="./ValidationEngine/css/validationEngine.jquery.css">
<script src="./ValidationEngine/js/jquery.validationEngine-zh_CN.js"></script>
<script src="./ValidationEngine/js/jquery.validationEngine.js"></script>

<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>


<link rel="stylesheet" type="text/css"
	href="./webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="./webuploader/style.css" />
<%
	String flg = (String) request.getAttribute("flg");
	String isok = (String) request.getAttribute("isok");
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
</head>
<style type="text/css">
.STYLE3 {
	font-size: x-small
}

.bgurang {
	background: rgb(242, 242, 242);
	font-size: 10px;
	text-align: center;
	border-color: rgb(110, 110, 110);
}

.window {
	background-color: #EFF3F7;
}

.bgurangtable {
	background: rgb(242, 242, 242);
	font-size: 10px;
	text-align: right;
	border-color: rgb(110, 110, 110);
}

.bgurangtb {
	background: rgb(242, 242, 242);
	font-size: 10px;
	color: blue;
	text-align: left;
	border-color: rgb(110, 110, 110);
}

.bgurangtbs {
	background: rgb(242, 242, 242);
	color: blue;
	font-size: 13px;
	text-align: center;
	border-color: rgb(110, 110, 110);
}

.sixes {
	font-size: 14px;
}

.btncss {
	background-color: #B7D9F5;
	border: 1px solid #CFDFEC;
	display: inline-block;
	cursor: pointer;
	color: #060606;
	font-family: Arial;
	font-size: 14px;
	padding: 5px 28px;
	text-decoration: none;
	transition: all 0.5s;
	-moz-transition: all 0.5s;
	-webkit-transition: all 0.5s;
	-o-transition: all 0.5s;
}

</style>
<style index="index" id="css_index">
table {
    white-space: normal;
    line-height: normal;
    font-weight: normal;
    font-size: medium;
    font-variant: normal;
    font-style: normal;
    color: -internal-quirk-inherit;
    text-align: start;
}
#details{
	bottom: 44px;
	text-align: center;
	width: 100%;
	padding-top:50px;
	z-index: 0;
	overflow: hidden;
	
}
#deta{
	bottom: 44px;
	text-align: left;
	width: 100%;
	z-index: 0;
	overflow: hidden;
}
</style>	

<script type="text/javascript">
var num;
	$(function(){

				
	$("#signform").validationEngine('attach', { 
				 promptPosition:'topRight:-15,0'
				 });
		var pid='<%=flg%>';
		var isok='<%=isok%>';
		if (isok == 4) {//签收
			$
					.ajax({
						url : 'shipOrder.do?method=getUpdateSignShipOrder',
						data : {
							id : pid
						},
						success : function(data) {
							document.getElementById("signtable").style.display = "";
							$('#Regform').form('load', data);
							$('#signform').form('load', data);
							var inputs = document.getElementsByTagName("input");
							for ( var i = 0; i < inputs.length; i++) {
								inputs[i].setAttribute("readOnly", true);
							}
							document.getElementById("actual_number").disabled = "disabled";
							document.getElementById("defect_number").disabled = "disabled";
							document.getElementById("copies_number").disabled = "disabled";
							document.getElementById("sign_time").disabled = "disabled";
							document.getElementById("sign_result").disabled = "disabled";
							document.getElementById("trupload").style.display = "none";

						}
					});
		} else {//未签收
			$.ajax({
				url : 'shipOrder.do?method=getUpdateShipOrder',
				data : {
					id : pid
				},
				success : function(data) {
					var val = data.shipping_order_state;
					if (val == 0) {
						data.shipping_order_state = "已发运";
					} else if (val == 1) {
						data.shipping_order_state = "分配订单";
					} else if (val == 2) {
						data.shipping_order_state = "司机接单";
					} else if (val == 3) {
						data.shipping_order_state = "电子围栏签收";
					} else if (val == 4) {
						data.shipping_order_state = "实际签收";
					}

					$('#Regform').form('load', data);
				}
			});
		}
		/* $("#carid").select2({
			placeholder : "请输入车牌号", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "car_owner.do?method=getPlateNumberLength",
					data : {
						number : query.term
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
		}); */

		$("#details").show();
		$("#deta").html("");
		if (pid != "" && pid != null) {
			$.ajax({
						type : "post",
						url : 'shipOrder.do?method=getShowOrder',
						data : {
							orders_id : pid
						},
						success : function(msg) {
							$("#deta").html("");
							if (msg == "") {
								$("#deta")
										.append(
												"<tr><td id='error'>*抱歉！未查到此运单"
														+ pid
														+ "信息，请确认运单号码是否正确，或致电终端配送项目组业务合作。</td></tr>");
							} else {
								$.each(msg, function(x, y) {
									$("#deta").append(
											'<tr><td>'
													+ y.goods_arrive_remakes
													+ '</td></tr>');
								});
							}
						}

					});
		}
		//结束
	});

	var _SJNUM = false;//实际数量的变量
	var _QSNUM = false;//缺失数量的变量
	var _HDNUM = false;//回单数量的变量
	var qs = function($dialog, $grid, $pjq, $mainMenu) {
		di();
		var sign = document.getElementById("sign_user").value;

		var number = document.getElementById("sign_usernumber").value;
		if (sign == "") {
			$("#sign_user").val($("#receipt_name").val());
			//$("#sign_user").val($("#receipt").val());
			$("#sign_userphone").val($("#receipt_tel").val());
			$("#actual_number").val($("#goods_num").val());
			$("#defect_number").val(0);
			$("#copies_number").val($("#goods_num").val());
			$("#order_id").val($("#shiping_order_id").val());
			$("#order_number").val($("#num").val());
			num = $("#goods_num").val();
			$("#shipping_orderid").val($("#hishipping_order").val());
			document.getElementById("signtable").style.display = "";
			var scroll_offset = $("#signtable").offset();
			$("body,html").animate({
				scrollTop : scroll_offset.top
			//让body的scrollTop等于pos的top，就实现了滚动
			}, 0);
		} else {
			if ($("#signform").validationEngine('validate')) {

				$pjq.messager.confirm('签收运单', '确定要签收吗?', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : 'shipOrder.do?method=saveSignShipOrder',
							data : $('#signform').serialize(),
							success : function(msg) {
								if (msg.flag) {
									$pjq.messager.alert('签收运单', '签收订单成功',
											'info');
									$dialog.dialog('close');
									$grid.datagrid('reload');
								} else {
									$pjq.messager.alert('签收运单', '签收订单失败',
											'info');
								}
								;
							}
						});
					}
				});
			}
		}
	};

	function checksj(obj) {
		//alert(num);
		if (obj.value <= num) {
			_SJNUM = true;
		} else {
			$.messager.alert('签收运单', '大于运单实际数量', 'info');
			//alert(num);
			obj.value = num;
		}
	}
	function checkqs(obj) {
		if (obj.value <= num) {
			_QSNUM = true;
		} else {
			$.messager.alert('签收运单', '大于运单实际数量', 'info');
			obj.value = 0;
		}
	}
	function checkhd(obj) {
		if (obj.value <= num) {
			_HDNUM = true;
		} else {
			$.messager.alert('签收运单', '大于运单实际数量', 'info');
			obj.value = num;
		}
	}
</script>
<body>
	<div class="easyui-tabs" id="tt">
		<div title="运单详细信息" id="order" style="">
			<form action="" method="post" id="Regform">
				<input type="hidden" name="shipping_order" id="hishipping_order">
				<fieldset>
					<table class="tableclass">
						<tr>
							<th colspan="4">基本信息</th>
						</tr>
						<tr>
							<td>货运编号:</td>

							<td class="td2"><input type="text" id="num"
								name="shiping_order_num" readonly="readonly"></td>

							<td>运单状态:</td>
							<td class="td2"><input type="text" id=""
								name="shipping_order_statestr" readonly="readonly"></td>
						</tr>
						<tr>

							<td>出货订单号:</td>
							<td class="td2"><input type="text" id="shiping_order_goid"
								name="shiping_order_goid" readonly="readonly"></td>
							<td>受理机构:</td>
							<td class="td2"><input type="text" id=""
								name="send_mechanism" readonly="readonly"></td>
						<tr>
							<td>起运时间：</td>
							<td class="td2"><input type="text" id="" name="send_time"
								readonly="readonly"></td>


							<td>终到位置：</td>
							<td class="td2"><input type="text" id="end_address"
								name="end_address" readonly="readonly"></td>
						</tr>

						<tr>
							<td>客户详细目的地：</td>
							<td class="td2"><input type="text" id=""
								name="end_mechanism" readonly="readonly"></td>
							<td>客户编号：</td>
							<td class="td2"><input type="text" id="" name="custom_code"
								readonly="readonly"></td>
						</tr>
						<tr>
							<td>发货客户名称：</td>
							<td class="td2"><input type="text" id="custom_name"
								name="custom_name" readonly="readonly">
							</td>
							<td>发货客户联系人：</td>
							<td class="td2"><input type="text" id="custom_contact"
								name="custom_contact" readonly="readonly"></td>
						</tr>
						<tr>
							<td>发货客户电话：</td>
							<td class="td2"><input type="text" id="custom_tel"
								name="custom_tel" readonly="readonly"></td>
							<td>收货客户名称：</td>
							<td class="td2"><input type="text" id="receipt"
								name="receipt" readonly="readonly"></td>
						</tr>
						<tr>
							<td>到货联系人：</td>
							<td class="td2"><input type="text" id="receipt_name"
								name="receipt_name" readonly="readonly"></td>

							<td>到货联系电话：</td>
							<td class="td2"><input type="text" id="receipt_tel"
								name="receipt_tel" readonly="readonly"></td>
						</tr>

						<tr>
							<td>货物名称：</td>
							<td class="td2"><input type="text" readonly="readonly"
								name="goods_name" />
							</td>

							<td>总件数：</td>
							<td class="td2"><input type="text" id="goods_num"
								readonly="readonly" type="text" placeholder="0" name="goods_num" />
							</td>
						</tr>

						<tr>
							<td>总重量(千克)：</td>
							<td class="td2"><input type="text" readonly="readonly"
								type="text" placeholder="0.0000" name="goods_weight" /></td>

							<td>总体积(立方米)：</td>
							<td class="td2"><input type="text" readonly="readonly"
								type="text" placeholder="0.0000" name="goods_vol" /></td>

						</tr>
						<tr>
							<th colspan="4">计费标准</th>
						</tr>
						<tr>
							<td>配送费：</td>
							<td class="td2"><input id="deliver_fee" readonly="readonly"
								type="text" name="deliver_fee" /></td>
							<td>上楼费：</td>
							<td class="td2"><input id="upstairs_fee" readonly="readonly"
								type="text" name="upstairs_fee" /></td>
						</tr>
						<tr>
							<td>附加费：</td>
							<td class="td2"><input id="added_fee" readonly="readonly"
								type="text" placeholder="0.00" name="added_fee" /></td>
							<td>其他费用：</td>
							<td class="td2"><input id="other_fee" readonly="readonly"
								type="text" placeholder="0.00" name="other_fee" /></td>
						</tr>
						<tr>
							<td>运费总额：</td>
							<td class="td2"><input placeholder="0.00" type="text"
								editable="" name="transport_pay" readonly="readonly" />
							</td>
							<td>代收货款：</td>
							<td><input type="text" readonly="readonly"
								placeholder="0.00" name="trade_agency" style="width:150px">
						</tr>
						<tr>
							<td>回单份数：</td>
							<td class="td2"><input type="text" name="is_recept"
								style="width:150px">
							</td>
						</tr>

						<tr>
							<td>备注:</td>
							<td colspan="3" class="td2"><textarea
									class="easyui-textarea" cols="45" rows="2" name="remarks"></textarea>
								<input type="hidden" name="creat_type" value="0"
								style="width:150px"> <input type="hidden" id="user"
								editable="" name="shipping_order" /> <input type="hidden"
								id="cid" editable="" name="custom_id" />
							</td>
						</tr>

					</table>
				</fieldset>
				<input readonly="readonly" type="hidden" name="shiping_order_id"
					id="shiping_order_id">
			</form>

			<form action="" id="signform">
				<input readonly="readonly" type="hidden" name="order_id"
					id="order_id"> <input readonly="readonly" type="hidden"
					name="order_number" id="order_number">
				<fieldset>
					<table class="tableclass" style="display: none;" id="signtable">
						<tr>
							<td><font color="red" style="margin-right:10px">*</font>收货人：</td>
							<td class="td2"><input class="" type="text" name="sign_user"
								id="sign_user" class="validate[required]" />
							</td>
							<td><font color="red" style="margin-right:10px">*</font>收货人证件：</td>
							<td class="td2"><input type="text" editable=""
								class="validate[required]" name="sign_usernumber"
								id="sign_usernumber" />
							</td>

						</tr>
						<tr>

							<td>收货代理人：</td>
							<td class="td2"><input type="text" name="sign_name"
								id="sign_name" />
							</td>
							<td><span style="color: red"></span>收货代理人证件：</td>
							<td class="td2"><input type="text" editable=""
								name="sign_number" id="sign_number" />
							</td>
						<tr>

							<td><font color="red" style="margin-right:10px">*</font>手机号：</td>
							<td class="td2"><input id="sign_userphone"
								name="sign_userphone" class="validate[required]" />
							</td>

							<td style="color: blue;"><span style="color: red"></span>签收结果：</td>
							<td class="td2"><select id='sign_result' name="sign_result"
								class="combobox" name="shipping_order_state"
								style="width:150px;height:22px"
								data-options="panelHeight : 'auto',editable:false">
									<option id="" value="0">异常签收</option>
									<option id="" value="1" selected="selected">正常签收</option>
							</select>
						</tr>
						<tr>
							<td style="color: blue;"><span style="color: red"></span><font
								color="red" style="margin-right:10px">*</font>实际数量：</td>
							<td class="td2"><input type="text" editable=""
								name="actual_number" id="actual_number" onblur="checksj(this)"
								class="validate[required]" /></td>

							<td style="color: blue;"><span style="color: red"></span><font
								color="red" style="margin-right:10px">*</font>缺失数量：</td>
							<td class="td2"><input type="text" editable=""
								name="defect_number" id="defect_number" onblur="checkqs(this)"
								class="validate[required]" /></td>
						</tr>
						<tr>
							<td style="color: blue;"><span style="color: red"></span><font
								color="red" style="margin-right:10px">*</font>回单份数：</td>
							<td class="td2"><input type="text" editable=""
								name="copies_number" id="copies_number" onblur="checkhd(this)"
								class="validate[required]" /></td>

							<td style="color: blue;"><span style="color: red"></span><font
								color="red" style="margin-right:10px">*</font>收货日期：</td>
							<td class="td2"><input
								onfocus="WdatePicker({isShowWeek:true})" id="sign_time"
								name="sign_time" class="validate[required]" />
							</td>
						</tr>
						<tr>
							<td style="color: blue;">收货经办:</td>
							<td class="td2"><input type="text" editable=""
								readonly="readonly" name="shipping_order" id="shipping_orderid"
								onblur="checkhd(this)" /></td>
						</tr>
						<tr>
							<td style="color: blue;">收货备注:</td>
							<td colspan="3" class="td2"><textarea
									class="easyui-textarea" cols="45" rows="2" name="sign_remarks"></textarea>
							</td>
						</tr>
						<tr id="trupload">
							<td>回单上传：</td>
							<td colspan="3">
								<div id="wrapper">
									<div id="container">
										<!--头部，相册选择和格式选择-->

										<div id="uploader">
											<div class="queueList">
												<div id="dndArea" class="placeholder">
													<div id="filePicker"></div>
													<p>请将照片拖到这里，单次最多可选5张</p>
												</div>
											</div>
											<div class="statusBar" style="display:none;">
												<div class="progress">
													<span class="text">0%</span> <span class="percentage"></span>
												</div>
												<div class="info"></div>
												<div class="btns">
													<div id="filePicker2"></div>
													<div class="uploadBtn">开始上传</div>
												</div>
											</div>
										</div>
									</div>
								</div> <!--   --></td>
						</tr>
					</table>
				</fieldset>
			</form>
			<input readonly="readonly" type="hidden" name="creat_type" value="0"
				style="width:150px">
		</div>
		<div title="运单状态" style="overflow:auto;padding:5px;" id="dddddd">
			<div id="details" style="text-align:left">
				<div id="loading"></div>
				<table id="deta">
				</table>
			</div>
		</div>
	</div>
</body>
</html>
