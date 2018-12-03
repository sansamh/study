package sort.bucketsort;

import sort.util.SortUtils;

/**
 * @version 1.0
 * @description: 计数排序
 *
 * @author: 侯春兵
 * @Date: 11:23 2018/12/3
 */
public class CountingSort {
	//基数排序可看为桶排序的变种
	//计数排序只能给非负整数排序 如果要排序的数据是其他类型的 将其在不改变相对大小的情况下，转化为非负整数
	//如果要排序的数据中有负数，数据的范围是 [-1000, 1000]，那我们就需要先对每个数据都加 1000，转化成非负整数据

	/**
	 * arr为非负正整数
	 *
	 * @param arr
	 * @param n   arr.length
	 */
	public static void radixSort(int[] arr, int n) {
		if (null == arr || arr.length <= 1) {
			SortUtils.printArr(arr);
			return;
		}

		// 1.找到数组中的最大值
		int max = 0;
		for (int i : arr) {
			if (i > max) {
				max = i;
			}
		}
		// 2.创建数组 长度为0-max
		int[] c = new int[max + 1];

		// 3.统计原数组中每个数值的个数之和 放进c中 c的下标为值
		for (int i = 0; i < arr.length; i++) {
			c[arr[i]]++;
		}
		SortUtils.printArr(c);
		// 4.c数组 依次累加 相当于得到排序后的下标
		for (int i = 1; i < c.length; i++) {
			c[i] += c[i - 1];
		}
		SortUtils.printArr(c);
		// 5.排序
		int [] r = new int[n];
		for (int i = 0; i < arr.length; i++) {
			//得到arr[i] 在c中的value, arr[i]为c的下标
			int index = c[arr[i]] - 1;
			//赋值
			r[index] = arr[i];
			c[arr[i]] --;
		}
		SortUtils.printArr(r);
	}

	public static void main(String[] args) {
		int[] arr = {3, 5, 8, 5, 1, 9, 2};
		radixSort(arr, arr.length);
	}

}
