package com.wfr.code.generator.config;

import com.wfr.code.generator.utils.StringUtils;

/**
 * 数据库配置
 *
 * @author wangfarui
 * @since 2022/8/23
 */
public class DbConfig {

    /**
     * 数据库连接地址
     */
    private String url;

    /**
     * 数据库类型
     * <p>默认类型为: MySQL</p>
     */
    private DbType dbType = DbType.MYSQL;

    /**
     * 数据库Host
     */
    private String host = "localhost";

    /**
     * 数据库端口
     */
    private String port = "3306";

    /**
     * 数据库名称
     */
    private String database;

    /**
     * 数据库登录用户
     */
    private String username = "root";

    /**
     * 数据库登录密码
     */
    private String password = "root";

    public String getUrl() {
        if (StringUtils.isNotBlank(this.url)) {
            return this.url;
        }
        this.url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?serverTimezone=Asia/Shanghai";
        return this.url;
    }

    public DbType getDbType() {
        return dbType;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static Builder toBuilder() {
        return new Builder();
    }

    /**
     * 数据库配置构建者
     */
    public static class Builder implements ConfigBuilder<DbConfig> {

        /**
         * 数据库配置
         */
        private DbConfig dbConfig = new DbConfig();

        public Builder setDbConfig(DbConfig dbConfig) {
            this.dbConfig = dbConfig;
            return this;
        }

        public Builder setUrl(String url) {
            this.dbConfig.url = url;
            return this;
        }

        public Builder setDbType(DbType dbType) {
            this.dbConfig.dbType = dbType;
            return this;
        }

        public Builder setHost(String host) {
            this.dbConfig.host = host;
            return this;
        }

        public Builder setPort(String port) {
            this.dbConfig.port = port;
            return this;
        }

        public Builder setDatabase(String database) {
            this.dbConfig.database = database;
            return this;
        }

        public Builder setUsername(String username) {
            this.dbConfig.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.dbConfig.password = password;
            return this;
        }

        @Override
        public DbConfig build() {
            return this.dbConfig;
        }
    }
}
