package com.redhat.rpc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redhat.rpc.model.ScoreModel;

/**
 * @author littleredhat
 */
public interface ScoreMapper {

	// 是否存在成绩
	public boolean hasScore(@Param("userId") long userId, @Param("subject") int subject);

	// 获取成绩
	public List<ScoreModel> getScore(long userId);

	// 设置成绩
	public boolean setScore(@Param("userId") long userId, @Param("score") ScoreModel score);

	// 更新成绩
	public boolean updateScore(@Param("userId") long userId, @Param("score") ScoreModel score);
}
