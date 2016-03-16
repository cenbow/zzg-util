package com.zzg.util.nearfind;

public class Test {

	public static void main(String args[]) {
		long[] array = new long[] { 1, 5, 7, 8 };
		NearFind nf = new NearFind(array);
		int i = nf.find(6);
		System.out.println("返回的索引是：" + i + " ,找到最近数是：" + array[i]);
	}
}
