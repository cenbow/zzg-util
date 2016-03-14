package com.zzg.util.ipipnet;

import java.util.Random;

public class IPTest {
	public static void main(String[] args) {

		Long st = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			IPUtil.parseIP(randomIp());
		}
		Long et = System.currentTimeMillis();
		System.out.println((et - st));

		System.out.println(IPUtil.parseIP("-1001.223.223.1").toString());

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
