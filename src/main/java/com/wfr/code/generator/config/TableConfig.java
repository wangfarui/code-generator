package com.wfr.code.generator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 表配置
 *
 * @author Wray
 * @since 2023/3/8
 */
public class TableConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(TableConfig.class);

    /**
     * 包含的表
     * <p>想要生成的表，全量名称匹配</p>
     */
    private List<String> includeTable = new ArrayList<>();

    public List<String> getIncludeTable() {
        return includeTable;
    }

    public void setIncludeTable(List<String> includeTable) {
        if (includeTable == null) {
            LOGGER.warn("includeTable不能为null");
            return;
        }
        this.includeTable = includeTable;
    }

    public void addIncludeTable(String... includeTables) {
        this.includeTable.addAll(Arrays.asList(includeTables));
    }

    public static Builder toBuilder() {
        return new Builder();
    }

    public static class Builder implements ConfigBuilder<TableConfig> {

        private TableConfig tableConfig = new TableConfig();

        public void setTableConfig(TableConfig tableConfig) {
            this.tableConfig = tableConfig;
        }

        public Builder addIncludeTable(String... tables) {
            tableConfig.addIncludeTable(tables);
            return this;
        }

        @Override
        public TableConfig build() {
            return this.tableConfig;
        }
    }
}
