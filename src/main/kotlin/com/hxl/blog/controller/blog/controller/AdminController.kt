package com.hxl.blog.controller.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.hxl.blog.controller.blog.entity.TbBlog
import com.hxl.blog.controller.blog.service.ITbBlogService
import com.hxl.blog.controller.classify.entity.TbClassify
import com.hxl.blog.controller.classify.service.ITbClassifyService
import com.hxl.blog.controller.ip.entity.TbIp
import com.hxl.blog.controller.ip.service.ITbIpService
import com.hxl.blog.controller.sys.entity.TbSysConfig
import com.hxl.blog.controller.sys.service.ITbSysConfigService
import com.hxl.blog.utils.BuilderMap
import com.hxl.blog.utils.ResultUtils
import com.hxl.blog.vo.LoginVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@RequestMapping("/api/admin")
@RestController()
class AdminController {
    @Autowired
    lateinit var sysConfig: ITbSysConfigService;

    @Autowired
    lateinit var blogService: ITbBlogService;

    @Autowired
    lateinit var classifyService: ITbClassifyService;

    @Autowired
    lateinit var ipService: ITbIpService;

    @PostMapping("login")
    fun login(@RequestBody vo: LoginVO, session: HttpSession, response: HttpServletResponse): Any {
        var result = sysConfig.login(vo)
        var id = session.id
        //跨越的话如果不加SameSite=None;Secure，Cookie会无法设置
        response.setHeader("Set-Cookie", "JSESSIONID=${id};SameSite=None;Secure")
        session.setAttribute("login", result)
        return ResultUtils.success(result, 0)
    }


    @PostMapping("add")
    fun add(@RequestBody body: TbBlog): Any {
        /**
         * 如果id不为空，可能是更新博客
         */
        if (body.id != null) {
            var oldBlog = blogService.getById(body)
            oldBlog?.let {
                with(body) {
                    watchCount = oldBlog.watchCount
                }
            }
            return ResultUtils.success(blogService.updateById(body), 0)
        }
        /**
         * 新增
         */
        with(body) {
            createDate = LocalDateTime.now()
            watchCount = 1
        }
        return ResultUtils.success(blogService.save(body), 0)
    }

    @PostMapping("deleteArticle")
    fun deleteArticle(@RequestBody body: Map<String, Any>): Any {
        if (body.containsKey("id")) {
            var id = body.get("id") as Int
            blogService.removeById(id)
        }
        return ResultUtils.success("OK", 0)
    }

    @GetMapping("getBrowseRecord")
    fun getBrowseRecord(@RequestParam(required = false, value = "page") page: Int): Any {
        return ResultUtils.success(
            ipService.page(
                Page(page.toLong(), 50),
                QueryWrapper<TbIp?>().orderByDesc("id")
            ), 0
        )
    }

    @GetMapping("getSysConfig")
    fun getSysConfig(): Any {
        return ResultUtils.success(sysConfig.list(), 0)
    }

    @PostMapping("setSysConfig")
    fun setSysConfig(@RequestBody map: Map<String, Any>): Any {
        map.forEach { (k, v) ->
            sysConfig.update(
                UpdateWrapper<TbSysConfig>()
                    .set("sys_value", v)
                    .eq("sys_key", k)
            )
        }
        return ResultUtils.success("OK", 0)
    }

    @GetMapping("dashboard")
    fun dashboard(): Any {
        return ResultUtils.success(
            BuilderMap()
                .put("article_num", blogService.listArticleCountByType())
                .put("total_visit", ipService.count())
                .put("blog_count", blogService.count())
                .put(
                    "today_count", ipService.count(
                        QueryWrapper<TbIp?>()
                            .apply("DATE_FORMAT(create_date,'%Y-%m-%d')=CURDATE()")
                    )
                ), 0
        )
    }

    @PostMapping("addClassify")
    fun addClassify(@RequestBody classify: TbClassify): Any {
        return ResultUtils.success(classifyService.save(classify), 0);
    }

    @PostMapping("removeClassify")
    fun removeClassify(@RequestBody classify: TbClassify): Any {
        return ResultUtils.success(
            classifyService.remove(QueryWrapper<TbClassify>().eq("classify", classify.classify)), 0
        );
    }
}