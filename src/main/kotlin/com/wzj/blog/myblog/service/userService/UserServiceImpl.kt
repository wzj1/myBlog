package com.wzj.blog.myblog.service.userService

import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl:UserService {

    @Autowired
    lateinit var  userMapper: UserMapper
    override fun insertUser(user: LoginEntity): Int =userMapper.insertUser(user)
    override fun updateUserById(userId: LoginEntity): Int =userMapper.updateUserById(userId)
    override fun updateUserPwdByName(userName: String, newUserPwd: String): Int =userMapper.updateUserPwdByName(userName, newUserPwd)
    override fun updateUserPwdById(userId: Int, newUserPwd: String): Int =userMapper.updateUserPwdById(userId, newUserPwd)
    override fun updateUserByName(userName: LoginEntity): Int =userMapper.updateUserByName(userName)

    override fun queryUserById(userId: Int): LoginEntity =userMapper.queryUserById(userId)
    override fun queryUserByPhone(phone: String): LoginEntity =userMapper.queryUserByPhone(phone)

    override fun queryUserByName(userName: String): MutableList<LoginEntity> =userMapper.queryUserByName(userName)
    override fun queryUserByLoginName(userName: String): MutableList<LoginEntity> =userMapper.queryUserByLoginName(userName)

    override fun queryUserLikeByName(userName: String): MutableList<LoginEntity> =userMapper.queryUserLikeByName(userName)
    override fun queryAll(): MutableList<LoginEntity> = userMapper.queryAll()

    override fun deleteUserByName(userName: String): Int =userMapper.deleteUserByName(userName)

    override fun deleteUserById(userId: Int): Int =userMapper.deleteUserById(userId)


}