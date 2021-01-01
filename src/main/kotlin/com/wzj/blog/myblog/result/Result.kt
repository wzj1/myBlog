package com.wzj.blog.myblog.result

import com.google.gson.JsonObject
import java.util.regex.Pattern

/**
 * 返回数据 封装类
 */
object Result {
    var json: JsonObject = JsonObject()

    fun success200(data: HashMap<String, Any?>, msg: String?): String {
        json.addProperty("code","200")
        json.addProperty("msg",msg)
        return json.toString()
    }

    fun success200(data:String?,msg: String?): String {
        json =JsonObject()
        json.addProperty("code","200")
        json.addProperty("data",data)
        json.addProperty("msg",msg)
        return log(json.toString())
    }

    fun success200(msg: String?): String {
        json =JsonObject()
        json.addProperty("code","200")
        json.addProperty("msg",msg)
        return log(json.toString())
    }

    fun success200(): String {
        json =JsonObject()
        json.addProperty("code","200")
        json.addProperty("msg","成功")
        return log(json.toString())
    }


     fun failure300(data: HashMap<String, Any?>, msg: String?): String {
         json =JsonObject()
         json.addProperty("code","300")
         json.addProperty("msg",msg)
         return log(json.toString())
    }

     fun failure(code:String?,data: HashMap<String, Any?>, msg: String?): String {
         json =JsonObject()
         json.addProperty("code",code)
         json.addProperty("msg",msg)
         return log(json.toString())
    }

     fun failure(code:String?, msg: String?): String {
         json =JsonObject()
         json.addProperty("code",code)
         json.addProperty("msg",msg)
         return log(json.toString())
    }



    fun failure300(data:String?,msg: String?): String {
        json =JsonObject()
        json.addProperty("code","300")
        json.addProperty("data",data)
        json.addProperty("msg",msg)
        return log(json.toString())
    }

    fun failure300(msg: String?): String {
        json =JsonObject()
        json.addProperty("code","300")
        json.addProperty("msg",msg)
        return log(json.toString())
    }

    fun failure300(): String {
        json =JsonObject()
        json.addProperty("code","300")
        json.addProperty("msg","失败")
        return log(json.toString())
    }

    fun  log(json: String):String{
        println("json:${repalceAll("\\\\",json)}")
        return repalceAll("\\\\",json)
    }

    fun  log(tag: String, json: String):String{
        println("$tag:${repalceAll("\\\\",json)}")
        return repalceAll("\\\\",json)
    }

    /**
     * 去除转义字符
     */
    fun repalceAll(expr: String?, substitute: String?): String {
        return  Pattern.compile(expr).matcher("\\").replaceAll(substitute);
    }

}