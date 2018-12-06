package sort.other;

/**
 * @version 1.0
 * @description:
 * @author: 侯春兵
 * @Date: 17:06 2018/12/6
 */
public class BinarySearch2 {

	/**
	 * 循环有序数组
	 */

	public static int find(int [] arr, int target) {
		int low = 0, high = arr.length - 1, middle;
		while (low <= high) {
			middle = low + ((high - low) >> 1);
			if (arr[middle] == target) return middle;
			if (arr[low] <= arr[middle]) {
				//左边有序
				if (arr[low] <= target && target <= arr[middle]) {
					high = middle - 1;
				} else {
					low = middle + 1;
				}
			} else {
				//右边有序
				if (arr[middle] <= target && target <= arr[high]) {
					low = middle + 1;
				} else {
					high = middle - 1;
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) {
		int [] arr = {4,5,6,7,0,1,2};
		System.out.println(find(arr, 0));
	}
}
