package com.hxl.blog.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 *
 *
 *
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@TableName("tb_classify")
class TbClassify : Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    var classify: String? = null
    var parentId: Int = -1;
    override fun toString(): String {
        return "TbClassify{" +
                "id=" + id +
                ", classify=" + classify +
                ", parentId=" + parentId +
                "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}