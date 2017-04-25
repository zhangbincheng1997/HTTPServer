package com.redhat.login.util;

import com.redhat.login.util.Property;

/**
 * @author littleredhat
 * 
 *         Config工具类
 */
public class Config {
	// Login网页启动端口
	public static final int LoginNetPort = Integer.parseInt(Property.getData("LoginNetPort"));
	// Login本地端口
	public static final int LoginLocalPort = Integer.parseInt(Property.getData("LoginLocalPort"));
	// Login ip
	public static final String LoginIp = Property.getData("LoginIp");

	// Gate网页启动端口
	public static final int GateNetPort = Integer.parseInt(Property.getData("GateNetPort"));
	// Gate本地端口
	public static final int GateLocalPort = Integer.parseInt(Property.getData("GateLocalPort"));
	// Gate ip
	public static final String GateIp = Property.getData("GateIp");

	// Node网页启动端口
	public static final int NodeNetPort = Integer.parseInt(Property.getData("NodeNetPort"));
	// Node本地端口
	public static final int NodeLocalPort = Integer.parseInt(Property.getData("NodeLocalPort"));
	// Node ip
	public static final String NodeIp = Property.getData("NodeIp");

	// Redis配置
	public static final String RedisIp = Property.getData("RedisIp");
	public static final int RedisPort = Integer.parseInt(Property.getData("RedisPort"));;
	public static final String RedisPassword = Property.getData("RedisPassword"); // 授权密码
	public static final int RedisTimeOut = Integer.parseInt(Property.getData("RedisTimeOut"));; // 超时时间
	public static final int RedisMaxActive = Integer.parseInt(Property.getData("RedisMaxActive"));; // 最大活动连接
	public static final int RedisMaxIdle = Integer.parseInt(Property.getData("RedisMaxIdle"));; // 最大空闲连接
	public static final int RedisMaxWait = Integer.parseInt(Property.getData("RedisMaxWait"));; // 最大等待时间
	public static final boolean RedisTestOnBorrow = Boolean.getBoolean(Property.getData("RedisTestOnBorrow")); // 获取连接时候进行检验
	public static final boolean RedisTestOnReturn = Boolean.getBoolean(Property.getData("RedisTestOnReturn")); // 归还连接时候进行检验
	public static final boolean RedisTestWhileIdle = Boolean.getBoolean(Property.getData("RedisTestWhileIdle")); // 空闲时候进行检验

	// 是否使用网页启动
	public static final boolean USE_NET = Boolean.getBoolean(Property.getData("USE_NET"));
	// 是否使用AES加密
	public static final boolean USE_AES = Boolean.getBoolean(Property.getData("USE_AES"));
	// AES密钥
	// 最好不要明文保存 可以将密钥加密放在文件读取
	public static final String AES_KEY = Property.getData("AES_KEY");
}
