package com.redhat.login.protocol;

/**
 * @author littleredhat
 * 
 *         RPC协议码
 */
public class RPCCode {
	public static final String RPC_TYPE = "RPC_TYPE"; // 节点类型key
	public static final String TYPE_INFO = "TYPE_INFO"; // 节点服务info
	public static final String TYPE_SCORE = "TYPE_SCORE"; // 节点服务score
	public static final String TYPE_MAIL = "TYPE_MAIL"; // 节点服务mail

	public static final String Get_Info = "getInfo"; // 获取用户信息
	public static final String Update_Info = "updateInfo"; // 更新用户信息
	public static final String Get_Score = "getScore"; // 获取成绩
	public static final String Set_Score = "setScore"; // 设置成绩
	public static final String Update_Score = "updateScore"; // 更新成绩
	public static final String Get_Mail = "getMail"; // 获取邮件
	public static final String Send_Mail = "sendMail"; // 发送邮件
}
