package com.wzj.blog.myblog.util

import com.wzj.blog.myblog.config.Constant
import javax.servlet.http.HttpServletRequest

/**
 *
 */
object SeesionUtil {

    /**
     * 存储Seesion-UserId
     */
    fun  setSessionUserId(request : HttpServletRequest,userid: Int?){
        val session = request.session
        session.setAttribute(Constant.USER_ID,userid)
    }

    /**
     * 存储Seesion-UserId
     */
    fun  getSessionUserId(request : HttpServletRequest):Int?{
        val session = request.session
      return session.getAttribute(Constant.USER_ID).toString().toInt()
    }



}