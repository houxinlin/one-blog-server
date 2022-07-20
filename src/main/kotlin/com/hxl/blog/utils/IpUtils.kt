package com.hxl.blog.utils

import java.net.URL

object IpUtils {
    private const val IP_QUERY = "https://www.vnet.fun/getRegion?ip="
    fun getIpInfo(ip: String): String? {
        return URL("${IP_QUERY}${ip}").readText()
    }
}