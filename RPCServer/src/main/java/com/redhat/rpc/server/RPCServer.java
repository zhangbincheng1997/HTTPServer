package com.redhat.rpc.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.redhat.rpc.protocol.RPCCode;
import com.redhat.rpc.service.InfoService;
import com.redhat.rpc.service.MailService;
import com.redhat.rpc.service.ScoreService;

/**
 * @author littleredhat
 * 
 *         RPC
 */
public class RPCServer {
	private static RPCServer instance = null;
	private static Map<String, JsonRpcServer> rpcServer = null;

	public static RPCServer getInstance() {
		if (instance == null) {
			instance = new RPCServer();
			instance.initData();
		}
		return instance;
	}

	private void initData() {
		rpcServer = new HashMap<String, JsonRpcServer>();
		// info
		JsonRpcServer infoServer = new JsonRpcServer(new InfoService(), InfoService.class);
		rpcServer.put(RPCCode.TYPE_INFO, infoServer);
		// score
		JsonRpcServer scoreServer = new JsonRpcServer(new ScoreService(), ScoreService.class);
		rpcServer.put(RPCCode.TYPE_SCORE, scoreServer);
		// mail
		JsonRpcServer mailServer = new JsonRpcServer(new MailService(), MailService.class);
		rpcServer.put(RPCCode.TYPE_MAIL, mailServer);
	}

	/**
	 * RPC处理
	 */
	public void handle(String type, InputStream request, OutputStream response) throws IOException {
		// 根据类型获取RPC服务
		JsonRpcServer server = rpcServer.get(type);
		// 开启RPC服务
		server.handle(request, response);
	}
}
