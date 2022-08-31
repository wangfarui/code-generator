package com.wfr.code.generator.utils;

/**
 * 基于 {@link System} 属性的工具类
 *
 * @author wangfarui
 * @since 2022/8/31
 */
public abstract class SystemUtils {

    public static final boolean IS_WIN;

    static {
        IS_WIN = System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * 获取系统目录路径的分隔符
     *
     * @return 系统目录路径的分隔符
     */
    public static String getPathSeparator() {
        return IS_WIN ? "\\" : "/";
    }

    /**
     * 获取系统目录路径的分隔符(字符形式)
     *
     * @return 系统目录路径的分隔符
     */
    public static char getPathSeparatorChar() {
        return IS_WIN ? '\\' : '/';
    }

    /**
     * 获取系统的换行符
     *
     * @return 系统的换行符
     */
    public static String getLineSeparator() {
        return System.lineSeparator();
    }
}
