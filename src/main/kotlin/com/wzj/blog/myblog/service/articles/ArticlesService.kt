package com.wzj.blog.myblog.service.articles

import com.wzj.blog.myblog.entity.ArticlesEntity

interface ArticlesService {
    fun insertArticles(articles: ArticlesEntity):Int
    fun insertViews(articlesId: Int,view:Int):Int
    fun insertCount(articlesId: Int,count: Int):Int
    fun updateArticles(articles: ArticlesEntity):Int
    fun updateTitelContent(articles: ArticlesEntity):Int
    fun queryById(articlesId: Int): ArticlesEntity
    fun queryforList():MutableList<ArticlesEntity>
    fun deleteById(articlesId: Int):Int
    fun deleteByUserId(userId: Int):Int
}