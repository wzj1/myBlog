package com.wzj.blog.myblog.entity

import com.google.gson.annotations.Expose
import com.sun.xml.internal.ws.developer.Serialization



open class ResultData<T>{
    @Expose
    var data: T? = null
}