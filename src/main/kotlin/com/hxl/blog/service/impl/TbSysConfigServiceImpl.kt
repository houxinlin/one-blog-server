package com.hxl.blog.service.impl

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.hxl.blog.entity.TbSysConfig
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.mapper.TbSysConfigMapper
import com.hxl.blog.service.ITbSysConfigService
import com.hxl.blog.config.SysKeyEnum
import com.hxl.blog.vo.LoginVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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