package com.redhat.login.server;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.redhat.login.protocol.Request;
import com.redhat.login.protocol.Response;
import com.redhat.login.protocol.ResponseCode;
import com.redhat.login.util.AES;
import com.redhat.login.util.Coder;
import com.redhat.login.util.Config;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author littleredhat
 */
public class Filter {
	private static Filter instance = null;
	private static Logger logger = LoggerFactory.getLogger(Filter.class);

	/**
	 * DEBUG
	 */
	private static Object xlock = new Object();
	private static int cnt = 0;

	public static Filter getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (Filter.class) {
				if (instance == null) {
					instance = new Filter();
				}
			}
		}
		return instance;
	}

	/**
	 * 过滤并验证
	 */
	public void doFilter(String msg, ChannelHandlerContext ctx) {
		try {
			// 存在%需要URL解码
			msg = msg.contains("%") ? URLDecoder.decode(msg, "UTF-8") : msg;
			// 解密 true使用AES false使用Base64
			msg = Config.USE_AES ? new String(AES.AESDecode(Config.AES_KEY, msg))
					: new String(Coder.decodeFromBase64(msg));

			// DEBUG
			synchronized (xlock) {
				logger.info("消息 " + ++cnt + " : " + msg);
			}

			// 解析JSON
			Request req = JSON.parseObject(msg, Request.class);
			if (req == null) {
				return;
			}

			/*
			 * // 获取id long id = req.getId(); // 获取token String token =
			 * req.getToken(); // 获取认证结果 boolean res =
			 * Authentication.checkToken(id, token); // 认证失败 if (!res) {
			 * HttpHandler.writeJSON(ctx, new Response(ResponseCode.AUTH_ERR,
			 * null)); return; }
			 */

			// Router处理
			Router.getInstance().route(req, ctx);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException错误 : " + e);
			e.printStackTrace();
			HttpHandler.writeJSON(ctx, new Response(ResponseCode.PROTO_ERR, null));
		}
	}
}
