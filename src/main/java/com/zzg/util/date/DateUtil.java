package com.zzg.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 安全的日期工具类
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年3月15日 下午5:38:01
 */
public final class DateUtil {

	private DateUtil() {
	}

	public static final String SDF_LONG = "yyyy-MM-dd HH:mm:ss,SSS";
	public final static String SDF_STANDARD = "yyyy-MM-dd HH:mm:ss";
	public static final String SDF_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String SDF_SHORT = "yyyy-MM-dd";

	private static final ReentrantLock lock = new ReentrantLock();

	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
		if (tl == null) {
			lock.lock();
			try {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							System.out.println("thread: " + Thread.currentThread() + " init pattern: " + pattern);
							return new SimpleDateFormat(pattern);
						}
					};
					System.out.println("put new sdf of pattern " + pattern + " to map");
					sdfMap.put(pattern, tl);
				}
			} finally {
				lock.unlock();
			}
		}
		return tl.get();
	}

	public static String format(Date date, String pattern) {
		return getSdf(pattern).format(date);
	}
	
	public static Date parse(String dateString, String pattern) throws ParseException {
		return getSdf(pattern).parse(dateString);
	}
}
