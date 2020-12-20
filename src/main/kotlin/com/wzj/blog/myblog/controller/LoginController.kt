package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.returnUtil.GetResultData
import com.wzj.blog.myblog.service.UserFriends.UserFriendsService
import com.wzj.blog.myblog.service.UserService.UserService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
open class LoginController {
    //好友表 登录更改状态
    @Autowired
    lateinit var userFriendsService: UserFriendsService

    //用户表  查询 用户信息
    @Autowired
    lateinit var userService: UserService

    @ResponseBody
    @PostMapping(value = ["/login"], consumes = ["application/json"])
    fun login(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val login = Gson().fromJson(data!!.data, LoginEntity::class.java)
        if (login.userName.isNullOrBlank())return GetResultData.failure300("用户名不能为空!!!")
        if (login.userPwd.isNullOrBlank())return GetResultData.failure300("用户密码不能为空!!!")
        if (login.userPwd!!.length<6)return GetResultData.failure300("密码长度不能少于6位字符")
        //检查是否有此用户
        val queryUserByName = userService.queryUserByLoginName(login.userName!!)
        if (queryUserByName.size<=0) return GetResultData.failure300("该用户未注册，请先注册")
        //判断用户名密码
        val loginEntity = queryUserByName[0]
        if (login.userName !=loginEntity.userName)return GetResultData.failure300("用户名或密码不正确")
        if (login.userPwd !=loginEntity.userPwd)return GetResultData.failure300("用户名或密码不正确")
        //查询好友表中好友ID为此用户ID  并修改状态为在线状态
        val updateFriendsStatus = userFriendsService.updateFriendsStatus(login.userId, 0)
        if (updateFriendsStatus<=0) GetResultData.log("修改状态失败!!!")
        //返回好友列表
        val queryFriendsByUserId = userFriendsService.queryFriendsByUserId(login.userId)
        loginEntity.friendsData=Gson().toJson(queryFriendsByUserId)
        return GetResultData.success200(Gson().toJson(loginEntity),"登录成功!!!")

    }


    /**
     * 修改登录状态
     */
    @ResponseBody
    @PostMapping(value = ["/updateLogin"], consumes = ["application/json"])
    fun updateLogin(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas
        val login = Gson().fromJson(data!!.data, LoginEntity::class.java)

        //查询好友表中好友ID为此用户ID  并修改状态为在线状态
        val updateFriendsStatus = userFriendsService.updateFriendsStatus(login.userId, 3)
        if (updateFriendsStatus<=0) {
            GetResultData.log("修改状态失败!!!")
            return GetResultData.failure300("修改状态失败!!!")
        }
        //返回好友列表
        val queryFriendsByUserId = userFriendsService.queryFriendsByUserId(login.userId)

        return GetResultData.success200("修改成功!!!")

    }









}