<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo" %>
<%@ page import="com.jy.model.*" %>

<% User user=(User)request.getSession().getAttribute(SessionInfo.SessionName); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>查看司机评价页面</title>

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

</head>
<script type="text/javascript">
	var flag='<%=user.getFlag()%>';
	var grid;
	$(function(){
		grid = $("#dg").datagrid({
			url : 'searchComment.do?method=getCommentList',
			border : false,
  			rownumbers : true,
  			fit : true,
  			singleSelect : false,
  			selectOnCheck : true,
  			checkOnSelect :true,
  			multiSort:true,
			columns : [ [ 
			{	field : 'ck',
			 	checkbox : true
			},
			{
				field : 'shiping_order_num',
				title : '货运编号',
				
				align : 'center'
			},
			{
				field : 'send_mechanism',
				title : '受理机构',
				
				align : 'center'
			},
			{
				field : 'end_address',
				title : '终到位置',
				
				align : 'center'
			},
			{
				field : 'receipt_name',
				title : '到货联系人',
				
				align : 'center'
			},
			{
				field : 'receipt_tel',
				title : '到货联系人电话',
				
				align : 'center'
			},
			{
				field : 'goods_name',
				title : '货物名称',
				
				align : 'center'
			},
			{
				field : 'goods_num',
				title : '货物总件数',
				
				align : 'center'
			},
			{
				field : 'driver_name',
				title : '司机姓名',
				
				align : 'center'
			},
			{
				field : 'comment_name',
				title : '评论人',
				
				align : 'center'
			},
			{
				field : 'xing',
				title : '评论分数',
				
				align : 'center'
			},
			{
				field : 'remark',
				title : '评论备注',
				
				align : 'center'
			},
			{
				field : 'comment_data',
				title : '评论时间',
				
				align : 'center'
			}]],
			pagination : true,//分页
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			toolbar : '#tb'
		});
	});
	//查询按钮	
	function search_Comment(){
  		$("#dg").datagrid('load',{
	 	 		shiping_order_num :$("#shiping_order_num").val(),
	 	 		send_mechanism :$("#send_mechanism").val(),
	 	 		end_address :$("#end_address").val(),	
	 	 		driver_name :$("#driver_name").val(),
	 	 		receipt_name :$("#receipt_name").val(),
	 	});	  
	 	
  	}
  //更多条件	
  function btnMoreSearch() {
  var btnMoreSearch=$("#btnMoreSearch");
		 if ($("#btnMoreSearch").attr("state") == "close") {
		 //主要代码 
			$('.easyui-layout').layout('panel', 'north').panel('resize',{height:92});
			$('.easyui-layout').layout('resize');
			btnMoreSearch.text("收起条件");
			btnMoreSearch.attr("state", "open");
			$("#searchInfoId").css("display","block");
			$("#driver_name").val("");
			$("#receipt_name").val("");
		} else {
			$('.easyui-layout').layout('panel', 'north').panel('resize',{height:55});
			$('.easyui-layout').layout('resize');
			btnMoreSearch.text("更多条件");
			btnMoreSearch.attr("state", "close");
			$("#searchInfoId").css("display","none");
		}
};
//重置
function reset() {
	$("#shiping_order_num").val("");
	$("#send_mechanism").val("");
	$("#end_address").val("");	
	$("#driver_name").val("");
	$("#receipt_name").val("");
}
function deleteEver(){
	var checkarray = [];
		var array = $('#dg').datagrid('getSelections');
		for ( var i = 0; i < array.length; i++) {
			checkarray.push(array[i].comment_id);
		}
		
		if (array != "") {
			$.messager.confirm('确认', '您确定要删除选中的记录吗？', function(r) {
				if (r) {
					$.ajax({
						url : "searchComment.do?method=deleteComment",
						type : "POST",
						data : {
							comment_id : checkarray.join(",")
						},
						success : function(data) {
							if (data.flag) {
								parent.$.messager.alert("信息删除","信息删除成功",
										'info', function(r) {
											$('#dg').datagrid('reload');
										});
							}else{
							parent.$.messager.alert("信息删除","信息删除失败",
										'info', function(r) {
											$('#dg').datagrid('reload');
										});
							}
						}
					});
				}
			});
		} else {
			parent.$.messager.alert("信息提示", "请选择要删除的信息", 'info');

		}
	}
	function putoutfile(){
	     var allRows2 = $("#dg").datagrid("getColumnFields");
		var row = $("#dg").datagrid("getSelections");
		var colName = [];
		var fieldName = [];
		var dataIds = [];
		for (i = 1; i < allRows2.length; i++) {
			var col = $('#dg').datagrid("getColumnOption", allRows2[i]);
			if (col.field != "check_type" && col.field != "send_type"
					&& col.field != "shipping_order_state") {
				if (flag == 0) {
					colName.push(col.title);//把title列名到数组里去 
					fieldName.push(col.field); //把field列属性名到数组里去 	
				} else {
					if (col.field != "deliver_fee"
							&& col.field != "upstairs_fee"
							&& col.field != "added_fee") {
						colName.push(col.title);//把title列名到数组里去 
						fieldName.push(col.field); //把field列属性名到数组里去 	
					}
				}
			}
		}

		for (i = 0; i < row.length; i++) {
			dataIds.push(row[i].comment_id);
		}
		$("#fieldName").val(fieldName.join(","));
		$("#headtitle").val(colName.join(","));
		$("#dataIds").val(dataIds.join(","));

		var outPutForm = $("#outputform");
		$("#method").val("");
		$("#method").val("allMessageOutPut");
		outPutForm.submit();
	
	}
</script>
<body  class="easyui-layout">
    <form action="searchComment.do" id="outputform" method="post">
    	<div region="north" title="检索" class="st-north" border="false" collapsible="false">
			<lable>货运编号:</lable>
			<input type="text" class="search-text" id="shiping_order_num" style="width:100px" name="shiping_order_num">
			<lable>受理机构:</lable>
			<input type="text" class="search-text" id="send_mechanism" name="send_mechanism">
			<lable>终到位置:</lable>
			<input type="text" class="search-text" style="width:150px" id="end_address" name="end_address">
			<a class="easyui-linkbutton" onclick="search_Comment()" data-options="iconCls:'icon-search'">查询</a> 
			<a id="btnMoreSearch" class="btn btn-sm btn-link" type="button" style="margin-top:16px;"
				href="javascript:void(0)" state="close" onclick="btnMoreSearch()">更多条件</a>
			<div id="searchInfoId"  style="display:none">
				<label>司机姓名:</label>
				<input type="text" class="search-text" style="width:150px" id="driver_name" name="driver_name">
				<label>收货客户姓名:</label>
				<input type="text" class="search-text" style="width:150px" id="receipt_name" name="receipt_name">
			</div>
		</div>
		<div region="center" >
			<table id="dg" title="评价信息"></table>
			<input type="hidden" name="method" value="putoutfile"
			id="method"> <input type="hidden" id="fieldName"
			name="fieldName"> <input type="hidden" id="headtitle"
			name="headtitle"> <input type="hidden" id="dataIds"
			name="dataIds">
		</div>	
    <div id="tb">
    </div>
     <input type="hidden"  id="comment_id"  name="comment_id">
    </form>
	
</body>
</html>
