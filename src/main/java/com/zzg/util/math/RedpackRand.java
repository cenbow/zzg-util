package com.zzg.util.math;

import java.util.Random;

/**
 * 高斯分布的随机红包
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年4月19日 上午10:38:02
 */
public class RedpackRand {
	private static Random random = new Random();

	private RedpackRand() {
	}

	/**
	 * 生成随机红包
	 * @author zhang_zg
	 * @param amount 红包总额(分)
	 * @param total 红包个数
	 * @param max 最大金额(分)
	 * @param min 最小金额(分)
	 * @return
	 */
	public static int[] random(int amount, int total, int max, int min) {
		if (amount <= 0 || total <= 0 || max <= 0 || min <= 0) {
			throw new IllegalArgumentException("all paramters value required > 0.");
		}

		if (min > max) {
			throw new IllegalArgumentException("min can not > max.");
		}

		if (max * total < amount) {
			throw new IllegalArgumentException("(max * total) required >= " + amount);
		}

		int avg = amount / total; // 平均值
		int[] result = new int[total];
		
		for (int i = 0; i < total; i++) {
			int temp = 0;
			if (random(min, max) > avg) {
				// 随机数>平均值，则产生小红包
				temp = min + xRandom(min, avg);
			} else {
				// 否则, 产生大红包
				temp = max - xRandom(avg, max);
			}
			result[i] = temp;
			amount -= temp;
		}

		// 还有余额,则尝试随机匀到红包里面去
		while (amount > 0) {
			int index = random.nextInt(total);
			int temp = result[index];
			if (temp < max) {
				amount--;
				result[index]++;
			}
		}

		// 余额为负数,则尝试随机从红包中减掉
		while (amount < 0) {
			int index = random.nextInt(total);
			int temp = result[index];
			if (temp > min) {
				amount++;
				result[index]--;
			}
		}

		return result;
	}

	private static int random(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * 高斯分布处理:先平方再开方，尽量将产生的随机数靠近min
	 * @param min
	 * @param max
	 * @return
	 */
	private static int xRandom(int min, int max) {
		return (int) Math.sqrt(random.nextInt((int) Math.pow(max - min, 2) + 1));
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 10; i++) {
			int[] array = RedpackRand.random(104, 5, 50, 1);
			int sum = 0;
			for (int j : array) {
				sum += j;
				System.out.print(j + ", ");
			}
			System.out.println("[avg = " + 104 / 5 + ",sum = " + sum + "]");
		}
	}
}