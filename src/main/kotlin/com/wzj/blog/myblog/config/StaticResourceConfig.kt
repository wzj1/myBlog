package com.wzj.blog.myblog.config

import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.SeesionUtil
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.io.IOException
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Slf4j
open class StaticResourceConfig : HandlerInterceptor {
    @Autowired
    lateinit var mainService: MainService

    private val SF = SimpleDateFormat("yyyyMMddHHmmss")
    var logger: Logger = LoggerFactory.getLogger(StaticResourceConfig::class.java)

    //时间戳
    val cache = TimeUtil.getDate("yyyyMMddHHmmssSSS")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        SeesionUtil.instance.mainService = mainService
        // 请求头校验
        val timestamp = request.getHeader("timestamp")
        val sign = request.getHeader("sign")
        val token = request.getHeader("token")
        val TRUE = if (request.getHeader("TRUE").isNullOrBlank())false else true
        try {
            Constant.version = request.getHeader("version").toInt()
        } catch (e: Exception) {
            logger.error(e.message)
        }


        println(request.requestURI)
        logger.info("timestamp：$timestamp $\r\n")
        logger.info("sign：$sign $\r\n")
        logger.info("token：$token $\r\n")
        if (!TRUE) {
            if (request.requestURI != "/login" && request.requestURI != "/outLogin" && request.requestURI != "/user/registered" && request.requestURI != "/user/registeredPhone" && request.requestURI != "/downLoad") {
                if (timestamp == null || timestamp.trim { it <= ' ' } == "" || sign == null || sign.trim { it <= ' ' } == "") {
                    logger.error("请求头缺失")
                    getResponse("请求头缺失", response)
                    return false
                }

                try {
                    when (SeesionUtil.instance.getSessionToken(token)) {
                        -1 -> {
                            getResponse("缺少必要参数用户ID", response)
                            return false
                        }
                        -2 -> {
                            getResponse("token不能为空", response)
                            return false
                        }
                        -3 -> {
                            getResponse("token失效，请重新登录", response)
                            return false
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        }

        logger.info("StaticResourceConfig---requestURI", "${request.requestURI}")
        logger.info("StaticResourceConfig", "${super.preHandle(request, response, handler)}")
        return true
    }


    /**
     * 构造响应消息体
     *
     * @param result
     * @param response
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getResponse(result: String, response: HttpServletResponse) {
        response.characterEncoding = "UTF-8"
        response.contentType = "application/json; charset=utf-8"
        var out = response.writer
        out.write(Result.failure(Constant.ERROR_CLEAR, result))
        out.flush()
        out.close()
    }


}