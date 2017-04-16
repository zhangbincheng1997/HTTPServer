package com.redhat.test;

/**
 * @author littleredhat
 */
public class MailReq {

	// ”√ªßid
	private int userId;

	public MailReq() {
	}

	public MailReq(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
