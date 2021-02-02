package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

open class SortsEntity {
   var  sortid:Int = 0          //分类ID
   var  sortName:String? =null          //分类名称
   var  sortAlias:String? =null         //分类别名
   var  sortDescription:String? =null           //分类描述
   var  parentSortid:Int = 0            //父分类ID



   override fun toString(): String {
      val sb =StringBuffer()
      sb.append("分类ID: $sortid \r\n")
      sb.append("分类名称: $sortName \r\n")
      sb.append("分类别名: $sortAlias \r\n")
      sb.append("分类描述: $sortDescription \r\n")
      sb.append("父分类ID: $parentSortid \r\n")

      return sb.toString()
   }


}