package com.hxl.blog.controller.blog.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.elasticsearch.annotations.CompletionField
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.core.completion.Completion
import java.io.Serializable
import java.time.LocalDateTime

/**
 *
 *
 *
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@Document(indexName = "tb_blog")
@TableName("tb_blog")
class TbBlog : Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null

    /**
     * 标题
     */

    var blogTitle: String? = null

    @TableField(exist = false)
     var suggest: Array<String> = arrayOf("")

    /**
     * md内容
     */
    var markdownContent: String? = null

    /**
     * 时间
     */
    var createDate: LocalDateTime? = null

    @Field(type = FieldType.Keyword)
    var classifyId: String? = null

    /**
     * 浏览总数
     */
    var watchCount: Int = 0

    /**
     * 描述信息
     */
    var blogDescribe: String? = null

    /**
     * tags
     */
    @Field(fielddata = true, type = FieldType.Text)
    var tags: String? = null
    override fun toString(): String {
        return "TbBlog{" +
                "id=" + id +
                ", blogTitle=" + blogTitle +
                ", markdownContent=" + markdownContent +
                ", createDate=" + createDate +
                ", classifyId=" + classifyId +
                ", watchCount=" + watchCount +
                ", blogDescribe=" + blogDescribe +
                ", tags=" + tags +
                "}"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}