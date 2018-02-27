<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>制作协议信息管理</title>
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
<style type="text/css">
fieldset {
	border: 1px dotted #D1D7DC;
	/* margin: 10px; */
	padding: 8px;
	width: 854px;
}

.st-north {
	
}

.iner {
	margin-top: 10px;
}
.testStyle{
	vertical-align: middle;
	width: 150px;
	height: 22px
}
</style>

</head>
<%
	String pid = (String) request.getAttribute("pid");
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
<script type="text/javascript">
	  	var dialog;
 		var grid;
 		var typeid=0;
 		var pid=('<%=pid%>');
 		var checkarray=[];
 		var editval;
 		var chanageType = [{ "value": "0", "text": "回付" }, { "value": "1", "text": "到付" }, { "value": "2", "text": "现付" }];
  		$(function(){
  			$.ajax({
			url : 'createA.do?method=getUpdateCreateAgreement',
			data : {
				id : pid
			},
			success : function(data) {
				if(data.agreement_type==0){
					$("#cc3").css("display","");
				}
				data.send_date=data.send_date!=null?data.send_date.substr(0,19):'';
				 if(data.ldp_id!=''){
					var id=data.ldp_id;
					$.ajax({
  					type: "POST",
  					async : false,
  					url:'depn.do?method=getDepn',
  					data:{department_id:id},
  					success:function(data){
  						$("#cc3").val(data.department_name);
					//$("#departments").append("<li>"+data+"</li>");
  					}
  				});
  				}
				if(data.ndp_id!=''){
					var id=data.ndp_id;
					$.ajax({
  					type: "POST",
  					async : false,
  					url:'depn.do?method=getDepn',
  					data:{department_id:id},
  					success:function(data){
  					$("#ndp").val(data.department_name);	
  					//$("#departments").append("<li>"+data+"</li>");
  						}
  				});	
				}
				$("#carid").prev("div").find("span.select2-chosen").text(data.car_number1);
				$("#compid").prev("div").find("span.select2-chosen").text(data.company_name);
				$("#comp_ren").val(data.end_people);
				$("#comp_phone").val(data.end_phone);
				typeid=data.agreement_type;
				$('#Regform').form('load', data);
			}
		});
  			
  		$.ajax({
			url: "createA.do?method=getXXCreateAgreement",
			type: "POST",
			data:{id:pid},
  			success:function(msg){
  				$.each(msg,function(i,item){
  					checkarray.push(item.order_id);			
  				});
  			checkarray=checkarray.join(",");
			grid=$("#dg").datagrid({
  			url : "shipOrder.do?method=getShipOrderCAM&pid="+checkarray,
  			border : false,
  			rownumbers : true,
  			fit : true,					  		
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			multiSort:true,
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
					{field : 'practiacl_num',title : '实际送货件数',width : 60,align:'center'
						,editor:{
		                	type:'numberbox',
		                	options:{
		                    	validType:'practiacl_num'
		                	}
		       			}	
					},
					{field : 'goods_weight',title : '重量',width : 60,align:'center'},
					{field : 'goods_vol',title : '体积',width : 60,align:'center'},
					{field : 'receipt_address',title : '送货地址',width : 100,align:'center'},
					{field : 'trade_agency',title : '代收款',width : 70,align:'center'},
					{field : 'transport_pay',title : '运费总额',width : 70,align:'center'},
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
					{field : 'change_pay',title : '应付中转费',width : 80,align:'center',sortable:'true'
						,editor:{
		                	type:'numberbox',
		                	options:{
		                		precision:2,
		                    	validType:'trunk_fee'
		                	}
	           			},formatter:function(val,row,index){
	  							if(val==null||val==""){
	  								return '0.00';
	  							}else{
	  								return val;
	  							}
	  						}	
					},
					{field : 'send_fee',title : '应付送货费',width : 80,align:'center',sortable:'true'
						,editor:{
		                	type:'numberbox',
		                	options:{
		                		precision:2,
		                    	validType:'send_fee'
		                	}
		           		},formatter:function(val,row,index){
	  							if(val==null||val==""){
	  								return '0.00';
	  							}else{
	  								return val;
	  							}
	  						}	
					},
					{field : 'change_other',title : '中转其他费',width : 80,align:'center'
						,editor:{
		                	type:'numberbox',
		                	options:{
		                		precision:2,
		                    	validType:'change_other'
		                	}
		           		}	
					},
					{field : 'chanage_pay_type',title : '中转付款方式',width : 90,align:'center',
						 editor: { type: 'combobox', options: { data: chanageType, valueField: "value", textField: "text" } }
	  					,formatter:function(val,row,index){
								if(val==0){
									return '回付';
								}else if(val==1){
									return '到付';
								}else if(val==2){
									return '现付';
								}
						}	
					},
					{field : 'payble_other',title : '应付其他费',width : 80,align:'center',sortable:'true'},
					{field : 'send_remarks',title : '送货备注',width : 90,align:'center',sortable:'true'
						,editor:{
		                	type:'text',
		                	options:{
		                    	validType:'send_remarks'
		                	}
	           			}		
					} ,
					{field : 'change_remarks',title : '中转备注',width : 90,align:'center',sortable:'true'
						,editor:{
		                	type:'text',
		                	options:{
		                    	validType:'change_remarks'
		                	}
	           			}	
					}
  			]],	
  			onBeforeEdit:function(index,row){ //可编辑之前
				editval=index;
		        row.editing = true;
		    },  
		    onAfterEdit:function(index,row){//编辑关闭后
				var bool=true;
		    	if(typeid!=0){
					if(typeid==2){
						if(row.trunk_fee==""){
				    		$('#dg').datagrid("updateRow", {index:index,row:{trunk_fee:'0.00'}});
							bool=false;
				    	}
					}else if(typeid==1){
			    		if(row.practiacl_num==""){
							bool=false;
							$('#dg').datagrid("updateRow", {index:index,row:{practiacl_num:'0'}});
						}
				    	if(row.take_fee==""){
				    		bool=false;
				    		$('#dg').datagrid("updateRow", {index:index,row:{take_fee:'0.00'}});
				    	}
			    	}
		    	}
		    	compute();
	  			if(typeid==0){
				}else if(typeid==1){
					$.ajax({url:'createA.do?method=updataMoney',data:{id:row.shiping_order_id,practiacl_num:row.practiacl_num,send_fee:row.send_fee,send_remarks:row.send_remarks},success:function(){
					}});
				}else if(typeid==2){
					$.ajax({url:'createA.do?method=updataMoney',data:{id:row.shiping_order_id,change_pay:row.change_pay,change_remarks:row.change_remarks,chanage_pay_type:row.chanage_pay_type,change_other:row.change_other},success:function(){
			    	}});
				}
		    	
		        row.editing = false;  
		    },
		    //单击事件
            onClickRow: function (rowIndex,rowData) {
                //单击开启编辑行
    				if(editval!=rowIndex){
    					$("#dg").datagrid("endEdit", editval);
    					$("#dg").datagrid("beginEdit", rowIndex);
	                	editval=rowIndex;
    				} else{
    					editval=undefined;
    					$('#dg').datagrid("endEdit", rowIndex);
    				} 
            },
  			onLoadSuccess:function(){
				 if(typeid==0){
					 $("#dg").datagrid('hideColumn', 'practiacl_num');
					 $("#dg").datagrid('hideColumn', 'send_fee');
					 $("#dg").datagrid('hideColumn', 'change_pay');
					 $("#dg").datagrid('hideColumn', 'change_remarks');
					 $("#dg").datagrid('hideColumn', 'send_remarks');
					 $("#dg").datagrid('hideColumn', 'chanage_pay_type');
					 $("#dg").datagrid('hideColumn', 'change_other');
				 }else if(typeid==2){
					 $("#dg").datagrid('hideColumn', 'practiacl_num');
					 $("#dg").datagrid('hideColumn', 'send_fee');
					 $("#dg").datagrid('hideColumn', 'send_remarks');
					 $("#dg").datagrid('showColumn', 'change_pay');
					 $("#dg").datagrid('showColumn', 'change_remarks');
					 $("#dg").datagrid('showColumn', 'chanage_pay_type');
					 $("#dg").datagrid('showColumn', 'change_other');
				 }else{
					 $("#dg").datagrid('showColumn', 'practiacl_num');
					 $("#dg").datagrid('showColumn', 'send_fee');
					 $("#dg").datagrid('showColumn', 'send_remarks');
					 $("#dg").datagrid('hideColumn', 'change_pay');
					 $("#dg").datagrid('hideColumn', 'change_remarks');
					 $("#dg").datagrid('hideColumn', 'chanage_pay_type');
					 $("#dg").datagrid('hideColumn', 'change_other');
				 }
            },
  			pagination:true,//分页
		    pageSize:10,
		    pageList:[5,10,15,20],		
  		});
  			}
 		});	
  		
	depart='<%=user.getDid()%>';
  	 $("#user").val('<%=user.getUsername()%>');
		$("#carid").select2({
			placeholder : "请输入车牌号", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "shipOrder.do?method=getPlateNumber",
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
						});
						query.callback(data);
					}
				});
			}
		}).on("select2-selecting", function(f) {
			$("#car").val(f.object.id);
			$("#carnum").val(f.object.text);
			var id = f.object.id;
			var url = 'driver.do?method=getDriverLength&search=' + id;
			//过滤职务
			$('#driver').combobox('clear');
			$('#driver').combobox('reload', url);
		});

		
		
		$('#type').combobox({
			onChange : function(newValue, oldValue) {
				if (newValue == "0") {
					$("#jigou").removeAttr("style");
					$("#phone").attr("style", "display:none");
					$("#ren").attr("style", "display:none");
					$("#company").attr("style", "display:none");
					$("#company_number").attr("style", "display:none");
					$("#send_date").attr("style", "display:none");
					$("#change_type").attr("style", "display:none");
				} else if (newValue == "2") {
					$("#jigou").attr("style", "display:none");
					$("#send_date").attr("style", "display:none");
					$("#company").removeAttr("style");
					$("#phone").removeAttr("style");
					$("#ren").removeAttr("style");
					$("#company_number").removeAttr("style");
					$("#change_type").removeAttr("style");
				} else {
					$("#change_type").attr("style", "display:none");
					$("#jigou").attr("style", "display:none");
					$("#phone").attr("style", "display:none");
					$("#ren").attr("style", "display:none");
					$("#company").attr("style", "display:none");
					$("#company_number").attr("style", "display:none");
					$("#send_date").removeAttr("style");

				}
			}
		});
		/*部门查询  */
		$("#compid").select2({
			placeholder : "请输入中转公司名", //默认文本框显示信息
			minimumInputLength : 1, //最小输入字符长度
			allowClear : true,
			multiple : false, //设置多项选择，false则只能单选
			maximumSelectionSize : 5, //最大选择个数
			query : function(query) {
				$.ajax({
					type : "POST",
					url : "createA.do?method=getCompany",
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
								text : y.name,
								ren : y.ren,
								phone : y.phones
							});
						});
						query.callback(data);
					}
				});
			}
		});

	});
  	//计算总金额
	 function compute() {
		 	    var rows = $('#dg').datagrid('getRows');//获取当前的数据行take_fee
		 	    var total=0;
		 	    if(typeid==0){
		 	    	
		 	    }else if(typeid==1){
		 	    	for (var i = 0; i < rows.length; i++) {
		  		        total =Number(Number(rows[i].send_fee)+Number(total)).toFixed(2) ;
		  		    }
		 	    }else if(typeid==2){
		 	    	for (var i = 0; i < rows.length; i++) {
		  		        total =Number(Number(rows[i].change_pay)+Number(total)).toFixed(2) ;
		  		    }
		 	    }
		 	   	$.ajax({url:'createA.do?method=updataZong',data:{cid:pid,money:total},success:function(){
				}});
			    $("#alls").val(total);
	}
  		
  	function deletefrom(){
  		$.messager.confirm('确认','您确定从协议中移除选中的记录吗？',function(r){ 
		  		if(r){
			  		var check=[];
				 	var array = $('#dg').datagrid('getSelections');
			  		if (editval != undefined)
			  	    {
			  			$('#dg').datagrid('endEdit', editval);
			  	    }
			  		for(var i=0;i<array.length;i++){
				  		check.push(array[i].shiping_order_id);
				  		var checkedRow = array[i];
			            var checkedRowIndex = $('#dg').datagrid('getRowIndex', checkedRow);
			            $('#dg').datagrid('deleteRow', checkedRowIndex);  
			            var rows = $('#dg').datagrid("getRows");
			            $('#dg').datagrid("loadData", rows);
			  		}
			  		if(array != ""){
				  		var rows = $('#dg').datagrid('getRows');
			  			if(rows.length>1||rows.length<array.length){
  				
	  			  		$.ajax({
	  						type : "POST",
	  						url : "createA.do?method=removeShip&cid=" + pid,
	  						data : {
	  							pid :check.join(",")
	  						}, //传递你输入框中的值
	  						success:function(data){
	  								if (data.flag) {
	  									parent.$.messager.alert("协议信息","已从协议中移除",'info',
	  									function(r) {
	  										compute();
	  									});
	  								} 
	  						}
	  					});
  			  		}else{
  		  				$.messager.alert("协议详细","无法移除,您可以尝试作废协议","info");
  		  			}
  			}else{
  	  			$.messager.alert("协议详细","请选择要移除的订单","info");
  	  		}
  		}
  	  });
  	}
  	
</script>
<body class="easyui-layout">
	<form action="shipOrder.do" id="Regform" method="post">
		<div region="north" class="st-north" height="175px" border="false"
			collapsible="false">
			<fieldset>
				<legend>协议信息</legend>
				<div>
				<input name="agreement_id" type="hidden" /> <input
					name="agreement_number" type="hidden" />
				<lable>托运机构:</lable>
				<input id="ndp" class="search-text" readonly="true" type="text" />
				<input id="ndpid" name="ndp_id" class="search-text" readonly="true"
					type="hidden" /> 	 
				<lable>类型:</lable>
				<select class="easyui-combobox" readonly="true" id="type"
					name="agreement_type" style="width:150px;"
					data-options="panelHeight : 'auto',editable:false">
					<option id="" value="0">班线</option>
					<option id="" value="1">配送</option>
					<option id="" value="2">转运</option>
				</select>
				<lable id="send_date" style="display: none">配送日期: <input
					id="send_date2" class="search-text" name="send_date"
					readonly="true"></lable>
				<lable  id="jigou" class="divlabel">下一机构:<input   id="cc3"  class="search-text" readonly="true"></lable>
				<lable id="company" class="divlabel" style="display: none">中转公司:
				<input id="cid" type="hidden" name="company_id" class="search-text">
				<input id="compid" type="text" class="search-text" readonly="true"></lable>
				<lable id="phone" class="divlabel" style="display: none">电话:
				<input id="comp_phone" class="search-text" name="company_tel"
					readonly="true"></lable>
				</div>
				<!-- <lable  id="ren"  style="display: none">到站联系人:
			<input    id="comp_ren" class="search-text c" readonly="true"></lable> -->
				<div class="iner">
				<lable id="company_number" style="display: none">转运单号: <input
					id="company" class="search-text" name="company_number"
					readonly="true"></lable>
				</div>
			</fieldset>
			<fieldset>
				<legend>派车单信息</legend>
				<div>
				<lable>车牌号:</lable>
				<input id="carid" readonly="true" type="hidden" class="search-text"
					onclick="compute()" /> <input id="carnum" type="hidden"
					class="search-text" name="car_number1" /> <input id="car"
					type="hidden" class="search-text" name="car_id" />
				<lable class="divlabel">总运费:</lable>
				<input id="alls" name="all_money" readonly="true"
					class="search-text" />
				<lable>司机:</lable>
				<input id="driver" name="car_name" readonly="true"
					class="search-text" />
				<lable>联系方式:</lable>
				<input id="phone" name="phone_number" class="search-text"
					readonly="true" />
				</div>
				<div style="padding-top:10px;">
				<lable>备注:</lable>
				<input id="note"readonly="true"
					name="remarks_send" class="testStyle"/> <input id="user" class="testStyle" type="hidden"
					 name="create_user" /> <input id="num" type="hidden" name="number" class="testStyle"/>
				</div>
			</fieldset>
		</div>

		<div region="center">
			<table id="dg" title="订单信息">
		    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deletefrom()">删除</a>
			</table>
		</div>
	</form>
</body>
</html>