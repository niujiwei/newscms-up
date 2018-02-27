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
    var flag = '<%=user.getFlag()%>';
    var orders = '<%=orders%>';
    var hidden, hidden2;
    var message;
    if (flag == 0) {
        hidden = false;
        hidden2 = false;
        message =[{
            label: '全部',
            value: ''
        },{
            label: '审批',
            value: '2,3'
        },{
            label: '未审批',
            value: '0,1'
        }];

    } else if (flag == 1 || flag == 2) {
        hidden = false;
        hidden2 = true;
        message =[{
            label: '全部',
            value: ''
        },{
            label: '审核',
            value: '2,3'
        },{
            label: '未审核',
            value: '0,1'
        }];
    } else if (flag == 4 || flag == 3) {
        hidden = true;
        hidden2 = false;
        message =[{
            label: '全部',
            value: ''
        },{
            label: '审核',
            value: '1,2,3'
        },{
            label: '未审核',
            value: '0'
        }];
    }

    $(function () {
        initData();
        grid = $("#dg").datagrid({
            url: 'subsidyInfo.do?method=getSubsidyMapInfo',
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
                field: 'state',
                title: '状态',
                width: 80,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return "已提交";
                    } else if (value == 1) {
                        return "项目已审核";
                    } else if (value == 2) {
                        return "终端审批通过";
                    } else if (value == 3) {
                        return "终端审批不通过";
                    }
                }
            }, {
                field: 'delivery_date',
                title: '配送时间',
                width: 100,
                align: 'center'
            }, {
                field: 'shiping_order_num',
                title: '货运编号',
                width: 120,
                align: 'center'
            }, {
                field: 'shiping_order_goid',
                title: '出货订单号',
                width: 80,
                align: 'center'
            }, {
                field: 'send_mechanism',
                title: '受理机构',
                width: 120,
                align: 'center'
            }, {
                field: 'receipt_name',
                title: '收货联系人',
                width: 80,
                align: 'center',
                sortable: 'true'
            },
                {
                    field: 'goods_num',
                    title: '总件数',
                    width: 160,
                    align: 'center'
                }, {
                    field: 'end_address',
                    title: '终到位置',
                    width: 160,
                    align: 'center'
                }, {
                    field: 'end_mechanism',
                    title: '收货客户地址',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'super_far_subsidy',
                    title: '超远距离申请（KM）',
                    width: 140,
                    align: 'center'
                }, {
                    field: 'upstairs_subsidy',
                    title: '上楼补助申请（楼层）',
                    width: 110,
                    align: 'center'
                }, {
                    field: 'super_volume_subsidy',
                    title: '超体积补助申请（总体积）',
                    width: 120,
                    align: 'center'
                }, {
                    field: 'other_subsidy',
                    title: '其它补助申请',
                    width: 110,
                    align: 'center'
                }, {
                    field: 'remarks',
                    title: '申请备注',
                    width: 120,
                    align: 'center'
                }, {
                    field: 'sign_time',
                    title: '签收日期',
                    width: 100,
                    align: 'center'
                }, {
                    field: 'result',
                    title: '时效结果',
                    width: 120,
                    align: 'center'
                }, {
                    field: 'customer_subsidy',
                    title: '项目部审核结果',
                    width: 120,
                    align: 'center',
                    hidden: hidden2
                }, {
                    field: 'customer_remarks',
                    title: '项目部审核备注',
                    width: 120,
                    align: 'center',
                    hidden: hidden2
                }, {
                    field: 'admin_subsidy',
                    title: '终端配送审批结果',
                    width: 120,
                    align: 'center',
                    hidden: hidden
                }, {
                    field: 'admin_remarks',
                    title: '终端配送审批备注',
                    width: 120,
                    align: 'center',
                    hidden: hidden
                },{
                    field: 'create_time',
                    title: '申请时间',
                    width: 120,
                    align: 'center',
                    //hidden: hidden
                },{
                    field: 'customer_time',
                    title: '项目部审核时间',
                    width: 120,
                    align: 'center',
                    hidden: hidden2
                },{
                    field: 'admin_time',
                    title: '管理员审核时间',
                    width: 120,
                    align: 'center',
                    hidden: hidden
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
                $("#topordernumber").val("");
                $("#downordernumber").val("");
                $("#receipt_name").val("");
                $("#receipt_tel").val("");
                $("#cn").select2("val", "");
                $("#custom_name").val("");
            } else {
                $('.easyui-layout').layout('panel', 'north').panel('resize', {
                    height: 60
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
        /*
         select框
         */
    });

    function initData() {


        $("#state").combobox({
            valueField: 'value',
            textField: 'label',
            data: message
        })
    }
    //查询
    function searchCar_owner() {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        $("#dg").datagrid('load', {
            start_time: startDate,
            end_time: endDate,
            shiping_order_num: $.trim($("#shiping_order_num").val()),
            shiping_order_goid: $.trim($("#shiping_order_goid").val()),//出货订单号
            send_mechanism: $.trim($("#send_mechanism").val()),//受理机构
            end_address: $.trim($("#end_address").val()),//终到位置
            end_mechanism: $.trim($("#end_mechanism").val()),//发货客户名称
            receipt_name: $.trim($("#receipt_name").val()),//到货联系人
            //result: $("#result").combobox("getValue"),//时效结果
            state: $("#state").combobox("getValue")//状态

        });

    }

    //添加
    function add() {
        dialog = parent.jy.modalDialog({
            title: '补助申请添加',
            url: 'subsidyInfo.do?method=toAddSubsidyOrder',
            height: 600,
            width: 1200,
            onDestroy: function () {
                $("#dg").datagrid('reload');
            }
        });
    }

    //重置
    function reset() {
        $("#startDate").val("");
        $("#endDate").val("");
        $("#shiping_order_num").val("");
        $("#end_address").val("");
        $("#shiping_order_goid").val("");
        $("#receipt_name").val("");
        $("#send_mechanism").val("");
        $("#end_mechanism").val("");
       /* $("#result").combobox("setValue", "");
        $("#result").combobox("setText", "全部");*/
        $("#state").combobox("setValue", "");
        $("#state").combobox("setText", "全部");

    }

    function modify() {
        var row = $("#dg").datagrid('getSelections');
        if (row.length != 1) return $.messager.alert("补助申请", "请选择一行信息", "info");

        if (row[0].state > 0) return $.messager.alert("补助申请", "该信息已经审核,不能修改", "info");
        var id = row[0].id;
        dialog = parent.jy
            .modalDialog({
                title: '补助申请修改',
                url: 'subsidyInfo.do?method=toUpdateSubsidyInfo&id='
                + id,
                width: 750,
                buttons: [{
                    text: '<input type="button" class="btncss" value="修改"/>',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow
                            .submitFormEdit(dialog, grid,
                                parent.$);
                    }
                }],
                onDestroy: function () {
                    $("#dg").datagrid('reload');
                }
            });

    }

    function deleteEver() {
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        if (array == "") return $.messager.alert("补助申请", "请选择要删除的信息", "info");
        for (var i = 0; i < array.length; i++) {
            if (array[i].state == 0) {
                checkarray.push(array[i].id);
            }
        }
        if (checkarray.length == 0) return $.messager.alert("补助申请", "请选择未审核的信息", "info");
        $.messager.confirm('确认', '确定要删除补助申请信息?',
            function (r) {
                if (r) {
                    $.ajax({
                        url: "subsidyInfo.do?method=deleteSubsidyInfoModel",
                        type: "POST",
                        data: {
                            id: checkarray.join(",")
                        },
                        success: function (data) {
                            if (data.success) {
                                parent.$.messager.alert("补助申请删除", "补助申请信息删除成功", 'info',
                                    function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
    }

    function waybillAudit() {
        var row = $("#dg").datagrid('getSelections');
        var state = 0;
        if (row.length != 1) return $.messager.alert("补助申请", "请选择一行信息", "info");
        if (flag == 4 || flag == 3) {
            state = 1;
        } else if (flag == 0) {
            state = 2;
        } else if (flag == 1 || flag == 2) {
            return $.messager.alert("补助申请", "没有审核权限", "info");
        }

        if (row[0].state >= state) return $.messager.alert("补助申请", "该信息已经审核", "info");
        var id = row[0].id;
        dialog = parent.jy
            .modalDialog({
                title: '补助申请审核',
                url: 'subsidyInfo.do?method=toAuditSubsidyInfo&id='
                + id,
                width: 750,
                height: 680,
                buttons: [{
                    text: '<input type="button" class="btncss" value="审核"/>',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow
                            .submitFormEdit(dialog, grid,
                                parent.$);
                    }
                }],
                onDestroy: function () {
                    $("#dg").datagrid('reload');
                }
            });
    }
    //导入
    function putintfile() {
        dialog = parent.jy.modalDialog({
            title: '导入补助信息',
            url: 'subsidyInfo.do?method=toImportSubsidyInfo',
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
            if (col.field != "state") {
                if (flag == 3 || flag == 4) {
                    if (col.field != "admin_subsidy" && col.field != "admin_remarks"&&col.field!="admin_time") {
                        colName.push(col.title);//把title列名到数组里去
                        fieldName.push(col.field); //把field列属性名到数组里去
                    }

                } else if (flag == 2 || flag == 1) {
                    if (col.field != "customer_subsidy" && col.field != "customer_remarks"&& col.field != "customer_time") {
                        colName.push(col.title);//把title列名到数组里去
                        fieldName.push(col.field); //把field列属性名到数组里去
                    }
                } else {
                    colName.push(col.title);//把title列名到数组里去
                    fieldName.push(col.field); //把field列属性名到数组里去
                    if(col.field == "admin_remarks" ){
                        colName.push("是否通过");//把title列名到数组里去
                        fieldName.push("whether_through"); //把field列属性名到数组里去
                    }

                }
            }

        }
        for (i = 0; i < row.length; i++) {
            dataIds.push(row[i].id);
        }
        $("#fieldName").val(fieldName.join(","));
        $("#headtitle").val(colName.join(","));
        $("#dataIds").val(dataIds.join(","));

        var outPutForm = $("#outputform");
        outPutForm.submit();

    }

    function cancelAudit() {
        var checkarray = [];
        var array = $('#dg').datagrid('getSelections');
        if (array == "") return $.messager.alert("补助申请", "请选择要撤销提交的信息", "info");
        for (var i = 0; i < array.length; i++) {
                checkarray.push(array[i].id);
        }
        if (checkarray.length == 0) return $.messager.alert("补助申请", "请选择要撤销提交的信息", "info");
        $.messager.confirm('确认', '确定要撤销补助申请信息?',
            function (r) {
                if (r) {
                    $.ajax({
                        url: "subsidyInfo.do?method=cancelAudit",
                        type: "POST",
                        data: {
                            id: checkarray.join(",")
                        },
                        success: function (data) {
                            if (data.success) {
                                parent.$.messager.alert("补助申请", "补助申请信息撤销成功", 'info',
                                    function (r) {
                                        $('#dg').datagrid('reload');
                                    });
                            }
                        }
                    });
                }
            });
    }
</script>
<body class="easyui-layout">
<form action="subsidyInfo.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         height="60px" collapsible="false">
        <lable>起运日期:</lable>
        <input id="startDate" name="start_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; "
               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> -
        <input id="endDate" name="end_time" class="Wdate" readonly="readonly"
               style="width:150px;height:22px;"
               onfocus="WdatePicker({skin:'twoer'})"/>
        <lable class="divlabel">货运编号:</lable>
        <input type="text" class="search-text" id="shiping_order_num" style="width:100px" name="shiping_order_num">
        <lable class="divlabel"> 出货订单号:</lable>
        <input type="text" class="search-text" id="shiping_order_goid"
               style="width: 100px" name="shiping_order_goid">
        <lable>受理机构:</lable>
        <input type="text" class="search-text" id="send_mechanism" name="send_mechanism"> <a
            class="easyui-linkbutton" onclick="searchCar_owner()"
            data-options="iconCls:'icon-search'">查询</a>
        <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button"style="margin-top:16px;"
           href="javascript:void(0)" state="close">更多条件</a>
        <div id="searchInfoId" style="display: none">
            <lable>终到位置:</lable>
            <input type="text" class="search-text" style="width:150px"
                   id="end_address" name="end_address">
            <label class="divlabel">收货联系人:</label>
            <input type="text" class="search-text" style="width:150px"
                   id="receipt_name" name="receipt_name">
            <label class="divlabel">收货客户地址:</label>
            <input type="text"
                   class="search-text" style="width:150px" id="end_mechanism"
                   name="end_mechanism">
            <div id="" style="padding:inherit">
              <%--  <label class="">时效结果:</label>
                <select id='result' name="result" class="easyui-combobox"
                        style="width:150px;height:22px"
                        data-options="panelHeight : 'auto',editable:false">
                    <option value="" selected="selected">全部</option>
                    <option value="正常">正常配送</option>
                    <option value="延迟">延时配送</option>
                </select>--%>
                <label class="divlabel">状态:</label>
                <select id='state' class="easyui-combobox"
                        name="state" style="width:150px;height:22px"
                        data-options="panelHeight : 'auto',editable:false">
                </select>
            </div>
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>
    <div id="tb">
       <%-- <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-add',plain:true"
           onclick="add()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-remove',plain:true"
           onclick="modify()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="deleteEver()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="waybillAudit()">审核</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="cancelAudit()">撤销审核</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="putintfile()">导入</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           data-options="iconCls:'icon-edit',plain:true"
           onclick="putoutfile()">导出</a>--%>
    </div>
    <input type="hidden" name="method" value="outPutSubsidyInfo" id="method">
    <input type="hidden" id="fieldName" name="fieldName">
    <input type="hidden" id="headtitle" name="headtitle">
    <input type="hidden" id="dataIds" name="dataIds">
</form>

</body>
</html>