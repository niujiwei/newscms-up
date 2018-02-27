<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML>
<html>
<head>


    <title>逆向物流添加运单信息</title>
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
    <script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>

</head>
<%
    User user = (User) request.getSession().getAttribute(SessionInfo.SessionName);
    String flg = (String) request.getAttribute("flg");
%>
<script type="text/javascript">
    var oneid = 0;
    var twoid = 0;
    var threeid = 0;
    var falg = '<%=user.getFlag()%>'
    $(function () {
        initData();
        showHidden();
    });


    function showHidden() {
        if(falg=='1'||falg=='2'){
            $("#tr_address").css("display","none");
            $("#tr_time_limitation").css("display","none");
            $("td[name=td_send_station]").css("display","none");
        }else if(falg=='3'||falg=='4'){
            $("td[name=tr_send_station]").css("display","none");

        }
    }

    function initData(){
        $("#mechanism").select2({  //自动完成
            placeholder: "请选择受理机构",
            minimumInputLength: 1,
            multiple: false,
            allowClear: true,
            query: function (query) {
                $.ajax({
                    type: "POST",
                    url: "shipOrder.do?method=getCustomName",
                    data: {name: query.term},   //传递你输入框中的值
                    success: function (msg) {
                        var msg = $.parseJSON(msg);
                        var data = {results: []};
                        $.each(msg, function (x, y) {
                            data.results.push({id: y.id, text: y.name, people: y.people, tel: y.tel});
                        });
                        query.callback(data);
                    }
                });
            }
        }).on("select2-selecting", function (f) {
            $("#send_mechanism").val(f.object.text);
        });

        $('#province').combobox({
            url: 'driver.do?method=getNewFinalPositionCounty',
            valueField: 'id',
            textField: 'text',
            width: 120,
            editable: false,
            onSelect: function (record) {
                oneid = record.citye_parent_id;
                twoid = 0;
                $('#city').combobox("clear");
                $('#county').combobox("clear");
                $('#city').combobox('reload', 'driver.do?method=getNewFinalpositionCity&&citye_parent_id=' + oneid);
                $('#county').combobox('reload', 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id=' + twoid);
            }
        });

        $('#city').combobox({
            url: 'driver.do?method=getNewFinalpositionCity&&citye_parent_id=' + oneid,
            valueField: 'id',
            width: 120,
            textField: 'text',
            editable: false,
            onSelect: function (record) {
                twoid = record.citye_parent_id;
                $('#county').combobox("clear");
                $('#county').combobox('reload', 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id=' + twoid);
            }
        });

        $('#county').combobox({
            url: 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id=' + twoid,
            valueField: 'id',
            width: 120,
            textField: 'text',
            editable: false

        });

        $('#end_province').combobox({
            url: 'driver.do?method=getNewFinalPositionCounty',
            valueField: 'id',
            textField: 'text',
            width: 120,
            editable: false,
            onSelect: function (record) {
                oneid = record.citye_parent_id;
                twoid = 0;
                $('#end_city').combobox("clear");
                $('#end_county').combobox("clear");
                $('#end_city').combobox('reload', 'driver.do?method=getNewFinalpositionCity&&citye_parent_id=' + oneid);
                $('#end_county').combobox('reload', 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id=' + twoid);
            }
        });

        $('#end_city').combobox({
            url: 'driver.do?method=getNewFinalpositionCity&&citye_parent_id=' + oneid,
            valueField: 'id',
            width: 120,
            textField: 'text',
            editable: false,
            onSelect: function (record) {
                twoid = record.citye_parent_id;
                $('#end_county').combobox("clear");
                $('#end_county').combobox('reload', 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id=' + twoid);
            }
        });

        $('#end_county').combobox({
            url: 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id=' + twoid,
            valueField: 'id',
            width: 120,
            textField: 'text',
            editable: false

        });

        $('#order_type').combobox({
            panelHeight: 'auto',
            editable: false,
            onChange: function (newValue, oldValue) {
                if (newValue == 1) {
                    $("#d_end_mechanism").css("display", "");
                    $("#d_receipt").css("display", "");
                    $("#d_receipt_address").css("display", "");
                    $("#d_receipt_tel").css("display", "");

                }
                if (newValue == 0) {
                    $("#d_end_mechanism").css("display", "none");
                    $("#d_receipt").css("display", "none");
                    $("#d_receipt_address").css("display", "none");
                    $("#d_receipt_tel").css("display", "none");
                }
            }
        });
    }

    var submitForm = function ($dialog, $grid, $pjq, $mainMenu) {
        if($("#send_mechanism").val()==="") return  $pjq.messager.alert('新增订单', '受理机构不可以为空', 'info');
        if($("#Regform").validationEngine('validate')){
            $pjq.messager.confirm('新增订单','确定要新增吗?',function(r){
                var poin = $("#province").combobox('getText');
                var city = $("#city").combobox('getText');
                var county = $("#county").combobox('getText');
                var end_province = $("#end_province").combobox('getText');
                var end_city = $("#end_city").combobox('getText');
                var end_county = $("#end_county").combobox('getText');
                $("#end_mechanism").val(end_province+end_city+end_county);
                $("#end_address").val(poin + city + county);

                $.ajax({
                    type: "POST",
                    url: 'reverse.do?method=addReverseOrderInfo',
                    data: $('#Regform').serialize(),
                    success: function (msg) {
                        if (msg.success) {
                            $pjq.messager.alert('新增订单', '订单添加成功', 'info');
                            $dialog.dialog('close');
                            $grid.datagrid('reload');
                        }
                    }
                });
            });
        }
    };

</script>
<body>
<form action="" method="post" id="Regform">
    <input type="hidden" name="shiping_order_id">
    <fieldset>
        <table class="tableclass">
            <tr>
                <th colspan="4">基本信息</th>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>货运编号:</td>
                <td colspan="1" class="td2">
                    <input type="text" class="" id="shiping_order_num" name="shiping_order_num">

                </td>
                <td><font color="red" style="margin-right:10px">*</font>逆向物流类型:</td>
                <td colspan="1" class="td2">
                    <select id='order_type' name="order_type"
                            style="width:150px;height:22px">
                        <option value="0" selected="selected">退货</option>
                        <option value="1">调拨</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td name="tr_send_station"><font color="red" style="margin-right:10px">*</font>受理机构:</td>
                <td name="tr_send_station" class="td2" style="height:40px">
                    <input type="text" id="mechanism" name="mechanism"  style="width:150px">
                    <input type="hidden" id="send_mechanism"  name="send_mechanism"></td>
                <td><font color="red" style="margin-right:10px">*</font>提货时效:</td>
                <td class="td2"  style="height:40px">
                    <input type="text" readonly onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="time_limitation"
                           name="time_limitation"></td>
            </tr>
            <tr id="tr_address">
                <td><font color="red" style="margin-right:10px">*</font>调拨/退货始发位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input id="province" name="driver_province">省
                    <input id="city" name="driver_city">市
                    <input id="county" name="driver_county">区
                    <input type="hidden" id="end_address" name="end_address">
                </td>
            </tr>
            <tr id="tr_time_limitation">
                <td><font color="red" style="margin-right:10px">*</font>起运时间:</td>
                <td class="td2" style="height:40px">
                    <input type="text" class="validate[required]"
                           id="send_time"
                           name="send_time" readonly="readonly"
                           onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})">
                </td>
                <td>出货订单号:</td>
                <td class="td2" style="height:40px">
                    <input type="text" class="validate[required]"
                           id="shiping_order_goid"
                           name="shiping_order_goid">
                </td>

            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>发货客户名称：</td>
                <td class="td2">
                    <input type="text" class="validate[required]" id="custom_name"
                           name="custom_name">
                </td>
                <td><font color="red" style="margin-right:10px">*</font>发货联系人：</td>
                <td class="td2">
                    <input type="text" class="validate[required]" id="custom_contact"
                           name="custom_contact">
                </td>

            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>发货客户电话：</td>
                <td class="td2">
                    <input type="text" id="custom_tel" class="validate[required]" name="custom_tel">
                </td>
                <td><font color="red" style="margin-right:10px">*</font>总件数：</td>
                <td class="td2">
                    <input type="text" class="validate[required,custom[integer],min[0]]"
                           placeholder="0" name="goods_num"/>
                </td>

            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>实际件数:</td>
                <td class="td2">
                    <input type="text" class="validate[custom[integer],min[0]]"
                           placeholder="0" name="real_num"/>
                </td>
                <td><font color="red" style="margin-right:10px">*</font>货物名称：</td>
                <td class="td2">
                    <input type="text" class=""
                           placeholder="0" name="goods_name"/>
                </td>

            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>总重量:</td>
                <td class="td2">
                    <input type="text" class="validate[custom[number],min[0]]"
                           placeholder="0" name="goods_weight"/>
                </td>
                <td><font color="red" style="margin-right:10px">*</font>总体积：</td>
                <td class="td2">
                    <input type="text" class="validate[custom[number],min[0]]"
                           placeholder="0" name="goods_vol"/>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" cols="45" rows="2" name="remarks"></textarea>
                    <input type="hidden" name="creat_type" value="0" style="width:150px">
                </td>
            </tr>
            <tr id="d_end_mechanism" style="display: none">
                <td><font color="red" style="margin-right:10px">*</font>调拨/退货终到位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input id="end_province" name="end_province">省
                    <input id="end_city" name="end_city">市
                    <input id="end_county" name="end_county">区
                    <input type="hidden" id="end_mechanism" name="end_mechanism">
                </td>
            </tr>
            <tr id="d_receipt" style="display: none">
                <td><font color="red" style="margin-right:10px">*</font>收货客户名称：</td>
                <td class="td2">
                    <input type="text" id="receipt_contact" class="" name="receipt_name">
                </td>
                <td><font color="red" style="margin-right:10px">*</font>收货联系人：</td>
                <td class="td2">
                    <input type="text" class=""
                          name="receipt_contact"/>
                </td>
            </tr>
            <tr id="d_receipt_tel" style="display: none">
                <td><font color="red" style="margin-right:10px">*</font>收货客户电话：</td>
                <td class="td2">
                    <input type="text" class="" id="receipt_tel"
                           name="receipt_tel">
                </td>
            </tr>
            <tr id="d_receipt_address" style="display: none">
                <td><font color="red" style="margin-right:10px">*</font>收货客户地址：</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" cols="45" rows="2" id="receipt_address"  name="receipt_address"></textarea>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
