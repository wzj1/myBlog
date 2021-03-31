package com.wzj.blog.myblog.service.userService

import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.entity.UserInfo


/**
 * 用户表操作
 */
interface  UserService {

    fun insertUser(user: UserInfo):Int
    fun updateUserImagePathById(user: UserInfo): Int
    fun updateUserById(userId: UserInfo):Int
    fun updateUserPwdByName(userName: String,newUserPwd:String): Int

    fun updateUserPwdById(userId: Int,newUserPwd:String): Int
    fun updateUserByName(userName: UserInfo):Int

    fun queryUserById(userId: Int): UserInfo
    fun queryUserByPhone(phone: String):UserInfo
    fun queryUserByName(userName: String):MutableList<UserInfo>
    fun queryUserByLoginName(userName: String):MutableList<LoginEntity>
    fun queryUserByLoginPhone(userName: String):LoginEntity
    fun queryUserLikeByName(userName: String):MutableList<UserInfo>

    fun deleteUserByName(userName: String):Int

    fun deleteUserById(userId: Int): Int

}