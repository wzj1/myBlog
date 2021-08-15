package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.entity.UserInfo
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.SeesionUtil
import lombok.extern.slf4j.Slf4j
import net.sf.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * 登录 修改状态 忘记密码
 */
@Controller
@CrossOrigin
@Slf4j
open class LoginController {

    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(LoginController::class.java)

    /**
     * 登录
     *
     */
//    @RequestMapping("/login",method = [RequestMethod.POST],produces = ["application/json;charset=UTF-8"])

    @PostMapping("/login")
    @ResponseBody
    fun login(@RequestBody data: String?, request : HttpServletRequest):JSONObject{



        if (CheckReceivedDataUtil.JsonToClass1<ResultData<UserInfo>>(data)==null) return Result.failure300ToJSON("格式错误!!!")
        val login = CheckReceivedDataUtil.JsonToClass1<UserInfo>(data)

//        logger.info(resultData.toString())
//        if (resultData === null )  return Result.failure300ToJSON("参数缺失 data 字段")
//        if (resultData.data === null )  return Result.failure300ToJSON("参数缺失")
////        val result = CheckReceivedDataUtil.JsonToClass1<UserInfo>(resultData.data)
//        val login= resultData.data!!
         logger.info(login.toString())
        if (login === null )  return Result.failure300ToJSON("参数缺失")
        if (login.userName.isNullOrBlank())return Result.failure300ToJSON("用户名不能为空!!!")
        if (login.userPwd.isNullOrBlank())return Result.failure300ToJSON("用户密码不能为空!!!")
        if (login.userPwd!!.length<6)return Result.failure300ToJSON("密码长度不能少于6位字符")
        //检查是否有此用户
        val logins = mainService.userService.queryUserByLoginName(login.userName!!)
        if (logins.size<=0) return Result.failure300ToJSON("该用户未注册，请先注册")
        //判断用户名密码
        val loginEntity = logins[0]
        if (login.userName !=loginEntity.userName)return Result.failure300ToJSON("用户名或密码不正确")
        if (login.userPwd !=loginEntity.userPwd)return Result.failure300ToJSON("用户名或密码不正确")
        //查询好友表中好友ID为此用户ID  并修改状态为在线状态
        val updateFriendsStatus = mainService.userFriendsService.updateFriendsStatus(login.userId, 0)
        if (updateFriendsStatus<=0) Result.log("修改状态失败!!!")
        //返回好友列表
        val queryFriendsByUserId = mainService.userFriendsService.queryFriendsByUserId(login.userId)
        loginEntity.friendsData=Gson().toJson(queryFriendsByUserId)
        SeesionUtil.setSessionUserId(request,loginEntity.userId)
        return Result.success200ToJSON(JSONObject.fromObject(loginEntity), "登录成功!!!")
    }


    /**
     * 修改登录状态
     */
    @ResponseBody
    @RequestMapping(value = ["/updateLogin"],method = [RequestMethod.POST])
    fun updateLogin(@ModelAttribute("data") data: String?,request : HttpServletRequest):String{

        if (CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java,data)==null) return Result.failure300("格式错误!!!")
        val login = CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")

        //查询好友表中好友ID为此用户ID  并修改状态为在线状态
        val updateFriendsStatus = mainService.userFriendsService.updateFriendsStatus(login?.userId!!, 3)
        if (updateFriendsStatus<=0) {
            Result.log("修改状态失败!!!")
            return Result.failure300("修改状态失败!!!")
        }
        //返回好友列表
        val queryFriendsByUserId = mainService.userFriendsService.queryFriendsByUserId(login.userId)

        return Result.success200("修改成功!!!")

    }


}