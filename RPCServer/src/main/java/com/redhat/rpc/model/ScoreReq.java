package com.redhat.rpc.model;

/**
 * @author littleredhat
 */
public class ScoreReq {

	// ”√ªßid
	private int userId;

	public ScoreReq() {
	}

	public ScoreReq(int userId, String subject) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
