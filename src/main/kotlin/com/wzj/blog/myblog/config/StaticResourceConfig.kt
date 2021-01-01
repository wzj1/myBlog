package com.wzj.blog.myblog.config

import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.io.IOException
import java.io.PrintWriter
import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Slf4j
open class StaticResourceConfig : HandlerInterceptor {
    private val SF = SimpleDateFormat("yyyyMMddHHmmss")

    var logger: Logger = LoggerFactory.getLogger(StaticResourceConfig::class.java)
    //时间戳
    val cache = TimeUtil.getDate("yyyyMMddHHmmssSSS")

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // 请求头校验
        val timestamp = request.getHeader("timestamp")
        val sign = request.getHeader("sign")
        println(request.requestURI)
        logger.info("timestamp：$timestamp $\r\n")
        logger.info("sign：$sign $\r\n")
        if (timestamp == null || timestamp.trim { it <= ' ' } == "" || sign == null || sign.trim { it <= ' ' } == "") {
            logger.error("请求头缺失")
            getResponse("请求头缺失", response)
            return false
        }

        if (request.requestURI!="login") {
            if (request.session.getAttribute(Constant.USER_ID).toString().isNullOrBlank()){
                getResponse("登录状态失效，请重新登录", response)
                return false
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
        var out: PrintWriter? = null
        out = response.writer
        out.write(Result.failure(Constant.ERROR_CLEAR,result))
        out.flush()
        out.close()
    }




}