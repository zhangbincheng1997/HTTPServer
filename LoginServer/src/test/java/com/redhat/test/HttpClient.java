package com.redhat.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redhat.login.model.LoginModel;
import com.redhat.login.model.UserModel;
import com.redhat.login.protocol.Request;
import com.redhat.login.protocol.RequestCode;
import com.redhat.login.protocol.Response;
import com.redhat.login.protocol.ResponseCode;
import com.redhat.login.protocol.SubCode;
import com.redhat.login.util.Coder;

/**
 * @author littleredhat
 */
public class HttpClient {
	private static long id = 0;
	private static String token = "";
	private static String ip = "127.0.0.1";
	private static int port = 9901;

	private static URL url;
	private static HttpURLConnection http;

	// 是否登录成功
	private static boolean isOk = false;

	public static void main(String[] args) throws Exception {
		// 获取键盘输入
		Scanner sc = new Scanner(System.in);

		System.out.println("====================");
		System.out.println("login		register");
		System.out.println("list		quit");
		System.out.println("====================");

		while (!isOk) {
			String option = sc.nextLine();
			if (option.equals("login")) {
				System.out.print("Enter Username : ");
				String username = sc.nextLine();
				System.out.print("Enter Password : ");
				String password = sc.nextLine();
				// login
				UserModel user = new UserModel(username, Coder.encodeToMD5(password));
				// msg
				Request msg = new Request(id, token, RequestCode.USER, SubCode.Login, JSON.toJSONString(user));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 用户登录
				String res = task(data);
				login(res);
			} else if (option.equals("register")) {
				System.out.print("Enter Username : ");
				String username = sc.nextLine();
				System.out.print("Enter Password : ");
				String password = sc.nextLine();
				// register
				UserModel user = new UserModel(username, Coder.encodeToMD5(password));
				Request msg = new Request(id, token, RequestCode.USER, SubCode.Register, JSON.toJSONString(user));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 用户注册
				String res = task(data);
				register(res);
			} else if (option.equals("list")) {
				System.out.println("====================");
				System.out.println("login		register");
				System.out.println("list		quit");
				System.out.println("====================");
			} else if (option.equals("quit")) {
				System.exit(0);
			}
		}

		System.out.println("========================================");
		System.out.println("getInfo				updateInfo");
		System.out.println("getScore			updateScore");
		System.out.println("list				quit");
		System.out.println("========================================");

		while (isOk) {
			String option = sc.nextLine();
			if (option.equals("getInfo")) {
				// getInfo
				Request msg = new Request(id, token, RequestCode.INFO, SubCode.GetInfo, null);
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 获取信息
				String res = task(data);
				getInfo(res);
			} else if (option.equals("updateInfo")) {
				System.out.println("Enter Name : ");
				String name = sc.nextLine();
				System.out.println("Enter Age : ");
				String _age = sc.nextLine();
				int age = Integer.parseInt(_age);
				System.out.println("Enter Teacher : ");
				String _teacher = sc.nextLine();
				int teacher = Integer.parseInt(_teacher);
				System.out.println("Enter Sex : ");
				String _sex = sc.nextLine();
				boolean sex = Boolean.parseBoolean(_sex);
				InfoModel info = new InfoModel(name, age, teacher, sex);
				// updateInfo
				Request msg = new Request(id, token, RequestCode.INFO, SubCode.UpdateInfo, JSON.toJSONString(info));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 更新信息
				String res = task(data);
				updateInfo(res);
			} else if (option.equals("getScore")) {
				// getScore
				Request msg = new Request(id, token, RequestCode.SCORE, SubCode.GetScore, null);
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 获取成绩
				String res = task(data);
				getScore(res);
			} else if (option.equals("updateScore")) {
				System.out.println("Enter Subject : ");
				String _subject = sc.nextLine();
				int subject = Integer.parseInt(_subject);
				System.out.println("Enter Score : ");
				String _score = sc.nextLine();
				int score = Integer.parseInt(_score);
				ScoreModel info = new ScoreModel(subject, score, new Date());
				// updateScore
				Request msg = new Request(id, token, RequestCode.SCORE, SubCode.UpdateScore, JSON.toJSONString(info));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 更新成绩
				String res = task(data);
				updateScore(res);
			} else if (option.equals("list")) {
				System.out.println("========================================");
				System.out.println("getInfo				updateInfo");
				System.out.println("getScore			updateScore");
				System.out.println("list				quit");
				System.out.println("========================================");
			} else if (option.equals("quit")) {
				System.exit(0);
			}
		}
	}

	// 发送请求
	private static String task(final String params) throws Exception {
		// 更新url
		url = new URL("http://" + ip + ":" + port);
		// openConnection
		http = (HttpURLConnection) url.openConnection();
		http.setDoOutput(true);

		// getOutputStream
		OutputStreamWriter out = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
		if (params != null) {
			out.write(params);
		}
		out.flush();
		out.close();

		// getInputStream
		InputStreamReader in = new InputStreamReader(http.getInputStream(), "UTF-8");
		BufferedReader reader = new BufferedReader(in);
		StringBuffer res = new StringBuffer();
		String line = null;
		while ((line = reader.readLine()) != null) {
			res.append(line);
		}
		in.close();

		// disconnect
		if (http != null) {
			http.disconnect();// 关闭连接
		}

		return new String(Coder.decodeFromBase64(res.toString()));
	}

	//////////

	private static void login(String msg) {
		System.out.println("login ==> " + msg);
		Response res = JSON.parseObject(msg, Response.class);

		if (res.getResponseCode() == ResponseCode.SUCCESS) {
			LoginModel model = JSON.parseObject(res.getData(), LoginModel.class);
			id = model.getId();
			token = model.getToken();
			port = model.getGatePort();
			ip = model.getGateIp();

			System.out.println("id == " + id);
			System.out.println("session == " + token);
			System.out.println("gateIp == " + ip);
			System.out.println("gatePort == " + port);

			// 登录成功
			isOk = true;
		} else {
			System.out.println("登录失败 : " + res.getResponseCode());
		}
	}

	private static void register(String msg) {
		System.out.println("register ==> " + msg);
		Response res = JSON.parseObject(msg, Response.class);

		if (res.getResponseCode() == ResponseCode.SUCCESS) {
			System.out.println("注册成功 : " + res.getResponseCode());
		} else {
			System.out.println("注册失败 : " + res.getResponseCode());
		}
	}

	//////////

	private static void getInfo(String msg) {
		System.out.println("getInfo ==> " + msg);
		Response res = JSON.parseObject(msg, Response.class);

		if (res.getResponseCode() == ResponseCode.SUCCESS) {
			InfoModel model = JSON.parseObject(res.getData(), InfoModel.class);
			String name = model.getName();
			int age = model.getAge();
			int teacher = model.getTeacher();
			boolean sex = model.isSex();
			System.out.println("get name == " + name);
			System.out.println("get age == " + age);
			System.out.println("get teacher == " + teacher);
			System.out.println("get sex == " + sex);
		} else {
			System.out.println("resultCode == " + res.getResponseCode());
		}
	}

	private static void updateInfo(String msg) {
		System.out.println("updateInfo ==> " + msg);
		Response res = JSON.parseObject(msg, Response.class);

		if (res.getResponseCode() == ResponseCode.SUCCESS) {
			System.out.println("更新成功 : " + res.getResponseCode());
		} else {
			System.out.println("更新失败 : " + res.getResponseCode());
		}
	}

	//////////

	private static void getScore(String msg) {
		System.out.println("getScore ==> " + msg);
		Response res = JSON.parseObject(msg, Response.class);

		if (res.getResponseCode() == ResponseCode.SUCCESS) {
			// 解析成绩列表
			List<ScoreModel> modelList = JSONObject.parseArray(res.getData(), ScoreModel.class);
			// 遍历成绩列表
			for (ScoreModel model : modelList) {
				int subject = model.getSubject();
				int score = model.getScore();
				Date date = model.getDate();
				System.out.println("get subject == " + subject);
				System.out.println("get score == " + score);
				System.out.println("get date == " + date);
			}
		} else {
			System.out.println("resultCode == " + res.getResponseCode());
		}
	}

	private static void updateScore(String msg) {
		System.out.println("updateScore ==> " + msg);
		Response res = JSON.parseObject(msg, Response.class);

		if (res.getResponseCode() == ResponseCode.SUCCESS) {
			System.out.println("更新成功 : " + res.getResponseCode());
		} else {
			System.out.println("更新失败 : " + res.getResponseCode());
		}
	}
}
