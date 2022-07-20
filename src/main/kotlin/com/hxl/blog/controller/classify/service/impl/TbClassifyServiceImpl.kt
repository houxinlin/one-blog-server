package com.hxl.blog.controller.classify.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.hxl.blog.controller.classify.mapper.TbClassifyMapper
import com.hxl.blog.controller.classify.entity.TbClassify
import com.hxl.blog.controller.classify.service.ITbClassifyService
import org.springframework.stereotype.Service

/**
 *
 *
 * 服务实现类
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@Service
class TbClassifyServiceImpl : ServiceImpl<TbClassifyMapper?, TbClassify?>(), ITbClassifyService