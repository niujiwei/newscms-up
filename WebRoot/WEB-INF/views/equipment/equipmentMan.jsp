<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>设备管理</title>
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
		<script type="text/javascript" src="./js/function.js"></script>
	
  	<%
	String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() +"/truck_cloud/"; 
 	%>
  </head>
  <script type="text/javascript">
  var grid;
  	$(function(){
  	
  	//键盘回车事件
		document.onkeydown = function(event) {
			e = event ? event : (window.event ? window.event : null);
			if (e.keyCode == 13) {
				//执行的方法 
				searchCar();
			}
		};
  $("#userid").select2({
    		 placeholder: "用户名",	//默认文本框显示信息
   			 minimumInputLength: 1,	//最小输入字符长度
   			 multiple: false,				//设置多项选择，false则只能单选
   			 allowClear:true,
    		 maximumSelectionSize: 5,
    		 query: function (query) {
			    	$.ajax({
					   type: "POST",
					   url: "equipment.do?method=getUserName",
					   data: {username:query.term},   //传递输入框中的值
					   success: function(msg){
			 		   	var msg=$.parseJSON(msg);		
			 			var data = {results: []};		
						$.each(msg,function(x,y){
							data.results.push({id: y.id, text: y.name,drivername:y.dn});
						});
				        query.callback(data);
					   }
					});
			    }
			    }).on("select2-selecting", function(e) {
			    	$("#drivername").val(e.choice.drivername);
			    });
		$('#cc').combotree({    
    		url: "depn.do?method=getTree&&id=",
    		onBeforeLoad: function(node, param) {
                if (node == null) {
                    $('#cc').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=";//加载顶层节点
                } else {
                
                    $('#cc').combotree('tree').tree('options').url = "depn.do?method=getTree&&id=" + node.id;//加载下层节点
                }
              
               }     
			});
  	var btnMoreSearch = $("#btnMoreSearch");
	btnMoreSearch.click(function() {
		 if (btnMoreSearch.attr("state") == "close") {
		 //主要代码 
			$('.easyui-layout').layout('panel', 'north').panel('resize',{height:84});
			$('.easyui-layout').layout('resize');
			btnMoreSearch.text("收起");
			btnMoreSearch.attr("state", "open");
			$("#searchInfoId").css("display","block");
		} else {
			$('.easyui-layout').layout('panel', 'north').panel('resize',{height:58});
			$('.easyui-layout').layout('resize');
			btnMoreSearch.text("更多...");
			btnMoreSearch.attr("state", "close");
			$("#searchInfoId").css("display","none");
		} 
	});
  		grid=$("#dg").datagrid({
  			url : 'equipment.do?method=getEquipmentManlist',
  			border : false,
  			rownumbers : true,
  			fit : true,
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			rowStyler : function(index, row) {
				if (row.is_binding == 0) {
					 return 'color:blue;';
  			    }
  			},
  			columns : [[
  				{field : 'ck',checkbox : true},
  				{field : 'equipment_number',title : '设备号',width : 80,align:'center'},
  				{field : 'car_number',title : '车牌号',width : 100,align:'center'},
  				{field : 'department_Name',title : '所属机构',width : 200,align:'center'},
  				{field : 'username',title : '用户名',width : 80,align:'center'},
  				{field : 'drivername',title : '司机姓名',width : 80,align:'center'},
  				{field : 'end_date',title : '服务到期时间',width : 100,align:'center'},
  				{field : 'is_binding',
				title : '是否绑定',
				align : 'center',
				width : 80,
				formatter : function(val, row, index) {
				if (row.is_binding=='1') {
					return '已绑定';
				} else {
					return '未绑定';
				}

				}
				},
  			]],
  			pagination : true,
  			pageSize : 10,
  			pageList : [5, 10, 15, 20 ],
  			toolbar : '#tb'
  		});
  	});
  	function bb(){
  		var ui =document.getElementById("cc");
		ui.style.visibility="hidden";
  	}
  	function aa(){
  		var ui =document.getElementById("cc");
		ui.style.visibility="visible";//display为空的话会好使，为block会使后边的空间换行
  	}
  	function searchCar(){
  	    var username=$("#userid").select2('data')==null?"": $("#userid").select2('data').text;
  		$('#dg').datagrid('load',{
  			equipment_number : $.trim($("#equipment_numberId").val()),
  			carid:username,
  			department_id:$.trim($("#cc").combotree('getValue')),
  			car_number:$.trim($("#car_number").val())
  		});
  	}
  	//添加
	function addEquipment() {
		dialog = parent.jy.modalDialog({
			title : '设备新增页面',
			url : 'equipment.do?method=addEquipment',
			width : 650,
			height:380,
			buttons : [{
				text : '<input type="button"  class="btncss" value="保存"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
				}
			}] 
		});
	};
		/*
  	修改查询
  	*/
  	function getUpdateEquipment(){
  		var row=$("#dg").datagrid('getSelections');
  			if(row.length==1){
	  			for(var i=0;i<row.length;i++){
		  			var pid=row[i].equipment_id;
		  		}		
	  			dialog = parent.jy.modalDialog({
				title : '设备修改页面',
				url : 'equipment.do?method=editEquipment&pid=' + pid,
				width : 650,
				height:380,
				buttons : [{
					text : '<input type="button" class="btncss" value="修改">',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitFormEdit(dialog, grid, parent.$);
					}
				}] 
			});
  		}else{
  			alert("请选择一行信息");
  		}	
  	}
  	//删除
  	function deleteEquipment(){
  		var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
  			checkarray.push(array[i].equipment_id);
  		}
  		if(array != ""){
  			$.messager.confirm('确认','您确定要删除选中的记录吗？',function(r){ 
    			if (r){ 
    				$.ajax({
						url: "equipment.do?method=deleteEquipment",
						type: "POST",
						data:{del:checkarray.join(",")},
			  				success:function(data){
			  					if (data.flag) {
									parent.$.messager.alert("设备信息删除","设备信息删除成功",'info',
										function(r) {
											$('#dg').datagrid('reload');
 										});
  								} 
  						}
					});
				}  			
  			});
  		}else{
  			$.messager.alert("设备信息","请选择要删除的信息",'info');
  		}
  	}
  	function reset(){
		$("#equipment_numberId").val("");
		$("#userid").select2('val','');
		$("#cc").combotree('setValue', "");
  	}
  	
  	function bangding(){
  	var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
  		    //alert(array[i].is_binding);
  		    if(array[i].is_binding!=1){
  		        
  		        checkarray.push(array[i].equipment_id);
  		    }
  			
  		}
  		
  		if(checkarray != ""&&checkarray.length>0){
  			$.messager.confirm('确认','您确定要绑定选中的记录吗？',function(r){ 
    			if (r){ 
    				$.ajax({
						url: "equipment.do?method=bundlingUserInfo",
						type: "POST",
						data:{id:checkarray.join(",")},
			  				success:function(data){
			  					if(data.message){
			  					parent.$.messager.alert("设备信息解绑","设备信息绑定成功",'info',
										function(r) {
											$('#dg').datagrid('reload');
 										});
			  					}
  						}
					});
				}  			
  			});
  		}else{
  			$.messager.alert("设备信息","请选择要绑定的信息或者选择的信息已经绑定",'info');
  		}
  	}
  	
  	function jiebang(){
  	var checkarray=[];
	 	var array = $('#dg').datagrid('getSelections');
  		for(var i=0;i<array.length;i++){
  			 if(array[i].is_binding==1){
  		       checkarray.push(array[i].equipment_id);
  		    }
  			
  		}
  		if(checkarray != ""&&checkarray.length>0){
  			$.messager.confirm('确认','您确定要绑定选中的记录吗？',function(r){ 
    			if (r){ 
    				$.ajax({
						url: "equipment.do?method=unbundlingUserInfo",
						type: "POST",
						data:{id:checkarray.join(",")},
			  				success:function(data){
			  					if(data.message){
			  					parent.$.messager.alert("设备信息解绑","设备信息解除绑定成功",'info',
										function(r) {
											$('#dg').datagrid('reload');
 										});
			  					}
  						}
					});
				}  			
  			});
  		}else{
  			$.messager.alert("设备信息","请选择要解除绑定的信息或者选择的信息未绑定",'info');
  		}
  	}
  </script>
  
  
  <body class="easyui-layout">
    	<div region="north" title="检索" class="st-north"  style="height: 58px;" border="false" data-options="collapsible:false">
						<label>设备号:</label><input type="text" class="search-text" id="equipment_numberId">
						<label>用户姓名:</label><input type="hidden" class="search-text" id="userid" placeholder="请选择车用户" style="height: 22px;margin-right: 7px;"/>
						<label>车牌号:</label><input type="text" class="search-text" id="car_number"/>
						<label>所属机构:</label><input type="text" class="search-text" id="cc" />
						<a class="easyui-linkbutton" onclick="searchCar()" data-options="iconCls:'icon-search'" style="margin-left: 10px;">查询</a>
<!-- 						<a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="" href="javascript:void(0)" state="close">更多...</a>
 -->					
					<div id="searchInfoId" style="display: none">
						<label>车辆用途:</label><input type="text" class="search-text" id="" placeholder="选择用途">
						<label>载货状态:</label><input type="text" class="search-text" id="" placeholder="请选择载货状态">
						<label>GPS状态:</label><input type="text" class="search-text" id="" placeholder="全部">
					</div>
    	</div>
    	
    	<div region="center" title="设备信息列表" region="center" style="background:#eee;overflow-y:hidden">
    		<table id="dg"></table>
    	</div>
    	<div id="tb">
    			 <a href="javascript:void(0)" class="easyui-linkbutton"  id="bangding" data-options="iconCls:'icon-add',plain:true" onclick="bangding()" >绑定</a>
    	    			 <a href="javascript:void(0)" class="easyui-linkbutton"  id="jiebang" data-options="iconCls:'icon-add',plain:true" onclick="jiebang()" >解绑</a>
    	
		 <a href="javascript:void(0)" class="easyui-linkbutton"  id="tbadd" data-options="iconCls:'icon-add',plain:true" onclick="addEquipment()" >新增</a>
		   <a href="javascript:void(0)" class="easyui-linkbutton"  id="tbmodify" data-options="iconCls:'icon-edit',plain:true" onclick="getUpdateEquipment()" >修改</a>
<!-- 		   <a href="javascript:void(0)" class="easyui-linkbutton" id="tbmodify" onclick="modify()" data-options="iconCls:'icon-edit',plain:true">导出</a> -->
		<a href="javascript:void(0)" class="easyui-linkbutton" id="tbresetpassword"  id="tbdel" onclick="deleteEquipment()" data-options="iconCls:'icon-remove',plain:true">删除</a>
 
		</div> 
		
			<input id="functionname"type="hidden" value=${function}>
  </body>
</html>