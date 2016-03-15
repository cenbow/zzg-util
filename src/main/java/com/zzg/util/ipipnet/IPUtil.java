package com.zzg.util.ipipnet;

/**
 * 单例的IP工具类
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年3月15日 下午5:38:46
 */
public class IPUtil {

	private static IPUtil currentIPUtil = new IPUtil();

	public static IPUtil getIPUtil() {
		return currentIPUtil;
	}

	private IPUtil() {
		IP.load("/Users/zhangzhiguang/Documents/workspace_zzg/zzg-util/src/main/resources/17monipdb/17monipdb.dat");
		System.out.println("17monipdb.dat 初始化成功！");
	}

	/**
	 * 解析IP成 国家 省份 城市
	 * 
	 * 如果IP地址非法，或者IP地址未找到位置返回的是一个[国家 省份 城市]都为空的对象
	 * 
	 * @author zhang_zg
	 * @param ip
	 * @return
	 */
	public static IPEntity parseIP(String ip) {
		IPEntity ipEntity = new IPEntity();

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

		return ipEntity;
	}
}
