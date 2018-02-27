var weChartBMap = function(data) {// 我的业务逻辑
	
	
    var myData = {
    		points:[],
    		lushu:"",
    		interval:1,//间隔时间分钟
    		markers:[],//存放签收接单
    };
    var history = data.history;//运单节点信息
	var driver = data.driver;//司机信息
	var user = data.user;//用户信息
	var time = data.time;//时间信息,用户名信息,设备号信息
	var order = data.order;//订单id
	var map = $baidumap.map;
	
	var method = $baidumap.method;
	
	//getDeliveryCustomerInfo();
	
	loadDriverInfo();
    var maker;
    var endPoint;

    function nowData(userName) {

        $.getJSON("map.do?method=loadmapinfo", {"carno": userName}, function (json) {
            $(json).each(function (i) {
                var data = json[i];
                var label = new BMap.Label(data.carno, {
                    offset: new BMap.Size(-20, -20)
                });
                label.setStyle({
                    color: "black",
                    fontSize: "13px",
                    fontWeight: "bolder",
                    height: "16px",
                    lineHeight: "16px",
                    fontFamily: "微软雅黑",
                    borderColor: "black",
                    backgroundColor: "#FFF774"
                });
                map.removeOverlay(maker);
                var point = new BMap.Point(data.lng, data.lat);
                var polyline = new BMap.Polyline([endPoint, point], {
                    strokeColor: "#DB55E5",
                    strokeWeight: 4,
                    strokeOpacity: 1
                });
                map.addOverlay(polyline);

                endPoint = point;
                maker = method.addMarker(map, point);
                maker.addEventListener('click', function (e) {
                    clickMakerEventListener(this, data);// 点击事件方法
                });
                map.panTo(point);
            });
        });
        setTimeout(function () {
            nowData(userName);
        }, 7000);
    }

    function trackCar(startTime) {
        map.setZoom(19);
        // map.clearOverlays();
        var size = 1000;
        //startTime = time.receivetime == undefined ? "" : time.receivetime;//接单时间
        //var endTime = new Date().Format("yyyy-MM-dd hh:mm:ss");//签收时间
        var userName = time.userName == undefined ? "" : time.userName;//用户姓名
        //var userName = 'qdps05';
        var equipmentNum = time.equipmentNum == undefined ? "" : time.equipmentNum;//设备号

        //loadDriverPoint (0, size, userName, startTime, endTime, equipmentNum, 1);
        nowData(userName);

    }



	function getDeliveryCustomerInfo(){//获取客户信息显示客户信息
		var myicon = new BMap.Icon("images/t3.png", new BMap.Size(50, 50), {
			anchor: new BMap.Size(45, 47)
		});//添加收货客户定位信息默认图标
		
		$.ajax({//查询收货客户定位信息
			   type: "POST",
			   url: "weChart.do?method=getDeliveryCustomerInfo",
			   data: {
				   delivery_cus_name:order.send_mechanism,
				   delivery_people:order.receipt_name
			   },
			   success: function(msg){
				   if(msg.customer){
					   var data =msg.customer; 
					   var radius = data.delivery_radius;
					   var delivery_point =data.delivery_point.split(",");
					   var point =  new BMap.Point(delivery_point[0],delivery_point[1]);
					   var customer = method.addMarker(map,point,{//添加坐标点
						  icon: myicon
					   });
					  var customerCircle=  method.addCircle(map, point, radius, {//添加园
							fillColor : "blue",//填充颜色。当参数为空时，折线覆盖物将没有填充效果。
							strokeWeight : 1,//边线的宽度，以像素为单位。
							fillOpacity : 0.3,//填充的透明度，取值范围0 - 1。
							strokeOpacity : 0.3,//边线透明度，取值范围0 - 1。
					   });
					  $baidumap.customerMarkers.push(customer);
					  $baidumap.customerMarkers.push(customerCircle);
				   }
			   }
		});
	}
	
	function loadTimePoint(){//加载分配签收接单的坐标点
		
		if(time==null||time==undefined) return "没有时间信息";
		
		var updatetime=time.updatetime==undefined?"":time.updatetime;//分配时间
		var receivetime=time.receivetime==undefined?"":time.receivetime;//接单时间
		var sign_time=time.sign_time==undefined?"":time.sign_time;//签收时间
		var userName=time.userName==undefined?"":time.userName;//用户姓名
		var equipmentNum=time.equipmentNum==undefined?"":time.equipmentNum;//设备号
		var auto_sign_time = time.auto_sign_time==undefined?"":time.auto_sign_time;//电子围栏签收时间
		
		$.ajax({//加载分配签收接单的坐标点
			   type: "POST",
			   url: "weChart.do?method=getSomeTimePoint",
			   data: {
				   updatetime:updatetime,
				   receivetime:receivetime,
				   sign_time:sign_time,
				   userName:userName,
				   equipmentNum:equipmentNum,
				   auto_sign_time:auto_sign_time,
			   },
			   success: function(msg){
				   if(msg){
					   for(var key in msg){
						  var data =  msg[key];
						  var title = "";
						  var imgUrl="";
						 
						  if(key=="receivetime") {title="接单标注";imgUrl="images/marker_purple.png";}
						  if(key=="auto_sign_time"){title="电子围栏标注";imgUrl="images/marker_red.png";}
						  if(key=="sign_time") {title="签收标注"; imgUrl="images/marker_green.png";}
						  var myIcon = new BMap.Icon(imgUrl, new BMap.Size(25,25));
						  var point = new BMap.Point(data.lng,data.lat);
						  var marker= new BMap.Marker(point,{icon:myIcon}); // 创建标注
						  marker.setTitle(title);
						  map.addOverlay(marker);// 将标注添加到地图中
						  myData.markers.push(point);
					   }
				   }
				 //  map.setViewport(myData.markers);
			   }
		});
	}
	
	
	function loadDriverInfo(){//根据运单信息查询定位信息和轨迹
		map.clearOverlays();
		loadTimePoint();
		getDeliveryCustomerInfo();
		var userName=time.userName==undefined?"":time.userName;//用户姓名
		var equipmentNum=time.equipmentNum==undefined?"":time.equipmentNum;//设备号
		var shipping_order_state =order.shipping_order_state;
        var receivetime = time.receivetime == undefined ? "" : time.receivetime;//接单时间
		if(shipping_order_state>1&&shipping_order_state<4){
			if(user==undefined) return $.messager.alert('地图定位提示', '您搜索的运单分配的司机未建立账户,没办法获取定位信息', 'info');
			$.ajax({//查询收货客户定位信息
				   type: "POST",
				   url: "weChart.do?method=loadMapDriverInfo",
				   data: {
					   userName:userName,
					   equipmentNum:equipmentNum
				   },
				   success: function(msg){
					  
					   if(msg){
						  var mapMessage = msg.map;
						  if(mapMessage==undefined) throw new Error("地图定位---未找到定位信息");
						  var data = mapMessage[0];
						  if(data==undefined) throw new Error("地图定位---map[0] 数组为空");
						  var point = new BMap.Point(data.lng,data.lat);
						  endPoint = point;
						  maker = method.addMarker(map, point);
						  maker.addEventListener('click', function(e) {
								clickMakerEventListener(this, data);// 点击事件方法
							});
						    var labelMesage ={
						    		labelTitle:data.carno
						    };
							method.addMakerLabel(maker, labelMesage);// 给maker点添加label
                           trackCar(receivetime);
					   }
					  
				   }
			});
			
		}else if(shipping_order_state>3){
			var size =1000;
			var startTime=time.receivetime==undefined?"":time.receivetime;//接单时间
			var endTime=time.sign_time==undefined?"":time.sign_time;//签收时间
			var userName=time.userName==undefined?"":time.userName;//用户姓名
			var equipmentNum=time.equipmentNum==undefined?"":time.equipmentNum;//设备号

			$.ajax({//查询收货客户定位信息
				   type: "POST",
				   url: "weChart.do?method=loadMapPointCount",
				   data: {
					   userName:userName,
					   startTime:startTime,
					   endTime:endTime,
					   equipmentNum:equipmentNum,
				   },
				   success: function(msg){
					  var count = msg.count;
					  
					  if(count==undefined) throw new Error("获取坐标信息数量失败");
					  
					  if(count==0) return;
					  map.clearOverlays();
					  var page = Math.ceil(count/size);//
				
					  loadDriverPoint(0,size,userName,startTime,endTime,equipmentNum,page);
					 
					  
				   }
			});
			
		}
	
		
	}
	
	function loadDriverPoint(page,size,userName,startTime,endTime,equipmentNum,count){//绘制轨迹
		$.ajax({//查询收货客户定位信息
			   type: "POST",
			   url: "weChart.do?method=loadMapPointInfo",
			   data: {
				   userName:userName,
				   startTime:startTime,
				   endTime:endTime,
				   page:page,
				   size:size,
				   equipmentNum:equipmentNum
			   },
			   success: function(msg){
				   var list = msg.list;
				   
				  for(var i=0 ;i<list.length-1;i++){
					    var old = list[i];
					    var now = list[i+1];
					    myData.points.push(old);
					   var b = timeInterval(old,now);
					   if(b) continue;
					  var oldPoint= new BMap.Point(old.lng,old.lat);//始点的经纬度  
                      var nowPoint =  new BMap.Point(now.lng,now.lat);//终止点的经纬度 
					   var jl = map.getDistance(oldPoint,
							   nowPoint).toFixed(2);
					   if(jl>650) {
						   var jld = new BMap.DrivingRoute(map, {
								onSearchComplete : function(results){
									var map = $baidumap.map;

									var opacity = 0.45;

									var planObj = results.getPlan(0);

									if (planObj == undefined)
										var polyline = new BMap.Polyline([ oldPoint,nowPoint],
												   {strokeColor:"#EF5350",//设置颜色   
									                strokeWeight:4, //宽度  
									                strokeOpacity:1});//透明度  
									       map.addOverlay(polyline);  
										

									var bounds = [];

									var addPoints = function(points) {
										for ( var i = 0; i < points.length; i++) {
											bounds.push(points[i]);
										}
										
									};
									// 绘制驾车步行线路
									for ( var i = 0; i < planObj.getNumRoutes(); i++) {

										var route = planObj.getRoute(i);

										if (route.getDistance(false) <= 0) {
											continue;
										}
										addPoints(route.getPath());
										// 驾车线路
										if (route.getRouteType() == BMAP_ROUTE_TYPE_DRIVING) {
											map.addOverlay(new BMap.Polyline(route.getPath(), {
												strokeColor : "green",
												strokeOpacity : opacity,
												strokeStyle:"dashed",
												strokeWeight : 6,
												enableMassClear : true
											}));
											
										} else {
											// 步行线路有可能为0
											map.addOverlay(new BMap.Polyline(route.getPath(), {
												strokeColor : "green",
												strokeOpacity : 0.75,
												strokeStyle:"dashed",
												strokeWeight : 4,
												enableMassClear : true
											}));
										}
									}
								}
							});
						   jld.search(oldPoint,
								   nowPoint);
						   continue;
					   }
					   var polyline = new BMap.Polyline([ oldPoint,nowPoint],
							   {strokeColor:"#EF5350",//设置颜色   
				                strokeWeight:4, //宽度  
				                strokeOpacity:1});//透明度  
				       map.addOverlay(polyline);  
					   
						/*var b = timeInterval(old,now);
					    
						if(b) { 
							
							
						
						}*/
						
					/*	var point=new BMap.Point(old.lng,old.lat);
						var myPoint = myData.points;
						myPoint.push(point);*/
				   }
				/*   $.each(list, function(i, itme) {
					   
						var point=new BMap.Point(itme.lng,itme.lat);
						var myPoint = myData.points;
						myPoint.push(point);
						
					});*/
				  /* method.addPolyline(map, myData.points,  {
						strokeColor : "#EF5350",//"#2DCD1F",
						strokeWeight : 4,
						strokeOpacity : 1
					});*/
				   if(page==(count-1)){
					   map.setViewport(myData.points);
					  // myData.lushu =  method.lushu(map, myData.points);
					   return;
				   }
				   if(page<count){
					 
					   loadDriverPoint(++page,size,userName,startTime,endTime,count);
				   };
				  
				 
			   }
			  
		});
		 
	}
	
    
	function clickMakerEventListener(maker, obj) {// maker点击事件
		var carsno = obj.carno;//获取用户名称
		
		var type="getLocation";//定义逆向解析
		
		var opts = {//定义信息窗口属性
			title : "<span style='color:#D2693C;font-size: 14px;font-weight: bold'></span>", // 信息窗口标题
			enableAutoPan : false, // 自动平移
			offset : new BMap.Size(-5, -15)// 偏离值
		};
		
		$.getJSON("weChart.do?method=loadMapDriverInfo",{"userName" : carsno},function(jsons) {//根据用户名去查询最新信息
			
			  var mapMessage = jsons.map;//赋值查询结果
			  
			  if(mapMessage==undefined) throw new Error("地图定位---未找到定位信息");
			  
			  var messagecar = mapMessage[0];//赋值查询结果
			  
			  if(messagecar==undefined) throw new Error("地图定位---map[0] 数组为空");
			  
			  var point = new BMap.Point(messagecar.lng,messagecar.lat);//更加查询结果重新定位maker的坐标
			  
			  maker.setPosition(point);//maker更新中心
			  
			  method.getGeocoder(type,{//调用方法地址解析方法
					point:maker.getPosition(),
					callback:function(geocoderResult){//解析方法回调函数
						
						if(geocoderResult==null) throw new Erorr("点击 maker点  获取_getGeocoder 逆向定位失败");
						var point = geocoderResult.point;//坐标地址
						var address=geocoderResult.address;//解析地址
						var addressComponents = geocoderResult.addressComponents;//结构化的地址描述
						var province =addressComponents.province;//省份名称。
						var city = addressComponents.city;//城市名称。
						var district = addressComponents.district;//区县名称。
						var street = addressComponents.street;//街道名称。
						var streetNumber=addressComponents.streetNumber;//门牌号码。
						var temperature="未获取到温度";
						var weather ="未获取到天气情况";
						var wind = "未获取到风力情况";
						var weather_data="";
						
						$.ajax({url : "http://api.map.baidu.com/telematics/v3/weather?"+ $.param({//调用jsonp 请求天气信息
							location :city,
							output:"json", // 城市
							ak:"3ktrkqQ4iISBiphzz8mkrZ68",
						}),
						dataType: 'jsonp', 
						success : function(data) {
							if(data){
								if(data.status="success"){
									weather_data = data.results[0].weather_data[0];
									temperature=weather_data.temperature;
									weather=weather_data.weather;
									wind=weather_data.wind;
									var messagedata={
											username:messagecar.carno,
											depement:messagecar.deptname,
											state:messagecar.state,
											address:address,
											time:messagecar.nowdate,
											weather_data:weather+wind+temperature,
											point:messagecar.lng+","+messagecar.lat
									};
									
									var message = $('#infoWindow').tmpl(messagedata).html();
									
									map.setZoom(15);
									map.setCenter(point);
									
									method.openInfoWindow(map, message, maker, opts);
								}
							}
						 }
					  });
					}
				});
		});
		
		
		
	}
	function getMessage() {// 获取定位信息对象

		$.getJSON("map.do?method=loadmapinfo", function(json) {

			$(json).each(function(i) {

				var obj = json[i];// 变量赋值

				var data = {};// 定义data对象

				data.labelTitle = obj.carno;// 增加data属性labelTitle

				var point = new BMap.Point(obj.lng, obj.lat);// 根据查询的对象或去坐标点

				var maker = method.addMarker(map, point);// 将maker点添加到地图中去

				maker.addEventListener('click', function(e) {

					clickMakerEventListener(this, obj);// 点击事件方法
				});
				method.addMakerLabel(maker, data);// 给maker点添加label
			});
			
			map.setViewport(map.makers);
			method.markerClusterer(map,$baidumap.markers);//调用聚合方法
		});
	};
	
	function timeInterval(old,now){
		var oldNowDate,nowNowDate;
		oldNowDate = old.nowdate;
		nowNowDate=now.nowdate;
		var s1 = new Date(oldNowDate);
		var s2 = new Date(nowNowDate);
		var days = s2.getTime() - s1.getTime();
		var minutes=days/60000;
		if(minutes<myData.interval) return false;
		var oldPoint = new BMap.Point(old.lng,old.lat);
		var nowPoint = new BMap.Point(now.lng,now.lat);
		method.drivingRoute (map, oldPoint, nowPoint);
		return true;
	}
	
};


