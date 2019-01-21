package utils;

import java.util.Arrays;

public class SortUtils {

    public static void printArr(int [] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void swap(int[] a, int s, int e) {
        a[s] = a[s] + a[e];
        a[e] = a[s] - a[e];
        a[s] = a[s] - a[e];
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }
}
