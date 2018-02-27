<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>中转费核销</title>
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
<!--命名按钮方法  -->
<style type="text/css">
.subtotal {
	font-weight: bold;
} /*合计单元格样式*/
</style>

</head>
<script type="text/javascript">
	var dialog;
	var grid;
	var editval;
	$(function() {
		grid = $("#dg")
				.datagrid(
						{
							url : 'payable.do?method=getPayable_fee',
							border : false,
							rownumbers : true,
							fit : true,
							singleSelect : false,
							selectOnCheck : true,
							checkOnSelect : true,
							multiSort : true,
							rowStyler : function(index, row) {
								if (row.write_off != 3) {
									return 'color:blue;';
								}
							},
							columns : [ [
									{
										field : 'ck',
										checkbox : true
									},
									{
										field : 'shiping_order_num',
										title : '订单编号',
										width : 80,
										align : 'center'
									},

									{
										field : 'send_station',
										title : '发站',
										width : 80,
										align : 'center'
									},
									{
										field : 'end_address',
										title : '到达地',
										width : 80,
										align : 'center'
									},
									{
										field : 'receipt',
										title : '收货人',
										width : 80,
										align : 'center'
									},
									{
										field : 'goods_name',
										title : '品名',
										width : 80,
										align : 'center'
									},
									{
										field : 'goods_num',
										title : '件数',
										width : 80,
										align : 'center'
									},
									{
										field : 'goods_weight',
										title : '重量',
										width : 80,
										align : 'center'
									},
									{
										field : 'goods_vol',
										title : '体积',
										width : 80,
										align : 'center'
									},
									{
										field : 'send_type',
										title : '交接方式',
										width : 80,
										align : 'center',
										formatter : function(val, row, index) {
											if (val == 0) {
												return "自提";
											} else if (val == 1) {
												return "送货";
											}
										}
									},
									
								/*	{
										field : 'paid_chayi',
										title : '实收',
										width : 80,
										align : 'center',
										editor : {
											type : 'numberbox',
											options : {
												precision : 2,
												validType : 'paid_chayi'
											}
										},
										formatter : function(val, row, index) {

											if (val == null || val == "") {
												return row.paid1;
											} else {
												return row.paid_chayi;
											}
										}
									}, */
									/* {
										field : 'paid_chayi1',
										title : '差异',
										width : 80,
										align : 'center',
										formatter : function(val, row, index) {
											if (row.paid_chayi == null
													|| row.paid_chayi == "") {

												return row.paid_chayi1;
											} else {
												return Number(Number(row.paid_chayi)
														- Number(row.paid1));
											}

										}
									}, */ {
										field : 'pay_type',
										title : '付款方式',
										width : 80,
										align : 'center',
										formatter : function(val, row, index) {
											if (val == 0) {
												return "回付";
											} else if (val == 1) {
												return "提付";
											} else if (val == 2) {
												return "现付";
											} else if (val == 3) {
												return "货到前付";
											}
										}
									}, {
										field : 'paid',
										title : '应收',
										width : 80,
										align : 'center'
									},{
										field : 'paid_chayi',
										title : '实收',
										width : 80,
										align : 'center',
										editor : {
											type : 'numberbox',
											options : {
												precision : 2,
												validType : 'paid_chayi'
											}
										},
										formatter : function(val, row, index) {

											if (val == null || val == "") {
												return row.paid1;
											} else {
												return row.paid_chayi;
											}
										}
									},
									{
										field : 'tifu_chayi',
										title : '差异',
										width : 80,
										align : 'center',
										formatter : function(val, row, index) {

											if (row.paid_chayi == null || val == "") {
												return val;
											} else {
												return Number(Number(row.paid_chayi)
														- Number(row.paid));
											}
										}
										},
									 {
										field : 'change_remarks',
										title : '中转备注',
										width : 120,
										align : 'center',
										editor : {
											type : 'text',
											options : {

												validType : 'change_remarks'
											}
										}
									},{
								field : 'trade_agency',
								title : '代收款',
								width : 80,
								align : 'center'
							}, {
								field : 'paid1',
								title : '应收',
								width : 80,
								align : 'center',},
								
								{
								field : 'chayi_daifukuan1',
								title : '实收',
								width : 80,
								align : 'center',
								editor : {
									type : 'numberbox',
									options : {
										precision : 2,
										validType : 'chayi_daifukuan1'
									}
								}
							},{
								field : 'chayi_daifukuan',
								title : '差异',
								width : 80,
								align : 'center',
										formatter : function(val, row, index) {

											if (row.chayi_daifukuan1 == null || row.chayi_daifukuan1 == "") {
												return val;
											} else {
												return Number(Number(row.chayi_daifukuan1)
														- Number(row.paid1));
											}
										}}, {
										field : 'create_time',
										title : '协议制作日期',
										width : 80,
										align : 'center'
									}, {
										field : 'company_time',
										title : '中转日期',
										width : 80,
										align : 'center'
									}, {
										field : 'company_name',
										title : '中转公司',
										width : 80,
										align : 'center'
									}, {
										field : 'change_fee',
										title : '应付中转费',
										width : 80,
										align : 'center'
									}, {
										field : 'change_fee1',
										title : '实付中转费',
										width : 80,
										align : 'center',
										editor : {
											type : 'numberbox',
											options : {
												precision : 2,
												validType : 'change_fee1'
											}
										},
										formatter : function(val, row, index) {
											if (val == null) {
												return row.change_fee;
											} else {
												return row.change_fee1;
											}
										}
									},
									{
										field : 'chayi_zhongzhuanfei',
										title : '差异',
										width : 80,
										align : 'center',
										formatter : function(val, row, index) {
											if (row.change_fee1 == null) {
												return val;
											} else {
												return Number(Number(row.change_fee1)
														- Number(row.change_fee));
											}
										}},
									
									 {
										field : 'fee_remarks',
										title : '应付中转费核销备注',
										width : 120,
										align : 'center',
										editor : {
											type : 'text',
											options : {

												validType : 'fee_remarks'
											}
										}
									}, {
										field : 'write_off',
										title : '核销方式',
										width : 80,
										align : 'center',
										formatter : function(val, row, index) {
											if (val == 0) {
												return "打卡";
											} else if (val == 1) {
												return "现金";
											} else if (val == 3) {
												return "未核销";
											}
										}
									}, {
										field : 'fee_date',
										title : '核销日期',
										width : 80,
										align : 'center'
									}, {
										field : 'people_zhong',
										title : '核销人',
										width : 80,
										align : 'center'
									},

							] ],
							onBeforeEdit : function(index, row) { //可编辑之前
								editval = index;
								row.editing = true;
							},
							onAfterEdit : function(index, row) {//编辑关闭后
								$('#dg').datagrid("endEdit", editval);
								$
										.ajax({
											type : "post",
											url : 'payable.do?method=updateShippingOrderz',
											data : row,
											success : function(data) {
												// Play with returned data in JSON format
											},
											error : function(msg) {

											}
										});
								/* $('#dg').datagrid("updateRow", {index:index,row:{gain_total:price}}); */
								row.editing = false;
							},
							//双击事件
							onDblClickRow : function(rowIndex, rowData) {
								var data = $('#dg').datagrid('getData');
								//双击开启编辑行
								if (rowIndex < data.rows.length - 1) {
									if (editval != rowIndex) {
										$('#dg').datagrid("endEdit", editval);
										$('#dg')
												.datagrid("beginEdit", rowIndex);
										editval = rowIndex;
									} else {
										editval = undefined;
										$('#dg').datagrid("endEdit", rowIndex);
									}
								}
							},
							rowStyler : function(index, row) {
								//合计行颜色
								if (row.shiping_order_num == '合计') {
									return 'background-color:yellow;font-weight:bold;color:blue';
								}
								if (row.shiping_order_num != '合计'
										&& row.write_off != 3) {
									return 'color:blue;';
								}
							},
							onLoadSuccess : function(data) {

								//添加合计行
								$('#dg').datagrid('appendRow', {
									shiping_order_num : '合计',
									trade_agency : compute("trade_agency"),
									paid1 : compute("paid1"),
									paid : compute("paid"),
									change_fee : compute("change_fee"),
									change_fee1 : compute("change_fee1"),
									fee_remarks : compute("fee_remarks")
								});
								//合并单元格
								var merges2 = [ {
									field : 'shiping_order_num',
									index : data.rows.length - 1,
									colspan : 9,
									rowspan : 1
								}, {
									field : 'change_remarks',
									index : data.rows.length - 1,
									colspan : 3,
									rowspan : 1
								} ]
								for ( var i = 0; i < merges2.length; i++)
									$('#dg').datagrid('mergeCells', {
										index : merges2[i].index,
										field : merges2[i].field,
										colspan : merges2[i].colspan,
										rowspan : merges2[i].rowspan
									});
							},
							pagination : true,//分页
							pageSize : 10,
							pageList : [ 5, 10, 15, 20 ],
							toolbar : '#tb'
						});
		var btnMoreSearch = $("#btnMoreSearch");
		btnMoreSearch.click(function() {
			if (btnMoreSearch.attr("state") == "close") {
				//主要代码 
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 92
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("收起条件");
				btnMoreSearch.attr("state", "open");
				$("#searchInfoId").css("display", "block");
				$("#phone").val("");
				$("#address").val("");
			} else {
				$('.easyui-layout').layout('panel', 'north').panel('resize', {
					height : 60
				});
				$('.easyui-layout').layout('resize');
				btnMoreSearch.text("更多条件");
				btnMoreSearch.attr("state", "close");
				$("#searchInfoId").css("display", "none");
			}
		});
		$('#cc3')
				.combotree(
						{
							url : "depn.do?method=getTree&&id=",
							width : 150,
							onBeforeLoad : function(node, param) {

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

		$("#carid").select2({
			placeholder : "请输入车牌号", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "income.do?method=IncemCarid",
					data : {
						phones : query.term
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
							;
						});
						query.callback(data);
					}
				});
			}
		});
	});

	function modify() {

		var array = $("#dg").datagrid('getSelections');
		var id = [];
		var a = false;
		for ( var i = 0; i < array.length; i++) {
			id.push(array[i].shiping_order_id); //注意修改你的id列 
			if (array[i].write_off == 3) {
				a = true;
			}
		}
		if (a == true) {
			dialog = parent.jy.modalDialog({
				title : '费用核销',
				url : 'payable.do?method=getEditCompanyPage&pid=' + id,
				width : 500,
				height : 300,
				buttons : [ {
					text : '<input type="button" class="btncss" value="核销"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow
								.submitFormEdit(dialog, grid, parent.$);
					}
				} ]
			});
		} else {
			$.messager.alert("中转费核销", " 请选择正确的信息", "info");
		}
	}
	//累加
	function compute(colName) {
		var rows = $('#dg').datagrid('getRows');
		var total = 0;
		for ( var i = 0; i < rows.length; i++) {

			if (isNaN(parseFloat(rows[i][colName]))) {
				total += 0;
			} else {
				total += parseFloat(rows[i][colName]);
			}
		}
		return total;
	}
	//查询
	function searchCar_owner() {
		$("#dg").datagrid('load', {

			shiping_order_num : $.trim($("#shiping_order_num").val()),
			receipt : $.trim($("#receipt").val()),
			fee_date : $.trim($("#fee_date").val()),
			dd : $.trim($("#dd").val()),
			create_time : $.trim($("#create_time").val()),
			yy : $.trim($("#yy").val()),
			write_off : $.trim($("#write_off").val()),
			send_station : $.trim($("#send_station").val()),
			goods_name : $.trim($("#goods_name").val()),
			goods_num : $.trim($("#goods_num").val()),
			pay_type : $.trim($("#pay_type").val()),
			paid : $.trim($("#paid").val()),
			company_name : $.trim($("#company_name").val()),
			
		});

	}

	//重置
	function reset() {
		$("#shiping_order_num").val("");
		$("#receipt").val("");
		$("#fee_date").val("");
		$("#dd").val("");
		$("#create_time").val("");
		$("#yy").val("");
		$("#write_off").val("");
		$("#send_station").val("");
		$("#goods_name").val("");
		$("#goods_num").val("");
		$("#pay_type").val("");
		$("#paid").val("");
		$("#company_name").val("");
		
	}
	document.onkeydown = function(event) {
  				e = event ? event : (window.event ? window.event : null);
  				if (e.keyCode == 13) {
  					//执行的方法 
  					searchCar_owner();
  				}
  			};	
	
		function del() {
		var array = $("#dg").datagrid('getSelections');
			var id = [];
			var a = false;
		for ( var i = 0; i < array.length; i++) {
			id.push(array[i].shiping_order_id); //注意修改你的id列 
			if (array[i].write_off != 3) {
				a = true;
			}
		}
	
			if (a== true) {
							$.messager.confirm('确认', '您确定要取消核销吗？', function(r) {
					if (r) {
						$.ajax({
							url : 'payable.do?method=editZhongzhuan&pid=' + id,
							type : "POST",
							data : {
								id:id.join(",")
							},
							success : function(data) {
								if (data.flag == false) {
									parent.$.messager.alert("其他费核销", "取消核销成功",
											'info', function(r) {
												$('#dg').datagrid('reload');
											});
								}
							}
						});
					}
				});
			} else {
				$.messager.alert('取消核销', ' 请选择正确的信息', 'info');
			}

		} 
</script>
<body class="easyui-layout">
	<form action="shipOrder.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			collapsible="false">
			<lable class="divlabel">订单编号:</lable>
			<input type="text" class="search-text" id="shiping_order_num"
				style="width:70px">
			<lable class="divlabel">收货人:</lable>
			<input type="text" class="search-text" id="receipt"
				style="width:70px">
			<lable class="divlabel">核销日期:</lable>
			<input id="fee_date" class="Wdate" style="width:50px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
				-
			<input id="dd"  class="Wdate"  readonly="readonly" style="width:50px;height:22px;" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
			<lable class="divlabel">协议制作日期:</lable>
				<input id="create_time" class="Wdate"
					style="width:50px;height:22px; "
					onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
					-
			<input id="yy"  class="Wdate"  readonly="readonly" style="width:50px;height:22px;" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
				<td>核销:</td>
				<td><select id='write_off' name="write_off"
					style="width:50px;height:22px">
						<option></option>
						<option value="0">打卡</option>
						<option value="1">现金</option>
						<option value="3">未核销</option>
				</select></td>
			<a class="easyui-linkbutton" onclick="searchCar_owner()"
				data-options="iconCls:'icon-search'">查询</a> <a id="btnMoreSearch"
				class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
				href="javascript:void(0)" state="close">更多条件</a>
			<div id="searchInfoId" style="display: none">

				
				<lable class="divlabel">发站:</lable>
			<input type="text" class="search-text" id="send_station"
				style="width:90px">
				<lable class="divlabel">物品名称:</lable>
			<input type="text" class="search-text" id="goods_name"
				style="width:90px">
				
				<lable class="divlabel">件数:</lable>
			<input type="text" class="search-text" id="goods_num"
				style="width:90px">
				<td>付费方式:</td>
				<td><select id='pay_type' name="pay_type"
					style="width:50px;height:22px">
						<option></option>
						<option value="0">回付</option>
						<option value="1">提付</option>
						<option value="2">现付</option>
						<option value="3">货到前付</option>
				</select></td>
				<lable class="divlabel">实收:</lable>
			<input type="text" class="search-text" id="paid"
				style="width:90px">
				<lable class="divlabel">中转公司:</lable>
			<input type="text" class="search-text" id="company_name"
				style="width:90px">
			</div>
		</div>
		<div region="center">
			<table id="dg" title="汇总信息"></table>
		</div>
		<div id="tb">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true" onclick="modify()">核销</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true" onclick="del()">取消核销</a>

		</div>
	</form>
</body>
</html>