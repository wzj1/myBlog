package com.wzj.blog.myblog.entity.apk

/**
 * APP 版本控制
 */
open class ApkEnt {
    var id:Int = -1; //住建 ID
    var channel_id:Int = 0 //渠道ID
    var app_id:Int = 0 //APPID
    var version_id:Int = 0 //app 版本ID
    var md5:String? =null //文件MD5
    var file_name:String? =null //文件名称 无后缀
    var url:String? =null //文件地址
    var delivery_status:Int = 0  //0 未上架  1 已上架  2 已下架
    var created_by:String? = null   //创建人
    var del_flag:Boolean = false   //是否删除
    var updated_by:String? = null   //更新人

}