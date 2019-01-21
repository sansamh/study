package sort.newcoder;

import java.util.Arrays;

public class BubbleSort {
    /**
     * 交换次数
     */
    private static int swapCount = 0;

    /**
     * 冒泡 learn from 左神
     */
    public static int[] sort(int[] a) {
        if (a == null || a.length < 2) {
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

    public static void swap(int[] arr, int a, int b) {
        swapCount++;
        arr[a] = arr[a] + arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }

    public static void main(String[] args) {
        int [] a = {2,5,7,4,3,6,9,1};
        System.out.println(Arrays.toString(sortOldWay(a)));
        //两个方法交换次数一样
        System.out.println(swapCount);
    }

    public static int[] sortOldWay(int[] a) {
        if (a == null || a.length < 2) {
            return a;
        }
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j+1]) {
                    swap(a, j,j+1);
                }
            }
        }
        return a;
    }
}
