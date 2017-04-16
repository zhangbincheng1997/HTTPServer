package com.redhat.rpc.mapper;

import java.util.List;

import com.redhat.rpc.model.ScoreModel;

/**
 * @author littleredhat
 */
public interface ScoreMapper {

	// 获取成绩
	public List<ScoreModel> getScore(int userId);

	// 设置成绩
	public boolean setScore(ScoreModel model);

	// 更新成绩
	public boolean updateScore(ScoreModel model);
}
