package com.wzj.blog.myblog.service.setting.aticleLable

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.mapper.ArticleLableMapper
import org.springframework.stereotype.Service

/**
 * 设置标签表
 */
@Service
open class ArticleLableServiceImpl :ArticleLableService {

    lateinit var articleMapper: ArticleLableMapper

    override fun addArtitleLable(articleid: Int, labelId: Int): Int =articleMapper.addArtitleLable(articleid, labelId)

    override fun upArtitleLable(articleid: Int, labelId: Int): Int =articleMapper.upArtitleLable(articleid, labelId)

    override fun queryById(articleid: Int): ArticlesEntity =articleMapper.queryById(articleid)

    override fun queryforList(): MutableList<ArticlesEntity> =articleMapper.queryforList()

    override fun deleteById(articleid: Int): Int =articleMapper.deleteById(articleid)
}