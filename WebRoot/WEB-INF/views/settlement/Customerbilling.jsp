<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>客户结算</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">

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
	var heji = 0;
	$(function() {
		grid = $("#dg")
				.datagrid(
						{
							url : 'shipOrder.do?method=getShipOrderBySendMechanism',
							border : false,
							rownumbers : true,
							fit : true,
							/* sortName:'transport_pay,check_time,shipping_order',
							sortOrder:'desc', */

							selectOnCheck : true,
							checkOnSelect : true,
							multiSort : true,
							rowStyler : function(index, row) {
								if (row.customer_settlement_state == 1) {
									return 'color:blue;';
								}
							},
							/* 	remoteSort:true, */
							onDblClickRow : function(rowIndex, rowData) {
								var custom_code = rowData.send_mechanism;
								var min_time = rowData.min_time;
								var max_time = rowData.max_time;
								dialog = parent.jy
										.modalDialog({
											title : '客户结算详细',
											url : 'shipOrder.do?method=ShipOrderBySendMechanism&custom_code='
													+ custom_code
													+ '&send_time='
													+ min_time
													+ '&end_time=' + max_time,
											width : 1200,
											heght : 700,
											buttons : [ {
												handler : function() {
													dialog.find('iframe')
															.get(0).contentWindow
															.submitFormEdit(
																	dialog,
																	grid,
																	parent.$);
												}
											} ]
										});
							},
							columns : [ [ {
								field : 'ck',
								checkbox : true
							}, {
								field : 'custom_code',
								title : '客户编号',
								width : 60,
								align : 'center'
							}, {
								field : 'send_mechanism',
								title : '受理机构',
								width : 120,
								align : 'center'
							}, {
								field : 'custom_name',
								title : '发货客户名称',
								width : 140,
								align : 'center'
							}, {
								field : 'num',
								title : '运单总数',
								width : 140,
								align : 'center'
							}, {
								field : 'min_time',
								title : '最早起运时间',
								width : 80,
								align : 'center',
								sortable : 'true'
							}, {
								field : 'max_time',
								title : '最晚起运时间',
								width : 80,
								align : 'center',
								sortable : 'true'
							},
							//{field : 'goods_name',title : '货物名称',width : 100,align:'center'},
							{
								field : 'goods_num',
								title : '物品总件数',
								width : 100,
								align : 'center'
							}, {
								field : 'goods_weight',
								title : '总重量',
								width : 100,
								align : 'center'
							}, {
								field : 'goods_vol',
								title : '总体积',
								width : 100,
								align : 'center'
							},
							//{field : 'transport_pay',title : '总运费',width : 100,align:'center'},
							{
								field : 'trade_agency',
								title : '代收货款',
								width : 100,
								align : 'center'
							}, {
								field : 'deliver_fee',
								title : '配送费',
								width : 100,
								align : 'center'
							}, {
								field : 'upstairs_fee',
								title : '上楼费',
								width : 100,
								align : 'center'
							}, {
								field : 'added_fee',
								title : '附加费',
								width : 100,
								align : 'center'
							}, {
								field : 'other_fee',
								title : '其他费用',
								width : 100,
								align : 'center'
							},
							//{field : 'remarks',title : '备注',width : 100,align:'center'},
							/*{field : 'heji',title : '合计',width : 140,align:'center',
							styler: function(value,row,index){
							return 'background-color:yellow;font-weight:bold;color:blue';
							},
							formatter: function(value,row,index){
							var transport_pay=0;var trade_agency=0;var deliver_fee=0; var upstairs_fee=0;var added_fee=0;var other_fee=0;
							if(row.transport_pay!=null&&row.transport_pay!=""&&row.transport_pay!=undefined){
							transport_pay=row.transport_pay*1;
							}
							if(row.trade_agency!=null&&row.trade_agency!=""&&row.trade_agency!=undefined){
							trade_agency=row.trade_agency*1;
							}
							if(row.deliver_fee!=null&&row.deliver_fee!=""&&row.deliver_fee!=undefined){
							deliver_fee=row.deliver_fee*1;
							}
							if(row.upstairs_fee!=null&&row.upstairs_fee!=""&&row.upstairs_fee!=undefined){
							upstairs_fee=row.upstairs_fee*1;
							}
							if(row.added_fee!=null&&row.added_fee!=""&&row.added_fee!=undefined){
							added_fee=row.added_fee*1;
							}
							if(row.other_fee!=null&&row.other_fee!=""&&row.other_fee!=undefined){
							other_fee=row.other_fee*1;
							}		if(index!=10){
									value = transport_pay+ trade_agency+ deliver_fee+ upstairs_fee+ added_fee+ other_fee;
									value = Number(value).toFixed(2);
									if(value!=null&&value!=""&&value!=undefined){
									heji= heji*1+value*1;
									}
									}else{
									value=Number(heji).toFixed(2);
									}
									return value;
								} 	}
							//{field : 'end_address',title : '终到位置',width : 120,align:'center'},
							//{field : 'end_mechanism',title : '终到机构',width : 120,align:'center'},
							//{field : 'custom_code',title : '客户编号',width : 100,align:'center'},
							
							{field : 'custom_contact',title : '发货客户联系人',width : 100,align:'center'},
							{field : 'custom_tel',title : '发货客户联系人',width : 100,align:'center'},
							{field : 'receipt',title : '收货客户名称',width : 100,align:'center'},
							{field : 'receipt_name',title : '到货联系人',width : 100,align:'center'},
							{field : 'receipt_tel',title : '到货联系电话',width : 100,align:'center'},
							{field : 'is_recept',title : '回单份数',width : 100,align:'center'},
							{field : 'updatetime',title : '货运单票导入时间',width : 140,align:'center',
							formatter: function(value,row,index){
									if(value!=undefined){
										value=value.substr(0,19);
									}
									return value;
								} 	
							},
							{field : 'shipping_order',title : '制单人',width : 100,align:'center'} */

							] ],
							/* rowStyler : function(index, row) {
											//合计行颜色
											if (row.shiping_order_num == '合计') {
												return 'background-color:yellow;font-weight:bold;color:blue';
											}
											// if (row.shiping_order_num != '合计'
											//		&& row.write_off1 != 3) {
											//	return 'color:blue;';
											//} 

										}, 
										onLoadSuccess : function(data) {
											//添加合计行
											$('#dg').datagrid('appendRow', {
												shiping_order_num : '合计',
												transport_pay : compute("transport_pay"),
												trade_agency : compute("trade_agency"),
												deliver_fee : compute("deliver_fee"),
												upstairs_fee : compute("upstairs_fee"),
												added_fee : compute("added_fee"),
												other_fee : compute("other_fee")
											});
											//合并单元格
											var merges2 = [ {
												field : 'shiping_order_num',
												index : data.rows.length - 1,
												colspan : 7,
												rowspan : 1
											}
											// , {
												//field : 'pay_type',
												//index : data.rows.length - 1,
												//colspan : 5,
												//rowspan : 1
											//} ]
											for ( var i = 0; i < merges2.length; i++)
												$('#dg').datagrid('mergeCells', {
													index : merges2[i].index,
													field : merges2[i].field,
													colspan : merges2[i].colspan
													//rowspan : merges2[i].rowspan
												});
										},*/
							pagination : true,//分页
							pageSize : 10,
							pageList : [ 5, 10, 15, 20 ],
							toolbar : '#tb'
						});
		//	回车查询
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchCar_owner();
			}
		};
		/*	//能更多条件
		var btnMoreSearch = $("#btnMoreSearch");
		btnMoreSearch.click(function() {
		 if (btnMoreSearch.attr("state") == "close") {
		 //主要代码 
			$('.easyui-layout').layout('panel', 'north').panel('resize',{height:92});
			$('.easyui-layout').layout('resize');
			btnMoreSearch.text("收起条件");
			btnMoreSearch.attr("state", "open");
			$("#searchInfoId").css("display","block");
				$("#phone").val("");
				$("#send_address").val("");
				$("#end_address").val("");
				$("#perpole").val("");
				$("#pay_state").combobox("setValue","");
		} else {
			$('.easyui-layout').layout('panel', 'north').panel('resize',{height:60});
			$('.easyui-layout').layout('resize');
			btnMoreSearch.text("更多条件");
			btnMoreSearch.attr("state", "close");
			$("#searchInfoId").css("display","none");
		}
		});
			$('#cc3').combotree({    
			url: "depn.do?method=getTree&&id=",
			width: 150,
			onBeforeLoad: function(node, param) {
			
		          if (node == null) {
		              $('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
		          } else {
		          
		              $('#cc3').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=" + node.id;//加载下层节点
		          }
		        
		         }     
		});*/

		//select框
		$("#cn").select2({ //自动完成
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
		});
	});
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
		return Number(total).toFixed(2);
	}
	//查询
	function searchCar_owner() {
		var startDate = document.getElementById("startDate").value;
		var endDate = document.getElementById("endDate").value;

		$("#dg").datagrid('load', {
			send_time : startDate,
			end_time : endDate,
			//shiping_order_num:$.trim($("#ddId").val()),
			//send_mechanism:$.trim($("#send_mechanism").val()),//受理机构
			//end_address:$.trim($("#end_address").val()),//终到位置
			custom_name : $.trim($("#custom_name").val()),//发货客户名称
		//receipt_name:$.trim($("#receipt_name").val()),//到货联系人
		//receipt_tel:$.trim($("#receipt_tel").val())//到货联系人电话
		});

	}
	//导出
	function putoutfile() {
		var row = $("#dg").datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert("用户管理", "请选择一行信息", "info");
			return;
		}

		/*var allRows2 = $("#dg").datagrid("getColumnFields");
			var colName=[];
			var fieldName=[];
			for(i=1;i<allRows2.length;i++)
			{
				var col = $('#dg').datagrid( "getColumnOption" , allRows2[i] );
				if(col.field!="check_type"&&col.field!="send_type"&&col.field!="shipping_order_state"){
					colName.push(col.title);//把title列名到数组里去 
					fieldName.push(col.field); //把field列属性名到数组里去 
				} 
			} 
			$("#fieldName").val(fieldName.join(","));
			 $("#headtitle").val(colName.join(","));*/
		 var s = JSON.stringify(row);
		$("#custom_name1").val(s);
		var outputform = $("#outputform");
		outputform.submit();
	}
	function ow(owurl) {
		var tmp = window.open("about:blank", "", "fullscreen=1");
		tmp.moveTo(0, 0);
		tmp.resizeTo(screen.width + 20, screen.height);
		tmp.focus();
		tmp.location = owurl;
	}

	//重置
	function reset() {
		$("#startDate").val("");
		$("#endDate").val("");
		$("#cn").select2("val", "");
		$("#custom_name").val("");
	}
	//结算
	function fn_pay() {
		var row = $("#dg").datagrid('getSelections');

		if (row.length > 0) {

			var custom_code = row[0].send_mechanism;
			var max_time = row[0].max_time;
			var min_time = row[0].min_time;
			var falg = row[0].customer_settlement_state;

			if (falg == 1) {
				$.messager.alert("客户结算", "您选择的数据已经全部结算完成", "info");
			} else {
				$.messager.confirm('确认', '您确定要结算选中的记录吗？', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							async : false,
							url : "settlement.do?method=custom_pay",
							data : {
								custom_code : custom_code,
								min_time : min_time,
								max_time : max_time
							},
							success : function(data) {
								if (data) {
									$.messager.alert("客户结算", "结算成功", "info",
											function(r) {
												$('#dg').datagrid('reload');
											});
									//$("#dg").datagrid('reload');
								} else {
									$.messager.alert("客户结算", "结算失败", "info");
								}
							}

						});
					}
				});
			}

		} else {
			$.messager.alert("客户结算", "请选择一行信息", "info");
		}
	}
	function searchMsg() {
		var row = $("#dg").datagrid("getSelections");
		if (row.length == 1) {
			var custom_code = row[0].send_mechanism;
			var min_time = row[0].min_time;
			var max_time = row[0].max_time;
			dialog = parent.jy
					.modalDialog({
						title : '客户结算详细',
						url : 'shipOrder.do?method=ShipOrderBySendMechanism&custom_code='
								+ custom_code
								+ '&send_time='
								+ min_time
								+ '&end_time=' + max_time,
						width : 1200,
						heght : 700,
						buttons : [ {
							handler : function() {
								dialog.find('iframe').get(0).contentWindow
										.submitFormEdit(dialog, grid, parent.$);
							}
						} ]
					});
		} else {
			$.messager.alert("运单录入", "请选择一行信息", "info");
		}
	}
</script>
<body class="easyui-layout">
	<form action="settlement.do" id="outputform" method="post">
		<div region="north" title="检索" class="st-north" border="false"
			height="60px" collapsible="false">
			<lable>起运日期:</lable>
			<input id="startDate" class="Wdate" readonly="readonly"
				style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})" /> 至<input
				id="endDate" class="Wdate" readonly="readonly"
				style="width:150px;height:22px; "
				onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})">
			<!--  <lable class="divlabel">货运编号:</lable>
	    	<input type="text" class="search-text" id="ddId"  style="width:100px"  >
	    	<lable>受理机构:</lable>
			<input type="text" class="search-text" id="send_mechanism" >
			<lable >终到位置:</lable>
	    	<input type="text"  class="search-text"style="width:150px"   id="end_address" >
	    	 -->

			<!-- <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="margin-top:16px;" href="javascript:void(0)" state="close">更多条件</a>
	    	 <div id="searchInfoId" style="display: none"> -->

			<label class="divlabel" style="padding-left: 0px;">受理机构:</label> <input
				type="hidden" id="cn" style="width: 150px;" /> <input type="hidden"
				id="custom_name" name="custom_name"> <a
				class="easyui-linkbutton" onclick="searchCar_owner()"
				data-options="iconCls:'icon-search'">查询</a>

			<!--  <label class="divlabel">到货联系人:</label>
	    	<input type="text"  class="search-text"style="width:150px"   id="receipt_name" >
	    	
	    	<label class="divlabel">到货联系人电话:</label>
	    	<input type="text"  class="search-text"style="width:150px"   id="receipt_tel" >
			</div>-->
		</div>
		<div region="center">
			<table id="dg" title="订单信息"></table>
		</div>
		<div id="tb">
			<!-- <a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput'
				onclick='pay()' data-options="iconCls:'icon-edit',plain:true">结算</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-remove',plain:true"
				onclick="searchMsg()">运单信息</a> -->
		</div>
		<input type="hidden" name="method" value="outShipOrder"> <input
			type="hidden" id="min_time" name="min_time"> <input
			type="hidden" id="max_time" name="max_time"> <input
			type="hidden" id="custom_name1" name="custom_name1">
	</form>
</body>
</html>