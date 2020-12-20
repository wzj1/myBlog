package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.UserFriendsEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface UserFriendsMapper {
    @Insert("insert into ${SqlUtil.UserFriends_Table}(ID,USERID,USERFRIENDSID,USERNOTE,USERSTATUS) values(${SqlUtil.seq_UserFriends},#{userId},#{userFriendsId},#{userNote},#{userStatus})")
    fun insertFriends(friends: UserFriendsEntity): Int

    @Update("update ${SqlUtil.UserFriends_Table} set USERNOTE=#{userNote},USERSTATUS=#{userStatus}  where  USERID=#{userId} and USERFRIENDSID=#{userFriendsId} ")
    fun updateFriends(friends: UserFriendsEntity): Int
    @Update("update ${SqlUtil.UserFriends_Table} set USERSTATUS=#{userStatus} where USERFRIENDSID=#{userFriendsId} ")
    fun updateFriendsStatus(userFriendsId: Int,userStatus:Int): Int

    @Select("select ID,USERID,USERFRIENDSID,USERNOTE,USERSTATUS from ${SqlUtil.UserFriends_Table} where ID=#{id} ")
    fun queryFriendsById(id: Int): MutableList<UserFriendsEntity>

    @Select("select ID,USERID,USERFRIENDSID,USERNOTE,USERSTATUS from ${SqlUtil.UserFriends_Table} where USERID=#{userId}")
    fun queryFriendsByUserId(userId: Int): MutableList<UserFriendsEntity>

    @Select("select ID,USERID,USERFRIENDSID,USERNOTE,USERSTATUS from ${SqlUtil.UserFriends_Table} where USERID=#{userId} and USERFRIENDSID=#{userFriendsId} ")
    fun queryFriendsByIdAndUserId(userId: Int, userFriendsId: Int): MutableList<UserFriendsEntity>

    @Delete("delete  ${SqlUtil.UserFriends_Table} where USERID=#{userId} and USERFRIENDSID=#{userFriendsId}")
    fun deleteFriendsById(userId: Int, userFriendsId: Int): Int



}