package com.redhat.gate.util;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author littleredhat
 * 
 *         Mybatis工具类
 */
public class SqlSessionFactoryUtil {
	// SqlSessionFactory
	private static SqlSessionFactory sqlSessionFactory = null;
	// 类线程锁
	private static final Class<SqlSessionFactoryUtil> CLASS_LOCK = SqlSessionFactoryUtil.class;

	private SqlSessionFactoryUtil() {
	}

	private static SqlSessionFactory initSqlSessionFactory() {
		InputStream in = SqlSessionFactoryUtil.class.getResourceAsStream("/mybatis-config.xml");

		synchronized (CLASS_LOCK) {
			if (sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
			}
		}
		return sqlSessionFactory;
	}

	public static SqlSession openSqlSession() {
		if (sqlSessionFactory == null) {
			initSqlSessionFactory();
		}
		return sqlSessionFactory.openSession();
	}
}
