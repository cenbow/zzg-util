package com.zzg.util.ipipnet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class IPTest {
	public static void main(String[] args) throws Exception {
		IP.load("/Users/zhangzhiguang/Documents/workspace_zzg/zzg-util/src/main/resources/17monipdb/17monipdb.dat");

		String path = "/Users/zhangzhiguang/Desktop/ip.txt";

		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] loc = IP.find(line);
			String province = loc[1];
			String city = loc[2];
			if (StringUtils.isBlank(city)) {
				city = province;
			}
			System.out.println(province + "|" + city);

		}
		br.close();

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
