package com.wzj.blog.myblog.service.articles

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.mapper.ArticlesMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ArticlesServiceImpl:ArticlesService {
    @Autowired
    lateinit var  articlesMapper: ArticlesMapper

    override fun insertArticles(articles: ArticlesEntity): Int =articlesMapper.insertArticles(articles)
    override fun insertViews(articlesId: Int, view: Int): Int =articlesMapper.insertViews(articlesId, view)

    override fun insertCount(articlesId: Int, count: Int): Int =articlesMapper.insertCount(articlesId, count)

    override fun updateArticles(articles: ArticlesEntity): Int =articlesMapper.updateArticles(articles)
    override fun updateTitelContent(articles: ArticlesEntity): Int =articlesMapper.updateTitelContent(articles)

    override fun queryById(articlesId: Int): ArticlesEntity=articlesMapper.queryById(articlesId)

    override fun queryforList(): MutableList<ArticlesEntity> =articlesMapper.queryforList()

    override fun deleteById(articlesId: Int): Int =articlesMapper.deleteById(articlesId)

    override fun deleteByUserId(userId: Int): Int =articlesMapper.deleteByUserId(userId)

}