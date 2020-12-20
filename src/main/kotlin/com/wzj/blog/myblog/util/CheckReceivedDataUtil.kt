package com.wzj.blog.myblog.util

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.returnUtil.GetResultData
import org.apache.http.util.TextUtils


object CheckReceivedDataUtil {

    fun IsCheckReceivedDataNull(data: ResultData<String>?):String?{
        if (data==null) return GetResultData.failure300("参数异常")
        if (data.data.isNullOrBlank()) return GetResultData.failure300("参数缺失!!!")
        if (isBadJson(data.data)) return GetResultData.failure300("不是json格式!!!")
        return null
    }


    fun isBadJson(json: String?): Boolean {
        return !isGoodJson(json)
    }

    fun isGoodJson(json: String?): Boolean {
        return if (TextUtils.isEmpty(json)) {
            false
        } else try {
            JsonParser().parse(json)
            true
        } catch (e: JsonSyntaxException) {
            false
        } catch (e: JsonParseException) {
            false
        }

    }
}