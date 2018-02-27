<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.jy.model.User" %>
<%@ page import="com.jy.common.SessionInfo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>供应商结算</title>
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
<% User user = (User) request.getSession().getAttribute(SessionInfo.SessionName);%>

<script type="text/javascript">
    var dialog;
    var grid;
    var editval;
    var suppliersSum;
    var suppliersOrderNum;
    var suppliers_id;
    var flag = '<%=user.getFlag()%>';
    $(function () {
        grid = $("#dg")
            .datagrid(
                {
                    url: 'Suppliers.do?method=getSupplierss',
                    border: false,
                    rownumbers: true,
                    fit: true,
                    singleSelect: false,
                    selectOnCheck: true,
                    checkOnSelect: true,
                    multiSort: true,

                    onDblClickRow: function (rowIndex, rowData) {
                        var pid = rowData.suppliersId;
                        var time = rowData.suppliersSendTime;
                        var driverId = rowData.driverId;
                        //var time=document.getElementById("timeId").value;
                        var timeId = rowData.suSendTime;
                        var state = rowData.clearing_state;
                        //console.info(state);
                        //var time = timeId.suppliersSendTime;
                        dialog = parent.jy
                            .modalDialog({
                                title: '供应商运单查看',
                                url: 'Suppliers.do?method=getSupplierOrder&pid='
                                + pid
                                + '&time='
                                + time
                                + '&timeId='
                                + timeId
                                + '&driverId=' + driverId,
                                //  data:{state:clearing_state}
                                width: 1200,
                                heght: 700,
                                buttons: [{
                                    handler: function () {
                                        dialog.find('iframe')
                                            .get(0).contentWindow
                                            .submitForm(dialog,
                                                grid,
                                                parent.$);
                                    }
                                }]
                            });
                    },
                    rowStyler: function (index, row) {
                        // alert(row.clearing_state);
                        if (row.clearing_state == 1) {
                            //alert(row.clearing_state);
                            return 'color:blue;';

                        }

                        //合计行颜色
                        if (row.suppliersOrderNum == '合计') {
                            return 'background-color:yellow;font-weight:bold;color:blue';
                        }
                    },
                    columns: [[
                        {
                            field: 'ck',
                            checkbox: true
                        },
                        //{field : 'suppliersOrderNum',title : '货运编号',width : 60,align:'center'},
                        {
                            field: 'suppliersName',
                            title: '供应商名称',
                            width: 140,
                            align: 'center'
                        },
                        {
                            field: 'suppliersAddress',
                            title: '终到位置',
                            width: 140,
                            align: 'center'
                        },
                        {
                            field: 'suppliersCount',
                            title: '运单总数',
                            width: 100,
                            align: 'center'
                        },
                        {
                            field: 'suppliersSendTime',
                            title: '最早起运时间',
                            width: 80,
                            align: 'center',
                            sortable: 'true'
                        },
                        {
                            field: 'suSendTime',
                            title: '最晚起运时间',
                            width: 100,
                            align: 'center'
                        },
                        /*{
                         field : 'suppliersGoodsName',
                         title : '货物名称',
                         width : 100,
                         align : 'center'
                         },*/
                        {
                            field: 'suppliersGoodsNum',
                            title: '总件数',
                            width: 100,
                            align: 'center'
                        },
                        //{field : 'suppliersGoodsWeight',title: '总体重',width:100,align:'center'},
                        //{field : 'suppliersGoodsVol',title : '总体积',width : 100,align:'center'},
                        /*{
                         field : 'suppliersTransportPay',
                         title : '总运费',
                         width : 100,
                         align : 'center'

                         },*/
                        {
                            field: 'suppliersDeliverFee',
                            title: '配送费',
                            width: 100,
                            align: 'center',
                            formatter: function (value, row, index) {
                                if (flag == 0) {
                                    return value;
                                }
                            }

                        },
                        {
                            field: 'suppliersUpstairsFee',
                            title: '上楼费',
                            width: 100,
                            align: 'center'

                        },
                        {
                            field: 'suppliersAddedFee',
                            title: '附加费',
                            width: 100,
                            align: 'center'

                        },
                        {
                            field: 'suppliersTradeAgency',
                            title: '代收货款',
                            width: 100,
                            align: 'center'
                        },
                        {field : 'suppliersGoodsWeight',title: '总重量',width:100,align:'center'},
                        {field : 'suppliersGoodsVol',title : '总体积',width : 100,align:'center'},

                        /*{
                            field: 'suppliersOtherFee',
                            title: '其他费用',
                            width: 100,
                            align: 'center'

                        }*/]],

                    /* {
                     field : 'suppliersSum',
                     title : '合计',
                     width : 140,
                     align : 'center',
                     styler : function(value, row, index) {
                     return 'background-color:yellow;font-weight:bold;color:blue';
                     },
                     formatter : function(value, row, index) {
                     var suppliersTransportPay = 0;
                     var suppliersTradeAgency = 0;
                     var suppliersDeliverFee = 0;
                     var suppliersUpstairsFee = 0;
                     var suppliersAddedFee = 0;
                     var suppliersOtherFee = 0;
                     var suppliersGoodsNum = 0;

                     if (row.suppliersTransportPay != null
                     && row.suppliersTransportPay != ""
                     && row.suppliersTransportPay != undefined) {
                     suppliersTransportPay = row.suppliersTransportPay * 1;
                     }
                     if (row.suppliersTradeAgency != null
                     && row.suppliersTradeAgency != ""
                     && row.suppliersTradeAgency != undefined) {
                     suppliersTradeAgency = row.suppliersTradeAgency * 1;
                     }
                     if (row.suppliersDeliverFee != null
                     && row.suppliersDeliverFee != ""
                     && row.suppliersDeliverFee != undefined) {
                     suppliersDeliverFee = row.suppliersDeliverFee * 1;
                     }

                     if (row.suppliersUpstairsFee != null
                     && row.suppliersUpstairsFee != ""
                     && row.suppliersUpstairsFee != undefined) {
                     suppliersUpstairsFee = row.suppliersUpstairsFee * 1;
                     }
                     if (row.suppliersAddedFee != null
                     && row.suppliersAddedFee != ""
                     && row.suppliersAddedFee != undefined) {
                     suppliersAddedFee = row.suppliersAddedFee * 1;
                     }
                     if (row.suppliersOtherFee != null
                     && row.suppliersOtherFee != ""
                     && row.suppliersOtherFee != undefined) {
                     suppliersOtherFee = row.suppliersOtherFee * 1;
                     }
                     if (row.suppliersGoodsNum != null
                     && row.suppliersGoodsNum != ""
                     && row.suppliersGoodsNum != undefined) {
                     suppliersGoodsNum = row.suppliersGoodsNum * 1;
                     }
                     if (index != 10) {
                     value = suppliersTransportPay
                     + suppliersTradeAgency
                     + suppliersDeliverFee
                     + suppliersUpstairsFee
                     + suppliersAddedFee
                     + suppliersOtherFee;
                     value = Number(value)
                     .toFixed(2);
                     if (value != null
                     && value != ""
                     && value != undefined) {
                     suppliersSum = suppliersSum
                     * 1 + value * 1;
                     }
                     } else {
                     value = Number(suppliersSum)
                     .toFixed(2);
                     }

                     return value;
                     }
                     }

                     ] ],
                     onLoadSuccess : function(data) {
                     //添加合计行
                     $('#dg')
                     .datagrid(
                     'appendRow',
                     {
                     suppliersOrderNum : '合计',
                     suppliersTransportPay : compute("suppliersTransportPay"),
                     suppliersTradeAgency : compute("suppliersTradeAgency"),
                     suppliersDeliverFee : compute("suppliersDeliverFee"),
                     suppliersUpstairsFee : compute("suppliersUpstairsFee"),
                     suppliersAddedFee : compute("suppliersAddedFee"),
                     suppliersOtherFee : compute("suppliersOtherFee")
                     });
                     //合并单元格
                     var merges2 = [ {
                     field : 'shiping_order_num',
                     index : data.rows.length - 1,
                     colspan : 7,
                     rowspan : 1
                     } ]
                     },
                     */
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
    });

    function compute(colName) {
        var rows = $('#dg').datagrid('getRows');
        var total = 0;
        for (var i = 0; i < rows.length; i++) {
            if (isNaN(parseFloat(rows[i][colName]))) {
                total += 0;
            } else {
                total += parseFloat(rows[i][colName]);
            }
        }
        return Number(total).toFixed(2);
    }
    //查询
    function searchCar_owner() {
        var suppliers_id;
        var timeId = document.getElementById("timeId").value;
        var timeIdd = document.getElementById("timeIdd").value;
        $("#dg").datagrid('load', {

            //suppliers_id:suppliers_id,
            suppliersSendTime: timeId,//开始时间
            suppliersEndTime: timeIdd,//结束时间
            suppliersEndAddress: $.trim($("#suppliersAddress").val()),
            suppliersName: $.trim($("#cn").val()),//供应商名称
        });
    }
    //导出
    function putoutfile() {
        var array = $('#dg').datagrid('getSelections');

        if (array.length > 0) {
            //console.info(array);
            var s = JSON.stringify(array);
            $("#suppliersId").val(s);
            //$("#supName").val(arra[0].suppliersName);
            var outputform = $("#outputform");
            outputform.submit();

        } else {
            $.messager.alert("运单结算", "请至少选择一个供应商", 'info');
        }
    }
    /*
     var allRows2 = $("#dg").datagrid("getColumnFields");
     var colName=[];
     var fieldName=[];
     for(i=1;i<allRows2.length;i++){

     var col = $('#dg').datagrid( "getColumnOption" , allRows2[i] );
     colName.push(col.title);//把title列名到数组里去
     fieldName.push(col.field); //把field列属性名到数组里去

     }


     $("#fieldName").val(fieldName.join(","));
     $("#headtitle").val(colName.join(","));
     var outputform=$("#outputform");
     outputform.submit();

     }*/

    //结算
    function fn_pay() {
        var checkarray = [];
        var checkip = '';

        var suppliersSendTime;
        var suSendTime;
        var cla;
        var array = $('#dg').datagrid('getSelections');

        if (array != "") {
            if (array.length == 1) {
                checkarray.push(array[0].driverId); //获取bean对象里的值
                suSendTime = array[0].suSendTime;
                suppliersSendTime = array[0].suppliersSendTime;
                cla = array[0].clearing_state;
                if (cla == 1) {
                    $.messager.alert("运单结算", "你选择的已结算！", 'info');
                    return;
                }
                if (cla == undefined) {
                    $.messager.alert("运单结算", "请选择正确运单！", 'info');
                    return;
                }
                $.messager.confirm('确认', '您确定要结算选中的记录吗？', function (r) {
                    if (r) {
                        $.ajax({
                            url: "Suppliers.do?method=settleSupp&suSendTime="
                            + suSendTime + '&suppliersSendTime='
                            + suppliersSendTime,
                            type: "POST",
                            data: {
                                driverId: checkarray.join(",")
                            },

                            success: function (data) {
                                if (data.flag) {
                                    /*parent.$.messager.alert("订单结算aaa","订单结算成功",'info',

                                     function(r) {
                                     $('#dg').datagrid('reload');
                                     });*/
                                } else {
                                    $.messager.alert("运单结算", "运单结算成功", 'info');
                                    $('#dg').datagrid('reload');
                                }
                            }
                        });
                    }
                });

            } else {
                $.messager.alert("运单结算", "请选择一个供应商进行结算", 'info');
            }
            // console.info(array[0]);
        } else {
            $.messager.alert("运单结算", "请选择要结算的信息", "info");
        }
    }
    function ow(owurl) {
        var tmp = window.open("about:blank", "", "fullscreen=1");
        tmp.moveTo(0, 0);
        tmp.resizeTo(screen.width + 20, screen.height);
        tmp.focus();
        tmp.location = owurl;
    }
    function searchMsg() {
        var row = $("#dg").datagrid("getSelections");
        if (row.length == 1) {
            var pid = row[0].suppliersId;
            var time = row[0].suppliersSendTime;
            var driverId = row[0].driverId;
            //var time=document.getElementById("timeId").value;
            var timeId = row[0].suSendTime;
            var state = row[0].clearing_state;
            dialog = parent.jy.modalDialog({
                title: '供应商运单查看',
                url: 'Suppliers.do?method=getSupplierOrder&pid=' + pid
                + '&time=' + time + '&timeId=' + timeId + '&driverId='
                + driverId,
                width: 1200,
                heght: 700,
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
<form action="Suppliers.do" id="outputform" method="post">
    <div region="north" title="检索" class="st-north" border="false"
         height="60px" collapsible="false">
        <lable>起运日期:</lable>
        <input id="timeId" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; " name="suppliersSendTime"
               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/>
        <lable>至</lable>
        <input id="timeIdd" class="Wdate" readonly="readonly"
               style="width:150px;height:22px; " name="suSendTime"
               onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})"/> <label>供应商名称:</label>
        <input type="text" id="cn" style="width: 150px;" name="suppliersName"/> <label>终到位置:</label>
        <input type="text" id=suppliersAddress style="width: 150px;" name="suppliersAddress"/> <a
            class="easyui-linkbutton" onclick="searchCar_owner()"
            data-options="iconCls:'icon-search'">查询</a>
        <div id="searchInfoId" style="display: none">
            <input type="hidden" id="suppliers_name" name="suppliers_name">
        </div>
    </div>
    <div region="center">
        <table id="dg" title="运单信息"></table>
    </div>
    <div id="tb">
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton"
            data-options="iconCls:'icon-remove',plain:true"
            onclick="settlesupp()">结算</a> <a href="javascript:void(0)"
            class="easyui-linkbutton"
            data-options="iconCls:'icon-remove',plain:true"
            onclick="xiangxixinxi()">运单信息</a> -->
    </div>
    <input type="hidden" name="method" value="outSuppOrder"> <input
        type="hidden" id="supName">
    <input type="hidden" id="suppliersId" name="suppliersId">
</form>
</body>
</html>