<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>已分配运单信息管理</title>
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


</head>
<%
    String driverId = (String) request.getAttribute("driverId");
%>
<script type="text/javascript">
    var dialog;
    var grid;
    var pid = '<%=driverId%>';

    $(function () {
        grid = $("#dg").datagrid({
            url: 'reverse.do?method=getTakingReverseOrderInfo&driverId=' + pid,
            border: false,
            rownumbers: true,
            fit: true,
            /* sortName:'transport_pay,check_time,shipping_order',
             sortOrder:'desc', */
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            multiSort: true,
            /* 	remoteSort:true, */
            columns: [[{
                field: 'ck',
                checkbox: true
            }, {
                field: 'order_type_name',
                title: '类型',
                width: 80,
                align: 'center'
            },  {
                field: 'shiping_order_num',
                title: '货运编号',
                width: 80,
                align: 'center'
            }, {
                field: 'send_mechanism',
                title: '受理机构',
                width: 120,
                align: 'center'
            }, {
                field: 'send_time',
                title: '起运时间',
                width: 80,
                align: 'center',
                sortable: 'true'
            }, {
                field: 'end_address',
                title: '调拨/退货始发位置',
                width: 140,
                align: 'center'
            }, {
                field: 'updatetime',
                title: '通知时间',
                width: 160,
                align: 'center'
            }, {
                field: 'time_limitation',
                title: '提货时效',
                width: 120,
                align: 'center'
            }, {
                field: 'custom_name',
                title: '发货客户名称',
                width: 150,
                align: 'center'

            }, {
                field: 'custom_contact',
                title: '发货联系人',
                width: 120,
                align: 'center'

            }, {
                field: 'custom_tel',
                title: '发货客户电话',
                width: 100,
                align: 'center'
            }, {
                field: 'goods_num',
                title: '总件数',
                width: 100,
                align: 'center'
            }, {
                field: 'remarks_order',
                title: '项目部备注',
                width: 100,
                align: 'center'
            }
            ]],

            pagination: true,//分页
            pageSize: 10,
            pageList: [5, 10, 15, 20],
            toolbar: '#tb'
        });
        //	回车查询
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
                    height: 110
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("收起条件");
                btnMoreSearch.attr("state", "open");
                $("#searchInfoId").css("display", "block");
                $("#end_address").val("");
                $("#custom_name").val("");
                $("#receipt_name").val("");
                $("#receipt_tel").val("");

            } else {
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 60
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("更多条件");
                btnMoreSearch.attr("state", "close");
                $("#searchInfoId").css("display", "none");
                $("#end_address").val("");
                $("#custom_name").val("");
                $("#receipt_name").val("");
                $("#receipt_tel").val("");
            }
        });
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
        $("#carid").select2({
            placeholder: "请输入车主电话", //默认文本框显示信息
            minimumInputLength: 1, //最小输入字符长度
            allowClear: true,
            multiple: false, //设置多项选择，false则只能单选
            maximumSelectionSize: 5, //最大选择个数
            query: function (query) {
                $.ajax({
                    type: "POST",
                    url: "shipOrder.do?method=getPhoneLength",
                    data: {
                        phones: query.term
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
            }
        });
    });

    //查询
    function searchCar_owner() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        $("#dg").datagrid('load', {
            send_time: startDate,
            end_time: endDate,
            end_address: $.trim($("#end_address").val()),//调拨/退货始发位置
            send_mechanism: $.trim($("#send_mechanism").val()),//受理机构
            custom_contact: $.trim($("#custom_contact").val()),//发货客户名称
            order_type : $.trim($("#order_type")
                .combobox("getValue"))//订单类型
        });

    }
    //删除
    function deleteShipOrder() {
        var checkarray = [];
        var num = 0;
        var array = $('#dg').datagrid('getSelections');
        if (array != "") {
            for (var i = 0; i < array.length; i++) {
                //checkarray.push(array[i].shiping_order_id);
                checkarray.push(array[i].shiping_order_id);

            }
            if (checkarray.length > 0) {
                $.messager
                    .confirm(
                        '确认',
                        '您选择的运单已经分配到对应的司机，您确定要取消这些分配吗？',
                        function (r) {
                            if (r) {
                                $
                                    .ajax({
                                        url: "reverse.do?method=cancelDistributionReverseOrder",
                                        type: "POST",
                                        data: {
                                            driverId: pid,
                                            orderIds: checkarray.join(",")
                                        },
                                        success: function (data) {
                                            if (data.flag) {
                                                parent.$.messager
                                                    .alert(
                                                        "运单信息删除",
                                                        "运单信息删除成功",
                                                        'info',
                                                        function (r) {
                                                            $(
                                                                '#dg')
                                                                .datagrid(
                                                                    'reload');
                                                        });
                                            }
                                        }
                                    });
                            }
                        });
            }
        } else {
            $.messager.alert("运单接单", "请选择一行信息", "info");
        }
    }
    function takingReverserInfo() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length!=1) $.messager.alert("逆向物流接单发运", "请选择一行信息", "info");
        var id = row[0].shiping_order_id;
        dialog = parent.jy.modalDialog({
            title: '逆向物流接单发运',
            url: 'reverse.do?method=toOrderShipment&id='+id,
            width: 680,
            buttons: [{
                text: '<input type="button" class="btncss" value="保存"/>',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }]
        });
    }

    //重置
    function reset() {
        $("#startDate").val("");
        $("#ddId").val("");
        $("#shipperorder_id").val("");
        $("#send_mechanism").val("");
        $("#end_address").val("");
        $("#custom_name").val("");
        $("#receipt_name").val("");
        $("#receipt_tel").val("");
        $("#end_mechanism").val("");
    }
</script>
<body class="easyui-layout">
<form action="shipOrder.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         height="60px" collapsible="false">
        <lable>通知时间:</lable>
        <input id="startDate" name="send_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; "
               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> -
        <input id="endDate" name="end_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px;"
               onfocus="WdatePicker({skin:'twoer'})"/>
        <lable style="" id="">调拨/退货始发位置:
            <input type="text" class="search-text" id="end_address" name="end_address">
        </lable>
        <lable id="receipt_address">受理机构:
            <input type="text" class="search-text" id="send_mechanism" name="send_mechanism">
        </lable>
        <a class="easyui-linkbutton" onclick="searchCar_owner()"
           data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
                                                          class="btn btn-sm btn-link" type="button"
                                                          style="margin-top:16px;"
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
            <label class="divlabel"
                   style="padding-left: 0px;">发货联系人:</label> <input type="text"
                                                                      id="custom_contact" style="width:130px"
                                                                      name="custom_contact">
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>
    <div id="tb">
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove',plain:true"
           onclick="deleteShipOrder()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-reset',plain:true" onclick="reset()">重置</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove',plain:true"
           onclick="takingReverserInfo()">接单发运</a>
    </div>
    <input type="hidden" name="method" value="outShipOrder"> <input
        type="hidden" id="fieldName" name="fieldName"> <input
        type="hidden" id="headtitle" name="headtitle">
</form>
<form action="shipOrder.do" target="_blank" id="printywm" method="post">
    <input type="hidden" name="method" value="getShipOrderPrintYwm">
    <input type="hidden" id="checkarray" name="checkarray">
</form>
</body>
</html>