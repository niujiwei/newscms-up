<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<% User user = (User) request.getSession().getAttribute(SessionInfo.SessionName);
    String orders = (String) request.getAttribute("order");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>运单信息管理</title>
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
    <script type="text/javascript" src="./js/dateUtils.js"></script>


</head>
<script type="text/javascript">
    var dialog;
    var grid;
    var editval;
    var flag = '<%=user.getFlag()%>';
    var orders = '<%=orders%>';
    var hidden;
    if (flag == 0 || flag == 3 || flag == 4) {
        hidden = false;
    } else {
        hidden = true;
    }

    $(function () {
        var data, type, type_text, dayormonth, startDate, endDate;
        if (orders != "null" || orders != "undefined") {
            data = orders.split(",");
            dayormonth = data[0];
            startDate = new Date();
            endDate = new Date();
            if (dayormonth == "day") {
                endDate = endDate.Format("yyyy-MM-dd");
                startDate = startDate.Format("yyyy-MM-dd");
            } else if (dayormonth = "month") {
                startDate.setDate(1);
                startDate = startDate.Format("yyyy-MM-dd");
                endDate.setMonth(endDate.getMonth() + 1);
                endDate.setDate(0);
                endDate = endDate.Format("yyyy-MM-dd");
            }
        }


        grid = $("#dg").datagrid({
            url: 'shipOrder.do?method=getShipOrder',
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
                field: 'shiping_order_num',
                title: '货运编号',
                width: 60,
                align: 'center'
            }, {
                field: 'send_mechanism',
                title: '受理机构',
                width: 120,
                align: 'center'
            }, {
                field: 'shiping_order_goid',
                title: '出货订单号',
                width: 80,
                align: 'center'
            }, {
                field: 'send_station',
                title: '起运地',
                width: 120,
                align: 'center'
            }, {
                field: 'send_time',
                title: '起运时间',
                width: 80,
                align: 'center',
                sortable: 'true'
            },
                {
                    field: 'end_address',
                    title: '终到位置',
                    width: 160,
                    align: 'center'
                }, {
                    field: 'end_mechanism',
                    title: '收货客户地址',
                    width: 160,
                    align: 'center'
                }, {
                    field: 'shiping_yueid',
                    title: '月结编号',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'custom_name',
                    title: '发货客户名称',
                    width: 140,
                    align: 'center'
                }, {
                    field: 'custom_contact',
                    title: '发货联系人',
                    width: 110,
                    align: 'center'
                }, {
                    field: 'custom_tel',
                    title: '发货客户电话',
                    width: 120,
                    align: 'center'
                }, {
                    field: 'receipt',
                    title: '收货客户名称',
                    width: 110,
                    align: 'center'
                }, {

                    field: 'receipt_name',
                    title: '收货联系人',
                    width: 110,
                    align: 'center'
                }, {
                    field: 'receipt_tel',
                    title: '收货客户电话',
                    width: 120,
                    align: 'center'
                }, {
                    field: 'goods_name',
                    title: '货物名称',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'is_recept',
                    title: '回单份数',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'trade_agency',
                    title: '代收货款',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'goods_num',
                    title: '总件数',
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
                },
                {
                    field: 'deliver_fee',
                    title: '配送费',
                    width: 100,
                    align: 'center',
                    hidden: hidden
                }, {
                    field: 'upstairs_fee',
                    title: '上楼费',
                    width: 100,
                    align: 'center',
                    hidden: hidden
                }, {
                    field: 'added_fee',
                    title: '附加费',
                    width: 100,
                    align: 'center',
                    hidden: hidden,
                },/*, {
                    field: 'updatetime',
                    title: '货运单票导入时间',
                    width: 140,
                    align: 'center',
                    formatter: function (value, row, index) {
                        if (value != undefined) {
                            value = value.substr(0, 19);
                        }
                        return value;
                    }
                },*/
                  {
                 field : 'remarks',
                 title : '发货备注',
                 width : 150,
                 align : 'center'
                 }

            ]],
            queryParams: {
                //shipping_order_state : type,
                send_time: startDate,
                end_time: endDate,
            },

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
                $("#topordernumber").val("");
                $("#downordernumber").val("");
                $("#receipt_name").val("");
                $("#receipt_tel").val("");
                $("#cn").select2("val", "");
                $("#custom_name").val("");
            } else {
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 85
                });
                $('.easyui-layout').layout('resize');
                btnMoreSearch.text("更多条件");
                btnMoreSearch.attr("state", "close");
                $("#searchInfoId").css("display", "none");
                $("#end_address").val("");
                $("#topordernumber").val("");
                $("#downordernumber").val("");
                $("#receipt_name").val("");
                $("#receipt_tel").val("");
                $("#cn").select2("val", "");
                $("#custom_name").val("");
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

        /*	$("#cn").select2({ //自动完成
         placeholder : "请选择发货客户名称",
         minimumInputLength : 1,
         multiple : false,
         allowClear : true,
         query : function(query) {
         $.ajax({
         type : "POST",
         url : "shipOrder.do?method=getCustomName",
         data : {
         name : query.term
         }, //传递你输入框中的值
         success : function(msg) {
         var msg = $.parseJSON(msg);
         var data = {
         results : []
         };
         $.each(msg, function(x, y) {
         data.results.push({
         id : y.id,
         text : y.name
         });
         });
         query.callback(data);
         }
         });
         },
         }).on("select2-selecting", function(f) {
         $("#custom_name").val(f.object.text);
         });*/
    });

    //查询
    function searchCar_owner() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        $("#dg").datagrid('load', {
            send_time: startDate,
            end_time: endDate,
            shiping_order_num: $.trim($("#ddId").val()),
            send_mechanism: $.trim($("#send_mechanism").val()),//受理机构
            end_address: $.trim($("#end_address").val()),//终到位置
            custom_name: $.trim($("#custom_name").val()),//发货客户名称
            receipt_name: $.trim($("#receipt_name").val()),//到货联系人
            receipt_tel: $.trim($("#receipt_tel").val()),//到货联系人电话
            topordernumber: $.trim($("#topordernumber").val()),//上游订单
            downordernumber: $.trim($("#downordernumber").val()),//下游订单
            shipperorder_id: $.trim($("#shipperorder_id").val()),//出货订单号
        });
    }
    //货物到达接收
    function isArrive() {
        var checkarray = [];
        var checkanum = [];
        var custom_id = "";
        var array = $('#dg').datagrid('getSelections');
        for (var i = 0; i < array.length; i++) {
            if (array[i].is_order_arrive != 1) {
                checkarray.push(array[i].shiping_order_id);
                checkanum.push(array[i].shiping_order_num);
                custom_id = array[i].custom_id;
            }

            /*  if(array[i].shipping_order_state==0){
             checkarray.push(array[i].shiping_order_id);
             }else{
             $.messager.confirm('提示','您选中的运单号'+array[i].shiping_order_num+'不能执行删除！')
             } */
        }
        if (checkarray.length > 0) {
            $.messager.confirm('确认', '您确定要处理选中的记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "shipOrder.do?method=isArrive",
                        type: "POST",
                        data: {
                            del: checkarray.join(","),
                            num: checkanum.join(","),
                            custom_id: custom_id
                        },
                        success: function (data) {
                            if (data.flag) {
                                parent.$.messager.alert("运单信息", "订单信息到达 ",
                                    'info', function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert("运单录入", "请选择未到达信息", "info");
        }
    }

    //货物到达取消
    function isNotArrive() {
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        for (var i = 0; i < array.length; i++) {
            if (array[i].is_order_arrive != 0) {
                checkarray.push(array[i].shiping_order_id);
            }

            /*  if(array[i].shipping_order_state==0){
             checkarray.push(array[i].shiping_order_id);
             }else{
             $.messager.confirm('提示','您选中的运单号'+array[i].shiping_order_num+'不能执行删除！')
             } */
        }
        if (checkarray.length > 0) {
            $.messager.confirm('确认', '您确定要取消到达选中的记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "shipOrder.do?method=isNotArrive",
                        type: "POST",
                        data: {
                            del: checkarray.join(","),
                        },
                        success: function (data) {
                            if (data.flag) {
                                parent.$.messager.alert("运单信息", "运单信息取消到达",
                                    'info', function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert("运单录入", "请选择已到达信息", "info");
        }
    }

    function cancelControl(){
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        for (var i = 0; i < array.length; i++) {
            if (array[i].business_type == 1) {
                checkarray.push(array[i].shiping_order_id);
            }
        }
        if (checkarray.length == 0) {
            return     $.messager.alert("取消控制", "请选择要取消的信息", "info");
        }else{
            $.messager.confirm('确认', '您确定要取消控制选中的记录吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "shipOrder.do?method=updateBusinessType",
                        type: "POST",
                        data: {
                            ids: checkarray.join(","),
                        },
                        success: function (data) {
                            if (data.success) {
                                parent.$.messager.alert("运单信息", "运单信息取消控制成功",
                                    'info', function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
        }

    }

    //发短息
    function sendMsg() {
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        for (var i = 0; i < array.length; i++) {
            checkarray.push(array[i].shiping_order_id);

            /*  if(array[i].shipping_order_state==0){
             checkarray.push(array[i].shiping_order_id);
             }else{
             $.messager.confirm('提示','您选中的运单号'+array[i].shiping_order_num+'不能执行删除！')
             } */
        }
        if (checkarray.length > 0) {
            $.messager.confirm('确认', '您确定选中的记录发短信吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "shipOrder.do?method=isNotArrive",
                        type: "POST",
                        data: {
                            del: checkarray.join(",")
                        },
                        success: function (data) {
                            if (data.flag) {
                                parent.$.messager.alert("信息发送", "信息发送成功",
                                    'info', function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert("运单录入", "请选择一行信息", "info");
        }

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
            if (checkarray.length > 0) {
                $.messager
                    .confirm(
                        '确认',
                        '您选择的运单有可能已经分配到对应的司机，您确定要删除选中的记录吗？',
                        function (r) {
                            if (r) {
                                $
                                    .ajax({
                                        url: "shipOrder.do?method=deleteShipOrder",
                                        type: "POST",
                                        data: {
                                            del: checkarray
                                                .join(",")
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
            } else {
                $.messager.alert("运单录入", "请选择未操作的运单信息删除", "info");
            }
        } else {

            $.messager.alert("运单录入", "请选择一行信息", "info");
        }
    }

    /*
     修改查询
     */
    function modify() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length == 1) {
            if (row[0].shipping_order_state >= 4) return $.messager.alert("运单录入", "运单已经签收，不可以进行修改", "info");
            for (var i = 0; i < row.length; i++) {
                var id = row[i].shiping_order_id;
                dialog = parent.jy
                    .modalDialog({
                        title: '运单修改',
                        url: 'shipOrder.do?method=getEditShipOrderPage&pid='
                        + id,
                        width: 750,
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
    }
    //详情查询
    function xiangxixinxi() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length == 1) {
            for (var i = 0; i < row.length; i++) {
                var id = row[i].shiping_order_id;
            }
            dialog = parent.jy.modalDialog({
                title: '运单详情',
                url: 'shipOrder.do?method=getShipOrderPage&pid=' + id,
                width: 680,
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
    //修改
    function updateUser() {
        var myform = document.forms[1];
        myform.action = "car_owner.do?method=updateCar_owner";
        myform.submit();

    }


    //勾选信息导出
    function putoutfile() {
        var allRows2 = $("#dg").datagrid("getColumnFields");
        var row = $("#dg").datagrid("getSelections");
        var colName = [];
        var fieldName = [];
        var dataIds = [];
        for (i = 1; i < allRows2.length; i++) {
            var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
            if (col.field != "check_type" && col.field != "send_type"
                && col.field != "shipping_order_state") {
                if (flag == 0 || flag == 3 || flag == 4) {
                    colName.push(col.title);//把title列名到数组里去
                    fieldName.push(col.field); //把field列属性名到数组里去
                } else {
                    if (col.field != "deliver_fee"
                        && col.field != "upstairs_fee"
                        && col.field != "added_fee") {
                        colName.push(col.title);//把title列名到数组里去
                        fieldName.push(col.field); //把field列属性名到数组里去
                    }
                }
            }
        }

        for (i = 0; i < row.length; i++) {
            dataIds.push(row[i].shiping_order_id);
        }
        $("#fieldName").val(fieldName.join(","));
        $("#headtitle").val(colName.join(","));
        $("#dataIds").val(dataIds.join(","));

        var outPutForm = $("#outputform");
        $("#method").val("");
        $("#method").val("allMessageOutPut");
        outPutForm.submit();

    }

    function add() {
        dialog = parent.jy.modalDialog({
            title: '运单新增',
            url: 'shipOrder.do?method=addShipOrder',
            /* 				 buttons :'#addbuttons'
             */
            width: 750,
            buttons: [{
                text: '<input type="button" class="btncss" value="保存"/>',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }]
        });
    };
    //打印
    function dy() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length == 1) {
            for (var i = 0; i < row.length; i++) {
                var pid = row[i].shiping_order_id;
            }
            var url = "shipOrder.do?method=getShipOrderPrint&pid=" + pid;
            ow(url);
        } else {
            $.messager.alert("运单录入", "请选择一行信息", "info");
        }

    }
    function ow(owurl) {
        var tmp = window.open("about:blank", "", "fullscreen=1");
        tmp.moveTo(0, 0);
        tmp.resizeTo(screen.width + 20, screen.height);
        tmp.focus();
        tmp.location = owurl;
    }
    //导入
    function putintfile() {
        dialog = parent.jy.modalDialog({
            title: '导入运单',
            url: 'shipOrder.do?method=imp',
            width: 600,
            height: 300,
            buttons: [{
                text: '<input type="button" value="导入" class="btncss">',
                handler: function () {
                    dialog.find('iframe').get(0).contentWindow.submitForm(
                        dialog, grid, parent.$);
                }
            }],
            onClose: function () {
                $("#dg").datagrid('reload');
            }
        });
    };
    //重置
    function reset() {
        $("#startDate").val("");
        $("#ddId").val("");
        $("#end_address").val("");
        $("#topordernumber").val("");
        $("#downordernumber").val("");
        $("#shipperorder_id").val("");
        $("#receipt_name").val("");
        $("#receipt_tel").val("");
        $("#send_mechanism").val("");
        $("#cn").select2("val", "");
        $("#custom_name").val("");
        $("#endDate").val("");
    }
    /* //所有信息导出
     function allMessageOutPut() {
     var allRows2 = $("#dg").datagrid("getColumnFields");

     var colName = [];
     var fieldName = [];

     for (i = 1; i < allRows2.length; i++) {
     var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
     if (col.field != "check_type" && col.field != "send_type"
     && col.field != "shipping_order_state") {
     if(flag==0){
     colName.push(col.title);//把title列名到数组里去
     fieldName.push(col.field); //把field列属性名到数组里去
     }else{
     if(col.field != "deliver_fee" && col.field != "upstairs_fee"
     && col.field != "added_fee"){
     colName.push(col.title);//把title列名到数组里去
     fieldName.push(col.field); //把field列属性名到数组里去
     }
     }
     }
     }

     $("#fieldName").val(fieldName.join(","));
     $("#headtitle").val(colName.join(","));
     $("#method").val("allMessageOutPut");
     var outPutForm = $("#outputform");

     outPutForm.submit();

     } */
    //查看订单
    function searchMsg() {
        var row = $("#dg").datagrid("getSelections");
        if (row.length == 1) {
            var pid = row[0].shiping_order_id;
            var shiping_order_num = row[0].shiping_order_num;
            dialog = parent.jy.modalDialog({
                title: '运单信息',
                url: 'orderC.do?method=getShipOrderPage&pid=' + pid
                + '&shiping_order_num=' + shiping_order_num,
                width: 1100,
                height: 550,
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

    //一键删除所有信息
    function deleteAllMessage() {
        /* 	var send_time= $("#startDate").val();
         var end_time=$("#endDate").val();
         var shiping_order_num =$("#ddId").val();
         var send_mechanism=$("#send_mechanism").val();
         var end_address =$("#end_address").val();
         var custom_name=$("#custom_name").val();
         var receipt_name=$("#receipt_name").val();
         var receipt_tel=$("#receipt_tel").val();
         var topordernumber = $("#topordernumber").val();
         var downordernumber=$("#downordernumber").val(); */
        $.messager.confirm('确认', '请先查询，检查删除条件是否正确，若条件为空则删除全部？', function (r) {
            if (r) {
                $.ajax({
                    url: "shipOrder.do?method=deleteAllMessage",
                    type: "POST",
                    data: {
                        send_time: $.trim($("#startDate").val()),
                        end_time: $.trim($("#endDate").val()),
                        shiping_order_num: $.trim($("#ddId").val()),
                        send_mechanism: $.trim($("#send_mechanism").val()),//受理机构
                        end_address: $.trim($("#end_address").val()),//终到位置
                        custom_name: $.trim($("#custom_name").val()),//发货客户名称
                        receipt_name: $.trim($("#receipt_name").val()),//到货联系人
                        receipt_tel: $.trim($("#receipt_tel").val()),//到货联系人电话
                        topordernumber: $.trim($("#topordernumber").val()),//上游订单
                        downordernumber: $.trim($("#downordernumber").val())
                        //下游订单,
                    },
                    success: function (data) {
                        if (data.flag) {
                            parent.$.messager.alert("运单信息删除", "运单信息删除成功",
                                'info', function (r) {
                                    $('#dg').datagrid('reload');
                                });
                        } else {
                            $.messager.alert("运单信息删除", "运单信息删除失败");
                        }
                    }
                });
            }
        });

    }
</script>
<body class="easyui-layout">
<form action="shipOrder.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         height="85px" collapsible="false">
        <lable>起运日期:</lable>
        <input id="startDate" name="send_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; "
               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> -
        <input id="endDate" name="end_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px;"
               onfocus="WdatePicker({skin:'twoer'})"/>
        <lable class="divlabel">货运编号:</lable>
        <input type="text" class="search-text" id="ddId" style="width:100px" name="shiping_order_num">
        <lable class="divlabel"> 出货订单号:</lable>
        <input type="text" class="search-text" id="shipperorder_id"
               style="width: 100px" name="shipperorder_id">
        <lable>受理机构:</lable>
        <input type="text" class="search-text" id="send_mechanism" name="send_mechanism"> <a
            class="easyui-linkbutton" onclick="searchCar_owner()"
            data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
                                                           class="btn btn-sm btn-link" type="button"
                                                           style="margin-top:16px;"
                                                           href="javascript:void(0)" state="close">更多条件</a>
        <div id="searchInfoId" style="display: none">
            <lable>终到位置:</lable>
            <input type="text" class="search-text" style="width:150px"
                   id="end_address" name="end_address"> <label class="divlabel"
                                                               style="padding-left: 0px;">发货客户名称:</label><%-- <input type="hidden"
					id="cn" style="width: 150px;" />--%> <input type="txet"
                                                                id="custom_name" name="custom_name"> <label
                class="divlabel">收货联系人:</label> <input type="text"
                                                       class="search-text" style="width:150px" id="receipt_name"
                                                       name="receipt_name">

            <label class="divlabel">收货客户电话:</label> <input type="text"
                                                            class="search-text" style="width:150px" id="receipt_tel"
                                                            name="receipt_tel">
           <%-- <div id="" style="padding:inherit">
                <label class="">上游订单:</label> <input type="text"
                                                     class="search-text" style="width:150px" id="topordernumber"
                                                     name="topordernumber">
                <label class="divlabel">下游订单:</label> <input type="text"
                                                             class="search-text" style="width:150px"
                                                             id="downordernumber" name="downordernumber">
            </div>--%>
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>
    <div id="tb"></div>
    <input type="hidden" name="method" value="someMessageOutPut"
           id="method"> <input type="hidden" id="fieldName"
                               name="fieldName"> <input type="hidden" id="headtitle"
                                                        name="headtitle"> <input type="hidden" id="dataIds"
                                                                                 name="dataIds">
</form>
<form action="shipOrder.do" target="_blank" id="printywm" method="post">
    <input type="hidden" name="method" value="getShipOrderPrintYwm">
    <input type="hidden" id="checkarray" name="checkarray">
</form>
</body>
</html>