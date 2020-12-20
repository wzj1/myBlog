package com.wzj.blog.myblog.service.ImageService

import com.wzj.blog.myblog.entity.ImageEntity


/**
 * 图片表操作接口
 */
interface ImageService {
    fun insertImage(image: ImageEntity):Int
    fun updateImage(image: ImageEntity):Int
    fun queryById(imageid: Int):ImageEntity
    fun queryByUserId(userid: Int):MutableList<ImageEntity>
    fun queryforList():MutableList<ImageEntity>
    fun deleteById(imageid: Int):Int
    fun deleteByUserId(userid: Int):Int
}