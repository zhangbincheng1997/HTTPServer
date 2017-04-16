package com.redhat.rpc.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rpc.mapper.MailMapper;
import com.redhat.rpc.model.MailModel;
import com.redhat.rpc.util.SqlSessionFactoryUtil;

/**
 * @author littleredhat
 */
public class MailManager {
	private static Logger logger = LoggerFactory.getLogger(MailManager.class);
	private static MailManager instance;

	public static MailManager getInstance() {
		if (instance == null) {
			instance = new MailManager();
		}
		return instance;
	}

	// 获取邮件
	public List<MailModel> getMail(int userId) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			MailMapper mailMapper = sqlSession.getMapper(MailMapper.class);
			// 执行事务
			List<MailModel> model = mailMapper.getMail(userId);
			sqlSession.commit();
			// 返回结果
			return model;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}

	// 发送邮件
	public boolean sendMail(MailModel model) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			MailMapper mailMapper = sqlSession.getMapper(MailMapper.class);
			// 执行事务
			boolean result = mailMapper.sendMail(model);
			sqlSession.commit();
			// 返回结果
			return result;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			sqlSession.rollback();
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return false;
	}
}
