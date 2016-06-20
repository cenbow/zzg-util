package com.zzg.util.test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Test {

	private static Semaphore semaphore = new Semaphore(0);

	private static Random random = new Random();

	public static void main(String[] args) throws UnsupportedEncodingException {
		String start_ds = "20160611";
		String end_ds = "20160612";
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT t1.tv_name, t1.abs_column_name, t1.is_live, GROUP_CONCAT(t2.recommend_id) AS card_id\n");
		sb.append("FROM kuyun_cards_recommends t1, kuyun_cards_subscribe t2\n");
		sb.append("WHERE t1.id = t2.recommend_id\n");
		sb.append("AND t1.abs_column_id = " + 11 + "\n");
		sb.append("GROUP BY t1.tv_name, t1.abs_column_name, t1.is_live\n");

		System.out.println(sb.toString());

		System.out.println(URLDecoder.decode(
				"http://cqcards.in.kuyun.com:80/cards/showColumnInfo?column_id=1000001002%20AND%203*2*1%3d6%20AND%20105%3d105&tv_id=1000000013", "UTF-8"));

		System.out.println(semaphore.tryAcquire());

		for (int i = 0; i < 10; i++) {
			System.out.println(random.nextInt(5000));
		}
		//
		//		StringBuffer sb = new StringBuffer();
		//		sb.append("SELECT t1.product_id, t1.client_agent, COUNT(t1.device_id) AS all_total,\n");
		//		sb.append("IF(t2.new_total IS NULL, 0, t2.new_total) AS new_total\n");
		//		sb.append("FROM kytv_user_plugin t1\n");
		//		sb.append("LEFT OUTER JOIN (\n");
		//		sb.append("	SELECT product_id, client_agent, COUNT(1) new_total\n");
		//		sb.append("	FROM kytv_user_plugin\n");
		//		sb.append("	WHERE create_time_plug = " + 20160512 + "\n");
		//		sb.append(" AND !(product_id = 49 AND sdk_version < 1000)\n"); // 屏蔽创维2.5版本
		//		sb.append("	GROUP BY product_id, client_agent\n");
		//		sb.append(") t2\n");
		//		sb.append("ON ( t1.product_id = t2.product_id AND t1.client_agent = t2.client_agent)\n");
		//		sb.append("WHERE t1.create_time_plug <= " + 20160512 + "\n");
		//		sb.append("AND !(t1.product_id = 49 AND t1.sdk_version < 1000)\n"); // 屏蔽创维2.5版本
		//		sb.append("GROUP BY t1.product_id, t1.client_agent\n");
		//		
		//		System.out.println(sb.toString());
		//		
		//		
		//		Response response = Http.get("http://tvtest.kuyun.com/cards/api/interface/wxpay/get_redpack_info?mch_billno=1306653501201604231000000108");
		//
		//		
		//		String text = response.getContent();
		//		
		//		Record content = Json.fromJson(Record.class, text);
		//		Record resp = Json.fromJson(Record.class, content.getString("Response"));
		//		if ("0".equals(resp.getString("result_code"))) {
		//			Record redpackInfo = Json.fromJson(Record.class, resp.getString("redpack_info"));
		//			String returnCode = redpackInfo.getString("return_code");
		//			String resultCode = redpackInfo.getString("result_code");
		//			if ("SUCCESS".equals(returnCode) && "SUCCESS".equals(resultCode)) {
		//				String redStatus = redpackInfo.getString("status");
		//				System.out.println(redStatus);
		//				if ("FAILED".equals(redStatus) || "REFUND".equals(redStatus)) {
		//
		//				}
		//			}
		//		}

	}
	
	public static void test() {
		try {
		} finally {
			System.out.println("TRY");
		}
	}

	public static boolean isNotDeviceId(String text) {
		if (StringUtils.isBlank(text)) {
			return true;
		}

		Pattern pattern = Pattern.compile("(\\|| |\\.|/|\\?)");
		Matcher matcher = pattern.matcher(text.trim());
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean isNotClientAgent(String text) {
		if (StringUtils.isBlank(text)) {
			return true;
		}

		Pattern pattern = Pattern.compile("(:|-|\\.|\\?|@)");
		Matcher matcher = pattern.matcher(text.trim());
		if (matcher.find()) {
			return true;
		}
		return false;
	}

}
