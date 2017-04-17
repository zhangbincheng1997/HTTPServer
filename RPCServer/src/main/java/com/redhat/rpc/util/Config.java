package com.redhat.rpc.util;

import com.redhat.rpc.util.Property;

/**
 * @author littleredhat
 * 
 *         Config工具类
 */
public class Config {
	// Login网页启动端口
	public static final int LoginNetPort = Integer.parseInt(Property.getData("loginNetPort"));
	// Login本地端口
	public static final int LoginLocalPort = Integer.parseInt(Property.getData("loginLocalPort"));
	// Login ip
	public static final String LoginIp = Property.getData("loginIp");

	// Gate网页启动端口
	public static final int GateNetPort = Integer.parseInt(Property.getData("gateNetPort"));
	// Gate本地端口
	public static final int GateLocalPort = Integer.parseInt(Property.getData("gateLocalPort"));
	// Gate ip
	public static final String GateIp = Property.getData("gateIp");

	// Node网页启动端口
	public static final int NodeNetPort = Integer.parseInt(Property.getData("nodeNetPort"));
	// Node本地端口
	public static final int NodeLocalPort = Integer.parseInt(Property.getData("nodeLocalPort"));
	// Node ip
	public static final String NodeIp = Property.getData("nodeIp");

	// Redis配置
	public static final String RedisIp = Property.getData("redisIp");
	public static final int RedisPort = Integer.parseInt(Property.getData("redisPort"));;
	public static final String RedisPassword = Property.getData("redisPassword"); // 授权密码
	public static final int RedisTimeOut = Integer.parseInt(Property.getData("redisTimeOut"));; // 超时时间
	public static final int RedisMaxActive = Integer.parseInt(Property.getData("redisMaxActive"));; // 最大活动连接
	public static final int RedisMaxIdle = Integer.parseInt(Property.getData("redisMaxIdle"));; // 最大空闲连接
	public static final int RedisMaxWait = Integer.parseInt(Property.getData("redisMaxWait"));; // 最大等待时间
	public static boolean RedisTestOnBorrow = Boolean.getBoolean(Property.getData("redisTestOnBorrow")); // 获取连接时候进行检验
	public static boolean RedisTestOnReturn = Boolean.getBoolean(Property.getData("redisTestOnReturn")); // 归还连接时候进行检验
	public static boolean RedisTestWhileIdle = Boolean.getBoolean(Property.getData("redisTestWhileIdle")); // 空闲时候进行检验

	// 是否使用网页启动
	public static boolean UseNet = Boolean.getBoolean(Property.getData("useNet"));
	// 是否使用AES加密
	public static boolean UseAES = Boolean.getBoolean(Property.getData("useAES"));
}
