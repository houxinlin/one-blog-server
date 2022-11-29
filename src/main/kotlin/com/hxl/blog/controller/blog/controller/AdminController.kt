package com.hxl.blog.controller.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.hxl.blog.config.SysKeyEnum
import com.hxl.blog.config.WebConfig
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
import com.hxl.blog.utils.joinLine
import com.hxl.blog.vo.LoginVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Paths
import java.time.LocalDateTime
import java.util.UUID
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
    lateinit var classifyService: ITbClassifyService
    @Autowired
    lateinit var ipService: ITbIpService;

    @PostMapping("login")
    fun login(@RequestBody vo: LoginVO, session: HttpSession, response: HttpServletResponse): Any {
        val result = sysConfig.login(vo)
        val id = session.id
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
            val oldBlog = blogService.getById(body)
            oldBlog?.let { with(body) { watchCount = oldBlog.watchCount } }
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

    /**
     * 删除文章
     */
    @PostMapping("deleteArticle")
    fun deleteArticle(@RequestBody body: Map<String, Any>): Any {
        if (body.containsKey("id")) {
            val id = body["id"] as Int
            blogService.removeById(id)
        }
        return ResultUtils.success("OK", 0)
    }

    /**
     * 获取浏览记录
     */
    @GetMapping("getBrowseRecord")
    fun getBrowseRecord(@RequestParam(required = false, value = "page") page: Int): Any {
        val result =   ipService.page(Page(page.toLong(), 50), QueryWrapper<TbIp?>().orderByDesc("id"))
        return ResultUtils.success(result, 0)
    }

    /**
     * 获取系统值
     */
    @GetMapping("getSysConfig")
    fun getSysConfig(): Any {
        return ResultUtils.success(sysConfig.list(), 0)
    }

    /**
     * 设置系统值
     */
    @PostMapping("setSysConfig")
    fun setSysConfig(@RequestBody map: Map<String, Any>): Any {
        map.forEach { (k, v) ->
            sysConfig.update(UpdateWrapper<TbSysConfig>().set("sys_value", v).eq("sys_key", k))
        }
        return ResultUtils.success("OK", 0)
    }
    @PostMapping("restPassword")
    fun restPassword(@RequestBody vo: LoginVO):String{
        if (vo.passwd.length<6) return "密码最少6位"
        sysConfig.update(UpdateWrapper<TbSysConfig>().set("sys_value", vo.passwd).eq("sys_key", SysKeyEnum.SYS_LOGIN_PASSWD))
        return "重置成功"
    }

    @PostMapping("configInfo")
    fun configInfo(@RequestParam(value = "userAvatar", required = false) userAvatar: MultipartFile?,
                   @RequestParam(value = "background", required = false) background: MultipartFile?,
                   @RequestParam map: Map<String, Any>):String{
        if (map.values.find { it.toString().isEmpty() }!=null) return  "无效参数"
        map.forEach { (key, value) ->
            sysConfig.update(UpdateWrapper<TbSysConfig>().set("sys_value", value).eq("sys_key", key.joinLine()))
        }
        userAvatar?.transferTo(Paths.get(WebConfig.STATIC_PATH,"av"))
        background?.transferTo(Paths.get(WebConfig.STATIC_PATH,"bck"))
        return "保存成功"
    }

    /**
     * 分析
     */
    @GetMapping("dashboard")
    fun dashboard(): Any {
        val result = BuilderMap()
            .put("article_num", blogService.listArticleCountByType())
            .put("total_visit", ipService.count())
            .put("blog_count", blogService.count())
            .put("city_top",blogService.getCityTop())
            .put("today_count", ipService.count(QueryWrapper<TbIp?>().apply("DATE_FORMAT(create_date,'%Y-%m-%d')=CURDATE()")))
        return ResultUtils.success(result,0)

    }

    /**
     * 添加文章分类
     */
    @PostMapping("addClassify")
    fun addClassify(@RequestBody classify: TbClassify): Any {
        return ResultUtils.success(classifyService.save(classify), 0);
    }

    /**
     * 删除文章
     */
    @PostMapping("removeClassify")
    fun removeClassify(@RequestBody classify: TbClassify): Any {
        return ResultUtils.success(classifyService.remove(QueryWrapper<TbClassify>().eq("classify", classify.classify)), 0);
    }

    @PutMapping("img")
    fun putImage(@RequestParam("img") multipartFile: MultipartFile):String{
        val name = UUID.randomUUID().toString()
        multipartFile.transferTo(Paths.get(WebConfig.STATIC_PATH,name))
        return name
    }
}