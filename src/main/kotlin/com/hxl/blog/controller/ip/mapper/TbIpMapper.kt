package com.hxl.blog.controller.ip.mapper

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.ip.mapper.TbIpMapper
import com.hxl.blog.controller.ip.entity.TbIp
import com.hxl.blog.controller.ip.service.ITbIpService
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import org.apache.ibatis.annotations.Mapper
import org.springframework.web.bind.annotation.RequestMapping

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

}