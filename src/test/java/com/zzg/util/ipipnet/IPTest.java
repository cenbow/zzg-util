package com.zzg.util.ipipnet;

import java.util.Random;

public class IPTest {
	public static void main(String[] args) {
		IP.load("/Users/zhangzhiguang/Documents/workspace_zzg/zzg-util/src/main/resources/17monipdb/17monipdb.dat");

		Long st = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			IP.findIp(randomIp());
		}
		Long et = System.currentTimeMillis();
		System.out.println((et - st));

		System.out.println(IP.findIp("-1001.223.223.1").toString());

	}

	public static String randomIp() {
		Random r = new Random();
		StringBuffer str = new StringBuffer();
		str.append(r.nextInt(1000000) % 255);
		str.append(".");
		str.append(r.nextInt(1000000) % 255);
		str.append(".");
		str.append(r.nextInt(1000000) % 255);
		str.append(".");
		str.append(0);

		return str.toString();
	}

}
