package com.wzj.blog.myblog.service.imageService

import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.mapper.ImageMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ImageServiceImpl :ImageService{
    @Autowired
    lateinit var  imgempper: ImageMapper

    override fun insertImage(image: ImageEntity): Int {
      return imgempper.insertImage(image)
    }

    override fun updateImage(image: ImageEntity): Int {
        return imgempper.updateImage(image)
    }

    override fun queryById(imageid: Int): ImageEntity {
        return imgempper.queryById(imageid)
    }

    override fun queryByUserId(userid: Int): MutableList<ImageEntity> {
        return imgempper.queryByUserId(userid)
    }

    override fun queryforList(): MutableList<ImageEntity> {
        return imgempper.queryforList()
    }

    override fun deleteById(imageid: Int): Int {
        return imgempper.deleteById(imageid)
    }

    override fun deleteByUserId(userid: Int): Int {
        return imgempper.deleteByUserId(userid)
    }
}