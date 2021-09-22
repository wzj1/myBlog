package com.wzj.blog.myblog.service.token

import com.wzj.blog.myblog.entity.UserTokenEntity

interface TokenService {



    //插入 token
    fun insertToken(token: UserTokenEntity):Int
    //查询
    fun queryToken(userId: Int):UserTokenEntity
    fun querySatatus(status: Int):MutableList<UserTokenEntity>
    fun queryAll():MutableList<UserTokenEntity>
    fun queryOrToken(token:String):UserTokenEntity

    //修改所有数据
    fun updateUserById(userId: UserTokenEntity):Int

    //修改 token 和登录状态
    fun updateUserByIdToTokenOrStatus(userId:Int,token: String,status:Int):Int

    //修改 token
    fun updateUserByIdToToken(userId:Int,token: String):Int



}