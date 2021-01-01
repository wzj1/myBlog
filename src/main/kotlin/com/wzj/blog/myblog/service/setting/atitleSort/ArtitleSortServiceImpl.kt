package com.wzj.blog.myblog.service.setting.atitleSort

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.mapper.ArtitleSortMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 设置标签表
 */
@Service
open class ArtitleSortServiceImpl : ArtitleSortService {

    @Autowired
    lateinit var artitleMapper: ArtitleSortMapper

    override fun addAtitleSort(articleid: Int, labelId: Int): Int=artitleMapper.addArtitleSort(articleid, labelId)

    override fun upAtitleSort(articleid: Int, labelId: Int): Int =artitleMapper.upArtitleSort(articleid, labelId)

    override fun queryById(articleid: Int): ArticlesEntity =artitleMapper.queryById(articleid)

    override fun queryforList(): MutableList<ArticlesEntity> =artitleMapper.queryforList()

    override fun deleteById(articleid: Int): Int =artitleMapper.deleteById(articleid)
}