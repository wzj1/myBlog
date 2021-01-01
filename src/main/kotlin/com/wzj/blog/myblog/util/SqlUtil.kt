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


    //博文表
    const val Articles_Table: String = "ARTICLES"

    //博文表 ID序列
    const val seq_Articles: String = "SEQ_ARTICLES.nextval"


    //评论表
    const val Comments_Table: String = "COMMENTS"

    //评论表 ID序列
    const val seq_Comments: String = "SEQ_COMMENTS.nextval"



    //设置标签
    const val Artitle_Lable_Table: String = "ARTITLE_LABLE"

    //设置标签 ID序列
    const val seq_Artitle_Lable: String = "SEQ_ARTITLE_LABLE.nextval"



    //设置分类
    const val ArtitleSort_Table: String = "ARTITLE_SORT"

    //设置分类 ID序列
    const val seq_ArtitleSort: String = "SEQ_ARTITLE_SORT.nextval"



    //标签表
    const val Lanels_Table: String = "LANELS"

    //标签 ID序列
    const val seq_Lanels: String = "SEQ_LANELS.nextval"



    //分类表
    const val Sorts_Table: String = "SORTS"

    //分类 ID序列
    const val seq_sorts: String = "SEQ_SORTS.nextval"




}