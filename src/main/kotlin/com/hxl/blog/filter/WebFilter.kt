package com.hxl.blog.filter

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebFilter(urlPatterns = ["*"])
class WebFilter:Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        var servletRequest=request as HttpServletRequest
        val servletResponse=(response as HttpServletResponse)
        servletResponse.addHeader("Access-Control-Allow-Headers","x-requested-with,content-type")
        servletResponse.addHeader("Access-Control-Allow-Methods","GET,POST,PUT,POST")
        servletResponse.addHeader("Access-Control-Allow-Origin",request.getHeader("Origin"))
        servletResponse.addHeader("Access-Control-Allow-Credentials","true")
        chain?.doFilter(request,response)
    }
}