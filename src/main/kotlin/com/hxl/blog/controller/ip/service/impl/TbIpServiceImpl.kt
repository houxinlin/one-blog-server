package com.hxl.blog.controller.ip.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.ip.mapper.TbIpMapper
import com.hxl.blog.controller.ip.entity.TbIp
import com.hxl.blog.controller.ip.service.ITbIpService
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.hxl.blog.utils.IpUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime

/**
 *
 *
 * 服务实现类
 *
 *
 * @author hxl
 * @since 2021-10-27
 */
@Service
class TbIpServiceImpl : ServiceImpl<TbIpMapper?, TbIp?>(), ITbIpService {
    @Autowired
    lateinit var ipMapper: TbIpMapper

    @Async
    override fun add(ip: String) {
        var ipInfo = IpUtils.getIpInfo(ip)
        ipInfo?.let {
            if (it.startsWith("[")) {
                var split = ipInfo
                    .removePrefix("[")
                    .removeSuffix("]")
                    .replace("\"", "")
                    .split(",")
                var tbIp = TbIp().apply {
                    ipAddress = ip
                    ipCity = split[1]
                    ipProvince = split[2]
                    createDate= LocalDateTime.now()
                }
                ipMapper.insert(tbIp)
            }
        }


    }
}