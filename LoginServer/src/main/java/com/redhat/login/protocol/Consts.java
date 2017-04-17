package com.redhat.login.protocol;

/**
 * @author littleredhat
 * 
 *         服务端常量
 */
public class Consts {
	// AES密钥
	// 最好不要明文保存 可以将密钥加密放在文件读取
	public static final String AES_KEY = "maomao";

	public static final String SESSION_KEY = "key"; // session Key
	public static final String SESSION_GET = "Cookie"; // session Header
	public static final String UTF8 = "UTF-8"; // UTF-8
	public static final String CONTENT_TYPE = "application/json; charset=utf-8"; // Http内容类型
}
