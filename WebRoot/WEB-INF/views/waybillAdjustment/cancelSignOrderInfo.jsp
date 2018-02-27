<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>取消签收信息</title>
    <jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
    <script type="text/javascript" src="./js/select2/select2.js"></script>
    <script type="text/javascript"
            src="./js/select2/select2_locale_zh-CN.js"></script>
    <script type="text/javascript" src="./js/function.js"></script>
    <script type="text/javascript" src="./js/dateUtils.js"></script>
    <%

        User user = (User) request.getSession().getAttribute(
                SessionInfo.SessionName);
        String orders = (String) request.getAttribute("order");
    %>
</head>
<script type="text/javascript">
    var username = '<%=user.getUsername()%>';
    var orders = '<%=orders%>';
    var dialog;
    var grid;
    $(function () {
        grid = $("#dg")
            .datagrid(
                {
                    url: 'shipOrder.do?method=getSignShipOrder',
                    border: false,
                    fit: true,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    toolbar: '#tbb',
                    columns: [[
                        {
                            field: 'ck',
                            checkbox: true
                        },
                        //{field : 'shiping_order_num',title : '订单号',width : 70,align:'center'},
                        {
                            field: 'shiping_order_num',
                            title: '货运编号',
                            width: 60,
                            align: 'center'
                        },
                        {
                            field: 'send_mechanism',
                            title: '受理机构',
                            width: 120,
                            align: 'center'
                        },
                        {
                            field: 'shiping_order_goid',
                            title: '出货订单号',
                            width: 80,
                            align: 'center'
                        },
                        {
                            field: 'end_address',
                            title: '终到位置',
                            width: 120,
                            align: 'center'
                        },

                        {
                            field: 'shipping_order_state',
                            title: '运单状态',
                            width: 70,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val == 0) {
                                    return "已发运";
                                } else if (val == 1) {
                                    return "已分配";
                                } else if (val == 2) {
                                    return "已接单";
                                } else if (val == 3) {
                                    return "电子围栏签收";
                                } else if (val == 4) {
                                    return "已签收";
                                } else if (val == 5) {
                                    return "已签收";
                                } else if (val == 6) {
                                    return "已签收";
                                }
                            }
                        },
                        {
                            field: 'shipping_order_statestr',
                            title: '运单状态',
                            hidden: true
                        },
                        {
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
                        {
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
                        }, {
                            field: 'receipt_name',
                            title: '收货联系人',
                            width: 100,
                            align: 'center'
                        }/*, {
                            field: 'sign_user',
                            title: '收货人',
                            width: 70,
                            align: 'center'
                        }*/, {
                            field: 'sign_usernumber',
                            title: '收货人证件',
                            width: 80,
                            align: 'center'
                        }, {
                            field: 'sign_remarks',
                            title: '签收备注',
                            width: 200,
                            align: 'center'
                        }, {
                            field: 'abnormal_name',
                            title: '异常情况',
                            width: 70,
                            align: 'center',
                            /* formatter : function(val, row, index) {
                             if (row.is_abnormal == 0) {
                             return "运单暂无异常情况";
                             } else {
                             return "<a href='javascript:void(0)' style='color:red' title='点击查看异常信息' onclick=\"showimg('"
                             + row.shiping_order_id
                             + "')\">点击查看</a>";
                             }
                             } */

                        }, {
                            field: 'goods_num',
                            title: '总件数',
                            width: 100,
                            align: 'center'
                        },

                        {
                            field: 'sign_name',
                            title: '收货代理人',
                            width: 80,
                            align: 'center'
                        }, {
                            field: 'sign_number',
                            title: '收货代理人证件',
                            width: 140,
                            align: 'center'
                        }, {
                            field: 'sign_username',
                            title: '签收经办人',
                            width: 70,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val == null) {
                                    return "";
                                } else {
                                    return val;
                                }
                            }
                        },
                        //{field : 'end_mechanism',title : '终到机构',width : 120,align:'center'},
                        //{field : 'custom_code',title : '客户编号',width : 100,align:'center'},
                        //{field : 'custom_name',title : '发货客户名称',width : 140,align:'center'},
                        //{field : 'custom_contact',title : '发货客户联系人',width : 100,align:'center'},
                        //	{field : 'custom_tel',title : '发货客户联系人',width : 100,align:'center'},
                        //{field : 'receipt',title : '收货客户名称',width : 100,align:'center'},

                        //{field : 'receipt_tel',title : '到货联系电话',width : 100,align:'center'},
                        //{field : 'goods_name',title : '货物名称',width : 100,align:'center'},
                        //{field : 'is_recept',title : '回单份数',width : 100,align:'center'},
                        //{field : 'transport_pay',title : '总运费',width : 100,align:'center'},
                        //{field : 'trade_agency',title : '代收货款',width : 100,align:'center'},
                        //{field : 'goods_weight',title : '总重量',width : 100,align:'center'},
                        //{field : 'goods_vol',title : '总体积',width : 100,align:'center'},

                    ]],
                    pagination: true,//分页
                    pageSize: 10,
                    rowStyler: function (rowIndex, rowData) {
                        //任务完成100%， 并且已审核通过，背景色置灰
                        if (rowData.sign_result == "0") {
                            return 'color:blue';
                        }
                    },
                    pageList: [5, 10, 15, 20],
                    queryParams: {
                        shipping_order_state:'1'
                    }
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
                $("#shipping_order_state").combobox("setValue", "");
                $("#shipping_order_state").combobox("setText", "全部");
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
                $("#shipping_order_state").combobox("setValue", "");
                $("#shipping_order_state").combobox("setText", "全部");
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
    });
    function showimg(obj) {
        dialog = parent.jy.modalDialog({
            title: '签收图片详情',
            url: 'shipOrder.do?method=getimgurl&iurl=' + obj,
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
    //查询
    function searchCar_owner() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        $("#dg").datagrid(
            'load',
            {
                start_date : startDate,
                end_date : endDate,
                name : $.trim($("#ddId").val()),
                shipping_order_state:'1',/*
                shipping_order_state : $.trim($("#shipping_order_state")
                    .combobox("getValue")),*/
                custom_name : $.trim($("#custom_name").val()),
                receipt : $.trim($("#receipt").val()),
                address : $.trim($("#address").val()),
                shipperorder_id : $.trim($("#shipperorder_id").val())
                //出货订单号
            });
    }


    //重置
    function reset() {
        $("#startDate").val("");
        $("#endDate").val("");
        $("#ddId").val("");
        $("#custom_name").val("");
        $("#receipt").val("");
        $("#address").val("");
        $("#shipperorder_id").val("");
     /*   $("#shipping_order_state").combobox("setValue", "");
        $("#shipping_order_state").combobox("setText", "全部");*/
    }

    //查看订单信息
    function searchMsg() {
        var row = $("#dg").datagrid('getSelections');
        var isok = 0;
        var id = 0;
        for (var i = 0; i < row.length; i++) {
            id = row[i].shiping_order_id;
            isok = row[i].shipping_order_state;
        }
        if (row.length == 1) {
            dialog = parent.jy.modalDialog({
                title: '运单详情',

                url: 'orderC.do?method=getShipOrderPage&pid='
                + row[0].shiping_order_id + '&shiping_order_num='
                + row[0].shiping_order_num,

                width: 1100,
                height: 550,
                buttons: [{
                    text: '',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.qs(dialog,
                            grid, parent.$);
                    }
                }]
            });
        } else {
            $.messager.alert("运单签收", '请选择一行信息', 'info');
        }
    }
    
    function cancelSignInfo() {
        var row = $("#dg").datagrid('getSelections');
        var shiping_order_id = row[0].shiping_order_id;
        if (row.length != 1) return $.messager.alert("取消签收", '请选择一行信息', 'info');
        if(row[0].waybill_state==2)  return $.messager.alert("取消签收", '该运单已经申请取消签收了，请等待处理', 'info');
        var shiping_order_num = row[0].shiping_order_num;
        $.messager.prompt('取消签收', '请输入取消签收的原因，必填', function(r){
            if (r!=''){
                $.ajax({
                    url:"waybillAdjustment.do?method=addCancelSignInfo",
                    data:{
                        shiping_order_id:shiping_order_id,
                        applicant_message:r,
                        applicant_type:1,
                        shiping_order_num:shiping_order_num
                    },
                    type:"post",
                    success:function (data) {
                        if(data.success){
                            $.messager.alert("取消签收", '取消签收申请成功', 'info');
                            grid.datagrid('reload');
                        }else{
                            $.messager.alert("取消签收", '取消签收申请失败', 'info');
                            grid.datagrid('reload');
                        }
                    },error:function () {
                        $.messager.alert('运单调整', '系统出错了', 'info');
                    }
                });
            }
        });

    }
</script>
<body class="easyui-layout">
<form action="" id="" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         collapsible="false">
        <lable>起运日期:</lable>
        <input id="startDate" name="start_date" class="Wdate"
               readonly="readonly" style="width:150px;height:22px; "
               onfocus="WdatePicker({skin:'twoer'})"/> - <input id="endDate"
                                                                name="end_date" class="Wdate" readonly="readonly"
                                                                style="width:150px;height:22px;"
                                                                onfocus="WdatePicker({skin:'twoer'})"/>
        <lable class="divlabel">货运编号:</lable>
        <input type="text" class="search-text" id="ddId" name="name"
               style="width:150px">
        <lable class="divlabel"> 出货订单号:</lable>
        <input type="text" class="search-text" id="shipperorder_id"
               style="width: 100px" name="shipperorder_id"><a
            class="easyui-linkbutton" onclick="searchCar_owner()"
            data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
                                                           class="btn btn-sm btn-link" type="button"
                                                           style="margin-top:16px;"
                                                           href="javascript:void(0)" state="close">更多条件</a>
        <div id="searchInfoId" style="display: none">
            <%--  <lable class="divlabel">运单状态:</lable>
          <select id='shipping_order_state' class="easyui-combobox"
                    name="shipping_order_state" style="width:150px;height:22px"
                    data-options="panelHeight : 'auto',editable:false">
                <option value="" selected="selected">全部</option>
                <option value="0">未签收</option>
                <!-- <option value="1">已分配</option>
                <option value="2">已接单</option>
                <option value="3">围栏签收</option> -->
                <option value="1">已签收</option>
            </select>--%>
            <lable>受理机构:</lable>
            <input type="text" class="search-text" style="width:150px"
                   id="custom_name" name="custom_name">
            <lable>到货联系人:</lable>
            <input type="text" class="search-text" style="width:150px"
                   id="receipt" name="receipt">
            <lable class="divlabel">终到位置:</lable>
            <input type="text" class="search-text" id="address" name="address">
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>

    <div id="tbb">
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove',plain:true"
           onclick="cancelSignInfo()">取消签收</a>
    </div>
    <input type="hidden" name="method" value="outSignShipOrderFile">
    <input type="hidden" id="fieldName" name="fieldName"> <input
        type="hidden" id="headtitle" name="headtitle"> <input
        type="hidden" id="dataIds" name="dataIds">
</form>
</body>
</html>