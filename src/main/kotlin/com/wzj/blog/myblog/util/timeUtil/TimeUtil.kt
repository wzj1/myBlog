package com.wzj.blog.myblog.util.timeUtil

import java.text.SimpleDateFormat
import java.util.*


/**
 * 时间 日期 工具类
 */
object TimeUtil {

    /**
     * 获取随机数
     */
    fun getRandom():Int=Random().nextInt(1000000000)

    /**
     * 获取时间
     */
    fun getTime():String{
       return getDate("hhmmss")
    }

    /**
     * 获取日期
     * 日期格式 如：默认 yyyyMMdd  yyyyMMddHHmmssSSS  yyyy-MM-dd HH:mm:ss
     */
    fun getDate(pattern:String?):String{
        var patterns:String = ""
        //判断日期格式  如果是空格式 则默认一个格式
        if (pattern.isNullOrBlank()){
            patterns = "yyyy-MM-dd"
        }
        val sdf = SimpleDateFormat(if (patterns.isNullOrBlank()) pattern else patterns)
        val fileAdd = sdf.format(Date())
        return fileAdd
    }



}