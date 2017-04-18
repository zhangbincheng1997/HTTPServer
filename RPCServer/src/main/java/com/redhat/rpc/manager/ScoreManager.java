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

	// 获取成绩
	public List<ScoreModel> getScore(int userId) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			List<ScoreModel> modelList = scoreMapper.getScore(userId);
			sqlSession.commit();
			// 返回结果
			return modelList;
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

	// 设置成绩
	public boolean setScore(ScoreModel model) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			boolean result = scoreMapper.setScore(model);
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

	// 更新成绩
	public boolean updateScore(ScoreModel model) {
		SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
		try {
			// 获取映射
			ScoreMapper scoreMapper = sqlSession.getMapper(ScoreMapper.class);
			// 执行事务
			boolean result = scoreMapper.updateScore(model);
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
