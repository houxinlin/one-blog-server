package com.hxl.blog.controller.classify.controller

import com.hxl.blog.controller.classify.service.ITbClassifyService
import com.hxl.blog.utils.ResultUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 *
 * 前端控制器
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@RequestMapping("/api/classify")
@RestController()
class TbClassifyController {
    @Autowired
    lateinit var classifyService: ITbClassifyService

    @GetMapping("listClassify")
    fun getClassify(): Any {
        return ResultUtils.success(classifyService.list(), 0);
    }
}