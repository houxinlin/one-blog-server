package com.hxl.blog.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.util.FileSystemUtils
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
class SysInitConfig : CommandLineRunner {
    override fun run(vararg args: String?) {
        unzip("av")
        unzip("bck")
    }

    fun unzip(name: String) {
        val av = Paths.get(WebConfig.STATIC_PATH, name)
        val avBytes = ClassPathResource("/static/${name}").inputStream.readBytes()
        if (Files.notExists(av)) Files.write(av, avBytes)
    }
}