package com.zzg.util.date;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		final String pattern1 = "yyyy-MM-dd";
		final String pattern2 = "yyyy-MM";

		final Set<String> set = Collections.synchronizedSet(new HashSet<String>());

		// 单线程执行
		ExecutorService exec = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 1000000; i++) {
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

		sleep(1000);

		ExecutorService mutiExec = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 10000000; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						String date = DateUtil.format(new Date(), pattern2);
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

	private static void sleep(long millSec) {
		try {
			TimeUnit.MILLISECONDS.sleep(millSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
