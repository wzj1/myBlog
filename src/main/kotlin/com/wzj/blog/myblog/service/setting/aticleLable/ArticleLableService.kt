package com.wzj.blog.myblog.service.setting.aticleLable

import com.wzj.blog.myblog.entity.ArticlesEntity

interface ArticleLableService {

    fun addArtitleLable(articleid:Int,labelId: Int):Int

    fun upArtitleLable(articleid:Int,labelId: Int):Int

    fun queryById(articleid:Int): ArticlesEntity

    fun queryforList():MutableList<ArticlesEntity>

    fun deleteById(articleid: Int):Int
}