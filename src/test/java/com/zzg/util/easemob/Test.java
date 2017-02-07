package com.zzg.util.easemob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Streams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzg.util.http.HttpUtil;

public class Test {
	private final static String CLIENT_ID = "";
	private final static String CLIENT_SECRET = "";
	private final static String ORG_NAME = "";
	private final static String APP_NAME = "";

	public static void main(String[] args) {
		String token = Test.getToken();
		System.out.println(token);
		Test.getChatMessages(token);
	}

	public static String getToken() {
		String url = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/token";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("grant_type", "client_credentials");
		params.put("client_id", CLIENT_ID);
		params.put("client_secret", CLIENT_SECRET);

		Header header = Header.create().set("Content-Type", "application/json");
		Response response = Http.post3(url, JSON.toJSONString(params), header, 30 * 1000);
		JSONObject jsonObject = JSON.parseObject(response.getContent());
		return jsonObject.getString("access_token");
	}

	public static String getChatMessages(String token) {
		String url = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatmessages?ql=select+*+where+timestamp>1403164734226&limit=3";
		
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", "Bearer " + token);
		header.put("Content-Type", "application/json");

		System.out.println(HttpUtil.get(url, header, 30 * 1000));
		return null;
	}

	public static String messages(String token) {
		String url = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/messages";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("target_type", "users");
		params.put("target", new String[] { "2cb53201ca400d589dddff187b3f4913" });
		params.put("from", "36552f045d80b8871cec5d6cc4c0f508");

		Map<String, String> msg = new HashMap<String, String>();
		msg.put("type", "txt");
		msg.put("msg", "一大波美女来袭！！！");
		params.put("msg", msg);

		params.put("client_secret", CLIENT_SECRET);

		Header header = Header.create().set("Content-Type", "application/json").set("Authorization", "Bearer " + token);
		Response response = Http.post3(url, JSON.toJSONString(params), header, 30 * 1000);
		System.out.println(response.getContent());
		return null;
	}

	public static void chatfiles(String token) {
		String url = "https://a1.easemob.com/" + ORG_NAME + "/" + APP_NAME + "/chatfiles";

		try {
			File file = File.createTempFile("chatfile", "data");
			URL img = new URL("http://aliulian.img-cn-beijing.aliyuncs.com/20160804BKGFI6M0.png");
			OutputStream ops = new FileOutputStream(file);
			Streams.writeAndClose(ops, img.openStream());

			Map<String, String> header = new HashMap<String, String>();
			header.put("Authorization", "Bearer " + token);
			Map<String, File> files = new HashMap<String, File>();
			files.put("file", file);
			System.out.println(HttpUtil.upload(url, header, files, 60 * 1000));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void inputstreamtofile(InputStream ins, File file) throws Exception {
		OutputStream ops = new FileOutputStream(file);
		Streams.writeAndClose(ops, ins);
	}
}
