package com.hxl.blog.config

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter


@Configuration
class LocalDateTimeSerializerConfig{
    private val pattern: String = "yyyy-MM-dd HH:mm:ss"

    @Bean
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer? {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            val dateFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            builder.deserializers(LocalDateDeserializer(dateFormatter))
            builder.deserializers(LocalDateTimeDeserializer(dateTimeFormatter))
            builder.serializers(LocalDateSerializer(dateFormatter))
            builder.serializers(LocalDateTimeSerializer(dateTimeFormatter))
        }
    }
}