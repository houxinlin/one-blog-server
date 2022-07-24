package com.hxl.blog.controller.blog.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO
import com.hxl.blog.config.MybatisConfig
import com.hxl.blog.controller.blog.entity.TbBlog
import com.hxl.blog.controller.blog.service.ITbBlogService
import com.hxl.blog.controller.ip.service.ITbIpService
import com.hxl.blog.utils.ResultUtils
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.elasticsearch.search.suggest.Suggest.Suggestion
import org.elasticsearch.search.suggest.SuggestBuilder
import org.elasticsearch.search.suggest.SuggestBuilders
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

/**
 *
 *
 * 前端控制器
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@RequestMapping("/api/blog")
@RestController()
class BlogController {
    @Autowired
    lateinit var blogService: ITbBlogService;

    @Autowired
    lateinit var restHighLevelClient: RestHighLevelClient

    @Autowired
    lateinit var ipService: ITbIpService;

    @GetMapping("getContent")
    fun getContent(@RequestParam("id") blogId: Int): Any {
        return LocalDateTime.now()
    }

    /**
     * 博客列表
     */
    @GetMapping("list")
    fun list(
        @RequestParam("page") page: Int,
        @RequestParam(value = "type", defaultValue = "") type: String, request: HttpServletRequest
    ): Any {
        request.getHeader("x-real-ip")?.run { ipService.addIpRecord(this) }
        val queryWrapper = QueryWrapper<TbBlog>().orderByDesc("id");

        if ("" != type) queryWrapper.eq("classify_id", type)
        queryWrapper.select(TbBlog::class.java) { t -> t.column != "markdown_content" }
        val list =
            blogService.page(PageDTO(page.toLong(), MybatisConfig.PAGE_MAX_SIZE), queryWrapper)
        return ResultUtils.success(list, 0)
    }

    /**
     * 日记
     */
    @GetMapping("listDiary")
    fun list(): Any {
        val list = blogService.list(QueryWrapper<TbBlog>().eq("classify_id", "日记"))
        return ResultUtils.success(list, 0)
    }

    /**
     * 博客详细
     */
    @GetMapping("detail")
    fun detail(@RequestParam("id") id: Int): Any {
        val blog = blogService.getById(id)
        blog?.let {
            it.watchCount = it.watchCount + 1
            blogService.updateById(blog)
        }
        return ResultUtils.success(blog, 0);
    }

    @GetMapping("autoCompletion")
    fun autoCompletion(@RequestParam("text") text: String): Any {
        val suggestBuilder = SuggestBuilder()
        val suggestion = SuggestBuilders
            .completionSuggestion("suggest").prefix(text)
        suggestBuilder.addSuggestion("suggest", suggestion)
        val request = SearchRequest().source(
            SearchSourceBuilder().suggest(suggestBuilder).fetchSource(
                arrayOf("blogTitle", "id"), arrayOf()
            )
        )
        val searchResponse: SearchResponse =
            restHighLevelClient.search(request, RequestOptions.DEFAULT)
        val suggest =
            searchResponse.suggest.getSuggestion<Suggestion<out Suggestion.Entry<out Suggestion.Entry.Option?>?>>(
                "suggest"
            ).entries
        if (suggest[0]!!.options.size >= 0) return suggest[0]!!.options
        return emptyArray<Void>()
    }

    @GetMapping("search")
    fun search(@RequestParam("q") searchText: String,@RequestParam("from") from :Int): Any {
        val searchRequest = SearchRequest("tb_blog")

        val searchSourceBuilder = SearchSourceBuilder()
        searchSourceBuilder.from(from-1)
        searchSourceBuilder.size(10)

        val boolQueryBuilder = QueryBuilders.boolQuery()
        boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("blogTitle", searchText))
        boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("markdownContent", searchText))
        searchSourceBuilder.query(boolQueryBuilder)
        searchRequest.source(searchSourceBuilder)
        searchSourceBuilder.fetchSource(arrayOf("blogTitle","createDate","id","watchCount"), arrayOf())
        val search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT)

        return search.hits

    }


}