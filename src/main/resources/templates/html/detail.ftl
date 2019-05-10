<html>
<head>
    <title>秒杀详情页</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <span class="glyphicon glyphicon-time"></span>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>


<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone">填写手机号</span>
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey" placeholder="填手机号(*╹▽╹*)"
                               class="form-control">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <#--验证信息-->
                <span id="killPhoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneButton" class="btn">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>

</body>

<script src="https://cdn.bootcss.com/jquery/2.0.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/js/bootstrap.min.js"></script>
<#--jquery cookie插件-->
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<#--jquery倒计时插件-->
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>

<script src="/js/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        seckill.detail.init({
            seckillId:${seckill.seckillId?c},
            startTime: ${seckill.startTimestamp?c}, //转换毫秒
            endTime: ${seckill.endTimestamp?c}
        });
    });
</script>

<script>
    var websocket;
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://127.0.0.1/websocket');
    } else {
        alert("你的浏览器暂不支持websocket,请更换其他浏览器再试");
    }

    websocket.onopen = function (event) {
        console.log("建立连接");
    };
    websocket.onclose = function (event) {
        console.log("关闭连接");
    };
    websocket.onmessage = function (event) {
        console.log("收到消息," + event.data);
        // 弹窗提示与播放提示音乐
        var msg = event.data;
        if (msg === "秒杀已结束") {
            $('#seckill-box').html("秒杀结束");
        }
    };
    websocket.onerror = function (event) {
        console.log("websocket异常");
    };
    /**
     * readyState状态如下:
     * CONNECTING：值为0，表示正在连接；
     OPEN：值为1，表示连接成功，可以通信了；
     CLOSING：值为2，表示连接正在关闭；
     CLOSED：值为3，表示连接已经关闭，或者打开连接失败。
     */

    // 向服务端发送消息(必须为open状态时可发送)
    if (websocket.readyState === 1) {
        websocket.send("服务端你好");
    }

</script>



</html>




