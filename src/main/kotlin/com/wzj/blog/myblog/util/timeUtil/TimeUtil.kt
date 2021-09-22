package com.wzj.blog.myblog.util.timeUtil

import java.text.DateFormat
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
       return getDate("yyyy-MM-dd HH:mm:ss")
    }

    /**
     * @param 获取当前时间戳
     * @return 时间戳
     */
    fun getTimeMillis():Long = Date().getTime()

    /**
     * @param time 时间 ：yyyy-mm-dd hh:mm:ss
     * @return 时间戳格式
     */
    fun getTimeForMillis(time:String):Long = getTimeForDate(time).time

    /**
     * 获取时间对象
     */
    fun getDate():Date = Date()

    fun getTimeForDate(time:String):Date{
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = simpleDateFormat.parse(time)
        return date
    }

    fun getDate(millis:Long):Date{
        return Date(millis)
    }

    /**
     * 获取时间戳
     */
    fun getTimeL():Long{
       return  Date().getTime()
    }

    fun getNextDay(date: Date?,day:Int): Date? {
        var date = date
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, +day) //+1今天的时间加一天
        date = calendar.time
        return date
    }

    /**
     * 将时间戳转为时间
     */
    fun stampToDateTime(timeMillis: Long = Date().getTime()): String? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(timeMillis)
        return simpleDateFormat.format(date)
    }
    /**
     * 将时间戳转为时间
     */
    fun stampToDateTimeL(time: String): Long {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = simpleDateFormat.parse(time)
        return date.time
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


    /**
     * 判断当前是否超过24小时
     * @param generateTime String? // 存储的登录时间 "yyyy-MM-dd HH:mm:ss"
     * @param dueToTime String? // 到期时间"yyyy-MM-dd HH:mm:ss"
     * @return Boolean 是否超过24小时未操作，false  未超过  true 超过
     *
     */
    fun judgmentDate2(generateTime: String?, dueToTime: String?,conditions:Float = 1f): Boolean {

        return compare_date(generateTime, dueToTime,conditions)
    }

    /**
     * @param DATE1 开始时间
     * @param DATE2 结束时间
     * @param conditions 条件 1 = 24小时 = 一天
     * @return boolean true 表示 超过设定条件，false 表示未满足条件
     */
    fun compare_date(DATE1: String?, DATE2: String?,conditions:Float): Boolean {
        //当时间为空时，重新注册
        if (DATE1.isNullOrBlank()||DATE2.isNullOrBlank()) return true
        // 24小时
        val nd = (1000 * (conditions) * 60 * 60).toLong()
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val dt1 = df.parse(DATE1) //开始时间
            val dt2 = df.parse(DATE2) //到期时间
            return dt1.time < dt2.time -nd
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return false
    }

}