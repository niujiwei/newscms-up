<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>添加补助申请信息</title>
    <meta charset="utf-8">
    <jsp:include page="/inc/easyuiLocation.jsp"/>
    <jsp:include page="../../../ValidationEngine/Validation.jsp"/>
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" href="./js/select2/select2.css" type="text/css"/>
    <script type="text/javascript" src="./js/select2/select2.js"></script>
    <script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>

</head>
<%
    User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
    String shiping_order_id = (String) request.getAttribute("shiping_order_id");
%>
<style>
    .tableclass .td2 input{
        margin: 1px;
    }
</style>
<script type="text/javascript">
    var shiping_order_id ='<%=shiping_order_id%>';
    var dialog,grid;
    $(function(){
        loadData();

    });
    /*
     * 加载数据
     */
    function loadData() {
        $.ajax({
            type : "POST",
            url : 'subsidyInfo.do?method=getShippingOrderMessage',
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
        dialog=$dialog;
        grid=$grid;
        var shiping_order_num = $.trim($("#shiping_order_num").val());
        var shiping_order_goid = $.trim($("#shiping_order_goid").val());
        validateOrderNum(shiping_order_num,shiping_order_goid);
    };

    function validateOrderNum(num,shipordernum){
        var super_far_subsidy =  $("#super_far_subsidy").val();
        var upstairs_subsidy =  $("#upstairs_subsidy").val();
        var super_volume_subsidy =  $("#super_volume_subsidy").val();
        var other_subsidy =$("#other_subsidy").val();
        if(super_far_subsidy==''&&upstairs_subsidy==''
            &&super_volume_subsidy==''&&other_subsidy=='') return $.messager.alert('补助申请', '请填写申请的信息', 'info');
        $.ajax({
            type: "POST",
            url: 'subsidyInfo.do?method=validateSubsidyInfo',
            data: {
                shiping_order_num: num,
                shiping_order_goid: shipordernum
            },
            success: function (data) {
                var success = data.success;
                if(success==false) return $.messager.alert('补助申请', '此订单号已经申请了补助信息', 'info');
                if($("#Regform").validationEngine('validate')){
                    addSubsidyInfo();
                }

            }
        });
    }

    function addSubsidyInfo(){
        $.ajax({
            type: "POST",
            url: 'subsidyInfo.do?method=saveSubsidyInfo',
            data: $('#Regform').serialize(),
            success: function (data) {
                var success = data.success;
                if(success==true) {
                    $.messager.alert('补助申请', '补助信息申请成功', 'info',function () {
                        dialog.dialog('close');
                        grid.datagrid('reload');
                    });

                }else{
                    $.messager.alert('补助申请', '补助信息申请失败', 'info');
                }
            }
        });
    }
</script>
<body >
<form action="" method="post" id="Regform">
    <input type="hidden" name="shiping_order_id">
    <input type="hidden" name="order_type">
    <fieldset>
        <table class="tableclass">
            <tr>
                <th colspan="4">基本信息</th>
            </tr>
            <tr>
                <td>货运编号:</td>
                <td class="td2">
                    <input type="text" id="shiping_order_num" name="shiping_order_num" readonly>
                </td>
                <td>出货订单号:</td>
                <td class="td2">
                    <input type="text" readonly id="shiping_order_goid" name="shiping_order_goid">
                </td>

                <!-- 新增字段 -->
            <tr>
                <td>受理机构:</td>
                <td class="td2">
                    <input type="text" readonly id="send_mechanism" name="send_mechanism">
                </td>
                <td>收货联系人：</td>
                <td class="td2">
                    <input type="text" id="receipt_name" readonly name="receipt_name">
                </td>
            </tr>
            <tr>
                <td>起运时间：</td>
                <td class="td2">
                    <input type="text" id="send_time" name="send_time" readonly="readonly"></td>

                <td>总件数：</td>
                <td class="td2">
                    <input  type="text" id="goods_num" readonly name="goods_num"/>
                </td>
            </tr>
           <%-- <tr>
                <td>客户详细目的地:</td>
                <td class="td2" colspan="3">
                    <input type="text" readonly id="end_mechanism" name="end_mechanism" style="width: 450px;">
                </td>
            </tr>--%>
            <tr>
                <td>终到位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input readonly type="text" id="end_address" name="end_address" style="width: 450px;">
                </td>
            </tr>
            <tr>
                <th colspan="4">申请信息</th>
            </tr>
            <tr>
                <td>实际配送日期：</td>
                <td class="td2">
                    <input  type="text" id="delivery_date" name="delivery_date"  onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/>
                </td>
                <td>超远距离（KM）：</td>
                <td class="td2">
                    <input  type="text" id="super_far_subsidy" name="super_far_subsidy" class="validate[custom[integer],min[0]]"/>
                </td>

            </tr>
            <tr>
                <td>上楼补助（楼层）：</td>
                <td class="td2">
                    <input  type="text" id="upstairs_subsidy" name="upstairs_subsidy" class="validate[custom[integer],min[0]]"/>
                </td>
                <td>超体积补助（总体积）：</td>
                <td class="td2">
                    <input  type="text" id="super_volume_subsidy" name="super_volume_subsidy" class="validate[custom[integer],min[0]]"/>
                </td>

            </tr>
            <tr>
                <td>其它补助:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" id="other_subsidy" cols="45" rows="2"
                              name="other_subsidy"></textarea>
                </td>
            </tr>
            <tr>
                <td>申请备注:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" id="remarks" cols="45" rows="2"
                              name="remarks"></textarea>
                </td>
            </tr>
          </table>
    </fieldset>
</form>
</body>
</html>
