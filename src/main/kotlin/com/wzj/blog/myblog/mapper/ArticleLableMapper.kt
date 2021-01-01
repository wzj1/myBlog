package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.CommentEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
open interface ArticleLableMapper {

    @Insert("insert into ${SqlUtil.Artitle_Lable_Table}(ARTICLE_LABLE_ID,LABEL_ID) values(#{articleid},#{labelId})")
    fun addArtitleLable(articleid:Int,labelId: Int):Int

    @Update("update  ${SqlUtil.Artitle_Lable_Table} set LABEL_ID=#{labelId} where ARTICLE_LABLE_ID=#{articleid}")
    fun upArtitleLable(articleid:Int,labelId: Int):Int

    @Select("select ARTICLE_LABLE_ID,LABEL_ID from  ${SqlUtil.Artitle_Lable_Table} where ARTICLE_LABLE_ID=#{articleid}")
    fun queryById(articleid:Int): ArticlesEntity

    @Select("select ARTICLE_LABLE_ID,LABEL_ID from  ${SqlUtil.Artitle_Lable_Table}")
    fun queryforList():MutableList<ArticlesEntity>

    @Delete("delete ${SqlUtil.Artitle_Lable_Table} where ARTICLE_LABLE_ID=#{articleid}")
    fun deleteById(articleid: Int):Int



}