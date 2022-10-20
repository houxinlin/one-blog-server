package com.hxl.blog.config

import com.hxl.blog.interceptor.AuthInterceptor
import org.slf4j.LoggerFactory
import org.springframework.boot.system.ApplicationHome
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Files
import java.nio.file.Paths


@Configuration
class WebConfig : WebMvcConfigurer {
   private val log =LoggerFactory.getLogger(WebConfig::class.java)
    companion object{
         val STATIC_PATH =ApplicationHome().dir.absolutePath+"/one-blog-static/"
    }
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)
        if (Files.notExists(Paths.get(STATIC_PATH))) Files.createDirectory(Paths.get(STATIC_PATH))
        val local ="file:$STATIC_PATH"
        log.info("add $local to resource handler")
        registry.addResourceHandler("/static/**").addResourceLocations(local)
    }
    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(AuthInterceptor())
            .addPathPatterns("/api/admin/**")
            .excludePathPatterns("/api/admin/login")
    }
}