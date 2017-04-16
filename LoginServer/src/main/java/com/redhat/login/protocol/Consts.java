package com.redhat.login.protocol;

/**
 * @author littleredhat
 * 
 *         服务端常量
 */
public class Consts {
	public static final boolean USE_NET = false; // 是否使用网页启动
	public static final boolean USE_AES = false; // 是否使用AES加密

	// AES密钥
	// 最好不要明文保存 可以将密钥加密放在文件读取
	public static final String AES_KEY = "maomao";

	public static final String SESSION_KEY = "key"; // sessionKey
	public static final String SESSION_GET = "cookie"; // session
	public static final String ID_GET = "id"; // id
	public static final String CONTENT_TYPE = "application/json; charset=utf-8"; // Http内容类型
}
