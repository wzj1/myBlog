package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose
import com.sun.xml.internal.ws.developer.Serialization



open class ResultData<T>{
    var data: T? = null

    override fun toString(): String {
        val sb = StringBuffer()
        sb.append("开始 \r\n")
        sb.append("data :  ${data.toString()} \r\n")
        return sb.toString()
    }
}