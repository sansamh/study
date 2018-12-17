package sort.newcoder;

import sort.util.SortUtils;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 10:23 2018/12/17
 */
public class InsertionSort {

	/**
	 * 插入排序
	 * 分为已排序和未排序 每次取未排序第一个 在已排序里找到应该插入的位置
	 * @param a
	 * @return
	 */

	public static int[] sort(int[] a) {
		if (a == null || a.length < 2) {
			return a;
		}

		for (int i = 1; i < a.length; i++) {
			for (int j = i - 1; j >= 0 && a[j] > a[j+1]; j--) {
				SortUtils.swap(a, j, j+1);
			}
		}
		return a;
	}

	public static void main(String[] args) {
		int[] a = {2,1,4,7,5,6,9,8,3};
		SortUtils.printArr(sort(a));
	}
}
