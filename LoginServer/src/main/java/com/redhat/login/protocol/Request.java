package com.redhat.login.protocol;

/**
 * @author littleredhat
 * 
 *         请求
 */
public class Request {

	// user id
	public long id;
	// user token
	public String token;
	// 请求码
	public short requestCode;
	// 子请求码
	public String subCode;
	// 数据
	public String data;

	public Request() {
	}

	public Request(long id, String token, short requestCode, String subCode, String data) {
		this.id = id;
		this.token = token;
		this.requestCode = requestCode;
		this.subCode = subCode;
		this.data = data;
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

	public short getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(short requestCode) {
		this.requestCode = requestCode;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
