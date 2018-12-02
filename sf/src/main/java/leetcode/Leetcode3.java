package leetcode;

import java.util.HashMap;
import java.util.HashSet;

public class Leetcode3 {
    /**
     * Given a string, find the length of the longest substring without repeating characters.
     *
     * Example 1:
     *
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Example 2:
     *
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     *
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */

    /**
     * 一个子串不可能出现重复的字符，那么我们可以用两个指针，一个指针用来遍历字符串，两个指针之间保持无重复字符，那么两个指针之间的长度即最大子串的长度。
     * 当发现有重复的字符时，另一个指针指向这个字符上一次出现的位置的下一个字符，继续遍历，直到找到最长子串
     * @param s
     * @return
     */
    public static int longestSubstringWithoutRepeatingCharacters(String s) {
        if (null == s || s.length() < 1) {
            return 0;
        }
        int res = 0;
        HashMap<Character, Integer> map = new HashMap<>(s.length());
        //i是遍历的指针 j是不重复元素的起点指针
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            res = Math.max(res, i - j + 1);

        }
        return res;
    }




    public static void main(String[] args) {
        String s = "qrsvbspk";
        System.out.println(longestSubstringWithoutRepeatingCharacters(s));
    }
}
