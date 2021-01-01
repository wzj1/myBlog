package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.CommentEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
open interface CommentMapper {

    // 添加博文数据
    @Insert("insert into ${SqlUtil.Comments_Table}(USERID,ARTICLEID,COMMENTLIKECOUNT,COMMENTDATE,PARENTCOMMENTID,COMMENTCONTENT,COMMENTID) values(#{userid},#{articleid},#{userid},#{commentLikeCount},#{commentDate},${SqlUtil.seq_Comments},#{commentContent},${SqlUtil.seq_Comments})")
    fun addComment(comment: CommentEntity):Int

    //修改点赞数
    @Update("update  ${SqlUtil.Comments_Table} set COMMENTLIKECOUNT=#{commentLikeCount} where COMMENTID=#{commentid}")
    fun upLikeCount(commentLikeCount: Int,commentid:Int):Int

    @Update("update  ${SqlUtil.Comments_Table} set USERID=#{userid},ARTICLEID=#{articleid},COMMENTLIKECOUNT=#{commentLikeCount},COMMENTDATE=#{commentDate},PARENTCOMMENTID=#{parentCommentid},COMMENTCONTENT=#{commentContent} where COMMENTID=#{commentid}")
    fun upComment(comment: CommentEntity):Int

    @Select("select COMMENTID,USERID,ARTICLEID,COMMENTLIKECOUNT,COMMENTDATE,PARENTCOMMENTID,COMMENTCONTENT from  ${SqlUtil.Comments_Table} where COMMENTID=#{commentid}")
    fun queryById(commentid: Int): ArticlesEntity

    @Select("select COMMENTID,USERID,ARTICLEID,COMMENTLIKECOUNT,COMMENTDATE,PARENTCOMMENTID,COMMENTCONTENT from  ${SqlUtil.Comments_Table}")
    fun queryforList():MutableList<ArticlesEntity>

    @Delete("delete ${SqlUtil.Comments_Table} where COMMENTID=#{commentid}")
    fun deleteById(commentid: Int):Int


}