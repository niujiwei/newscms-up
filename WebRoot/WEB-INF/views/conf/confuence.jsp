<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML>
<html>
<head>


<title>添加订单信息</title>
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
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<style type="text/css">
li {
	float: left;
}
</style>
</head>
<script type="text/javascript">
	$(function() {
		$("#Regform").validationEngine('attach', {
			promptPosition : 'topRight:-15,0'
		});
		grid = $("#dg").datagrid({
			url : 'conf.do?method=getConfluence',
			border : false,
			fit : false,
			height : 500,
			rownumbers : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			multiSort : true,
			onSelect : function(index, data) {

				$("#user").datagrid('load', {
					subject : data.project,
				/* end_date :endDate,
					name:$.trim($("#ddId").val()),//订单编号
					phone_number:$.trim($("#phone").val()),//收货人
					institution:$.trim($("#cc3").combotree("getValue")),
					type:$.trim($("#address").val()), */
				});

			},
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'happen',
				title : '本期发生',
				width : 60,
				align : 'center',
				  styler: function (value, row, index) {
              return 'background-color:blue;color:white';
           }

			}, {
				field : 'fee_date',
				title : '时间',
				width : 100,
				align : 'center'
			}, {
				field : 'direction',
				title : '方向',
				width : 60,
				align : 'center'
			}, {
				field : 'source',
				title : '来源',
				width : 60,
				align : 'center'
			}, {
				field : 'project',
				title : '项目',
				width : 60,
				align : 'center'
			}, {
				field : 'other_shouru',
				title : '其他收入',
				width : 60,
				align : 'center'
			}, {
				field : 'other_zhichu',
				title : '其他支出',
				width : 80,
				align : 'center'
			}, {
				field : 'handle',
				title : '处理库品',
				width : 60,
				align : 'center'
			}, 

			/* 	{field : 'shipping_order_state',title : '状态',width : 50,align:'center',
					formatter:function(val,row,index){
						if(val==0){
							return "受理";
						}else if(val==1){
							return "在途";
						}else if(val==2){
						return "到达";
						}else if(val==3){
						return "配送";
						}else if(val==4){
						return "签收";
						}
					}	
				}, */
			/* {field : 'order_state',title : '订单状态',width : 90,align:'center',hidden:'true'}, 
			{field : 'checktype',title : '发货方式',width : 90,align:'center',hidden:'true'},
			{field : 'sendtype',title : '提货方式',width : 90,align:'center',hidden:'true'},
			{field : 'shipping_order',title : '制单人',width : 90,align:'center',sortable:'true'},
			{field : 'check_time',title : '创建时间',width : 100,align:'center',sortable:'true'},
			{field : 'updateDate',title : '更新日期',width : 80,align:'center'}, */
			] ],
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});

		grid = $("#user").datagrid({
			url : 'conf.do?method=getMingxi',
			border : false,
			fit : false,
			height : 500,

			rownumbers : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			multiSort : true,

			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'happen_date',
				title : '发生日期',
				width : 100,
				align : 'center'
			}, {
				field : 'subject',
				title : '科目',
				width : 100,
				align : 'center'
			}, {
				field : 'subject_two',
				title : '二级科目',
				width : 100,
				align : 'center'
			}, {
				field : 'subject_three',
				title : '三级科目',
				width : 100,
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
				field : 'money',
				title : '金额',
				width : 100,
				align : 'center'
			}, {
				field : 'remarks',
				title : '备注',
				width : 100,
				align : 'center'
			}, {
				field : 'shiping_order_num',
				title : '运单号',
				width : 100,
				align : 'center'
			}, {
				field : 'customer_name',
				title : '客户名',
				width : 100,
				align : 'center'
			}, {
				field : 'consignee',
				title : '收货人',
				width : 100,
				align : 'center'
			}

			/* 	{field : 'shipping_order_state',title : '状态',width : 50,align:'center',
					formatter:function(val,row,index){
						if(val==0){
							return "受理";
						}else if(val==1){
							return "在途";
						}else if(val==2){
						return "到达";
						}else if(val==3){
						return "配送";
						}else if(val==4){
						return "签收";
						}
					}	
				}, */
			/* {field : 'order_state',title : '订单状态',width : 90,align:'center',hidden:'true'}, 
			{field : 'checktype',title : '发货方式',width : 90,align:'center',hidden:'true'},
			{field : 'sendtype',title : '提货方式',width : 90,align:'center',hidden:'true'},
			{field : 'shipping_order',title : '制单人',width : 90,align:'center',sortable:'true'},
			{field : 'check_time',title : '创建时间',width : 100,align:'center',sortable:'true'},
			{field : 'updateDate',title : '更新日期',width : 80,align:'center'}, */
			] ],
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});
		$("#carid").select2({
			placeholder : "请输入车牌号", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "incomedp.do?method=getPlateNumber",
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
		}).on("select2-selecting", function(f) {
			$("#car").val(f.object.id);
			$("#carnum").val(f.object.text);
		});
	});
	function searchOrder() {
		$("#dg").datagrid('load', {
			direction:$.trim($("#direction").val()),
			fee_date:$.trim($("#fee_date").val()),
			dd:$.trim($("#dd").val()),
		
		});
	}
document.onkeydown = function(event) {
  				e = event ? event : (window.event ? window.event : null);
  				if (e.keyCode == 13) {
  					//执行的方法 
  					searchCar_owner();
  				}
  			};	

</script>
<body class="easyui-layout">
	<div region="west" border="true" split="true" width="500px">
		<div style="padding:10px;">
			<input type="hidden" name="mingxi_id"> 
			<input type="hidden"name="id"> 
	<label>方向：</label>
	<input class="search-text" id="direction" style="width:100px"/>
	 <lable>发车日期:</lable>
			<input  id="fee_date"  class="Wdate"  readonly="readonly" style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			-
			<input id="dd"  class="Wdate"  readonly="readonly" style="width:50px;height:22px;" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
	    	 
			<a class="easyui-linkbutton"
				onclick="searchOrder()" data-options="iconCls:'icon-search'">查询</a>
		</div>

		<table id="dg" height="340px" title="单票信息"></table>
		<!-- 	<div id="tb">
    		<input type="hidden" name="tatal_id">
			<input type="hidden" name="shiping_order_id">
    		 <a class="easyui-linkbutton" onclick="xiangxiseach()" data-options="iconCls:'icon-search'" >详细查询</a>	
    		</div>	 -->
	</div>
	<div region="center">
		<table id="user">
		</table>


	</div>


</body>
</html>
