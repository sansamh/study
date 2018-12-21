package sort.newcoder;

import utils.SortUtils;

import java.util.Arrays;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 11:41 2018/12/17
 */
public class QuickSort {

	/**
	 * 快速排序
	 */
	public static void sort(int[] a) {
		if (a == null || a.length < 2) {
			return;
		}
		quickSort(a, 0, a.length - 1);
	}

	private static void quickSort(int[] a, int low, int high) {
		if (high < low) return;
		//三数取中 将中值放到第一位
		int middle = getMiddle(a, high, low);

		int start = low, end = high;

		while (start < end) {
			//从左往右 找第一个比middle小的
			while (start < end && a[end] >= middle) {
				end--;
			}
			a[start] = a[end];

			while (start < end && a[start] <= middle) {
				start++;
			}
			a[end] = a[start];
		}
		//跳出循环 start=end
		a[start] = middle;
		quickSort(a, low, start-1);
		quickSort(a, start+1, high);
	}

	private static int getMiddle(int[] a, int high, int low) {
		int middle = low + ((high - low) >> 1);
		if (a[low] > a[high]) {
			SortUtils.swap(a, low, high);
		}
		if (a[middle] > a[high]) {
			SortUtils.swap(a, middle, high);
		}
		if (a[middle] > a[low]) {
			SortUtils.swap(a, middle, low);
		}
		return a[low];
	}

	public static void main(String[] args) {
		int[] ints = SortUtils.generateRandomArray(20, 100);
//		sort(ints);
		quickSortZ(ints);
		System.out.println(Arrays.toString(ints));
	}


	/**
	 * 左神版本
	 */

	public static void quickSortZ(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		quickSortZ(arr, 0, arr.length - 1);
	}

	public static void quickSortZ(int[] arr, int l, int r) {
		if (l < r) {
			swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
			int[] p = partition(arr, l, r);
			quickSortZ(arr, l, p[0] - 1);
			quickSortZ(arr, p[1] + 1, r);
		}
	}

	public static int[] partition(int[] arr, int l, int r) {
		int less = l - 1;
		int more = r;
		while (l < more) {
			if (arr[l] < arr[r]) {
				swap(arr, ++less, l++);
			} else if (arr[l] > arr[r]) {
				swap(arr, --more, l);
			} else {
				l++;
			}
		}
		swap(arr, more, r);
		return new int[] { less + 1, more };
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
}
