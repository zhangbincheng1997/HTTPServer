package com.redhat.rpc.util;

import java.util.Date;

import com.redhat.rpc.protocol.Consts;
import com.redhat.rpc.util.Coder;
import com.redhat.rpc.util.Redis;

/**
 * @author littleredhat
 * 
 *         Authnentication工具类
 */
public class Authentication {
	private static Authentication instance = null;

	public static Authentication getInstancce() {
		if (instance == null) {
			instance = new Authentication();
		}
		return instance;
	}

	/**
	 * session = md5(key + _ + id + _ + time)
	 */
	public static String getSession(int id) {
		StringBuilder session = new StringBuilder(Consts.SESSION_KEY);
		session.append("_");
		session.append(id);
		session.append("_");
		session.append(new Date().getTime() / 1000);
		return Coder.encodeToMD5(session.toString());
	}

	public void setSession(int id, String session) {
		// keyX
		String key = Consts.SESSION_KEY + id;
		// 不存在才设置
		// 生存时间为900s
		Redis.getInstance().set_time(0, session, key, "NX");
	}

	/**
	 * 判断id与session对应id是否一致
	 */
	public boolean checkSession(int id, String session) {
		int id_server = getId(session);
		if (id == id_server) {
			// keyX
			String key = Consts.SESSION_KEY + id;
			// 存在才设置
			// 刷新session生存时间
			Redis.getInstance().set_time(0, session, key, "XX");
			return true;
		}
		return false;
	}

	// 根据session获取id
	private int getId(String session) {
		boolean res = Redis.getInstance().exists(0, session);
		if (res) {
			String key = Redis.getInstance().get(0, session);
			// 存入redis的id格式为keyX
			// 所以要从key_后面开始截取X
			return Integer.parseInt(key.substring(Consts.SESSION_KEY.length()));
		}
		return -1;
	}
}
