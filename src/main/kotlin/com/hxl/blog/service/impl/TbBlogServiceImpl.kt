package com.hxl.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.mapper.TbBlogMapper
import com.hxl.blog.entity.TbBlog
import com.hxl.blog.service.ITbBlogService
import com.hxl.blog.mapper.ITbClassifyService
import com.hxl.blog.mapper.TbIpMapper
import com.hxl.blog.entity.Software
import com.hxl.blog.entity.SoftwareType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.function.Function
import java.util.stream.Collectors
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

    @Autowired
    lateinit var blogMapper: TbBlogMapper
    private lateinit var cityTopCache: Map<String, Int>

    @PostConstruct
    fun init() {
        this.cityTopCache = loadCityTop()
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

    private fun loadCityTop(): MutableMap<String, Int> {
        val result = linkedMapOf<String, Int>()
        //select  *  ,count(id)  from tb_ip  where ip_province!=""  group by ip_province   order by count(id) desc   limit 10
        ipMapper.getCityTop().forEach {
            result[it["ip_province"].toString()] = it["c"].toString().toInt()
        }
        return result
    }

    override fun listSoftware(): MutableMap<String, List<Software>> {
        return blogMapper.listSoftware().stream().collect(Collectors.toMap({ it.typeName }, { it.softwareList!! }))
    }
}