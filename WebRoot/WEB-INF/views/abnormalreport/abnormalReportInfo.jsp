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
                    height: 120
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
                    url: 'abnormalreport.do?method=getAbnromalReportInfo',
                    fit: true,
                    rownumbers: true,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    toolbar: '#tb',
                    rowStyler: function (index, row) {
                        // alert(row.clearing_state);
                        if (row.abnormal_result == 0) {
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
                        }, /* {
                         field : 'shippingOrder.send_mechanism',
                         title : '终到位置',
                         align : 'center',
                         formatter : function(val, row, index) {
                         if (row.shippingOrder) {
                         return row.shippingOrder.send_mechanism;
                         } else {
                         return '';
                         }

                         }
                         }, */
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
                        }, {
                            field: 'shippingOrder.receipt',
                            title: '收货客户名称',
                            width: 110,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.receipt;
                                } else {
                                    return '';
                                }

                            }
                        },
                        {
                            field: 'shippingOrder.receipt_name',
                            title: '收货联系人',
                            align: 'center',
                            width: 120,
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.receipt_name;
                                } else {
                                    return '';
                                }

                            }
                        },
                        {
                            field: 'shippingOrder.receipt_tel',
                            title: '收货客户电话',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.receipt_tel;
                                } else {
                                    return '';
                                }

                            }

                        },
                        {
                            field: 'shippingOrder.goods_name',
                            title: '货品名称',
                            align: 'center',
                            width: 120,
                            formatter: function (val, row, index) {
                                if (row.shippingOrder) {
                                    return row.shippingOrder.goods_name;
                                } else {
                                    return '';
                                }

                            }

                        }, {
                            field : 'remarks',
                            title : '发货备注',
                            width : 150,
                            align : 'center'
                        },
                        {
                            field: 'abnormal_type',
                            title: '异常类型',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val == "1") {
                                    return "货物异常";
                                } else if (val == "2") {
                                    return "客户异常";
                                }
                            }
                        },
                        {
                            field: 'abnormal_message',
                            title: '异常上报内容',
                            width: 120,
                            align: 'center',
                        }, {
                            field: 'abnormal_remark',
                            title: '异常备注',
                            width: 120,
                            align: 'center'
                        }, {
                            field: 'abnormal_num',
                            title: '不合格品件数',
                            width: 120,
                            align: 'center',
                        },
                        {
                            field: 'abnormal_time',
                            title: '上报时间',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val != null) {
                                    return val.substring(0, 19);
                                }
                            }
                        },
                        {
                            field: 'abnormal_name',
                            title: '上报人',
                            width: 120,
                            align: 'center',
                        },
                        {
                            field: 'abnormal_resultremark',
                            title: '处理结果',
                            align: 'center',
                            width: 120,
                        },
                        {
                            field: 'abnormal_result',
                            title: '是否审核',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {
                                if (val == "0") {
                                    return "未处理";
                                } else if (val == "1") {
                                    return "已处理";
                                }
                            }
                        },

                        {
                            field: 'imageURL',
                            title: '查看图片',
                            width: 120,
                            align: 'center',
                            formatter: function (val, row, index) {

                                if (row.abnormal_type == 2) {
                                    return "客户异常未上传照片";
                                } else {
                                    return "<a href='javascript:void(0)' style='color:red' title='点击查看签收图片' onclick=\"showimg('"
                                        + row.id
                                        + "')\">点击查看</a>";
                                }
                            }
                        }

                    ]],
                    pagination: true,
                    pageSize: 10,
                    pageList: [5, 10, 15, 20]
                });

        dropOrder();
    });
    //重置
    function reset() {
        $("#send_time").val("");
        $("#end_time").val("");
        $("#shiping_order_num").val("");
        $("#shiping_order_goid").val("");
        $("#abnormal_result").combobox('setValue', "");
        $("#abnormal_type").combobox('setValue', "");
        $("#end_address").val("");
        $("#receipt_name").val("");
    }

    //导出
    function putoutfile() {
        //var allRows2 = $("#dg").datagrid("getColumnFields");
        var array = $('#dg').datagrid('getSelections');
        /* var colName = [];
         var fieldName = []; */
        var checkarray = [];
        /* for (i = 1; i < allRows2.length; i++) {
         var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
         colName.push(col.title);//把title列名到数组里去
         fieldName.push(col.field); //把field列属性名到数组里去
         } */
        for (var i = 0; i < array.length; i++) {
            checkarray.push(array[i].id);
        }
        /* 	$("#fieldName").val(fieldName.join(","));
         $("#headtitle").val(colName.join(",")); */
        $("#abnormal_ids").val(checkarray.join(","));
        var outputform = $("#outputform");
        outputform.submit();
    }

    function dropOrder() {
        if (orders == "null")
            return;
        var data = orders.split(",");
        var shiping_order_num = data[0];
        var shiping_order_goid = data[1];
        $("#shiping_order_num").val(shiping_order_num);
        $("#shiping_order_goid").val(shiping_order_goid);
        searchUser();

    }

    //查询
    function searchUser() {
        $("#dg")
            .datagrid(
                'load',
                {
                    send_time: $.trim($("#send_time").val()),//上报时间
                    end_time: $.trim($("#end_time").val()),//上报时间
                    shiping_order_num: $.trim($("#shiping_order_num")
                        .val()),//货运编号
                    shiping_order_goid: $
                        .trim($("#shiping_order_goid").val()),//出货订单号
                    abnormal_result: $.trim($("#abnormal_result")
                        .combobox('getValue')),//是否处理
                    abnormal_type: $.trim($("#abnormal_type")
                        .combobox('getValue')),//上报类型
                    end_address: $.trim($("#end_address").val()),//终到位置
                    receipt_name: $.trim($("#receipt_name").val()),//收货客户姓名
                    send_mechanism: $.trim($("#send_mechanism").val()),//受理机构

                });
    }

    //异常反馈
    function abnormalFeedback() {
        dialog = parent.jy.modalDialog({
            title: '异常反馈运单信息',
            url: 'abnormalreport.do?method=serachOrder',
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

    //异常处理

    function exceptionHandling() {
        var row = $("#dg").datagrid("getSelections");
        if (row.length == 1) {
            var abnormal_result = row[0].abnormal_result;
            var id = row[0].id;
            if (abnormal_result == 0) {
                dialog = parent.jy
                    .modalDialog({
                        title: '运单信息',
                        url: 'abnormalreport.do?method=exceptionHandling&id='
                        + id,
                        width: 750,
                        height: 600,
                        buttons: [{
                            text: '<input type="button" class="btncss" value="处理"/>',
                            handler: function () {
                                dialog.find('iframe').get(0).contentWindow
                                    .submitHandling(dialog, grid,
                                        parent.$);
                            }
                        }]
                    });
            } else {
                $.messager.alert("异常反馈", "请选择未处理的信息", "info");
            }
        } else {
            $.messager.alert("异常反馈", "请选择一行信息", "info");
        }

    }

    //查看订单
    function searchMsg() {
        var row = $("#dg").datagrid("getSelections");
        if (row.length == 1) {
            var pid = row[0].shiping_order_id;
            var shiping_order_num = row[0].shiping_order_num;
            dialog = parent.jy.modalDialog({
                title: '运单信息',
               /* url: 'orderC.do?method=getShipOrderPage&pid=' + pid
                + '&shiping_order_num=' + shiping_order_num,*/
                url: 'abnormalreport.do?method=exceptionHandling&id='+row[0].id,
                width: 750,
                height: 600,
                buttons: [{
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow
                            .submitFormEdit(dialog, grid, parent.$);
                    }
                }]
            });
        } else {
            $.messager.alert("异常反馈", "请选择一行信息", "info");
        }
    }

    //查看异常反馈图片
    function showimg(obj) {
        dialog = parent.jy.modalDialog({
            title: '异常反馈图片详情',
            url: 'abnormalreport.do?method=showAbnormalImg&id=' + obj,
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
</script>
<body class="easyui-layout">
<form action="abnormalreport.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         data-options="collapsible:false" height="60px">
        <label class="divlabel">上报时间：</label> <input id="send_time"
                                                     name="send_time" class="Wdate" readonly="readonly"
                                                     style="width:150px;height:22px; "
                                                     onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> -
        <input
                id="end_time" name="end_time" class="Wdate" readonly="readonly"
                style="width:150px;height:22px;"
                onfocus="WdatePicker({skin:'twoer'})"/> <label class="divlabel">货运编号：</label>
        <input id="shiping_order_num" type="text" name="shiping_order_num"
               class="search-text"> <label class="divlabel">出货订单号：</label>
        <input id="shiping_order_goid" type="text" name="shiping_order_goid"
               class="search-text"> <a class="easyui-linkbutton"
                                       onclick="searchUser()" data-options="iconCls:'icon-search'"
                                       id="searchId">查询</a> <a id="btnMoreSearch"
                                                               class="btn btn-sm btn-link" type="button"
                                                               style="margin-top:16px;"
                                                               href="javascript:void(0)" state="close">更多条件</a>
        <div id="searchInfoId" style="display: none">
            <label class="divlabel">是否处理：</label> <select id="abnormal_result"
                                                          class="easyui-combobox" name="abnormal_result"
                                                          data-options="panelHeight : 'auto',editable:false"
                                                          style="width:150px;">
            <option value="" selected="selected">全部</option>
            <option value="1">是</option>
            <option value="0">否</option>
        </select> <label class="divlabel">异常类型：</label> <select id="abnormal_type"
                                                                class="easyui-combobox" name="abnormal_type"
                                                                data-options="panelHeight : 'auto',editable:false"
                                                                style="width:150px;">
            <option value="" selected="selected">全部</option>
            <option value="2">客户异常</option>
            <option value="1">货物异常</option>
        </select>
            <label class="divlabel">终到位置：</label> <input id="end_address"
                                                               type="text" name="end_address" class="search-text">
            <label
                    class="divlabel">收货联系人：</label> <input id="receipt_name"
                                                           type="text" name="receipt_name" class="search-text">
            <div id="" style="padding:inherit">
            <label class="divlabel"> 受理机构：</label>
            <input id="send_mechanism" type="text" name="send_mechanism"
                   class="search-text">
            </div>
        </div>
    </div>
    <input type="hidden" name="abnormal_ids" id="abnormal_ids"> <input
        type="hidden" name="method" value="outPutAbnormalFile"> <input
        type="hidden" id="fieldName" name="fieldName"> <input
        type="hidden" id="headtitle" name="headtitle">
    <div title="异常信息展示" region="center"
    >
        <table id="dg"></table>
    </div>

    <div id="tb">
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton"
            data-options="iconCls:'icon-add',plain:true"
            onclick="abnormalFeedback()">异常反馈</a> <a href="javascript:void(0)"
            class="easyui-linkbutton" id="modify"
            data-options="iconCls:'icon-edit',plain:true"
            onclick="exceptionHandling()">异常处理</a> <a href="javascript:void(0)"
            class="easyui-linkbutton"
            data-options="iconCls:'icon-remove',plain:true" onclick="searchMsg()">查看运单</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
            data-options="iconCls:'icon-ok',plain:true" onclick="putoutfile()">导出</a> -->
    </div>
    <input id="functionname" type="hidden" value="${function}">
</form>
</body>
</html>
