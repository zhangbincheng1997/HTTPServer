package com.redhat.rpc.model;

/**
 * @author littleredhat
 */
public class InfoModel {

	// 用户id
	private int userId;

	// 等级
	private int level;

	// 信息
	private String info;

	public InfoModel() {
	}

	public InfoModel(int userId, int level, String info) {
		this.userId = userId;
		this.level = level;
		this.info = info;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
