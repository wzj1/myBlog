package com.wzj.blog.myblog.service.token

import com.wzj.blog.myblog.entity.UserTokenEntity
import com.wzj.blog.myblog.mapper.UserFriendsMapper
import com.wzj.blog.myblog.mapper.UserTokenMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TokenServiceImpl:TokenService {
    @Autowired
    lateinit var  tokenMapper: UserTokenMapper
    override fun insertToken(token: UserTokenEntity): Int  = tokenMapper.insertToken(token)
    override fun queryToken(userId: Int): UserTokenEntity  = tokenMapper.queryToken(userId)
    override fun queryOrToken(token: String): UserTokenEntity= tokenMapper.queryOrToken(token)

    override fun querySatatus(status: Int): MutableList<UserTokenEntity> = tokenMapper.querySatatus(status)
    override fun queryAll(): MutableList<UserTokenEntity>  =tokenMapper.queryAll()

    override fun updateUserById(userId: UserTokenEntity): Int  = tokenMapper.updateUserById(userId)

    override fun updateUserByIdToTokenOrStatus(userId: Int, token: String, status: Int): Int  = tokenMapper.updateUserByIdToTokenOrStatus(userId, token, status)

    override fun updateUserByIdToToken(userId: Int, token: String): Int = tokenMapper.updateUserByIdToToken(userId, token)

}