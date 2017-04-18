package com.redhat.gate.server;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.redhat.gate.protocol.HttpMsg;
import com.redhat.gate.protocol.RPCCode;
import com.redhat.gate.protocol.RequestCode;
import com.redhat.gate.protocol.ResultCode;
import com.redhat.gate.util.Config;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author littleredhat
 * 
 *         RPC
 */
public class RPCClient {
	private static RPCClient instance = null;
	private static Logger logger = LoggerFactory.getLogger(RPCClient.class);
	private static String _url = "http://" + Config.GateIp + ":" + Config.NodeLocalPort;

	public static RPCClient getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (RPCClient.class) {
				if (instance == null) {
					instance = new RPCClient();
				}
			}
		}
		return instance;
	}

	/**
	 * Handle处理
	 */
	public void handle(String msg, ChannelHandlerContext ctx) throws Exception {
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
			logger.info("RPCClient Test");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.SUCCESS, null));
			break;
		case RequestCode.C2S_Get_Info:
			logger.info("RPCClient C2S_Get_Info");
			HttpMsg res1 = rpc(RPCCode.TYPE_INFO, RPCCode.Get_Info, httpMsg);
			HttpHandler.writeJSON(ctx, res1);
			break;
		case RequestCode.C2S_Update_Info:
			logger.info("RPCClient Update_Info");
			HttpMsg res2 = rpc(RPCCode.TYPE_INFO, RPCCode.Update_Info, httpMsg);
			HttpHandler.writeJSON(ctx, res2);
			break;
		case RequestCode.C2S_Get_Score:
			logger.info("RPCClient C2S_Get_Score");
			HttpMsg res3 = rpc(RPCCode.TYPE_SCORE, RPCCode.Get_Score, httpMsg);
			HttpHandler.writeJSON(ctx, res3);
			break;
		case RequestCode.C2S_Set_Score:
			logger.info("RPCClient C2S_Set_Score");
			HttpMsg res4 = rpc(RPCCode.TYPE_SCORE, RPCCode.Set_Score, httpMsg);
			HttpHandler.writeJSON(ctx, res4);
			break;
		case RequestCode.C2S_Update_Score:
			logger.info("RPCClient C2S_Update_Score");
			HttpMsg res5 = rpc(RPCCode.TYPE_SCORE, RPCCode.Update_Score, httpMsg);
			HttpHandler.writeJSON(ctx, res5);
			break;
		case RequestCode.C2S_Get_Mail:
			logger.info("RPCClient C2S_Get_Mail");
			HttpMsg res6 = rpc(RPCCode.TYPE_MAIL, RPCCode.Get_Mail, httpMsg);
			HttpHandler.writeJSON(ctx, res6);
			break;
		case RequestCode.C2S_Send_Mail:
			logger.info("RPCClient C2S_Send_Mail");
			HttpMsg res7 = rpc(RPCCode.TYPE_MAIL, RPCCode.Send_Mail, httpMsg);
			HttpHandler.writeJSON(ctx, res7);
			break;
		default:
			logger.error("无协议号");
			HttpHandler.writeJSON(ctx, new HttpMsg(ResultCode.PROTO_ERR, null));
			break;
		}
	}

	/**
	 * RPC
	 */
	public HttpMsg rpc(String type, String methodName, HttpMsg msg) throws Exception {
		JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(_url));
		Map<String, String> headers = new HashMap<String, String>();
		// 添加key
		headers.put(RPCCode.RPC_TYPE, type);
		// 设置headers
		client.setHeaders(headers);
		try {
			// 参数数组
			Object[] params = new Object[] { msg };
			// 开启RPC
			return client.invoke(methodName, params, HttpMsg.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
