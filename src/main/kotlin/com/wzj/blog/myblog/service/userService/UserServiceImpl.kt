package com.wzj.blog.myblog.service.userService

import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.entity.UserInfo
import com.wzj.blog.myblog.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl:UserService {

    @Autowired
    lateinit var  userMapper: UserMapper

    override fun insertUser(user: UserInfo): Int =userMapper.insertUser(user)
    override fun updateUserById(userId: UserInfo): Int =userMapper.updateUserById(userId)
    override fun updateUserImagePathById(user: UserInfo): Int =userMapper.updateUserImagePathById(user)
    override fun updateUserPwdByName(userName: String, newUserPwd: String): Int =userMapper.updateUserPwdByName(userName, newUserPwd)
    override fun updateUserPwdById(userId: Int, newUserPwd: String): Int =userMapper.updateUserPwdById(userId, newUserPwd)
    override fun updateUserByName(userName: UserInfo): Int =userMapper.updateUserByName(userName)

    override fun queryUserById(userId: Int): UserInfo =userMapper.queryUserById(userId)
    override fun queryUserByPhone(phone: String): UserInfo =userMapper.queryUserByPhone(phone)

    override fun queryUserByName(userName: String): MutableList<UserInfo> =userMapper.queryUserByName(userName)
    override fun queryUserByLoginName(userName: String): MutableList<LoginEntity> =userMapper.queryUserByLoginName(userName)
    override fun queryUserByLoginPhone(userName: String): LoginEntity  = userMapper.queryUserByLoginPhone(userName)

    override fun queryUserLikeByName(userName: String): MutableList<UserInfo> =userMapper.queryUserLikeByName(userName)
    override fun deleteUserByName(userName: String): Int =userMapper.deleteUserByName(userName)

    override fun deleteUserById(userId: Int): Int =userMapper.deleteUserById(userId)


}