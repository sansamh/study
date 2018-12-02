package leetcode;

import java.util.Arrays;
import java.util.HashMap;

public class Leetcode1 {

    /**
     * 1. Two Sum
     * Easy
     * 9097
     * 271
     *
     *
     * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     *
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     *
     * Example:
     *
     * Given nums = [2, 7, 11, 15], target = 9,
     *
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     */

    /**
     * times:O(n)
     * space:O(n)
     *
     * @param arr
     * @param target
     * @return
     */
    public static int[] twoSum(int[] arr, int target) {
        if (null == arr || arr.length < 2) {
            return new int[]{-1, -1};
        }
        int[] res = new int[]{-1, -1};
        //存放value, index
        HashMap<Integer, Integer> map = new HashMap<>(arr.length);

        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(target - arr[i])) {
                res[0] = map.get(target - arr[i]);
                res[1] = i;
                break;
            }
            map.put(arr[i], i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {7, 1, 0, 2048, 2055};
        int target = 2049;
        System.out.println(Arrays.toString(twoSum(nums, target)));
    }
}
