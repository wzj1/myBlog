package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.config.rsaUtil.annotation.Decrypt
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.entity.UserTokenEntity
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.SeesionUtil
import com.wzj.blog.myblog.util.key.RSAUtil1
import lombok.extern.slf4j.Slf4j
import net.sf.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.math.BigInteger
import javax.servlet.http.HttpServletRequest

/**
 * 登录 修改状态 忘记密码
 */
@Controller
@CrossOrigin
@Slf4j
open class LoginController {
    protected var logger: Logger = LoggerFactory.getLogger(LoginController::class.java)
    @Autowired
    protected lateinit var mainService: MainService
    /**
     * 登录
     *
     */
//    @RequestMapping("/login",method = [RequestMethod.POST],produces = ["application/json;charset=UTF-8"])

    @PostMapping("/login")
    @Decrypt
    @Encrypt
    @ResponseBody
    fun login(@RequestBody data: String?, request : HttpServletRequest): BaseData<Any?> {

        if (data.isNullOrBlank())  return Result1.failure300("数据异常")
        val login = Gson().fromJson(data,LoginEntity::class.java)

         logger.info(login.toString())
        if (login === null )  return Result1.failure300("参数缺失")
        if (login.userName.isNullOrBlank())return Result1.failure300("用户名不能为空!!!")
        if (login.userPwd.isNullOrBlank())return Result1.failure300("用户密码不能为空!!!")
        if (login.userPwd!!.length<6)return Result1.failure300("密码长度不能少于6位字符")

        //检查是否有此用户
        val logins = mainService.userService.queryUserByLoginName(login.userName!!)
        if (logins.size<=0) return Result1.failure300("该用户未注册，请先注册")

        //判断用户名密码
        val loginEntity = logins[0]
        if (login.userPwd !=loginEntity.userPwd)return Result1.failure300("用户名或密码不正确")
        //查询好友表中好友ID为此用户ID  并修改状态为在线状态
        val updateFriendsStatus = mainService.userFriendsService.updateFriendsStatus(login.userId, 0)

        try {
            // 返回用户头像
            val imageEntity = mainService.imageService.queryByUserId(loginEntity.userId)
            if (imageEntity.size>0) {
               val  imageent = imageEntity[0]
                if (!imageent.image_path.isNullOrBlank()) {
                    loginEntity.userProFilePhoto = "http://${imageent.image_path}"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //返回好友列表
        val queryFriendsByUserId = mainService.userFriendsService.queryFriendsByUserId(loginEntity.userId)
        if (queryFriendsByUserId.size>0){
            loginEntity.friendsData=Gson().toJson(queryFriendsByUserId)
        }


        SeesionUtil.instance.setSessionUserId(request,loginEntity.userId)
       val token =  mainService.tokenService.queryToken(loginEntity.userId)
        loginEntity.token = token.token
        loginEntity.status = token.status
       val json =  JSONObject.fromObject(loginEntity)
        json.remove("userPwd")
        json.remove("userIp")
        return Result1.success200(loginEntity, "登录成功!!!")
    }


    /**
     * 修改登录状态
     */
    @Decrypt
    @Encrypt
    @ResponseBody
    @RequestMapping(value = ["/updateLogin"],method = [RequestMethod.POST])
    fun updateLogin(@ModelAttribute("data") data: String?,request : HttpServletRequest):String{
        if (CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val login = CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java, data)
        val sessionUserId = SeesionUtil.instance.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")

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


    /**
     * 退出登录
     */
    @ResponseBody
    @Decrypt
    @Encrypt
    @RequestMapping(value = ["/outLogin"],method = [RequestMethod.POST])
    fun outLogin(request : HttpServletRequest):BaseData<Any?>{
       val token =  request.getHeader("token")
        if (token==null) return Result1.failure300("缺少token!!!")
        val ent =  mainService.tokenService.queryOrToken(token)
        if (ent ==null )  return Result1.failure300("用户不存在!!!")
        if (ent.userId<=0)  return Result1.failure300("退出失败!!!")
        //查询好友表中好友ID为此用户ID  并修改状态为在线状态
        val queryFriendsByUserId1 = mainService.userFriendsService.queryFriendsByUserId(ent.userId)
        if (queryFriendsByUserId1.size>0){
           mainService.userFriendsService.updateFriendsStatus(ent.userId, 3)
        }
        ent.status = 3
        mainService.tokenService.updateUserById(ent)
        return Result1.success200("退出成功!!!")

    }


    private fun makeToken(userInfo: LoginEntity){
        if (userInfo.userPwd.isNullOrBlank()) return
        if (userInfo.userId<=0) return

        val tokenEntity = mainService.tokenService.queryToken(userInfo.userId)
        val token = BigInteger(userInfo.userPwd?.toByteArray()).toString(16)
        if (tokenEntity.userId<=0){
            val userToken = UserTokenEntity()
            userToken.userId = userInfo.userId
            userToken.status = 0
            userToken.token = token
            mainService.tokenService.insertToken(userToken)
        }else{
            mainService.tokenService.updateUserByIdToToken(tokenEntity.userId,token)
        }

    }

    private fun parseToken(tokenHex: String?): String? {
        var tokenJson:String? =null
        if (tokenHex != null) {
             tokenJson = String(BigInteger(tokenHex, 16).toByteArray()) // username_password(md5)
        }
        return tokenJson
    }

}