package com.redhat.test;

import java.util.Date;

/**
 * @author littleredhat
 */
public class MailModel {

	// 邮件id
	private int mailId;

	// 邮件类型
	private String type;

	// 发件人昵称
	private String sendName;

	// 收件人id
	private int receiveId;

	// 邮件内容
	private String content;

	// 发送日期
	private Date date;

	public MailModel() {
	}

	public MailModel(int mailId, String type, String sendName, int receiveId, String content, Date date) {
		this.mailId = mailId;
		this.type = type;
		this.sendName = sendName;
		this.receiveId = receiveId;
		this.content = content;
		this.date = date;
	}

	public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public int getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
