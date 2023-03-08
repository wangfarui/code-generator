package com.wfr.code.generator.config;

/**
 * 文件保留策略
 *
 * @author Wray
 * @since 2023/3/8
 */
public enum FileStrategy {
    /**
     * 新建, 历史文件所有删除
     */
    NEW,
    /**
     * 追加, 出现重复文件时会报错
     */
    APPEND,
    /**
     * 覆盖，出现重复文件时会替换
     */
    COVER
}
