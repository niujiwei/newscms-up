<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>司机信息添加</title>
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

    User user = (User) request.getSession().getAttribute(
            SessionInfo.SessionName);
%>
<script type="text/javascript">
    var oneid = 0;
    var twoid = 0;
    var threeid =0;
    var userFlag = '<%=user.getFlag()%>';
    $(function(){
        $("#Regform").validationEngine('attach', {
            promptPosition:'topRight:-15,0'
        });
        $("#carid").select2({
            placeholder: "请输入供应商",	//默认文本框显示信息
            minimumInputLength: 1,	//最小输入字符长度
            allowClear:true,
            multiple: false,				//设置多项选择，false则只能单选
            //  maximumSelectionSize: 5, //最大选择个数
            query: function (query) {
                $.ajax({
                    type: "POST",
                    url: "driver.do?method=getSuppliers",
                    data: {num:query.term},   //传递你输入框中的值
                    success: function(msg){
                        var msg=$.parseJSON(msg);
                        var data = {results: []};
                        $.each(msg,function(x,y){
                            data.results.push({id: y.id, text: y.name});;
                        });
                        query.callback(data);
                    }
                });
            }
        });
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
                $('#county').combotree("clear");
                $('#city').combobox('reload','driver.do?method=getNewFinalpositionCity&&citye_parent_id='+oneid);
                $('#county').combotree('reload','driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+ twoid);
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
                $('#county').combotree("clear");
                $('#county').combotree('reload','driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid);
            }
        });
        /* $('#county').combobox({
         url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid,
         valueField : 'id',
         width:120,
         textField : 'text',
         editable:false

         });*/
        $('#county').combotree({
            multiple : true,
            url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+ twoid,

        });
        $('#carType').combobox({
            url:'suppliers.do?method=carType',
            valueField:'id',
            textField:'text'
        });
        if (userFlag==2){
            $("#carid").select2(
                'data',{
                    id:'<%=user.getSuppliers_id()%>',
                    text:'<%=user.getRealName()%>'
                });
            $("#carid").prop("disabled", true);
        }

    });
    var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
        var poin = $("#province").combobox('getText');
        var city = $("#city").combobox('getText');
        var county = $("#county").combotree('getText');

        var cartype=$("#carType").combobox('getText');
        $("#driver_cartype").val(cartype);
        $("#driver_address").val(poin+city+county);
        if($("#carid").val()==""){
            $pjq.messager.alert('新增信息', '供应商信息未填', 'info');
        }else if(poin==""){
            $pjq.messager.alert('新增信息', '终到位置信息未填', 'info');
        }else{
            plateNumber=$.trim($("#carid").select2("val"));
            //var province1 =$("province").combobox("getText");
            //alert(province1);
            if($("#Regform").validationEngine('validate')){

                $pjq.messager.confirm('新增信息','确定要新增吗?',function(r){
                    if (r){
                        $.ajax({
                            type: "POST",
                            url:'driver.do?method=saveDriver',
                            data:$('#Regform').serialize(),
                            success:function(msg){
                                if(msg.flag){
                                    $pjq.messager.alert('新增信息','添加成功','info');
                                    $dialog.dialog('close');
                                    $grid.datagrid('reload');
                                }else{
                                    $pjq.messager.alert('新增失败','添加失败：姓名和车牌重复','info');
                                }
                            }
                        });
                    }else{

                    }

                });
            }else{

            };
        }

    };
</script>
<body style="padding: 0px;margin:0px;">
<form action="" method="post" id="Regform">
    <input type="hidden" id="driver_address" name="driver_address" >
    <input type="hidden" name="driverId">
    <input type="hidden"
           id="driver_cartype" name="driver_cartype">
    <fieldset>
        <table class="tableclass">
            <tr>
                <th colspan="4">基本信息</th>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>终到位置:</td>
                <td class="td2" colspan="3" style="height:40px">
                    <input id="province"  name="driver_province">省
                    <input  id="city"  name="driver_city">市
                    <input  id="county" name="driver_countys">区
                </td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>姓名:</td>
                <td class="td2"><input type="text" class="validate[required]" id=""
                                       name="driver_name"></td>
                <td>供应商:</td>
                <td class="td2">
                    <input type="hidden"  id="carid" name="driver_suppliers"style="width:150px">
                </td>
            <tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>性别:</td>
                <td class="td2">
                    <select id="driver_sex" data-options="width:'120',panelHeight:50,editable:false," style=""  class="easyui-combobox" name="driver_sex">
                        <option value="1">男</option>
                        <option value="0">女</option>
                    </select>
                </td>
                <td>年龄:</td>
                <td class="td2">
                    <input type="text" class="validate[custom[number],max[200]]" id="driver_age" name="driver_age" value="0">
                </td>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>联系方式:</td>
                <td class="td2"><input type="text" class="validate[required]" id="driver_phone"
                                       name="driver_phone"></td>
                <td><font color="red" style="margin-right:10px">*</font>身份证:</td>
                <td class="td2"><input type="text" class="validate[required]" id="driver_cardnumber"
                                       name="driver_cardnumber" /></td>
            </tr>
            <tr>
                <td><font color="red" style="margin-right:10px"></font>手机品牌:</td>
                <td class="td2"><input type="text" class=""
                                       id="driver_phonebrand" name="driver_phonebrand"></td>
                <td><font color="red" style="margin-right:10px"></font>手机型号:</td>
                <td class="td2"><input type="text" class=""
                                       id="driver_phonemodel" name="driver_phonemodel"></td>
            </tr>
            <tr>
                <td>车辆类型:</td>
                <td    class="td2">
                    <input id="carType" type="text"></td>
                <td><font color="red" style="margin-right:10px"></font>车牌号:</td>
                <td class="td2"><input type="text" class="validate[minSize[7],maxSize[7],custom[carnumber]]"
                                       id="" name="driver_carnumber"></td>
            </tr>
            <tr>

            <tr>
            <tr>
                <td><font color="red" style="margin-right:10px">*</font>备注:</td>
                <td colspan="3" class="td2">
					<textarea class="easyui-textarea " id="driver_remarks"
                              cols="45" rows="3" name="driver_remarks"></textarea>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
