package com.redhat.login.core;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.login.util.Redis;
import com.redhat.login.core.GameServer;
import com.redhat.login.util.Config;
import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * @author littleredhat
 * 
 *         服务端初始类
 */
public class GameInit {
	private static Logger logger = LoggerFactory.getLogger(GameInit.class);

	/**
	 * Main
	 */
	public static void main(String[] args) throws Exception {
		// 启动Redis
		Redis.getInstance().initData();

		if (Config.USE_NET) { // 网页启动
			JMXOpen();
		} else { // 测试
			GameServer.getInstance().startServer();
		}
	}

	// JMX 网页启动 127.0.0.1:9900
	private static void JMXOpen() throws Exception {
		// 创建server
		MBeanServer server = MBeanServerFactory.createMBeanServer();

		ObjectName gameServerName = new ObjectName("GameInit:name=GameServer");
		// 注册MBean
		server.registerMBean(GameServer.getInstance(), gameServerName);

		HtmlAdaptorServer adapter = new HtmlAdaptorServer();
		adapter.setPort(Config.LoginNetPort);
		ObjectName adapterName = new ObjectName("GameInit:name=HtmlAdapter,port=" + Config.LoginNetPort);
		// 注册MBean
		server.registerMBean(adapter, adapterName);

		// 创建adapter
		adapter.start();

		logger.info("GameInit at {}:{}", Config.LoginIp, Config.LoginNetPort);
	}
}
