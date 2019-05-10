
package com.imooc.seckill.service.SpecialService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.LongAdder;


/**
 * websocket服务端核心类
 * @author : wang zns
 * @date : 2019-05-08
 */
@ServerEndpoint("/websocket")
@Component
@Slf4j
public class WebSocketService {

    /**
     * 记录当前websocket的连接数（保证线程安全）
     */
    private static LongAdder connectAccount = new LongAdder();

    /**
     *存放每个客户端对应的websocketServer对象(需保证线程安全)
     */
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
    /**
     * 与客户端的连接对象
     */
    private Session session;

    /**
     * 连接成功调用的方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        connectAccount.increment();
        log.info("有新的连接接入，当前连接数为{}", connectAccount);
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        connectAccount.decrement();
        log.info("有连接关闭，当前连接数为{}", connectAccount);
    }

    /**
     * 收到客户端消息时调用
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("收到客户端发来的消息,message -> {}", message);
    }

    /**
     * 服务端向客户端发送消息
     * @param message
     */
    public void sendMessage(String message) {
        for (WebSocketService webSocketService : webSocketSet) {
            try {
                log.info("【websocket消息】 广播消息, message={}", message);
                webSocketService.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("向客户端发送消息失败, {}", e.getMessage());
            }
        }
    }

}