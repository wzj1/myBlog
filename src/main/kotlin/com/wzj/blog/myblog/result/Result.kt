package com.wzj.blog.myblog.result

import com.google.gson.Gson
import com.alibaba.fastjson.JSON
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.util.key.RSAUtil1
import net.sf.json.JSONObject
import java.util.regex.Pattern

/**
 * 返回数据 封装类
 */
class Result {

    companion object{
        var json: MutableMap<String, Any?> = hashMapOf()
        fun success200(data: HashMap<String, Any?>, msg: String?): String {
            json = hashMapOf()
            json["code"] = 200
            json["msg"] = msg
            return log(JSONObject.fromObject(json))
        }


        fun success200(data: String, msg: String): String {

            json = hashMapOf()
            json["code"] = 200
            json["data"] = JSONObject.fromObject(data)
            json["msg"] = msg
            return log(JSONObject.fromObject(json))
        }

        fun success200(msg: String?): String {
            json = hashMapOf()
            json["code"] = 200
            json["msg"] = msg
            return log(JSONObject.fromObject(json))
        }

        fun success200(): String {
            json = hashMapOf()
            json["code"] = 200
            json["msg"] = "成功"
            return log(JSONObject.fromObject(json))
        }


        fun failure300(data: HashMap<String, Any?>, msg: String?): String {
            json = hashMapOf()
            json["code"] = 300
            json["msg"] = msg
            return log(JSONObject.fromObject(json))
        }

        fun failure(code: String?, msg: String?): String {
            json = hashMapOf()
            json["code"] = if (code.isNullOrBlank()) 300 else code.toInt()
            json["msg"] = msg
            return log(JSONObject.fromObject(json))

        }


        fun failure300(data: String?, msg: String?): String {
            json = hashMapOf()
            json["code"] = 300
            json["data"] = JSONObject.fromObject(data)
            json["msg"] = msg
            return log(JSONObject.fromObject(json))
        }

        fun failure300(msg: String?): String {
            json = hashMapOf()
            json["code"] = 300
            json["msg"] = msg
            return log(JSONObject.fromObject(json))
        }


        fun failure300(): String {
            json = hashMapOf()
            json["code"] = 300
            json["msg"] = "失败"
            return log(JSONObject.fromObject(json))
        }

        fun log(json: JSONObject): String {
//        println("json:${repalceAll("\\\\", json.toString())}")
            println("json:${json.toString()}")

//        val data = RSAUtil1.encryptByPublic(repalceAll("\\\\", json.toString()))
////        val data = RSAUtil1.encryptByPublic(json.toString())
//        println("密文数据:${data}")
            return repalceAll("\\\\", json.toString())
        }

        fun log(json: String): String {
//        println("json:${repalceAll("\\\\", json)}")
            println("json:${json}")

//        val data = RSAUtil1.encryptByPublic(repalceAll("\\\\", json))
//        val data = RSAUtil1.encryptByPublic(json)
//        println("密文数据:${data}")
            return  repalceAll("\\\\", json)
        }

        private fun log(tag: String, json: String): String {
            println("$tag:${repalceAll("\\\\", json)}")
            return repalceAll("\\\\", json)
        }

        /**
         * 去除转义字符
         */
        private fun repalceAll1(expr: String?, substitute: String?): JSONObject {
            return JSONObject.fromObject(Pattern.compile(expr).matcher("\\").replaceAll(substitute))
        }

        /**
         * 去除转义字符
         */
        private fun repalceAll(expr: String?, substitute: String?): String {
            return Pattern.compile(expr).matcher("\\").replaceAll(substitute)!!
        }
    }





}