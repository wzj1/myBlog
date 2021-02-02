package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

class ImageEntity {
  var image_id :Int = 0             //ID 主键
  var image_type:Int = 0        //类型 0头像, 1其他
  var image_data:String?=null        //添加时间
  var image_name:String?=null        //文件名称
  var image_suffix:String?=null        //文件后缀
  var user_id:Int=0   //用户ID
  var image_address:String?=null        //地址
  var image_path:String?=null        //路径

    override fun toString(): String {
        val sb =StringBuffer()
        sb.append("ID: $image_id \r\n")
        sb.append("类型: $image_type \r\n")
        sb.append("添加时间: $image_data \r\n")
        sb.append("文件名称: $image_name \r\n")
        sb.append("文件后缀: $image_suffix \r\n")
        sb.append("用户ID: $user_id \r\n")
        sb.append("地址: $image_address \r\n")
        sb.append("路径: $image_path \r\n")

        return sb.toString()
    }



}