package com.zzg.util.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurHashMap {

	public static void main(String[] args) {
		final Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();

		ExecutorService mutiExec = Executors.newFixedThreadPool(200);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			Thread t = new Thread() {
				@Override
				public void run() {
					try {
						map.put(1, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			mutiExec.execute(t);
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		mutiExec.shutdown();
	}
}
