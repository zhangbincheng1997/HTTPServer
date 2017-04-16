package com.redhat.login.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.login.server.HttpServer;

/**
 * @author littleredhat
 * 
 *         服务端管理类
 */
public class GameServer implements GameServerMBean {
	private static GameServer instance = null;
	private static Logger logger = LoggerFactory.getLogger(GameServer.class);
	private boolean shutdown = true;

	private GameServer() {
	}

	public static GameServer getInstance() {
		if (instance == null) {
			instance = new GameServer();
		}
		return instance;
	}

	/**
	 * 获取shutdown
	 */
	public boolean getShutdown() {
		return shutdown;
	}

	/**
	 * 设置shutdown
	 */
	public void setShutdown(boolean shutdown) {
		this.shutdown = shutdown;
	}

	/**
	 * 开启服务器
	 */
	public void startServer() {
		logger.info("================开启服务器================");
		HttpServer.getInstance().start();
		shutdown = false;
		logger.info("================服务器开启================");
	}

	/**
	 * 关闭服务器
	 */
	public void shutServer() {
		logger.info("================关闭服务器================");
		HttpServer.getInstance().shut();
		shutdown = true;
		logger.info("================服务器关闭================");
	}
}
