package com.wfr.code.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.wfr.code.generator.config.DbConfig;
import com.wfr.code.generator.config.DbType;
import com.wfr.code.generator.config.GlobalConfig;
import com.wfr.code.generator.utils.StringUtils;
import com.wfr.code.generator.utils.SystemUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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

    public static void generate(DbConfig dbConfig, GlobalConfig globalConfig) {

        // 输出目录
        final String outDir;
        // mybatis Mapper输出目录
        final String mapperOutDir;
        // 父包名
        final String packageName;
        // 输出文件的路径
        final Map<OutputFile, String> pathInfoMap = new HashMap<>();

        if (StringUtils.isBlank(globalConfig.getOutputDir())) {
            outDir = CURRENT_CLASS.getResource("").getPath();
        } else {
            outDir = globalConfig.getOutputDir();
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
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
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
        String OUT_DIR = "D:\\workspaces\\wfr\\code-generator\\src\\main\\java\\com\\wfr\\code\\generator\\tmp";

        removeDir(new File(OUT_DIR));
//        removeDir(new File(MAPPER_OUT_DIR));

        DbConfig dbConfig = DbConfig.toBuilder()
                .setDbType(DbType.MYSQL)
                .setDatabase("basic_platform")
                .setHost("localhost")
                .setPort("3307")
                .setUsername("your")
                .setPassword("your")
                .build();

        GlobalConfig globalConfig = GlobalConfig.toBuilder()
                .setAuthor("wangfarui")
                .setOutputDir(OUT_DIR)
                .build();

        generate(dbConfig, globalConfig);
    }
}
