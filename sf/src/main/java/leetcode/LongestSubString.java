package leetcode;

/**
 * <p>
 * LongestSubString 最长回文串
 * </p>
 *
 * @author houcb
 * @since 2019-07-22 19:07
 */
public class LongestSubString {

    public static void main(String[] args) {
        String s = "madam";
        System.out.println(m1(s));
        System.out.println(m2(s));
        System.out.println(m3(s));
    }

    /**
     * 暴力解法 循环
     *
     * @param s
     * @return
     */
    public static String m1(String s) {
        if (null == s || s.length() < 2) {
            return s;
        }

        int start = 0, end = 0;
        int temp1, temp2;
        for (int i = 0, len = s.length(); i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                temp1 = i;
                temp2 = j;
                // 如果 1 - 5 会出现temp1 = temp2 =3的情况 所以判断的时候需要 temp1 >= temp2
                // 如果 1 - 4 会出现temp1 = 3 temp2 =2的情况 所以判断的时候需要 temp1 >= temp2
                while (temp1 < temp2 && s.charAt(temp1) == s.charAt(temp2)) {
                    temp1++;
                    temp2--;
                }
                if (temp1 >= temp2 && j - i + 1 > end - start + 1) {
                    start = i;
                    end = j;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * 动态规划
     * <p>
     * 1、定义二维数组存储dp的结果值
     * 2、单个字符（起点终点索引相同）全部为true
     * 3、两个字符如果字符相同为true（注意数组不要越界）
     * 4、依次循环三个字符、四个字符......
     * 5、有起点索引 i，有子串长度 k 则可以得到终点索引 j （同样注意数组越界问题）
     * 6、比较回文子串长度与保存的result长度
     *
     * @param s
     * @return
     */
    public static String m2(String s) {
        // dp[i][j] = dp[i+1][j-1] && s.charAt(i+1) == s.charAt(j-1)
        if (null == s || s.length() < 2) {
            return s;
        }
        int len = s.length();
        String res = "";
        boolean[][] dp = new boolean[len][len];
        // 1 单个字符串是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        // 2 两个相邻字符串如果相同 则为回文串 注意数组边界
        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (dp[i][i + 1]) {
                res = s.substring(i, i + 1 + 1);
            }
        }
        // 3 一次循环三个字符串 四个字符串
        for (int i = 3; i <= len; i++) {
            for (int j = 0; j + i <= len; j++) {    // 4 j为起点下标 i为字符串长度
                // 5 l为终点下标 = j + i - 1
                int l = i + j - 1;
                dp[j][l] = dp[j + 1][l - 1] && s.charAt(j) == s.charAt(l);
                if (dp[j][l] && (l - j + 1) > res.length()) {
                    res = s.substring(j, l + 1);
                }
            }
        }
        return res;

    }

    public static String m3(String s) {
        String res = "";
        int len = s.length();
        boolean[][] dp = new boolean[len][len];

        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        for (int i = 0; i < len - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (dp[i][i + 1]) {
                res = s.substring(i, i + 1 + 1);
            }
        }

        for (int k = 3; k <= len; k++) {
            for (int i = 0; i + k <= len; i++) {
                int j = i + k - 1;
                dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                if (dp[i][j] && j - i + 1 > res.length()) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;

    }
}
