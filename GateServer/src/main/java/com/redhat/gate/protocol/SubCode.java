package com.redhat.gate.protocol;

/**
 * @author littleredhat
 * 
 *         子请求码
 */
public class SubCode {
	public static final short TEST = 9981; // 测试

	public static final String Login = "login"; // 用户登录
	public static final String Register = "register"; // 用户注册

	public static final String GetInfo = "getInfo"; // 获取信息
	public static final String UpdateInfo = "updateInfo"; // 更新信息

	public static final String GetScore = "getScore"; // 获取成绩
	public static final String UpdateScore = "updateScore"; // 更新成绩
}
