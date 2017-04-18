package com.redhat.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
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
public class HttpClientMulti {
	// 并发线程数
	private static int CNT = 1600;

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < CNT; ++i) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					int id = 0;
					String session = null;
					String ip = "127.0.0.1";
					int port = 9901;
					String username = "maomao";
					String password = "520";

					////////// 请求 //////////

					// login
					UserReq user = new UserReq(username, Coder.encodeToMD5(password));
					HttpMsg msg = new HttpMsg(RequestCode.C2S_Login, JSON.toJSONString(user));
					String str = JSON.toJSONString(msg);
					String data = Coder.encodeToBase64(str);
					// 登录结果
					String res = null;
					try {
						res = task(id, session, ip, port, data);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("login ==> " + res);
					HttpMsg resMsg = JSON.parseObject(res, HttpMsg.class);
					if (resMsg.getCode() == ResultCode.SUCCESS) {
						UserModel model = JSON.parseObject(resMsg.getData(), UserModel.class);
						// 设置登录数据
						id = model.getId();
						session = model.getSession();
						port = model.getGatePort();
						ip = model.getGateIp();

						////////// 请求 //////////

						// getInfo
						InfoReq info = new InfoReq(id);
						HttpMsg msg2 = new HttpMsg(RequestCode.C2S_Get_Info, JSON.toJSONString(info));
						String str2 = JSON.toJSONString(msg2);
						String data2 = Coder.encodeToBase64(str2);
						// 获取用户信息结果
						String res2 = null;
						try {
							res2 = task(id, session, ip, port, data2);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						System.out.println("getInfo ==> " + res2);
						HttpMsg resMsg2 = JSON.parseObject(res2, HttpMsg.class);
						if (resMsg2.getCode() == ResultCode.SUCCESS) {
							System.out.println("获取成功!!!");
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
	private static String task(int id, String session, final String ip, final int port, final String params)
			throws Exception {
		// 更新url
		URL url = new URL("http://" + ip + ":" + port);
		// openConnection
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
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
}
