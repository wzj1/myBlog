package com.wzj.blog.myblog.service.setting.atitleSort

import com.wzj.blog.myblog.entity.ArticlesEntity

interface ArtitleSortService {

    fun addAtitleSort(articleid:Int,labelId: Int):Int

    fun upAtitleSort(articleid:Int,labelId: Int):Int

    fun queryById(articleid:Int): ArticlesEntity

    fun queryforList():MutableList<ArticlesEntity>

    fun deleteById(articleid: Int):Int
}