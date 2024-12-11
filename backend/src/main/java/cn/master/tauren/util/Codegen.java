package cn.master.tauren.util;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDate;

/**
 * @author Created by 11's papa on 12/02/2024
 **/
public class Codegen {
    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://172.16.2.10:3306/tauren?useInformationSchema=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("3eCrAw8Ba6Tg");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.getPackageConfig().setBasePackage("cn.master.tauren");
        globalConfig.setSourceDir(System.getProperty("user.dir") + "/backend/src/main/java");

        //设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
                .setTablePrefix("tb_")
                .setGenerateTable("tb_precipitation");

        //设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setWithLombok(true)
                .setJdkVersion(21);

        //设置生成 mapper
        globalConfig.enableMapper();
        globalConfig.enableService();
        globalConfig.enableServiceImpl();


        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("create_time");
        columnConfig.setOnInsertValue("now()");
        globalConfig.setColumnConfig("tb_precipitation", columnConfig);
        ColumnConfig updateDateConfig = new ColumnConfig();
        updateDateConfig.setColumnName("update_time");
        updateDateConfig.setOnInsertValue("now()");
        updateDateConfig.setOnUpdateValue("now()");
        globalConfig.setColumnConfig("tb_precipitation", updateDateConfig);
        globalConfig.getJavadocConfig().setAuthor("11's papa").setSince("1.0.0 " + LocalDate.now());
        return globalConfig;
    }
}
