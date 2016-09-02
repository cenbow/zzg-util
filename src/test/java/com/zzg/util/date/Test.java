package com.zzg.util.date;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.nutz.lang.Lang;

public class Test {

	public static void main(String[] args) {
		final String pattern1 = "yyyy-MM-dd";
		final String pattern2 = "yyyy-MM";

		final Set<String> set = Collections.synchronizedSet(new HashSet<String>());

		// 单线程执行
		System.out.println("单线程执行:----------");
		ExecutorService exec = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 1000; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						String date = DateUtil.format(new Date(), pattern1);
						if (set.add(date)) {
							System.out.println(date);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(t);
		}
		exec.shutdown();

		Lang.sleep(1000);

		System.out.println("多线程执行:----------");
		ExecutorService mutiExec = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 10000; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						String date = DateUtil.format(new Date(), pattern1);
						if (set.add(date)) {
							System.out.println(date);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			mutiExec.execute(t);
		}
		mutiExec.shutdown();
	}
}
