package com.redhat.login.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

/**
 * @author littleredhat
 */
public interface UserMapper {

	// 获取用户id
	public long getId(String username);

	// 获取用户密码
	public String getPassword(long id);

	// 添加用户
	public boolean addUser(@Param("username") String username, @Param("password") String password);

	// 更新用户登录时间
	public boolean updateOnline(@Param("id") long id, @Param("date") Date date);

	// 获取用户登录时间
	public Date getOnline(long id);

	// 更新用户登录时间
	public boolean updateEnable(@Param("id") long id, @Param("enable") boolean enable);

	// 获取是否允许登录
	public boolean getEnable(long id);
}
