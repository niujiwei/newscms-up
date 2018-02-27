<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>


<title>添加信息</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
        <link rel="stylesheet" type="text/css" href="styles.css">
        -->
<script type="text/javascript" src="./webuploader/jquery.js"></script>
<script type="text/javascript" src="./webuploader/webuploader.js"></script>
<script type="text/javascript" src="./webuploader/upload.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<%-- <jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include> --%>
<link rel="stylesheet"
	href="./ValidationEngine/css/validationEngine.jquery.css">
<script src="./ValidationEngine/js/jquery.validationEngine-zh_CN.js"></script>
<script src="./ValidationEngine/js/jquery.validationEngine.js"></script>

<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>


<link rel="stylesheet" type="text/css"
	href="./webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="./webuploader/style.css" />

<!-- 引入CSS
    <link rel="stylesheet" type="text/css" href="./webuploader/webuploader.css">
    
    引入JS
    <script type="text/javascript" src="./webuploader/webuploader.js"></script> -->

<!--SWF在初始化的时候指定，在后面将展示-->
<!-- <link rel="stylesheet" type="text/css" href="./webuploader/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="./examples/image-upload/style.css" />	 -->
</head>
<script type="text/javascript">
	var oneid = 0;
	var twoid = 0;
	var threeid = 0;
	
	$(function() {
		$("#uploadForm").validationEngine('attach', {
			promptPosition : 'topRight:-15,0'
		});
		var $target = $('input,textarea,select');
		$target.bind('keydown', function(e) {
			var key = e.which;
			if (key == 13) {
				e.preventDefault();
				var nxtIdx = $target.index(this) + 1;
				$target.eq(nxtIdx).focus();
			}
		});
		$('#province').combotree(
				{
					url : 'driver.do?method=getNewFinalPositionAddress',
					valueField : 'id',
					textField : 'text',
					multiple : true,
					width : 220,
					editable : false,
					
				});
		/* $('#city')
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
						}); */

		/* $('#county').combobox({
		    url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='+twoid,
		    valueField : 'id',
		    width:120,
		    textField : 'text',
		    editable:false

		}); */
		/* $('#county').combotree({
							multiple : true,
							url : 'driver.do?method=getNewFinalpositionCounty&&citye_parent_id='
									+ twoid,
							onCheck:function(node, checked){
							    if(checked){
							      county_id.push(node);
							    }else{
							      var index = county_id.indexOf(node);
							      county_id.splice(index,1);
							    }
							    console.info(county_id);
							   
							   
							}

		
		});
 */
		/* 	    $('#province').combobox({
		 url : 'suppliers.do?method=getSuppiadd',
		 valueField : 'id',
		 textField : 'text',
		 width:154,
		 height:20,
		 editable:false,
		 onSelect:function(record){
		 //oneid = record.oneid;
		 //	$('#province').combobox("clear");
		 } 
		 }); */
		$('#suppliers_customer').combotree({
			multiple : true,
			url : 'shipOrder.do?method=getNewCustomName',

			onLoadSuccess : function(node, data) {
				formatComboTreeValue();
			},
			onCheck : function(node, checked) {
				formatComboTreeValue();
			},
			onClick : function(node, checked) {
				formatComboTreeValue();
			}
		});
		//去除根节点在选中的显示
		function formatComboTreeValue() {

			var value = $('#suppliers_customer').combotree('getText');
			if (value.indexOf("全选,") >= 0) {
				var endIndex = value.indexOf(',');
				value = value.substring(endIndex + 1);
				$('#suppliers_customer').combotree('setText', value);
			}
		}
		;
		/* 	$('#suppliers_customer')
					.combobox(
							{
								multiple : true,
								url : 'shipOrder.do?method=getCustomName',
								valueField : 'id',
								textField : 'name',
								formatter : function(row) {
									var opts = $(this).combobox("options");
									return "<input type='checkbox' class='combobox-checkbox'>"
											+ row[opts.textField];
								},
								onShowPanel : function() {
									var opts = $(this).combobox("options");
									target = this;
									var values = $(target).combobox("getValues");
									$.map(values, function(value) {
										var children = $(target).combobox("panel")
												.children();
										$.each(children, function(index, obj) {
											if (value == obj.getAttribute("value")
													&& obj.children
													&& obj.children.length > 0) {
												obj.children[0].checked = true;
											}
										});
									});
								},
								onLoadSuccess : function() {
									var opts = $(this).combobox("options");
									var target = this;
									var values = $(target).combobox("getValues");
									$.map(values, function(value) {
										var children = $(target).combobox("panel")
												.children();
										$.each(children, function(index, obj) {
											if (value == obj.getAttribute("value")
													&& obj.children
													&& obj.children.length > 0) {
												obj.children[0].checked = true;
											}
										});
									});
								},

								onSelect : function(row) {
									var opts = $(this).combobox("options");
									var objCom = null;
									var children = $(this).combobox("panel")
											.children();
									$.each(children, function(index, obj) {
										if (row[opts.valueField] == obj
												.getAttribute("value")) {
											objCom = obj;
										}
									});
									if (objCom != null && objCom.children
											&& objCom.children.length > 0) {
										objCom.children[0].checked = true;
									}
								},

								onUnselect : function(row) {
									var opts = $(this).combobox("options");
									var objCom = null;
									var children = $(this).combobox("panel")
											.children();
									$.each(children, function(index, obj) {
										if (row[opts.valueField] == obj
												.getAttribute("value")) {
											objCom = obj;
										}
									});
									if (objCom != null && objCom.children
											&& objCom.children.length > 0) {
										objCom.children[0].checked = false;
									}
								}
							}); */

		/* $("#suppliers_customer").select2({ //自动完成
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
			url : 'suppliers.do?method=carType',
			valueField : 'id',
			textField : 'text'
		});
	});
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		/*  	 var poin = $("#province").combobox('getText');
		 console.info(poin);
		 $("#driver_address").val(poin); */
		 var t = $('#province').combotree('tree');	// 得到树对象
         var n = t.tree('getChecked');	// 得到选择的节点
         var province = JSON.stringify(n);
        
		 $("#dprovince").val(province);
		/* var poin = $("#province").combobox('getText');
		var city = $("#city").combobox('getText');
		var county = $("#county").combotree('getText'); */
		var cartype = $("#carType").combobox('getText');
		$("#suppliers_cartype").val(cartype);
		//$("#driver_address").val(poin + city + county);
		var suppliers = $("#suppliers_customer").combotree('getText');
		$(".uploadBtn").click();
		//可提交
		if ($("#uploadForm").validationEngine('validate') 
				&& suppliers != "") {
				
			$pjq.messager.confirm('供应商新增', '确定要新增吗?', function(r) {
				if (r) {
					$
							.ajax({
								type : "POST",
								url : 'suppliers.do?method=addProve',
								data : $('#uploadForm').serialize(),
								success : function(msg) {
									if (msg.flag) {
										$pjq.messager.alert('供应商新增', '供应商添加成功',
												'info');
										$grid.datagrid('reload');
										$dialog.dialog('close');
									}
									;
								}
							});
				}
			});
		} else {
			$pjq.messager.alert('新增供应商', '必填信息不可为空', 'info');
		}
	};
	function addRow() {

	}
</script>
<body class="easyui-layout">
	<table id="dg" title="供应商信息"></table>
	<div region="center">
		<form action="#" method="post" id="uploadForm">
			<input type="hidden" name="suppliersId"> <input type="hidden"
				id="driver_address" name="suppliersAddress"> <input
				type="hidden" id="suppliers_cartype" name="suppliers_cartype">
			<fieldset>
				<table class="tableclass">
					<tr>
						<th colspan="4">基本信息</th>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>发货客户名称:</td>
						<td class="td2" colspan="4"><input type="text"
							style="width: 400px;" id="suppliers_customer"
							name="suppliers_customer"></td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>供应商公司名称:</td>
						<td class="td2"><input type="text" class="validate[required]"
							id="num" name="supplie_company">
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>供应商名称:</td>
						<td class="td2"><input type="text" class="validate[required]"
							id="num" name="suppliersName">
						</td>
						<td><font color="red" style="margin-right:10px">*</font>公司法人:</td>
						<td class="td2"><input type="text" class="validate[required]"
							id="" name="suppliersPeople"> <!-- <font color="red" style="margin-right:10px">*</font>承运地址:</td>
                        <td class="td2">
                         <input id="province"> --> <!-- <input type="text"  class="validate[required]" id="num" name="suppliersAddress" > -->
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>终到位置:</td>

						<td class="td2" colspan="3" style="height:40px"><input
							id="province" >
							<input type="hidden" name ="driver_provincename" id="dprovince">
							 <!-- <input id="city"
							name="driver_city">市 <input id="county"
							name="suppers_county">区</td> -->
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px">*</font>联系人:</td>
						<td class="td2"><input type="text" class="validate[required]"
							id="" name="suppliersPerson">
						</td>

						<td><font color="red" style="margin-right:10px">*</font>电话:</td>
						<td class="td2"><input type="text"
							class="validate[required]" id="num" id=""
							name="suppliersPhone">
						</td>
					</tr>
					<tr>
						<td><font color="red" style="margin-right:10px"></font>手机品牌:</td>
						<td class="td2"><input type="text" class="" id=""
							name="phone_brand">
						</td>

						<td><font color="red" style="margin-right:10px"></font>手机型号:</td>
						<td class="td2"><input type="text" class="" id=""
							name="phone_model">
						</td>
					</tr>
					<tr>
						<td>车辆类型:</td>
						<td class="td2"><input id="carType" type="text">
						</td>
						<td><font color="red" style="margin-right:10px"></font>车牌号:</td>
						<td class="td2"><input type="text"
							class="validate[minSize[7],maxSize[7],custom[carnumber]]" id=""
							name="suppliers_carnumber">
						</td>
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
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'twoer'})" />
						</td>
						<td><font color="red" style="margin-right:10px">*</font>结束时间:</td>
						<td class="td2"><input id="departure_timeId" type="text"
							name="suppliersEndDate" class="Wdate validate[required]"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'twoer'})" />
						</td>
					</tr>
					<tr>
						<td>资质图片：</td>
						<td colspan="3">
							<div id="wrapper">
								<div id="container">
									<!--头部，相册选择和格式选择-->

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
							</div> <!--   -->
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</body>
</html>
