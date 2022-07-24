package com.hxl.blog.config

import com.hxl.blog.controller.blog.entity.TbBlog
import com.hxl.blog.controller.blog.service.impl.TbBlogServiceImpl
import com.hxl.blog.es.EsBlogRepository
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.PutMappingRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import javax.annotation.Resource

@Configuration
class ElasticsearchInit : CommandLineRunner {
    @Resource
    lateinit var esBlogRepository: EsBlogRepository

    @Autowired
    lateinit var tbBlogServiceImpl: TbBlogServiceImpl

    @Autowired
    lateinit var restHighLevelClient: RestHighLevelClient

    override fun run(vararg args: String?) {
        val list = tbBlogServiceImpl.list() as List<TbBlog>
        val putMappingRequest = PutMappingRequest("tb_blog")
        putMappingRequest.source(createMappingProperties())
        restHighLevelClient.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT)
        list.forEach { it.suggest = arrayOf(it.blogTitle.toString()) }
        esBlogRepository.saveAll(list)
        println("es完成"+list.size)
    }

    fun createProperties(value: String): Map<String, Any> {
        val result: MutableMap<String, Any> = HashMap()
        result["type"] = value
        return result
    }

    fun createMappingProperties(): Map<String, *>? {
        val mapping = HashMap<String, Any>()
        val properties = HashMap<String, Any>()
        properties["suggest"] = createProperties("completion")
        mapping["properties"] = properties
        return mapping
    }
}