package com.hxl.blog.controller.ip.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.hxl.blog.controller.ip.entity.TbIp
import com.hxl.blog.controller.ip.mapper.TbIpMapper
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
    override fun addIpRecord(ip: String) {
        var city = "未知"
        var province = "未知"
        val ipInfo = IpUtils.getIpInfo(ip)
        ipInfo?.let {
            val  objectMapper = ObjectMapper()
            val javaType: JavaType = objectMapper.typeFactory.constructMapType(HashMap::class.java, String::class.java, String::class.java)
            val result :Map<String,String> = objectMapper.readValue(ipInfo,javaType) as Map<String,String>
            city=result.getOrDefault("city","未知")
            province=result.getOrDefault("prov","未知")
        }
        val tbIp = TbIp().apply {
            this.ipCity = city
            this.ipProvince = province
            this.ipAddress = ip
            this.createDate = LocalDateTime.now()
        }
        ipMapper.insert(tbIp)

    }

}