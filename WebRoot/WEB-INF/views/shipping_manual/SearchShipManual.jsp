<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html>
<head>


<title>详细运单信息</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
<script type="text/javascript" src="./js/select2/select2_expand.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
	<script type="text/javascript" src="./js/select2/select2.js"></script>
	<script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>
<%
	String flg = (String) request.getAttribute("flg");
%>
</head>
<script type="text/javascript">
	$(function(){
		var pid='<%=flg%>';
		$.ajax({
			url : 'shipManual.do?method=getUpdateShipManual',
			data : {
				id : pid
			},
			success : function(data) {
				if(data.plate_number==null){
				data.plate_number='';
				} 
				//$("#carid").val(data.plate_number);
				//$("#sect").select2('data',{id:data.travel_card_id,text:data.plate_number});
				//$("#sect").prev("div").find("span.textbox combo").text(data.gender);
				/* if(data.check_type==0){
					$("#check1").val("发货人门点");
					}else if(data.check_type==1){
					$("#check1").val("起运货运站");	
				} */
				if(data.send_type==0){
				$("#send").val("自提");}
				else if(data.send_type==1){
				$("#send").val("送货");
				}
				if(data.pay_type==1){				
				$("#type").val("到付");
				}else if(data.pay_type==0){
				$("#type").val("回付");
				} else if(data.pay_type==2){
				$("#type").val("现付");
				
				}else if(data.pay_type==3){
				$("#type").val("货到前付");	
				} 
				//$("#carid").prev("div").find("span.select2-chosen").text(data.plate_number);
				data.updatetime=data.updatetime.substr(0,19)
				$('#Regform').form('load', data);
			}
		});
		/* $("#carid").select2({
			placeholder : "请输入车牌号", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
			}
			}); */
	});
</script>
<body >
	<form action="" method="post" id="Regform">
		<input type="hidden" name="shiping_order_id">
		<fieldset>
			<table class="tableclass">
				<tr>
				<th colspan="4">基本信息</th>
			</tr>
				 <tr>
				 <td >订单号:</td>
					<td  class="td2"><input type="text" class="validate[required]" id="num"
						name="shiping_order_num"readonly="true"  ></td>
				 <td ></font>发站:</td>
					<td  class="td2"><input type="text" id=""
						name="send_station"readonly="true"  ></td>
				</tr>
				<tr>				
				<td >到站:</td>
					<td   class="td2"><input type="text"  class="validate[required]" id=""
						name="end_address"readonly="true"  ></td>
				<td  ></font>发货方：</td>
					<td  class="td2"><input type="text" id=""
						name="custom_name"readonly="true"  ></td>
						</tr>			
			
				<tr>					
						<td >收货方：</td>
					<td  class="td2"><input type="text" class="validate[required]" id=""
						name="receipt"readonly="true"  ></td>
						<td >发货方电话：</td>
					<td  class="td2"><input type="text" class="validate[custom[phone]]" id=""
						name="seller_phone"readonly="true"  ></td>
				</tr>
						<tr>
					
					<td >收货方电话：</td>
					<td  class="td2"><input type="text" class="validate[custom[phone]]" id=""
						name="receipt_tel"readonly="true"  ></td>
						</tr>
				<!-- <tr>
					<td >手机：</td>	
					<td  colspan="3"class="td2"><input type="text"  class="validate[custom[phone],custom[number]]" id=""
						name="receipt_phone"></td>
				</tr> -->
				<tr>
					<td >收货地址/单位：</td>	
					<td colspan="3" class="td2">
					<textarea cols="45" rows="2" type="text" name="receipt_address" readonly="true"  ></textarea>
					</td>
				</tr>
				<tr>
					<td  >品名：</td>
					<td class="td2"><input   type="text"  name="goods_name"readonly="true"  /></td>
					<td  >包装：</td>
					<td  class="td2"><input  type="text"  name="goods_packing" readonly="true" /> 
					</td>
				</tr>

				<tr>
					<td >总件数：</td>
					<td  class="td2"><input  type="text" type="text"  class="validate[custom[integer],min[0]]" placeholder="0"  name="goods_num" readonly="true" /></td>
					<td >总重量(千克)：</td>
					<td  class="td2"><input type="text" type="text"  class="validate[,custom[number],min[0]]" placeholder="0.0000"  name="goods_weight" readonly="true" /></td>	
				</tr>
				
			<tr>
					<td >总体积(立方米)：</td>
					<td  class="td2"><input type="text"  type="text"  class="validate[custom[number],max[99999.9999],min[0.0000]]"   placeholder="0.0000"   name="goods_vol" readonly="true" /></td>
			<!-- 	</tr> 
				<tr>
					<td ><font color="red" style="margin-right:10px">*</font>发货方式：</td>
					<td class="td2">
					<select id='check' class="combobox" name="check_type" style="width:150px;height:22px"
						 data-options="panelHeight : 'auto',editable:false">
							<option id="" value="0">发货人门点</option>
							<option id="" value="1">起运货运站</option>
					</select></td> -->
					<td >提货方式：</td>	
					<td class="td2"><input id='send'  type="text"readonly="true" >
					<input  type="hidden"  name="send_type" readonly="true" >
					<!-- <td >交接方式：</td>
					<td class="td2"><select id='send' class="combobox" name="send_type" style="width:150px;height:22px"
						 data-options="panelHeight : 'auto',editable:false" readonly="true" >
							<option id="" value="0">自提</option>
							<option id="" value="1">送货</option>
					</select> </td> -->
				</tr>	
				<tr>
					<th colspan="4">计费标准</th>
				</tr>
					<tr>
					<td >应付送货费：</td>
					<td class="td2"><input id="delivery_charge_fee"  type="text"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  placeholder="0.00"  name="delivery_charge_fee"readonly="true"  /></td>
					<td >应收送货费：</td>
					<td class="td2"><input id="take_fee" type="text"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  name="take_fee" readonly="true" /></td>					
				</tr>
				<tr>
					<td >应付其他费：</td>
					<td class="td2"><input id="payble_other"  type="text"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  placeholder="0.00"  name="payble_other" readonly="true" /></td>
					<td >应付装卸费：</td>
					<td class="td2"><input id="send_pay" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00" name="payble_coolie"readonly="true"  /></td>
				</tr> 
				<tr>
					<td >应收中转费：</td>
					<td  class="td2"><input id="trunk_fee" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  name="trunk_fee" readonly="true" /></td>
					<td >应付实际接货费：</td>
					<td  class="td2"><input id="delivery_fee" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  name="delivery_fee"readonly="true"  /></td>
				</tr> 
				<tr>
					<td >应收保险费：</td>
					<td  class="td2"><input id="insurance" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  name="insurance"readonly="true"  /></td>
					<td >申明价值：</td>
					<td  class="td2"><input id="affirm_value" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  name="affirm_value"readonly="true"  /></td>
				</tr> 
				<tr>
					<td >应收备注:</td>
					<td colspan="3" class="td2">
					<textarea class="easyui-textarea" cols="45" rows="2" name="remarks_fee"readonly="true" ></textarea></td>
				</tr>
				<tr>
					<!-- <input id="take_pay" type="hidden"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"   value="0.00" name="take_fee" />
					<input id="send_pay" type="hidden"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00" value="0.00" name="send_fee" />
					<input id="adorn_fee"  type="hidden"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  placeholder="0.00" value="0.00" name="adorn_fee" />
					<input id="trunk_fee" type="hidden"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  value="0.00"name="trunk_fee" />
					<input type="hidden"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  value="0.00"  name="insurance" /> 
					 <td >保险费：</td>
					<td  class="td2"><input type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  value="0.00"  name="insurance" /></td>  -->
					 <td >付费方式：</td>
					<td class="td2">
					<input id='type'  type="text" readonly="true" >
					<input   type="hidden" readonly="true"  name="pay_type">
					<!-- <td>付费方式：</td>
					<td class="td2"><select id='type' class="combobox" name="pay_type" style="width:150px;height:22px"
						 data-options="panelHeight : 'auto',editable:false" readonly="true" >
							<option id="" value="0">回付</option>
							<option id="" value="1">现付</option>
							<option id="" value="2">到付</option>
							<option id="" value="3">货到前付</option>
					</select></td> -->
					<td >运费总额：</td>
					<td  class="td2"><input    placeholder="0.00"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  type="text"
						editable="" name="transport_pay" readonly="true" /> 
					</td>
				</tr> 
				<tr>
					<td >代收货款：</td> 
					<td >
					<input type="text" class="validate[custom[number],max[99999.99],min[0.00],custom[dd]] "  placeholder="0.00"  name="trade_agency"  style="width:150px"readonly="true" >
					<td >回扣：</td>
					<td class="td2"><input type="text"  name="back_fee" style="width:150px"readonly="true" >
					</td>
					</tr>
					<td >回单：</td>
					<td class="td2"><input type="text"  name="is_recept" style="width:150px"readonly="true" >
					</td>
					<td >开单时间：</td>
					<td class="td2"><input name="order_time" style="width:150px"readonly="true" >
					</td>
						<!--<tr>
				<td >接货车号：</td>
					<td  class="td2"><input class="search-text" type="hidden"  id="carid" name="car_id" style="width:150px">
					</td>
					<td >规格(米)：</td>
					<td class="td2"><input type="text"  name="spe" style="width:150px">
					</td>
				</tr> 
				<tr>
						<input type="hidden"  value="0" name="shipping_order_state" style="width:150px">
			 	<td >订单状态：</td>
					<td  class="td2"><select id='state'  class="combobox" name="shipping_order_state" style="width:150px;height:22px"
						 data-options="panelHeight : 'auto',editable:false">
							<option id="" value="0">受理</option>
							<option id="" value="1">在途</option>
							<option id="" value="2">到达</option>
							<option id="" value="3">配送</option>
							<option id="" value="4">签收</option>
					</select> 
					</td> 
					<td >中转地：</td>
					<td  class="td2"  colspan="3"><input   type="text"
						editable="" name="change_address" /> 	
					</td>
				</tr> 
				<!-- <tr>
				<td >制单人：</td>
					<td   colspan="3" class="td2"><input   type="text"
						editable="" name="shipping_order" /> 
					</td>
				</tr> -->
				<tr>
				<td >备注:</td>
					<td colspan="3" class="td2">
					<textarea class="easyui-textarea" cols="45" rows="2" name="remarks"readonly="true" ></textarea>
					<input type="hidden"   name="creat_type" value="0" style="width:150px">
					<input   type="hidden"  id="user" editable="" name="shipping_order" /> 
					<input   type="hidden"  id="cid"	editable="" name="custom_id" /> 
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>
