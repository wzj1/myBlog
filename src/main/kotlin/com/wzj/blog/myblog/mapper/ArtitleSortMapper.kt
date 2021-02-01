package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.CommentEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
 interface ArtitleSortMapper {


    @Insert("insert into ${SqlUtil.ArtitleSort_Table}(ARTICLE_ID,SORT_ID) values(#{articleid},#{sortId})")
    fun addArtitleSort(articleid:Int,sortId: Int):Int

    @Update("update  ${SqlUtil.ArtitleSort_Table} set SORT_ID=#{sortId} where ARTICLE_ID=#{sortId}")
    fun upArtitleSort(articleid:Int,sortId: Int):Int

    @Select("select ARTICLE_ID,SORT_ID from  ${SqlUtil.ArtitleSort_Table} where ARTICLE_ID=#{articleid}")
    fun queryById(articleid:Int): ArticlesEntity

    @Select("select ARTICLE_ID,SORT_ID from  ${SqlUtil.ArtitleSort_Table}")
    fun queryforList():MutableList<ArticlesEntity>

    @Delete("delete ${SqlUtil.ArtitleSort_Table} where ARTICLE_ID=#{articleid}")
    fun deleteById(articleid: Int):Int






}