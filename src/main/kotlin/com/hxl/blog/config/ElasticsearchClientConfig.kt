package com.hxl.blog.config

import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration

@Configuration
class ElasticsearchClientConfig : AbstractElasticsearchConfiguration() {
    override fun elasticsearchClient(): RestHighLevelClient {
        val clientConfiguration = ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build()
       return RestClients.create(clientConfiguration).rest()
    }
}