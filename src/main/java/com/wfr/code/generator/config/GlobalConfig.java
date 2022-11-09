package com.wfr.code.generator.config;

/**
 * 全局配置
 *
 * @author wangfarui
 * @since 2022/8/24
 */
public class GlobalConfig {

    /**
     * 输出目录
     * <p>format: /a/b/c or C:\a\b\c
     */
    private String outputDir;

    /**
     * 目录分隔符
     * <p>默认为windows分隔符, linux需要设置为/</p>
     */
    private String dirSeparator = "\\";

    /**
     * java文件包名
     * <p>format: a.b.c
     * <p>为空时, 默认使用 outputDir 作为包名
     */
    private String packageName;

    /**
     * 作者
     */
    private String author;

    public String getOutputDir() {
        return outputDir;
    }

    public String getDirSeparator() {
        return dirSeparator;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getAuthor() {
        return author;
    }

    public static Builder toBuilder() {
        return new Builder();
    }

    /**
     * 全局配置构建者
     */
    public static class Builder implements ConfigBuilder<GlobalConfig> {

        private GlobalConfig globalConfig = new GlobalConfig();

        public Builder setGlobalConfig(GlobalConfig globalConfig) {
            this.globalConfig = globalConfig;
            return this;
        }

        public Builder setOutputDir(String outputDir) {
            this.globalConfig.outputDir = outputDir;
            return this;
        }

        public Builder setDirSeparator(String dirSeparator) {
            this.globalConfig.dirSeparator = dirSeparator;
            return this;
        }

        public Builder setPackageName(String packageName) {
            this.globalConfig.packageName = packageName;
            return this;
        }

        public Builder setAuthor(String author) {
            this.globalConfig.author = author;
            return this;
        }

        @Override
        public GlobalConfig build() {
            return this.globalConfig;
        }
    }

}
