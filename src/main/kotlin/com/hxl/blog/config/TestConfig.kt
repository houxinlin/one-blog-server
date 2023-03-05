package com.hxl.blog.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping

@Configuration
class TestConfig {
    @Scheduled(fixedRate = 20000)
    fun testScheduled2(){
        println("testScheduled2")
    }

    @Scheduled(fixedRate = 30000)
    fun testScheduled1(){
        println("testScheduled1")
    }
}