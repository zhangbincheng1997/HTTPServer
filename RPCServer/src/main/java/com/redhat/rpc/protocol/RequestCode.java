package com.redhat.rpc.protocol;

/**
 * @author littleredhat
 * 
 *         请求码
 */
public class RequestCode {
	public static final int TEST = 10000; // 测试

	public static final int C2S_Login = 10001; // 登录
	public static final int C2S_Register = 10002; // 注册

	public static final int C2S_Get_Info = 20001; // 获取用户信息
	public static final int C2S_Update_Info = 20002; // 更新用户信息

	public static final int C2S_Get_Score = 30001; // 获取成绩
	public static final int C2S_Set_Score = 30002; // 设置成绩
	public static final int C2S_Update_Score = 30003; // 更新成绩

	public static final int C2S_Get_Mail = 40001; // 获取邮件
	public static final int C2S_Send_Mail = 40002; // 发送邮件
}
