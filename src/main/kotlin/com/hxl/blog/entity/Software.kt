package com.hxl.blog.entity

import java.sql.Date
import java.time.LocalDate

class Software {
    var id:Int=0;
    var softwareName:String=""
    var softwareDescribe:String=""
    var developerDate : LocalDate?=null
    var githubUrl:String=""
    var binUrl:String=""
    var softwareTypeId:Int=0
    var softwareDate:LocalDate ?= null
}