package com.redhat.rpc.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.redhat.rpc.manager.MailManager;
import com.redhat.rpc.model.MailModel;
import com.redhat.rpc.model.MailReq;
import com.redhat.rpc.protocol.HttpMsg;
import com.redhat.rpc.protocol.ResultCode;

/**
 * @author littleredhat
 */
public class MailService {

	/**
	 * 获取邮件
	 */
	public HttpMsg getMail(HttpMsg msg) {
		// 获取用户请求
		MailReq req = JSON.parseObject(msg.getData(), MailReq.class);

		// 获取邮件
		List<MailModel> modelList = MailManager.getInstance().getMail(req.getUserId());

		if (modelList != null) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, JSON.toJSONString(modelList));
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.S2C_Mail_No, null);
		}
	}

	/**
	 * 发送邮件
	 */
	public HttpMsg sendMail(HttpMsg msg) {
		// 获取请求内容
		MailModel model = JSON.parseObject(msg.getData(), MailModel.class);

		// 获取设置结果
		boolean res = MailManager.getInstance().sendMail(model);

		if (res) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, null);
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.S2C_Mail_No, null);
		}
	}
}
