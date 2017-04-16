package com.redhat.login.model;

/**
 * @author littleredhat
 */
public class UserReq {

	private String username; // ÓÃ»§Ãû
	private String password; // ÃÜÂë

	public UserReq() {
	}

	public UserReq(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
