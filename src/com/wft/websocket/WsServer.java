package com.wft.websocket;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class WsServer  extends WebSocketServer{
	
	private final static Logger log = Logger.getLogger(WebSocketServer.class);
	
	  public WsServer(int port) {
	        super(new InetSocketAddress(port));
	    }

	    public WsServer(InetSocketAddress address) {
	        super(address);
	    }

	    @Override
	    public void onOpen(WebSocket conn, ClientHandshake handshake) {
	        // ws连接的时候触发的代码，onOpen中我们不做任何操作
	    	// log.info("onopen..."+conn.toString());
	    }

	    @Override
	    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
	        //断开连接时候触发代码
	        userLeave(conn);
	        log.info(reason);
	    }

	    @Override
	    public void onMessage(WebSocket conn, String message) {
	    	log.info(message);
	        if(null != message &&message.startsWith("online")){
	            String userName=message.replaceFirst("online", message);//用户名
	            userJoin(conn,userName);//用户加入
	        }else if(null != message && message.startsWith("offline")){
	            userLeave(conn);
	        }

	    }

	    @Override
	    public void onError(WebSocket conn, Exception ex) {
	        //错误时候触发的代码
	    	log.info("on error");
	        ex.printStackTrace();
	    }
	    /**
	     * 去除掉失效的websocket链接
	     * @param conn
	     */
	    private void userLeave(WebSocket conn){
	        WsPool.removeUser(conn);
	    }
	    /**
	     * 将websocket加入用户池
	     * @param conn
	     * @param userName
	     */
	    private void userJoin(WebSocket conn,String userName){
	        WsPool.addUser(userName, conn);
	    }

}
