package com.hxl.blog.utils

class KotlinExtend {
}

fun String.joinLine():String {
    val result = StringBuffer()
    for (i in this.toCharArray().indices) {
        if (this[i] in 'A'..'Z') {
            result.append("_${this[i]}")
        } else {
            result.append(this[i])
        }
    }
    return result.toString().lowercase()
}