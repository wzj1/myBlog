package com.wzj.blog.myblog.service.apk

import com.wzj.blog.myblog.entity.apk.ApkEnt

interface ApkService {
    fun addApk(apkEnt: ApkEnt):Int

    fun upApk(apkEnt: ApkEnt):Int

    fun queryById(id:Int): ApkEnt

    fun query(apkEnt:ApkEnt): ApkEnt

    fun queryforList():MutableList<ApkEnt>

    fun deleteById(id: Int):Int
}