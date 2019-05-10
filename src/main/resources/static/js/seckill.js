/**
 * 存放主要交互逻辑的js代码
 * javascript交互逻辑模块化
 * */

var seckill = {
    /*封装秒杀相关ajax的url*/
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }

    },
    //处理秒杀逻辑
    // 获取秒杀地址，控制显示逻辑，执行秒杀
    handleSeckill: function (seckillId, node) {
        //获取秒杀地址  控制显示逻辑 执行秒杀
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //在回调函数中执行交互流程
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开始秒杀
                    //获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl:" + killUrl);
                    // 使用one 点击事件只绑定一次，防止多次点击发送大量相同请求
                    $('#killBtn').one('click', function () {
                        //绑定执行秒杀的操作
                        // 第一步先禁用按钮
                        $(this).addClass('disabled');
                        // 第二步 发送秒杀的请求
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                console.log("stateInfo:"+stateInfo);
                                //显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            } else {
                                // 秒杀失败
                                alert("错误!"+result.msg);
                            }
                        });
                    });
                    node.show();
                } else {
                    // 未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //重新计算计时逻辑
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result' + result);
            }
        });
    },
    //验证手机号
    validatePhone: function (phone) {
        //直接判断对象会看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        // 时间判断
        console.log("startTime:" + startTime);
        console.log("now:"+nowTime +"   endTime:"+endTime);
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html("秒杀结束");
        } else if (nowTime < startTime) {
            // 秒杀未开始，开始计时，使用插件,计时时间绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                // 回调函数，时间改变是帮助时间输出
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');
                seckillBox.html(format);
                //时间完成后回调时间
            }).on('finish.countdown', function () {
                // 获取秒杀地址，控制显示逻辑，执行秒杀
                seckill.handleSeckill(seckillId, seckillBox);
            });
        } else {
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            // 用户手机验证和登录，计时交互操作
            // 规划交互流程
            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            //验证手机号 返回false 显示弹出层
            if (!seckill.validatePhone(killPhone)) {
                //绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                //显示弹出层
                killPhoneModal.modal({
                    show: true,// 显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                $('#killPhoneButton').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        // 电话号码写入cookie当中
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误</lable>').show(300);
                    }
                });
            }
            //已经登录

            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    console.log("nowTime:"+nowTime);
                    //时间判断
                    // 根据系统当前时间 秒杀时间 秒杀结束时间
                    seckill.countdown(seckillId, nowTime, startTime, endTime);

                } else {
                    console.log('result:' + result);
                }
            });

        }
    }
};