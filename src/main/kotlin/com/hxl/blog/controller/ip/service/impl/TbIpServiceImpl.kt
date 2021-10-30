package com.hxl.blog.controller.ip.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.ip.mapper.TbIpMapper
import com.hxl.blog.controller.ip.entity.TbIp
import com.hxl.blog.controller.ip.service.ITbIpService
import com.hxl.blog.utils.IpUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
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
    override fun addIpRecord(ip: String?) {
        var city = "";
        var province = "";
        ip?.let {
            var ipInfo = IpUtils.getIpInfo(ip)
            ipInfo?.let {
                if (it.startsWith("[")) {
                    var split = ipInfo
                        .removePrefix("[")
                        .removeSuffix("]")
                        .replace("\"", "")
                        .split(",")
                    city = split[1]
                    province = split[2]
                }
            }
        }
        var newIp = ip ?: "未知IP"
        var tbIp = TbIp().apply {
            this.ipCity = city
            this.ipProvince = province
            this.ipAddress = newIp
            this.createDate = LocalDateTime.now()
        }
        ipMapper.insert(tbIp)

    }

}