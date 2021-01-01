package com.wzj.blog.myblog.service.sorts

import com.wzj.blog.myblog.entity.SortsEntity

interface SortsService {
    fun addSorts(sortName:String?,sortAlias:String?,sortDescription:String?,parentSortid:Int):Int
    fun upSorts(sortName:String?,sortAlias:String?,sortDescription:String?,parentSortid:Int,sortid:Int,):Int
    fun queryById(sortid:Int): SortsEntity
    fun queryforList():MutableList<SortsEntity>
    fun deleteById(sortid: Int):Int
}