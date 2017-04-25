package com.redhat.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
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
public class HttpClientMulti {
	private static Logger logger = LoggerFactory.getLogger(HttpClientMulti.class);
	// 并发线程数
	private static int CNT = 800;

	/**
	 * DEBUG
	 */
	private static int cnt = 0;
	private static Object xlock = new Object();

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < CNT; ++i) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					long id = 0;
					String token = null;
					String ip = "127.0.0.1";
					int port = 9901;
					String username = "maomao";
					String password = "520";

					////////// 请求 //////////

					// login
					UserModel user = new UserModel(username, Coder.encodeToMD5(password));
					Request req = new Request(id, token, RequestCode.USER, SubCode.Login, JSON.toJSONString(user));
					String str = JSON.toJSONString(req);
					String data = Coder.encodeToBase64(str);
					// 用户登录结果
					String res = null;
					try {
						res = task(id, token, ip, port, data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("login ==> " + res);
					Response resMsg = JSON.parseObject(res, Response.class);
					if (resMsg.getResponseCode() == ResponseCode.SUCCESS) {
						LoginModel model = JSON.parseObject(resMsg.getData(), LoginModel.class);
						// 设置登录数据
						id = model.getId();
						token = model.getToken();
						port = model.getGatePort();
						ip = model.getGateIp();

						////////// 请求 //////////

						// getInfo
						Request msg2 = new Request(id, token, RequestCode.INFO, SubCode.GetInfo, null);
						String str2 = JSON.toJSONString(msg2);
						String data2 = Coder.encodeToBase64(str2);
						// 获取信息结果
						String res2 = null;
						try {
							res2 = task(id, token, ip, port, data2);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("getInfo ==> " + res2);
						Response resMsg2 = JSON.parseObject(res2, Response.class);
						if (resMsg2.getResponseCode() == ResponseCode.SUCCESS) {

							// DEBUG
							synchronized (xlock) {
								logger.info("获取成功!!!" + ++cnt);
							}

						} else {
							System.out.println("获取失败!!!");
						}
					} else {
						System.out.println("登录失败!!!");
					}
				}
			});
			t.start();
		}
	}

	// 发送请求
	private static String task(long id, String session, final String ip, final int port, final String params)
			throws Exception {
		// 更新url
		URL url = new URL("http://" + ip + ":" + port);
		// openConnection
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
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
}
