package com.redhat.rpc.mapper;

import org.apache.ibatis.annotations.Param;

import com.redhat.rpc.model.InfoModel;

/**
 * @author littleredhat
 */
public interface InfoMapper {

	// 获取信息
	public InfoModel getInfo(long id);

	// 更新信息
	public boolean updateInfo(@Param("id") long id, @Param("info") InfoModel info);
}
