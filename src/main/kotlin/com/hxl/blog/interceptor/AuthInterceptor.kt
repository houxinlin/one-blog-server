package com.hxl.blog.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.hxl.blog.utils.ResultUtils
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var login = request.session.getAttribute("login")
        if (login == null || login == false) {
            response.contentType="text/paint;charset=UTF-8"
            response.writer.append(createResponseBody())
            response.writer.flush()
            return false
        }
        return true
    }

    private fun createResponseBody(): String {
        var objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(ResultUtils.success("未登陆", -1000))
    }
}