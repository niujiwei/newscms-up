<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.text.*"%>

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
	width: 994px;
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
	String[] pid = (String[]) request.getAttribute("flg");
	User user = (User) request.getSession().getAttribute(
			SessionInfo.SessionName);
%>
<script type="text/javascript">
	  	var dialog;
 		var grid;
 		var pid=[];
 		var editval;
 		var typeid=0;
 		var chanageType = [{ "value": "0", "text": "回付" }, { "value": "1", "text": "到付" }, { "value": "2", "text": "现付" }];
 		<%for (int i = 0; i < pid.length; i++) {%>
 			pid.push('<%=pid[i]%>');
 		<%}%>
 		pid=pid.join(",");
  		$(function(){
  			grid=$("#dg").datagrid({
  			url : 'shipOrder.do?method=getShipOrderCAM&pid='+pid,
  			border : false,
  			rownumbers : true,
  			fit : true,
  			singleSelect : true,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			multiSort:true,
  		/* 	remoteSort:true, */
  			columns : [[
  				{field : 'shiping_order_num',title : '订单号',width : 50,align:'center'}, 				
  				/* {field : 'send_station',title : '起运地',width : 50,align:'center'}, */
  				{field : 'end_address',title : '到达地',width : 50,align:'center'},
  				{field : 'receipt',title : '收货人',width : 50,align:'center'},
  				{field : 'receipt_tel',title : '电话',width : 80,align:'center'},
  				{field : 'pay_type',title : '付款方式',width : 60,align:'center'
  	  				,/* editor: { type: 'combobox', options: { data: sendType, valueField: "value", textField: "text" } }
  	  				, */formatter:function(val,row,index){
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
  	  			{field : 'transport_pay',title : '运费总额',width : 60,align:'center'},
  	  			{field : 'is_recept',title : '回单',width : 50,align:'center'},
  	  			{field : 'trade_agency',title : '代收款',width : 50,align:'center'},
  	  			{field : 'practiacl_num',title : '实际送货件数',width : 90,align:'center'
	  	  			,editor:{
	                	type:'numberbox',
	                	options:{
	                    	validType:'practiacl_num'
	                	}
	       			}/* ,formatter:function(val,row,index){
  							if(val==null||val==""){
  								return row.goods_num;
  							}else{
  								return val;
  							}
  						} */
  	  			},
  	  			{field : 'change_pay',title : '应付中转费',width : 80,align:'center'
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
				{field : 'send_fee',title : '应付送货费',width : 80,align:'center'
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
								return "回付";
							}else if(val==1){
								return "到付";
							}else if(val==2){
								return "现付";
							}
					}	
				},
				{field : 'goods_name',title : '物品名称',width : 60,align:'center'},
  				{field : 'goods_num',title : '货物数量',width :60,align:'center'},
  				{field : 'goods_weight',title : '总重量',width : 50,align:'center'},
  				{field : 'goods_vol',title : '总体积',width : 50,align:'center'},
  				{field : 'send_type',title : '交接方式',width : 60,align:'center',
  					formatter:function(val,row,index){
  						if(val==0){
  							return "自提";
  						}else if(val==1){
  						return "送货";
  						}
  					}
  				},
  				{field : 'receipt_address',title : '送货地址',width : 60,align:'center'},
				{field : 'change_remarks',title : '中转备注',width : 300,align:'center'
					,editor:{
	                	type:'text',
	                	options:{
	                    	validType:'change_remarks'
	                	}
           			}
				},
				{field : 'send_remarks',title : '送货备注',width : 300,align:'center'
					,editor:{
	                	type:'text',
	                	options:{
	                    	validType:'send_remarks'
	                	}
           			}	
				},
  			]],	
  			onBeforeEdit:function(index,row){ //可编辑之前
				editval=index;
		        row.editing = true;
		    },  
		    onAfterEdit:function(index,row){//编辑关闭后
		    	/* var price=Number(Number(row.take_fee)+Number(row.send_fee)+Number(row.adorn_fee)+Number(row.trunk_fee)+Number(row.insurance)).toFixed(2);
		    	$('#dg').datagrid("updateRow", {index:index,row:{transport_pay:price}});
		    	$('#dg').datagrid("endEdit",editval); */
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
		    	
		    	Nubmer(row.trunk_fee)
  				/* $('#dg').datagrid("updateRow", {index:index,row:{gain_total:price}}); */
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
            	 
            	 compute();
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
					 $("#dg").datagrid('showColumn', 'change_other');
					 $("#dg").datagrid('showColumn', 'chanage_pay_type');
					 $("#dg").datagrid('showColumn', 'change_pay');
					 $("#dg").datagrid('showColumn', 'change_remarks');
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
  		    $("#alls").val(total);
  		}
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
		/*部门查询  */
		depart='<%=user.getDid()%>';
		$("#ndpid").val(depart);
		$.ajax({
  			type: "POST",
  			async : false,
  			url:'depn.do?method=getDepn',
  			data:{department_id:depart},
  			success:function(data){
  			$("#ndp").val(data.department_name);
  			//$("#ndpid").val(data.department_id);
  			//$("#departments").append("<li>"+data+"</li>");
  			}
  		});
		
		$('#driver2').combobox({
			url: '',
			valueField: 'id',
			textField: 'text',
			editable: false,
			onSelect: function (rec) {
				$("#name").val(rec.text);
					$.ajax({
						url: "driver.do?method=getPhone",
						type: "POST",
						data:{id:rec.id},
			  			success:function(data){
			  				$("#phones").val(data.phoneNumber)
			  				}
			  			});
					},
					onLoadSuccess: function (rec) {
					}
			});
		/*
  		select框
  	*/ 	
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
					url : "shipOrder.do?method=getPlateNumberLength",
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
			id = f.object.id;
			var url = 'driver.do?method=getDriverLength&search=' + id;
			//过滤职务
			$('#driver').combobox('clear');
			$('#driver').combobox('reload', url);
		});

		$('#type').combobox({
			onChange : function(newValue, oldValue) {
				/* if(newValue !="0"){
				$("#jigou").attr("style", "display:none");	
				}else{
				$("#jigou").removeAttr("style");
				} */
				typeid = newValue;
				if (newValue == "0") {
					$("#jigou").removeAttr("style");
					$("#phone").attr("style", "display:none");
					$("#ren").attr("style", "display:none");
					$("#company").attr("style", "display:none");
					$("#bian").attr("style", "display:none");
					$("#company_number").attr("style", "display:none");
					$("#comp_phone").val("");
					$("#comp_ren").val("");
					$("#compName").select2("val", "");
					$("#cid").val("");
					$("#dg").datagrid('reload');
					$("#fei").html("运费总额:");
					$("#take_data").attr("style", "display:none");
					$("#beizhu").html("备注:");
				} else if (newValue == "2") {
					$("#jigou").attr("style", "display:none");
					$("#company_number").removeAttr("style");
					$("#bian").removeAttr("style");
					$("#company").removeAttr("style");
					$("#phone").removeAttr("style");
					$("#ren").removeAttr("style");
					$("#cc3").combotree('setValue', "");
					$("#dg").datagrid('reload');
					$("#fei").html("中转费总额:");
					$("#take_data").attr("style", "display:none");
					$("#beizhu").html("中转备注:");
				} else {
					$("#jigou").attr("style", "display:none");
					$("#phone").attr("style", "display:none");
					$("#ren").attr("style", "display:none");
					$("#company").attr("style", "display:none");
					$("#bian").attr("style", "display:none");
					$("#company_number").attr("style", "display:none");
					$("#cc3").combotree('setValue', "");
					$("#comp_phone").val("");
					$("#comp_ren").val("");
					$("#compName").select2("val", "");
					$("#cid").val("");
					$("#dg").datagrid('reload');
					$("#fei").html("配送费总额");
					$("#take_data").removeAttr("style", "display:none");
					$("#beizhu").html("配送备注:");
				} /*else if (newValue == "0") {
										$("#jigou").removeAttr("style");
									}else if(newValue == "2"){
										$("#jigou").removeAttr("style");
									}  */
			}
		});
		$("#type").combobox("setText", "班线");

		$("#comid").select2({
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
		}).on("select2-selecting", function(f) {
			$("#cid").val(f.object.id);
			$("#comp_ren").val(f.object.ren);
			$("#comp_phone").val(f.object.phone);
			//$("#carnum").val(f.object.text);
			/* var id=f.object.id;
			var url = 'driver.do?method=getDriverLength&search=' +id;
			//过滤职务
			$('#driver').combobox('clear');
			$('#driver').combobox('reload', url); */
		});

	});//createA.do?method=getCompany

	function compute() {
		var rows = $("#dg").datagrid('getRows');//获取当前的数据行
		var total = 0;
		for (var i = 0; i < rows.length; i++) {
			total = parseInt(rows[i]['transport_pay']) + total;
		}
		$("#alls").val(total + ".00");
	}
	//关闭未关闭的可编辑行
		function endEdits(){
			var rows = $("#dg").datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				$("#dg").datagrid('endEdit', i);
			}
		}
	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {
		endEdits();
		if ($("#type").combotree("getValue") == 0&& $("#cc3").combotree("getValue") == "") {
			$pjq.messager.alert('新增协议', '班线必须填写下一机构', 'info');
		}else if($("#type").combotree("getValue") == 2 && ($("#compid").val()=='' || $("#compid").val()==null)){
			$pjq.messager.alert('新增协议', '中转必须填写中专公司', 'info');
		}else {
			if ($("#car").val() == "" || $("#car").val() == null) {
				$pjq.messager.alert('新增协议', '车牌号不可为空', 'info');
			} else if ($("#driver").val() == ""|| $("#driver").val() == null) {
				$pjq.messager.alert('新增协议', '司机不可为空', 'info');
			} else {
				$pjq.messager.confirm('新增协议', '确定要新增吗?', function(r) {
					if (r) {
						$.ajax({
							type : "POST",
							url : 'createA.do?method=saveCreateAgreement&pid='
									+ pid,
							data : $('#Regform').serialize(),
							success : function(msg) {
								if (msg.flag) {
									$pjq.messager.alert('新增协议', '协议添加成功',
											'info');
									$dialog.dialog('close');
									$grid.datagrid('reload');
								}
								;
							}
						});
					}
				});
			} 
		}

	};
	/* $("#bian").toggle(function(){
		alert("2");
	},function(){
		alert("3");
	}); */
	function select() {
		dialog = parent.jy.modalDialog({
			title : '选择中专公司',
			url : 'createA.do?method=selectAgreement',
			width : 940,
			height : 485,
			resizable : true,
			buttons : [ {
				text : '<input type="button"  class="btncss" value="确定"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	}
	function selectcar() {
		dialog = parent.jy.modalDialog({
			title : '选择车辆',
			url : 'createA.do?method=selectCar',
			width : 940,
			height : 485,
			id:'f',
			resizable : true,
			buttons : [ {
				text : '<input type="button"  class="btncss" value="确定"/>',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(
							dialog, grid, parent.$);
				}
			} ]
		});
	}
</script>
<body class="easyui-layout">
	<form action="shipOrder.do" id="Regform" method="post">
		<div region="north" class="st-north" height="205px" border="false"
			collapsible="false">
			<fieldset>
				<legend>协议信息</legend>
				<div>
				<input name="agreement_id" type="hidden" /> <input
					name="agreement_number" type="hidden" />
				<lable>托运机构:</lable>
				<input id="ndp" class="search-text" readonly="true" type="text" />
				<input id="ndpid" class="search-text" type="hidden" name="ndp_id" />
				<lable>类型:</lable>
				<select class="easyui-combobox" id="type" name="agreement_type"
					style="width:150px;"
					data-options="panelHeight : 'auto',editable:false">
					<option id="" value="0">班线</option>
					<option id="" value="1">配送</option>
					<option id="" value="2">转运</option>
				</select>
				<%
					String data2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(Calendar.getInstance().getTime());
				%>
				<lable id="take_data" style="display: none">时间: <input
					id="send_date" name="send_date" class="Wdate" value="<%=data2%>"
					readonly="readonly" style="width:150px;height:22px; "
					onfocus="WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</lable>
				<lable id="jigou" class="divlabel">
				<font color="red" style="margin-right:10px">*</font>下一机构: <input
					id="cc3" name="ldp_id" class="search-text"></lable>
				<lable id="company" class="divlabel" style="display: none"
					value="nicaiz">
				<font color="red" style="margin-right:10px">*</font>中转公司: <input
					id="compName" name="companyName" class="search-text"> <input
					id="compid" name="company_id" type="hidden" class="search-text">
				</lable>
				<!--<input   id="cid"  type="hidden"  name="company_id"  class="search-text">
			<input   id="compid"  type="hidden" class="search-text">  -->

				<a class="easyui-linkbutton" id="bian" onclick="select()"
					data-options="iconCls:'icon-search'" style="display:none">选择</a>
				<lable id="phone" class="divlabel" style="display: none">电话:
				<input id="comp_phone" name="company_tel" class="search-text"></lable>
				</div>
				<div class="iner">
				<lable id="ren" style="display: none">到站联系人:
				<input id="comp_ren" name="" class="testStyle"></lable>
				<lable id="company_number" style="display: none" class="divlabel">转运单号:
				<input id="company_number" name="company_number"
					class="testStyle"></lable>
				</div>
			</fieldset>
			<fieldset>
				<legend>派车单信息</legend>
				<div>
				<lable>
				<font color="red" style="margin-right:10px">*</font>车牌号:</lable>
				<input id="canum" class="search-text" type="hidden" name="car_id" />
				<input id="car" class="search-text" name="car_number1" /> <a
					class="easyui-linkbutton" id="biancar" onclick="selectcar()"
					data-options="iconCls:'icon-search'">选择</a>
				<lable>
				<!-- <font color="red" style="margin-right:10px">*</font> -->司机:<input
					id="driver" name="car_name" class="search-text" /> </lable>
				<lable class="divlabel" id="fei">总运费:</lable>
				<input id="alls" name="all_money" class="search-text" />
				<lable class="divlabel">联系方式:<input id="phones"
					name="phone_number" class=" search-text "></lable>
				</div>
				<div class="iner">
				<lable id="beizhu" >备&nbsp;注:</lable>
				<input id="note" class="testStyle" name="remarks_send" /> <input
					id="user" type="hidden" class="testStyle" name="create_user" />
				<input id="num" type="hidden" class="testStyle" name="number" />
				</div>
			</fieldset>
		</div>

		<div region="center">
			<table id="dg" title="订单信息"></table>
		</div>
		<input type="hidden" id="dialog2" />
	</form>
</body>
</html>