package com.hxl.blog.entity

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 *
 *
 *
 *
 *
 * @author hxl
 * @since 2021-10-27
 */
@TableName("tb_sys_config")
class TbSysConfig : Serializable {
    var sysKey: String? = null
    var sysValue: String? = null
    override fun toString(): String {
        return "TbSysConfig{" +
                "key=" + sysKey +
                ", value=" + sysValue +
                "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}