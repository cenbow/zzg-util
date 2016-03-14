package com.zzg.util.ipipnet;

import java.util.Random;

import com.zzg.util.ipipnet.IPUtil;

public class IPTest {
	public static void main(String[] args) {

		Long st = System.nanoTime();
		for (int i = 0; i < 1000000; i++) {
			IPUtil.parseIP(randomIp());
		}
		Long et = System.nanoTime();
		System.out.println((et - st) / 1000 / 1000);

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
