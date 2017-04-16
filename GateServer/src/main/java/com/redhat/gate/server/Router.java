package com.redhat.gate.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.redhat.gate.protocol.HttpMsg;
import com.redhat.gate.protocol.RPCCode;
import com.redhat.gate.protocol.RequestCode;
import com.redhat.gate.protocol.ResultCode;

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
			instance = new Router();
		}
		return instance;
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
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.PROTO_ERR, null));
			return;
		}
		// 消息处理
		switch (httpMsg.getCode()) {
		case RequestCode.TEST:
			logger.info("Router Test");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.SUCCESS, null));
			break;
		case RequestCode.C2S_Get_Info:
			logger.info("Router C2S_Get_Info");
			HttpMsg res1 = RPC.getInstance().handle(RPCCode.TYPE_INFO, RPCCode.Get_Info, httpMsg);
			HttpHandler.writeJSON(ctx, res1);
			break;
		case RequestCode.C2S_Update_Info:
			logger.info("Router Update_Info");
			HttpMsg res2 = RPC.getInstance().handle(RPCCode.TYPE_INFO, RPCCode.Update_Info, httpMsg);
			HttpHandler.writeJSON(ctx, res2);
			break;
		case RequestCode.C2S_Get_Score:
			logger.info("Router C2S_Get_Score");
			HttpMsg res3 = RPC.getInstance().handle(RPCCode.TYPE_SCORE, RPCCode.Get_Score, httpMsg);
			HttpHandler.writeJSON(ctx, res3);
			break;
		case RequestCode.C2S_Set_Score:
			logger.info("Router C2S_Set_Score");
			HttpMsg res4 = RPC.getInstance().handle(RPCCode.TYPE_SCORE, RPCCode.Set_Score, httpMsg);
			HttpHandler.writeJSON(ctx, res4);
			break;
		case RequestCode.C2S_Update_Score:
			logger.info("Router C2S_Update_Score");
			HttpMsg res5 = RPC.getInstance().handle(RPCCode.TYPE_SCORE, RPCCode.Update_Score, httpMsg);
			HttpHandler.writeJSON(ctx, res5);
			break;
		case RequestCode.C2S_Get_Mail:
			logger.info("Router C2S_Get_Mail");
			HttpMsg res6 = RPC.getInstance().handle(RPCCode.TYPE_MAIL, RPCCode.Get_Mail, httpMsg);
			HttpHandler.writeJSON(ctx, res6);
			break;
		case RequestCode.C2S_Send_Mail:
			logger.info("Router C2S_Send_Mail");
			HttpMsg res7 = RPC.getInstance().handle(RPCCode.TYPE_MAIL, RPCCode.Send_Mail, httpMsg);
			HttpHandler.writeJSON(ctx, res7);
			break;
		default:
			logger.error("无协议号");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.PROTO_ERR, null));
			break;
		}
	}
}
