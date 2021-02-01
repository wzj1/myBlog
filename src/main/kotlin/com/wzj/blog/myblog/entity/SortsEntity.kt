package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

open class SortsEntity {
   @Expose
   var  sortid:Int = 0          //分类ID
   @Expose
   var  sortName:String? =null          //分类名称
   @Expose
   var  sortAlias:String? =null         //分类别名
   @Expose
   var  sortDescription:String? =null           //分类描述
   @Expose
   var  parentSortid:Int = 0            //父分类ID

}