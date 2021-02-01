package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

class UserFriendsEntity {
    @Expose
    var id:Int = 0 // ID
    @Expose
    var userId:Int = 0 //用户ID
    @Expose
    var userFriendsId:Int = 0 //好友ID
    @Expose
    var userNote:String? = null //好友备注
    @Expose
    var userStatus:Int = -1  // 0 在线 1 离开 2 忙碌 3 离线 4 隐身

}