package sort.newcoder;

import sort.util.SortUtils;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 10:31 2018/12/17
 */
public class SelectionSort {
	/**
	 * 选择排序
	 * 分为已排序和未排序
	 * 每次找到未排序最小的一个 放到已排序的后面
	 */
	public static int[] sort(int[] a) {
		if (a == null || a.length < 2) {
			return a;
		}
		int minIndex;
		for (int i = 0; i < a.length - 1; i++) {
			minIndex = i;
			for (int j = i + 1; j < a.length; j ++) {
				minIndex = a[j] < a[minIndex] ? j : minIndex;
			}
			if (minIndex != i) {
				SortUtils.swap(a, i, minIndex);
			}
		}

		return a;
	}

	public static void main(String[] args) {
		int[] a = {2,1,4,7,5,6,9,8,3};
		SortUtils.printArr(sort(a));
	}
}
