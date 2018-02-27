/**
 * 累牛满面
 *
 * @param {Object} $
 */
var $baidumap = window.$baidumap = $baidumap || {};

(function ($) {
    // --------------------------------百度地图方法调用----------------------------------------------//
    $baidumap = {
        map: "",
        markers: [], // 司机maker点
        labels: [],//所有label
        circles: [],//所有circles
        customerMarkers: [], // 客户maker点
        linesPoints: [], // 线路points点
        points: "",// 临时存放point
        method: {},//方法
        lushu: {},
    };

    // -------------------------------这里是默认属性百度地图初始化的时候调用-----------------------------------//
    $baidumap.defaultOptions = { // 设置默认属性
        dom: "baiduMap", // dom 定义
        point: {
            lng: 116.404, // 地理经度。
            lat: 39.915, // 地理纬度。
        },
        zoom: "5", // 缩放级别
        version: '1.0.0', // 版本号
        minZoom: "5",
        maxZoom: "18",
        enableScrollWheelZoom: true, // 启用滚轮放大缩小
        callback: function (map) {

        }
    };

    // -----------------------------这里是百度地图默认属性：覆盖物的默认属性------------------------------------//

    $baidumap.overlay = $baidumap.overlay || {}; // 定义覆盖物的属性

    $baidumap.overlay.iconUrlCar = "images/kc.png"; // 覆盖物图片路径汽车

    $baidumap.overlay.labelOptions = { // 定义label 的属性
        offset: new BMap.Size(30, 5),
        position: "",
        enableMassClear: false
    };

    $baidumap.overlay.labelStyle = { // 定义label 样式
        color: "red",
        fontSize: "12px",
        height: "20px",
        lineHeight: "20px",
        fontFamily: "微软雅黑"
    };

    $baidumap.overlay.iconOptions = { // 定义覆盖icon属性
        anchor: new BMap.Size(20, 20), // 图标的定位锚点
        imageOffset: new BMap.Size(0, 0), // 图片相对于可视区域的偏移值。
        infoWindowAnchor: new BMap.Size(0, 0), // 信息窗口定位锚点
        printImageUrl: "" //
    };

    $baidumap.overlay.markerOptions = { // 定义mark默认属性
        offset: new BMap.Size(0, 0), // 标注的位置偏移值。
        icon: new BMap.Icon($baidumap.overlay.iconUrlCar,
            new BMap.Size(34, 35), $baidumap.overlay.iconOptions), // 标注所用的图标对象。
        enableMassClear: true, // 是否在调用map.clearOverlays清除此覆盖物，默认为true。
        enableDragging: false, // 是否启用拖拽，默认为false
        enableClicking: true, // 是否响应点击事件。默认为true
        raiseOnDrag: false, // 拖拽标注时，标注是否开启离开地图表面效果。默认为false。
        draggingCursor: "", // 拖拽标注时的鼠标指针样式。此属性值需遵循CSS的cursor属性规范。
        rotation: 0, // 旋转角度。
        shadow: "", // 阴影图标。
        title: "这是标题", // 鼠标移到marker上的显示内容。
    };

    $baidumap.overlay.infoWindowOptions = { // 定义信息窗口
        width: 0, // 信息窗宽度
        height: 0, // 信息窗高度
        maxWidth: 730, // 信息窗最大化时的宽度
        offset: new BMap.Size(0, 0), // 信息窗位置偏移值
        title: "信息窗标题文字", // 信息窗标题文字
        enableAutoPan: true, // 是否开启信息窗口打开时地图自动移动（默认开启）
        enableCloseOnClick: true, // 是否开启点击地图关闭信息窗口（默认开启）
        enableMessage: false, // 是否在信息窗里显示短信发送按钮（默认开启）
        message: "", // 自定义部分的短信内容，可选项
    };

    $baidumap.overlay.circleOptions = { // 定义圆形覆盖物的属性
        strokeColor: "blue", // 圆形边线颜色。
        fillColor: "yellow", // 圆形填充颜色
        strokeWeight: 2, // 圆形边线的宽度
        strokeOpacity: 0.5, // 圆形边线透明度
        fillOpacity: 0.1, // 圆形填充的透明度
        strokeStyle: "solid", // 圆形边线的样式
        enableMassClear: true, // 是否在调用map.clearOverlays清除此覆盖物，
        enableEditing: false, // 是否启用线编辑
        enableClicking: false, // 是否响应点击事件
    };

    $baidumap.overlay.polylineOptions = { // 定义折线的默认属性
        strokeColor: "red", // 折线颜色
        strokeWeight: 1, // 折线的宽度
        strokeOpacity: 0.5, // 折线的透明度
        strokeStyle: "solid", // 折线的样式
        enableMassClear: true, // 是否在调用map.clearOverlays清除此覆盖物，
        enableEditing: false, // 是否启用线编辑，
        enableClicking: false
        // 是否响应点击事件

    };

    $baidumap.overlay.lushuOptions = { // 定义路书的默认属性
        landmarkPois: [],
        icon: new BMap.Icon(
            'http://developer.baidu.com/map/jsdemo/img/car.png',
            new BMap.Size(52, 26), {
                anchor: new BMap.Size(27, 13)
            }),
        speed: 200,
        defaultContent: "",
        autoView: true,
        enableRotation: true,
    };

    $baidumap.markerClusterer = $baidumap.markerClusterer || {}; // 定义聚合对象

    $baidumap.markerClusterer.markerClustererOptions = { // 聚合的属性设置
        markers: $baidumap.markers, // 要聚合的标记数组
        girdSize: 60,
        maxZoom: 11,
        minClusterSize: 2,
        isAverangeCenter: false,
        styles: $baidumap.markerClusterer.clustererStyles
        // 自定义聚合后的图标风格
    };

    $baidumap.markerClusterer.clustererStyles = [{ // 聚合样式
        url: 'img/m1.png',
        size: new BMap.Size(53, 52),
        opt_anchor: [16, 0],
        textColor: '#333',
        opt_textSize: 10
    }, {
        url: 'img/m2.png',
        size: new BMap.Size(56, 55),
        opt_anchor: [40, 35],
        textColor: '#333',
        opt_textSize: 12
    }];


    var method = $baidumap.method;
    /**
     * 初始化百度地图 调用回调函数返回map
     *
     * @param {Object}
     *            options $baidumap.defaultOptions; 百度地图初始化参数
     *
     */
    method.init = function _init(options) { // 百度地图初始化

        if (options == undefined)
            throw new Error("option is undefined"); // options 没有定义

        if (typeof options == 'string')
            throw new Error("option is string"); // options 是字符串类型

        options = $.extend({}, $baidumap.defaultOptions, options); // 继承options
        // 的属性

        var defaultOption = $baidumap.defaultOptions;

        try {
            var _dom = options.dom == undefined ? defaultOption.dom
                : options.dom; // 获取百度地图dom对象

            var _point = options.point == undefined ? defaultOption.point
                : options.point; // 获取初始化的point

            var _zoom = options.zoom == undefined ? defaultOption.point
                : options.zoom; // 获取初始化的zoom

            var _enableScrollWheelZoom = options.enableScrollWheelZoom == undefined ? defaultOption.enableScrollWheelZoom
                : options.enableScrollWheelZoom;

            var fnCallBack = options.callback == undefined ? defaultOption.callback
                : options.callback;

            var _map = new BMap.Map(_dom, options); // 初始化百度地图

            if (_map == undefined)
                throw new Error("百度地图  初始化失败");

            var point = new BMap.Point(_point.lng, _point.lat); // 初始化 point 对象

            if (point == undefined)
                throw new Error("百度地图  point对象初始化失败");

            _map.centerAndZoom(point, _zoom); // 初始化地图,设置中心点坐标和地图级别

            _map.enableScrollWheelZoom(_enableScrollWheelZoom); // 初始化 是否支持滚轮滚动

            var butt_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_LEFT});// 左上角，添加比例尺

            var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件

            var overViewOpen = new BMap.OverviewMapControl({isOpen: false, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});

            _map.addControl(butt_left_control);

            _map.addControl(top_left_navigation);

            _map.addControl(overViewOpen);

            $baidumap.map = _map; // 将map赋值给他

        } catch (e) {
            throw new Error("百度地图初始化失败,请检查ak是否声明");
        }

        if (typeof fnCallBack != "function")
            throw new Error("回掉函数不是方法,请重新声明");

        _callback(_map, options); // 调用回调函数

        function _callback(_map, options) { // 回调函数扩展
            var fnCall = $.extend({}, options, { // 扩展回调方法
                callback: function () {
                    options.callback.call(this, _map); // 回调函数
                },
            });

            fnCall.callback(); // 调用回调方法，，，后期可以扩展使用
        }

    };

    method.getGeocoder = function (type, options) {// 地理解析type getPoint
        // 正向，getLocation逆向
        var _geoc = new BMap.Geocoder();

        options = options == undefined ? {} : options;

        var callback = options.callback;

        if (typeof callback != "function")
            throw new Error("地理解析  callback 没有定义");

        if (type == "getPoint") {// callback return point
            var address = options.address;

            var city = options.city;

            if (address == undefined)
                throw new Error("getPoint 地理解析 address 地址为输入");

            _geoc.getPoint(address, callback, city);

        } else if (type == "getLocation") {// callback return GeocoderResult

            var _point = options.point;

            if (_point == undefined)
                throw new Error("getLocation 地理解析  point 坐标点为输入");

            _geoc.getLocation(_point, callback);
        } else {
            throw new Error("地理解析 type 请选择正确的类型 getPoint 或者 getLocation ");
        }
    };

    /**
     * 添加maker 坐标点
     *
     * @param {Object}
     *            map 地图
     * @param {Object}
     *            point 坐标点
     * @param {Object}
     *            options $baidumap.overlay.markerOptions
     */
    method.addMarker = function (map, point, options) { // 添加坐标点

        var _map = map == undefined ? $baidumap.map : map;

        var _point = point;

        var _options = options == undefined ? {} : options;

        var _markerOptions = $.extend({}, $baidumap.overlay.markerOptions,
            _options); // 继承options 的属性

        /*
         * var _lng = point.lng;
         *
         * var _lat = point.lat;
         *
         * var _point = new BMap.Point(_lng, _lat);
         *
         * var _markerOptions = $baidumap.overlay.markerOptions;
         */

        var _marker = new BMap.Marker(_point, _markerOptions);

        _marker.setTop(true);

        _map.addOverlay(_marker); // 将标注添加到地图中

        $baidumap.markers.push(_marker);//添加坐标点到全局变量中

        return _marker;
    };

    /**
     * 百度地图聚合方法
     *
     * @param {Object}
     *            map 地图实例
     * @param {Object}
     *            options $baidumap.markerClusterer.markerClustererOptions
     */
    method.markerClusterer = function (map, options) { // 聚合方法

        var _map = map == undefined ? $baidumap.map : map;

        var _options = options == undefined ? {} : options;

        var _markerClustererOptions = $.extend({},
            $baidumap.markerClusterer.markerClustererOptions, _options); // 继承options
        // 的属性

        var markerClusterer = new BMapLib.MarkerClusterer(_map,
            _markerClustererOptions); // 实现聚合

        return markerClusterer; // 把聚合对象返回

    };

    /**
     * 给maker 添加label
     *
     * @param {Object}
     *            maker 坐标点
     * @param {Object}
     *            data labelTitle:"标题" labelStyle:$baidumap.overlay.labelStyle
     * @param {Object}
     *            options $baidumap.overlay.labelOptions
     */
    method.addMakerLabel = function (maker, data, options) { // 添加 label

        var _maker = maker; // 获取maker点

        options = options == undefined ? {
                position: _maker.getPosition()
            } : options;

        var _labelOptions = $.extend({}, $baidumap.overlay.labelOptions,
            options);

        data = data == undefined ? {
                labelTitle: "这是标题",
                labelStyle: $baidumap.overlay.labelStyle
            } : data;

        var _data = data; // 获取data对象

        var _title = _data.labelTitle; // 获取data标题

        var _style = _data.labelStyle; // 获取data 样式

        var _label = new BMap.Label(_title, _labelOptions); // 创建laber

        _label.setStyle(_style); // 设置样式

        _maker.setLabel(_label); // 将label 添加到 maker

        $baidumap.labels.push(_label);//添加到全局变量里面

        return _label; // 把label返回
    };

    /**
     * 创建信息窗口
     *
     * @param {Object}
     *            map
     * @param {Object}
     *            content
     * @param {Object}
     *            e
     * @param {Object}
     *            options $baidumap.overlay.infoWindowOptions
     */
    method.openInfoWindow = function (map, content, e, options) {

        var _map = map == undefined ? $baidumap.map : map;

        options = options == undefined ? {} : options;

        var _options = $.extend({}, $baidumap.overlay.infoWindowOptions,
            options);

        var _p = e;

        var _point = new BMap.Point(_p.getPosition().lng, _p.getPosition().lat); // 创建point
        // 对象

        var _infoWindow = new BMap.InfoWindow(content, _options); // 创建信息窗口对象

        _map.openInfoWindow(_infoWindow, _point); // 开启信息窗口

        return _infoWindow;
    };

    /**
     * 添加圆形覆盖物
     *
     * @param {Object}
     *            map 地图实例
     * @param {Object}
     *            point 坐标点
     * @param {Object}
     *            radius 园的半径
     * @param {Object}
     *            options $baidumap.overlay.circleOptions
     */
    method.addCircle = function (map, point, radius, options) {

        var _map = map == undefined ? $baidumap.map : map;

        options = options == undefined ? {} : options;

        var _options = $.extend({}, $baidumap.overlay.circleOptions, options);

        var _point = point;

        var _radius = radius;

        var circle = new BMap.Circle(_point, _radius, options); // 创建圆

        _map.addOverlay(circle);

        $baidumap.circles.push(circle);

        return circle;

    };
    /*
     function _addOverlay(data, options) { // 添加覆盖物方法

     var _map = $baidumap.map; // 将对象中的map 去除赋值

     if (_map == undefined)
     throw new Error("map 没有实例化,请先初始化map地图");

     var bounds = _map.getBounds();
     var sw = bounds.getSouthWest();
     var ne = bounds.getNorthEast();
     var lngSpan = Math.abs(sw.lng - ne.lng);
     var latSpan = Math.abs(ne.lat - sw.lat);
     for ( var i = 0; i < 1; i++) {
     var _point = new BMap.Point(sw.lng + lngSpan
     * (Math.random() * 0.7), ne.lat - latSpan
     * (Math.random() * 0.7));
     var _point2 = new BMap.Point(sw.lng + lngSpan
     * (Math.random() * 0.7), ne.lat - latSpan
     * (Math.random() * 0.7));
     var _maker = _addMarker(_map, _point);
     var _maker = _addMarker(_map, _point2);
     _drivingRoute(_map, _point, _point2);
     //
     // $baidumap.linesPoints.push(_point);
     // $baidumap.markers.push(_maker);
     // _addCircle(_map, _point, 100);
     _maker
     .addEventListener(
     'click',
     function(e) {
     console.info(e);
     var me = {
     username : '张三'
     };
     var data = $('#infoWindow').tmpl(me).html();

     var opts = {
     title : "<span style='color:#D2693C;font-size: 14px;font-weight: bold'></span>", // 信息窗口标题
     enableAutoPan : false, // 自动平移
     offset : new BMap.Size(-5, -15)
     // 偏离值
     };

     // _openInfoWindow(_map, data, this, opts);

     });

     var data = {
     labelTitle : "i" + i,
     labelStyle : {
     color : "blue",
     fontSize : "12px",
     height : "20px",
     lineHeight : "20px",
     fontFamily : "微软雅黑"
     }
     };
     // _addMakerLabel(_maker, data);
     }


     * _markerClusterer(_map, options);
     *
     * _addPolyline(_map, $baidumap.linesPoints);


     // _drivingRoute(_map);
     // console.info($baidumap.linesPoints)
     // _run(_map, $baidumap.linesPoints);
     }*/

    /**
     * 自定义起点重点坐标maker
     *
     * @param {Object}
     *            map
     * @param {Object}
     *            point
     * @param {Object}
     *            index
     * @param {Object}
     *            imgType
     */
    method.myIcon = function (map, point, index, imgType) {
        var myIcon, width, height, url;
        if (imgType == 1) {
            url = "http://developer.baidu.com/map/jsdemo/img/dest_markers.png";
            width = 42;
            height = 34;
            myIcon = new BMap.Icon(url, new BMap.Size(width, height), {
                offset: new BMap.Size(14, 32),
                imageOffset: new BMap.Size(0, 0 - index * height)
            });
        } else {
            url = "http://developer.baidu.com/map/jsdemo/img/trans_icons.png";
            width = 22;
            height = 25;
            var d = 25;
            var cha = 0;
            var jia = 0;
            if (index == 2) {
                d = 21;
                cha = 5;
                jia = 1;
            }
            myIcon = new BMap.Icon(url, new BMap.Size(width, d), {
                offset: new BMap.Size(10, (11 + jia)),
                imageOffset: new BMap.Size(0, 0 - index * height - cha)
            });
        }
        var _marker = new BMap.Marker(point, {
            icon: myIcon
        });
        // 起点和终点放在最上面
        if (imgType == 1) {
            _marker.setTop(true);
        }
        map.addOverlay(_marker);

        return myIcon;
    };

    /**
     * 普通 添加线路
     *
     * @param {Object}
     *            map
     * @param {Object}
     *            points
     * @param {Object}
     *            options $baidumap.overlay.polylineOptions
     */
    method.addPolyline = function (map, points, options) {

        options = options == undefined ? {} : options;

        var _options = $.extend({}, $baidumap.overlay.polylineOptions, options);

        var __polyline = new BMap.Polyline(points, _options);

        map.addOverlay(__polyline);

        map.setZoom(15);

        map.setViewport(points);

        return __polyline;

    };

    /**
     * 驾车导航
     *
     * @param {Object}
     *            map
     * @param {Object}
     *            point1
     * @param {Object}
     *            point2
     * @param {Object}
     *            options
     */
    method.drivingRoute = function (map, point1, point2, options) { // 根据坐标点返回驾车实例

        var _drivingRoute = new BMap.DrivingRoute(map, {
            onSearchComplete: drawLine
        });

        _drivingRoute.search(point1, point2);
        // _drivingRoute.search("天安门", "百度大厦");

    };
    /**
     * 驾车导航回调函数
     *
     * @param {Object}
     *            results
     */
    drawLine = function (results) {

        var map = $baidumap.map;

        var opacity = 0.45;

        var planObj = results.getPlan(0);

        if (planObj == undefined)

            throw new Error("驾车导航 未找到路线");

        var bounds = [];

        var addPoints = function (points) {
            for (var i = 0; i < points.length; i++) {
                bounds.push(points[i]);
            }

        };
        // 绘制驾车步行线路
        for (var i = 0; i < planObj.getNumRoutes(); i++) {

            var route = planObj.getRoute(i);

            if (route.getDistance(false) <= 0) {
                continue;
            }
            addPoints(route.getPath());
            // 驾车线路
            if (route.getRouteType() == BMAP_ROUTE_TYPE_DRIVING) {
                map.addOverlay(new BMap.Polyline(route.getPath(), {
                    strokeColor: "#0030ff",
                    strokeOpacity: opacity,
                    strokeStyle: "dashed",
                    strokeWeight: 6,
                    enableMassClear: true
                }));

            } else {
                // 步行线路有可能为0
                map.addOverlay(new BMap.Polyline(route.getPath(), {
                    strokeColor: "#30a208",
                    strokeOpacity: 0.75,
                    strokeStyle: "dashed",
                    strokeWeight: 4,
                    enableMassClear: true
                }));
            }
        }

        //map.setViewport(bounds);

        /*	method.myIcon(map, results.getEnd().point, 1, 1);// 结束点

         method.myIcon(map, results.getStart().point, 0, 1);// // 开始点
         */
        // _run(map,bounds,"start");
    };

    /**
     * 路书
     *
     * @param {Object}
     *            map
     * @param {Object}
     *            path
     * @param {Object}
     *            options $baidumap.overlay.lushuOptions:options;
     */
    method.lushu = function (map, path, options) {

        options = options == undefined ? $baidumap.overlay.lushuOptions
            : options;

        var lushu = new BMapLib.LuShu(map, path, options);

        $baidumap.lushu = lushu;

        return lushu;
    };

    /**
     * 普通的点运动方法
     *
     * @param {Object}
     *            map
     * @param {Object}
     *            linesPoints $baidumap.linesPoints
     *
     */
    method.run = function (map, linesPoints, type) {

        linesPoints = linesPoints == undefined ? $baidumap.linesPoints
            : linesPoints;

        var pts = linesPoints;

        var len = pts.length;

        if (len == 0)
            throw new Error("potions 为空");

        _myIcon(map, linesPoints[0], "0", "1"); // 添加起点

        _myIcon(map, linesPoints[len - 1], "1", "1"); // 添加终点

        var carMk = new BMap.Marker(pts[0], $baidumap.overlay.markerOptions);

        map.addOverlay(carMk);

        var BT = "";

        if (type == "start") {

            BT = resetMkPoint(1, len, pts, carMk);
        } else {
            window.clearTimeout(BT);
        }

        function resetMkPoint(i, len, pts, carMk) {
            carMk.setPosition(pts[i]);
            _openInfoWindow(map, "", carMk, "");
            var time;
            if (i < len) {
                time = setTimeout(function () {
                    i++;
                    resetMkPoint(i, len, pts, carMk);
                }, 100);
            }
            return time;
        }
    };


    //$baidumap.init($baidumap.default);
})(jQuery);

/*method.drivingRoute(map,new BMap.Point("117.049784","36.713985"),new BMap.Point("117.050032","36.713351"));
 getMessage();
 loadDriverPoint(0,1,'gys','2016-11-14 13:09:03','2016-11-14 13:19:44',1);
 */

var myBMap = function (data) {// 我的业务逻辑


    var myData = {
        points: [],
        lushu: "",
        interval: 1,//间隔时间分钟
        markers: []//存放签收接单
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


    myBMap.run = function () {

        myData.lushu.start();
    };
    myBMap.pause = function () {
        myData.lushu.pause();
    };
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
        var endTime = new Date().Format("yyyy-MM-dd hh:mm:ss");//签收时间
        var userName = time.userName == undefined ? "" : time.userName;//用户姓名
        //var userName = 'qdps05';
        var equipmentNum = time.equipmentNum == undefined ? "" : time.equipmentNum;//设备号

        //loadDriverPoint (0, size, userName, startTime, endTime, equipmentNum, 1);
        nowData(userName);

    }

    function getDeliveryCustomerInfo() {//获取客户信息显示客户信息
        var myicon = new BMap.Icon("images/t3.png", new BMap.Size(50, 50), {
            anchor: new BMap.Size(45, 47)
        });//添加收货客户定位信息默认图标

        $.ajax({//查询收货客户定位信息
            type: "POST",
            url: "baiduMap.do?method=getDeliveryCustomerInfo",
            data: {
                delivery_cus_name: order.send_mechanism,
                delivery_people: order.receipt_name
            },
            success: function (msg) {
                if (msg.customer) {
                    var data = msg.customer;
                    var radius = data.delivery_radius;
                    var delivery_point = data.delivery_point.split(",");
                    var point = new BMap.Point(delivery_point[0], delivery_point[1]);
                    var customer = method.addMarker(map, point, {//添加坐标点
                        icon: myicon
                    });
                    var customerCircle = method.addCircle(map, point, radius, {//添加园
                        fillColor: "blue",//填充颜色。当参数为空时，折线覆盖物将没有填充效果。
                        strokeWeight: 1,//边线的宽度，以像素为单位。
                        fillOpacity: 0.3,//填充的透明度，取值范围0 - 1。
                        strokeOpacity: 0.3//边线透明度，取值范围0 - 1。
                    });
                    $baidumap.customerMarkers.push(customer);
                    $baidumap.customerMarkers.push(customerCircle);
                }
            }
        });
    }

    function loadTimePoint() {//加载分配签收接单的坐标点


        if (time == null || time == undefined) return "没有时间信息";

        var updatetime = time.updatetime == undefined ? "" : time.updatetime;//分配时间
        var receivetime = time.receivetime == undefined ? "" : time.receivetime;//接单时间
        var sign_time = time.sign_time == undefined ? "" : time.sign_time;//签收时间
        var userName = time.userName == undefined ? "" : time.userName;//用户姓名
        var equipmentNum = time.equipmentNum == undefined ? "" : time.equipmentNum;//设备号
        var auto_sign_time = time.auto_sign_time == undefined ? "" : time.auto_sign_time;//电子围栏签收时间

        $.ajax({//加载分配签收接单的坐标点
            type: "POST",
            url: "baiduMap.do?method=getSomeTimePoint",
            data: {
                updatetime: updatetime,
                receivetime: receivetime,
                sign_time: sign_time,
                userName: userName,
                equipmentNum: equipmentNum,
                auto_sign_time: auto_sign_time
            },
            success: function (msg) {
                if (msg) {
                    for (var key in msg) {
                        var data = msg[key];
                        var title = "";
                        var imgUrl = "";

                        if (key == "receivetime") {
                            title = "接单标注";
                            imgUrl = "images/marker_purple.png";
                        }
                        if (key == "auto_sign_time") {
                            title = "电子围栏标注";
                            imgUrl = "images/marker_red.png";
                        }
                        if (key == "sign_time") {
                            title = "签收标注";
                            imgUrl = "images/marker_green.png";
                        }
                        var myIcon = new BMap.Icon(imgUrl, new BMap.Size(25, 25));
                        var point = new BMap.Point(data.lng, data.lat);
                        var marker = new BMap.Marker(point, {icon: myIcon}); // 创建标注
                        marker.setTitle(title);
                        map.addOverlay(marker);// 将标注添加到地图中
                        myData.markers.push(point);
                    }
                }
               // map.setViewport(myData.markers);
            }
        });

    }


    function loadDriverInfo() {//根据运单信息查询定位信息和轨迹
        map.clearOverlays();
        loadTimePoint();
        getDeliveryCustomerInfo();

        var userName = time.userName == undefined ? "" : time.userName;//用户姓名
        var equipmentNum = time.equipmentNum == undefined ? "" : time.equipmentNum;//设备号
        var shipping_order_state = order.shipping_order_state;
        var receivetime = time.receivetime == undefined ? "" : time.receivetime
        if (shipping_order_state > 1 && shipping_order_state < 4) {
            if (user == undefined) return $.messager.alert('地图定位提示', '您搜索的运单分配的司机未建立账户,没办法获取定位信息', 'info');
            $.ajax({//查询收货客户定位信息
                type: "POST",
                url: "baiduMap.do?method=loadMapDriverInfo",
                data: {
                    userName: userName,
                    equipmentNum: equipmentNum
                },
                success: function (msg) {
                    if (msg) {
                        var mapMessage = msg.map;
                        if (mapMessage == undefined) throw new Error("地图定位---未找到定位信息");
                        var data = mapMessage[0];
                        if (data == undefined) throw new Error("地图定位---map[0] 数组为空");
                        endPoint = new BMap.Point(data.lng, data.lat);

                        maker = method.addMarker(map, endPoint);
                        map.setCenter(endPoint);//新加的-----------
                        maker.addEventListener('click', function (e) {
                            clickMakerEventListener(this, data);// 点击事件方法
                        });
                        var labelMesage = {
                            labelTitle: data.carno
                        };
                        method.addMakerLabel(maker, labelMesage);// 给maker点添加label
                        trackCar(receivetime);
                    }
                }

            });

        } else if (shipping_order_state > 3) {
            var size = 1000;
            var startTime = time.receivetime == undefined ? "" : time.receivetime;//接单时间
            var endTime = time.sign_time == undefined ? "" : time.sign_time;//签收时间
            var userName = time.userName == undefined ? "" : time.userName;//用户姓名
            var equipmentNum = time.equipmentNum == undefined ? "" : time.equipmentNum;//设备号
            var endDateTime = new Date(endTime).getTime();
            var startDateTime = new Date(startTime).getTime();
            var date3 = endDateTime - startDateTime; //时间差的毫秒数
            var days = Math.floor(date3 / (24 * 3600 * 1000));//计算出相差天数
            if (days > 5) {
                $.messager.alert('地图定位提示', '您搜索的运单签收日期到接单日期大于5天,系统默认查询签收日期的前3天', 'info');
                startTime = new Date(endDateTime - (24 * 3600 * 1000 * 3)).Format("yyyy-MM-dd");
            }
            $.ajax({//查询收货客户定位信息
                type: "POST",
                url: "baiduMap.do?method=loadMapPointCount",
                data: {
                    userName: userName,
                    startTime: startTime,
                    endTime: endTime,
                    equipmentNum: equipmentNum
                },
                success: function (msg) {
                    var count = msg.count;

                    if (count == undefined) throw new Error("获取坐标信息数量失败");

                    if (count == 0) return $.messager.alert('提示', '节点信息未找到');
                    $.messager.alert('提示', '节点坐标信息找到 ' + count + '个');
                    var page = Math.ceil(count / size);//

                    loadDriverPoint(0, size, userName, startTime, endTime, equipmentNum, page);


                }
            });

        }

    }

    function loadDriverPoint(page, size, userName, startTime, endTime, equipmentNum, count) {//绘制轨迹

        $.ajax({//查询收货客户定位信息
            type: "POST",
            url: "baiduMap.do?method=loadMapPointInfo",
            data: {
                userName: userName,
                startTime: startTime,
                endTime: endTime,
                page: page,
                size: size,
                equipmentNum: equipmentNum
            },
            success: function (msg) {
                var list = msg.list;
                for (var i = 0; i < list.length - 1; i++) {
                    var old = list[i];
                    var now = list[i + 1];
                    myData.points.push(old);
                    var b = timeInterval(old, now);
                    if (b) continue;
                    var oldPoint = new BMap.Point(old.lng, old.lat);//始点的经纬度
                    var nowPoint = new BMap.Point(now.lng, now.lat);//终止点的经纬度
                    endPoint = nowPoint;
                    var jl = map.getDistance(oldPoint,
                        nowPoint).toFixed(2);
                        if (jl > 650) {
                        var jld = new BMap.DrivingRoute(map, {
                            onSearchComplete: function (results) {
                                var map = $baidumap.map;

                                var opacity = 0.45;

                                var planObj = results.getPlan(0);

                                if (planObj == undefined)
                                    var polyline = new BMap.Polyline([oldPoint, nowPoint],
                                        {
                                            strokeColor: "#EF5350",//设置颜色
                                            strokeWeight: 4, //宽度
                                            strokeOpacity: 1
                                        });//透明度
                                map.addOverlay(polyline);


                                var bounds = [];

                                var addPoints = function (points) {
                                    for (var i = 0; i < points.length; i++) {
                                        bounds.push(points[i]);
                                    }

                                };
                                // 绘制驾车步行线路
                                for (var i = 0; i < planObj.getNumRoutes(); i++) {

                                    var route = planObj.getRoute(i);

                                    if (route.getDistance(false) <= 0) {
                                        continue;
                                    }
                                    addPoints(route.getPath());
                                    // 驾车线路
                                    if (route.getRouteType() == BMAP_ROUTE_TYPE_DRIVING) {
                                        map.addOverlay(new BMap.Polyline(route.getPath(), {
                                            strokeColor: "green",
                                            strokeOpacity: opacity,
                                            strokeStyle: "dashed",
                                            strokeWeight: 6,
                                            enableMassClear: true
                                        }));

                                    } else {
                                        // 步行线路有可能为0
                                        map.addOverlay(new BMap.Polyline(route.getPath(), {
                                            strokeColor: "green",
                                            strokeOpacity: 0.75,
                                            strokeStyle: "dashed",
                                            strokeWeight: 4,
                                            enableMassClear: true
                                        }));
                                    }
                                }
                            }
                        });
                        jld.search(oldPoint,
                            nowPoint);
                        continue;
                    }
                    var polyline = new BMap.Polyline([oldPoint, nowPoint],
                        {
                            strokeColor: "#EF5350",//设置颜色
                            strokeWeight: 4, //宽度
                            strokeOpacity: 1
                        });//透明度
                    map.addOverlay(polyline);
                }
                if (page == (count - 1)) {
                    myData.lushu = method.lushu(map, myData.points);
                    map.setViewport(myData.points);
                    return;
                }
                if (page < count) {
                    loadDriverPoint(++page, size, userName, startTime, endTime, count);
                }
            }

        });

    }


    function clickMakerEventListener(maker, obj) {// maker点击事件
        var carsno = obj.carno;//获取用户名称

        var type = "getLocation";//定义逆向解析

        var opts = {//定义信息窗口属性
            title: "<span style='color:#D2693C;font-size: 14px;font-weight: bold'></span>", // 信息窗口标题
            enableAutoPan: false, // 自动平移
            offset: new BMap.Size(-5, -15)// 偏离值
        };

        $.getJSON("baiduMap.do?method=loadMapDriverInfo", {"userName": carsno}, function (jsons) {//根据用户名去查询最新信息

            var mapMessage = jsons.map;//赋值查询结果

            if (mapMessage == undefined) throw new Error("地图定位---未找到定位信息");

            var messagecar = mapMessage[0];//赋值查询结果

            if (messagecar == undefined) throw new Error("地图定位---map[0] 数组为空");

            var point = new BMap.Point(messagecar.lng, messagecar.lat);//更加查询结果重新定位maker的坐标

            maker.setPosition(point);//maker更新中心

            method.getGeocoder(type, {//调用方法地址解析方法
                point: maker.getPosition(),
                callback: function (geocoderResult) {//解析方法回调函数

                    if (geocoderResult == null) throw new Erorr("点击 maker点  获取_getGeocoder 逆向定位失败");
                    var point = geocoderResult.point;//坐标地址
                    var address = geocoderResult.address;//解析地址
                    var addressComponents = geocoderResult.addressComponents;//结构化的地址描述
                    var province = addressComponents.province;//省份名称。
                    var city = addressComponents.city;//城市名称。
                    var district = addressComponents.district;//区县名称。
                    var street = addressComponents.street;//街道名称。
                    var streetNumber = addressComponents.streetNumber;//门牌号码。
                    var temperature = "未获取到温度";
                    var weather = "未获取到天气情况";
                    var wind = "未获取到风力情况";
                    var weather_data = "";

                    $.ajax({
                        url: "http://api.map.baidu.com/telematics/v3/weather?" + $.param({//调用jsonp 请求天气信息
                            location: city,
                            output: "json", // 城市
                            ak: "3ktrkqQ4iISBiphzz8mkrZ68"
                        }),
                        dataType: 'jsonp',
                        success: function (data) {
                            if (data) {
                                if (data.status = "success") {
                                    weather_data = data.results[0].weather_data[0];
                                    temperature = weather_data.temperature;
                                    weather = weather_data.weather;
                                    wind = weather_data.wind;
                                    var messagedata = {
                                        username: messagecar.carno,
                                        depement: messagecar.deptname,
                                        state: messagecar.state,
                                        address: address,
                                        time: messagecar.nowdate,
                                        weather_data: weather + wind + temperature,
                                        point: messagecar.lng + "," + messagecar.lat
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

        $.getJSON("map.do?method=loadmapinfo", function (json) {

            $(json).each(function (i) {

                var obj = json[i];// 变量赋值

                var data = {};// 定义data对象

                data.labelTitle = obj.carno;// 增加data属性labelTitle

                var point = new BMap.Point(obj.lng, obj.lat);// 根据查询的对象或去坐标点

                var maker = method.addMarker(map, point);// 将maker点添加到地图中去

                maker.addEventListener('click', function (e) {

                    clickMakerEventListener(this, obj);// 点击事件方法
                });
                method.addMakerLabel(maker, data);// 给maker点添加label
            });

            map.setViewport(map.makers);
            method.markerClusterer(map, $baidumap.markers);//调用聚合方法
        });
    }

    function timeInterval(old, now) {
        var oldNowDate, nowNowDate;
        oldNowDate = old.nowdate;
        nowNowDate = now.nowdate;
        var s1 = new Date(oldNowDate);
        var s2 = new Date(nowNowDate);
        var days = s2.getTime() - s1.getTime();
        var minutes = days / 60000;
        if (minutes < myData.interval) return false;
        var oldPoint = new BMap.Point(old.lng, old.lat);
        var nowPoint = new BMap.Point(now.lng, now.lat);
        method.drivingRoute(map, oldPoint, nowPoint);
        return true;
    }
};


//myBMap();


