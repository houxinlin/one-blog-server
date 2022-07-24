package com.hxl.blog.es

import com.hxl.blog.controller.blog.entity.TbBlog
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface EsBlogRepository: ElasticsearchRepository<TbBlog,Long> {
}