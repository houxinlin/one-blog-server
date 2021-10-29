package com.hxl.blog.controller.sys.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.hxl.blog.controller.sys.entity.TbSysConfig
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.sys.mapper.TbSysConfigMapper
import com.hxl.blog.controller.sys.service.ITbSysConfigService
import com.baomidou.mybatisplus.extension.service.IService
import org.springframework.web.bind.annotation.RequestMapping
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