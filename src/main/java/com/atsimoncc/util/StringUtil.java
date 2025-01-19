package com.atsimoncc.util;

/**
 * ClassName: StringUtil
 * Package: com.atsimoncc.util
 * Description:
 */
public class StringUtil {
    // 判斷字串是否為 null 或者空字串
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
