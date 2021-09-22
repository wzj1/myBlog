package com.wzj.blog.myblog.entity

open class UserTokenEntity {
    //ID
    var id :Int = -1
    //用户ID
    var userId:Int = -1
    //注册时间
    var register_time:String? = null
    //到期时间
    var dueto_time:String? = null

    //失效时间 默认1天 24小时
    var failurenum:Float = 1f

    //默认未登录
    var status:Int = -1

    //token
    var token:String? =null




}