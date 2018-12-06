package sort.other;

public class BinarySearch3 {

    /**
     * 从小到大排序的数组
     * 查找第一个等于给定值的元素
     */
    public static int findFirstValue(int [] arr, int n, int target) {
        int low = 0;
        int high = n - 1;
        int mid;
        while (low <= high) {
            mid = low + (high - low >> 1);
            if (arr[mid] > target) {
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                if ((mid == 0) || (arr[mid - 1] != target)) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }
    public static int findFirstValue2(int [] arr, int n, int target) {
        int low = 0;
        int high = n - 1;
        int mid;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (arr[mid] >= target) {
                high = mid - 1;
            } else  {
                low = mid + 1;
            }
        }
        if (low < n && arr[low] == target) {
            return low;
        }

        return -1;
    }

    /**
     * 从小到大排序的数组
     * 查找最后一个等于给定值的元素
     */
    public static int findLastValue(int [] arr, int n, int target) {
        int low = 0;
        int high = n -1;
        int mid;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (arr[mid] < target) {
                low = mid + 1;
            } else if (arr[mid] > target) {
                high = mid - 1;
            } else {
                if (mid == n - 1 || arr[mid + 1] != target) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }

        if (high < n && (arr[high] == target)) {
            return high;
        }


        return -1;
    }
    public static int findLastValue2(int [] arr, int n, int target) {
        int low = 0;
        int high = n -1;
        int mid;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (arr[mid] <= target) {
                low = mid + 1;
            } else if (arr[mid] > target) {
                high = mid - 1;
            }
        }

        if (high < n && (arr[high] == target)) {
            return high;
        }


        return -1;
    }

    /**
     * 从小到大排序的数组
     * 查找第一个大于等于给定值的元素
     *
     */
    public static int findFirstBigValue(int [] arr, int n, int target) {
        int low = 0;
        int high = n -1;
        int mid;
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                if (mid == 0 || arr[mid - 1] < target) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }


        return -1;
    }

    public static int bsearch(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid =  low + ((high - low) >> 1);
            if (a[mid] >= value) {
                if ((mid == 0) || (a[mid - 1] < value)) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int [] arr = {1,2,3,4,5,6,8,8,8,11,18};
//        System.out.println(findFirstValue2(arr, arr.length, 8));
        System.out.println(findFirstBigValue(arr, arr.length, 10));
        System.out.println(bsearch(arr, arr.length, 10));
    }
}
