<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>逆向签收信息管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">


    <jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
    <script type="text/javascript" src="./js/select2/select2.js"></script>
    <script type="text/javascript"
            src="./js/select2/select2_locale_zh-CN.js"></script>
    <script type="text/javascript" src="./js/function.js"></script>
    <%
        User user = (User) request.getSession().getAttribute(
                SessionInfo.SessionName);
    %>
</head>
<script type="text/javascript">
    var username = '<%=user.getUsername()%>';
    var dialog;
    var grid;
    $(function () {
        grid = $("#dg")
            .datagrid(
                {
                    url: 'reverse.do?method=getSignReverseOrderInfo',
                    border: false,
                    rownumbers: true,
                    fit: true,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    toolbar: '#tb',
                    columns: [[
                        {
                            field: 'ck',
                            checkbox: true
                        },
                        {
                            field: 'order_type_name',
                            title: '类型',
                            width: 80,
                            align: 'center'
                        }, {
                            field: 'shiping_order_num',
                            title: '货运编号',
                            width: 60,
                            align: 'center'
                        }, {
                            field: 'send_mechanism',
                            title: '受理机构',
                            width: 90,
                            align: 'center'
                        }, {
                            field: 'end_address',
                            title: '调拨/退货始发位置',
                            width: 100,
                            align: 'center'
                        },
                        {
                            field: 'end_mechanism',
                            title: '调拨/退货终到位置',
                            width: 120,
                            align: 'center'
                        },
                        {
                            field: 'shipping_order_statestr',
                            title: '运单状态'
                        }, {
                            field: 'send_time',
                            title: '起运时间',
                            width: 80,
                            align: 'center',
                            sortable: 'true'
                        },
                        {
                            field: 'sign_time',
                            title: '签收日期',
                            width: 120,
                            align: 'center'
                        },
                        /*{
                            field: 'imageUrl',
                            title: '签收图片',
                            width: 70,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (row.shipping_order_state < 4) {
                                    return "运单未签收或无图片";
                                } else {

                                    return "<a href='javascript:void(0)' style='color:red' title='点击查看签收图片' onclick=\"showimg('"
                                        + row.shiping_order_id
                                        + "')\">点击查看</a>";
                                }
                            }
                        }, */{
                            field : 'sign_user',
                            title : '签收人',
                            width : 70,
                            align : 'center'
                        }, {
                            field : 'sign_usernumber',
                            title : '签收人证件',
                            width : 80,
                            align : 'center'
                        }, {
                            field: 'sign_remarks',
                            title: '签收备注',
                            width: 70,
                            align: 'center'
                        }, {
                            field: 'goods_num',
                            title: '总件数',
                            width: 100,
                            align: 'center'
                        }, {
                            field: 'real_num',
                            title: '实际件数',
                            width: 100,
                            align: 'center'
                        }
                    ]],
                    pagination: true,//分页
                    pageSize: 10,
                    pageList: [5, 10, 15, 20],
                    toolbar: '#tb'
                });

        document.onkeydown = function (event) {
            e = event ? event : (window.event ? window.event : null);
            if (e.keyCode == 13) {
                //执行的方法
                searchCar_owner();
            }
        };

        //能更多条件
        var btnMoreSearch = $("#btnMoreSearch");
        btnMoreSearch.click(function () {
            if (btnMoreSearch.attr("state") == "close") {
                //主要代码 
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 92
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("收起条件");
                btnMoreSearch.attr("state", "open");
                $("#searchInfoId").css("display", "block");
                $("#custom_name").val("");
                $("#receipt").val("");
                $("#address").val("");
                $("#reverseOrder_state").combobox("setValue", "");
                $("#reverseOrder_state").combobox("setText", "全部");
                $("#cn").select2("val", "");
            } else {
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 60
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("更多条件");
                btnMoreSearch.attr("state", "close");
                $("#searchInfoId").css("display", "none");
                $("#custom_name").val("");
                $("#receipt").val("");
                $("#address").val("");
                $("#reverseOrder_state").combobox("setValue", "");
                $("#reverseOrder_state").combobox("setText", "全部");
                $("#cn").select2("val", "");
            }
        });
        $("#reverseOrder_state").combobox("setText", "全部");
        $('#cc3')
            .combotree(
                {
                    url: "depn.do?method=getTree&&id=",
                    width: 150,
                    onBeforeLoad: function (node, param) {

                        if (node == null) {
                            $('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
                        } else {

                            $('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id="
                                + node.id;//加载下层节点
                        }

                    }
                });
        /*
         select框
         */

        $("#cn").select2({ //自动完成
            placeholder: "请选择终到位置",
            minimumInputLength: 1,
            multiple: false,
            allowClear: true,
            query: function (query) {
                $.ajax({
                    type: "POST",
                    url: "shipOrder.do?method=getCustomName",
                    data: {
                        name: query.term
                    }, //传递你输入框中的值
                    success: function (msg) {
                        var msg = $.parseJSON(msg);
                        var data = {
                            results: []
                        };
                        $.each(msg, function (x, y) {
                            data.results.push({
                                id: y.id,
                                text: y.name
                            });
                        });
                        query.callback(data);
                    }
                });
            },
        }).on("select2-selecting", function (f) {
            $("#receipt").val(f.object.text);
        });
        //是否显示终到位置条件查询
        var flag = '<%=user.getFlag()%>';
        if (flag != 3 && flag != 4) {
            $("#receipt_address").removeAttr("style");
        }
    });
    //删除
    function deleteEver() {
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        for (var i = 0; i < array.length; i++) {
            checkarray.push(array[i].shiping_order_id);
        }
        if (array != "") {
            $.messager.confirm('确认', '您确定要删除选中的记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "shipOrder.do?method=deleteShipOrder",
                        type: "POST",
                        data: {
                            del: checkarray.join(",")
                        },
                        success: function (data) {
                            if (data.flag) {
                                parent.$.messager.alert("运单信息删除", "运单信息删除成功",
                                    'info', function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert("运单签收", "请选择一行信息", "info");
        }
    }

    function showimg(obj) {
        dialog = parent.jy.modalDialog({
            title: '签收图片详情',
            url: 'reverse.do?method=getSingReverseOrderImg&id=' + obj,
            width: 680,
            buttons: [{
                text: '',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.qs(dialog, grid,
                        parent.$);
                }
            }]
        });
    }
    /*
     修改查询
     */
    function modify() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length == 1) {
            for (var i = 0; i < row.length; i++) {
                var id = row[i].shiping_order_id;
            }
            dialog = parent.jy.modalDialog({
                title: '运单修改',
                url: 'reverse.do?method=getEditShipOrderPage&pid=' + id,
                width: 680,
                buttons: [{
                    text: '<input type="button" class="btncss" value="修改"/>',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow
                            .submitFormEdit(dialog, grid, parent.$);
                    }
                }]
            });
        } else {
            $.messager.alert("运单签收", "请选择一行信息", "info");
        }
    }
    //详情查询 签收
    function qianshou() {
        var row = $("#dg").datagrid('getSelections');
        var isok = 0;
        var id = 0;
        for (var i = 0; i < row.length; i++) {
            id = row[i].shiping_order_id;
            isok = row[i].shipping_order_state;
        }

        if (row.length == 1) {
            if(isok<2) return $.messager.alert("逆向物流运运单签收", '请先进行接货发运', 'info');
            if(row[0].order_type==1) return $.messager.alert("逆向物流运运单签收", '请选择退货信息', 'info');
            if (isok == 4 || isok == 5 || isok == 6) {
                dialog = parent.jy.modalDialog({
                    title: '逆向物流运单详情',
                    url: 'reverse.do?method=toEditSignReverseOrderInfo&id='
                    + id,
                    width: 680,
                    buttons: [{
                        text: '',
                        handler: function () {
                            dialog.find('iframe').get(0).contentWindow.qs(
                                dialog, grid, parent.$);
                        }
                    }]
                });
            } else {
                dialog = parent.jy
                    .modalDialog({
                        title: '逆向物流运单详情',
                        url: 'reverse.do?method=toEditSignReverseOrderInfo&id='
                        + id,
                        width: 680,
                        buttons: [{
                            text: '<input type="button" id="btncss"  class="btncss" value="签收"/>',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow
                                    .qs(dialog, grid, parent.$);
                            }
                        }]
                    });
            }
        } else {
            $.messager.alert("运单签收", '请选择一行信息', 'info');
        }
    }



    //查询
    function searchCar_owner() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        $("#dg").datagrid('load',
            {
                send_time: startDate,
                end_time: endDate,
                shiping_order_num: $.trim($("#ddId").val()),//订单号
                shipping_order_state:$.trim($("#shipping_order_state").combobox("getValue")),//订单号
                order_type : $.trim($("#order_type").combobox("getValue")),//订单类型
                send_mechanism:$.trim($("#send_mechanism").val()),//受理机构
                end_address:$.trim($("#end_address").val()),//调拨/退货始发位置
                end_mechanism: $.trim($("#end_mechanism").val())//调拨/退货终到位置
            });
    }

    //重置
    function reset() {
        $("#cn").select2("val", "");
        $("#startDate").val("");
        $("#endDate").val("");
        $("#ddId").val("");
        $("#custom_name").val("");


        $("#receipt").val("");
        $("#shipping_order_state").combobox("setValue", "");
        $("#shipping_order_state").combobox("setText", "全部");
    }

    //查看订单信息
    function searchMsg() {
        var row = $("#dg").datagrid('getSelections');

        if (row.length == 1) {
            var pid = row[0].shiping_order_id;
            var shiping_order_num = row[0].shiping_order_num;
            dialog = parent.jy.modalDialog({
                title: '逆向运单信息',
                url: 'reverse.do?method=getReverseOrderPage&pid=' + pid
                + '&shiping_order_num=' + shiping_order_num,
                width: 800,
                height: 450,
                buttons: [{
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow
                            .submitFormEdit(dialog, grid, parent.$);
                    }
                }]
            });
        } else {
            $.messager.alert("运单签收", '请选择一行信息', 'info');
        }
    }
    //导出签收信息
    function putoutfile() {
        var allRows2 = $("#dg").datagrid("getColumnFields");
        var row = $("#dg").datagrid("getSelections");
        var pagination = $('#dg').datagrid('getPager').data("pagination").options;	//分页
        var colName = [];
        var fieldName = [];
        var dataIds = [];
        for (i = 1; i < allRows2.length; i++) {
            var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
                colName.push(col.title);//把title列名到数组里去
                fieldName.push(col.field); //把field列属性名到数组里去
        }
        for (i = 0; i < row.length; i++) {
            dataIds.push(row[i].shiping_order_id);
        }
        $("#fieldName").val(fieldName.join(","));
        $("#headtitle").val(colName.join(","));
        $("#dataIds").val(dataIds.join(","));
        $("#total").val(pagination.total);
        var outPutForm = $("#outputform");
        outPutForm.submit();
    }

    function OrderImg(ids) {
        dialog = parent.jy.modalDialog({
            title: '上传图片',
            url: 'reverse.do?method=backImg&pid=' + ids,
            width: 680,
            height: 350,
            buttons: [{
                text: '<input type="button" class="btncss" value="保存"/>',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                }
            }]
            , onClose: function () {
                $.ajax({
                    type: "POST",
                    url: 'reverse.do?method=shhavepit',
                });
                dialog.dialog("destroy");
            }
        });
    }
</script>
<body class="easyui-layout">
<form action="reverse.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         collapsible="false">
        <lable>起运日期:</lable>
        <input id="startDate" name="start_date" class="Wdate"
               readonly="readonly" style="width:150px;height:22px; "
               onfocus="WdatePicker({skin:'twoer'})"/> -
        <input id="endDate" name="end_date" class="Wdate" readonly="readonly"
               style="width:150px;height:22px;" onfocus="WdatePicker({skin:'twoer'})"/>
        <lable class="divlabel">货运编号:</lable>
        <input type="text" class="search-text" id="ddId" name="num"
               style="width:150px">
        <lable class="divlabel">运单状态:</lable>
        <select id='shipping_order_state' class="easyui-combobox"
                name="shipping_order_state" style="width:150px;height:22px"
                data-options="panelHeight : 'auto',editable:false">
            <option value="" selected="selected">全部</option>
            <option value="0">未签收</option>
            <option value="4">已签收</option>
        </select>
        <a class="easyui-linkbutton" onclick="searchCar_owner()"
                data-options="iconCls:'icon-search'">查询</a>
        <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button"style="margin-top:16px;"
           href="javascript:void(0)" state="close">更多条件</a>
        <div id="searchInfoId" style="display: none">
            <label class="divlabel" style="">类型:</label>
            <select id='order_type' class="easyui-combobox"
                    name="order_type" style="width:150px;height:22px"
                    data-options="panelHeight : 'auto',editable:false">
                <option value="" selected="selected">全部</option>
                <option value="0">退货</option>
                <option value="1">调拨</option>
            </select>
            <lable >受理机构:</lable>
                <input type="text" class="search-text" id="send_mechanism" name="send_mechanism">
            <label class="divlabel" style="">调拨/退货始发位置:</label>
            <input type="text"
                   id="end_address" style="width:130px"
                   name="end_address">
            <label class="divlabel" style="">调拨/退货终到位置:</label>
            <input type="text"
                   id="end_mechanism" style="width:130px"
                   name="end_mechanism">
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>

    <div id="tb"></div>
    <input type="hidden" name="method" value="exportSignReverseOrder">
    <input type="hidden" id="fieldName" name="fieldName"> <input
        type="hidden" id="headtitle" name="headtitle"> <input
        type="hidden" id="dataIds" name="dataIds">
    <input type="hidden" id="total" name="total">
</form>
</body>
</html>