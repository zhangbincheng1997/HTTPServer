package com.redhat.login.model;

/**
 * @author littleredhat
 */
public class UserModel {

	private int id; // 用户id
	private String session; // 用户session
	private int gatePort; // gate port
	private String gateIp; // gate ip
	
	public UserModel() {
	}
	
	public UserModel(int id, String session, int gatePort, String gateIp) {
		this.id = id;
		this.session = session;
		this.gatePort = gatePort;
		this.gateIp = gateIp;
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

	public int getGatePort() {
		return gatePort;
	}

	public void setGatePort(int gatePort) {
		this.gatePort = gatePort;
	}

	public String getGateIp() {
		return gateIp;
	}

	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}
}
