package com.wzj.blog.myblog.xxjob.shuaxintoken.jobhandler

import com.google.gson.Gson
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.TokenEncryptUtils
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import com.xxl.job.core.biz.model.ReturnT
import com.xxl.job.core.handler.annotation.XxlJob
import kotlin.Throws

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.Exception

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
</String> */
//@Component
open class SampleXxlJob {
    var logger: Logger = LoggerFactory.getLogger(SampleXxlJob::class.java)

    @Autowired
    lateinit var mainService: MainService

//    /**
//     * 1、简单任务示例（Bean模式）
//     */
//    @XxlJob("tokenJobHandler")
//    @Throws(Exception::class)
//    fun tokenJobHandler(param: String?): ReturnT<String> {
//
//        val sb = StringBuffer()
//
//        //查询所有token
//        val queryAll = mainService.tokenService.querySatatus(1)
//
//        //循环遍历
//        for (user in queryAll) {
//            val parseJWT = TokenEncryptUtils.parseJWT(user.token)
//            //是否满足条件
//            if (!parseJWT) {
//
//                val userinfo = mainService.userService.queryUserById(user.userId)
//                val timeMisll:Long = TimeUtil.getTimeMillis()
//                val time = TimeUtil.getDate(timeMisll)
//
//                sb.append("生成时间:$time \r\n")
//                user.register_time =  TimeUtil.stampToDateTime(TimeUtil.getNextDay(time,0)!!.time)
//                //到期时间以时间配置 * 一天的时间戳 + 开始时间 得到结束时间
//                user.dueto_time = TimeUtil.stampToDateTime(TimeUtil.getNextDay(time,user.failurenum.toInt())!!.time)
//                sb.append("结束时间: ${user.dueto_time} \r\n")
//                sb.append("用户ID: ${user.userId} \r\n")
//                val map = hashMapOf<String, Any>()
//                map["userPhone"] = userinfo.userPhone.toString()
//                map["userName"] = userinfo.userName.toString()
//                map["userPwd"] = userinfo.userPwd.toString()
//                map["userId"] = userinfo.userPwd.toString()
//                user.token = TokenEncryptUtils.createJWT(user.userId.toString(),
//                    Gson().toJson(map),time.time,TimeUtil.getNextDay(time,user.failurenum.toInt())!!.time)
//                sb.append("token: ${user.token} \r\n")
//                user.status = 0
//                val t = mainService.tokenService.updateUserById(user)
//                if (t > 0) {
//                    logger.info("${user.userId}  满足条件更新成功")
//                } else {
//                    logger.info("${user.userId}  更新失败")
//                }
//                logger.info(sb.toString())
//            } else {
//                logger.info("${user.userId} 不满足条件")
//            }
//        }
//
//        return ReturnT.SUCCESS
//    }

    fun init() {
        logger.info("init")
    }

    fun destroy() {
        logger.info("destory")
    }


}