package com.zzg.util.faceplusplus;

import java.util.HashMap;
import java.util.Map;

import org.nutz.http.Http;
import org.nutz.http.Response;

public class FaceDetect {

	private static final String URL = "https://api-cn.faceplusplus.com/facepp/v3/detect";

	private static final String APP_KEY = "ZZCJR7u5Lim6JUII4ago23l0uFBW2don";
	private static final String APP_SECRET = "Ej8ncsEdXfPOlBRkk6LzBUOPtQIbQH6Y";

	public static void detect(String imgUrl, int gender) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("api_key", APP_KEY);
		params.put("api_secret", APP_SECRET);
		params.put("image_url", imgUrl);
		params.put("return_attributes", "gender,age,smiling,glass,headpose,facequality,blur");


		Response response = Http.post2(URL, params, 10 * 1000);

		System.out.println(response.getContent());
	}

	public static void main(String[] args) {
		String imgUrl = "http://yueai-app-upload.img-cn-beijing.aliyuncs.com/20170118RFZPOHBQ.png";
		FaceDetect.detect(imgUrl, 1);
	}
}
