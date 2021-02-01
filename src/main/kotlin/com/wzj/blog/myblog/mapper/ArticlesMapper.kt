package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
 interface ArticlesMapper {
    @Update("update  ${SqlUtil.Articles_Table} set ARTICLEVIEWS=#{view} where ARTICLEID=#{articlesId}")
    fun insertViews(articlesId: Int,view:Int):Int

    @Update("update  ${SqlUtil.Articles_Table} set ARTICLECOMMENTCOUNT=#{count} where ARTICLEID=#{articlesId}")
    fun insertCount(articlesId: Int,count: Int):Int

    @Insert("insert into ${SqlUtil.Articles_Table}(ARTICLEID,USERID,ARTICLETITLE,ARTICLECONTENT,ARTICLEVIEWS,ARTICLECOMMENTCOUNT,ARTICLEDATE) values(${SqlUtil.seq_Articles},#{userid},#{articleTitle},#{articleContent},#{articleViews},#{articleCommentCount},#{articleDate})")
    fun insertArticles(articles: ArticlesEntity):Int

    @Update("update  ${SqlUtil.Articles_Table} set USERID=#{userid},ARTICLETITLE=#{articleTitle},ARTICLECONTENT=#{articleContent},ARTICLEVIEWS=#{articleViews},ARTICLECOMMENTCOUNT=#{articleCommentCount},ARTICLEDATE=#{articleDate} where ARTICLEID=#{articlesId}")
    fun updateArticles(articles: ArticlesEntity):Int

    @Update("update  ${SqlUtil.Articles_Table} set ARTICLETITLE=#{articleTitle},ARTICLECONTENT=#{articleContent},ARTICLEDATE=#{articleDate} where ARTICLEID=#{articlesId}")
    fun updateTitelContent(articles: ArticlesEntity):Int

    @Select("select ARTICLEID,USERID,ARTICLETITLE,ARTICLECONTENT,ARTICLEVIEWS,ARTICLECOMMENTCOUNT,ARTICLEDATE from  ${SqlUtil.Articles_Table} where ARTICLEID=#{articlesId}")
    fun queryById(articlesId: Int): ArticlesEntity

    @Select("select ARTICLEID,USERID,ARTICLETITLE,ARTICLECONTENT,ARTICLEVIEWS,ARTICLECOMMENTCOUNT,ARTICLEDATE from  ${SqlUtil.Articles_Table}")
    fun queryforList():MutableList<ArticlesEntity>

    @Delete("delete ${SqlUtil.Articles_Table} where ARTICLEID=#{articlesId}")
    fun deleteById(articlesId: Int):Int

    @Delete("delete ${SqlUtil.Articles_Table} where USERID=#{userid}")
    fun deleteByUserId(userId: Int):Int

}