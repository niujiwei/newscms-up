<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%


String change_fee1 = request.getParameter("change_fee1");
%>
<%
	String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() +"/surmax/"; 
 %>
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
	<script type="text/javascript" src="./js/select2/select2_locale_zh-CN.js"></script>
	<script type="text/javascript" src="./js/function.js"></script><!--命名按钮方法  -->
	
  
  </head>
   <% 	String dd = (String) request.getAttribute("sl.getWrite_id()"); %>
  <script type="text/javascript">
	  	var dialog;
 		var grid;
 		var editval;
  		$(function(){
  			grid=$("#dg").datagrid({
  			url : 'collect.do?method=getCollect',
  			border : false,
  			rownumbers : true,
  			fit : true,
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			multiSort:true,
  			rowStyler : function(index, row) {
				if (row.write_off3!=3) {
				
					return 'color:blue;';
				}
			},
  					columns : [[
  				{field : 'ck',checkbox : true},
  				{field : 'custom_name',title : '客户名称',width :80,align:'center'},
  				{field : 'car_time',title : '客户发车车次',width : 80,align:'center'},
  				{field : 'send_station',title : '发站',width : 80,align:'center'},
  				{field : 'car_number',title : '车牌号',width : 80,align:'center'},
  				{field : 'car_type',title : '车型',width : 80,align:'center'},
  				{field : 'driver_name',title : '司机姓名',width : 80,align:'center'},
  				{field : 'send_time',title : '发车日期',width : 80,align:'center'},
  				{field : 'receipt_time',title : '倒车日期',width : 80,align:'center'},
  				{field : 'transport_pay',title : '代收到付运费',width : 80,align:'center'
  				,editor:{
	                	type:'numberbox',
	                	options:{
	                		precision:2,
	                    	validType:'transport_pay'
	                	}
	           		}},
  				{field : 'paid_fee',title : '实收到付运费',width : 80,align:'center',editor:{
	                	type:'numberbox',
	                	options:{
	                		precision:2,
	                    	validType:'paid_fee'
	                	}
	           		},
  					formatter:function(val,row,index){
  						if(val==null ){
  							return row.transport_pay;
  						}else{
  						return row.paid_fee;
  							}
  						}	
  					 },
  				
  				{field : 'difference',title : '差异',width : 80,align:'center',	formatter:function(val,row,index){
	           		   if(val==null){
	           		 		return Number(Number(row.paid_fee)-Number(row.transport_pay));
	           		   }
  					}},
  				{field : 'trunk_fee',title : '应收中转费',width : 80,align:'center'},
  			{field : 'take_fee',title : '应收送货费',width : 80,align:'center'},
  			{field : 'ys_unloading_fee',title : '应收落地费',width : 80,align:'center'},
  			{field : 'adorn_fee',title : '应收其他费',width : 80,align:'center'},
  			{field : 'total_cost',title : '应收费用总计',width : 80,align:'center',
  			  formatter:function(val,row,index){
	   	
	    	
	           		   if(val==null){
	           		 
	           		return Number(Number(row.trunk_fee)+Number(row.take_fee)+Number(row.adorn_fee)+Number(row.ys_unloading_fee	));
	           		   }
  					}  
  					},
  			{field : 'remarks_fee1',title : '应收客户账款备注',width : 120,align:'center',editor:{
	                	type:'text',
	          			     options:{
	                			
	                    	validType:'remarks_fee1'
	                	}}},
  			
  					{field : 'write_off3',title : '核销方式',width : 80,align:'center',
  					formatter:function(val,row,index){
  						if(val==0 ){
  							return "打卡";
  						}else if(val==1){
  						return "现金";
  						}else if(val==3){
  						return "未核销";
  						}
  					}	 },
  						{field : 'kuhu_date',title : '核销日期',width : 80,align:'center'},
  						{field : 'people_zhuang',title : '核销人',width : 80,align:'center'},
  			]],onBeforeEdit:function(index,row){ //可编辑之前
				editval=index;
		        row.editing = true;
		    },  
		    onAfterEdit:function(index,row){//编辑关闭后
		    	$('#dg').datagrid("endEdit",editval);
					$.ajax({
           			 type: "post",
          				url : 'collect.do?method=updateShippingOrder',
           				data :  row
           				
           				,
            			success: function (data) {
                		// Play with returned data in JSON format
            },
            error: function (msg) {
                            }
        });
  				/* $('#dg').datagrid("updateRow", {index:index,row:{gain_total:price}}); */
		        row.editing = false;  
		    },
		    //双击事件
            onDblClickRow: function (rowIndex,rowData) {
                //双击开启编辑行
    				if(editval!=rowIndex){
    					$('#dg').datagrid("endEdit", editval);
    					$('#dg').datagrid("beginEdit", rowIndex);
	                	editval=rowIndex;
    				}else{
    					editval=undefined;
    					$('#dg').datagrid("endEdit", rowIndex);
    				}
                	
            },
  			 onLoadSuccess:function(){
  			/*  $('#dg').datagrid('appendRow', {
                Saler: '<span class="subtotal">合计</span>',
                TotalOrderCount: '<span class="subtotal">' + compute("TotalOrderCount") + '</span>',
                TotalOrderMoney: '<span class="subtotal">' + compute("TotalOrderMoney") + '</span>',
                TotalOrderScore: '<span class="subtotal">' + compute("TotalOrderScore") + '</span>',
                TotalTrailCount: '<span class="subtotal">' + compute("TotalTrailCount") + '</span>',
                Rate: '<span class="subtotal">' + ((compute("TotalOrderScore") / compute("TotalTrailCount")) * 100).toFixed(2) + '</span>'
            });
            	 compute(); */
            },
  				
  			pagination:true,//分页
		    pageSize:10,
		    pageList:[5,10,15,20],
  			toolbar : '#tb'
  		});
  		
  		
  		
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
  	
  	});
  		function modify(){
		  var array=$("#dg").datagrid('getSelections');
  			var id = [];
  			var a = false;
		for ( var i = 0; i < array.length; i++) {
			id.push(array[i].custom_id); //注意修改你的id列 
			if (array[i].write_off3 == 3) {
				a = true;
			}
		}	
		
		if (a == true) {
			if (id != "") {
	  			dialog = parent.jy.modalDialog({
				title : '费用核销',
				url : 'collect.do?method=getEditCompanyPagef&pid=' + id,
				width : 500,
				height:300,
				buttons : [{
					text : '<input type="button" class="btncss" value="核销"/>',
					handler : function() {
						dialog.find('iframe').get(0).contentWindow.submitFormEdit(dialog, grid, parent.$);
					}
				}] 
			});
  		}
  		}else{
  			$.messager.alert("中转费核销"," 请选择正确的信息","info");
  		}	
  	}
  function compute(colName) {
            var rows = $('#dg').datagrid('getRows');
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += parseFloat(rows[i][colName]);
            }
            return total;
        }
  		//查询
  	function searchCar_owner(){
  		$("#dg").datagrid('load',{
  				
	 	 		custom_name:$.trim($("#custom_name").val()),
	 	 		car_time:$.trim($("#car_time").val()),
	 	 		kuhu_date:$.trim($("#kuhu_date").val()),
	 	 		write_off3:$.trim($("#write_off3").val()),
	 	 		send_time:$.trim($("#send_time").val()),
	 	 		receipt_time:$.trim($("#receipt_time").val()),
	 	 		car_number:$.trim($("#car_number").val()),
	 	 		driver_name:$.trim($("#driver_name").val()),
	 	 		aa:$.trim($("#aa").val()),
	 	 		bb:$.trim($("#bb").val()),
	 	 		cc:$.trim($("#cc").val()),
	 	 		
	 	});	  
	 	
  	}

  //重置
	function reset() {
		$("#custom_name").val("");
		$("#car_time").val("");
		$("#kuhu_date").val("");
		$("#write_off3").val("");
		$("#send_time").val("");
		$("#receipt_time").val("");
		$("#car_number").val("");
		$("#driver_name").val("");
			$("#aa").val("");
				$("#bb").val("");
					$("#cc").val("");
	}
  	/* function del() {
		var array = $("#dg").datagrid('getSelections');
			var id = [];
			var a = false;
		for ( var i = 0; i < array.length; i++) {
			id.push(array[i].custom_id); //注意修改你的id列 
			if (array[i].write_off3 != 3) {
				a = true;
			}
		}
	
			if (a== true) {
				$.messager.confirm('确认', '您确定要取消核销吗？', function(r) {
					if (r) {
						$.ajax({
							url : 'collect.do?method=editKuhu&pid=' + id,
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
				$.messager.alert('取消核销', '你选择的信息没有核销', 'info');
			}

	} */
 	function del() {
		var array = $("#dg").datagrid('getSelections');
			var id = [];
			
			
			var a = false;
		for ( var i = 0; i < array.length; i++) {
			id.push(array[i].custom_id); //注意修改你的id列 
			if (array[i].write_off3 != 3) {
				a = true;
			}
		}
	
			if (a== true) {
							$.messager.confirm('确认', '您确定要取消核销吗？', function(r) {
					if (r) {
						$.ajax({
							url : 'collect.do?method=editKuhu&pid=' + id,
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
document.onkeydown = function(event) {
  				e = event ? event : (window.event ? window.event : null);
  				if (e.keyCode == 13) {
  					//执行的方法 
  					searchCar_owner();
  				}
  			};	
  

  </script>
  <body class="easyui-layout">
  <form action="shipOrder.do" id="outputform" method="post">
    	<div region="north" title="检索" class="st-north" border="false" collapsible="false"  >
	    	<lable class="divlabel">客户名称:</lable>
	    	 <input type="text" class="search-text" id="custom_name"  style="width:100px"  >
	    	 <lable class="divlabel">客户发车发次:</lable>
	    	 <input type="text" class="search-text" id="car_time"  style="width:100px"  >
	    	 <lable class="divlabel">核销日期:</lable>
	    	 <input  id="kuhu_date"  class="Wdate"   style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
	    	 -
	    	  <input  id="aa"  class="Wdate"   style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
	    	  <lable class="divlabel">发车日期:</lable>
	    	 <input  id="send_time"  class="Wdate"   style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
	    	 -
	    	  <input  id="bb"  class="Wdate"   style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
	    	  <lable class="divlabel">到车日期:</lable>
	    	 <input  id="receipt_time"  class="Wdate"   style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
	    	 -
	    	  <input  id="cc"  class="Wdate"   style="width:50px;height:22px; " onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd '})" />
	    	 <a class="easyui-linkbutton" onclick="searchCar_owner()" data-options="iconCls:'icon-search'" >查询</a>	
	     <a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="margin-top:16px;" href="javascript:void(0)" state="close">更多条件</a>
	    	 <div id="searchInfoId" style="display: none">
	    	
			
				<td>核销:</td>
					<td ><select id='write_off3'  name="write_off3"  style="width:50px;height:22px">
							<option> </option>
							<option  value="0">打卡</option>
							<option  value="1">现金</option>
							<option  value="3">未核销</option>
					</select></td> 
					<lable class="divlabel">车牌号:</lable>
	    	 <input type="text" class="search-text" id="car_number"  style="width:100px"  >
	    	 <lable class="divlabel">司机姓名:</lable>
	    	 <input type="text" class="search-text" id="driver_name"  style="width:100px"  >
			</div>
    	</div>
    	<div region="center" >
    		<table id="dg" title="汇总信息" ></table>
    	</div>
    	<div id="tb">
   	<a href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-edit',plain:true"onclick="modify()">核销</a>
   	<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-edit',plain:true" onclick="del()">取消核销</a>
   	
		</div>	
		</form>	
  </body>
</html>