package com.redhat.rpc.manager;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rpc.mapper.ScoreMapper;
import com.redhat.rpc.model.ScoreModel;
import com.redhat.rpc.util.SqlSessionFactoryUtil;

/**
 * @author littleredhat
 */
public class ScoreManager {
	private static Logger logger = LoggerFactory.getLogger(ScoreManager.class);
	private static ScoreManager instance;

	public static ScoreManager getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (ScoreManager.class) {
				if (instance == null) {
					instance = new ScoreManager();
				}
			}
		}
		return instance;
	}

	public boolean hasScore(long userId, int subject) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			boolean result = scoreMapper.hasScore(userId, subject);
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

	// 获取信息
	public List<ScoreModel> getScore(long userId) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			List<ScoreModel> scoreList = scoreMapper.getScore(userId);
			sqlSession.commit();
			// 返回结果
			return scoreList;
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

	// 设置信息
	public boolean setScore(long userId, ScoreModel score) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			boolean result = scoreMapper.setScore(userId, score);
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

	// 更新信息
	public boolean updateScore(long userId, ScoreModel score) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			boolean result = scoreMapper.updateScore(userId, score);
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
