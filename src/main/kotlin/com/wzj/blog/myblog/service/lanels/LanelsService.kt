package com.wzj.blog.myblog.service.lanels

import com.wzj.blog.myblog.entity.LanelsEntity

interface LanelsService {
    fun addLanels(labelName:String?,labelAlias:String?,labelDescription:String?):Int
    fun upLanels(labelId:Int,labelName:String?,labelAlias:String?,labelDescription:String?):Int
    fun queryById(labelId:Int): LanelsEntity
    fun queryByLanels():MutableList<LanelsEntity>
    fun deleteById(labelId:Int):Int

}