package com.wzj.blog.myblog.entity

class UserInfo {

    var userIp:String?=null        //用户IP
    var userName:String?=null        //用户名
    var userPwd:String?=null        //用户密码
    var userEmail:String?=null        //用户邮箱
    var userProFilePhoto:String?=null        //用户头像地址
    var userPhone:String?=null        //用户手机号
    var userRegisrAtionTime:String?=null        //用户注册时间
    var userBirthDay:String?=null        //用户生日
    var userAge:Int?=null        //用户年龄
    var userNickName:String?=null        //用户昵称
    var userConstellAtion:String?=null        //用户星座
    var userId:Int=0      //用户ID
    //修改密码时需要
    var newUserPwd:String?=null      //用户新密码
    var userPwdType:Int?=null      // 0 修改密码  1 忘记密码

}