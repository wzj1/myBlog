package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

open class LanelsEntity {
   var labelId :Int = 0         //标签ID
   var labelName:String?=null           //标签名称
   var labelAlias:String?=null          //标签别名
   var labelDescription:String?=null            //标签描述


   override fun toString(): String {
      return StringBuffer().append("标签ID: $labelId \r\n")
              .append("标签名称: $labelName \r\n")
              .append("标签别名: $labelAlias \r\n")
              .append("标签描述: $labelDescription \r\n")
              .toString()
   }

}