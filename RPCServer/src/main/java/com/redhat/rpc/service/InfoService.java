package com.redhat.rpc.service;

import com.alibaba.fastjson.JSON;
import com.redhat.rpc.manager.InfoManager;
import com.redhat.rpc.model.InfoModel;
import com.redhat.rpc.model.InfoReq;
import com.redhat.rpc.protocol.HttpMsg;
import com.redhat.rpc.protocol.ResultCode;

/**
 * @author littleredhat
 */
public class InfoService {

	/**
	 * 获取用户信息
	 */
	public HttpMsg getInfo(HttpMsg msg) {
		// 获取请求内容
		InfoReq req = JSON.parseObject(msg.getData(), InfoReq.class);

		// 获取用户信息
		InfoModel model = InfoManager.getInstance().getInfo(req.getUserId());

		if (model != null) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, JSON.toJSONString(model));
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.S2C_Info_No, null);
		}
	}

	/**
	 * 设置用户信息
	 */
	public HttpMsg updateInfo(HttpMsg msg) {
		// 获取请求内容
		InfoModel model = JSON.parseObject(msg.getData(), InfoModel.class);

		// 获取设置结果
		boolean res = InfoManager.getInstance().setInfo(model);

		if (res) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, null);
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.S2C_Info_No, null);
		}
	}
}
