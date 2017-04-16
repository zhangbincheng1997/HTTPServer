package com.redhat.test;

/**
 * @author littleredhat
 */
public class ScoreModel {

	// 用户id
	private int userId;

	// 科目
	private String subject;

	// 成绩
	private int score;

	public ScoreModel() {
	}

	public ScoreModel(int userId, String subject, int score) {
		this.userId = userId;
		this.subject = subject;
		this.score = score;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userid) {
		this.userId = userid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
