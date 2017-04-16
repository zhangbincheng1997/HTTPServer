package com.redhat.gate.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.redhat.gate.protocol.HttpMsg;
import com.redhat.gate.protocol.RPCCode;
import com.redhat.gate.util.Config;

/**
 * @author littleredhat
 * 
 *         RPC
 */
public class RPC {
	private static RPC instance = null;
	private static Logger logger = LoggerFactory.getLogger(RPC.class);
	// private static JsonRpcHttpClient client = null;
	// private static Map<String, String> headers = null;
	private static String _url = "http://" + Config.GateIp + ":" + Config.NodeLocalPort;
	private static URL url = null;

	public static RPC getInstance() {
		if (instance == null) {
			instance = new RPC();
			instance.initData();
		}
		return instance;
	}

	private void initData() {
		try {
			// 初始化主机地址
			url = new URL(_url);
			logger.info("RPC at {}:{}", Config.GateIp, Config.NodeLocalPort);
		} catch (MalformedURLException e) {
			logger.error("RPC Failed!!!");
			e.printStackTrace();
		}
	}

	/**
	 * RPC
	 */
	public HttpMsg handle(String type, String name, HttpMsg msg) {
		JsonRpcHttpClient client = new JsonRpcHttpClient(url);
		Map<String, String> headers = new HashMap<String, String>();
		// 添加key
		headers.put(RPCCode.RPC_TYPE, type);
		// 设置headers
		client.setHeaders(headers);
		try {
			// 参数数组
			Object[] params = new Object[] { msg };
			// 开启RPC
			return client.invoke(name, params, HttpMsg.class);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
