<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>

<% User user = (User) request.getSession().getAttribute(SessionInfo.SessionName); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>逆向物流分配</title>
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

    <% String driverId = (String) request.getAttribute("driverId");%>
</head>
<script type="text/javascript">
    var dialog;
    var grid;
    var editval;
    var driverId = '<%=driverId%>';
    $(function () {
        grid = $("#dg").datagrid({
            url: 'reverse.do?method=getDistributionReverseOrderInfo',
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
                field: 'order_type_name',
                title: '类型',
                width: 80,
                align: 'center'
            }, {
                field: 'send_mechanism',
                title: '受理机构',
                width: 120,
                align: 'center'
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
            onSelect: function (index, data) {
                var selectuser = $("#selectuser").text();
                var selectuserid = $("#selectuserid").text();
                /* if(selectuser.indexOf(data.shiping_order_num)!=-1){
                 $.messager.alert("警告","不可以选择重复运单",'info');
                 return;
                 } */
                if (selectuser == "请选择") {
                    $("#selectuser").text("");
                    $("#selectuserid").text("");
                }
                if ($("#selectuser").text() != "") {
                    $("#selectuser").append(",");
                    $("#selectuserid").append(",");
                }
                if (data.shiping_order_id != null
                    && data.shiping_order_id != "") {
                    if ($("#channelId").text() != "") {
                        $("#channelId").append(",");
                    }
                    $("#channelId").append(data.shiping_order_id);
                }
                $("#selectuser").append(data.shiping_order_num);
                $("#selectuserid").append(data.shiping_order_id);
                $('#dg').datagrid('deleteRow', index);
            },


            pagination: true,//分页
            pageSize: 10,
            pageList: [5, 10, 15, 20],
            toolbar: '#tbb'
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
                    height: 85
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
        /*
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
         }
         }).on("select2-selecting", function (f) {
         $("#end_mechanism").val(f.object.text);
         });*/
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
            custom_contact: $.trim($("#custom_contact").val()),//发货联系人
            order_type : $.trim($("#order_type")
                .combobox("getValue"))//订单类型
        });

    }


    //重置
    function reset() {
        $("#startDate").val("");
        $("#endDate").val("");
        $("#end_address").val("");
        $("#send_mechanism").val("");
        $("#custom_contact").val("");
        $("#order_type").combobox("setValue", "");
        $("#order_type").combobox("setText", "全部");
    }
    var submitForm = function ($dialog, $grid, $pjq, $mainMenu) {
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        if ($("#selectuserid").text() != "") {
            checkarray = $("#selectuserid").text().split(",");
        } else if (array.length != 0) {
            for (var i = 0; i < array.length; i++) {
                checkarray.push(array[i].shiping_order_id);
            }
        }
        //alert(checkarray[0]);
        if (!($("#selectuserid").text() == "" && array.length == 0)) {
            $pjq.messager.confirm('新增运单', '确定要分配这些运单吗?', function (r) {
                if (r) {
                    $.ajax({
                        type: "POST",
                        url: 'reverse.do?method=saveDistributionReverseOrderInfo',
                        data: {
                            driverId: driverId,
                            orders: checkarray.join(",")
                        },
                        success: function (msg) {
                            if (msg.flag) {
                                $pjq.messager.alert('新增信息', '添加成功', 'info');
                                $dialog.dialog('close');
                                $grid.datagrid('reload');
                            } else {
                                $pjq.messager.alert('新增失败', '添加失败：姓名和车牌重复',
                                    'info');
                            }
                        }
                    });
                } else {
                }
            });

        } else {
            $pjq.messager.alert('分配运单', '请选择一行信息', 'info');

        }

    };
    function clearuser() {
        if ($("#selectuser").text() != "请选择") {
            $("#selectuser").text("请选择");
            $("#selectuserid").text("");
            $("#channelId").text("");
            $("#message_touserid").val("");
            $("#message_tousername").val("");
            $("#cnid").val("");
            $("#dg").datagrid('clearSelections');
            $("#dg").datagrid('reload');
        }
    }

</script>
<body class="easyui-layout">
<form action="reverse.do" id="outputform" method="post">
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
    <div id="tbb">
        <tr style='margin: 20px;'>
            <td><font color="red" style="margin-right:10px">*</font>选择运单:</td>
            <td class="td2"><span id="selectuser"
                                  style="word-wrap:break-word;word-break:break-all;">请选择</span>&nbsp;&nbsp;<img
                    alt="清除" src="./images/clear.png"
                    style="cursor: pointer;width: 10px;height: 10px;"
                    onclick="clearuser()"> <span id="selectuserid"
                                                 style="display: none;"></span> <span id="channelId"
                                                                                      style="display: none;"></span>
        </tr>
    </div>
    <input type="hidden" name="method" value="outReverseOrder"
           id="method"> <input type="hidden" id="fieldName"
                               name="fieldName"> <input type="hidden" id="headtitle"
                                                        name="headtitle"> <input type="hidden" id="dataIds"
                                                                                 name="dataIds">
</form>
</body>
</html>