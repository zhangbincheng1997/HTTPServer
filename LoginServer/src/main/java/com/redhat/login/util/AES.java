package com.redhat.login.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author littleredhat
 * 
 *         AES工具类
 */
@SuppressWarnings("restriction")
public class AES {

	/**
	 * AES加密
	 */
	public static String AESEncode(String rule, String content) {
		try {
			// 1.构造密钥生成器
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.初始化密钥生成器
			// 生成一个128位的随机数
			keygen.init(128, new SecureRandom(rule.getBytes()));
			// 3.获得原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得密钥字节数组
			byte[] raw = original_key.getEncoded();
			// 5.生成密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			// 6.生成密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 7.初始化密码器
			// 加密 Encrypt_mode
			// 解密 Decrypt_mode
			// 第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			// 8.获取加密内容字节数组
			byte[] byte_encode = content.getBytes("utf-8");
			// 9.根据密码器的初始化方式加密
			byte[] byte_AES = cipher.doFinal(byte_encode);
			// 10.将加密后的数据转化为字符串
			String AES_encode = new String(new BASE64Encoder().encode(byte_AES));
			// 11.返回字符串
			return AES_encode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密
	 */
	public static String AESDecode(String rule, String content) {
		try {
			// 1.构造密钥生成器
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			// 2.初始化密钥生成器
			// 生成一个128位的随机数
			keygen.init(128, new SecureRandom(rule.getBytes()));
			// 3.获得原始对称密钥
			SecretKey original_key = keygen.generateKey();
			// 4.获得密钥字节数组
			byte[] raw = original_key.getEncoded();
			// 5.生成密钥
			SecretKey key = new SecretKeySpec(raw, "AES");
			// 6.生成密码器
			Cipher cipher = Cipher.getInstance("AES");
			// 7.初始化密码器
			// 加密 Encrypt_mode
			// 解密 Decrypt_mode
			// 第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			// 8.获取解密内容字节数组
			byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
			// 9.根据密码器的初始化方式解密
			byte[] byte_decode = cipher.doFinal(byte_content);
			// 10.将解密后的数据转化为字符串
			String AES_decode = new String(byte_decode, "utf-8");
			// 11.返回字符串
			return AES_decode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
