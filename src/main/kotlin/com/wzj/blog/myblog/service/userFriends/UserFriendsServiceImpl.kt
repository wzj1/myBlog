package com.wzj.blog.myblog.service.userFriends

import com.wzj.blog.myblog.entity.UserFriendsEntity
import com.wzj.blog.myblog.mapper.UserFriendsMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserFriendsServiceImpl:UserFriendsService {
    @Autowired
    lateinit var  userMapper: UserFriendsMapper
    override fun insertFriends(friends: UserFriendsEntity): Int =userMapper.insertFriends(friends)
    override fun updateFriendsStatus(userFriendsId: Int, userStatus: Int): Int= userMapper.updateFriendsStatus(userFriendsId, userStatus)

    override fun updateFriends(friends: UserFriendsEntity): Int =userMapper.updateFriends(friends)

    override fun queryFriendsById(id: Int): MutableList<UserFriendsEntity> =userMapper.queryFriendsById(id)

    override fun queryFriendsByUserId(userId: Int): MutableList<UserFriendsEntity> =userMapper.queryFriendsByUserId(userId)
    override fun queryFriendsByIdAndUserId(userId: Int, userFriendsId: Int): MutableList<UserFriendsEntity> =userMapper.queryFriendsByIdAndUserId(userId,userFriendsId)


    override fun deleteFriendsById(userId: Int, userFriendsId: Int): Int =userMapper.deleteFriendsById(userId,userFriendsId)

}