package com.hxl.blog.controller.sys.mapper

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.hxl.blog.controller.sys.entity.TbSysConfig
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.sys.mapper.TbSysConfigMapper
import com.hxl.blog.controller.sys.service.ITbSysConfigService
import com.baomidou.mybatisplus.extension.service.IService
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
interface TbSysConfigMapper : BaseMapper<TbSysConfig?>