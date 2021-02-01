package com.wzj.blog.myblog.service.comment

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.CommentEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update


 interface CommentService {

    // 添加博文数据
    fun addComment(comment: CommentEntity):Int

    //修改点赞数
    fun upLikeCount(commentLikeCount: Int,commentid:Int):Int

    fun upComment(comment: CommentEntity):Int

    fun queryById(commentid: Int): CommentEntity

    fun queryforList():MutableList<CommentEntity>

    fun deleteById(commentid: Int):Int
}