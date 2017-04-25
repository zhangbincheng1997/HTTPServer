package com.redhat.login.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.redhat.login.server.HttpHandler;
import com.redhat.login.handler.UserHandler;
import com.redhat.login.model.UserModel;
import com.redhat.login.protocol.Request;
import com.redhat.login.protocol.RequestCode;
import com.redhat.login.protocol.Response;
import com.redhat.login.protocol.ResponseCode;
import com.redhat.login.protocol.SubCode;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author littleredhat
 * 
 *         Router
 */
public class Router {
	private static Router instance = null;
	private Logger logger = LoggerFactory.getLogger(Router.class);

	private Router() {
	}

	public static Router getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (Router.class) {
				if (instance == null) {
					instance = new Router();
				}
			}
		}
		return instance;
	}

	/**
	 * Route处理
	 */
	public void route(Request msg, ChannelHandlerContext ctx) {
		// 消息处理
		switch (msg.getRequestCode()) {
		case RequestCode.TEST:
			logger.info("Router TEST");
			HttpHandler.writeJSON(ctx, new Response(ResponseCode.SUCCESS, null));
			break;
		case RequestCode.USER:
			// 登录
			if (msg.getSubCode().equals(SubCode.Login)) {
				logger.info("Router Login");
				Response res = UserHandler.login(JSON.parseObject(msg.data, UserModel.class));
				HttpHandler.writeJSON(ctx, res);
			}
			// 注册
			else if (msg.getSubCode().equals(SubCode.Register)) {
				logger.info("Router Register");
				Response res = UserHandler.register(JSON.parseObject(msg.data, UserModel.class));
				HttpHandler.writeJSON(ctx, res);
			}
			break;
		default:
			logger.error("无协议号");
			HttpHandler.writeJSON(ctx, new Response(ResponseCode.PROTO_ERR, null));
			break;
		}
	}
}
