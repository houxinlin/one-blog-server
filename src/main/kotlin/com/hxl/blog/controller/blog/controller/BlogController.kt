package com.hxl.blog.controller.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.hxl.blog.config.MybatisConfig
import com.hxl.blog.config.SysKeyEnum
import com.hxl.blog.controller.blog.entity.TbBlog
import com.hxl.blog.controller.blog.service.ITbBlogService
import com.hxl.blog.controller.ip.service.ITbIpService
import com.hxl.blog.controller.sys.service.ITbSysConfigService
import com.hxl.blog.utils.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.function.Predicate
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
    lateinit var blogService: ITbBlogService;

    @Autowired
    lateinit var ipService: ITbIpService;
    @Autowired
    lateinit var sysConfig: ITbSysConfigService;
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
        @RequestParam(value = "type", defaultValue = "") type: String, request: HttpServletRequest
    ): Any {
        request.getHeader("x-real-ip")?.run { ipService.addIpRecord(this)}
        val queryWrapper = QueryWrapper<TbBlog>().orderByDesc("id");

        if ("" != type) queryWrapper.eq("classify_id", type)
        queryWrapper.select(TbBlog::class.java) { t -> t.column != "markdown_content" }
        val list = blogService.page(PageDTO(page.toLong(), MybatisConfig.PAGE_MAX_SIZE), queryWrapper)
        return ResultUtils.success(list, 0)
    }

    /**
     * 日记
     */
    @GetMapping("listDiary")
    fun list(): Any {
        val  list = blogService.list(QueryWrapper<TbBlog>().eq("classify_id", "日记"))
        return ResultUtils.success(list, 0)
    }

    /**
     * 博客详细
     */
    @GetMapping("detail")
    fun detail(@RequestParam("id") id: Int): Any {
        val  blog = blogService.getById(id)
        blog?.let {
            it.watchCount = it.watchCount + 1
            blogService.updateById(blog)
        }
        return ResultUtils.success(blog, 0);
    }
    @GetMapping("getConfigs")
    fun getConfigs():MutableMap<String,String>{
        return sysConfig.list().filterNot { it!!.sysKey== SysKeyEnum.SYS_LOGIN_PASSWD.value }
            .stream()
            .collect(Collectors.toMap({it!!.sysKey},{it!!.sysValue}))
    }

}