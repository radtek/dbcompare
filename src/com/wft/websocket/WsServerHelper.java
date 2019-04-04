package com.wft.websocket;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocketImpl;
import org.springframework.stereotype.Component;

import com.wft.util.SystemMessage;

//需要打开8887端口
@Component
public class WsServerHelper {

	private final static Logger log = Logger.getLogger(WsServerHelper.class);
	
	@PostConstruct
	public void init(){
		int port = SystemMessage.getInteger("websocket_port");
		log.info("====启动监听websocket:"+port);
		startWebsocketInstantMsg(port);
	}
	
	 /**
     * 启动即时聊天服务
     */
    public void startWebsocketInstantMsg(int port) {
        WebSocketImpl.DEBUG = false;
        WsServer s;
        s = new WsServer(port);
        s.start();
    }
}
