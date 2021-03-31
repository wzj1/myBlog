package com.wzj.blog.myblog.entity


open class UserInfo {
    var userIp:String?=null        //用户IP
    var userName:String?=null      //用户名
    var userPwd:String?=null        //用户密码
    var userEmail:String?=null        //用户邮箱
    var userProFilePhoto:String?=null        //用户头像地址
    var userPhone:String?=null        //用户手机号
    var userRegisrAtionTime:String?=null        //用户注册时间
    var userBirthDay:String?=null        //用户生日
    var userAge:Int=0        //用户年龄
    var userNickName:String?=null        //用户昵称
    var userConstellAtion:String?=null        //用户星座
    var userId:Int=0      //用户ID
    var loginType:Int=0      //登录类型
    //修改密码时需要
    var newUserPwd:String?=null      //用户新密码
    var userPwdType:Int?=null      // 0 修改密码  1 忘记密码
    var verifyCode:String?=null      //验证码
    var verifyCodeType:Int?=null      // 0 注册  1修改/忘记密码  2.其他


    override fun toString(): String {
        return StringBuffer().append("用户IP: $userIp \r\n")
                .append("用户名: $userName \r\n")
                .append("用户密码: $userPwd \r\n")
                .append("用户邮箱: $userEmail \r\n")
                .append("用户头像地址: $userProFilePhoto \r\n")
                .append("用户手机号: $userPhone \r\n")
                .append("用户注册时间: $userRegisrAtionTime \r\n")
                .append("用户生日: $userBirthDay \r\n")
                .append("用户年龄: $userAge \r\n")
                .append("用户昵称: $userNickName \r\n")
                .append("用户星座: $userConstellAtion \r\n")
                .append("用户ID: $userId \r\n")
                .append("登录类型: $loginType \r\n")
                .append("用户新密码: $newUserPwd \r\n")
                .append("0 修改密码  1 忘记密码: $userPwdType \r\n")
                .toString()
    }


}