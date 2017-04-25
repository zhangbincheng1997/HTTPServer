package com.redhat.login.handler;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.redhat.login.manager.ShieldManager;
import com.redhat.login.manager.UserManager;
import com.redhat.login.model.LoginModel;
import com.redhat.login.model.UserModel;
import com.redhat.login.protocol.Response;
import com.redhat.login.protocol.ResponseCode;
import com.redhat.login.util.Authentication;
import com.redhat.login.util.Config;

/**
 * @author littleredhat
 */
public class UserHandler {

	public static Response login(UserModel model) {
		// 获取数据
		String username = model.getUsername();
		String password = model.getPassword();

		// id
		long id = UserManager.getInstance().getId(username);
		if (id == -1) {
			System.out.println("用户名不存在!!!");
			return new Response(ResponseCode.Login_Username_Err, null);
		}

		// enable
		boolean enable = UserManager.getInstance().getEnable(id);
		if (!enable) {
			System.out.println("用户进入黑名单!!!");
			return new Response(ResponseCode.Login_Black_List, null);
		}

		// password
		String password_server = UserManager.getInstance().getPassword(id);
		if (!password.equals(password_server)) {
			System.out.println("用户密码错误!!! " + password + " != " + password_server);
			return new Response(ResponseCode.Login_Password_Err, null);
		}

		Date date = new Date();
		// online
		boolean online = UserManager.getInstance().updateOnline(id, date);
		if (!online) {
			System.out.println("更新失败!!!");
			return new Response(ResponseCode.COMMON_ERR, null);
		}

		System.out.println("登录成功!!!" + date);
		// 生成token
		String token = Authentication.getToken(id);
		// 存储redis
		Authentication.setToken(id, token);
		// 构造实例
		LoginModel res = new LoginModel(id, token, Config.GateLocalPort, Config.GateIp);

		return new Response(ResponseCode.SUCCESS, JSON.toJSONString(res));
	}

	public static Response register(UserModel model) {
		// 获取数据
		String username = model.getUsername();
		String password = model.getPassword();

		// id
		long id = UserManager.getInstance().getId(username);
		if (id != -1) {
			System.out.println("用户名已存在!!!");
			return new Response(ResponseCode.Register_Username_Err, null);
		}

		// ill
		boolean ill = ShieldManager.getInstance().hasShield(username);
		if (ill) {
			System.out.println("用户名非法!!!");
			return new Response(ResponseCode.Register_Username_Ill, null);
		}

		// user
		boolean result = UserManager.getInstance().addUser(username, password);
		if (result) {
			System.out.println("注册成功!!!");
			return new Response(ResponseCode.SUCCESS, null);
		} else {
			System.out.println("注册失败!!!");
			return new Response(ResponseCode.COMMON_ERR, null);
		}
	}
}
