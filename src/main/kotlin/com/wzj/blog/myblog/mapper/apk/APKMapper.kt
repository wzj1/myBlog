package com.wzj.blog.myblog.mapper.apk

import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.apk.ApkEnt
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface APKMapper {

    @Insert("insert into ${SqlUtil.YESAPI_APPVER_APK}(channel_id,app_id,version_id,md5,file_name,url,delivery_status,created_by,del_flag,updated_by) values(#{channel_id},#{app_id},#{version_id},#{md5},#{file_name},#{url},#{delivery_status},#{created_by},#{del_flag},#{updated_by})")
    fun addApk(apkEnt: ApkEnt):Int

//    @Update("update  ${SqlUtil.YESAPI_APPVER_APK} set version_id=#{version_id},md5=#{md5},url=#{url},del_flag=#{del_flag},updated_by=#{updated_by} where id=#{id} and version_id=#{version_id} and del_flag=#{del_flag}")
    @Update("update  ${SqlUtil.YESAPI_APPVER_APK} set version_id=#{version_id},md5=#{md5},file_name=#{file_name},url=#{url},del_flag=#{del_flag},updated_by=#{updated_by} where id=#{id}")
    fun upApk(apkEnt: ApkEnt):Int

    @Select("select id,channel_id,app_id,version_id,md5,file_name,url,delivery_status,created_by,del_flag,updated_by from  ${SqlUtil.YESAPI_APPVER_APK} where id=#{id}")
    fun queryById(id:Int): ApkEnt

    @Select("select id,channel_id,app_id,version_id,md5,file_name,url,delivery_status,created_by,del_flag,updated_by from  ${SqlUtil.YESAPI_APPVER_APK} where  version_id=#{version_id} and del_flag=#{del_flag}")
    fun query(apkEnt:ApkEnt): ApkEnt

    @Select("select id,channel_id,app_id,version_id,md5,file_name,url,delivery_status,created_by,del_flag,updated_by from  ${SqlUtil.YESAPI_APPVER_APK}")
    fun queryforList():MutableList<ApkEnt>

    @Delete("delete ${SqlUtil.YESAPI_APPVER_APK} where id=#{id}")
    fun deleteById(id: Int):Int

}