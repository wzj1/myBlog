package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

open class ArticlesEntity  {
   var  articleid:Int = 0                      //博文ID
   var  userid : Int = 0                     //发表用户ID
   var  articleTitle:String?=null                        //博文标题
   var  articleContent:String?=null                      //博文内容
   var  articleViews: Int = 0                        //浏览量
   var  articleCommentCount: Int = 0                     //评论总数
   var  articleDate:String?=null                     //发表时间

    override fun toString(): String {
        return StringBuffer().append("博文ID: $articleid \r\n")
                .append("发表用户ID: $userid \r\n")
                .append("博文标题: $articleTitle \r\n")
                .append("博文内容: $articleContent \r\n")
                .append("浏览量: $articleViews \r\n")
                .append("评论总数: $articleCommentCount \r\n")
                .append("发表时间 $articleDate \r\n").toString()
    }
}