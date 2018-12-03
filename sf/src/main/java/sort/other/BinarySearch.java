package sort.other;

/**
 * @version 1.0
 * @description: 二分查找
 * @author: 侯春兵
 * @Date: 17:36 2018/12/3
 */
public class BinarySearch {

	/**
	 * @param arr    升序数组
	 * @param target 查找目标
	 * @return int 目标所在下标
	 */
	public static int bSearch(int[] arr, int low, int high, int target) {
		if (null == arr || arr.length < 1) {
			System.out.println("error");
			return -1;
		}
		// 1.中位数取值
		int mid = low + (high - low >> 1);
		// 2.终止条件 low <= high
		while (low <= high) {
			if (arr[mid] == target) {
				return mid;
			} else if (arr[mid] < target) {
				// 3.1右边找 更新low
				low = mid + 1;
				return bSearch(arr, low, high, target);
			} else {
				// 3.2左边找 更新high
				high = mid - 1;
				return bSearch(arr, low, high, target);
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int [] arr = {1,2,3,4,5,6,7,8,9};
		System.out.println(bSearch(arr, 0, arr.length - 1, 3));
	}
}
