<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/17/017
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DMS APP 下载</title>
    <style type="text/css">
        #weixin-tip {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            background: rgba(0, 0, 0, 0.8);
            filter: alpha(opacity=80);
            width: 100%;
            height: 100%;
            z-index: 100;
        }

        #weixin-tip p {
            text-align: center;
            margin-top: 10%;
            padding: 0 5%;
            position: relative;
        }

        #weixin-tip .close {
            color: #fff;
            padding: 5px;
            font: bold 20px/24px simsun;
            text-shadow: 0 1px 0 #ddd;
            position: absolute;
            top: 0;
            left: 5%;
        }
    </style>
    <script type="application/javascript">
        var is_weixin = (function(){return navigator.userAgent.toLowerCase().indexOf('micromessenger') !== -1})();
         window.onload = function() {
            var winHeight = typeof window.innerHeight != 'undefined' ? window.innerHeight : document.documentElement.clientHeight; //兼容IOS，不需要的可以去掉
            var btn = document.getElementById('J_weixin');
            var tip = document.getElementById('weixin-tip');
            var close = document.getElementById('close');
            if (is_weixin) {
                tip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
                tip.style.display = 'block';
              /*  btn.onclick = function(e) {
                    tip.style.height = winHeight + 'px'; //兼容IOS弹窗整屏
                    tip.style.display = 'block';
                    return false;
                };*/
                close.onclick = function() {
                    tip.style.display = 'none';
                }
            }else{
                window.location.href="DMS.apk";
            }
        }
    </script>
</head>
<body>
<%--
<a id="J_weixin" class="android-btn" href="http://www.56dms.com/apk/DMS.apk"><img src="images/android-btn.png" alt="安卓版下载"/></a>
--%>
<div id="weixin-tip"><p><img src="../images/live_weixin.png" alt="微信打开"/><span id="close" title="关闭" class="close">×</span>
</p></div>
</body>
</html>
