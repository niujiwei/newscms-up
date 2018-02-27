<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>
    <script type="text/javascript" src="js/json2.js"></script>
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
    <script type="text/javascript" src="./js/function.js"></script>
    <link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
    <script type="text/javascript" src="./js/select2/select2.js"></script>
    <script type="text/javascript"
            src="./js/select2/select2_locale_zh-CN.js"></script>
    <script type="text/javascript" src="./js/select2/select2_expand.js"></script>
</head>
<%String orders = (String) request.getAttribute("order"); %>
<script type="text/javascript">
    var dialog;
    var grid;
    var orders = '<%=orders%>';
    $(function () {
        //能更多条件
        var btnMoreSearch = $("#btnMoreSearch");
        btnMoreSearch.click(function () {
            if (btnMoreSearch.attr("state") == "close") {
                //主要代码 
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 80
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("收起条件");
                btnMoreSearch.attr("state", "open");
                $("#searchInfoId").css("display", "block");
                $("#abnormal_result").combobox('setValue', "");
                $("#abnormal_type").combobox('setValue', "");
                $("#end_address").val("");
                $("#receipt_name").val("");
            } else {
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 60
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("更多条件");
                btnMoreSearch.attr("state", "close");
                $("#searchInfoId").css("display", "none");
                $("#abnormal_result").combobox('setValue', "");
                $("#abnormal_type").combobox('setValue', "");
                $("#end_address").val("");
                $("#receipt_name").val("");
            }
        });

        //键盘回车事件
        document.onkeydown = function (event) {
            e = event ? event : (window.event ? window.event : null);
            if (e.keyCode == 13) {
                //执行的方法
                searchUser();
            }
        };
        grid = $("#dg")
            .datagrid(
                {
                    url: 'waybillAdjustment.do?method=getWaybillAdjustmentListInfo',
                    fit: true,
                    rownumbers: true,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    toolbar: '#tb',
                    rowStyler: function (index, row) {
                        // alert(row.clearing_state);
                        if (row.applicant_state == 0) {
                            //alert(row.clearing_state);
                            return 'color:blue;';

                        }
                    },
                    columns: [[
                        {
                            field: 'ck',
                            checkbox: true
                        },
                        {
                            field: 'shippingOrder.send_mechanism',
                            title: '受理机构',
                            align: 'center',
                            width: 120,
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.send_mechanism;
                                } else {
                                    return '';
                                }

                            }
                        },
                        {
                            field: 'shippingOrder.end_address',
                            title: '终到位置',
                            align: 'center',
                            width: 120,
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.end_address;
                                } else {
                                    return '';
                                }

                            }
                        },
                        {
                            field: 'shippingOrder.shiping_order_num',
                            title: '货运编号',
                            align: 'center',
                            width: 120,
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.shiping_order_num;
                                } else {
                                    return '';
                                }

                            }
                        },
                        {
                            field: 'shippingOrder.shiping_order_goid',
                            title: '出货订单号',
                            align: 'center',
                            width: 120,
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.shiping_order_goid;
                                } else {
                                    return '';
                                }
                            }
                        },{
                            field : 'remarks',
                            title : '发货备注',
                            width : 100,
                            align : 'center'
                        },
                        {
                            field: 'applicant_name',
                            title: '申请人姓名',
                            align: 'center',
                            width: 120,
                        },
                        {
                            field: 'applicant_time',
                            title: '申请时间',
                            width: 120,
                            align: 'center',
                        },
                        {
                            field: 'applicant_message',
                            title: '申请原因',
                            align: 'center',
                            width: 120,
                        },
                        {
                            field: 'applicant_type',
                            title: '申请类型',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val == "0") {
                                    return "运单调整";
                                } else if (val == "1") {
                                    return "取消签收";
                                }
                            }
                        },
                        {
                            field: 'applicant_state',
                            title: '审核状态',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val =="0") {
                                    return "未审核"
                                }else if(val =="1"){
                                    return "已审核"
                                }
                            }
                        },
                        {
                            field: 'auditor_name',
                            title: '审核人姓名',
                            width: 120,
                            align: 'center',
                        },
                        {
                            field: 'auditor_time',
                            title: '审核时间',
                            width: 120,
                            align: 'center',
                        },
                        {
                            field: 'auditor_message',
                            title: '审核意见',
                            width: 120,
                            align: 'center'
                        }
                    ]],
                    pagination: true,
                    pageSize: 10,
                    pageList: [5, 10, 15, 20]
                });

       // dropOrder();
    });
    //重置
    function reset() {
        $("#start_time").val("");
        $("#end_time").val("");
        $("#shiping_order_num").val("");
        $("#shiping_order_goid").val("");
        $("#applicant_state").combobox('setValue', "");
        $("#applicant_type").combobox('setValue', "");
        $("#send_mechanism").val("");
        $("#end_address").val("");
    }


    //查询
    function searchUser() {
        var endTime;
        if($.trim($("#end_time").val())!="")
            endTime  = new Date($("#end_time").val()).add(1).day().toString("yyyy-MM-dd");
        $("#dg")
            .datagrid(
                'load',
                {
                    start_time: $.trim($("#start_time").val()),//上报时间
                    end_time: endTime,//上报时间
                    shiping_order_num: $.trim($("#shiping_order_num")
                        .val()),//货运编号
                    shiping_order_goid: $
                        .trim($("#shiping_order_goid").val()),//出货订单号
                    applicant_state: $.trim($("#applicant_state")
                        .combobox('getValue')),//是否处理
                    applicant_type: $.trim($("#applicant_type")
                        .combobox('getValue')),//上报类型
                    send_mechanism: $.trim($("#send_mechanism").val()),//受理机构
                    end_address: $.trim($("#end_address").val())//终到位置
                });
    }

    //异常反馈
    function waybillAdjustment() {
        dialog = parent.jy.modalDialog({
            title: '运单调整运单信息',
            url: 'waybillAdjustment.do?method=waybillAdjustmentSearchOrder',
            resizable: true,
            height: 600,
            width: 1200,
            buttons: [{
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }],
            onDestroy: function () {
                $('#dg').datagrid('reload');
            }
        });

    }
    function cancelSign() {
        dialog = parent.jy.modalDialog({
            title: '运单取消签收信息',
            url: 'waybillAdjustment.do?method=toCancelSignOrderInfo',
            resizable: true,
            height: 600,
            width: 1200,
            buttons: [{
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }],
            onDestroy: function () {
                $('#dg').datagrid('reload');
            }
        });
    }

    function waybillAudit() {
        var row = $("#dg").datagrid("getSelections");
        if (row.length != 1)  return $.messager.alert("运单调整", "请选择一行信息", "info");
        var id = row[0].id;
        if (row[0].applicant_state == 1)  return $.messager.alert("运单调整", "请选择未审核信息", "info");
        dialog = parent.jy
            .modalDialog({
                title: '运单调整审核',
                url: 'waybillAdjustment.do?method=toWaybillAuditInfo&id='
                + id,
                width: 750,
                height: 600,
                buttons: [{
                    text: '<input type="button" class="btncss" value="审核"/>',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow
                            .submitHandling(dialog, grid,
                                parent.$);
                    }
                }]
            });
    }
    function searchMsg(){
        var row = $("#dg").datagrid("getSelections");
        if (row.length != 1)  return $.messager.alert("运单调整", "请选择一行信息", "info");
        var id = row[0].id;
        dialog = parent.jy
            .modalDialog({
                title: '运单调整查看',
                url: 'waybillAdjustment.do?method=toLookWaybillAuditInfo&id='
                + id,
                width: 750,
                height: 600,
            });
    }

</script>
<body class="easyui-layout">
<form action="abnormalreport.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         data-options="collapsible:false" height="60px">
        <label class="divlabel">申请时间：</label>
        <input id="start_time" name="start_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; "onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> -
        <input id="end_time" name="end_time" class="Wdate" readonly="readonly"
                style="width:150px;height:22px;"
                onfocus="WdatePicker({skin:'twoer'})"/>
        <label class="divlabel">货运编号：</label>
        <input id="shiping_order_num" type="text" name="shiping_order_num"
               class="search-text"> <label class="divlabel">出货订单号：</label>
        <input id="shiping_order_goid" type="text" name="shiping_order_goid"
               class="search-text">
        <a class="easyui-linkbutton"onclick="searchUser()"
           data-options="iconCls:'icon-search'"id="searchId">查询</a>
        <a id="btnMoreSearch"class="btn btn-sm btn-link" type="button"
           style="margin-top:16px;" href="javascript:void(0)" state="close">更多条件</a>
        <div id="searchInfoId" style="display: none">
            <label class="divlabel">是否处理：</label>
            <select id="applicant_state"class="easyui-combobox" name="applicant_state"
                    data-options="panelHeight : 'auto',editable:false" style="width:150px;">
            <option value="" selected="selected">全部</option>
            <option value="1">是</option>
            <option value="0">否</option>
            </select>
            <label class="divlabel">调整类型：</label>
            <select id="applicant_type"class="easyui-combobox" name="applicant_type"
                    data-options="panelHeight : 'auto',editable:false" style="width:150px;">
                <option value="" selected="selected">全部</option>
                <option value="0">运单调整</option>
                <option value="1">取消签收</option>
            </select>
            <label class="divlabel">受理机构</label>
            <input id="send_mechanism" type="text" name="send_mechanism" class="search-text">
            <label class="divlabel">终到位置：</label>
            <input id="end_address" type="text" name="end_address" class="search-text">
            <%--<label class="divlabel">到货联系人：</label>
            <input id="receipt_name"type="text" name="receipt_name" class="search-text">--%>

        </div>
    </div>
    <input type="hidden" name="abnormal_ids" id="abnormal_ids"> <input
        type="hidden" name="method" value="outPutAbnormalFile"> <input
        type="hidden" id="fieldName" name="fieldName"> <input
        type="hidden" id="headtitle" name="headtitle">
    <div title="运单调整信息展示" region="center">
        <table id="dg"></table>
    </div>
    <div id="tb">
    <%--  <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-add',plain:true"
           onclick="waybillAdjustment()">运单调整</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove',plain:true"
           onclick="cancelSign()">取消签收</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="waybillAudit()">运单审核</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="lookMessage()">运单审核</a>--%>


    </div>
</form>
</body>
</html>
