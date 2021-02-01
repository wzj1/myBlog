package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

class CommentEntity {
    @Expose
    var commentid:Int = 0           //ID 主键
    @Expose
    var userid:Int = 0              //发表用户ID
    @Expose
    var articleid:Int = 0           //评论博文ID
    @Expose
    var commentLikeCount:Int=0           //点赞数
    @Expose
    var commentDate:String?=null            //评论日期时间
    @Expose
    var parentCommentid:Int = 0         //父评论ID
    @Expose
    var commentContent:String?=null         //评论内容


    override fun toString(): String {
        return StringBuffer().append("ID: $commentid \r\n")
                .append("发表用户ID: $userid \r\n")
                .append("评论博文ID: $articleid \r\n")
                .append("点赞数: $commentLikeCount \r\n")
                .append("评论日期时间: $commentDate \r\n")
                .append("父评论ID: $parentCommentid \r\n")
                .append("评论内容 $commentContent \r\n").toString()
    }
}