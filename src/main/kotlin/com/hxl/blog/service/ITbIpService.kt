package com.hxl.blog.service

import com.hxl.blog.entity.TbIp
import com.baomidou.mybatisplus.extension.service.IService

/**
 *
 *
 * 服务类
 *
 *
 * @author hxl
 * @since 2021-10-27
 */
interface ITbIpService : IService<TbIp?> {
    fun addIpRecord(ip: String)
}