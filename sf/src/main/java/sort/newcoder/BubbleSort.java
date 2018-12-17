package sort.newcoder;

import java.util.Arrays;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 9:41 2018/12/17
 */
public class BubbleSort {
	/**
	 * 左神代码 冒泡算法
	 * 从length-1 到 0 区间内找极值
	 */
	public static int[] sort(int[] a) {
		if (a == null || a.length <= 1) {
			return a;
		}
		for (int end = a.length - 1; end > 0; end--) {
			for (int i = 0; i < end; i++) {
				if (a[i] > a[i+1]) {
					swap(a, i, i+1);
				}
			}
		}
		return a;
	}

	public static void swap(int[] a, int j, int m) {
		a[j] = a[j] + a[m];
		a[m] = a[j] - a[m];
		a[j] = a[j] - a[m];
	}

	public static void main(String[] args) {
		int [] a = {2,5,7,4,1,9,6};
		System.out.println(Arrays.toString(sort(a)));
	}
}
