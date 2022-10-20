package com.hxl.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.system.ApplicationHome
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@ServletComponentScan
@EnableAsync
class BlogApplication
fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
