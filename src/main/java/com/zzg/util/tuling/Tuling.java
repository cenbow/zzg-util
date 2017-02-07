package com.zzg.util.tuling;

import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.lang.Lang;

import com.alibaba.fastjson.JSONObject;

/**
 * 图灵
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年10月10日 下午4:35:37
 */
public class Tuling {

	private final static String API_URL = "http://www.tuling123.com/openapi/api";
	private final static String APIKEY = "a952948d9c27469c8d4494454b7bcf7f";
	private final static String SECRET = "d25980f83cd6457d";

	private Tuling() {

	}


	public static String ask(String question) {
		String data = "{\"key\":\"" + APIKEY + "\",\"info\":\"" + question + "\"}";
		//获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());

		//生成密钥
		String keyParam = SECRET + timestamp + APIKEY;
		String key = Lang.md5(keyParam);

		//加密
		Aes mc = new Aes(key);
		data = mc.encrypt(data);

		//封装请求参数
		JSONObject json = new JSONObject();
		json.put("key", APIKEY);
		json.put("timestamp", timestamp);
		json.put("data", data);
		
		Header header = Header.create().set("Content-Type", "application/json; param=value");
		Response response = Http.post3(API_URL, json.toString(), header, 30 * 1000);
		return response.getContent();
	}

	public static void main(String[] args) {
		System.out.println(Tuling.ask("南京奇闻轶事"));
	}
}
