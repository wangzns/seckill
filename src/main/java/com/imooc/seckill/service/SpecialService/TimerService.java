package com.imooc.seckill.service.SpecialService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author : wang zns
 * @date : 2019-05-08
 */
@Component
@Slf4j
public class TimerService {

    @Autowired
    private WebSocketService webSocketService;


    public void executeTimeCountdown(Long startTimestamp, Long endTimestamp) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                // 向websocket客户端推送消息
                log.info("【executeTimeCountdown】 run，endTimestamp={}"+endTimestamp);
                webSocketService.sendMessage("秒杀已结束");
            }
        };
        // 到秒杀结束时间时执行
        log.info("startTimestamp={}", startTimestamp);
        log.info("endTimestamp={}", endTimestamp);
        log.info("currentTimestamp={}", System.currentTimeMillis());
        timer.schedule(timerTask, endTimestamp - System.currentTimeMillis());
    }

}
