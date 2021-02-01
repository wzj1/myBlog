package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose

class UserInfo {
    @Expose
    var userIp:String?=null        //用户IP
    @Expose
    var userName:String?=null        //用户名
    @Expose
    var userPwd:String?=null        //用户密码
    @Expose
    var userEmail:String?=null        //用户邮箱
    @Expose
    var userProFilePhoto:String?=null        //用户头像地址
    @Expose
    var userPhone:String?=null        //用户手机号
    @Expose
    var userRegisrAtionTime:String?=null        //用户注册时间
    @Expose
    var userBirthDay:String?=null        //用户生日
    @Expose
    var userAge:Int?=null        //用户年龄
    @Expose
    var userNickName:String?=null        //用户昵称
    @Expose
    var userConstellAtion:String?=null        //用户星座
    @Expose
    var userId:Int=0      //用户ID
    @Expose
    var login_type:Int=0      //用户ID
    //修改密码时需要
    @Expose
    var newUserPwd:String?=null      //用户新密码
    @Expose
    var userPwdType:Int?=null      // 0 修改密码  1 忘记密码

}