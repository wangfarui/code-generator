package com.wfr.code.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wfr.code.generator.config.*;
import com.wfr.code.generator.utils.StringUtils;
import com.wfr.code.generator.utils.SystemUtils;
import org.springframework.lang.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 代码自动生成器
 *
 * @author wangfarui
 * @since 2022/8/22
 */
public abstract class CodeGenerator {

    static Class<?> CURRENT_CLASS = CodeGenerator.class;

    private static final String MAPPER_URI;

    static {
        String separator = SystemUtils.getPathSeparator();
        MAPPER_URI = "resources" + separator + "mapper";
    }

    public static void generate(@NonNull DbConfig dbConfig, @NonNull GlobalConfig globalConfig, @NonNull TableConfig tableConfig) {
        generate(dbConfig, globalConfig, tableConfig, FileStrategy.COVER);
    }

    public static void generate(@NonNull DbConfig dbConfig, @NonNull GlobalConfig globalConfig, @NonNull TableConfig tableConfig, FileStrategy fileStrategy) {

        // 输出目录
        final String outDir;
        // mybatis Mapper输出目录
        final String mapperOutDir;
        // 父包名
        final String packageName;
        // 输出文件的路径
        final Map<OutputFile, String> pathInfoMap = new HashMap<>();

        if (StringUtils.isBlank(globalConfig.getOutputDir())) {
            outDir = Objects.requireNonNull(CURRENT_CLASS.getResource("")).getPath();
        } else {
            outDir = globalConfig.getOutputDir();
            if (FileStrategy.NEW.equals(fileStrategy)) {
                formatFile(new File(outDir));
            }
        }

        int i = outDir.indexOf("java");
        if (i < 0) {
            throw new IllegalArgumentException("OUT_DIR 目录地址错误");
        }
        mapperOutDir = outDir.substring(0, i) + MAPPER_URI;

        if (StringUtils.isBlank(globalConfig.getPackageName())) {
            packageName = outDir.substring(i + 5).replace(SystemUtils.getPathSeparatorChar(), '.');
        } else {
            packageName = globalConfig.getPackageName();
        }

        String dirSeparator = globalConfig.getDirSeparator();

        pathInfoMap.put(OutputFile.entity, outDir + dirSeparator + "entity");
        pathInfoMap.put(OutputFile.mapper, outDir + dirSeparator + "mapper");
        pathInfoMap.put(OutputFile.xml, mapperOutDir);
        pathInfoMap.put(OutputFile.service, outDir + dirSeparator + "service");
        pathInfoMap.put(OutputFile.serviceImpl, outDir + dirSeparator + "service" + dirSeparator + "impl");
        pathInfoMap.put(OutputFile.controller, outDir + dirSeparator + "controller");

        FastAutoGenerator.create(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword())
                .globalConfig(builder -> {
                    if (StringUtils.isNotBlank(globalConfig.getAuthor())) {
                        builder.author(globalConfig.getAuthor());
                    }
                    builder.disableOpenDir()
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outDir) // 指定输出目录
                            .enableSwagger();
                })
                .packageConfig(builder -> {
                    builder.parent(packageName) // 设置父包名
                            .pathInfo(pathInfoMap); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .disableSerialVersionUID()
                            .enableLombok();
                    builder.controllerBuilder()
                            .enableRestStyle();
                    builder.mapperBuilder()
                            .enableMapperAnnotation();
                    builder.addInclude(tableConfig.getIncludeTable());
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    private static void formatFile(File file) {
        removeDir(file);
        if (!file.isDirectory()) {
            boolean b = file.mkdirs();
            if (!b) {
                throw new IllegalStateException("输出目录异常: " + file.getAbsolutePath());
            }
        }
    }

    private static void removeDir(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    removeDir(f);
                } else {
                    f.delete();
                }
            }
        }
    }

    public static void main(String[] args) {
        String OUT_DIR = "/Users/wangfarui/workspaces/wfr/basic-service-platform/platform-web/src/main/java/com/wfr/basic/service/platform/tmp";

        DbConfig dbConfig = DbConfig.toBuilder()
                .setDbType(DbType.MYSQL)
                .setDatabase("basic_platform_0")
                .setHost("localhost")
                .setPort("3306")
                .setUsername("your")
                .setPassword("your")
                .build();

        GlobalConfig globalConfig = GlobalConfig.toBuilder()
                .setAuthor("wangfarui")
                .setOutputDir(OUT_DIR)
                .build();

        TableConfig tableConfig = TableConfig.toBuilder()
                .addIncludeTable("t_order")
                .build();

        generate(dbConfig, globalConfig, tableConfig);
    }
}
