package com.redhat.login.manager;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.login.mapper.ShieldMapper;
import com.redhat.login.util.SqlSessionFactoryUtil;

/**
 * @author littleredhat
 */
public class ShieldManager {
	private static Logger logger = LoggerFactory.getLogger(ShieldManager.class);
	private static ShieldManager instance;

	public static ShieldManager getInstance() {
		if (instance == null) {
			instance = new ShieldManager();
		}
		return instance;
	}

	// 是否存在屏蔽词
	public boolean hasShield(String username) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ShieldMapper shieldMapper = sqlSession.getMapper(ShieldMapper.class);
			// 执行事务
			boolean result = shieldMapper.hasShield(username);
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
		return true;
	}
}
