package com.zzg.util.test;

import org.nutz.http.Http;
import org.nutz.http.Response;

public class Test {
	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			Response response = Http.get("http://localhost:8080/backyard-aibinong-platform/test.html");
			System.out.println(response.getContent());
		}

	}
}
