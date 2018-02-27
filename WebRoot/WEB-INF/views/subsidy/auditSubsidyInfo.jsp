<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>审核补助申请信息</title>
    <meta charset="utf-8">
    <jsp:include page="/inc/easyuiLocation.jsp"/>
    <jsp:include page="../../../ValidationEngine/Validation.jsp"/>
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" href="./js/select2/select2.css" type="text/css"/>
    <script type="text/javascript" src="./js/select2/select2.js"></script>
    <script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>

</head>
<%
    User user = (User) request.getSession().getAttribute(SessionInfo.SessionName);
    String id = (String) request.getAttribute("id");
%>
<style>
    .tableclass .td2 input {
        margin: 1px;
    }
</style>
<script type="text/javascript">
    var id = '<%=id%>';
    var dialog, grid;
    var falg='<%=user.getFlag()%>';
    $(function () {
        loadData();

    });
    /*
     * 加载数据
     */
    function loadData() {
        validateOrderNum();
        $.ajax({
            type: "POST",
            url: 'subsidyInfo.do?method=getSubsidyInfoModelById',
            data: {
                id: id
            },
            success: function (data) {
                var order = data.data;
                if (order) {
                    $("#Regform").form('load', order);
                }

            }
        });
    }
    var submitFormEdit = function ($dialog, $grid, $pjq, $mainMenu) {
        dialog = $dialog;
        grid = $grid;
        addSubsidyInfo();
    };

    function validateOrderNum() {
        if (falg==4||falg==3){
            $("#admin_title").css("display","none");
            $("#admin_tr1").css("display","none");
            $("#admin_tr2").css("display","none");
        }else if(falg==0){
            $("#customer_subsidy").attr("readonly","readonly")
            $("#customer_remarks").attr("readonly","readonly")
        }
    }

    function addSubsidyInfo() {
        $.ajax({
            type: "POST",
            url: 'subsidyInfo.do?method=auditSubsidyInfo',
            data: $('#Regform').serialize(),
            success: function (data) {
                var success = data.success;
                if (success == true) {
                    $.messager.alert('补助申请', '补助信息审核成功', 'info', function () {
                        dialog.dialog('close');
                        grid.datagrid('reload');
                    });
                } else {
                    $.messager.alert('补助申请', '补助信息审核失败', 'info');
                }
            }
        });
    }
</script>
<body>
<form action="" method="post" id="Regform">
    <input type="hidden" name="shiping_order_id">
    <input type="hidden" name="id">

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
            <tr>
                <td>收货客户地址:</td>
                <td class="td2" colspan="3">
                    <input type="text" readonly id="end_mechanism" name="end_mechanism" style="width: 450px;">
                </td>
            </tr>
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
                    <input  type="text" readonly id="delivery_date" name="delivery_date"/>
                </td>
                <td>超远距离（KM）：</td>
                <td class="td2">
                    <input  type="text" readonly id="super_far_subsidy" name="super_far_subsidy"/>
                </td>

            </tr>
            <tr>
                <td>上楼补助（楼层）：</td>
                <td class="td2">
                    <input type="text"  readonly id="upstairs_subsidy" name="upstairs_subsidy"/>
                </td>
                <td>超体积补助（总体积）：</td>
                <td class="td2">
                    <input type="text"  readonly id="super_volume_subsidy" name="super_volume_subsidy"/>
                </td>

            </tr>
            <tr>
                <td>其它补助:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" readonly id="other_subsidy" cols="45" rows="2"
                              name="other_subsidy"></textarea>
                </td>
            </tr>
            <tr>
                <td>申请备注:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" readonly id="remarks" cols="45" rows="2"
                              name="remarks"></textarea>
                </td>
            </tr>
            <tr>
                <th colspan="4">项目审核信息</th>
            </tr>
            <tr>
                <td>项目部审核结果（补助金额）:</td>
                <td class="td2">
                    <input type="text"  id="customer_subsidy" name="customer_subsidy"/>
                </td>
            </tr>
            <tr>
                <td>项目部审核备注:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea"  id="customer_remarks" cols="45" rows="2"
                              name="customer_remarks">
                    </textarea>
                </td>
            </tr>
            <tr id="admin_title">
                <th colspan="4">终端审核信息</th>
            </tr>
            <tr id="admin_tr1">
                <td>终端审批结果（补助金额）:</td>
                <td class="td2">
                    <input type="text" id="admin_subsidy" name="admin_subsidy"/>
                </td>
                <td>是否通过:</td>
                <td class="td2">
                    <select id='whether_through' name="whether_through"
                            style="width:150px;height:22px">
                        <option value="0" selected="selected">是</option>
                        <option value="1">否</option>
                    </select>
                </td>
            </tr>
            <tr id="admin_tr2">
                <td>终端审批备注:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea"  id="admin_remarks" cols="45" rows="2"
                              name="admin_remarks">
                    </textarea>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
