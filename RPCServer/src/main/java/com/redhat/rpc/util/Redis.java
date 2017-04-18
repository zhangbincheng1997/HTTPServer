package com.redhat.rpc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author littleredhat
 * 
 *         Redis工具类
 */
public class Redis {
	private static Redis instance = null;
	private static Logger logger = LoggerFactory.getLogger(Redis.class);
	private static JedisPool pool;

	private Redis() {
	}

	public static Redis getInstance() {
		if (instance == null) {
			// 双检查锁机制
			synchronized (Redis.class) {
				if (instance == null) {
					instance = new Redis();
					instance.initData();
				}
			}
		}
		return instance;
	}

	private void initData() {
		// Redis配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Config.RedisMaxActive);
		config.setMaxIdle(Config.RedisMaxIdle);
		config.setMaxWaitMillis(Config.RedisMaxWait);
		config.setTestOnBorrow(Config.RedisTestOnBorrow);
		config.setTestOnReturn(Config.RedisTestOnReturn);
		config.setTestWhileIdle(Config.RedisTestWhileIdle);

		// 实例化Redis连接池
		pool = new JedisPool(config, Config.RedisIp, Config.RedisPort, Config.RedisTimeOut, Config.RedisPassword);
		if (pool.isClosed()) {
			logger.error("Redis Failed!!!");
		} else {
			logger.info("Redis at {}:{}", Config.RedisIp, Config.RedisPort);
		}
	}

	public void ping() {
		Jedis redis = pool.getResource();
		redis.ping();
		redis.close();
	}

	public void select(int db) {
		Jedis redis = pool.getResource();
		redis.select(db);
		redis.close();
	}

	/**
	 * exists
	 */
	public boolean exists(int db, String key) {
		Jedis redis = pool.getResource();
		redis.select(db);
		boolean ret = redis.exists(key);
		redis.close();
		return ret;
	}

	/**
	 * set
	 */
	public void set(int db, String key, String value) {
		Jedis redis = pool.getResource();
		redis.select(db);
		redis.set(key, value);
		redis.close();
	}

	/**
	 * set
	 * 
	 * @param nxxx
	 *            NX|XX, NX -- Only set the key if it does not already exist. XX
	 *            -- Only set the key if it already exist.
	 * @param expx
	 *            EX|PX, expire time units: EX = seconds; PX = milliseconds
	 * @param time
	 *            expire time in the units of <code>expx</code>
	 */
	public void set_time(int db, String key, String value, String nxxx) {
		Jedis redis = pool.getResource();
		redis.select(db);
		// EX ==> seconds
		redis.set(key, value, nxxx, "EX", 900);
		redis.close();
	}

	/**
	 * get
	 */
	public String get(int db, String key) {
		Jedis redis = pool.getResource();
		redis.select(db);
		String ret = redis.get(key);
		redis.close();
		return ret;
	}

	/**
	 * del
	 */
	public long del(int db, String key) {
		Jedis redis = pool.getResource();
		redis.select(db);
		long cnt = redis.del(key);
		redis.close();
		return cnt;
	}

	/**
	 * scard
	 */
	public long scard(int db, String key) {
		Jedis redis = pool.getResource();
		redis.select(db);
		long size = redis.scard(key);
		redis.close();
		return size;
	}

	/**
	 * lpush
	 */
	public void lpush(int db, String key, String value) {
		Jedis redis = pool.getResource();
		redis.select(db);
		redis.lpush(key, value);
		redis.close();
	}
}
