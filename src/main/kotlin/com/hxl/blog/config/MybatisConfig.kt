package com.hxl.blog.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MybatisConfig {
    companion object {
        const val PAGE_MAX_SIZE: Long = 10;
    }
    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor? {
        val interceptor = MybatisPlusInterceptor()
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL))
        return interceptor
    }

}