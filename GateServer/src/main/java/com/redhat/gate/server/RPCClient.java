package com.redhat.gate.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.redhat.gate.protocol.Request;
import com.redhat.gate.protocol.RequestCode;
import com.redhat.gate.protocol.Response;
import com.redhat.gate.protocol.ResponseCode;
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
	 * RPCClient处理
	 */
	public void handle(Request req, ChannelHandlerContext ctx) {
		// 消息处理
		switch (req.getRequestCode()) {
		case RequestCode.TEST:
			logger.info("RPCClient TEST");
			HttpHandler.writeJSON(ctx, new Response(ResponseCode.SUCCESS, null));
			break;
		case RequestCode.INFO:
			logger.info("RPCClient INFO");
			Response res1 = rpc(req.getRequestCode(), req.getSubCode(), req);
			HttpHandler.writeJSON(ctx, res1);
			break;
		case RequestCode.SCORE:
			logger.info("RPCClient SCORE");
			Response res2 = rpc(req.getRequestCode(), req.getSubCode(), req);
			HttpHandler.writeJSON(ctx, res2);
			break;
		default:
			logger.error("无协议号");
			HttpHandler.writeJSON(ctx, new Response(ResponseCode.PROTO_ERR, null));
			break;
		}
	}

	/**
	 * RPC
	 */
	public Response rpc(int type, String methodName, Request msg) {
		JsonRpcHttpClient client = null;
		try {
			client = new JsonRpcHttpClient(new URL(_url));

			Map<String, String> headers = new HashMap<String, String>();
			// 添加key
			headers.put("TYPE", Integer.toString(type));
			// 设置headers
			client.setHeaders(headers);

			// 参数数组
			Object[] params = new Object[] { msg };
			// 开启RPC
			return client.invoke(methodName, params, Response.class);
		} catch (MalformedURLException e1) {
			logger.error("MalformedURLException错误!!!" + e1);
			e1.printStackTrace();
		} catch (Throwable e2) {
			logger.error("Throwable错误!!!" + e2);
			e2.printStackTrace();
		}
		return null;
	}
}
