<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<% User user = (User) request.getSession().getAttribute(SessionInfo.SessionName); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>逆向物流运单信息管理</title>
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


</head>
<script type="text/javascript">
    var dialog;
    var grid;
    var editval;
    $(function () {
        grid = $("#dg").datagrid({
            url: 'reverse.do?method=getReverseOrderInfo',
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
            }, {
                field: 'shiping_order_num',
                title: '货运编号',
                width: 120,
                align: 'center'
            }, {
                field: 'send_mechanism',
                title: '受理机构',
                width: 120,
                align: 'center'
            }, {
                field: 'shiping_order_goid',
                title: '出货订单号',
                width: 120,
                align: 'center',
                sortable: 'true'
            }, {
                field: 'send_time',
                title: '起运时间',
                width: 80,
                align: 'center',
                sortable: 'true'
            }, {
                field: 'send_station',
                title: '起运地',
                width: 140,
                align: 'center',
                sortable: 'true'
            }, {
                field: 'end_address',
                title: '调拨/退货始发位置',
                width: 140,
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
                width: 150,
                align: 'center'

            }, {
                field: 'custom_tel',
                title: '发货客户电话',
                width: 120,
                align: 'center'
            }, {
                field: 'goods_name',
                title: '货物名称',
                width: 120,
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
            }, {
                field: 'goods_weight',
                title: '总重量',
                width: 100,
                align: 'center'

            }, {
                field: 'goods_vol',
                title: '总体积',
                width: 100,
                align: 'center'

            }, {
                field: 'end_mechanism',
                title: '调拨/退货终到位置',
                width: 100,
                align: 'center'

            }, {
                field: 'receipt_name',
                title: '收货客户名称',
                width: 100,
                align: 'center'
            }, {
                field: 'receipt_address',
                title: '收货客户地址',
                width: 100,
                align: 'center'
            }, {
                field: 'receipt_contact',
                title: '收货联系人',
                width: 100,
                align: 'center'
            }, {
                field: 'receipt_tel',
                title: '收货客户电话',
                width: 100,
                align: 'center'
            }, {
                field: 'remarks_order',
                title: '项目部备注',
                width: 100,
                align: 'center'
            }, {
                field: 'remarks',
                title: '司机供应商备注',
                width: 100,
                align: 'center'
            }, {
                field: 'updatetime',
                title: '通知时间',
                width: 160,
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
                $("#end_mechanism").val("");
                $("#custom_name").select2("val", "");
            } else {
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 60
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("更多条件");
                btnMoreSearch.attr("state", "close");
                $("#searchInfoId").css("display", "none");
                $("#end_mechanism").val("");
                $("#custom_name").select2("val", "");
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
        /*
         select框
         */

        /*  $("#cn").select2({ //自动完成
         placeholder: "请选择受理机构",
         minimumInputLength: 1,
         multiple: false,
         allowClear: true,
         query: function (query) {
         $("#send_mechanism").val("");
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
         }
         }).on("select2-selecting", function (f) {
         $("#send_mechanism").val(f.object.text);
         });*/
    });

    //查询
    function searchCar_owner() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        $("#dg").datagrid('load', {
            send_time: startDate,//开始时间
            end_time: endDate,//结束时间
            shiping_order_num: $.trim($("#ddId").val()),//订单号
            end_address: $.trim($("#end_address").val()),//调拨/退货始发位置
            end_mechanism: $.trim($("#end_mechanism").val()),//调拨/退货终到位置
            custom_contact: $.trim($("#custom_contact").val()),//发货客户名称
            order_type: $.trim($("#order_type")
                .combobox("getValue")),//订单类型
            receipt_contact: $.trim($("#receipt_contact").val()),//收货客户名称
            send_mechanism: $.trim($("#send_mechanism").val())//受理机构

        });

    }
    //删除
    function deleteEver() {
        var checkarray = [];
        var num = 0;
        var array = $('#dg').datagrid('getSelections');
        if (array != "") {
            for (var i = 0; i < array.length; i++) {
                if (array[i].shipping_order_state == 0) {
                    checkarray.push(array[i].shiping_order_id);
                }
            }
            if (checkarray.length == 0) return $.messager.alert("运单信息删除", "请选择未操作的运单信息", 'info');
            $.messager
                .confirm(
                    '确认',
                    '您确定要删除选中的记录吗？',
                    function (r) {
                        if (r) {
                            $
                                .ajax({
                                    url: "reverse.do?method=deleteReverseOrderInfo",
                                    type: "POST",
                                    data: {
                                        ids: checkarray
                                            .join(",")
                                    },
                                    success: function (data) {
                                        if (data.success) {
                                            parent.$.messager.alert("运单信息删除", "运单信息删除成功", 'info', function (r) {
                                                $('#dg').datagrid('reload');
                                            });
                                        }
                                    }
                                });
                        }
                    });

        }
    }

    /*
     修改查询
     */
    function modify() {

        var row = $("#dg").datagrid('getSelections');
        //if (row.length == 1 && row[0].shipping_order_state > 0) {
        if (row.length == 1) {
            for (var i = 0; i < row.length; i++) {
                if(row[i].shipping_order_state!=0) return $.messager.alert("运单录入", "请选择一行未操作的信息", "info");
                var id = row[i].shiping_order_id;
                dialog = parent.jy
                    .modalDialog({
                        title: '逆向物流运单修改',
                        url: 'reverse.do?method=toEditReverseOrder&id='
                        + id,
                        width: 700,
                        height: 490,
                        buttons: [{
                            text: '<input type="button" class="btncss" value="修改"/>',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow
                                    .submitFormEdit(dialog, grid,
                                        parent.$);
                            }
                        }]
                    });
            }
        } else {
            $.messager.alert("运单录入", "请选择一行信息", "info");
        }
        //}
    }


    //逆向物流新增
    function add() {
        dialog = parent.jy.modalDialog({
            title: '逆向物流新增',
            url: 'reverse.do?method=toAddReverseOrderInfo',
            /* 				 buttons :'#addbuttons'
             */
            width: 700,
            height: 490,
            buttons: [{
                text: '<input type="button" class="btncss" value="保存"/>',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }]
        });
    }

    //导入
    function putintfile() {
        dialog = parent.jy.modalDialog({
            title: '导入逆向运单',
            url: 'reverse.do?method=toImportReverserOrderInfo',
            width: 600,
            height: 300,
            buttons: [{
                text: '<input type="button" value="导入" class="btncss">',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }],
            onDestroy: function () {
                $("#dg").datagrid('reload');
            }
        });
    }
    //重置
    function reset() {
        $("#startDate").val("");
        $("#ddId").val("");
        $("#send_mechanism").val("");
        // $("#cn").select2("val", "");
        $("#custom_name").val("");
        $("#endDate").val("");
        $("#end_address").val("");
        $("#order_type").combobox("setValue", "");
        $("#order_type").combobox("setText", "全部");
        $("#send_mechanism").val("");
    }
    //所有信息导出
    function putoutfile() {
        var pagination = $('#dg').datagrid('getPager').data("pagination").options;	//分页
        var allRows2 = $("#dg").datagrid("getColumnFields");
        var row = $("#dg").datagrid("getSelections");
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
    //查看订单
    function searchMsg() {
        var row = $("#dg").datagrid("getSelections");
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
            $.messager.alert("运单录入", "请选择一行信息", "info");
        }
    }

</script>
<body class="easyui-layout">
<form action="reverse.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         height="60px" collapsible="false">
        <lable>起运日期:</lable>
        <input id="startDate" name="send_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; "
               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> -
        <input id="endDate" name="end_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px;"
               onfocus="WdatePicker({skin:'twoer'})"/>
        <lable class="divlabel">货运编号:</lable>
        <input type="text" class="search-text" id="ddId" style="width:120px" name="shiping_order_num">
        <lable id="receipt_address">受理机构:</lable>
        <%--
                    <input type="text" class="search-text" id="cn" name="cn">
        --%>
        <input type="text" class="search-text" id="send_mechanism" name="send_mechanism">
        <a class="easyui-linkbutton" onclick="searchCar_owner()"
           data-options="iconCls:'icon-search'">查询</a>
        <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
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
            <label class="divlabel" style="">调拨/退货始发位置:</label>
            <input type="text"
                   id="end_address" style="width:130px"
                   name="end_address">
            <label class="divlabel" style="">发货客户名称:</label>
            <input type="text"
                   id="custom_contact" style="width:130px"
                   name="custom_contact">
            <label class="divlabel" style="">收货客户名称:</label>
            <input type="text"
                   id="receipt_contact" style="width:130px"
                   name="receipt_contact">
            <div id="" style="padding:inherit">
                <label class="divlabel" style="">调拨/退货终到位置:</label>
                <input type="text"
                       id="end_mechanism" style="width:130px"
                       name="end_mechanism">
            </div>
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>
    <div id="tb"></div>
    <input type="hidden" name="method" value="exportReverseOrder"
           id="method">
    <input type="hidden" id="fieldName" name="fieldName">
    <input type="hidden" id="headtitle" name="headtitle">
    <input type="hidden" id="dataIds" name="dataIds">
    <input type="hidden" id="total" name="total">
</form>
<form action="reverse.do" target="_blank" id="printywm" method="post">
    <input type="hidden" name="method" value="getShipOrderPrintYwm">
    <input type="hidden" id="checkarray" name="checkarray">
</form>
</body>
</html>