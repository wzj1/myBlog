package com.wzj.blog.myblog.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.result.Result
import lombok.extern.slf4j.Slf4j
import net.sf.json.JSONObject
import net.sf.json.JsonConfig
import org.apache.http.util.TextUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.HashMap





@Slf4j
object CheckReceivedDataUtil {
    var logger: Logger = LoggerFactory.getLogger(CheckReceivedDataUtil::class.java)

    fun IsCheckReceivedDataNull(data: ResultData<String>?): String? {
        if (data == null) return Result.failure300("参数异常")
        if (data.data.isNullOrBlank()) return Result.failure300("参数缺失!!!")
        if (isBadJson(data.data)) return Result.failure300("不是json格式!!!")
        return null
    }


    fun isBadJson(json: String?): Boolean {
        return isGoodJson(json)
    }

    fun isGoodJson(json: String?): Boolean {
        return if (TextUtils.isEmpty(json)) {
            false
        } else try {
            JSONObject.fromObject(json)
            true
        } catch (e: JsonSyntaxException) {
            false
        } catch (e: JsonParseException) {
            false
        }

    }

    fun <T> JsonToClass(t: Class<T>, json: String?): T? {
        if (!isBadJson(json)) return null
        try {
            logger.info(json)

            val asJsonObject = JsonObject().getAsJsonObject(json)

            val tc = Gson().fromJson(asJsonObject, t::class.java)
            logger.info("JSON 映射成功")
            return tc as T

        } catch (e: Exception) {
            e.printStackTrace()
            logger.error(e.message)
            return null
        }

    }

    inline fun <reified T> JsonToClass1(json: JSONObject?): T? {
        if (!isBadJson(json.toString())) return null
        try {
            logger.info(json.toString())
            val jsonConfig = JsonConfig()
            jsonConfig.rootClass = T::class.java
            val jt = JSONObject.toBean(json, jsonConfig)
            logger.info("JSON 映射成功")
            logger.info("JSON ${jt.toString()}")
            return jt as T

        } catch (e: Exception) {
            e.printStackTrace()
            logger.error(e.message)
            return null
        }

    }

    inline fun <reified T> JsonToClass1(json: String?): T? {
        if (!isBadJson(json)) return null
        try {
            logger.info(json)
//          val jsonObj =   JSONObject.fromObject(json)
//
//            val classMap: MutableMap<String, Class<*>> = HashMap()
//            classMap["stuList"] = T::class.java
//
//            val jt = JSONObject.toBean(jsonObj, T::class.java,classMap)
            logger.info("JSON 映射成功")
//            logger.info("JSON ${jt.toString()}")
//            return jt as T

           return Gson().fromJson(json,T::class.java)


        } catch (e: Exception) {
            e.printStackTrace()
            logger.error(e.message)
            return null
        }

    }
//
//    internal class ClassData(var jsonString: String, var classType: Class<*>)
//
//    internal class ClassDataSerializer : JsonSerializer<ClassData?> {
//
//        override fun serialize(src: ClassData?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
//            return JsonPrimitive(src?.jsonString)
//        }
//    }
//
//    @JvmStatic
//    fun main(args: Array<String>) {
//        val gsonBuilder = GsonBuilder()
//        gsonBuilder.registerTypeAdapter(ClassData::class.java, ClassDataSerializer())
//        val map = HashMap<String, ClassData>()
//        map["key"] = ClassData("key", String::class.java)
//        val gson = gsonBuilder.create()
//        val json = gson.toJson(map)
//        println(json)
//    }

}