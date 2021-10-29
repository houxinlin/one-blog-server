package com.hxl.blog.utils

class ResultUtils {
    companion object {
        fun success(data: Any?, code: Int): Any {
            return mapOf("data" to data, "code" to code, "msg" to "OK")
        }
    }
}