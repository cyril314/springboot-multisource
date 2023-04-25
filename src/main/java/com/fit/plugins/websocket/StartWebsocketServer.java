package com.fit.plugins.websocket;

import com.fit.plugins.websocket.instantMsg.ChatServer;
import com.fit.plugins.websocket.online.OnlineChatServer;
import com.fit.util.Const;
import com.fit.util.IniFileUtil;
import com.fit.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * web服务器启动后立即执行
 */
@Component
@Order(value = 1) // 1 代表启动顺序
public class StartWebsocketServer implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(StartWebsocketServer.class);

    @Override
    public void run(ApplicationArguments var1) throws Exception {
        startWebsocketOnline();        //启动在线管理服务
        startWebsocketInstantMsg();    //启动即时聊天服务
        LOG.info("--------------系统启动成功--------------");
    }

    /**
     * 启动在线管理服务
     */
    public void startWebsocketOnline() {
        OnlineChatServer s;
        try {
            String infFilePath = PathUtil.getClasspath() + Const.SYSSET;    //配置文件路径
            String onlinePort = IniFileUtil.readCfgValue(infFilePath, "SysSet1", "onlinePort", "8869");//在线管理端口
            s = new OnlineChatServer(Integer.parseInt(onlinePort));
            s.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动即时聊天服务
     */
    public void startWebsocketInstantMsg() {
        ChatServer s;
        try {
            String infFilePath = PathUtil.getClasspath() + Const.SYSSET;    //配置文件路径
            String imPort = IniFileUtil.readCfgValue(infFilePath, "SysSet1", "imPort", "8879");//即时通讯端口
            s = new ChatServer(Integer.parseInt(imPort));
            s.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
