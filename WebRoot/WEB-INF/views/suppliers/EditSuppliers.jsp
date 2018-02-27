<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.jy.model.User" %>
<%@ page import="com.jy.common.SessionInfo" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>供应商修改</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">


    <jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
    <jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
    <script type="text/javascript" src="./js/select2/select2.js"></script>
    <script type="text/javascript"
            src="./js/select2/select2_locale_zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="./css/main.css">
    <link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
    <link rel="stylesheet" type="text/css"
          href="./webuploader/webuploader.css"/>
    <link rel="stylesheet" type="text/css" href="./webuploader/style.css"/>
</head>
<%
    String flg = (String) request.getAttribute("flg");
    User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
%>
<script type="text/javascript">
    var dialog;
    var grid;
    var images = new Array;
    var pid = '<%=flg%>';
    var flag='<%= user.getFlag()%>';
    var oneid = 0;
    var twoid = 0;
    var threeid = 0;
    $(function () {

        $("#editform").validationEngine('attach', {
            promptPosition: 'topRight:-15,0'
        });
        var $target = $('input,textarea,select');
        $target.bind('keydown', function (e) {
            var key = e.which;
            if (key == 13) {
                e.preventDefault();
                var nxtIdx = $target.index(this) + 1;
                $target.eq(nxtIdx).focus();
            }
        });
        $('#province').combotree(
            {
                url: 'driver.do?method=getNewFinalPositionAddress',
                valueField: 'id',
                textField: 'text',
                multiple: true,
                width: 220,
                editable: false,

            });
        /* 	$('#province').combobox(
         {
         url : 'driver.do?method=getNewFinalPositionCounty',
         valueField : 'id',
         textField : 'text',
         width : 120,
         editable : false,
         onSelect : function(record) {
         oneid = record.citye_parent_id;
         twoid = 0;
         $('#city').combobox("clear");
         $('#county').combotree("clear");
         $('#city').combobox(
         'reload',
         'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
         + oneid);
         $('#county').combotree(
         'reload',
         'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
         + twoid);
         }
         });

         $('#city')
         .combobox(
         {
         url : 'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
         + oneid,
         valueField : 'id',
         width : 120,
         textField : 'text',
         editable : false,
         onSelect : function(record) {
         twoid = record.citye_parent_id;
         $('#county').combotree("clear");
         $('#county').combotree(
         'reload',
         'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
         + twoid);
         }
         });
         */
        /*  $('#county')
         .combobox(
         {
         url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
         + twoid,
         valueField : 'id',
         width : 120,
         textField : 'text',
         editable : false

         });*/
        /* 	$('#county')
         .combotree(
         {
         multiple : true,
         url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
         + twoid,

         }); */
        /* 		$("#suppliers_customer").select2({ //自动完成
         placeholder : "请选择发货客户名称",
         //minimumInputLength: 1,
         multiple : true,
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
         id : y.customerid,
         text : y.name
         });
         });
         query.callback(data);
         }
         });
         },
         }); */
        $('#carType').combobox({
            url: 'suppliers.do?method=carType',
            valueField: 'id',
            textField: 'text'
        });
        $('#suppliers_customer').combotree({
            multiple: true,
            url: 'shipOrder.do?method=getNewCustomName',
            onLoadSuccess: function (node, data) {
                formatComboTreeValue();
            },
            onCheck: function (node, checked) {
                formatComboTreeValue();
            },
            onClick: function (node, checked) {
                formatComboTreeValue();
            }
        });
        initData();
        //去除根节点在选中的显示
        function formatComboTreeValue() {

            var value = $('#suppliers_customer').combotree('getText');
            if (value.indexOf("全选,") >= 0) {
                var endIndex = value.indexOf(',');
                value = value.substring(endIndex + 1);
                $('#suppliers_customer').combotree('setText', value);
            }
        }

        $.ajax({
            url: 'suppliers.do?method=updateSuppliers',
            data: {
                cid: pid
            },
            success: function (data) {
                console.info(data);

                /* $("#province").combobox("setValue",
                 data.driver_province);
                 $("#city").combobox(
                 'reload',
                 'driver.do?method=getNewFinalpositionCity&&citye_parent_id='
                 + data.driver_province);
                 $("#city").combobox("setValue", data.driver_city);

                 $("#carType").combobox("setValue", data.suppliers_cartype);
                 $("#county").combotree(
                 'reload',
                 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
                 + data.driver_city);

                 if (data.suppers_county[0] == null) {
                 data.suppers_county[0] = "";
                 }
                 $("#county")
                 .combotree("setValues", data.suppers_county); */

                $('#editform').form("load", data);


                var customer = [];

                $.each(data.customer, function (k, v) {

                    customer.push(v.customer_id);
                });

                $("#suppliers_customer").combotree("setValues",
                    customer);
                var citys = [];
                var t = $('#province').combotree('tree');

                $.each(data.listCity_infos, function (k, v) {
                    var node = t.tree('find', v.province);
                    if (node) {
                        t.tree('expandAll', node.target);
                    } else {
                        t.tree('expandAll');
                    }


                    citys.push(v.county);
                });
                //$("#province").combotree("setText", data.suppliersAddress);
                $("#province").combotree("setValues", data.suppliersAddress);

                /* $("#province2").val(data.driver_province);
                 $("#city2").val(data.driver_city);
                 $("#county2").val(data.driver_county); */

                if (data.suppliersProve != ""
                    && data.suppliersProve != null) {

                    images = (data.suppliersProve).split("|");
                    var imag = "<dl>";
                    for (var i = 0; i < images.length; i++) {
                        var im = images[i];
                        var idd = im.substring(im.lastIndexOf("/") + 1,
                            im.lastIndexOf("."));
                        imag += "<div id=\"" + idd + "\" style=\"float:left;margin-left:10px;\"><dt><img class=\"imsty\" src=\"" + images[i] + "\"/></dt> <dd><input style=\"width:80px;\" onclick=\"deleteImag('"
                            + idd
                            + "')\" type=\"button\" value=\"删除\"/></dd></div>";
                    }
                    imag += "</dl>";
                    $("#xian").html(imag);
                    //$("#shang").attr("style","margin-top:100px");
                    $("#container").removeAttr("class");
                    $("#container").attr("class",
                        "webuploader-element-invisible");
                }
            }
        });


    });

    function initData() {
       if(flag!='0'){
            $("#province").combotree("disable",true);
            $("#suppliers_customer").combotree("disable");
       }
    }

    function deleteImag(path) {
        var pa = $("#" + path + " >> img").attr("src");
        $.messager.confirm("删除图片", "确定要删除吗?", function (r) {
            if (r) {
                for (var n = 0; n < images.length; n++) {
                    if (images[n] == pa) {
                        images.remove(pa);
                    }
                }
                var imaStr = images.join("|");
                $.ajax({
                    url: 'suppliers.do?method=deleteimage',
                    data: {
                        impath: pa,
                        images: imaStr,
                        suppId: pid
                    },
                    success: function (data) {
                        $("#" + path + "").hide();
                        if (imaStr == "" || imaStr == null) {
                            shangShow();
                        }
                        //$.messager.info("图片删除成功");
                    }
                });
            }
        });
    }
    function shangShow() {
        $("#container").removeAttr("class");
        $("#container").attr("class", ".webuploader-pick");
    }
    var submitFormEdit = function ($dialog, $grid, $pjq, $mainMenu) {
        var ima = images.join("|");
        //if($("#editform").validationEngine('validate')){
        //可提交
        /* var poin = $("#province").combobox('getText');
         var city = $("#city").combobox('getText');
         var county = $("#county").combobox('getText'); */

        var t = $('#province').combotree('tree');	// 得到树对象
        var n = t.tree('getChecked');	// 得到选择的节点
        var province = JSON.stringify(n);
        $("#dprovince").val(province);
        var carType = $("#carType").combobox('getText');
        $("#suppliers_cartype").val(carType);

        var suppliers = $("#suppliers_customer").combotree('getText');
        var _supplierss = $("#suppliers_customer").combotree("getValues");
        $("#suppliers_customer1").val(_supplierss);
        if ($("#editform").validationEngine('validate') && suppliers != "") {
            $pjq.messager
                .confirm(
                    '修改供应商信息',
                    '确定要修改吗?',
                    function (r) {
                        if (r) {
                            $(".uploadBtn").click();
                            $
                                .ajax({
                                    type: "POST",
                                    url: 'suppliers.do?method=update&impath='
                                    + ima,
                                    data: $('#editform')
                                        .serialize(),
                                    success: function (msg) {
                                        setTimeout(
                                            function () {
                                                if (msg.flag) {
                                                    $
                                                        .ajax({
                                                            type: "post",
                                                            url: 'suppliers.do?method=updateImage',
                                                            data: {
                                                                images: ima,
                                                                suppId: pid
                                                            },
                                                            success: function (da) {
                                                                if (da.flag) {
                                                                    $pjq.messager
                                                                        .alert(
                                                                            '修改供应商信息',
                                                                            '供应商信息修改成功',
                                                                            'info');
                                                                    $dialog
                                                                        .dialog('close');
                                                                    $grid
                                                                        .datagrid('reload');
                                                                }
                                                            }
                                                        });
                                                }
                                                ;
                                            }, 1000);
                                    }
                                });
                        }
                    });
        } else {
            $pjq.messager.alert('修改供应商信息', '必填信息不可为空', 'info');
        }
    };
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val)
                return i;
        }
        return -1;
    };
    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
</script>
<body class="easyui-layout">
<div region="center">
    <form action="#" method="post" id="editform">
        <input type="hidden" id="driver_address" name="suppliersAddress">
        <input type="hidden" name="suppliersId"> <input type="hidden"
                                                        id="suppliers_cartype" name="suppliers_cartype">
        <fieldset>
            <table class="tableclass">
                <tr>

                    <th colspan="4">基本信息</th>
                </tr>
                <tr>
                    <td><font color="red" style="margin-right:10px">*</font>发货客户名称:</td>
                    <td class="td2" colspan="4"><input type="text"
                                                       style="width: 420px;" id="suppliers_customer" name=""> <input
                            type="hidden" id="suppliers_customer1" name="suppliers_customer">
                    </td>
                </tr>
                <tr>
                    <td><font color="red" style="margin-right:10px">*</font>供应商公司名称:</td>
                    <td class="td2"><input type="text" class="validate[required]"
                                           id="supplie_company" name="supplie_company"></td>
                </tr>
                <tr>
                    <td><font color="red" style="margin-right:10px">*</font>供应商名称:</td>
                    <td class="td2"><input type="text" class="validate[required]"
                                           id="suppliersName" name="suppliersName">
                    </td>
                    <td>公司法人:</td>
                    <td class="td2"><input type="text" class="validate[required]"
                                           id="suppliersPeople" name="suppliersPeople">
                    </td>
                    <!-- <td><font color="red" style="margin-right:10px">*</font>承运地址:</td>
                    <td class="td2"><input type="text"
                        class="validate[required]" id="num" name="suppliersAddress" >
                    </td> -->
                </tr>
                <tr>
                    <td><font color="red" style="margin-right:10px">*</font>终到位置:</td>
                    <td class="td2" colspan="3" style="height:40px"><input
                            id="province"> <input type="hidden"
                                                  name="driver_provincename" id="dprovince"></td>
                </tr>
                <tr>

                    <td><font color="red" style="margin-right:10px">*</font>联系人:</td>
                    <td class="td2"><input type="text" class="validate[required]"
                                           id="" name="suppliersPerson">
                    </td>

                    <td><font color="red" style="margin-right:10px">*</font>电话:</td>
                    <td class="td2"><input type="text" class="validate[required]"
                                           id="num" id="" name="suppliersPhone">
                    </td>
                </tr>
                <tr>
                    <td><font color="red" style="margin-right:10px"></font>手机品牌:</td>
                    <td class="td2"><input type="text" class="" id=""
                                           name="phone_brand"></td>

                    <td><font color="red" style="margin-right:10px"></font>手机型号:</td>
                    <td class="td2"><input type="text" class="" id=""
                                           name="phone_model"></td>
                </tr>
                <tr>
                    <td>车辆类型:</td>
                    <td class="td2"><input id="carType" type="text"
                                           name="car_type"></td>

                    <td><font color="red" style="margin-right:10px"></font>车牌号:</td>
                    <td class="td2"><input type="text"
                                           class="validate[minSize[7],maxSize[7],custom[carnumber]]" id=""
                                           name="suppliers_carnumber"></td>
                </tr>
                <tr>
                    <td>服务范围:</td>
                    <td class="td2"><input type="text" class="" id=""
                                           name="suppliersService">
                    </td>

                    <td><font color="red" style="margin-right:10px">*</font>合同期限:</td>
                    <td class="td2"><input type="text" class="validate[required]"
                                           id="" name="suppliersDeadline">
                    </td>
                </tr>
                <tr>
                    <td><font color="red" style="margin-right:10px">*</font>开始时间:</td>
                    <td class="td2"><input id="departure_timeId" type="text"
                                           name="suppliersStartDate" class="Wdate validate[required]"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'twoer'})"/>
                    </td>

                    <td><font color="red" style="margin-right:10px">*</font>结束时间:</td>
                    <td class="td2"><input id="departure_timeId" type="text"
                                           name="suppliersEndDate" class="Wdate validate[required]"
                                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'twoer'})"/>
                    </td>
                </tr>
                <tr>
                    <td><label>资质图片：</label> <input type="button"
                                                    onclick="shangShow()" class="botto" value="添加"/>
                    </td>
                    <td class="td2" colspan="3">
                        <table class="tableclass">
                            <tr>
                                <td id="xian"></td>
                            </tr>
                            <tr id="shang">
                                <td>
                                    <div id="wrapper">
                                        <div id="container">
                                            <div id="uploader">
                                                <div class="queueList">
                                                    <div id="dndArea" class="placeholder">
                                                        <div id="filePicker"></div>
                                                        <p>请将照片拖到这里，单次最多可选5张</p>
                                                    </div>
                                                </div>
                                                <div class="statusBar" style="display:none;">
                                                    <div class="progress">
                                                        <span class="text">0%</span> <span class="percentage"></span>
                                                    </div>
                                                    <div class="info"></div>
                                                    <div class="btns">
                                                        <div id="filePicker2"></div>
                                                        <div class="uploadBtn">开始上传</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <script type="text/javascript"
                                            src="./webuploader/webuploader.js"></script>
                                    <script
                                            type="text/javascript" src="./webuploader/upload.js"></script>
                                </td>
                            </tr>
                        </table> <!-- <script type="text/javascript" src="./webuploader/jquery.js"></script> -->
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
</div>
<!-- <div region="east" border="true" split="true" title="资质图片" class="cs-west" style="width:240px;" id="westId">
    <div id="nav" class="easyui-accordion" fit="true"  border="false" >
     导航内容

      </div> -->

</body>
</html>