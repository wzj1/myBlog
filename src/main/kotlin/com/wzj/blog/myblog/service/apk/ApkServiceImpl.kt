package com.wzj.blog.myblog.service.apk

import com.wzj.blog.myblog.entity.apk.ApkEnt
import com.wzj.blog.myblog.mapper.ArticlesMapper
import com.wzj.blog.myblog.mapper.apk.APKMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class ApkServiceImpl:ApkService {

    @Autowired
    lateinit var  apkMapper: APKMapper

    override fun addApk(apkEnt: ApkEnt): Int  = apkMapper.addApk(apkEnt)

    override fun upApk(apkEnt: ApkEnt): Int  = apkMapper.upApk(apkEnt)

    override fun queryById(id: Int): ApkEnt  = apkMapper.queryById(id)

    override fun query(apkEnt: ApkEnt): ApkEnt  = apkMapper.query(apkEnt)

    override fun queryforList(): MutableList<ApkEnt>  = apkMapper.queryforList()

    override fun deleteById(id: Int): Int  = apkMapper.deleteById(id)

}