package com.redhat.login.core;

/**
 * @author littleredhat
 * 
 *         MBean 用来作为JMX接口
 */
public interface GameServerMBean {

	// 设置shutdown
	public boolean getShutdown();

	// 获取shutdown
	public void setShutdown(boolean shutdown);

	// 开启服务器
	public void startServer();

	// 关闭服务器
	public void shutServer();
}
