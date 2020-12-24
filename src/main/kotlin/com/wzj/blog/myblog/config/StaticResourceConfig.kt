package com.wzj.blog.myblog.config

import com.wzj.blog.myblog.returnUtil.GetResultData
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



open class StaticResourceConfig : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        println(request.requestURI)
        val obj = request.session.getAttribute("session_user")

        GetResultData.log("StaticResourceConfig---requestURI","${request.requestURI}")
//
//        if (request.requestURI == "/image") {
//            GetResultData.log("StaticResourceConfig---image","true")
//            return true
//        }

        GetResultData.log("StaticResourceConfig","${super.preHandle(request, response, handler)}")
        return super.preHandle(request, response, handler)
    }


}