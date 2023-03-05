package com.hxl.blog.mapper

import com.hxl.blog.entity.TbIp
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

/**
 *
 *
 * Mapper 接口
 *
 *
 * @author hxl
 * @since 2021-10-27
 */
@Mapper
interface TbIpMapper : BaseMapper<TbIp?>{

    fun getCityTop():List<Map<String,String>>
}