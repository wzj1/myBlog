package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

open class LanelsEntity {
   @Expose
   var labelId :Int = 0         //标签ID
   @Expose
   var labelName:String?=null           //标签名称
   @Expose
   var labelAlias:String?=null          //标签别名
   @Expose
   var labelDescription:String?=null            //标签描述




}