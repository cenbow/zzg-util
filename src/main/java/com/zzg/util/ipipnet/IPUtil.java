package com.zzg.util.ipipnet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IPUtil {

	private static IPUtil currentIPUtil = new IPUtil();

	public static IPUtil getIPUtil() {
		return currentIPUtil;
	}

	private IPUtil() {
		IP.load("/Users/zhangzhiguang/Documents/workspace_zzg/zzg-util/src/main/resources/17monipdb/17monipdb.dat");
	}

	/**
	 * 解析IP成 国家 省份 城市
	 * 
	 * @author zhang_zg
	 * @param ip
	 * @return
	 */
	public static IPEntity parseIP(String ip) {
		IPEntity ipEntity = new IPEntity();

		// 校验IP地址是否合法
		Pattern p = Pattern
				.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher m = p.matcher(ip);

		if (m.find()) {
			try {
				String[] array = IP.find(ip);

				int len = array.length;
				if (len > 0) {
					ipEntity.setCounty(array[0]);
				}
				if (len > 1) {
					ipEntity.setProvinces(array[1]);
				}
				if (len > 2) {
					ipEntity.setProvinces(array[2]);
				}

			} catch (Exception e) {
			}
		}
		return ipEntity;
	}
	
	
	public static void main(String[] args) {
		System.out.println(IPUtil.parseIP("123.123.123.123").toString());
		;
	}


}
