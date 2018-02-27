var wechart = {
		yanzheng:function(option){
			
			var message =  option==null||option==''? '请在微信客户端打开':option.message;
		    var type = option==null||option==''? 'cw':option.type;
			document.write('<form action="weChart.do?method=weChartMessage" method="post" name="wechartform1" style="display:none">');  
	        document.write('<input type="hidden" name="message" value="'+message+'"/>');  
	        document.write('<input type="hidden" name="type" value="'+type+'"/>');  
	        document.write('</form>');  
			//alert(1);
			 // 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器
	        document.wechartform1.submit(); 
		},
		weChart:function(){
			
			var useragent = navigator.userAgent;
		    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
		        // 以下代码是用javascript强行关闭当前页面
		        //document.wechartform1.submit(); 
		    	wechart.yanzheng("");
		    }
			
		}
};
//wechart.weChart();