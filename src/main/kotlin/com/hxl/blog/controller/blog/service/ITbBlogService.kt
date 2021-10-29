package com.hxl.blog.controller.blog.service

import com.baomidou.mybatisplus.extension.service.IService
import com.hxl.blog.controller.blog.entity.TbBlog

/**
 *
 *
 * 服务类
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
interface ITbBlogService : IService<TbBlog?> {
     fun listArticleCountByType(): Map<String,Long>
}