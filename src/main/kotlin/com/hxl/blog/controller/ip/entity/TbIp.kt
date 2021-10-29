package com.hxl.blog.controller.ip.entity

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.ip.mapper.TbIpMapper
import com.hxl.blog.controller.ip.entity.TbIp
import com.hxl.blog.controller.ip.service.ITbIpService
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableName
import org.springframework.web.bind.annotation.RequestMapping
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 *
 *
 *
 * @author hxl
 * @since 2021-10-27
 */
@TableName("tb_ip")
class TbIp : Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    var ipAddress: String? = null
    var ipCity: String? = null
    var ipProvince: String? = null
    var createDate: LocalDateTime? = null
    override fun toString(): String {
        return "TbIp{" +
                "id=" + id +
                ", ipAddress=" + ipAddress +
                ", ipCity=" + ipCity +
                ", ipProvince=" + ipProvince +
                "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}