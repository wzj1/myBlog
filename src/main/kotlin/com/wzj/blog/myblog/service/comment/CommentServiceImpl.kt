package com.wzj.blog.myblog.service.comment

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.CommentEntity
import com.wzj.blog.myblog.mapper.ArticlesMapper
import com.wzj.blog.myblog.mapper.CommentMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CommentServiceImpl :CommentService {
    @Autowired
    lateinit var  commentMapper: CommentMapper
    override fun addComment(comment: CommentEntity): Int =commentMapper.addComment(comment)

    override fun upLikeCount(commentLikeCount: Int, commentid: Int): Int =commentMapper.upLikeCount(commentLikeCount, commentid)

    override fun upComment(comment: CommentEntity): Int =commentMapper.upComment(comment)

    override fun queryById(commentid: Int): CommentEntity =commentMapper.queryById(commentid)

    override fun queryforList(): MutableList<CommentEntity> =commentMapper.queryforList()

    override fun deleteById(commentid: Int): Int =commentMapper.deleteById(commentid)
}