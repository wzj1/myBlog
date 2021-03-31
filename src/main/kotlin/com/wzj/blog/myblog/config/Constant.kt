package com.wzj.blog.myblog.config

/**
 * 全局统一常量池
 * 常量池
 */
object Constant {
    //session 用户ID
    const val USER_ID="userId"

    const val SESSION_ID="sessions"

    //登录失效状态码
    const val ERROR_CLEAR = "301"

    //是否生产环境  更改存储文件路径

    const val isStorageFilePath:Boolean = true

    //生产默认文件地址
//    const val imagePath:String = "/var/image/"

    //测试默认文件地址
    const val imagePath:String = "/Volumes/AS/uploadImagePath/"


    /**
     * 图片显示地址
     *     private var image_https = "https://www.wzjlb.com.cn"
    private var image_prot = "8443"
     */

    //生产
//    const val image_IP:String = "https://www.wzjlb.com.cn"
//    const val image_prot:String = "8443"


    //测试
    const val image_IP:String = "192.168.0.106"
    const val image_prot:String = "8081"




}