package com.hxl.blog.controller.classify.controller

import com.hxl.blog.controller.classify.service.ITbClassifyService
import com.hxl.blog.utils.ResultUtils
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

/**
 *
 *
 * 前端控制器
 *
 *
 * @author hxl
 * @since 2021-10-22
 */
@RequestMapping("/api/classify")
@RestController
class TbClassifyController {
    @Autowired
    lateinit var classifyService: ITbClassifyService

    @Autowired
    lateinit var restHighLevelClient: RestHighLevelClient
    @GetMapping("listClassify")
    fun getClassify(): Any {
        val  list = classifyService.list()

        val searchSourceBuilder = SearchSourceBuilder()
        val searchRequest = SearchRequest("tb_blog")

        val termsAggregationBuilder = AggregationBuilders.terms("group_by_classifyId_count").field("classifyId")
        searchSourceBuilder.aggregation(termsAggregationBuilder)
        searchRequest.source(searchSourceBuilder)

        val search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT)
        val aggregations = search.aggregations
        val group = aggregations.get<ParsedStringTerms>("group_by_classifyId_count")
        val buckets = group.buckets
        val groupCount = buckets.stream().collect(Collectors.toMap({ obj: Terms.Bucket -> obj.key }) { obj:Terms.Bucket->obj.docCount })
        return ResultUtils.success(mutableMapOf("list" to list,"group_count" to groupCount), 0);
    }
}