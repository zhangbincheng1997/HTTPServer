package com.redhat.login.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author littleredhat
 * 
 *         Coder工具类
 */
public class Coder {

	/**
	 * Base64编码
	 */
	public static String encodeToBase64(String data) {
		return Base64.encodeBase64String(data.getBytes());
	}

	/**
	 * Base64解码
	 */
	public static byte[] decodeFromBase64(String data) {
		return Base64.decodeBase64(data.getBytes());
	}

	/**
	 * md5编码
	 */
	public static String encodeToMD5(String data) {
		return DigestUtils.md5Hex(data);
	}

	/**
	 * sha1编码
	 */
	public static String encodeToSHA1(String data) {
		return DigestUtils.sha1Hex(data);
	}
}
