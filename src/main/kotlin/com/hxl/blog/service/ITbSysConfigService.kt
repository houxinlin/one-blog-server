package com.hxl.blog.service

import com.hxl.blog.entity.TbSysConfig
import com.baomidou.mybatisplus.extension.service.IService
import com.hxl.blog.vo.LoginVO

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