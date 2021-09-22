package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.UserTokenEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Mapper
@Repository
 interface UserTokenMapper {

    /**
     *  token
     */


    //插入 token
    @Insert("insert into  ${SqlUtil.userToken_Table}(USERID,REGISTER_TIME,DUETO_TIME,FAILURENUM,TOKEN,STATUS) values(#{userId},#{register_time},#{dueto_time},#{failurenum},#{token},#{status})")
    fun insertToken(token: UserTokenEntity):Int

    //修改所有数据
    @Update("update ${SqlUtil.userToken_Table} set USERID=#{userId},REGISTER_TIME=#{register_time},DUETO_TIME=#{dueto_time},FAILURENUM=#{failurenum} ,TOKEN=#{token} ,STATUS=#{status} where USERID=#{userId}")
    fun updateUserById(userId: UserTokenEntity):Int

    //修改 token 和登录状态
    @Update("update ${SqlUtil.userToken_Table} set TOKEN=#{token} ,STATUS=#{status} where USERID=#{userId}")
    fun updateUserByIdToTokenOrStatus(userId:Int,token: String,status:Int):Int

    //修改 token
    @Update("update ${SqlUtil.userToken_Table} set TOKEN=#{token} where USERID=#{userId}")
    fun updateUserByIdToToken(userId:Int,token: String):Int


    //查询
    @Select("select * from  ${SqlUtil.userToken_Table}  where USERID=#{userId}")
    fun queryToken(userId:Int):UserTokenEntity

    //查询
    @Select("select * from  ${SqlUtil.userToken_Table}  where TOKEN=#{token}")
    fun queryOrToken(token: String):UserTokenEntity


    //查询
    @Select( "select * from  ${SqlUtil.userToken_Table}  where STATUS=#{status}")
    fun querySatatus(status:Int):MutableList<UserTokenEntity>


    //查询所有
    @Select("select * from  ${SqlUtil.userToken_Table}")
    fun queryAll():MutableList<UserTokenEntity>



}