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
class Result1 {

    companion object{

        var ent  = BaseData<Any?>()

        /**
         * 返回成功
         * @param msg 返回说明
         * @return 封装固定对象
         */
        fun success200(msg: String?): BaseData<Any?> {
            var ent  = BaseData<Any?>()
            ent.code = 200
            ent.msg = msg
            return ent
        }

        /**
         * 返回成功
         * @param t 数据对象 可空对象
         * @param msg 返回说明
         * @return 封装固定对象
         */
        fun <T> success200(t:T?,msg: String?): BaseData<Any?> {
            var ent  = BaseData<Any?>()
            ent.code = 200
            ent.msg = msg
            ent.data = Gson().toJsonTree(t).asJsonObject
            return ent
        }

        /**
         * 返回成功
         * @param t 数据对象数组 可空对象
         * @param msg 返回说明
         * @return 封装固定对象
         */
        fun <T> success200(t:MutableList<T>?,msg: String?): BaseData<Any?> {
            var ent  = BaseData<Any?>()
            ent.code = 200
            ent.msg = msg
            ent.data = Gson().toJsonTree(t).asJsonArray
            return ent
        }

        /**
         * 返回失败
         * @param t 数据对象 可空对象
         * @param msg 返回说明
         * @return 封装固定对象
         */
        fun <T> failure300(t:T?,msg: String?): BaseData<Any?> {
            var ent  = BaseData<Any?>()
            ent.code = 300
            ent.msg = msg
            ent.data = Gson().toJsonTree(t).asJsonObject
            return ent
        }
        /**
         * 返回失败
         * @param t 数据对象数组 可空对象
         * @param msg 返回说明
         * @return 封装固定对象
         */
        fun <T> failure300(t:MutableList<T>?,msg: String?): BaseData<Any?> {
            var ent  = BaseData<Any?>()
            ent.code = 300
            ent.msg = msg
            ent.data = Gson().toJsonTree(t).asJsonArray
            return ent
        }
        /**
         * 返回失败
         * @param msg 返回说明
         * @return 封装固定对象
         */
        fun failure300(msg: String?): BaseData<Any?> {
            ent.code = 300
            ent.msg = msg
            return ent
        }
    }





}