package sort.other;

import java.lang.annotation.Target;

/**
 * @version 1.0
 * @description: 求平方根
 * @author: 侯春兵
 * @Date: 17:45 2018/12/3
 */
public class Square {

	public static double methodByBinarySearch(double t, Double precise) {
		double low = 0, high = t, middle;
		if (t < 0) {
			throw new RuntimeException("Negetive number cannot have a sqrt root.");
		}

		while (low <= high) {
			middle = low + (high - low) / 2;
			if (middle == t / middle) {
				return middle;
			} else {
				if (middle < t / middle) {
					low = middle + 1;
				} else {
					high = middle - 1;
				}
			}
		}


		return low + (high - low) / 2;
	}

	public static void main(String[] args) {
		System.out.println(methodByBinarySearch(9, null));
		System.out.println(Math.sqrt(9));
	}
}
