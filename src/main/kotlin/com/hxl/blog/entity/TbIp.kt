package com.hxl.blog.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableName
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