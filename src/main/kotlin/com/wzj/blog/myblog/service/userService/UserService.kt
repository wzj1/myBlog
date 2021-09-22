package com.wzj.blog.myblog.service.userService

import com.wzj.blog.myblog.entity.LoginEntity


/**
 * 用户表操作
 */
interface  UserService {

    fun insertUser(user: LoginEntity):Int

    fun updateUserById(userId: LoginEntity):Int
    fun updateUserPwdByName(userName: String,newUserPwd:String): Int

    fun updateUserPwdById(userId: Int,newUserPwd:String): Int
    fun updateUserByName(userName: LoginEntity):Int

    fun queryUserById(userId: Int): LoginEntity
    fun queryUserByPhone(phone: String):LoginEntity
    fun queryUserByName(userName: String):MutableList<LoginEntity>
    fun queryUserByLoginName(userName: String):MutableList<LoginEntity>
    fun queryUserLikeByName(userName: String):MutableList<LoginEntity>
    fun queryAll():MutableList<LoginEntity>

    fun deleteUserByName(userName: String):Int

    fun deleteUserById(userId: Int): Int

}