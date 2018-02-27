<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML>
<html>
<head>
	<title>添加运单信息</title>
	<meta charset="utf-8">
	<!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
	<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
	<jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
	<script type="text/javascript" src="./js/select2/select2.js"></script>
	<script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>

</head>
<%
	User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
	String shiping_order_id = (String) request.getAttribute("shiping_order_id");
%>
<style>
/*	.font_color{
		color: #ff0000;
	}*/
	.tableclass .td2 input{
		margin: 1px;
	}
</style>
<script type="text/javascript">
    var oneid = 0;
    var twoid = 0;
    var threeid =0;
    var shiping_order_id ='<%=shiping_order_id%>';
    $(function(){
        var $target = $('input,textarea,select');
        $target.bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                var nxtIdx = $target.index(this) + 1;
                $target.eq(nxtIdx).focus();
            }
        });
        loadData();

        $("#Regform").validationEngine('attach', {
            promptPosition:'topRight:-15,0'
        });

        $("#cn").select2({  //自动完成
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
                            data.results.push({id: y.id, text: y.name,people:y.people,tel:y.tel});
                        });
                        query.callback(data);
                    }
                });
            },
        }).on("select2-selecting", function(f) {
            $("#custom_name").val(f.object.text);
            $("#cnpeople").val(f.object.people);
            $("#cntel").val(f.object.tel);
        });
        $("#user").val('<%=user.getUsername()%>');
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
    });

    /*
    * 加载数据
    */
    function loadData() {
        $.ajax({
            type : "POST",
            url : 'waybillAdjustment.do?method=getShippingOrderMessage',
            data : {
                orderId:shiping_order_id
            },
            success : function(data){
                var order = data.order;
                if(order){
                    $("#Regform").form('load', order);
				}

            }
        });
    }
    var submitForm = function ($dialog, $grid, $pjq, $mainMenu) {
        var orderData = {
            order: {
                shiping_order_id:shiping_order_id
			},
            changeMessage: [],
            dialog:$dialog,
            grid:$grid,
            pjq:$pjq
        };
        var num = $.trim($("#new_shiping_order_num").val());
        var shipordernum = $.trim($("#new_shiping_order_goid").val());
        var old_order_num = $.trim($("#shiping_order_num").val());
        var oldshipordernum = $.trim($("#shiping_order_goid").val());
       if((num!=old_order_num&&num!='')||oldshipordernum!=shipordernum&&shipordernum!=''){
           validateOrderNum(num,shipordernum,orderData);
		}else{
           compareOrder(orderData);
		}

    };

    function validateOrderNum(num,shipordernum,orderData){
        $.ajax({
            type: "POST",
            url: 'shipOrder.do?method=getNumber',
            data: {
                number: num,
                shipordernum: shipordernum
            },
            success: function (data) {
                if (data.shiping_order_num != null) {
                    $.messager.alert('运单调整', '此订单号已经存在', 'info');
                } else {
                    compareOrder(orderData);
                }
            }
        });
	}

    function compareOrder(orderData){
        var object,data,inputId,oldData,changeText;
        var province = $("#province").combobox('getText');
        var city = $("#city").combobox('getText');
        var county = $("#county").combobox('getText');
        $("#new_end_address").val(province+city+county);
		var inputList = $("input[mytype='new']");
		for(var i=0;i<inputList.length;i++){
		    object =  $(inputList[i]);
            changeText= $(object.context.parentNode).prev().context.previousElementSibling.innerText;
		    console.info();
			data = object.val();
			inputId = object.attr("myid");
			oldData = $("#"+inputId).val();
			if(data!=''){
                if(data!=oldData){
                    var listMessage={};
                    listMessage["waybill_adjustment_tittle"]=changeText;
                    listMessage["waybill_adjustment_after"]=data;
                    listMessage["waybill_adjustment_before"]=oldData;
                    listMessage["waybill_adjustment_message"]=changeText+"由 ["+oldData+"] 变成 ["+data+"]";
                    orderData.changeMessage.push(listMessage);
				}
                orderData.order[inputId] = data;
			}else{
                orderData.order[inputId] = oldData;
			}
		}
        addWaybillAdjustmentInfo(orderData);
	}
	function addWaybillAdjustmentInfo(orderData){
		if(orderData.changeMessage.length==0) return $.messager.alert('运单调整', '未调整任何运单信息', 'info');
		var applicant_message = $("#applicant_message").val();
		if(applicant_message==null||applicant_message=='') return $.messager.alert('运单调整', '请填写运单调整原因', 'info');
        $.messager.progress();
        $.ajax({
		    url:"waybillAdjustment.do?method=addWaybillAdjustmentInfo",
			type:"post",
			data:{
                orderData:JSON.stringify(orderData.order),
                changeMessage:JSON.stringify(orderData.changeMessage),
                applicant_message:applicant_message,
                applicant_type:0,
                shiping_order_num:$("#shiping_order_num").val()
			},
			success:function (data) {
				if(data.success){
                    orderData.pjq.messager.alert('运单调整', '信息已经申报成功，请等待审核', 'info');
                    orderData.dialog.dialog('close');
                    orderData.grid.datagrid('reload');
				}else{
                    return  $.messager.alert('运单调整', '信息已经申报失败', 'info');
				}
            },
			error:function () {
                $.messager.alert('运单调整', '系统出错了', 'info');
            }

		});
	}
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
				<td >货运编号:</td>
				<td class="td2">
					<input type="text" id="shiping_order_num" name="shiping_order_num" readonly>
					<input type="text" id="new_shiping_order_num" mytype="new" myid="shiping_order_num">
				</td>
				<td>出货订单号:</td>
				<td class="td2">
					<input type="text" readonly id="shiping_order_goid" name="shiping_order_goid">
					<input type="text" id="new_shiping_order_goid" mytype="new" myid="shiping_order_goid">
				</td>

				<!-- 新增字段 -->
			<tr>
				<td>受理机构:</td>
				<td class="td2">
					<input type="text" readonly id="send_mechanism" name="send_mechanism">
					<input type="text" mytype="new" myid="send_mechanism">
				</td>
				<td>起运地:</td>
				<td class="td2">
					<input type="text" id="send_station" readonly name="send_station">
					<input type="text" mytype="new" myid="send_station">
				</td>
			</tr>
			<!-- ///////////////////////////////////////////// -->
			<tr>
				<td >起运时间：</td>
				<td  class="td2">
					<input type="text" id="send_time" name="send_time" readonly="readonly">
					<input type="text"  mytype="new" myid="send_time" readonly="readonly" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})">
				</td>

				<td >月结编号：</td>
				<td  class="td2">
					<input type="text" id="shiping_yueid" readonly name="shiping_yueid">
					<input type="text" mytype="new" myid="shiping_yueid">
				</td>
			</tr>
			<tr>
				<td>收货客户地址:</td>
				<td class="td2"  colspan="3">
					<input type="text" readonly id="end_mechanism" name="end_mechanism"style="width: 450px;">
					<input type="text" mytype="new" myid="end_mechanism" style="width: 450px;">
				</td>
			</tr>
			<tr>
				<td>终到位置:</td>
				<td class="td2" colspan="3" style="height:40px">
					<input readonly type="text" id="end_address" name="end_address" style="width: 450px;">
					<br>
						<input id="province"  name="new_driver_province">省
						<input  id="city"  name="new_driver_city">市
						<input  id="county" name="new_driver_county">区
					    <input type="hidden" id="new_end_address" mytype="new" myid="end_address">
				</td>
			</tr>
			<tr>
				<td >发货客户名称：</td>
				<td  class="td2">
					<input type="text" id="custom_name" name="custom_name">
					<%--<input type="text" id="cn">--%>
					<input type="text" mytype="new" myid="custom_name">
				</td>
				<td >发货联系人：</td>
				<td  class="td2">
					<input type="text" readonly id="custom_contact" name="custom_contact">
					<input type="text" mytype="new" myid="custom_contact">
				</td>
			</tr>

			<tr>
				<td >发货客户电话：</td>
				<td  class="td2">
					<input type="text" id="custom_tel" readonly name="custom_tel">
					<input type="text" mytype="new" myid="custom_tel">
				</td>
				<td >收货客户名称：</td>
				<td  class="td2">
					<input type="text" id="receipt" readonly name="receipt">
					<input type="text" mytype="new" myid="receipt">
				</td>
			</tr>
			<tr>
				<td >收货联系人：</td>
				<td  class="td2">
					<input type="text" id="receipt_name" readonly name="receipt_name">
					<input type="text" mytype="new" myid="receipt_name">
				</td>

				<td >收货客户电话：</td>
				<td  class="td2">
					<input type="text" readonly id="receipt_tel" name="receipt_tel">
					<input type="text" mytype="new" myid="receipt_tel">
				</td>
			</tr>

			<tr>
				<td >货物名称：</td>
				<td  class="td2">
					<input  type="text" id="goods_name" readonly name="goods_name" />
					<input type="text" mytype="new" myid="goods_name">
				</td>

				<td >总件数：</td>
				<td  class="td2">
					<input  type="text" type="text" id="goods_num" readonly name="goods_num" />
					<input  type="text" type="text"  class="validate[custom[integer],min[0]]" placeholder="0"  mytype="new" myid="goods_num" />
				</td>
			</tr>

			<tr>
				<td >总重量(千克)：</td>
				<td  class="td2">
					<input type="text" type="text" id="goods_weight" readonly name="goods_weight" />
					<input type="text" mytype="new" myid="goods_weight" class="validate[,custom[number],min[0]]" placeholder="0.0000"  />
				</td>

				<td >总体积(立方米)：</td>
				<td  class="td2">
					<input type="text"  type="text"  id="goods_vol" readonly name="goods_vol" />
					<input type="text"  type="text" mytype="new" myid="goods_vol" class="validate[custom[number],max[99999.9999],min[0.0000]]"   placeholder="0.0000" />
				</td>

			</tr>
			<tr>
				<th colspan="4">计费标准</th>
			</tr>
			<tr>
				<td >配送费：</td>
				<td class="td2">
					<input  type="text"  readonly id="deliver_fee" name="deliver_fee" />
					<input mytype="new" myid="deliver_fee" type="text"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00" />
				</td>
				<td >上楼费：</td>
				<td class="td2">
					<input type="text" id="upstairs_fee" readonly name="upstairs_fee" />
					<input mytype="new" myid="upstairs_fee" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00" />
				</td>
			</tr>
			<tr>
				<td >附加费：</td>
				<td class="td2">
					<input type="text"  readonly id="added_fee" name="added_fee" />
					<input mytype="new" myid="added_fee"  type="text"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  placeholder="0.00" />
				</td>
				<td >其他费用：</td>
				<td  class="td2">
					<input  type="text"  id="other_fee" readonly name="other_fee" />
					<input mytype="new" myid="other_fee" type="text"   class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" placeholder="0.00"  />
				</td>
			</tr>
			<tr>
			<%--	<td >运费总额：</td>
				<td  class="td2">
					<input type="text" readonly name="transport_pay" />
					<input placeholder="0.00"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"  type="text"
						   editable="" name="new_transport_pay" />
				</td>--%>
				<td >代收货款：</td>
				<td  class="td2">
					<input type="text" name="trade_agency" id="trade_agency" readonly>
					<input type="text"mytype="new" myid="trade_agency" class="validate[custom[number],max[99999.99],min[0.00],custom[dd]] "  placeholder="0.00"  style="width:150px">
				<td >回单份数：</td>
				<td class="td2">
					<input type="text"  id="is_recept" name="is_recept" readonly>
					<input type="text" mytype="new" myid="is_recept"  class="validate[custom[number],max[99999.99],min[0.00],custom[dd]] " style="width:150px">
				</td>
			</tr>
		<%--	<tr>
				<td >回单份数：</td>
				<td class="td2">
					<input type="text"  name="is_recept" readonly>
					<input type="text"  name="new_is_recept" class="validate[custom[number],max[99999.99],min[0.00],custom[dd]] " style="width:150px">
				</td>
			</tr>--%>

			<tr>
				<td >申请原因:</td>
				<td colspan="3" class="td2">
					<textarea class="easyui-textarea" id="applicant_message" cols="45" rows="2" name="applicant_message"></textarea>
					<!--<input type="hidden"   name="creat_type" value="0" style="width:150px">
					 <input   type="hidden"  id="user" editable="" name="shipping_order" />
					<input   type="hidden"  id="cid"	editable="" name="custom_id" />-->
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>
