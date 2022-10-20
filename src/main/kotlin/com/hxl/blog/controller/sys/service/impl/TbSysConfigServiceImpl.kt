package com.hxl.blog.controller.sys.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.hxl.blog.controller.sys.entity.TbSysConfig
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.sys.mapper.TbSysConfigMapper
import com.hxl.blog.controller.sys.service.ITbSysConfigService
import com.baomidou.mybatisplus.extension.service.IService
import com.hxl.blog.config.SysKeyEnum
import com.hxl.blog.vo.LoginVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping

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
class TbSysConfigServiceImpl : ServiceImpl<TbSysConfigMapper?, TbSysConfig?>(), ITbSysConfigService {
    @Autowired
    lateinit var sysConfigDao: TbSysConfigMapper;

    override fun login(loginVO: LoginVO): Any {
        val sysConfig = selectKey(SysKeyEnum.SYS_LOGIN_PASSWD.value)
        return sysConfig?.sysValue.equals(loginVO.passwd);
    }

    fun selectKey(key: String): TbSysConfig? {
        return sysConfigDao.selectOne(QueryWrapper<TbSysConfig>().eq("sys_key", key))
    }
}