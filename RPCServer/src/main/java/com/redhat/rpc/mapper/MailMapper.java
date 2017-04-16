package com.redhat.rpc.mapper;

import java.util.List;

import com.redhat.rpc.model.MailModel;

/**
 * @author littleredhat
 */
public interface MailMapper {

	// 获取邮件
	public List<MailModel> getMail(int userId);

	// 发送邮件
	public boolean sendMail(MailModel model);
}
