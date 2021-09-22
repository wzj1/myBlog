package com.wzj.blog.myblog.util

import com.google.gson.Gson
import com.wzj.blog.myblog.entity.UserTokenEntity
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 *
 */
open class SeesionUtil {

    var logger: Logger = LoggerFactory.getLogger(SeesionUtil::class.java)

    @Autowired
    lateinit var mainService: MainService


    companion object {
        @Volatile
        private var sInstance: SeesionUtil? = null

        /**
         * 获取单例
         * @return
         */
        public val instance: SeesionUtil
            get() {
                if (sInstance == null) {
                    synchronized(SeesionUtil::class.java) {
                        if (sInstance == null) {
                            sInstance = SeesionUtil()
                        }
                    }
                }
                return sInstance!!
            }

    }


    /**
     * 存储Seesion-UserId
     */
    fun setSessionUserId(request: HttpServletRequest, userid: Int?) {
        //是修改还是插入
        var status = false

        logger.info("进入存储Token")
        logger.info("默认状态是$status")

        //查询用户信息
        val userInfos = mainService.userService.queryUserById(userid!!)
        logger.info("查询的用户信息：${userInfos.toString()}")
        //查询token表信息
        val token = mainService.tokenService.queryToken(userid)
        logger.info("查询的token：${token}")
        var user = UserTokenEntity()
        //如果存在则 重新赋值
        if (token != null && token.userId > 0) {
            status = false
            user = token
            logger.info("存在Token并重新赋值：${user}")
        } else {
            status = true
        }
        val timeMisll: Long = TimeUtil.getTimeMillis()
        val time = TimeUtil.getDate(timeMisll)

        val ss = Date().time
        user.register_time = TimeUtil.stampToDateTime(TimeUtil.getNextDay(time, 0)!!.time)
        user.userId = userInfos.userId
        //到期时间以时间配置 * 一天的时间戳 + 开始时间 得到结束时间
        user.dueto_time = TimeUtil.stampToDateTime(TimeUtil.getNextDay(time, user.failurenum.toInt())!!.time)
        val map = hashMapOf<String, Any>()
        map["userPhone"] = userInfos.userPhone.toString()
        map["userName"] = userInfos.userName.toString()
        map["userPwd"] = userInfos.userPwd.toString()
        map["userId"] = userInfos.userPwd.toString()
        user.token = TokenEncryptUtils.createJWT(userInfos.userId.toString(),
            Gson().toJson(map),
            time.time,
            TimeUtil.getNextDay(time, 1)!!.time)
        user.status = 0
        logger.info("需要插入的toKen信息：${Gson().toJson(user)}")
        //判断是插入还是修改
        if (status) {
            if (user.userId > 0) {
                val ss = mainService.tokenService.insertToken(user)
                if (ss > 0) {
                    logger.info("插入token成功")
                } else {
                    logger.info("插入token失败")
                }
            }
        } else {
            if (user.userId > 0) {
            val sta = mainService.tokenService.updateUserById(user)
            logger.info(if (sta > 0) "修改成功" else "修改失败")
            }
        }
    }

    /**
     * 存储Seesion-UserId
     */
    fun getSessionUserId(request: HttpServletRequest): Int {
        val token = request.getHeader("token")
        val userid = request.getHeader("userId")
        if (userid.isNullOrBlank()) return -1
        if (token.isNullOrBlank()) return -2
        //查询所有token
        val queryAll = mainService.tokenService.queryToken(userid.toInt())
        if (token == queryAll.token) {
            return 1
        } else {
            return -3
        }
    }

    /**
     * 存储Seesion-UserId
     */
    fun getSessionToken(token: String): Int {
        try {
            if (token.isNullOrBlank()) return -2
            //查询所有token
            val queryAll = mainService.tokenService.queryOrToken(token)
            if (queryAll == null || queryAll.token.isNullOrBlank()) return -2
            if (token == queryAll.token) {
                return 1
            } else {
                return -2
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return -4
        }


    }

}