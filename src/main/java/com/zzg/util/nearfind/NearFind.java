package com.zzg.util.nearfind;

import java.util.Arrays;

/**
 * 返回数组最匹配的值
 * 	  可用于数据分组
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年3月16日 上午11:29:22
 */
public class NearFind {

	private long[] sortedArray;

	public NearFind(long[] array) {
		// 数组排序
		Arrays.sort(array);
		this.sortedArray = array;
	}

	public int find(long key) {
		int index = Arrays.binarySearch(sortedArray, key);

		if (index >= 0) {
			return index;
		}

		index = Math.abs(index + 1);
		if (index == 0) {
			return index;
		}

		int size = sortedArray.length;
		if (index >= size) {
			return size - 1;
		}

		if ((key - sortedArray[index - 1]) > (sortedArray[index] - key)) {
			return index;
		} else {
			return index - 1;
		}
	}
}
