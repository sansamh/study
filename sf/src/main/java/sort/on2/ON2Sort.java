package sort.on2;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 时间复杂度为O(n*2)的排序算法
 */
public class ON2Sort {

    /**
     * 冒泡排序 小到大
     * 空间复杂度O(n) 原地排序
     *
     * @param arr
     */
    public static void maopao(int[] arr) {
        if (ArrayUtils.isEmpty(arr) || arr.length < 2) {
            SortUtils.printArr(arr);
            return;
        }
        //标识是否进行了交换 如果没有进行交换 说明已经排序完成
        boolean flag;
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = true;
                }
            }
            if (!flag) {
                return;
            } else {
                SortUtils.printArr(arr);
            }
        }
    }

    /**
     * 交换排序 小到大
     * 思想为将数组分为两部分 一部分为已排序的 一部分未排序 每次取未排序的第一位与已排序的部分进行比较 找到自己的位置
     * 空间复杂度O(n) 原地排序
     *
     * @param arr
     */
    public static void jiaohuan(int[] arr) {
        if (ArrayUtils.isEmpty(arr) || arr.length < 2) {
            SortUtils.printArr(arr);
            return;
        }

        int j, temp;
        for (int i = 1; i < arr.length; i++) {
            j = i - 1;
            temp = arr[i];
            for (; j >= 0; j--) {
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            //arr[j] <= temp(arr[i]) 的时候 不用交换
            if (j + 1 != i) {
                arr[j + 1] = temp;
                SortUtils.printArr(arr);
            }
        }
    }

    /**
     * 选择排序 小到大
     * 也是分为两部分 已排序和未排序
     * 每次从数组中找到最小值 放到已排序的尾部
     * 空间复杂度O(n) 非原地排序 5 8 5 2 9，我们知道第一遍选择第1个元素5会和2交换，那么原序列中两个5的相对前后顺序就被破坏了
     *
     * @param arr
     */
    public static void xuanze(int[] arr) {
        if (ArrayUtils.isEmpty(arr) || arr.length < 2) {
            SortUtils.printArr(arr);
            return;
        }

        int min, temp;
        for (int i = 0; i < arr.length; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                temp = arr[min];
                arr[min] = arr[i];
                arr[i] = temp;
                SortUtils.printArr(arr);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 8, 6, 9, 5, 4, 0, 7};
        maopao(arr);
        System.out.println("--------------------");

        int[] arr1 = {3, 2, 1, 8, 6, 9, 5, 4, 0, 7};
        jiaohuan(arr1);
        System.out.println("--------------------");

        int[] arr2 = {3, 2, 1, 8, 6, 9, 5, 4, 0, 7};
        xuanze(arr2);
    }

}
