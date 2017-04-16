package com.redhat.rpc.mapper;

import com.redhat.rpc.model.InfoModel;

/**
 * @author littleredhat
 */
public interface InfoMapper {

	// 获取用户信息
	public InfoModel getInfo(int userId);

	// 更新用户信息
	public boolean setInfo(InfoModel model);
}
