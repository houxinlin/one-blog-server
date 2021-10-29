package com.hxl.blog.config

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter


@Configuration
class LocalDateTimeSerializerConfig{
    private val pattern: String = "yyyy-MM-dd HH:mm:ss"

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer? {
        Jackson2ObjectMapperBuilderCustomizer(){

        }
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.simpleDateFormat(pattern)
            builder.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(pattern)))
            builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)))
        }
    }

}