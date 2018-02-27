<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%


%>
<%
	String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() +"/surmax/"; 
 %>
 <%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.User" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>协议制作管理</title>
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
	<script type="text/javascript" src="./js/function.js"></script><!--命名按钮方法  -->
	
  
  </head>
   <% 
  	User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
   %>
  <script type="text/javascript">
	  	var dialog;
 		var grid;
 		var departid='<%=user.getDid()%>';
  		$(function(){
  			grid=$("#dg").datagrid({
  			url : 'createA.do?method=getCreateAgreement&departid='+departid,
  			border : false,
  			rownumbers : true,
  			fit : true,
  			sortName:'transport_pay,check_time,shipping_order',
  			sortOrder:'desc',
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			multiSort:true,
  		/* 	remoteSort:true, */
  			columns : [[
  				{field : 'agreement_id',checkbox : true},
  				{field : 'agreement_number',title : '协议编号',width : 90,align:'center'},
  				{field : 'agreement_type',title : '协议类型',width : 60,align:'center',
  					formatter:function(val,row,index){
  						if(val==0){
  							return "班线";
  						}else if(val==1){
  							return "配送";
  						}else if(val==2){
  							return "转运";
  						}
  					}	
  				
  				},
  				{field : 'end_address',title : '到达地',width : 60,align:'center'},
  				{field : 'ndp_id',title : '托运机构',width : 90,align:'center',
  				formatter:function(val,row,index){
  						var de='';
	  						$.ajax({
					  			type: "POST",
					  			async : false,
					  			url:'depn.do?method=getDepn',
					  			data:{department_id:val},
					  			success:function(data){
					  			de=data.department_name;
					  			//$("#ndpid").val(data.department_id);
					  			//$("#departments").append("<li>"+data+"</li>");
					  			}
					  		});
					  	return de;	 	
  					}	
  				
  				},
  				{field : 'ldp_id',title : '下一机构',width : 90,align:'center',
  					formatter:function(val,row,index){
  						var de='';
	  						$.ajax({
					  			type: "POST",
					  			async : false,
					  			url:'depn.do?method=getDepn',
					  			data:{department_id:val},
					  			success:function(data){
					  			de=data.department_name;
					  			//$("#ndpid").val(data.department_id);
					  			//$("#departments").append("<li>"+data+"</li>");
					  			}
					  		});
					  	return de;	 	
  					}	
  				
  				},
  				{field : 'company_id',title : '中专公司',width : 90,align:'center',
  					formatter:function(val,row,index){
  						var de='';
	  						$.ajax({
					  			type: "POST",
					  			async : false,
					  			url:'company.do?method=getUpdateCompany',
					  			data:{id : val},
					  			success:function(data){
					  			de=data.company_name;
					  			//$("#ndpid").val(data.department_id);
					  			//$("#departments").append("<li>"+data+"</li>");
					  			}
					  		});
					  	return de;	 	
  					}	
  				
  				},
  				{field : 'car_number1',title : '车牌号',width : 70,align:'center'},
  				{field : 'car_name',title : '司机',width : 65,align:'center'},
  				{field : 'all_money',title : '运费总额',width : 70,align:'center'},
  				{field : 'phone_number',title : '联系方式',width : 80,align:'center'},
  				{field : 'order_state',title : '状态',width : 50,align:'center',
  					formatter:function(val,row,index){
  						if(val==0){
  							return "未出库";
  						}else if(val==1){
  							return "配送中";
  						}else if(val==2){
  							return "转运中";
  						}else if(val==4){
  							return "已签收";
  						}else if(val==3){
  							return "已到达";
  						}
  					}
  				},
  				{field : 'number',title : '运单数',width : 60,align:'center'},
  				{field : 'remarks',title : '备注',width : 100,align:'center'},
  				//{field : 'create_user',title : '协议制作者',width : 90,align:'center',sortable:'true'},
  				{field : 'create_time',title : '协议制作时间',width : 140,align:'center',sortable:'true',
  					formatter: function(value,row,index){
  		  				if(value!=undefined){
  		  					value=value.substr(0,19);
  		  				}
  		  				return value;
  		  			} 		
  				},
  				//{field : 'updateDate',title : '更新日期',width : 80,align:'center'},
  				
  			]],
  			pagination:true,//分页
		    pageSize:10,
		    pageList:[5,10,15,20],
  			toolbar : '#tb'
  		});
  		
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
			
			$("#agreement_type option:first").prop("selected", 'selected');
			$("#end_address").val("");
			$("#company_id").val("");
			$("#phone_number").val();
			$("#order_state option:first").prop("selected", 'selected');
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
  	document.onkeydown = function(event) {
		e = event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13) {
			//执行的方法 
			searchCar_owner();
		}
	};
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
	 	 		car_name:$.trim($("#car_name").val()),
	 	 		car_number1:$.trim($("#car_number1").val()),
	 	 		agreement_type:$.trim($("#agreement_type").val()),
	 	 		end_address:$.trim($("#end_address").val()),
	 	 		company_id:$.trim($("#company_id").val()),
	 	 		phone_number:$.trim($("#phone_number").val()),
	 	 		order_state:$.trim($("#order_state").val()),
  				//institution:$.trim($("#cc3").combotree("getValue")),
	 	});	  
	 	
  	}
  	//删除
  	function deleteEver(){
  		var checkarray=[];
  		var check=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
  		if(array[i].isagain==1){
  			check.push(array[i].agreement_id);
  		}else{
  			checkarray.push(array[i].agreement_id);
  		}
  			
  		}
  		if(array != ""){
  			$.messager.confirm('确认','您确定作废选中的记录吗？',function(r){ 
    			if (r){ 
    				$.ajax({
						url: "createA.do?method=deleCreateA",
						type: "POST",
						data:{del:checkarray.join(","),
							  dela:check.join(",")
						},
			  				success:function(data){
			  					if (data.flag) {
									parent.$.messager.alert("协议信息","协议信息已作废",'info',
										function(r) {
											$('#dg').datagrid('reload');
 										});
  								} 
  						}
					});
				}  			
  			});
  		}else{
  			$.messager.alert("协议制作","请选择要作废的信息","info");
  		}
  	}
  	
  	/*
  	详细
  	*/
  	function modify(){
  		var row=$("#dg").datagrid('getSelections');
  			if(row.length==1){
	  			for(var i=0;i<row.length;i++){
		  			var id=row[i].agreement_id;
		  		}		
	  			dialog = parent.jy.modalDialog({
				title : '协议详情',
				url : 'createA.do?method=xiangxiEditCreateA&pid=' + id,
				width : 930,
				buttons : [{
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitFormEdit(dialog, grid, parent.$);
					}
				}] ,onClose:function(){
					$('#dg').datagrid('reload');
				} 
			});
  		}else{
  			$.messager.alert("协议制作","请选择一行信息","info");
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
				title : '订单详情',
				url : 'shipOrder.do?method=getShipOrderPage&pid=' + id,
				width : 930,
				buttons : [{
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitFormEdit(dialog, grid, parent.$);
					}
				}]
			});
  		}else{
  			$.messager.alert("协议制作","请选择一行信息","info");
  		}	
  	}
  	//修改
  	function updateUser(){
	 var myform=document.forms[1];
  	 myform.action="car_owner.do?method=updateCar_owner";
  	 myform.submit();
	
	}
  	
  	//导出
  	function putoutfile(){
		var allRows2 = $("#dg").datagrid("getColumnFields");
		/* var colName=[];
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
  		$("#headtitle").val(colName.join(",")); */
  		var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
	  		checkarray.push(array[i].agreement_id);
  		}
		$("#agId").val(checkarray.join(","));
  		var outputform=$("#outputform");
  		outputform.submit(); 
  	}
  	
 	function add() {
		dialog = parent.jy.modalDialog({
			title : '承运协议制作',
			url : 'createA.do?method=getCreateAgreePage',
/* 				 buttons :'#addbuttons'
*/
			resizable:true,
			height : 600,
			width : 1100,
			buttons : [{
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}],
			onClose:function(){
				$('#dg').datagrid('reload');
			}
		});
	};
  	
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
		$("#car_name").val("");
		$("#car_number1").val("");
		$("#agreement_type option:first").prop("selected", 'selected');
		$("#end_address").val("");
		$("#company_id").val("");
		$("#phone_number").val();
		$("#order_state option:first").prop("selected", 'selected');
	}
	
	function createLin(){
		 var row=$("#dg").datagrid('getSelections');
		if(row.length==1){
			var aid=row[0].agreement_id;//协议id
			$("#aid").val(aid);
			document.getElementById("clform").action="maplin.do?method=createLin";
			document.getElementById("clform").submit(); 
		}else{
			alert("请选择一行信息");
		}
	}
  </script>
  <body class="easyui-layout">
	  <form action="" name="clform"  id="clform"  method="post">
	  	<input id="aid" name="aid" value="">
	  </form>
  	<form action="createA.do" id="outputform" method="post">
    	<div region="north" title="检索" class="st-north" border="false" collapsible="false"  >
	    	<lable>制单时间:</lable>
			<input  id="startDate"  class="Wdate"  readonly="readonly" style="width:150px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			-
			<input id="endDate"  class="Wdate"  readonly="readonly" style="width:150px;height:22px;" onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
	    	<lable class="divlabel">司机:</lable>
	    	 <input type="text" class="search-text" name="car_name" id="car_name"  style="width:150px"  >
	    	<lable class="divlabel">车牌号:<input type="text" name="car_number1" class="search-text" id="car_number1"  style="width:150px"  ></lable>
	    	 <a class="easyui-linkbutton" onclick="searchCar_owner()" data-options="iconCls:'icon-search'" >查询</a>	
	    	 <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="margin-top:16px;" href="javascript:void(0)" state="close">更多条件</a>
	    	 <div id="searchInfoId" style="display: none">
	    	 	<lable class="divlabel1">协议类型:<select id="agreement_type" name="agreement_type">
	    	 		<option value="" selected="selected">请选择</option>
	    	 		<option value="0">班线</option>
	    	 		<option value="1">配送</option>
	    	 		<option value="2">转运</option>
	    	 	</select></lable>	
	    	 	<lable id="end_address1" class="divlabel">到达地:<input type="text" class="search-text" name="end_address" id="end_address"  style="width:150px"  ></lable>
	    	 	<lable id="company_id1" class="divlabel">中转公司:<input type="text" class="search-text" name="company_id" id="company_id"  style="width:150px"  ></lable>
	    	 	<lable id="phone_number1" class="divlabel">联系方式:<input type="text" class="search-text" name="phone_number" id="phone_number"  style="width:150px"  ></lable>
	    	 	<lable id="order_state1" class="divlabel">状态:
	    	 		<select id="order_state" name="order_state">
	    	 			<option value="" selected="selected">请选择</option>
	    	 			<option value="0">未出库</option>
	    	 			<option value="1">配送中</option>
	    	 			<option value="2">转运中</option>
	    	 			<option value="4">已签收</option>
	    	 			<option value="3">已到达</option>
	    	 		</select>
	    	 	</lable>
	    	 </div>
    	</div>
    	<div region="center" >
    		<table id="dg" title="协议信息" ></table>
    	</div>
    	<div id="tb">
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"onclick="add()">制作协议</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" id="modify" data-options="iconCls:'icon-edit',plain:true"onclick="modify()">详细信息</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="deleteEver()">作废</a>
		 <!--<a href="javascript:void(0)"	class="easyui-linkbutton" data-options="iconCls:'icon-input',plain:true" onclick="showIcons()" id="tbru"style="display: none">导入</a>
		   --><a href="javascript:void(0)" class="easyui-linkbutton" id="outputCar_owner" data-options="iconCls:'icon-output',plain:true" onclick="putoutfile()">导出</a>
		   <!--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="getShipOrderPage()">详细</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"   onclick="daShipOrder()">打印</a> -->
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true"   onclick="createLin()">创建路线</a> -->
			
		</div>	
		<input type="hidden" name="agreementIds" id="agId">
		<input type="hidden" name="method" value="outAgreement">
		<input type="hidden" id="fieldName" name="fieldName">
		<input type="hidden" id="headtitle" name="headtitle">
		</form>	
  </body>
</html>