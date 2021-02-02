package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

class UserFriendsEntity {
    var id:Int = 0 // ID
    var userId:Int = 0 //用户ID
    var userFriendsId:Int = 0 //好友ID
    var userNote:String? = null //好友备注
    var userStatus:Int = -1  // 0 在线 1 离开 2 忙碌 3 离线 4 隐身



    override fun toString(): String {
        val sb =StringBuffer()
        sb.append("ID: $id \r\n")
        sb.append("用户ID: $userId \r\n")
        sb.append("好友ID: $userFriendsId \r\n")
        sb.append("好友备注: $userNote \r\n")
        sb.append("0 在线 1 离开 2 忙碌 3 离线 4 隐身: $userStatus \r\n")
        return sb.toString()
    }
}