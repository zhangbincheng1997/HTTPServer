package com.redhat.rpc.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.redhat.rpc.manager.ScoreManager;
import com.redhat.rpc.model.ScoreModel;
import com.redhat.rpc.protocol.Request;
import com.redhat.rpc.protocol.Response;
import com.redhat.rpc.protocol.ResponseCode;

/**
 * @author littleredhat
 */
public class ScoreService {

	/**
	 * 获取成绩
	 */
	public Response getScore(Request msg) {
		// 获取结果
		List<ScoreModel> scoreList = ScoreManager.getInstance().getScore(msg.getId());

		if (scoreList != null) {
			// 返回成功响应
			return new Response(ResponseCode.SUCCESS, JSON.toJSONString(scoreList));
		} else {
			// 返回失败响应
			return new Response(ResponseCode.COMMON_ERR, null);
		}
	}

	/**
	 * 更新成绩
	 */
	public Response updateScore(Request msg) {
		// 获取内容
		long id = msg.getId();
		ScoreModel model = JSON.parseObject(msg.getData(), ScoreModel.class);

		boolean hasExist = ScoreManager.getInstance().hasScore(id, model.subject);
		boolean res = false;
		if (hasExist) {
			// 已经存在则更新
			res = ScoreManager.getInstance().updateScore(id, model);
		} else {
			// 不存在则设置
			res = ScoreManager.getInstance().setScore(id, model);
		}

		if (res) {
			// 返回成功响应
			return new Response(ResponseCode.SUCCESS, JSON.toJSONString(model));
		} else {
			// 返回失败响应
			return new Response(ResponseCode.COMMON_ERR, null);
		}
	}
}
