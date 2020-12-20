package com.wzj.blog.myblog.service.UserFriends

import com.wzj.blog.myblog.entity.UserFriendsEntity

interface UserFriendsService {

    fun insertFriends(friends: UserFriendsEntity): Int
    fun updateFriendsStatus(userFriendsId: Int,userStatus:Int): Int
    fun updateFriends(friends: UserFriendsEntity): Int

    fun queryFriendsById(id: Int): MutableList<UserFriendsEntity>
    fun queryFriendsByIdAndUserId(userId: Int, userFriendsId: Int): MutableList<UserFriendsEntity>
    fun queryFriendsByUserId(userId: Int): MutableList<UserFriendsEntity>
    fun deleteFriendsById(userId: Int, userFriendsId: Int): Int


}