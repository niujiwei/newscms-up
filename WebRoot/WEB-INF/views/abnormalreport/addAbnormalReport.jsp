<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.jy.common.SessionInfo"%>
<%@ page import="com.jy.model.User"%>


<!DOCTYPE HTML>
<html>
<head>


<title>异常反馈添加</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="js/json2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
<script type="text/javascript" src="./js/function.js"></script>
<link rel="stylesheet" href="./js/select2/select2.css" type="text/css"></link>
<script type="text/javascript" src="./js/select2/select2.js"></script>
<script type="text/javascript"
	src="./js/select2/select2_locale_zh-CN.js"></script>
<script type="text/javascript" src="./js/select2/select2_expand.js"></script>
<script type="text/javascript" src="./webuploader/webuploader.js"></script>
<link rel="stylesheet" type="text/css"
	href="./webuploader/webuploader.css" />
<link rel="stylesheet" type="text/css" href="./webuploader/style.css" />
<script type="text/javascript" src="./webuploader/$webupload.js"></script>
<%
	String shiping_order_id = (String) request
			.getAttribute("shiping_order_id");
	 User user=(User)request.getSession().getAttribute(SessionInfo.SessionName);
%>
</head>
<script type="text/javascript">
    var $upload;
	$(function() {
	    $upload = $webupload("abnormalreport.do?method=uploadImage");//上传图片
	    var shiping_order_id ='<%=shiping_order_id%>';
		$.ajax({
			url : 'abnormalreport.do?method=getShipOrderMsg',
			data : {
				shiping_order_id : shiping_order_id
			},
			success : function(data) {
				$("#order").form('load', data);
			}
		});
		var type1 = [ {
			"id" : 1,
			"text" : "外好内部破损",
			"selected" : true
		}, {
			"id" : 2,
			"text" : "外好内部挤压"
		}, {
			"id" : 3,
			"text" : "外好内部变形",
		}, {
			"id" : 4,
			"text" : "外好内部渗漏"
		}, {
			"id" : 5,
			"text" : "外好内部污染"
		}, {
			"id" : 6,
			"text" : "外好内部短少"
		}, {
			"id" : 7,
			"text" : "外包装破损"
		}, {
			"id" : 8,
			"text" : "外包装挤压"
		}, {
			"id" : 9,
			"text" : "外包装变形",
		}, {
			"id" : 10,
			"text" : "外包装渗漏"
		}, {
			"id" : 11,
			"text" : "外包装污染"
		}, {
			"id" : 12,
			"text" : "外包装短少"
		}, {
			"id" : 13,
			"text" : "整箱少货"
		}, {
			"id" : 14,
			"text" : "无回单"
		}, {
			"id" : 15,
			"text" : "其它"
		} ];
		var type2 = [ {
			"id" : 21,
			"text" : "客户电话无人接听",
			"selected" : true
		}, {
			"id" : 22,
			"text" : "客户电话关机"
		}, {
			"id" : 23,
			"text" : "客户电话停机",

		}, {
			"id" : 24,
			"text" : "客户联系不上"
		}, {
			"id" : 25,
			"text" : "客户不方便收货"
		}, {
			"id" : 26,
			"text" : "客户出差等通知收货"
		}, {
			"id" : 27,
			"text" : "其它"
		} ];
		$("#abnormal_realson").combobox({
			editable : true,
			valueField : 'id',
			textField : 'text',
			onSelect : function(param) {

			}
		});
		$("#abnormal_type").combobox({
			editable : false,
			value : "",
			onSelect : function(param) {
				if (param.value == 1) {
					$("#abnormal_realson").combobox("loadData", type1);
				}
				if (param.value == 2) {
					$("#abnormal_realson").combobox("loadData", type2);
				}

			}
		});

		//结束
	});

	var submitForm = function($dialog, $grid, $pjq, $mainMenu) {

		var files = $upload.getFiles();
        if (files.length > 0) {
           var successNum =  $upload.getFiles('complete').length;
           var filesNum=files.length;


           if(successNum==filesNum){
            successUpload($dialog, $grid, $pjq, $mainMenu);
           }else{
            $upload.upload();//上传图片
            $upload.onUploadSuccess = function(file, response) {//上传成功
				successUpload($dialog, $grid, $pjq, $mainMenu);
			};
			$upload.onUploadError = function(file, reason) {
				$.messager
						.confirm(
								'上传图片失败',
								'是否重新上传，是：重新上传上报，否：直接上报，取消图片?',
								function(r) {
									if (r) {
									   uploader.retry();//重置
									} else {
									   successUpload($dialog, $grid, $pjq, $mainMenu);
									}
								});
			};
		    }
		}else{
		   successUpload($dialog, $grid, $pjq, $mainMenu);
    }
    };
	
	function successUpload($dialog, $grid, $pjq, $mainMenu) {
	    var abnormal_message = $("#abnormal_realson").combobox('getText');
		$("#abnormal_message").val(abnormal_message);
		var abnormal_type=$("#abnormal_type").combobox('getText');//异常类型
		var abnormal_realson= $("#abnormal_realson").combobox('getText');//异常原因
		var abnormal_num= $("#abnormal_num").val();//异常数量
		var abnormal_name= $("#abnormal_name").val();//异常上报人
		$("#abnormal_type_name").val(abnormal_type);
		if(abnormal_type==""){
		   $pjq.messager.alert('异常反馈', '请选择异常类型','info');
		}else if(abnormal_realson==""){
		   $pjq.messager.alert('异常反馈', '请选择异常原因','info');
		}else if(abnormal_num==""){
		   $pjq.messager.alert('异常反馈', '请输入异常数量','info');
		}else if(abnormal_name==""){
		   $pjq.messager.alert('异常反馈', '请输入异常上报人姓名','info');
		}else{
	    $.ajax({
		      url : 'abnormalreport.do?method=saveAbnormalReport',
		      type:'post',
		      data :$("#order").serialize(),
		      success : function (data){
		          if(data){
		          $pjq.messager.alert('异常反馈', '异常反馈成功，等待审核结果','info');
				  $dialog.dialog('close');
				  $grid.datagrid('reload');
		          }else{
		          $pjq.messager.alert('异常反馈', '异常反馈失败','info');
		          }
		          
		      },
		  });
	    }
	};
</script>
<body>
	<form action="" method="post" id="order">
		<input type="hidden" name="shiping_order_id">
		<fieldset>
			<table class="tableclass">
				<tr>
					<th colspan="4">运单信息</th>

				</tr>
				<tr>
					<td>货运编号：</td>
					<td class="td2"><input type="text" id="shiping_order_num"
						class="validate[required]" name="shiping_order_num"
						readonly="readonly"></td>
					<td>出货订单号：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="shiping_order_goid" class="validate[required]"
						name="shiping_order_goid">
					</td>
				</tr>
				<tr>
					<td>起运时间：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="send_time" class="validate[required]" name="send_time">
					</td>
					<td>受理机构：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="send_mechanism" name="send_mechanism">
					</td>
				</tr>
				<tr>
					<td>收货联系人：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="receipt_name" name="receipt_name">
					</td>
					<td>收货客户名称：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="receipt_tel" name="receipt_tel">
					</td>
				<tr>
				<tr>
					<td>货物名称：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="goods_name" name="goods_name">
					</td>
					<td>总件数：</td>
					<td class="td2"><input readonly="readonly" id="goods_num"
						name="goods_num" /></td>
				</tr>
				<tr>
					<td>总重量：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="goods_weight" name="goods_weight">
					</td>
					<td>总体积：</td>
					<td class="td2"><input type="text" readonly="readonly"
						id="goods_vol" name="goods_vol">
					</td>
				</tr>
			</table>
			<table class="tableclass">
				<tr>
					<th colspan="4">异常信息</th>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>异常类型：</td>
					<td class="td2"><select id="abnormal_type"
						class="easyui-combobox" name="abnormal_type" style="width:156px">
							<option value="2">客户异常</option>
							<option value="1">货物异常</option>
							
					</select>
					<input type="hidden" name="abnormal_type_name" id="abnormal_type_name">
					</td>
					<td><font color="red" style="margin-right:10px">*</font>异常原因：</td>
					<td class="td2"><select id="abnormal_realson"
						class="easyui-combobox" style="width:156px">
					</select> <input type="hidden" name="abnormal_message" id="abnormal_message">
					</td>
				</tr>
				<tr>
					<td><font color="red" style="margin-right:10px">*</font>异常件数：</td>
					<td class="td2"><input type="text"
						name="abnormal_num" id="abnormal_num" /></td>
					<td><font color="red" style="margin-right:10px">*</font>上报人姓名：
					</td>
					<td class="td2"><input type="text" id="abnormal_name"
						name="abnormal_name" value="<%=user.getUsername()%>">
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td class="td2" colspan="3"><textarea
							style="width: 400px;resize: none;" name="abnormal_remark"></textarea>
					</td>
				</tr>
				<tr>
					<td>异常图片</td>
					<td colspan="3">
						<div id="wrapper">
							<div id="container">
								<!--头部，相册选择和格式选择-->

								<div id="uploader">
									<div class="queueList">
										<div id="dndArea" class="placeholder">
											<div id="filePicker"></div>
											<p>请将照片拖到这里，单次最多可选5张</p>
										</div>
									</div>
									<div class="statusBar" style="display:none;">
										<div class="progress">
											<span class="text">0%</span> <span class="percentage"></span>
										</div>
										<div class="info"></div>
										<div class="btns">
											<div id="filePicker2"></div>
											<div class="uploadBtn">开始上传</div>
										</div>
									</div>
								</div>
							</div>
						</div> <!--   -->
					</td>
				</tr>
			</table>
		</fieldset>
		<input type="hidden" name="method" value="addunqualified">
	</form>
</body>
</html>
