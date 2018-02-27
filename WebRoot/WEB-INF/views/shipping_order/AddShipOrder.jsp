<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML>
<html>
<head>


    <title>添加运单信息</title>
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
    var userFlag = '<%=user.getFlag()%>';
    $(function () {
        var $target = $('input,textarea,select');
        $target.bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                var nxtIdx = $target.index(this) + 1;
                $target.eq(nxtIdx).focus();
            }
        });
        $("#Regform").validationEngine('attach', {
            promptPosition: 'topRight:-15,0'
        });

        $("#cn").select2({  //自动完成
            placeholder: "请选择发货客户名称",
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
            },
        }).on("select2-selecting", function (f) {
            $("#custom_name").val(f.object.text);
            $("#cnpeople").val(f.object.people);
            $("#cntel").val(f.object.tel);
            $("#send_mechanism").val(f.object.text);
        });
        $("#user").val('<%=user.getUsername()%>');
        $("#cid").val('<%=flg%>');
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

    });
    //   		function saveDriver(){
    //   			var myform=document.forms[0];
    //   			myform.action="driver.do?method=saveDriver";
    //   			myform.submit();
    //   		}
    var submitForm = function ($dialog, $grid, $pjq, $mainMenu) {
        var poin = $("#province").combobox('getText');
        var city = $("#city").combobox('getText');
        var county = $("#county").combobox('getText');

        $("#end_address").val(poin + city + county);
        var num = $.trim($("#num").val());
        var shipordernum = $.trim($("#shipingordernum").val());
        $.ajax({
            type: "POST",
            url: 'shipOrder.do?method=getNumber',
            data: {
                number: num,
                shipordernum: shipordernum
            },
            success: function (data) {
                if (data.shiping_order_num != null) {
                    $pjq.messager.alert('新增订单', '此订单号已经存在', 'info');
                } else {
                    if ($("#Regform").validationEngine('validate')) {
                        //可提交
                        $pjq.messager.confirm('新增订单', '确定要新增吗?', function (r) {
                            if (r) {
                                $.ajax({
                                    type: "POST",
                                    url: 'shipOrder.do?method=saveShipOrder',
                                    data: $('#Regform').serialize(),
                                    success: function (msg) {
                                        if (msg.flag) {
                                            $pjq.messager.alert('新增订单', '订单添加成功', 'info');
                                            $dialog.dialog('close');
                                            $grid.datagrid('reload');
                                        }
                                        ;
                                    }
                                });
                            }
                        });
                    } else {
                        $pjq.messager.alert('新增订单', '必填信息不可为空', 'info');
                    }
                }
            }
        });

    };

    /* document.onkeydown= function(e) { e=e||window.event;
     kc=e.keyCode||e.charCode;
     if ((kc == 83&&e.ctrlKey)) {
     alert('^s');
     }
     }
     */
</script>
<body>
<form action="" method="post" id="Regform">
    <input type="hidden" name="shiping_order_id">
    <input type="hidden" id="end_address" name="end_address">
    <fieldset>
        <table class="tableclass">
            <tr>
                <th colspan="4">基本信息</th>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>货运编号:</td>
                <td class="td2"><input type="text" class="validate[required]" id="num"
                                       name="shiping_order_num"></td>

                <td><font color="red" style="margin-right:10px">*</font>受理机构:</td>
                <td class="td2">
                    <input type="text" id="cn">
                    <input type="hidden" class="validate[required]" id="send_mechanism"
                           name="send_mechanism"></td>
                <!-- 新增字段 -->
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>出货订单号:</td>
                <td class="td2"><input type="text" class="validate[required]" id="shipingordernum"
                                       name="shiping_order_goid"></td>

                <td><font color="red" style="margin-right:10px">*</font>起运地:</td>
                <td class="td2"><input type="text" class="validate[required]" id=""
                                       name="send_station"></td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>收货客户地址:</td>
                <td class="td2"><input type="text" class="validate[required]" id="111"
                                       name="end_mechanism"></td>

                <td><font color="red" style="margin-right:10px">*</font>业务类型:</td>
                <td class="td2">
                    <select id='business_type' class="easyui-combobox"
                            name="business_type" style="width:150px;height:22px"
                            data-options="panelHeight : 'auto',editable:false">
                        <option value="0">同城</option>
                        <option value="1">城际</option>
                    </select>
                </td>
            </tr>

            <!-- ///////////////////////////////////////////// -->
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>起运时间：</td>
                <td class="td2"><input type="text" class="Wdate validate[required]" id=""
                                       name="send_time" readonly="readonly"
                                       onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"></td>


                <td><font color="red" style="margin-right:10px">*</font>月结编号：</td>
                <td class="td2"><input type="text" class="validate[required]" id=""
                                       name="shiping_yueid"></td>
            </tr>


            <tr>
                <td><font color="red" style="margin-right:10px">*</font>终到位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input id="province" name="driver_province">省
                    <input id="city" name="driver_city">市
                    <input id="county" name="driver_county">区
                </td>
            </tr>

            <!--  <tr>
               <td ><font color="red" style="margin-right:10px">*</font>终到机构：</td>
                <td  class="td2"><input type="text" class="validate[required]" id=""
                    name="end_mechanism"></td>
                <td ><font color="red" style="margin-right:10px">*</font>客户编号：</td>
                <td  class="td2"><input type="text" class="validate[required]" id=""
                                        name="custom_code"></td>
            </tr>-->
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>发货客户名称：</td>
                <td class="td2">

                    <input type="text" id="custom_name" name="custom_name">
                </td>
                <td><font color="red" style="margin-right:10px">*</font>发货联系人：</td>
                <td class="td2"><input type="text" class="validate[required]" id="cnpeople"
                                       name="custom_contact"></td>
            </tr>

            <tr>
                <td><font color="red" style="margin-right:10px">*</font>发货客户电话：</td>
                <td class="td2"><input type="text" class="validate[required]" id="cntel"
                                       name="custom_tel"></td>
                <td><font color="red" style="margin-right:10px">*</font>收货客户名称：</td>
                <td class="td2"><input type="text" class="validate[required]" id=""
                                       name="receipt"></td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>收货联系人：</td>
                <td class="td2"><input type="text" class="validate[required]" id=""
                                       name="receipt_name"></td>

                <td><font color="red" style="margin-right:10px">*</font>收货客户电话：</td>
                <td class="td2"><input type="text" class="validate[required]" id=""
                                       name="receipt_tel"></td>
            </tr>

            <tr>
                <td>货物名称：</td>
                <td class="td2"><input type="text" name="goods_name"/>
                </td>

                <td><font color="red" style="margin-right:10px">*</font>总件数：</td>
                <td class="td2"><input type="text" type="text" class="validate[custom[number],min[0]]" placeholder="0"
                                       name="goods_num"/></td>
            </tr>

            <tr>
                <td><font color="red" style="margin-right:10px">*</font>总重量(千克)：</td>
                <td class="td2"><input type="text" type="text" class="validate[,custom[number],min[0]]"
                                       placeholder="0.0000" name="goods_weight"/></td>

                <td><font color="red" style="margin-right:10px">*</font>总体积(立方米)：</td>
                <td class="td2"><input type="text" type="text"
                                       class="validate[custom[number],max[99999.9999],min[0.0000]]" placeholder="0.0000"
                                       name="goods_vol"/></td>

            </tr>
            <%--    <tr>
                    <td >上游订单:</td>
                    <td  class="td2"><input type="text" type="text"  style="width:150px" name="topordernumber" /></td>

                    <td >下游订单：</td>
                    <td  class="td2"><input type="text"  type="text"  style="width:150px" name="downordernumber" /></td>
                </tr>--%>
            <tr>
                <th colspan="4">计费标准</th>
            </tr>
            <tr>
                <td>配送费：</td>
                <td class="td2"><input id="deliver_fee" type="text"
                                       class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"
                                       placeholder="0.00" name="deliver_fee"/></td>
                <td>上楼费：</td>
                <td class="td2"><input id="upstairs_fee" type="text"
                                       class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"
                                       placeholder="0.00" name="upstairs_fee"/></td>
            </tr>
            <tr>
                <td>附加费：</td>
                <td class="td2"><input id="added_fee" type="text"
                                       class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"
                                       placeholder="0.00" name="added_fee"/></td>
                <td>其他费用：</td>
                <td class="td2"><input id="other_fee" type="text"
                                       class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]"
                                       placeholder="0.00" name="other_fee"/></td>
            </tr>
            <tr>
                <td>运费总额：</td>
                <td class="td2"><input placeholder="0.00"
                                       class="validate[custom[number],max[99999.99],min[0.00],custom[dd]]" type="text"
                                       editable="" name="transport_pay"/>
                </td>
                <td>代收货款：</td>
                <td class="td2">
                    <input type="text" class="validate[custom[number],max[99999.99],min[0.00],custom[dd]] "
                           placeholder="0.00" name="trade_agency" style="width:150px">

            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>回单份数：</td>
                <td class="td2"><input type="text" name="is_recept"
                                       class="validate[required,custom[number],max[99999.99],min[0.00],custom[dd]] "
                                       style="width:150px">
                </td>
                <%--   <td><font color="red" style="margin-right:10px">*</font>制单人:</td>
                   <td class="td2"><input type="text"  class="validate[required]" id=""
                                          name="shipping_order"></td>--%>
            </tr>

            <tr>
                <td>发货备注:</td>
                <td colspan="3" class="td2">
                    <textarea class="easyui-textarea" cols="45" rows="2" name="remarks"></textarea>
                    <input type="hidden" name="creat_type" value="0" style="width:150px">
                    <!-- <input   type="hidden"  id="user" editable="" name="shipping_order" /> -->
                    <input type="hidden" id="cid" editable="" name="custom_id"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
