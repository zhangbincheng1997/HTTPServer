package com.redhat.login.protocol;

/**
 * @author littleredhat
 * 
 *         响应码
 */
public class ResponseCode {
	public static final short SUCCESS = 1000; // 成功

	public static final short COMMON_ERR = 5000; // 默认错误
	public static final short SERVER_ERR = 5001; // 服务器错误
	public static final short AUTH_ERR = 5002; // 认证错误
	public static final short PROTO_ERR = 5003; // 协议错误

	public static final short Login_Username_Err = 10001; // 用户名不存在
	public static final short Login_Password_Err = 10002; // 用户密码错误
	public static final short Login_Black_List = 10003; // 用户进入黑名单

	public static final short Register_Username_Err = 10004; // 用户名已存在
	public static final short Register_Username_Ill = 10005; // 用户名不合法
}
