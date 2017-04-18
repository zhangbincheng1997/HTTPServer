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
import com.redhat.login.model.UserModel;
import com.redhat.login.model.UserReq;
import com.redhat.login.protocol.Consts;
import com.redhat.login.protocol.HttpMsg;
import com.redhat.login.protocol.RequestCode;
import com.redhat.login.protocol.ResultCode;
import com.redhat.login.util.Coder;

/**
 * @author littleredhat
 */
public class HttpClient {
	private static int id = 0;
	private static String session = "";
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
		System.out.println("====================");

		while (!isOk) {
			String option = sc.nextLine();
			if (option.equals("login")) {
				System.out.print("用户名 : ");
				String username = sc.nextLine();
				System.out.print("密码 : ");
				String password = sc.nextLine();
				// login
				UserReq user = new UserReq(username, Coder.encodeToMD5(password));
				// msg
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Login, JSON.toJSONString(user));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 登录
				String res = task(data);
				login(res);
			} else if (option.equals("register")) {
				System.out.print("用户名 : ");
				String username = sc.nextLine();
				System.out.print("密码 : ");
				String password = sc.nextLine();
				// register
				UserReq user = new UserReq(username, Coder.encodeToMD5(password));
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Register, JSON.toJSONString(user));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 注册
				String res = task(data);
				register(res);
			} else if (option.equals("list")) {
				System.out.println("====================");
				System.out.println("login		register");
				System.out.println("====================");
			} else if (option.equals("quit")) {
				break;
			}
		}

		System.out.println("========================================");
		System.out.println("getInfo					updateInfo");
		System.out.println("getScore	setScore	updateScore");
		System.out.println("getMail					sendMail");
		System.out.println("========================================");

		while (isOk) {
			String option = sc.nextLine();
			if (option.equals("getInfo")) {
				InfoReq req = new InfoReq(id);
				// getInfo
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Get_Info, JSON.toJSONString(req));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 获取用户信息
				String res = task(data);
				getInfo(res);
			} else if (option.equals("updateInfo")) {
				System.out.print("level : ");
				int level = Integer.parseInt(sc.nextLine());
				System.out.print("info : ");
				String info = sc.nextLine();
				// updateInfo
				InfoModel model = new InfoModel(id, level, info);
				// msg
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Update_Info, JSON.toJSONString(model));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 更新用户信息
				String res = task(data);
				updateInfo(res);
			}

			else if (option.equals("getScore")) {
				ScoreReq req = new ScoreReq(id);
				// getScore
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Get_Score, JSON.toJSONString(req));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 获取成绩
				String res = task(data);
				getScore(res);
			} else if (option.equals("setScore")) {
				System.out.print("subject : ");
				String subject = sc.nextLine();
				System.out.print("score : ");
				int score = Integer.parseInt(sc.nextLine());
				// setScore
				ScoreModel model = new ScoreModel(id, subject, score);
				// msg
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Set_Score, JSON.toJSONString(model));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 设置成绩
				String res = task(data);
				setScore(res);
			} else if (option.equals("updateScore")) {
				System.out.print("subject : ");
				String subject = sc.nextLine();
				System.out.print("score : ");
				int score = Integer.parseInt(sc.nextLine());
				// updateScore
				ScoreModel model = new ScoreModel(id, subject, score);
				// msg
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Update_Score, JSON.toJSONString(model));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 更新成绩
				String res = task(data);
				updateScore(res);
			}

			else if (option.equals("getMail")) {
				MailReq req = new MailReq(id);
				// getMail
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Get_Mail, JSON.toJSONString(req));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 获取邮件
				String res = task(data);
				getMail(res);
			} else if (option.equals("sendMail")) {
				System.out.print("type : ");
				String type = sc.nextLine();
				System.out.print("sendName : ");
				String sendName = sc.nextLine();
				System.out.println("receiveId : ");
				int receiveId = Integer.parseInt(sc.nextLine());
				System.out.print("content : ");
				String content = sc.nextLine();
				// sendMail
				MailModel model = new MailModel(-1, type, sendName, receiveId, content, new Date());
				// msg
				HttpMsg msg = new HttpMsg(RequestCode.C2S_Send_Mail, JSON.toJSONString(model));
				// to JSON
				String str = JSON.toJSONString(msg);
				// to Base64
				String data = Coder.encodeToBase64(str);
				// 发送邮件
				String res = task(data);
				sendMail(res);
			} else if (option.equals("list")) {
				System.out.println("========================================");
				System.out.println("getInfo					updateInfo");
				System.out.println("getScore	setScore	updateScore");
				System.out.println("getMail					sendMail");
				System.out.println("========================================");
			} else if (option.equals("quit")) {
				break;
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
		// session非空则设置
		if (session != null && !session.isEmpty()) {
			String _session = session + ";" + id;
			// 设置session
			http.setRequestProperty(Consts.SESSION_GET, _session);
		}

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
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			UserModel model = JSON.parseObject(res.getData(), UserModel.class);
			id = model.getId();
			session = model.getSession();
			port = model.getGatePort();
			ip = model.getGateIp();

			System.out.println("id == " + id);
			System.out.println("session == " + session);
			System.out.println("gateIp == " + ip);
			System.out.println("gatePort == " + port);

			// 登录成功
			isOk = true;
		} else {
			System.out.println("登录失败 : " + res.getCode());
		}
	}

	private static void register(String msg) {
		System.out.println("register ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			System.out.println("注册成功 : " + res.getCode());
		} else {
			System.out.println("注册失败 : " + res.getCode());
		}
	}

	//////////

	private static void getInfo(String msg) {
		System.out.println("getInfo ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			InfoModel model = JSON.parseObject(res.getData(), InfoModel.class);
			int level = model.getLevel();
			String info = model.getInfo();
			System.out.println("get level == " + level);
			System.out.println("get info == " + info);
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}

	private static void updateInfo(String msg) {
		System.out.println("setInfo ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			System.out.println("setInfo == success");
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}

	//////////

	private static void getScore(String msg) {
		System.out.println("getScore ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			// 解析成绩列表
			List<ScoreModel> modelList = JSONObject.parseArray(res.getData(), ScoreModel.class);
			// 遍历成绩列表
			for (ScoreModel model : modelList) {
				String subject = model.getSubject();
				int score = model.getScore();
				System.out.println("get subject == " + subject);
				System.out.println("get score == " + score);
			}
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}

	private static void setScore(String msg) {
		System.out.println("setScore ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			System.out.println("updateScore == success");
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}

	private static void updateScore(String msg) {
		System.out.println("updateScore ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			System.out.println("updateScore == success");
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}

	//////////

	private static void getMail(String msg) {
		System.out.println("getMail ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			// 解析邮件列表
			List<MailModel> modelList = JSONObject.parseArray(res.getData(), MailModel.class);
			// 遍历邮件列表
			for (MailModel model : modelList) {
				int id = model.getMailId();
				String type = model.getType();
				String sendName = model.getSendName();
				String content = model.getContent();
				Date date = model.getDate();
				System.out.println("get id == " + id);
				System.out.println("get type == " + type);
				System.out.println("get sendName == " + sendName);
				System.out.println("get content == " + content);
				System.out.println("get date == " + date);
			}
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}

	private static void sendMail(String msg) {
		System.out.println("setMail ==> " + msg);
		HttpMsg res = JSON.parseObject(msg, HttpMsg.class);

		if (res.getCode() == ResultCode.SUCCESS) {
			System.out.println("setMail == success");
		} else {
			System.out.println("resultCode == " + res.getCode());
		}
	}
}
