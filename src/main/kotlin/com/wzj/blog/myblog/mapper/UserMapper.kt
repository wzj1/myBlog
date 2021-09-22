package com.wzj.blog.myblog.mapper

import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.util.SqlUtil
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
 interface UserMapper {
    /**
     * 注册用户
     */
    @Insert("insert into ${SqlUtil.userInfo_Table}(USERIP,USERNAME,USERPWD,USEREMAIL,USERPROFILEPHOTO,USERPHONE,USERREGISTRATIONTIME,USERBIRTHDAY,USERAGE,USERNICKNAME,USERCONSTELLATION) values(#{userIp},#{userName},#{userPwd},#{userEmail},#{userProFilePhoto},#{userPhone},#{userRegisrAtionTime},#{userBirthDay},#{userAge},#{userNickName},#{userConstellAtion})")
    fun insertUser(user: LoginEntity):Int

    @Update("update ${SqlUtil.userInfo_Table} set USERIP=#{userIp},USEREMAIL=#{userEmail},USERPROFILEPHOTO=#{userProFilePhoto},USERPHONE=#{userPhone} ,USERREGISTRATIONTIME=#{userRegisrAtionTime} ,USERBIRTHDAY=#{userBirthDay} ,USERAGE=#{userAge} ,USERNICKNAME=#{userNickName},USERCONSTELLATION=#{userConstellAtion} where USERID=#{userId}")
    fun updateUserById(userId: LoginEntity):Int

    @Update("update ${SqlUtil.userInfo_Table} set USERPWD=#{newUserPwd} where USERNAME=#{userName}")
    fun updateUserPwdByName(userName: String,newUserPwd:String): Int

    @Update("update ${SqlUtil.userInfo_Table} set USERPWD=#{newUserPwd} where USERID=#{userId}")
    fun updateUserPwdById(userId: Int,newUserPwd:String): Int

    @Update("update ${SqlUtil.userInfo_Table} set USERIP=#{userIp},USEREMAIL=#{userEmail},USERPROFILEPHOTO=#{userProFilePhoto},USERPHONE=#{userPhone} ,USERREGISTRATIONTIME=#{userRegisrAtionTime} ,USERBIRTHDAY=#{userBirthDay} ,USERAGE=#{userAge} ,USERNICKNAME=#{userNickName},USERCONSTELLATION=#{userConstellAtion} where USERNAME=#{userName}")
    fun updateUserByName(userName: LoginEntity):Int

    @Select("select * from ${SqlUtil.userInfo_Table} where  USERID=#{userId}")
    fun queryUserById(userId: Int):LoginEntity


   @Select("select * from ${SqlUtil.userInfo_Table} where  userPhone=#{phone}")
   fun queryUserByPhone(phone: String):LoginEntity



   @Select("select * from ${SqlUtil.userInfo_Table} where USERNAME like '%#{userName}%'")
    fun queryUserLikeByName(userName: String):MutableList<LoginEntity>


   @Select("select * from ${SqlUtil.userInfo_Table}")
   fun queryAll():MutableList<LoginEntity>

    @Select("select * from ${SqlUtil.userInfo_Table} where USERNAME=#{userName}")
    fun queryUserByName(userName: String):MutableList<LoginEntity>

    @Select("select * from ${SqlUtil.userInfo_Table} where USERNAME=#{userName}")
    fun queryUserByLoginName(userName: String):MutableList<LoginEntity>

    @Delete("delete ${SqlUtil.userInfo_Table} where UserName=#{userName}")
    fun deleteUserByName(userName: String):Int

    @Select("delete * from ${SqlUtil.userInfo_Table} where  USERID=#{userId}")
    fun deleteUserById(userId: Int):Int

    @Select("#{sql}")
    fun deleteExtSql(sql: String):Int

    @Update("#{sql}")
    fun updateExtSql(sql: String):Int

    @Select("#{sql}")
    fun selectExtSql(sql: String):Int








}