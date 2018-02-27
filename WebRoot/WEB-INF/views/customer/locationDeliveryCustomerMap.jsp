<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%


%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


    <title>My JSP 'MyJsp.jsp' starting page</title>
    <meta http-equiv="Content-Type" content="textml; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="/inc/easyuiLocation.jsp"></jsp:include>
    <jsp:include page="../../../ValidationEngine/Validation.jsp"></jsp:include>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=3ktrkqQ4iISBiphzz8mkrZ68"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
    <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css"/>
    <title>地图标记</title>
    <style type="text/css">

        html, body {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 99%;
            font-family: 'Microsoft YaHei', '微软雅黑', Arial, Helvetica, sans-serif;
        }

        .bt {
            height: 31px;
            cursor: pointer;
            padding: 0 22px;
            font: 300 15px/29px 'Open Sans', Helvetica, Arial, sans-serif;
            color: #333;
            background-color: #fff;
            border-color: #ccc;
            border: 1px solid #ccc;
        }

        #wxdtdiv {
            position: absolute;
            z-index: 3;
            background-color: white;
            height: 50px;
            width: 48px;
            right: 10px;
            margin-top: 1%;
            margin-right: 1%;
            background-image: url("images/wx.png");
            cursor: pointer;
        }

        .bts {
            height: 31px;
            cursor: pointer;
            padding: 0 22px;
            font: 300 15px/29px 'Open Sans', Helvetica, Arial, sans-serif;
            color: #333;
            background-color: #296191;
            color: white;
            border-color: #ccc;
            border: 1px solid #ccc;
            color: white;
            color: white;
            border-color: #ccc;
            border: 1px solid #ccc;
        }

        #allMap {
            position: absolute;
            z-index: 2;
            width: 97%;
            height: 89%;
        }

        #center {
            width: 97%;
        }

        #dialog, #dialog2 {
            position: absolute;
            z-index: 3;
            background-color: white;
            height: 340px;
            width: 300px;
            margin-left: 68%;
            margin-top: 12%;
        }

        #bigndiv, #bigndiv2 {
            width: 300px;
            height: 35px;
            background-color: #296191;
        }

        #bigmapleave, #bigmapleave2 {
            color: white;
            font-family: 'Microsoft YaHei', ΢���ź�, Arial, Helvetica, sans-serif;
            font-size: 14px;
            font-weight: bold;
            line-height: 35px;
            margin-left: 10px;
        }

        #sm, #sm2 {
            cursor: pointer;
            margin-left: 180px;
            margin-top: 15px;
        }

        input {
            height: 30px;
        }

        input, img {
            vertical-align: middle;
        }

        input[type="checkbox"] {
            -webkit-appearance: none;
            height: 16px;
            vertical-align: middle;
            width: 16px;
            border: 1px solid #C1C1C1;
        }

        input[type="checkbox"]:hover {
            border: 1px solid #C1C1C1;
            cursor: pointer;
        }

        input[type="checkbox"]:checked {
            background-image: url("images/ckd.png");
        }

        input[type="checkbox"]:checked:hover {

            cursor: pointer;
        }

        .la {

            font-size: 16px;
            font-family: Helvetica, 'Hiragino Sans GB', 'Microsoft Yahei', '微软雅黑', Arial, sans-serif;
        }

        }

        .combo {
            border-color: #A9A9A9;
            background-color: #fff;
            border-radius: 0;
        }
    </style>
</head>

<body>
<div id="dialog">
    <div id="bigndiv">
        <form id="form1" action="" method="post"
              style="font-family: 'Microsoft YaHei', 微软雅黑, Arial, Helvetica, sans-serif;font-size: 14px;">
            <input type="hidden" id="update">
            <input type="hidden" id="updateid" name="delivery_id">
            <span id="bigmapleave">添加标注点</span> <img id="sm" onclick="closed(1)" alt="关闭"
                                                     src="images/close.png" width="10px" height="10px"
                                                     style="margin-top: 2px">
            <div align="center" style="margin-top: 15px">
                <label><font color="red">客户名称</font></label><input id="delivery_name" type="text" name="delivery_name"
                                                                   readonly="true" class="validate[required]"
                                                                   style="margin-left: 10px">
            </div>
            <div align="center" style="margin-top: 15px">
                <label><font color="red">客户编码</font></label><input id="delivery_num" type="text" name="delivery_num"
                                                                   readonly="true" class="validate[required]"
                                                                   style="margin-left: 10px">
            </div>
            <div align="center" style="margin-top: 15px">
                <label><font>联系方式</font></label><input id="delivery_tel1" type="text" name="delivery_tel"
                                                       readonly="true" class="validate[custom[phone]]"
                                                       style="margin-left: 10px">
            </div>
            <div align="center" style="margin-top: 15px">
                <label><font>范围</font></label><input id="delivery_radius" type="text" name="delivery_radius"
                                                     class="validate[custom[number],max[999999],min[1]]"
                                                     style="margin-left: 30px;width:100px"><font
                    style="margin-left:5px">米</font>
            </div>
            <div align="center" style="margin-top: 15px">
                <label style="margin-left: -12;">详细地址</label><textarea id="address1" name="delivery_address"
                                                                       style="margin-left: 10px;resize: none;width: 173px;height: 30px;"></textarea>
            </div>
            <div align="center" style="margin-top: 25px">
                <button type="button" class="bts" onclick="saveremark1('savemark')">
                    <img alt="保存" src="images/bc.png" width="18px" height="18px"
                         style="  vertical-align: sub;"> 保存
                </button>
                <!-- <button type="button" onclick="saveremark1('xinjianmark')" class="bt" style="width:80px">新建</button> -->
                <button type="button" onclick="closed(1)" class="bt">取消</button>
            </div>
        </form>
    </div>
</div>
<!--
<div id="dialog2">
    <div id="bigndiv2">
    <form id="form2" action="" method="post">
         <input type="hidden" id="updateid2" name="delivery_id">
      <span id="bigmapleave2">添加标注区域</span> <img id="sm2" onclick="closed(2)" alt="关闭"
      src="images/close.png" width="10px" height="10px"style="margin-top: 2px">
      <div align="center" style="margin-top: 23px">
      <label><font color="red">客户名称</font></label><input id="delivery_name2"  readonly="true" name="delivery_name" class="validate[required]" type="text" style="margin-left: 10px">
      </div>
      <div align="center" style="margin-top: 23px">
      <label><font color="red">客户编码</font></label><input id="delivery_num2"  readonly="true" name="delivery_num" class="validate[required]" type="text" style="margin-left: 10px">
      </div>
      <div align="center" style="margin-top: 23px">
      <label><font>联系方式</font></label><input id="delivery_tel2" readonly="true" type="text" name="delivery_tel" class="validate[custom[phone]]" style="margin-left: 10px">
      </div>
      <div align="center" style="margin-top: 23px">
      <label style="margin-right: 10px; ">详细地址</label><textarea id="address2" name="delivery_address"style="resize: none;width: 173px;height: 30px;"></textarea>
      </div>
      <input type="hidden" id="hidden_point" name="delivery_point">
      <div align="center" style="margin-top: 28px">
      <button type="button" class="bts" onclick="saveremark1('savepolygon')">
                  <img alt="保存" src="images/bc.png" width="18px" height="18px"
                      style="  vertical-align: sub;"> 保存
      </button>

      <button type="button" onclick="closed(2)" class="bt">取消</button>
      </div>
  </form>
  </div>
</div>
-->
<div id="center"
     style="margin-top: 10px;margin-left:15px;border: 1px solid #CFCFCF;height: 40px;background-color: #FFFFFF">
    <div style="float: left;height: 32px;margin-left:15px;margin-top:5px">
        <input type="text" id="weizhi"
               style="height: 30px;  border: none; margin-left: 5px;  border: 1px solid #ccc;"><input type="button"
                                                                                                      onclick="search()"
                                                                                                      style="background-image:url('images/chaxun.png');width: 60px;height:30px;border: none;  margin-left: 5px;">
    </div>
    <div style="float: left;height: 32px; margin-left: 65%;margin-top:5px">
        <input type="button" title="添加标注点" onclick="addremark()"
               style="background-image:url('images/biaozhu.png');  width: 29px;  height: 28px; border: 1px solid #3B9FF3;">
        <!--
        <input type="button" title="添加标注区域" onclick="addPolygon()"style="background-image:url('images/quyu.png');  width: 29px;  height: 28px;  border: 1px solid #3B9FF3;">
         -->
        <input type="button" value="返回" onclick="fanhui()"
               style="background-color:#2995F2;color: white;width: 38px;  height: 28px;  border: 1px solid #3B9FF3;">

    </div>
</div>
<div id="allMap" style="margin-top: 0px;margin-left:15px;border: 1px solid #CFCFCF"></div>
<div id="wxdtdiv">
    <input type="hidden" id="pdwd" value="卫星">
</div>
</body>
<script type="text/javascript">
    var cidd = "<%=request.getAttribute("id")%>";
    var map = new BMap.Map("allMap");
    var zpoint = "";
    var ppoint;
    var circle;
    var b = true;
    var a = true;
    var d = false;
    var infoWindow;
    var polygon;
    var delivery_radius;
    var myicon = new BMap.Icon("images/t3.png", new BMap.Size(50, 50), {
        anchor: new BMap.Size(45, 47)
    });

    $("#dialog").hide();
    $("#dialog2").hide();
    initMap();//如果是一个字符串，将第一个字母大写然后返回
    $(function () {
        $("#form1").validationEngine('attach', {
            promptPosition: 'centerLeft:160,-10'/*'topRight:-50,0'*/,
        });
        $("#form2").validationEngine('attach', {
            promptPosition: 'centerLeft:160,-10'/*'topRight:-50,0'*/,
        });
        $("#dialog").slideDown(500, function () {
        });//slideDown() 方法通过使用滑动效果，显示隐藏的被选元素。

        //取到客户信息的列表数据*******************************************************************
        $.ajax({
            type: "POST",
            async: false,//同步
            url: 'deliveryCustomer.do?method=getDeliveryCustomerbyid',
            data: {cid: cidd},
            success: function (data2) {
                $("#delivery_name").val(data2.delivery_name);
                $("#delivery_tel1").val(data2.delivery_tel);

                $("#delivery_radius").val(data2.delivery_radius);
                delivery_radius = $("#delivery_radius").val();
                //去掉原来坐标，不再显示，重新定位，获取新的坐标地址，进行更新
                if (data2.delivery_point != "") {
                    data2.delivery_point = "";
                }
                if (delivery_radius == "") {
                    $("#delivery_radius").val("500");
                    delivery_radius = $("#delivery_radius").val();
                }
                $("#delivery_num").val(data2.delivery_num);
                $("#address1").val(data2.delivery_address);
            }
        });
        $("#dialog").slideDown(500, function () {
        });

        /* //地图上自动获取后台传来地址的标注点***************************************
         var delivery_address=$("#address1").val();
         //标注点处解析出的地址******************
         var gc = new BMap.Geocoder();// 将地址解析结果显示在地图上,并调整地图视野
         gc.getPoint(delivery_address, function(pt){ //1次解析、后台获取列表中的地址，解析后获取坐标
         zpoint=pt;
         if(pt){
         //map.addOverlay(new BMap.Marker(pt)); //如果地址解析成功，则添加红色marker
         var myGeo = new BMap.Geocoder();//创建地址解析器实例
         // 将地址解析结果显示在地图上,并调整地图视野
         //  var maker = new BMap.Marker(pt);
         //map.addOverlay(maker);//添加覆盖物
         myGeo.getLocation(pt, function(rsl) {//2次解析是，将坐标解析成具体地址，显示在标注的提示栏中

         var addComp = rsl.addressComponents;
         var infoWindow = new BMap.InfoWindow("地址："
         + addComp.province + addComp.city
         + addComp.district + addComp.street
         + addComp.streetNumber, opts); // 创建信息窗口对象

         map.openInfoWindow(infoWindow, pt);//打开信息窗口
         var marker = new BMap.Marker(pt, {
         icon : myicon
         });
         map.addOverlay(marker);//添加层
         circle = new BMap.Circle(pt, delivery_radius, {//圆
         fillColor : "blue",//填充颜色。当参数为空时，折线覆盖物将没有填充效果。
         strokeWeight : 1,//边线的宽度，以像素为单位。
         fillOpacity : 0.3,//填充的透明度，取值范围0 - 1。
         strokeOpacity : 0.3//边线透明度，取值范围0 - 1。
         });
         map.addOverlay(circle);//添加层
         });
         //标注点处解析出的地址****************
         }else{
         var ls = new BMap.LocalSearch(delivery_address);//LocalSearch 本地搜索提供某一特定地区的位置搜索服务，比如在北京市搜索“公园”。
         ls.search(delivery_address);//search 发起检索
         ls.setSearchCompleteCallback(function(rs){//setSearchCompleteCallback 设置检索结束后的回调函数,如果是多关键字检索,回调函数参数为LocalResult的数组
         if (ls.getStatus() == BMAP_STATUS_SUCCESS){//BMAP_STATUS_SUCCESS 检索成功。对应数值“0”。getStatus:返回状态码
         var poi = rs.getPoi(0); //取第1个查询结果
         if(poi){
         var pt2 = poi.point;
         map.addOverlay(new BMap.Marker(pt2)); //如果查询到，则添加红色marker
         }
         }else{
         alert("您选择地址没有解析到结果!");
         }
         });
         }
         }, delivery_address);*/

        //地图上自动获取后台传来地址的标注点***********************************

        getmodify("<%=request.getAttribute("id")%>");
    });
    function initMap() {
        createMap();//创建地图
        setMapEvent();//设置地图事件
        addMapControl();//向地图添加控件
    }

    //创建地图函数：
    function createMap() {

        var point = new BMap.Point(117.479345, 36.744883);//定义一个中心点坐标
        map.centerAndZoom(point, 7);//设定地图的中心点和坐标并将地图显示在地图容器中

        window.map = map;//将map变量存储在全局
    }

    //地图事件设置函数：
    function setMapEvent() {
        map.enableDragging();//启用地图拖拽事件，默认启用(可不写)
        map.enableScrollWheelZoom();//启用地图滚轮放大缩小
        map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
        map.enableKeyboard();//启用键盘上下左右键移动地图
    }
    function fanhui() {
        location.href = "deliveryCustomer.do?method=getDelivery2";
    }
    function closed(only) {
        if (only == "1") {
            $("#dialog").slideUp(500, function () {
            });
            if ($("#update").val() == "update44444") {
                location.href = "map.do?method=getRemarkMap";
            } else {

                $('#form1').form('clear');
                a = false;
            }

        } else if (only == "2") {
            $("#dialog2").slideUp(500, function () {
            });
            if ($("#update").val() == "update2222") {
                location.href = "map.do?method=getRemarkMap";
            } else {
                $('#form2').form('clear');
                d = false;
            }

        }
        map.clearOverlays();
        b = true;
    }
    //地图控件添加函数：
    function addMapControl() {
        //向地图中添加缩放控件
        var opts = {
            type: BMAP_NAVIGATION_CONTROL_LARGE
        }; //放大缩小样式
        map.addControl(new BMap.NavigationControl(opts));
        //向地图中添加缩略图控件
        var ctrl_ove = new BMap.OverviewMapControl({
            anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
            isOpen: 0
        });
        map.addControl(ctrl_ove);
        //向地图中添加比例尺控件
        var ctrl_sca = new BMap.ScaleControl({
            anchor: BMAP_ANCHOR_BOTTOM_LEFT
        });
        map.addControl(ctrl_sca);
    }


    var opts = {
        width: 200,     // 信息窗口宽度
        height: 40,     // 信息窗口高度
        enableMessage: false//设置允许信息窗发送短息
    };

    function addremark() {
        map.clearOverlays();
        if (b == true) {
            b = false;
            a = true;
            $.ajax({
                type: "POST",
                async: false,
                url: 'deliveryCustomer.do?method=getDeliveryCustomerbyid',
                data: {cid: cidd},
                success: function (data2) {
                    $("#delivery_name").val(data2.delivery_name);
                    $("#delivery_tel1").val(data2.delivery_tel);
                    $("#delivery_radius").val(data2.delivery_radius);
                    $("#delivery_num").val(data2.delivery_num);
                    $("#address1").val(data2.delivery_address);
                }
            });
            $("#dialog").slideDown(500, function () {
            });
            map.setDefaultCursor("crosshair");
            map.addEventListener("click", function (e) {
                if (a == true) {
                    a = false;
                    var point = new BMap.Point(e.point.lng, e.point.lat);
                    zpoint = point;
                    var marker = new BMap.Marker(point, {icon: myicon});
                    marker.enableDragging();                        //使其能够被拖拽
                    circle = new BMap.Circle(point, 100, {
                        fillColor: "blue",
                        strokeWeight: 1,
                        fillOpacity: 0.3,
                        strokeOpacity: 0.3
                    });
                    map.addOverlay(circle);
                    map.addOverlay(marker);
                    var geoc = new BMap.Geocoder();
                    geoc.getLocation(e.point, function (rs) {
                        var addComp = rs.addressComponents;
                        infoWindow = new BMap.InfoWindow("地址：" + addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber, opts);  // 创建信息窗口对象
                        $("#address1").val(addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);
                        map.openInfoWindow(infoWindow, point);
                        marker.addEventListener("click", function () {
                            map.openInfoWindow(infoWindow, point); //开启信息窗口
                        });
                    });
                    marker.addEventListener("dragend", function (e) {
                        geoc.getLocation(e.point, function (rs) {
                            var addComp = rs.addressComponents;
                            circle.setCenter(e.point);
                            zpoint = e.point;
                            infoWindow.setContent("地址：" + addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);  // 创建信息窗口对象
                            $("#address1").val(addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);
                            map.openInfoWindow(infoWindow, e.point);
                            marker.addEventListener("click", function () {
                                map.openInfoWindow(infoWindow, e.point); //开启信息窗口
                            });
                        });
                    });

                } else {

                }
            });
        } else {

        }
    }
    function addPolygon() {
        if (b == true) {
            b = false;
            $.ajax({
                type: "POST",
                async: false,
                url: 'deliveryCustomer.do?method=getDeliveryCustomerbyid',
                data: {cid: cidd},
                success: function (data2) {
                    $("#delivery_name2").val(data2.delivery_name);
                    $("#delivery_tel2").val(data2.delivery_tel);
                    $("#delivery_num2").val(data2.delivery_num);
                    $("#address2").val(data2.delivery_address);
                }
            });
            $("#dialog2").slideDown(500, function () {
            });
            map.setDefaultCursor("crosshair");
            //var a=true;
            var addComp;
            var styleOptions = {
                strokeColor: "red",    //边线颜色。
                fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                strokeWeight: 3,       //边线的宽度，以像素为单位。
                strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
                strokeStyle: 'solid' //边线的样式，solid或dashed。
            };
            var drawingManager = new BMapLib.DrawingManager(map, {
                isOpen: true, //是否开启绘制模式
                enableDrawingTool: false, //是否显示工具栏
                drawingToolOptions: {
                    anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
                    offset: new BMap.Size(5, 5), //偏离值
                    scale: 0.8 //工具栏缩放比例
                },
                polygonOptions: styleOptions//多边形的样式
            });
            drawingManager.setDrawingMode(BMAP_DRAWING_POLYGON);
            drawingManager.addEventListener('overlaycomplete', function (e) {
                d = true;
                e.overlay.enableEditing();
                polygon = e.overlay.getPath();
                e.overlay.addEventListener('lineupdate', function (type, target) {
                    polygon = e.overlay.getPath();

                });
                var haha = e.overlay.getBounds().getCenter();
                zpoint = haha;
                var marker = new BMap.Marker(haha, {icon: myicon});
                marker.enableDragging();
                map.addOverlay(marker);
                marker.addEventListener("dragend", function (f) {
                    zpoint = f.point;
                    var geoc = new BMap.Geocoder();
                    geoc.getLocation(f.point, function (rs) {
                        addComp = rs.addressComponents;
                        infoWindow.setContent("地址：" + addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);  // 创建信息窗口对象
                        $("#address2").val(addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);
                        map.openInfoWindow(infoWindow, f.point);
                        marker.addEventListener("click", function () {
                            map.openInfoWindow(infoWindow, f.point);

                        });

                    });
                });
                var geoc = new BMap.Geocoder();
                geoc.getLocation(haha, function (rs) {
                    addComp = rs.addressComponents;
                    infoWindow = new BMap.InfoWindow("地址：" + addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber, opts);  // 创建信息窗口对象
                    $("#address2").val(addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber);
                    map.openInfoWindow(infoWindow, haha);
                    marker.addEventListener("click", function () {
                        map.openInfoWindow(infoWindow, haha); //开启信息窗口
                    });


                });


            });

        } else {

        }
    }
    function search() {
        map.clearOverlays();
        var myGeo = new BMap.Geocoder();
        // 将地址解析结果显示在地图上,并调整地图视野
        var weizhi1 = document.getElementById("weizhi").value;
        myGeo.getPoint(weizhi1, function (point) {
            if (point) {
                setfanwei(weizhi1);
                map.centerAndZoom(point, 12);
                var maker = new BMap.Marker(point);
                map.addOverlay(maker);
                myGeo.getLocation(point, function (rs) {
                    var addComp = rs.addressComponents;
                    var infoWindow = new BMap.InfoWindow("地址：" + addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber, opts);  // 创建信息窗口对象

                    map.openInfoWindow(infoWindow, point);
                    marker.addEventListener("click", function () {
                        map.openInfoWindow(infoWindow, point); //开启信息窗口
                    });
                });
            } else {
                alert("无效地址!");
            }
        });

    }
    function setfanwei(city) {
        var bdary = new BMap.Boundary();
        bdary.get(city, function (rs) {       //获取行政区域
            //map.clearOverlays();        //清除地图覆盖物
            var count = rs.boundaries.length; //行政区域的点有多少个
            for (var i = 0; i < count; i++) {
                var ply = new BMap.Polygon(rs.boundaries[i], {
                    strokeColor: "red",
                    fillColor: "",
                    strokeWeight: 3,
                    fillOpacity: 0,
                    strokeOpacity: 0.5,
                    strokeStyle: 'dashed'
                }); //建立多边形覆盖物
                map.addOverlay(ply);  //添加覆盖物
            }
        });
    }
    function updatemark(data) {
        var myform1 = document.getElementById("form1");
        $("#updateid").val(cidd);

        var cpoint = zpoint.lng + "," + zpoint.lat;

        document.forms[0].action = "deliveryCustomer.do?method=updateLocationRemarkCustomer&&delivery_point=" + cpoint + "&&delivery_areaType=0&&kkk=" + data;
        document.forms[0].submit();
    }
    function savemark(data) {
        updatemark(data);
    }
    function updatepolygon(data) {
        ppoint = zpoint.lng + "," + zpoint.lat + "|";

        for (var i = 0; i < polygon.length; i++) {
            ppoint += polygon[i].lng + "," + polygon[i].lat + "|";
        }
        ppoint = ppoint.substring(0, ppoint.length - 1);
        var myform = document.getElementById("form2");

        $("#hidden_point").val(ppoint);
        $("#updateid2").val(cidd);
        myform.action = "deliveryCustomer.do?method=updateLocationRemarkCustomer&&delivery_areaType=1&&kkk=" + data;
        myform.submit();

    }
    function savepolygon(data) {
        updatepolygon(data);
    }
    //卫星地图切换按钮
    $("#wxdtdiv").click(function () {
        obj = $("#pdwd").val();
        if (obj == "卫星") {
            $(this).css("background-image", "url('images/dt.png')");
            $("#pdwd").val("地图");
            map.setMapType(BMAP_HYBRID_MAP);
        } else {
            $(this).css("background-image", "url('images/wx.png')");
            $("#pdwd").val("卫星");
            map.setMapType(BMAP_NORMAL_MAP);
        }
    });
    function saveremark1(data1) {
        if (data1 == "savemark") {
            savemark("save");
        } else if (data1 == "savepolygon") {
            savepolygon("save");
        }
    }
    function getmodify(data) {
        if (data != "null") {
            var opts2 = {
                width: 200,     // 信息窗口宽度
                height: 130,     // 信息窗口高度
                enableMessage: false,//设置允许信息窗发送短息
            };
            $("#update").val("update");
            $.ajax({
                type: "POST",
                async: false,
                url: 'deliveryCustomer.do?method=getDeliveryCustomerbyid',
                data: {cid: data},
                success: function (data2) {
                    if (data2.delivery_areaType == 0) {
                        $("#updateid").val(data2.delivery_id);
                        var remark_point = data2.delivery_point.split(",");
                        var point = new BMap.Point(remark_point[0], remark_point[1]);
                        var marker = new BMap.Marker(point, {icon: myicon});
                        var range = data2.delivery_radius;
                        map.addOverlay(marker);
                        zpoint = point;
                        map.centerAndZoom(point, 11);
                        marker.enableDragging();                        //使其能够被拖拽
                        circle = new BMap.Circle(point, range, {
                            fillColor: "blue",
                            strokeWeight: 1,
                            fillOpacity: 0.3,
                            strokeOpacity: 0.3
                        });
                        map.addOverlay(circle);
                        var geoc = new BMap.Geocoder();
                        geoc.getLocation(point, function (rs) {
                            var addComp = rs.addressComponents;
                            infoWindow = new BMap.InfoWindow("<label class='la'>客户名称:" + data2.delivery_name + "</br>地址:" + data2.delivery_address +
                                "</br>联系人:" + data2.delivery_people + "</br>联系方式:" + data2.delivery_tel + "</label>", opts2);  // 创建信息窗口对象
                            $("#address1").val(data2.delivery_address);
                            map.openInfoWindow(infoWindow, point);
                            marker.addEventListener("click", function () {
                                map.openInfoWindow(infoWindow, point); //开启信息窗口
                            });
                        });
                        marker.addEventListener("dragend", function (e) {
                            zpoint = e.point;        //添加拖拽的响应事件
                            geoc.getLocation(e.point, function (rs) {

                                var addComp = rs.addressComponents;
                                var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                                circle.setCenter(e.point);
                                infoWindow.setContent("<label class='la'>客户名称:" + data2.delivery_name + "</br>地址:" + address +
                                    "</br>联系人:" + data2.delivery_people + "</br>联系方式:" + data2.delivery_tel + "</label>");  // 创建信息窗口对象
                                $("#address1").val(address);
                                map.openInfoWindow(infoWindow, e.point);
                                marker.addEventListener("click", function () {
                                    map.openInfoWindow(infoWindow, e.point); //开启信息窗口
                                });
                            });
                        });
                        $("#delivery_name").val(data2.delivery_name);
                        $("#delivery_tel1").val(data2.delivery_tel);
                        $("#delivery_radius").val(data2.delivery_radius);
                        $("#delivery_num").val(data2.delivery_num);
                        $("#address1").val(data2.delivery_address);
                        $("#dialog").slideDown(500, function () {
                        });
                    } else if (data2.delivery_areaType == 1) {
                        $("#updateid2").val(data2.delivery_id);
                        var remark_point = data2.delivery_point.split("|");
                        var point = new BMap.Point(remark_point[0].split(",")[0], remark_point[0].split(",")[1]);
                        var marker = new BMap.Marker(point, {icon: myicon});
                        zpoint = point;
                        map.addOverlay(marker);
                        map.centerAndZoom(point, 11);
                        var styleOptions = {
                            strokeColor: "red",    //边线颜色。
                            fillColor: "red",      //填充颜色。当参数为空时，圆形将没有填充效果。
                            strokeWeight: 3,       //边线的宽度，以像素为单位。
                            strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
                            fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
                            strokeStyle: 'solid' //边线的样式，solid或dashed。
                        };
                        var points = [];
                        for (var i = 1; i < remark_point.length; i++) {
                            var pointss = new BMap.Point(remark_point[i].split(",")[0], remark_point[i].split(",")[1]);
                            points.push(pointss);
                        }
                        var polygons = new BMap.Polygon(points, styleOptions);
                        map.addOverlay(polygons);
                        polygons.enableEditing();
                        polygon = polygons.getPath();
                        polygons.addEventListener('lineupdate', function (type, target) {
                            polygon = polygons.getPath();
                        });
                        var geoc = new BMap.Geocoder();
                        geoc.getLocation(point, function (rs) {
                            var addComp = rs.addressComponents;
                            infoWindow = new BMap.InfoWindow("<label class='la'>客户名称:" + data2.delivery_name + "</br>地址:" + data2.delivery_address +
                                "</br>联系人:" + data2.delivery_people + "</br>联系方式:" + data2.delivery_tel + "</label>", opts2);  // 创建信息窗口对象
                            $("#address2").val(data2.delivery_address);
                            map.openInfoWindow(infoWindow, point);
                            marker.addEventListener("click", function () {
                                map.openInfoWindow(infoWindow, point); //开启信息窗口
                            });
                        });
                        marker.enableDragging();
                        marker.addEventListener("dragend", function (e) {
                            zpoint = e.point;        //添加拖拽的响应事件
                            geoc.getLocation(e.point, function (rs) {


                                var addComp = rs.addressComponents;
                                var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                                infoWindow.setContent("<label class='la'>客户名称:" + data2.delivery_name + "</br>地址:" + address +
                                    "</br>联系人:" + data2.delivery_people + "</br>联系方式:" + data2.delivery_tel + "</label>");  // 创建信息窗口对象
                                $("#address2").val(address);
                                map.openInfoWindow(infoWindow, e.point);
                                marker.addEventListener("click", function () {
                                    map.openInfoWindow(infoWindow, e.point); //开启信息窗口
                                });
                            });
                        });
                        $("#dialog2").slideDown(500, function () {
                        });
                        $("#delivery_tel2").val(data2.delivery_tel);
                        $("#delivery_num2").val(data2.delivery_num);
                        $("#delivery_name2").val(data2.delivery_name);
                        $("#address2").val(data2.delivery_address);
                    }
                }
            });
        }
    }
</script>
</html>
