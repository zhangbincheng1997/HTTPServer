package com.redhat.rpc.service;

import com.alibaba.fastjson.JSON;
import com.redhat.rpc.manager.InfoManager;
import com.redhat.rpc.model.InfoModel;
import com.redhat.rpc.protocol.Request;
import com.redhat.rpc.protocol.Response;
import com.redhat.rpc.protocol.ResponseCode;

/**
 * @author littleredhat
 */
public class InfoService {

	/**
	 * 获取信息
	 */
	public Response getInfo(Request msg) {
		// 获取结果
		InfoModel model = InfoManager.getInstance().getInfo(msg.getId());

		if (model != null) {
			// 返回成功响应
			return new Response(ResponseCode.SUCCESS, JSON.toJSONString(model));
		} else {
			// 返回失败响应
			return new Response(ResponseCode.COMMON_ERR, null);
		}
	}

	/**
	 * 更新信息
	 */
	public Response updateInfo(Request msg) {
		// 获取内容
		long id = msg.getId();
		InfoModel model = JSON.parseObject(msg.getData(), InfoModel.class);

		// 更新结果
		boolean res = InfoManager.getInstance().updateInfo(id, model);

		if (res) {
			// 返回成功响应
			return new Response(ResponseCode.SUCCESS, JSON.toJSONString(model));
		} else {
			// 返回失败响应
			return new Response(ResponseCode.COMMON_ERR, null);
		}
	}
}
