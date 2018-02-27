<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%


%>
<%
	String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() +"/surmax/"; 
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>协议信息管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="./css/main.css">
	<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
	<script type="text/javascript" src="./js/select2/select2.js"></script>
	<script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>
<!-- 	<script type="text/javascript" src="./js/function.js"></script>命名按钮方法  -->
	
  
  </head>
  <script type="text/javascript">
	  	var dialog;
 		var grid;
  		$(function(){
  			$("#type").combobox("setText","全部");//赋值
  			grid=$("#dg").datagrid({
  			url : 'shipOrder.do?method=getSignShipOrderCA',
  			border : false,
  			rownumbers : true,
  			fit : true,
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			multiSort:true,
  		/* 	remoteSort:true, */
  			columns : [[
					{field : 'ck',checkbox : true},
					{field : 'send_time',title : '发车时间',width : 120,align:'center',sortable:'true'},
					{field : 'send_station',title : '发站',width : 100,align:'center'},
					{field : 'custom_name',title : '发货人',width : 80,align:'center'},
					{field : 'shiping_order_num',title : '订单号',width : 60,align:'center'},
					{field : 'end_address',title : '到站',width : 55,align:'center'},
					{field : 'receipt',title : '收货人',width : 60,align:'center'},
					{field : 'receipt_tel',title : '电话',width : 80,align:'center'},
					{field : 'send_type',title : '交接方式',width : 70,align:'center',
					formatter:function(val,row,index){
							if(val==0){
								return "自提";
							}else if(val==1){
							return "送货";
							}
						}
					},
					{field : 'goods_name',title : '品名',width : 60,align:'center'},
					{field : 'goods_packing',title : '包装',width : 60,align:'center'},
					{field : 'goods_num',title : '件数',width : 60,align:'center'},
					{field : 'goods_weight',title : '重量',width : 60,align:'center'},
					{field : 'goods_vol',title : '体积',width : 60,align:'center'},
				/* 	{field : 'car_number',title : '车牌号',width : 70,align:'center'}, */
					{field : 'receipt_address',title : '送货地址',width : 100,align:'center'},
					{field : 'trade_agency',title : '代收款',width : 70,align:'center'},
					{field : 'transport_pay',title : '运费总额',width : 70,align:'center'/* sortable: true */},
					{field : 'pay_type',title : '付款方式',width : 50,align:'center',
						formatter:function(val,row,index){
							if(val==0){
								return "回付";
							}else if(val==1){
								return "提付";
							}else if(val==2){
							return "现付";
							}else if(val==3){
							return "货到前付";
							}
						}	
					}, 
					{field : 'shipping_order_state',title : '状态',width : 50,align:'center',
						formatter:function(val,row,index){
							if(val==0){
								return "未出库";
							}else if(val==1){
								return "配送中";
							}else if(val==2){
								return "转运中";
							}else if(val==4){
								return "已签收";
							}else if(val=3){
								return "已到达";
							}
						}	
					},  
					{field : 'is_recept',title : '回单',width : 70,align:'center'},
					{field : 'remarks',title : '备注',width : 90,align:'center',sortable:'true'},
					{field : 'trunk_fee',title : '应收中转费',width : 80,align:'center',sortable:'true'},
					{field : 'take_fee',title : '应收送货费',width : 80,align:'center',sortable:'true'},
					{field : 'adorn_fee',title : '应收其他费',width : 80,align:'center',sortable:'true'},
					{field : 'remarks_fee',title : '应收备注',width : 90,align:'center',sortable:'true'}  				
  			]],
  			onSelect:function(index,data){
				var selectuser=$("#selectuser").text();
				var selectuserid=$("#selectuserid").text();
				if(selectuser.indexOf(data.shiping_order_num)!=-1){
					$.messager.alert("警告","不可以选择重复运单",'info');
					return;
				}
				if(selectuser=="请选择"){
					$("#selectuser").text("");
					$("#selectuserid").text("");
				}
				if($("#selectuser").text()!=""){
					$("#selectuser").append(",");
					$("#selectuserid").append(",");
				}
				if(data.shiping_order_id!=null&&data.shiping_order_id!=""){
					if($("#channelId").text()!=""){
						$("#channelId").append(",");
					}
					$("#channelId").append(data.shiping_order_id);
				}
				$("#selectuser").append(data.shiping_order_num);
				$("#selectuserid").append(data.shiping_order_id);
			/* 	$("#cnid").val($("#channelId").text());
				$("#message_touserid").val($("#selectuserid").text());
				$("#message_tousername").val($("#selectuser").text()); */
				$('#dg').datagrid('deleteRow', index);
			},
  			pagination:true,//分页
		    pageSize:10,
		    pageList:[5,10,15,20],
  			toolbar : '#tb'
  		});
  			
  			
  			document.onkeydown = function(event) {
  				e = event ? event : (window.event ? window.event : null);
  				if (e.keyCode == 13) {
  					//执行的方法 
  					searchCar_owner();
  				}
  			};	
  		//能更多条件
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
  				$("#address").val("");
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
  			});
  			/*
  	  		select框
  	  	*/
  	  	
  			$("#carid").select2({
  			    placeholder: "请输入车主电话",	//默认文本框显示信息
  			    minimumInputLength: 1,	//最小输入字符长度
  			    allowClear:true,
  			    multiple: false,				//设置多项选择，false则只能单选
  			    maximumSelectionSize: 5, //最大选择个数
  				query: function (query) {
  			    	$.ajax({
  					   type: "POST",
  					   url: "shipOrder.do?method=getPhoneLength",
  					   data: {phones:query.term},   //传递你输入框中的值
  					   success: function(msg){
  			 		   	var msg=$.parseJSON(msg);		
  			 			var data = {results: []};		
  						$.each(msg,function(x,y){
  							data.results.push({id: y.id, text: y.name});;
  						});
  				        query.callback(data);
  					   }
  					});
  			    }
  			});	 		 
  	  	});
  	//查询
  	  	function searchCar_owner(){
  	  		var startDate=document.getElementById("startDate").value;
  	  		var endDate=document.getElementById("endDate").value;
  	  		/* if($.trim($("#phone").select2("data"))!=""){
  	  		phone=$.trim($("#phone").select2("data").text);
  	  			} */
  	  		$("#dg").datagrid('load',{	
  	  				start_date :startDate,
  		 	 		end_date :endDate,
  		 	 		ddId:$.trim($("#ddId").val()),
  		 	 		order_state:$.trim($("#order_state").combobox("getValue")),
  		 		 	pay_type:$.trim($("#pay_type").combobox("getValue")),
  		 			perpole:$.trim($("#perpole").val()),
  		 			end_address:$.trim($("#end_address").val()),
  		 			send_address:$.trim($("#send_address").val()),
  	  				//institution:$.trim($("#cc3").combotree("getValue")),
  	  				phone_number:$.trim($("#phone_number").val())
  		 	});	  
  		 	
  	  	}
  	  function clearuser(){
			if($("#selectuser").text()!="请选择"){
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
  	//删除
  	function deleteEver(){
  		var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
  			checkarray.push(array[i].shiping_order_id);
  		}
  		if(array != ""){
  			$.messager.confirm('确认','您确定要删除选中的记录吗？',function(r){ 
    			if (r){ 
    				$.ajax({
						url: "order.do?method=deleteShipOrder",
						type: "POST",
						data:{del:checkarray.join(",")},
			  				success:function(data){
			  					if (data.flag) {
									parent.$.messager.alert("订单信息删除","订单信息删除成功",'info',
										function(r) {
											$('#dg').datagrid('reload');
 										});
  								} 
  						}
					});
				}  			
  			});
  		}else{
  			alert("请选择要删除的信息");
  		}
  	}
  	
  	//详情查询
  	function xiangxixinxi(){
  		var row=$("#dg").datagrid('getSelections');
  			if(row.length==1){
	  			for(var i=0;i<row.length;i++){
		  			var id=row[i].shiping_order_id;
		  		}		
	  			dialog = parent.jy.modalDialog({
				title : '协议详情',
				url : 'shipOrder.do?method=getShipOrderPage&pid=' + id,
				width : 680,
				buttons : [{
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitFormEdit(dialog, grid, parent.$);
					}
				}] 
			});
  		}else{
  			alert("请选择一行信息");
  		}	
  	}
  	
  	//导出
  		function putoutfile(){
		var allRows2 = $("#dg").datagrid("getColumnFields");
		var colName=[];
		var fieldName=[];
		for(i=1;i<allRows2.length;i++)
		{
			var col = $('#dg').datagrid( "getColumnOption" , allRows2[i] );
			if(col.field!="check_type"&&col.field!="send_type"){
				colName.push(col.title);//把title列名到数组里去 
				fieldName.push(col.field); //把field列属性名到数组里去 
			} 
		} 
		$("#fieldName").val(fieldName.join(","));
  		 $("#headtitle").val(colName.join(","));
  		var outputform=$("#outputform");
  		outputform.submit(); 
  	}
  	
 	function addShipOrder() {
  		var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		
  		if($("#selectuserid").text()!=""){
  			checkarray=$("#selectuserid").text().split(",");
  		}else if(array.length!=0){
  			for(var i=0;i<array.length;i++){
  				checkarray.push(array[i].shiping_order_id);
  			}
  		}
  		
  		if(!($("#selectuserid").text()==""&&array.length==0)){
  			dialog = parent.jy.modalDialog({
			title : '协议制作',
			url : 'createA.do?method=getEditCreateAgreePage&pid='+checkarray,
			height : 600,
			width : 1100,
			resizable:true,
			id:"company",
			buttons : [{
				text : '<input type="button" class="btncss" value="保存"/>', 
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}]
		});	
  		}else{
  			alert("请选择一行信息");
  		}
		
	};
  	//打印
  	function dy(){
  		var row=$("#dg").datagrid('getSelections');
  			if(row.length==1){
	  			for(var i=0;i<row.length;i++){
		  			var pid=row[i].shiping_order_id;
		  		}	
							var url="shipOrder.do?method=getShipOrderPrint&pid="+pid;
							ow(url);
  		}else{
  			alert("请选择一行信息");
  		}	
  	
  	}
  	function ow(owurl){ 
		var tmp=window.open("about:blank","","fullscreen=1");
		tmp.moveTo(0,0); 
		tmp.resizeTo(screen.width+20,screen.height); 
		tmp.focus(); 
		tmp.location=owurl; 
		} 
  	//导入
  	function putintfile() {
		dialog = parent.jy.modalDialog({
			title : '导入订单',
			url : 'shipOrder.do?method=imp',
			width : 600,
			height: 300,
			buttons : [{
				text : '<input type="button" value="导入" class="btncss">',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}] 
		});
	};
	//重置
	function reset(){
		$("#startDate").val("");
		$("#endDate").val("");
		$("#ddId").val("");
		$("#order_state").combobox("setValue","");
		$("#pay_type").combobox("setValue","");
		$("#perpole").val("");
		$("#end_address").val("");
		$("#send_address").val("");
		$("#phone_number").val("");
	}
	//能更多条件
	var btnMoreSearch = $("#btnMoreSearch");
btnMoreSearch.click(function() {
	 if (btnMoreSearch.attr("state") == "close") {
	 //主要代码 
		$('.easyui-layout').layout('panel', 'north').panel('resize',{height:92});
		$('.easyui-layout').layout('resize');
		btnMoreSearch.text("收起条件");
		btnMoreSearch.attr("state", "open");
		$("#searchInfoId").css("display","block");
		$("#startDate").val("");
		$("#endDate").val("");
	} else {
		$('.easyui-layout').layout('panel', 'north').panel('resize',{height:60});
		$('.easyui-layout').layout('resize');
		btnMoreSearch.text("更多条件");
		btnMoreSearch.attr("state", "close");
		$("#searchInfoId").css("display","none");
	}
});
  </script>
  <body class="easyui-layout">
  	<form action="shipOrder.do" id="outputform" method="post">
    	<div region="north" title="检索" class="st-north" border="false"  height="60px" collapsible="false"  >
	    	<lable>发车日期:</lable>
			<input  id="startDate"  class="Wdate"  readonly="readonly" style="width:150px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			-
			<input id="endDate"  class="Wdate"  readonly="readonly" style="width:150px;height:22px;" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
	    	<lable class="divlabel">订单编号:</lable>
	    	 <input type="text" class="search-text" id="ddId"  style="width:150px"  >
	    	 	<lable class="divlabel">订单状态:</lable>
				<select class="easyui-combobox" id="order_state" name="agreement_type"
					style="width:150px;"
					data-options="panelHeight : 'auto',editable:false">
					<option id="" value="">全部</option>
					<option id="" value="0">未出库</option>
					<option id="" value="1">配送中</option>
					<option id="" value="2">转运中</option>
					<option id="" value="3">签收</option>
					<option id="" value="4">已到达</option>
				</select>
	    	 <a class="easyui-linkbutton" onclick="searchCar_owner()" data-options="iconCls:'icon-search'" >查询</a>	
	    	 <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="margin-top:16px;" href="javascript:void(0)" state="close">更多条件</a>
	    	 <div id="searchInfoId" style="display: none">
	    	 <lable >付款方式:</lable>
				<select class="easyui-combobox" id="pay_type" name="agreement_type"
					style="width:150px;"
					data-options="panelHeight : 'auto',editable:false">
					<option id="" value="">全部</option>
					<option id="" value="0">回付</option>
					<option id="" value="1">提付</option>
					<option id="" value="2">现付</option>
					<option id="" value="3">货到前付</option>
				</select>
	    	<lable class="divlabel">收货人:</lable>
	    	<input type="text"  class="search-text"style="width:150px"   id="perpole" >
			<!--<lable>所属机构:</lable>
			<input id="cc3" name="did">-->
			<lable>到站:</lable>
			<input type="text" class="search-text" id="send_adress" >
			<lable >发站:</lable>
	    	<input type="text"  class="search-text"style="width:150px"   id="end_address" >
	    	<lable >电话:</lable>
	    	<input type="text"  class="search-text"style="width:150px"   id="phone_number" >
			</div>
    	</div>
    	<div region="center" >
    		<table id="dg" title="订单信息" ></table>
    	</div>
    	<div id="tb">
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"onclick="addShipOrder()">制作协议</a>
		    <!--<a href="javascript:void(0)" class="easyui-linkbutton" id="getUpdateCar_owner" data-options="iconCls:'icon-edit',plain:true"onclick="getUpdateCar_owner()">修改</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="deleteShipOrder()">删除</a>
		 <a href="javascript:void(0)"	class="easyui-linkbutton" data-options="iconCls:'icon-input',plain:true" onclick="showIcons()" id="tbru"style="display: none">导入</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" id="outputCar_owner" data-options="iconCls:'icon-output',plain:true" onclick="putoutfile()">导出</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="getShipOrderPage()">详细</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="daShipOrder()">打印</a> -->
		     <tr style='margin: 20px;'>
			    <td><font color="red" style="margin-right:10px">*</font>选择运单:</td>
				<td class="td2"><span id="selectuser" style="word-wrap:break-word;word-break:break-all;">请选择</span>&nbsp;&nbsp;<img alt="清除" src="./images/clear.png" style="cursor: pointer;width: 10px;height: 10px;" onclick="clearuser()">
				<span  id="selectuserid" style="display: none;"></span>
				<span  id="channelId" style="display: none;" ></span>
				<!-- <input type="hidden" id="message_touserid" name="message_touser">
				<input type="hidden" id="message_tousername" name="message_tousername">
				<input type="hidden" id="cnid" name="channelId"> -->
			 </tr>
		</div>	
		<input type="hidden" name="method" value="outShipOrder">
		<input type="hidden" id="fieldName" name="fieldName">
		<input type="hidden" id="headtitle" name="headtitle">
		</form>
  </body>
</html>