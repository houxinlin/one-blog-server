import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/db_blog", "root", "hxl..")
                .globalConfig(builder -> {
                    builder.author("hxl") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/home/HouXinLin/project/java/blog/src/main/kotlin/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.hxl.blog.controller") // 设置父包名
                            .moduleName("ip") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "/home/HouXinLin/project/java/blog/src/main/resources/mybatis-mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tb_ip") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
