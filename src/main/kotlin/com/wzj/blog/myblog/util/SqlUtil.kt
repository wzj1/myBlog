package com.wzj.blog.myblog.util

/**
 * SQL 语句公共类
 */
object SqlUtil {
    //用户表
    const val userInfo_Table: String = "USERINFO"

    //用户表序列
    const val seq_UserInfo: String = "SEQ_USERINFO.nextval"

    //图片、头像表
    const val Image_Lob_Table: String = "IMAGE_LOB"

    //图片、头像 序列
    const val seq_Image_Lob: String = "SEQ_IMAGE_LOB.nextval"

    //好友表
    const val UserFriends_Table: String = "USERFRIENDS"

    //好友表 ID序列
    const val seq_UserFriends: String = "SEQ_USERFRIENDS.nextval"


}