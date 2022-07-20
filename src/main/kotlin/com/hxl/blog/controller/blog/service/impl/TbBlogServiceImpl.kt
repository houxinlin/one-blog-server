package com.hxl.blog.controller.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.blog.mapper.TbBlogMapper
import com.hxl.blog.controller.blog.entity.TbBlog
import com.hxl.blog.controller.blog.service.ITbBlogService
import com.hxl.blog.controller.classify.service.ITbClassifyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 *
 * 服务实现类
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@Service
class TbBlogServiceImpl : ServiceImpl<TbBlogMapper?, TbBlog?>(), ITbBlogService {
    @Autowired
    lateinit var blogService: ITbBlogService;


    @Autowired
    lateinit var classifyService: ITbClassifyService;

    override fun listArticleCountByType(): Map<String, Long> {
        var result = mutableMapOf<String, Long>()
        for (item in classifyService.list()) {
            result.put(item!!.classify!!, blogService.count(QueryWrapper<TbBlog>().eq("classify_id", item.classify)))
        }
        return result
    }
}