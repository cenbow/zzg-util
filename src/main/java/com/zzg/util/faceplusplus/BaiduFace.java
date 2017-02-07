package com.zzg.util.faceplusplus;

import org.nutz.http.Header;
import org.nutz.http.Http;
import org.nutz.http.Response;
import org.nutz.img.Images;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class BaiduFace {

	private static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token";
	private static final String URL = "https://aip.baidubce.com/rest/2.0/face/v1/detect";

	public static final String APP_ID = "9217958";
	private static final String APP_KEY = "7AQURWvrs9UCzfUjTL7xhdMA";
	private static final String APP_SECRET = "d4pDi0B1pyWmeFBbqti42tXD1g4i5eU0";

	public static String getToken() {
		String url = TOKEN_URL + "?" + "grant_type=client_credentials&client_id=" + APP_KEY + "&client_secret="
				+ APP_SECRET;
		Response response = Http.get(url);
		JSONObject data = JSON.parseObject(response.getContent());
		return data.getString("access_token");
	}

	public static void detect(String image, int gender) {
		String token = getToken();
		System.out.println(token);
		String url = URL + "?access_token=" + token;

		Header header = Header.create().set("Content-Type", "application/x-www-form-urlencoded; param=value");
		Response response = Http.post3(url, image, header, 10 * 1000);
		System.out.println(response.getContent());
	}

	public static void main(String[] args) {
		String imgUrl = "/Users/zhangzhiguang/Desktop/katong.png";
		String image = Images.encodeBase64(imgUrl);
		System.out.println(image + "__________");
		BaiduFace.detect(image, 1);
	}

}
