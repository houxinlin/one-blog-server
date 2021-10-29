package com.hxl.blog.controller.sys.service

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.hxl.blog.controller.sys.entity.TbSysConfig
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.sys.mapper.TbSysConfigMapper
import com.hxl.blog.controller.sys.service.ITbSysConfigService
import com.baomidou.mybatisplus.extension.service.IService
import com.hxl.blog.vo.LoginVO
import org.springframework.web.bind.annotation.RequestMapping

/**
 *
 *
 * 服务类
 *
 *
 * @author hxl
 * @since 2021-10-27
 */
interface ITbSysConfigService : IService<TbSysConfig?> {
    fun login(loginVO: LoginVO): Any
}