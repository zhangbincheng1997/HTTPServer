package com.redhat.login.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.redhat.login.server.HttpHandler;
import com.redhat.login.protocol.ResultCode;
import com.redhat.login.handler.UserHandler;
import com.redhat.login.protocol.HttpMsg;
import com.redhat.login.protocol.RequestCode;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author littleredhat
 * 
 *         Router
 */
public class Router {
	private static Router instance = null;
	private Logger logger = LoggerFactory.getLogger(Router.class);

	private UserHandler userHandler = null;

	private Router() {
	}

	public static Router getInstance() {
		if (instance == null) {
			instance = new Router();
			instance.initHandler();
		}
		return instance;
	}

	private void initHandler() {
		userHandler = new UserHandler();
	}

	/**
	 * Route处理
	 */
	public void route(String msg, ChannelHandlerContext ctx) {
		// 获取消息
		HttpMsg httpMsg = null;
		try {
			httpMsg = JSON.parseObject(msg, HttpMsg.class);
		} catch (Exception e) {
			logger.error("格式错误");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.PROTO_ERR, ""));
			return;
		}
		// 消息处理
		switch (httpMsg.getCode()) {
		case RequestCode.TEST:
			logger.info("Router TEST");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.SUCCESS, ""));
			break;
		case RequestCode.C2S_Login: {
			logger.info("Router C2S_Login");
			HttpMsg res = userHandler.login(httpMsg);
			HttpHandler.writeJSON(ctx, res);
			break;
		}
		case RequestCode.C2S_Register: {
			logger.info("Router C2S_Register");
			HttpMsg res = userHandler.register(httpMsg);
			HttpHandler.writeJSON(ctx, res);
			break;
		}
		default:
			logger.error("无协议号");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.PROTO_ERR, ""));
			break;
		}
	}
}
