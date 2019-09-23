package io.sansam.utils;

/**
 * <p>
 * CommonUtils
 * </p>
 *
 * @author houcb
 * @since 2019-09-11 16:01
 */
public class CommonUtils {

    public static boolean isBlank(String cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
