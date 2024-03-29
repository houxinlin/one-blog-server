package com.hxl.blog.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.hxl.blog.entity.Software
import com.hxl.blog.entity.SoftwareType
import com.hxl.blog.entity.TbBlog
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * Mapper 接口
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@Mapper
interface TbBlogMapper : BaseMapper<TbBlog?>{
    fun listSoftware(): List<SoftwareType>
}