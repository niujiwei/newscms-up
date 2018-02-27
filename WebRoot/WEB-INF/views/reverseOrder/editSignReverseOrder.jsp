<%@page import="com.jy.common.SessionInfo" %>
<%@page import="com.jy.model.User" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


    <title>逆向物流--签收运单信息</title>


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
          href="./webuploader/webuploader.css"/>
    <link rel="stylesheet" type="text/css" href="./webuploader/style.css"/>
    <%
        String id = (String) request.getAttribute("id");
        User user = (User) request.getSession().getAttribute(
                SessionInfo.SessionName);
    %>
</head>
<script type="text/javascript">
    var num;
    $(function () {
        $("#signform").validationEngine('attach', {
            promptPosition: 'topRight:-15,0'
        });
        var pid = '<%=id%>';
        $.ajax({
            url: 'reverse.do?method=getSignReverseOrderById',
            data: {
                id: pid
            },
            success: function (data) {
                var object = data.success;
                if(object.shipping_order_state>=4){
                    loadSingData(object);
                }else {
                    loadData(object);
                }
            }
        });
    });

    var qs = function ($dialog, $grid, $pjq, $mainMenu) {
        di();
        var sign = document.getElementById("sign_user").value;
        if (sign == "") {
            $("#order_number").val($("#shiping_order_num").val());
            $("#sign_user").val($("#end_mechanism").val());
            $("#actual_number").val($("#goods_num").val());
            $("#defect_number").val(0);
            $("#sign_userphone").val($("#receipt_tel").val());
            $("#order_id").val($("#shiping_order_id").val());
            num = $("#goods_num").val();
            $("#shipping_orderid").val('<%=user.getUsername()%>');
            document.getElementById("signtable").style.display = "";
            var scroll_offset = $("#signtable").offset();
            $("body,html").animate({
                scrollTop: scroll_offset.top
            }, 0);
        } else {
            if ($("#signform").validationEngine('validate')) {
                $pjq.messager.confirm('签收运单', '确定要签收吗?', function (r) {
                    if (r) {
                        $.ajax({
                            type: "POST",
                            url: 'reverse.do?method=saveSignReverseOrderInfo',
                            data: $('#signform').serialize(),
                            success: function (msg) {
                                if (msg.success) {
                                    $pjq.messager.alert('签收运单', '签收订单成功',
                                        'info');
                                    $dialog.dialog('close');
                                    $grid.datagrid('reload');
                                } else {
                                    $pjq.messager.alert('签收运单', '签收订单失败',
                                        'info');
                                }

                            }
                        });
                    }
                });
            }
        }
    };

    function loadSingData(data) {
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
    function loadData(data) {
        $('#Regform').form('load', data);
    }
</script>
<body>
<form action="" method="post" id="Regform">
    <input id="shiping_order_id" type="hidden" name="shiping_order_id">
    <fieldset>
        <table class="tableclass">
            <tr>
                <th colspan="4">基本信息</th>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>货运编号:</td>
                <td colspan="1" class="td2">
                    <input type="text" class="" readonly id="shiping_order_num" name="shiping_order_num">

                </td>
                <td><font color="red" style="margin-right:10px">*</font>逆向物流类型:</td>
                <td colspan="1" class="td2">
                    <select id='order_type' readonly="true" name="order_type"
                            style="width:150px;height:22px">
                        <option value="0" selected="selected">退货</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>起运地:</td>
                <td class="td2">
                    <input type="text" class="" readonly id="send_station"
                           name="send_station"></td>
                <td><font color="red" style="margin-right:10px">*</font>受理机构:</td>
                <td class="td2" style="height:40px">
                    <input type="text" readonly id="send_mechanism" class="" name="send_mechanism"></td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>调拨/退货始发位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input type="text" readonly id="end_address" name="end_address">
                </td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>提货时效:</td>
                <td class="td2" style="height:40px">
                    <input type="text"  readonly class="" id="time_limitation"
                           name="time_limitation">
                </td>
                <td><font color="red" style="margin-right:10px">*</font>起运时间:</td>
                <td class="td2" style="height:40px">
                    <input type="text"  class="validate[required]" id="send_time"
                           name="send_time" readonly="readonly"
                           >
                </td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>发货联系人：</td>
                <td class="td2">
                    <input type="text" readonly class="" id="custom_contact"
                           name="custom_contact">
                </td>
                <td><font color="red" style="margin-right:10px">*</font>发货客户电话：</td>
                <td class="td2">
                    <input type="text" readonly id="custom_tel" class="" name="custom_tel">
                </td>
            </tr>
            <tr>
                <td><font color="red" readonly style="margin-right:10px">*</font>总件数：</td>
                <td class="td2">
                    <input type="text" readonly class="validate[custom[integer],min[0]]"
                           placeholder="0" name="goods_num"/>
                </td>
                <td><font color="red" style="margin-right:10px">*</font>实际件数:</td>
                <td class="td2">
                    <input type="text" readonly class="validate[custom[integer],min[0]]"
                           placeholder="0" name="real_num"/>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td colspan="3" class="td2">
                    <textarea readonly class="easyui-textarea" cols="45" rows="2" name="remarks"></textarea>
                    <input type="hidden" name="creat_type" value="0" style="width:150px">
                </td>
            </tr>
         <%--   <tr id="d_end_mechanism" style="display: none">
                <td><font color="red" style="margin-right:10px">*</font>调拨/退货终到位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input type="text" id="end_mechanism" name="end_mechanism">
                </td>
            </tr>
            <tr id="d_receipt" style="display: none">
                <td><font color="red" style="margin-right:10px">*</font>收货客户联系人：</td>
                <td class="td2">
                    <input type="text" id="receipt_contact" class="" name="receipt_contact">
                <td><font color="red" style="margin-right:10px">*</font>收货客户电话：</td>
                <td class="td2">
                    <input type="text" class="" id="receipt_tel"
                           name="receipt_tel">
                </td>
            </tr>--%>

        </table>
    </fieldset>
</form>

<form action="" id="signform">
    <input readonly="readonly" type="hidden" name="order_id" id="order_id">
    <input readonly="readonly" type="hidden" name="order_number"
           id="order_number">
    <input readonly="readonly" type="hidden"
           name="sign_userphone" id="sign_userphone">
    <fieldset>
        <table class="tableclass" style="display: none;" id="signtable">
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>签收人：</td>
                <td class="td2"><input class="" type="text" name="sign_user"
                                       id="sign_user" class="validate[required]"/>
                </td>
                <td><font color="red" style="margin-right:10px">*</font>签收人证件：</td>
                <td class="td2"><input type="text" editable="" class="validate[required]"
                                       name="sign_usernumber" id="sign_usernumber" /></td>
            </tr>
            <tr>
                <td style="color: blue;"><span style="color: red"></span><font
                        color="red" style="margin-right:10px">*</font>实际件数：
                </td>
                <td class="td2"><input type="text" editable=""
                                       name="actual_number" id="actual_number"
                                       class="validate[required]"/></td>

                <td style="color: blue;"><span style="color: red"></span><font
                        color="red" style="margin-right:10px">*</font>缺失件数：
                </td>
                <td class="td2"><input type="text" editable=""
                                       name="defect_number" id="defect_number"
                                       class="validate[required]"/></td>
            </tr>

            <tr>
                <td style="color: blue;">签收备注:</td>
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
                    </div> <!--   -->
                </td>
            </tr>
        </table>
    </fieldset>
</form>
<input readonly="readonly" type="hidden" name="creat_type" value="0"
       style="width:150px">

</body>
</html>
