package com.ym.yth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketService {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);
    //用来存放每个客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
    //记录当前的连接数
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接口客户端的标识
    private String sid = "";


    /**
     * 连接建立成功后的回调
     *
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        LOG.info("有新窗口开始监听:" + sid + "，当前人数为" + getOnlineCount());
        this.sid = sid;
        try {
            sendMessage("后台告诉前台：Socket连接成功");
        } catch (IOException e) {
            LOG.error("WebSocket连接IO异常");
        }
    }

    /**
     * 连接关闭时的回调
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        LOG.info("有一连接关闭！当前在线人数为：" + getOnlineCount());
    }


    /**
     * 收到客户端消息后的回调
     *
     * @param msg     客户端发送的信息
     * @param session 当前会话
     */
    @OnMessage
    public void onMessage(String msg, Session session) {
        LOG.info("收到来自窗口" + sid + "的信息:" + msg);
        for (WebSocketService item : webSocketSet) {
            try {
                String s = new StringBuffer(msg).reverse().toString();
                String s1 = "后台收到了前台发送的数据" + msg + "处理后开始发往前端：" + s;
                System.out.println(s1);
                item.sendMessage(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭时的回调
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
        LOG.info("Error");
    }

    /**
     * 服务器主动发送信息
     *
     * @param s 信息内容
     * @throws IOException
     */
    public void sendMessage(String s) throws IOException {
        this.session.getBasicRemote().sendText(s);
    }

    //群发自定义消息
    public static void sendInfo(String message, @PathParam("sid") String sid) throws IOException {
        LOG.info("推送消息到窗口:" + sid + "，推送内容：" + message);
        for (WebSocketService service : webSocketSet) {
            try {
                if (sid == null) {
                    service.sendMessage(message);
                } else if (service.sid.equals(sid)) {
                    service.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }


    public static synchronized int addOnlineCount() {
        return WebSocketService.onlineCount++;
    }

    public static synchronized int subOnlineCount() {
        return WebSocketService.onlineCount--;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

}
