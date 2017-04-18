package com.redhat.login.model;

/**
 * @author littleredhat
 */
public class UserModel {

	private int id; // 用户id
	private String session; // 用户session
	private String gateIp; // gate ip
	private int gatePort; // gate port

	public UserModel() {
	}

	public UserModel(int id, String session, int gatePort, String gateIp) {
		this.id = id;
		this.session = session;
		this.gateIp = gateIp;
		this.gatePort = gatePort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
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
