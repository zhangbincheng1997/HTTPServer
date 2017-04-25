package com.redhat.login.model;

/**
 * @author littleredhat
 */
public class UserModel {

	private String username; // ÓÃ»§Ãû
	private String password; // ÃÜÂë

	public UserModel() {
	}

	public UserModel(String username, String password) {
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
