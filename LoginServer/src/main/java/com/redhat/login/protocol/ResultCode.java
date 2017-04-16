package com.redhat.login.protocol;

/**
 * @author littleredhat
 * 
 *         响应码
 */
public class ResultCode {
	public static final int SUCCESS = 1000; // 成功

	public static final int COMMON_ERR = 5000; // 默认错误
	public static final int SERVER_ERR = 5001; // 服务器错误
	public static final int AUTH_ERR = 5002; // 认证错误
	public static final int PROTO_ERR = 5003; // 协议错误

	public static final int S2C_Login_Username_Err = 10001; // 用户名不存在
	public static final int S2C_Login_Password_Err = 10002; // 密码错误
	public static final int S2C_Login_Black_List = 10003; // 黑名单

	public static final int S2C_Register_Username_Err = 10004; // 用户名已存在
	public static final int S2C_Register_Username_Ill = 10005; // 用户名不合法

	public static final int S2C_Info_No = 20001; // 用户信息不存在

	public static final int S2C_Score_No = 30001; // 成绩不存在

	public static final int S2C_Mail_No = 40001; // 邮件不存在
}
