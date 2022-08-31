package com.wfr.code.generator.utils;

/**
 * {@link String} 工具类
 *
 * @author wangfarui
 * @since 2022/8/30
 */
public abstract class StringUtils {

    public static boolean isBlank(CharSequence cs) {
        if (cs == null) {
            return true;
        }
        int len = cs.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}
