package com.redhat.login.model;

/**
 * @author littleredhat
 */
public class LoginModel {

	private long id; // 用户id
	private String token; // 用户token
	private String gateIp; // gate ip
	private int gatePort; // gate port

	public LoginModel() {
	}

	public LoginModel(long id, String token, int gatePort, String gateIp) {
		this.id = id;
		this.token = token;
		this.gateIp = gateIp;
		this.gatePort = gatePort;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getGateIp() {
		return gateIp;
	}

	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}

	public int getGatePort() {
		return gatePort;
	}

	public void setGatePort(int gatePort) {
		this.gatePort = gatePort;
	}
}
