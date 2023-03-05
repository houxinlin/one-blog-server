package com.hxl.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.hxl.blog.config.MybatisConfig
import com.hxl.blog.config.SysKeyEnum
import com.hxl.blog.entity.TbBlog
import com.hxl.blog.service.ITbBlogService
import com.hxl.blog.service.ITbSysConfigService
import com.hxl.blog.entity.Software
import com.hxl.blog.service.ITbIpService
import com.hxl.blog.utils.ResultUtils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

/**
 *
 *
 * 前端控制器
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@RequestMapping("/api/blog")
@RestController()
class BlogController {
    @Autowired
    lateinit var blogService: ITbBlogService

    @Autowired
    lateinit var ipService: ITbIpService

    @Autowired
    lateinit var sysConfig: ITbSysConfigService

    @GetMapping("getContent")
    fun getContent(@RequestParam("id") blogId: Int): Any {
        return LocalDateTime.now()
    }

    /**
     * 博客列表
     */
    @GetMapping("list")
    fun list(
        @RequestParam("page") page: Int,
        @RequestParam(value = "type", defaultValue = "") type: String,
        @RequestParam(value = "orderBy", defaultValue = "") orderBy: String,
        request: HttpServletRequest
    ): Any {
        //添加ip记录
        request.getHeader("x-real-ip")?.run { ipService.addIpRecord(this) }
        val queryWrapper = QueryWrapper<TbBlog>()
        //指定分类
        if ("" != type) queryWrapper.eq("classify_id", type)
        //浏览量
        if ("" != orderBy) {
            if ("asc".equals(orderBy, ignoreCase = false)) {
                queryWrapper.orderByAsc("watch_count")
            } else {
                queryWrapper.orderByDesc("watch_count")
            }
        }
        //id降序
        queryWrapper.orderByDesc("id")
        //排除markdown内容
        queryWrapper.select(TbBlog::class.java) { t -> t.column != "markdown_content" }
        //分页查询
        val list = blogService.page(PageDTO(page.toLong(), MybatisConfig.PAGE_MAX_SIZE), queryWrapper)
        return ResultUtils.success(list, 0)
    }

    /**
     * 日记
     */
    @GetMapping("listDiary")
    fun list(): Any {
        val list = blogService.list(QueryWrapper<TbBlog>().eq("classify_id", "日记"))
        return ResultUtils.success(list, 0)
    }

    /**
     * 博客详细
     */
    @GetMapping("detail")
    fun detail(@RequestParam("id") id: Int): Any {
        val blog = blogService.getById(id)
        blog?.let {
            it.watchCount = it.watchCount + 1
            blogService.updateById(blog)
        }
        return ResultUtils.success(blog, 0);
    }

    /**
     * 获取配置
     */
    @GetMapping("getConfigs")
    fun getConfigs(): MutableMap<String, String> {
        return sysConfig.list().filterNot { it!!.sysKey == SysKeyEnum.SYS_LOGIN_PASSWD.value }
            .stream()
            .collect(Collectors.toMap({ it!!.sysKey }, { it!!.sysValue }))
    }

    @GetMapping("listSoftware")
    fun listSoftware(): Map<String, List<Software>> {
        return blogService.listSoftware()
    }

    @GetMapping("test")
    fun test():LocalDate{
        return LocalDate.now()
    }
}