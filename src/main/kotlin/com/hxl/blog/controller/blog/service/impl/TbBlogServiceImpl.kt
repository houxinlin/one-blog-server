package com.hxl.blog.controller.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.blog.mapper.TbBlogMapper
import com.hxl.blog.controller.blog.entity.TbBlog
import com.hxl.blog.controller.blog.service.ITbBlogService
import com.hxl.blog.controller.classify.service.ITbClassifyService
import com.hxl.blog.controller.ip.mapper.TbIpMapper
import com.hxl.blog.controller.ip.service.ITbIpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * 服务实现类
 * @author hxl
 * @since 2021-10-22
 */
@Service
class TbBlogServiceImpl : ServiceImpl<TbBlogMapper?, TbBlog?>(), ITbBlogService {
    @Autowired
    lateinit var blogService: ITbBlogService;

    @Autowired
    lateinit var ipMapper: TbIpMapper

    @Autowired
    lateinit var classifyService: ITbClassifyService;

    private lateinit var cityTopCache :Map<String,Int>
    @PostConstruct
    fun init(){
        this.cityTopCache = loadCitytop()
    }
    override fun listArticleCountByType(): Map<String, Long> {
        val result = mutableMapOf<String, Long>()
        for (item in classifyService.list()) {
            result[item!!.classify!!] = blogService.count(QueryWrapper<TbBlog>().eq("classify_id", item.classify))
        }
        return result
    }

    override fun getCityTop(): Map<String, Int> {
        return cityTopCache
    }

    private fun loadCitytop(): MutableMap<String, Int> {
        val result = linkedMapOf<String, Int>()
        //select  *  ,count(id)  from tb_ip  where ip_province!=""  group by ip_province   order by count(id) desc   limit 10
        ipMapper.getCityTop().forEach {
            result[it["ip_province"].toString()]=it["c"].toString().toInt()
        }
        return result
    }
}