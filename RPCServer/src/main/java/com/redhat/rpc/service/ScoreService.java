package com.redhat.rpc.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.redhat.rpc.manager.ScoreManager;
import com.redhat.rpc.model.ScoreModel;
import com.redhat.rpc.model.ScoreReq;
import com.redhat.rpc.protocol.HttpMsg;
import com.redhat.rpc.protocol.ResultCode;

/**
 * @author littleredhat
 */
public class ScoreService {

	/**
	 * 获取用户成绩
	 */
	public HttpMsg getScore(HttpMsg msg) {
		// 获取用户请求
		ScoreReq req = JSON.parseObject(msg.getData(), ScoreReq.class);

		// 获取用户成绩
		List<ScoreModel> modelList = ScoreManager.getInstance().getScore(req.getUserId());

		if (modelList != null) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, JSON.toJSONString(modelList));
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.S2C_Score_No, null);
		}
	}

	/**
	 * 设置用户成绩
	 */
	public HttpMsg setScore(HttpMsg msg) {
		// 获取用户请求
		ScoreModel model = JSON.parseObject(msg.getData(), ScoreModel.class);

		// 获取用户成绩
		boolean res = ScoreManager.getInstance().setScore(model);

		if (res) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, null);
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.S2C_Score_No, null);
		}
	}

	/**
	 * 更新用户成绩
	 */
	public HttpMsg updateScore(HttpMsg msg) {
		// 获取请求内容
		ScoreModel model = JSON.parseObject(msg.getData(), ScoreModel.class);

		// 获取设置结果
		boolean res = ScoreManager.getInstance().updateScore(model);

		if (res) {
			// 返回成功响应
			return new HttpMsg(ResultCode.SUCCESS, null);
		} else {
			// 返回失败响应
			return new HttpMsg(ResultCode.COMMON_ERR, null);
		}
	}
}
