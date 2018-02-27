<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="textml;charset=utf-8">
<link rel="stylesheet" href="bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="comment/comment.css" />
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css" />
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<title>运单节点及司机评价页面</title>

</head>
<script type="text/javascript">
var shiping_order_num;
	function getSearch1() {
	document.getElementById("next1").style.display = "none";//隐藏
		$("#deta1").html("");
		shiping_order_num = $("#shiping_order_num").val();

		if (shiping_order_num != null && shiping_order_num != "") {
			$.ajax({
						type : "post",
						url : 'searchWaybill.do?method=getSearchWaybill',
						data : {
							shiping_order_num : shiping_order_num
						},
						success : function(msg){

							if (msg == ""){
								$("#deta1")
										.append(
												"<tr><td id='error'  style='color: red'>*抱歉！未查到此运单"
														+ shiping_order_num
														+ "信息，请确认运单号码是否正确，或致电终端配送项目组业务合作。</td></tr>");
							} else {
								$.each(msg, function(x, y) {
									$("#deta1").append(
											'<tr><td>' + y.goods_arrive_remakes
													+ '</td></tr>');
								});
								document.getElementById("next1").style.display = "";//显示
							}

						}
					});

		} else {
			document.getElementById("next1").style.display = "none";//显示

		}
	};
//*********司机信息***************************************************************


   //*************星级评论*************************
var current_value;
// 代码整理：优设记 www.ui3g.com
jQuery.fn.rater	= function(options) {
		
	// 默认参数
	var settings = {
		enabled	: true,
		url		: '',
		method	: 'post',
		min		: 1,
		max		: 10,
		step	: 1,
		value	: null,
		after_click	: null,
		before_ajax	: null,
		after_ajax	: null,
		title_format	: null,
		info_format	: null,
		image	: 'images/stars.jpg',
		imageAll :'images/stars-all.gif',
		defaultTips :true,
		clickTips :true,
		width	: 24,
		height	: 24
	}; 
	
	// 自定义参数
	if(options) {  
		jQuery.extend(settings, options); 
	}
	
	//外容器
	var container	= jQuery(this);
	
	// 主容器
	var content	= jQuery('<ul class="rater-star"></ul>');
	content.css('background-image' , 'url(' + settings.image + ')');
	content.css('height' , settings.height);
	content.css('width' , (settings.width*settings.step) * (settings.max-settings.min+settings.step)/settings.step);
	//显示结果区域
	var result= jQuery('<div class="rater-star-result"></div>');
	container.after(result); 
	//显示点击提示
	var clickTips= jQuery('<div class="rater-click-tips"><span>点击星星就可以评分了</span></div>');
		if(!settings.clickTips){
			clickTips.hide();	
		}
	container.after(clickTips); 
	//默认手形提示
	var tipsItem= jQuery('<li class="rater-star-item-tips"></li>');
	tipsItem.css('width' , (settings.width*settings.step) * (settings.max-settings.min+settings.step)/settings.step);
	tipsItem.css('z-index' , settings.max / settings.step + 2);
		if(!settings.defaultTips){	//隐藏默认的提示
			tipsItem.hide();
		}
	content.append(tipsItem);
	// 当前选中的
	var item	= jQuery('<li class="rater-star-item-current"></li>');
	item.css('background-image' , 'url(' + settings.image + ')');
	item.css('height' , settings.height);
	item.css('width' , 0);
	item.css('z-index' , settings.max / settings.step + 1);
	if (settings.value) {
		item.css('width' , ((settings.value-settings.min)/settings.step+1)*settings.step*settings.width);
	};
	content.append(item);

	
	// 星星
	for (var value=settings.min ; value<=settings.max ; value+=settings.step) {
		item	= jQuery('<li class="rater-star-item"><div class="popinfo"></div></li>');
		if (typeof settings.info_format == 'function') {
			//item.attr('title' , settings.title_format(value));
			item.find(".popinfo").html(settings.info_format(value));
			item.find(".popinfo").css("left",(value-1)*settings.width);
		}
		else {
			item.attr('title' , value);
		}
		item.css('height' , settings.height);
		item.css('width' , (value-settings.min+settings.step)*settings.width);
		item.css('z-index' , (settings.max - value) / settings.step + 1);
		item.css('background-image' , 'url(' + settings.image + ')');
		
		if (!settings.enabled) {	// 若是不能更改，则隐藏
			item.hide();
		}
		
		content.append(item);
	}
	
	content.mouseover(function(){
		if (settings.enabled) {
			jQuery(this).find('.rater-star-item-current').hide();
		}
	}).mouseout(function(){
			jQuery(this).find('.rater-star-item-current').show();
	});
	// 添加鼠标悬停/点击事件
	var shappyWidth=(settings.max-2)*settings.width;
	var happyWidth=(settings.max-1)*settings.width;
	var fullWidth=settings.max*settings.width;
	content.find('.rater-star-item').mouseover(function() {
		jQuery(this).prevAll('.rater-star-item-tips').hide();
		jQuery(this).attr('class' , 'rater-star-item-hover');
		jQuery(this).find(".popinfo").show();
		
		//当3分时用笑脸表示
		if(parseInt(jQuery(this).css("width"))==shappyWidth){
			jQuery(this).addClass('rater-star-happy');
		}
		//当4分时用笑脸表示
		if(parseInt(jQuery(this).css("width"))==happyWidth){
			jQuery(this).addClass('rater-star-happy');
		}
		//当5分时用笑脸表示
		if(parseInt(jQuery(this).css("width"))==fullWidth){
			jQuery(this).removeClass('rater-star-item-hover');
			jQuery(this).css('background-image' , 'url(' + settings.imageAll + ')');
			jQuery(this).css({cursor:'pointer',position:'absolute',left:'0',top:'0'});
		}
	}).mouseout(function() {
		var outObj=jQuery(this);
		outObj.css('background-image' , 'url(' + settings.image + ')');
		outObj.attr('class' , 'rater-star-item');
		outObj.find(".popinfo").hide();
		outObj.removeClass('rater-star-happy');
		jQuery(this).prevAll('.rater-star-item-tips').show();
		//var startTip=function () {
		//outObj.prevAll('.rater-star-item-tips').show();
		//};
		//startTip();
	}).click(function() {
		//jQuery(this).prevAll('.rater-star-item-tips').css('display','none');
		jQuery(this).parents(".rater-star").find(".rater-star-item-tips").remove();
		jQuery(this).parents(".goods-comm-stars").find(".rater-click-tips").remove();
		jQuery(this).prevAll('.rater-star-item-current').css('width' , jQuery(this).width());
		   if(parseInt(jQuery(this).prevAll('.rater-star-item-current').css("width"))==happyWidth||parseInt(jQuery(this).prevAll('.rater-star-item-current').css("width"))==shappyWidth){	
			jQuery(this).prevAll('.rater-star-item-current').addClass('rater-star-happy');
			}
		else{
			jQuery(this).prevAll('.rater-star-item-current').removeClass('rater-star-happy');
			}
			if(parseInt(jQuery(this).prevAll('.rater-star-item-current').css("width"))==fullWidth){	
			jQuery(this).prevAll('.rater-star-item-current').addClass('rater-star-full');
			}
		else{
			jQuery(this).prevAll('.rater-star-item-current').removeClass('rater-star-full');
			}
		var star_count		= (settings.max - settings.min) + settings.step;
		var current_number	= jQuery(this).prevAll('.rater-star-item').size()+1;
		current_value	= current_number * settings.step * 2;
		
		//显示当前分值
		if (typeof settings.title_format == 'function') {
			jQuery(this).parents().nextAll('.rater-star-result').html(current_value+'分&nbsp;');
		}
		$("#StarNum").val(current_value);
		//jQuery(this).parents().next('.rater-star-result').html(current_value);
		//jQuery(this).unbind('mouseout',startTip)
	});
	
	jQuery(this).html(content);
	
};

// 星星打分
$(function(){
	var options	= {
	max	: 5,
	title_format	: function(value) {
		var title = '';
		switch (value) {
			case 1 : 
				title	= '很不满意';
				break;
			case 2 : 
				title	= '不满意';
				break;
			case 3 : 
				title	= '一般';
				break;
			case 4 : 
				title	= '满意';
				break;
			case 5 : 
				title	= '非常满意';
				break;
			default :
				title = value;
				break;
		}
		return title;
	},
	info_format	: function(value) {
		var info = '';
		switch (value) {
			case 1 : 
				info	= '<div class="info-box">2分&nbsp;很不满意</div></div>';
				break;
			case 2 : 
				info	= '<div class="info-box">4分&nbsp;不满意</div></div>';
				break;
			case 3 : 
				info	= '<div class="info-box">6分&nbsp;一般</div></div>';
				break;
			case 4 : 
				info	= '<div class="info-box">8分&nbsp;满意</div></div>';
				break;
			case 5 : 
				info	= '<div class="info-box">10分&nbsp;非常满意</div></div>';
				break;
			default :
				info = value;
				break;
		}
			return info;
		}
	};
	$('#rate-comm-1').rater(options);
});
//******************查询司机信息********************************************
	var receipt_tel;
	var receipt_name;
	$("#shiping_order_num1").val(shiping_order_num);
	function getSearch2() {
		receipt_tel = $("#receipt_tel").val();
		receipt_name = $("#receipt_name").val();
		$("#qx").html("");
		$("#deta2").html("");
		if ((receipt_tel != null && receipt_tel != "")
				| (receipt_name != "" && receipt_name != null)) {
			$
					.ajax({
						type : "post",
						url : 'searchDriver.do?method=getSearchDriver',
						data : {
							receipt_tel : receipt_tel,
							receipt_name : receipt_name,
							shiping_order_num : shiping_order_num
						},
						success : function(msg) {

							if (msg == "") {
								$("#qx")
										.append(
												"<tr><td id='error'  style='color: red'>*抱歉！未查到此信息，请确认查询信息是否正确，或致电终端配送项目组业务合作。</td></tr>");
												document.getElementById("next2").style.display = "none";//隐藏
												document.getElementById("comment").style.display = "none";//隐藏
							} else {

								$("#deta2")
										.append(
												'<tr><td>司机姓名:</td><td>'
														+ msg.driver_name
														+ '</td></tr><tr><td>联系电话:</td><td>'
														+ msg.driver_phone
														+ '</td></tr>');
								$("#shipping_order_state").val(
										msg.shipping_order_state);
								$("#comment_state").val(msg.comment_state);
								var shipping_order_state = $(
										"#shipping_order_state").val();
								var comment_state = $("#comment_state").val();

								if (shipping_order_state == 4
										|| shipping_order_state == 5
										|| shipping_order_state == 6) {

									if (comment_state !=1 && comment_state !=2) {

										document.getElementById("next2").style.display = "";//显示
									} else {
										$("#qx")
												.append(
														"<tr><td id='error'  style='color: red'>*抱歉！此信息已评论。</td><td></td></tr>");
									}
								} else {
									$("#qx")
											.append(
													"<tr><td id='error'  style='color: red'>*抱歉！此信息未签收。</td><td></td></tr>");
								}
							}
						}
					});

		} else {
			document.getElementById("next2").style.display = "none";//隐藏

		}
	};
	function next(a) {
		if (a == 2) {
			document.getElementById("comment").style.display = "";//显示
		} else if (a == 3) {
			$("#remark").val("");
			$("#qx").html("");
			document.getElementById("comment").style.display = "none";//隐藏
		}else if(a==1){
			document.getElementById("next1").style.display = "none";//隐藏
			document.getElementById("driver").style.display = "";//隐藏
			document.getElementById("shipping").style.display = "none";//隐藏
		
		}
	};
	function adds() {
	$("#qx").html("");
		var xing = current_value;
		var remark = $("#remark").val();
		if (xing != null && xing != "" && remark != null && remark != "") {
			$.ajax({
						type : "post",
						url : 'searchDriver.do?method=getComment',
						data : {
							xing : xing,//星级别
							remark : remark,//备注
							receipt_tel : receipt_tel,
							receipt_name : receipt_name,
							shiping_order_num : shiping_order_num
						},
						success : function(msg) {
							if (msg.flag) {
								document.getElementById("next2").style.display = "none";//隐藏
								document.getElementById("comment").style.display = "none";//隐藏
								xing="";
								$("#remark").val("");
								$("#qx").append(
												"<tr><td id='error'  style='color: blue'>*保存成功。</td><td></td></tr>");

							} else {
								$("#qx").append(
												"<tr><td id='error'  style='color: red'>*抱歉！保存失败。</td><td></td></tr>");
							}
						}
					});
		} else {
			$("#qx").html("*抱歉！评论信息不能为空。");
		}
	}
</script>
<body>
<!-- 运单查询 -->
	<div class="container-fluid" id="shipping">
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
			<button class="btn btn-info" type="button" onclick="getSearch1()">查询</button>
		</div>


		<div class="row">
			<div class="col-xs-12" style="height:30px"></div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<table class="table" id="deta1">
				</table>
			</div>
			<div class="col-xs-6">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<button class="btn btn-info" type="button" onclick="next(1)" id="next1" style="display: none;">下一步</button>
			</div>
		</div>
	</div>
<!-- ***************************司机信息************************************** -->
	<div class="container-fluid" id="driver" style="display:none">
		<div class="row">
			<div class="col-xs-12">
				<p class="bg-primary" style="height: 40px;">
					<strong style="padding-left:30px;font-size: 30px">司机信息</strong>
				</p>
			</div>
		</div>
	<div class="row">
		<div class="col-xs-3">
			<div class="input-group">
				<div class="input-group-addon">收货联系人</div>
				<input type="text" class="form-control" name="receipt_name"
					id="receipt_name" placeholder="请输入收货联系人">
			</div>
		</div>
		<div class="col-xs-3">
			<div class="input-group">
				<div class="input-group-addon">收货客户电话</div>
				<input type="text" class="form-control" name="receipt_tel"
					id="receipt_tel" placeholder="请输入收货客户电话"> <input
					type="hidden" id="shiping_order_num1" name="shiping_order_num1">
				<input type="hidden" id="shipping_order_state"
					name="shipping_order_state"> <input type="hidden"
					id="comment_state" name="comment_state">
			</div>
		</div>
		<div class="col-xs-6">
			<button class="btn btn-info" type="button" onclick="getSearch2()">查询</button>
		</div>
	</div>

		<div class="row">
			<div class="col-xs-12" style="height:30px"></div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<table class="table" id="deta2">
				</table>
			</div>
			<div class="col-xs-6"></div>
		</div>
		<div class="row">
			<table class="table" id="qx" style="color:red">
				</table>
			<div class="col-xs-6"> </div>		
		</div>
		<div class="row">
			<div class="col-xs-12">
				<button class="btn btn-info" type="button" onclick="next(2)"
					id="next2" style="display:none ;">评论</button>
			</div>
		</div>
	</div>
<!-- ***************************评论星级*************************** -->
	<div class="container-fluid" id="comment" style="display:none">
		<div class="row">
			<div class="col-xs-12">

				<div class="quiz">
					<h3>我要评论</h3>
					<div class="quiz_content">
						<form action="" id="" method="post">
							<div class="goods-comm">
								<div class="goods-comm-stars">
									<span class="star_l">满意度：</span>
									<div id="rate-comm-1" class="rate-comm" ></div>
								</div>
							</div>
							<div class="l_text">
								<label class="m_flo">内 容：</label>
								<textarea name="remark" id="remark" class="text"></textarea>
								<span class="tr">至少输入一个文字</span>
							</div>
							<button type="button" class="btn btn-default"
								data-dismiss="modal" onclick="next(3)">
								<span class="glyphicon glyphicon-remove"></span>关闭
							</button>
							<button type="button" id="btn_submit" class="btn btn-primary"
								data-dismiss="modal" onclick="adds()">
								<span class="glyphicon glyphicon-floppy-disk"></span>保存
							</button>
						</form>
					</div>
					<!--quiz_content end-->
				</div>
			</div>
		</div>
	</div>
</body>
</html>