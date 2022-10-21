package com.hxl.blog.config

enum class SysKeyEnum(var value: String, var default: String) {
    SYS_LOGIN_PASSWD("sys_login_passwd", "123456"),
    SYS_BLOG_TITLE("sys_blog_title", "HouXinLin"),
    BLOG_WELCOME_TEXT("blog_welcome_text","a")
}