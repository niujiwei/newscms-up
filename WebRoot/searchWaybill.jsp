<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="textml;charset=utf-8">
<link rel="stylesheet" href="bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css" />
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<title>运单状态节点页面</title>

</head>
<script type="text/javascript">
	function getSearch() {
		$("#deta  tr").html("");
		var shiping_order_num = $("#shiping_order_num").val();
		if (shiping_order_num != null && shiping_order_num != "") {

			$
					.ajax({
						type : "post",
						url : 'searchWaybill.do?method=getSearchWaybill',
						data : {
							shiping_order_num : shiping_order_num
						},
						success : function(msg) {
							if (msg == "") {
								$("#deta")
										.append(
												"<tr><td id='error'  style='color: red'>*抱歉！未查到此运单"
														+ shiping_order_num
														+ "信息，请确认运单号码是否正确，或致电终端配送项目组业务合作。</td></tr>");
							} else {
								$.each(msg, function(x, y) {
									$("#deta").append(
											'<tr><td>' + y.goods_arrive_remakes
													+ '</td></tr>');
								});
							}
						}
					});
		} 
	};
</script>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12">
				<p class="bg-primary" style="height: 40px;">
					<strong style="padding-left:30px;font-size: 30px">运单状态</strong>
				</p>
			</div>
		</div>
		<div class="col-xs-2">
			<input type="text" class="form-control" name="shiping_order_num"
				id="shiping_order_num" placeholder="请输入货运编号">
		</div>
		<div class="col-xs-10">
			<button class="btn btn-info" type="button" onclick="getSearch()">查询</button>
		</div>

	
	<div class="row">
		<div class="col-xs-12" style="height:30px"></div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<table class="table" id="deta">
			</table>
		</div>
	</div>
	</div>

</body>
</html>