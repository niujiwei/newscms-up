$(function(){
	var fs;
	//var ff = $(window.parent.document).find("iframes");
	var ff = window.parent.document.getElementById("urlId").value;
	//var ss = $(window.parent.document).find("iframe").attr("src");
	$.ajax({
		type : "POST",
		async : false,
		url : 'user.do?method=getFunctions',
		data : {
			url : ff
		},
		success : function(data2) {
			
			fs=data2.split(",");
		}
	});
	for ( var i = 0; i < fs.length; i++) {
		if (fs[i] == "添加") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbadd' onclick='add()' data-options=\"iconCls:\'icon-add\',plain:true\">添加</a>");
		} else if (fs[i] == "修改") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbmodify' onclick='modify()' data-options=\"iconCls:\'icon-edit\',plain:true\">修改</a>");
		} else if (fs[i] == "删除") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbdel' onclick='deleteEver()' data-options=\"iconCls:\'icon-remove\',plain:true\">删除</a>");
		}else if (fs[i] == "详细信息") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbxinxi' onclick='xiangxixinxi()' data-options=\"iconCls:\'icon-edit\',plain:true\" >详细信息</a>");
		}else if (fs[i] == "导出") {
			$("#tb").append("<a href='javascript:void(0)'class='easyui-linkbutton' id='tbput' onclick='putoutfile()' data-options=\"iconCls:\'icon-output\',plain:true\">导出</a>");
		} else if (fs[i] == "重置密码") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbresetpassword' onclick='resetpassword()' data-options=\"iconCls:\'icon-reset\',plain:true\" >重置密码</a>");
		} else if (fs[i] == "导入") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='putintfile()' data-options=\"iconCls:\'icon-input\',plain:true\">导入</a>");
		} else if (fs[i] == "绑定车辆") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='bdcar()' data-options=\"iconCls:\'icon-lock\',plain:true\">绑定车辆</a>");
		} else if (fs[i] == "打印") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='dy()' data-options=\"iconCls:\'icon-print\',plain:true\">打印</a>");
		}else if (fs[i] == "一维打印") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='printYWM()' data-options=\"iconCls:\'icon-print\',plain:true\">一维打印</a>");
		}else if (fs[i] == "编辑") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='eidtChange()' data-options=\"iconCls:\'icon-edit\',plain:true\">编辑</a>");
		}else if (fs[i] == "接收") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='otherDealBack()' data-options=\"iconCls:\'icon-edit\',plain:true\">接收</a>");		
		}else if (fs[i] == "返回") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='dealBack()' data-options=\"iconCls:\'icon-edit\',plain:true\">返回</a>");
		}else if (fs[i] =="寄出") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='dealNotBack()' data-options=\"iconCls:\'icon-edit\',plain:true\">寄出</a>");
		}else if (fs[i] =="查看运单") {
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='searchMsg()' data-options=\"iconCls:\'icon-edit\',plain:true\">查看运单</a>");
		}else if(fs[i] =="所有信息导出"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='allMessageOutPut()' data-options=\"iconCls:\'icon-output\',plain:true\">所有信息导出</a>");
		}else if(fs[i]=="勾选信息导出"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='someMessageOutPut()' data-options=\"iconCls:\'icon-output\',plain:true\">勾选信息导出</a>");
		}else if(fs[i]=="一键删除"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='deleteAllMessage()' data-options=\"iconCls:\'icon-remove\',plain:true\">一键删除</a>");
		}else if(fs[i]=="签收"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='qianshou()' data-options=\"iconCls:\'icon-edit\',plain:true\">签收</a>");
		}else if(fs[i]=="分配运单"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='fenpei()' data-options=\"iconCls:\'icon-edit\',plain:true\">分配运单</a>");
		}else if(fs[i]=="异常反馈"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='abnormalFeedback()' data-options=\"iconCls:\'icon-add\',plain:true\">异常反馈 </a>");
		}else if(fs[i]=="异常处理"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='exceptionHandling()' data-options=\"iconCls:\'icon-edit\',plain:true\">异常处理 </a>");
		}else if(fs[i]=="解绑"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='jiebang()' data-options=\"iconCls:\'icon-edit\',plain:true\">解绑 </a>");
		}else if(fs[i]=="绑定"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='bangding()' data-options=\"iconCls:\'icon-edit\',plain:true\">绑定 </a>");
		}else if(fs[i]=="结算"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='fn_pay()' data-options=\"iconCls:\'icon-edit\',plain:true\">结算</a>");
		}else if(fs[i]=="查看信息"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='lookMessage()' data-options=\"iconCls:\'icon-edit\',plain:true\">查看信息</a>");
		}else if(fs[i]=="发送信息"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbinput' onclick='sendMessageManage()' data-options=\"iconCls:\'icon-add\',plain:true\">发送信息</a>");
		}else if(fs[i]=="运单调整"){
            $("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-add\',plain:true\" onclick='waybillAdjustment()'>运单调整</a>");
		}else if(fs[i]=="取消签收"){
            $("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-remove\',plain:true\" onclick='cancelSign()'>取消签收</a>");
        }else if(fs[i]=="运单审核"){
			$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-edit\',plain:true\" onclick='waybillAudit()'>运单审核</a>");
		}else if(fs[i]=="新增备注信息"){
            $("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-edit\',plain:true\" onclick='modify()'>新增备注信息</a>");
        }else if(fs[i]=="撤销审核"){
            $("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-edit\',plain:true\" onclick='cancelAudit()'>撤销审核</a>");
        }else if(fs[i]=="取消控制"){
            $("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' data-options=\"iconCls:\'icon-edit\',plain:true\" onclick='cancelControl()'>取消控制</a>");
        }
		
	}
	$("#tb").append("<a href='javascript:void(0)' class='easyui-linkbutton' id='tbreset' onclick='reset()' data-options=\"iconCls:\'icon-reset\',plain:true\">重置</a>");
	$.parser.parse();
});

