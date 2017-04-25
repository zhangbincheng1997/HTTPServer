package com.redhat.login.manager;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.login.mapper.UserMapper;
import com.redhat.login.util.SqlSessionFactoryUtil;

/**
 * @author littleredhat
 */
public class UserManager {
	private static Logger logger = LoggerFactory.getLogger(UserManager.class);
	private static UserManager instance;

	public static UserManager getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (UserManager.class) {
				if (instance == null) {
					instance = new UserManager();
				}
			}
		}
		return instance;
	}

	// 获取用户Id
	public long getId(String username) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			long result = userMapper.getId(username);
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
		return -1;
	}

	// 获取用户密码
	public String getPassword(long id) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			String result = userMapper.getPassword(id);
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
		return null;
	}

	// 添加用户
	public boolean addUser(String username, String password) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			boolean result = userMapper.addUser(username, password);
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

	// 更新最后登陆时间
	public boolean updateOnline(long id, Date date) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			boolean result = userMapper.updateOnline(id, date);
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

	// 获取最后登陆时间
	public Date getOnline(long id) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			Date result = userMapper.getOnline(id);
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
		return null;
	}

	// 更新是否允许登录
	public boolean updateEnable(long id, boolean enable) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			boolean result = userMapper.updateEnable(id, enable);
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

	// 获取是否允许登录
	public boolean getEnable(long id) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			// 执行事务
			boolean result = userMapper.getEnable(id);
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
