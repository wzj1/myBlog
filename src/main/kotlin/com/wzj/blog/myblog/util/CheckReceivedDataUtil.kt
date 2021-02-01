package com.wzj.blog.myblog.util

import com.google.gson.*
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.result.Result
import lombok.extern.slf4j.Slf4j
import org.apache.http.util.TextUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.reflect.Type
import java.util.*


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
            JsonParser().parse(json)
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
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(ClassData::class.java, ClassDataSerializer())
            val gson = gsonBuilder.create()
            val tc = gson.fromJson(json, t::class.java)
            logger.info("JSON 映射成功")
            return tc as T

        } catch (e: Exception) {
            e.printStackTrace()
            logger.error(e.message)
            return null
        }

    }

    internal class ClassData(var jsonString: String, var classType: Class<*>)

    internal class ClassDataSerializer : JsonSerializer<ClassData?> {

        override fun serialize(src: ClassData?, p1: Type?, p2: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(src?.jsonString)
        }
    }
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