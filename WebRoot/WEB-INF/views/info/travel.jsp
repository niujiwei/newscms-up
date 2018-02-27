<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
 <!-- /**
 * 固定车辆界面
 * @author lx
 */  -->
    <title>车辆基础信息管理</title>
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
	
  </head>
  <script type="text/javascript">
  	var grid;
  	var dialog;
  	$(function(){
  	var fs=$("#functionname").val().split(",");
	for(var i = 0;i<fs.length;i++){
	if(fs[i]=="添加"){
		$("#tbadd").removeAttr("style");
	}else if(fs[i]=="修改"){
	$("#tbmodify").removeAttr("style");
	}else if(fs[i]=="删除"){
	$("#tbdel").removeAttr("style");
	}else if(fs[i]=="导入"){
	$("#tbput").removeAttr("style");
	}else if(fs[i]=="导出"){
	$("#tboutput").removeAttr("style");
	}else if(fs[i]=="回放"){
	$("#tbhuifang").removeAttr("style");
	}else if(fs[i]=="跟踪"){
	$("#tbgenzong").removeAttr("style");
	}else if(fs[i]=="货运人共享"){
	$("#tbgongxiang").removeAttr("style");
	}
	
	} 
  	$('#cc2').combotree({    
    		url: "depn.do?method=getTree&&id=",    
    		width: 150,
    		onBeforeLoad: function(node, param) {
    		
                if (node == null) {
                    $('#cc2').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
                } else {
                
                    $('#cc2').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=" + node.id;//加载下层节点
                }
              
               }     
			}); 
  			/* 根据车牌号查询 */
  		  $("#number_search").select2({
    		 placeholder: "请选择车辆",	//默认文本框显示信息
   			 minimumInputLength: 1,	//最小输入字符长度
   			 multiple: false,				//设置多项选择，false则只能单选
   			 allowClear:true,
    		 maximumSelectionSize: 5,
    		 query: function (query) {
			    	$.ajax({
					   type: "POST",
					   url: "travel.do?method=getCarNum",
					   data: {search:query.term},   //传递输入框中的值
					   success: function(msg){
			 		   	var msg=$.parseJSON(msg);		
			 			var data = {results: []};		
						$.each(msg,function(x,y){
							data.results.push({id: y.id, text: y.name});
						});
				        query.callback(data);
					   }
					});
			    }
			    });

  			/* 初始化页面 */
  			grid=$("#dg").datagrid({
  			url : 'travel.do?method=getTravel',
  			border : false,
  			rownumbers : true,
  			fit : true,
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			columns : [[
  				{field : 'ck',checkbox : true},
  				{field : 'plateNumber',title : '车牌号码',width : 80},
  				{field : 'carOwner',title : '所有人',width : 80},
  				{field : 'user_idcard',title : '身份证',width : 120},
  				{field : 'departmentId',title : '所属部门',width : 140,
  				
	     	 formatter:function(val,row,index){
		        	$.ajax({
  					type: "POST",
  					async : false,
  					url:'user.do?method=getDepartment',
  					data:{did:val},
  					success:function(data){
  						da=data;
  			}
  		});
  						val = da;
  						return val;
		        } },
		        {field : 'tels',title : '电话',width : 120},
		        {field : 'address',title : '地址',width : 160},
  				{field : 'carLength',title : '车长',width : 80,
  				formatter:function(value,rec,index){ 
						 var carLength='';
					 if(typeof(rec.tran)!="undefined"){
					if(rec.tran!=null){
					carLength=rec.tran.carLength;
					}
					} 
						return carLength;
					}},
  				{field : 'length_name',title : '厢型',width : 80},
  				{field : 'tonnage',title : '吨位',width : 80,
  				 formatter:function(value,rec,index){ 
						 var tonnage='';
					 if(typeof(rec.tran)!="undefined"){
					if(rec.tran!=null){
					tonnage=rec.tran.tonnage;
					}
					} 
						return tonnage;
					} },
  				{field : 'carType',title : '车辆类型',width : 80,
  					formatter:function(val,row,index){
  						if(val=="A"){
  							return "市内车";
  						}else if(val=="B"){
  							return "班线车";
  						}
  					}
  				}
  				/* {field : 'positionTime',title : '定位时间',width : 80,
  					formatter:function(val,row,index){
  						if(val!=null){
  							return val.substring(0,19);
  						}
  					}
  				} */
  			]],
  			pagination : true,
  			pageSize : 10,
  			pageList : [5, 10, 15, 20 ],
  			toolbar : '#tb'
  		});
  	});
  	
  	/*  跳转到新增界面*/
  	function addCar(){
  		dialog = parent.jy.modalDialog({
			title : '新增信息',
			url : 'travel.do?method=getAddCar',
			width:720,
			buttons : [{
				text : '<input type="button"  class="btncss" value="保存">',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}] 
		});
  	};
  	
  	
  	
  		/* 跳转到修改界面*/
  	function editTruck(){
  		var row = $('#dg').datagrid('getSelections');
  		if(row.length==1){
  			for(var i=0;i<row.length;i++){
  				var travelCardId = row[i].travelCardId;
  			}
  			dialog = parent.jy.modalDialog({
				title : '修改信息',
				url : 'travel.do?method=getEditTravel&travelCardId='+travelCardId,
				width:720,
				buttons : [{
					text : '<input type="button" class="btncss" value="保存">',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitFormEdit(dialog, grid, parent.$);
					}
				}] 
			});
		}else{
			$.messager.alert("车辆基本信息","请选择一行","info");
  		}
  	}
  	
  	
  	/* 批量删除 */
  	function deleteTruck(){
  		var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
  			checkarray.push(array[i].travelCardId);
  		}
  		
  		if(array != ""){
  			$.messager.confirm('确认','您确定要删除选中的记录吗？',function(r){ 
    			if (r){ 
    				$.ajax({
						url: "travel.do?method=checkAgreement",
						type: "POST",
						data:{del:checkarray.join(",")},
			  				success:function(data){
			  					if (data.flag) {
									alert("您选中的车辆已经被绑定，请解绑后再执行删除操作!");
  								}else{
  								 $.ajax({
						url: "travel.do?method=deleteTravel",
						type: "POST",
						data:{del:checkarray.join(",")},
			  				success:function(data){
			  					if (data.flag) {
									parent.$.messager.alert("车辆信息删除","车辆信息删除成功",'info',
									function(r) {
											$('#dg').datagrid('reload');
  										});
  								} 
  						}
					});
  								}
  						}
					});
    				
				}  			
  			});
  		}else{
  			$.messager.alert("车辆基本信息","请选择要删除的信息","info");
  		}
  	}
  	function reset(){
  	$("#number_search").select2("val", "");
  	$("#cc2").combotree("setValue","");
  	}
  	//导出
  	function putoutfile(){
		var allRows2 = $("#dg").datagrid("getColumnFields");
			var array = $('#dg').datagrid('getSelections');
			if(array.length>0){
				var carid=[];
  		for(var i=0;i<array.length;i++){
  			carid.push(array[i].travelCardId);
  		}
		var colName=[];
		var fieldName=[];
		for(i=1;i<allRows2.length;i++)
		{
			var col = $('#dg').datagrid( "getColumnOption" , allRows2[i] );
			colName.push(col.title);//把title列名到数组里去 
			fieldName.push(col.field); //把field列属性名到数组里去 
		} 
		$("#fieldName").val(fieldName.join(","));
  		 $("#headtitle").val(colName.join(","));
  		 $("#putCarId").val(carid.join(","));
  		var outputform=$("#outputform");
  		outputform.submit(); 
			}else{
			parent.$.messager.alert("警告","请选择数据");
			}
  	}
  	
  	/* 查询 */
  	function searchTruck(){
  		$('#dg').datagrid('load',{
  			plateNumber : $.trim($("#number_search").select2("val")),
  			did:$.trim($("#cc2").combotree("getValue"))
  		});
  	}
  </script>
  <body class="easyui-layout">
  <form action="travel.do" id="outputform" method="post">
    	<div region="north" title="车辆基础信息管理" class="st-north" border="false" data-options="collapsible:false">
			<div>
				<lable>车牌号码：</lable><input type="hidden" id="number_search" name="plateNumber"style="width: 140px"/>
				<lable  class="divlabel" >所属机构：</lable><input id="cc2" name="did" style="width: 130px">
				&nbsp<a class="easyui-linkbutton" onclick="searchTruck()" data-options="iconCls:'icon-search'">查询</a>
			</div>
			<!-- <div id="cc">
				<lable>载货状态:</lable><input type="text" id="" placeholder="请选择载货状态">
				<lable>GPS状态:</lable><input type="text" id="" placeholder="全部">
			</div> -->
    	</div>
    	
    	<div region="center" title="车辆信息列表" region="center" style="background:#eee;overflow-y:hidden">
    		<table id="dg"></table>
    	</div>
    	<div id="tb">
		   <a class="easyui-linkbutton" id="tbadd"onclick="addCar()"  data-options="iconCls:'icon-add',plain:true" style="display: none">新增</a>
		   <a class="easyui-linkbutton" id="tbmodify"onclick="editTruck()" data-options="iconCls:'icon-edit',plain:true" style="display: none">修改</a>
		   <a class="easyui-linkbutton" id="tbdel"onclick="deleteTruck()" data-options="iconCls:'icon-remove',plain:true" style="display: none">删除</a>
		   <a class="easyui-linkbutton" id="tboutput" onclick="putoutfile()" data-options="iconCls:'icon-output',plain:true"style="display: none">导出</a>
		 <!--   <a class="easyui-linkbutton" id="tbput" onclick="" data-options="iconCls:'icon-input',plain:true"style="display: none">导入</a> -->
		    <!--  <a class="easyui-linkbutton" id="tbhuifang" onclick="" data-options="plain:true"style="display: none">回放</a>
		  <a class="easyui-linkbutton" id="tbgenzong" onclick="" data-options="plain:true"style="display: none">跟踪</a>-->
		  <!--  <a class="easyui-linkbutton" onclick="tbgongxiang" data-options="plain:true"style="display: none">货运人共享</a> -->
		   <a class="easyui-linkbutton" id="tbreset" onclick="reset()"data-options="iconCls:'icon-reset',plain:true">重置</a>
		</div>
		<input type="hidden" name="method" value="outputCar">
		<input type="hidden" id="fieldName" name="fieldName">
		<input type="hidden" id="headtitle" name="headtitle">
		<input type="hidden" id="putCarId" name="putCarId">
		</form>
		<input id="functionname"type="hidden" value=${function}>
  </body>
</html>
