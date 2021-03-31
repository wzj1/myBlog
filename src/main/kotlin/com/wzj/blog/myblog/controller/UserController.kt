package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.entity.UserInfo
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.SeesionUtil
import com.wzj.blog.myblog.util.phoneUtils.PhoneUtils
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import com.wzj.blog.myblog.util.uploadImage.UploadImageUtil
import lombok.extern.slf4j.Slf4j
import net.sf.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

/**
 * 用户信息表
 *
 */
@Controller
@RequestMapping("/user")
@Slf4j
open class UserController {

    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(UserController::class.java)


    /**
     * 注册 用户
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/registered"])
    fun registered(@RequestBody data: String?,  request: HttpServletRequest): JSONObject {
        if (CheckReceivedDataUtil.JsonToClass1<ResultData<UserInfo>>(data)==null) return Result.failure300ToJSON("格式错误!!!")
        val userInfodata = CheckReceivedDataUtil.JsonToClass1<ResultData<String>>(data)
        val userInfo = CheckReceivedDataUtil.JsonToClass1<UserInfo>(userInfodata?.data)


        if (userInfo?.userName.isNullOrBlank()) return Result.failure300ToJSON("用户名不能为空!")
        if (userInfo?.userPwd.isNullOrBlank()) return Result.failure300ToJSON("用户名密码不能为空!")
        if (userInfo?.userPwd?.length!! < 6) return Result.failure300ToJSON("用户名密码不能少于6位!")
        //通过用户名查询用户是否存在
        val findByName = findByName(userInfo.userName!!, 0)
        //如果存在 则提示用户已注册
        if (findByName.size > 0) {
            return Result.failure300ToJSON("该用户已注册!!")
        }


        if (userInfo.verifyCode != "123456")  return Result.failure300ToJSON("验证码验证失败!!!")


        //注册时间
        userInfo.userRegisrAtionTime = TimeUtil.getTime()
        if (userInfo.userIp.isNullOrBlank()) userInfo.userIp = "192.168.0.106"
        if (userInfo.userEmail.isNullOrBlank()) userInfo.userEmail = ""
        if (userInfo.userProFilePhoto.isNullOrBlank()) userInfo.userProFilePhoto = ""
        if (userInfo.userBirthDay.isNullOrBlank()) userInfo.userBirthDay = ""
        if (userInfo.userConstellAtion.isNullOrBlank()) userInfo.userConstellAtion = ""

        userInfo.userNickName =userInfo.userName

        //注册用户
        val insertUser = mainService.userService.insertUser(userInfo)
        if (insertUser <= 0) return Result.failure300ToJSON("注册失败,请重试!")

        val queryUserByName = findByName(userInfo?.userPhone!!, 2)
        if (queryUserByName.size <= 0) {
            return Result.failure300ToJSON("注册失败,请重试!!")
        }
        return Result.success200ToJSON(Gson().toJson(queryUserByName[0]), "注册成功!")
    }

    /**
     * 手机号注册用户
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/registeredPhone"])
    fun registeredPhone(
        @RequestParam(value = "file", required = false) file: MultipartFile,
        @ModelAttribute("data") data: String?,
        request: HttpServletRequest
    ): String {

        if (CheckReceivedDataUtil.JsonToClass<UserInfo>(
                UserInfo::class.java,
                data
            ) == null
        ) return Result.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java, data)

        if (userInfo?.userName.isNullOrBlank()) return Result.failure300("用户名不能为空!")
        if (userInfo?.userPwd.isNullOrBlank()) return Result.failure300("用户名密码不能为空!")
        if (userInfo?.userPwd?.length!! < 6) return Result.failure300("用户名密码不能少于6位!")
        //通过用户名查询用户是否存在
        val findByName = findByName(userInfo.userName!!, 0)
        //如果存在 则提示用户已注册
        if (findByName.size > 0) {
            return Result.failure300("该用户已注册!!")
        }

        if (!file.isEmpty) {
            try {
                val uploadUtil = UploadImageUtil()
                val path = uploadUtil.uploadPicture(file, request)
                val image = ImageEntity()
                image.image_name = uploadUtil.getFileName()
                image.image_suffix = uploadUtil.getFileSuffix()
                image.image_data = uploadUtil.getFileTime()
                image.image_path = path
                image.user_id = userInfo.userId
                image.image_type = 0
                val insertImage = mainService.imageService.insertImage(image)

                if (insertImage <= 0) {
                    Result.log("上传失败")
                } else {
                    if (!path.isNullOrBlank()) {
                        //上传头像
                        userInfo.userProFilePhoto = path
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        //注册时间
        userInfo.userRegisrAtionTime = TimeUtil.getTime()

        //注册用户
        val insertUser = mainService.userService.insertUser(userInfo)
        if (insertUser <= 0) return Result.failure300("注册失败,请重试!")
        return Result.success200("注册成功!")
    }

    /**
     * 通过用户名查询用户
     * @param userName 用户名
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/findUserName"])
    fun findUserName(@ModelAttribute("data") data: String?, request: HttpServletRequest): String {

        if (CheckReceivedDataUtil.JsonToClass<UserInfo>(
                UserInfo::class.java,
                data
            ) == null
        ) return Result.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java, data)
        SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")

        if (userInfo?.userName.isNullOrBlank()) return Result.failure300("用户名不能为空!")
        val queryUserByName = findByName(userInfo?.userName!!, 0)
        if (queryUserByName.size <= 0) {
            return Result.failure300("该用户暂未注册!!")
        }
        return Result.success200(Gson().toJson(queryUserByName), "注册成功!")
    }


    /**
     * 通过用户ID查询用户
     * @param userId 用户名ID
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/findUserId"])
    fun findUserId(@ModelAttribute("data") data: String?, request: HttpServletRequest): String {

        if (CheckReceivedDataUtil.JsonToClass<UserInfo>(
                UserInfo::class.java,
                data
            ) == null
        ) return Result.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java, data)
        val sessionUserId =
            SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")

        if (userInfo?.userId!! <= 0) return Result.failure300("用户名ID不能为空!")
        val queryUserByName = findByName(userInfo.userId.toString(), 1)
        if (queryUserByName.size <= 0) {
            return Result.success200("该用户暂未注册!!")
        }
        return Result.success200(Gson().toJson(queryUserByName), "注册成功!")
    }


    /**
     * 修改用户信息
     * @param userInfo 用户名修改信息  JSON
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/upUserInfo"])
    fun updateUserInfo(
        @RequestParam(value = "file", required = false) file: MultipartFile,
        @ModelAttribute("data") data: String?,
        request: HttpServletRequest
    ): String {

        if (CheckReceivedDataUtil.JsonToClass<UserInfo>(
                UserInfo::class.java,
                data
            ) == null
        ) return Result.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java, data)
        val sessionUserId =
            SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (userInfo == null) return Result.failure300("用户不存在!")

        if (userInfo.userName.isNullOrBlank() && userInfo.userId == 0) {
            return Result.failure300("用户名或用户ID不能为空!")
        }

        var mlist: MutableList<UserInfo>? = null
        //查询用户是否存在
        if (!userInfo.userName.isNullOrBlank()) {
            mlist = findByName(userInfo.userName!!, 0)
        } else {
            mlist = findByName(userInfo.userId.toString(), 1)
        }
        //不存在则 修改失败 返回
        if (mlist.size <= 0) return Result.failure300("修改失败，未查询到用户信息!")
        //当用户存在 判断是否上传头像，如未上传 跳过 反之上传
        val userInfo1 = mlist[0]
        if (!file.isEmpty) {
            try {
                val uploadUtil = UploadImageUtil()
                val path = uploadUtil.uploadPicture(file, request)
                val image = ImageEntity()
                image.image_name = uploadUtil.getFileName()
                image.image_suffix = uploadUtil.getFileSuffix()
                image.image_data = uploadUtil.getFileTime()
                image.image_path = path
                image.user_id = userInfo.userId
                image.image_type = 0
                val insertImage = mainService.imageService.insertImage(image)
                if (insertImage <= 0) {
                    Result.log("上传失败")
                }

                if (!path.isNullOrBlank()) {
                    userInfo.userProFilePhoto = path
                } else {
                    userInfo.userProFilePhoto = userInfo1.userProFilePhoto
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            //当没有时从服务器获取
            userInfo.userProFilePhoto = userInfo1.userProFilePhoto
        }

        userInfo.userId = userInfo1.userId

        if (userInfo.userPhone.isNullOrBlank()) {
            userInfo.userPhone = userInfo1.userPhone
        }

        if (userInfo.userEmail.isNullOrBlank()) {
            userInfo.userEmail = userInfo1.userEmail
        }

        if (userInfo.userEmail.isNullOrBlank()) {
            userInfo.userEmail = userInfo1.userEmail
        }

        if (userInfo.userBirthDay.isNullOrBlank()) {
            userInfo.userBirthDay = userInfo1.userBirthDay
        }

        if (userInfo.userAge == 0) {
            userInfo.userAge = userInfo1.userAge
        }

        if (userInfo.userNickName.isNullOrBlank()) {
            userInfo.userNickName = userInfo1.userNickName
        }

        if (userInfo.userConstellAtion.isNullOrBlank()) {
            userInfo.userConstellAtion = userInfo1.userConstellAtion
        }

        if (userInfo.userRegisrAtionTime.isNullOrBlank()) {
            userInfo.userRegisrAtionTime = userInfo1.userRegisrAtionTime
        }

        val updateUserById = mainService.userService.updateUserById(userInfo)

        if (updateUserById <= 0) {
            return Result.failure300("修改失败!!")
        }
        return Result.success200("修改成功!")
    }


    /**
     * 删除用户信息
     * @param userId 用户ID删除
     * @param userName 用户名删除
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/dlUserInfo"])
    fun deleteUserInfo(@ModelAttribute("data") data: String?, request: HttpServletRequest): String {
        if (CheckReceivedDataUtil.JsonToClass<UserInfo>(
                UserInfo::class.java,
                data
            ) == null
        ) return Result.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<UserInfo>(UserInfo::class.java, data)
        val sessionUserId =
            SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (userInfo == null) return Result.failure300("用户不存在!")

        var mlist: MutableList<UserInfo>? = null
        var type: Int = 0
        if (userInfo.userId == 0) {
            if (userInfo.userName.isNullOrBlank()) {
                return Result.failure300("删除失败,用户名或用户ID不能为空!!")
            } else {
                mlist = findByName(userInfo.userName!!, 0)
                type = 1
            }

        } else {
            type = 0
            mlist = findByName(userInfo.userId.toString(), 1)
        }
        if (mlist.size > 0) {
            return when (type) {
                1 -> if (mainService.userService.deleteUserByName(userInfo.userName!!) > 0) return Result.success200("删除成功!") else return Result.failure300(
                    "删除失败!"
                )
                0 -> if (mainService.userService.deleteUserById(userInfo.userId) > 0) return Result.success200("删除成功!") else return Result.failure300(
                    "删除失败!"
                )
                else -> Result.failure300("删除失败!")
            }
        }
        return Result.failure300("删除失败!")
    }

    /**
     * 修改用户密码
     * @param userInfo 用户名修改信息  JSON
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/upPwd"])
    fun updatePwd(@RequestBody data: String?, request: HttpServletRequest): JSONObject {
        logger.info("这是原数据 \r\n  $data  ")
        val resultData = CheckReceivedDataUtil.JsonToClass1<ResultData<String>>(data)
        if (resultData === null) return Result.failure300ToJSON("参数缺失 data 参数")
        if (resultData.data === null) return Result.failure300ToJSON("参数缺失 data 参数")
        logger.info("这是原data数据 \r\n  $resultData.data  ")
        val userInfo = Gson().fromJson<UserInfo>(resultData.data, object : TypeToken<UserInfo>() {}.type)
        logger.info("这是原data数据 \r\n  ${userInfo.toString()}  ")
        try {
            SeesionUtil.getSessionUserId(request) ?: return Result.failure300ToJSON(
                Constant.ERROR_CLEAR,
                "登陆状态失效,请重新登陆!"
            )
        } catch (e: Exception) {
            logger.error("获取Seesion 失败", e)
        }
        if (userInfo == null) return Result.failure300ToJSON("用户不存在!")

        if (userInfo.userPwdType == 0) {
            if (userInfo.userName.isNullOrBlank()) {
                if (userInfo.userId == 0) {
                    return Result.failure300ToJSON("用户名或用户名ID不能为空!")
                }
            }


        }else{
            if (userInfo.verifyCode.isNullOrBlank()) return Result.failure300ToJSON("请输入验证码!")
            if (userInfo.verifyCode?.length != 6) return Result.failure300ToJSON("验证码错误!")
            if (userInfo.verifyCode!="123456") return Result.failure300ToJSON("验证码已过期，请重新获取!")
        }

        if (userInfo.userPwd.isNullOrBlank()) return Result.failure300ToJSON("用户名密码不能为空!")
        if (userInfo.userPwd?.length!! < 6) return Result.failure300ToJSON("用户名密码不能少于6位!")
        if (userInfo.newUserPwd?.length!! < 6) return Result.failure300ToJSON("用户名密码不能少于6位!")
        if (userInfo.newUserPwd.isNullOrBlank()) return Result.failure300ToJSON("新用户名密码不能为空!")

        val mlist = when (userInfo.userPwdType) {
            0 -> findByName(userInfo.userName!!, 0)
            1 -> findByName(userInfo.userPhone.toString(), 2)
            else -> arrayListOf<UserInfo>()
        }
        logger.info("查询的用户信息 \r\n  $mlist  ")
        if (mlist.size <= 0) return Result.failure300ToJSON("修改失败,原用户名或原用户ID错误!!")
        //验证密码是否正确
        if (userInfo.userPwdType == 0) {
            if (mlist[0].userPwd == userInfo.userPwd) return Result.failure300ToJSON("原密码验证失败!")
        }

        return when (userInfo.userPwdType) {
            0 -> if (mainService.userService.updateUserPwdByName(
                    userInfo.userName!!,
                    userInfo.newUserPwd!!
                ) > 0
            ) return Result.success200ToJSON("修改密码成功!") else return Result.failure300ToJSON("修改密码失败!")
            1 -> if (mainService.userService.updateUserPwdById(
                    mlist[0].userId,
                    userInfo.newUserPwd!!
                ) > 0
            ) return Result.success200ToJSON("修改密码成功!") else return Result.failure300ToJSON("修改密码失败!")
            else -> Result.failure300ToJSON("修改密码失败!")
        }
    }


    /**
     * 查询用户  公共方法
     * @param type 0 用户名 1 用户ID
     */
    fun findByName(userName: String, type: Int): MutableList<UserInfo> {
        val mlist: MutableList<UserInfo> = arrayListOf()
        return when (type) {
            0 -> mainService.userService.queryUserByName(userName)
            1 -> {
                val queryUserById = mainService.userService.queryUserById(userName.toInt())
                if (queryUserById != null) {
                    mlist.add(queryUserById)
                }
                mlist
            }
            2 -> {
                val queryUserById = mainService.userService.queryUserByPhone(userName)
                if (queryUserById != null) {
                    mlist.add(queryUserById)
                }
                mlist
            }
            else -> mlist
        }

    }

    /**
     * 获取短信验证码
     * @param userId 用户ID删除
     * @param userName 用户名删除
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/getVerifyCode"])
    fun getVerifyCode(@RequestBody data: String?, request: HttpServletRequest): JSONObject {
        logger.info("获取短信验证码   \r\n   $data")
        if (data != null) {
            val resultData = CheckReceivedDataUtil.JsonToClass1<ResultData<String>>(data)

            if (resultData === null) return Result.failure300ToJSON("参数缺失 data 参数")
            if (resultData.data.isNullOrBlank()) return Result.failure300ToJSON("参数缺失 data 参数")
            logger.info("这是原data数据 \r\n  ${resultData.data}  ")

            val user = Gson().fromJson<UserInfo>(resultData.data, object : TypeToken<UserInfo>() {}.type)
            logger.info("这是原data数据 \r\n  ${user.toString()}  ")

            val isPhoen = PhoneUtils.isPhoneLegal(user.userPhone)

            if (!isPhoen) return Result.failure300ToJSON("手机号格式不正确")

           return when(user.verifyCodeType){
                0->{
                    val map = hashMapOf<String, Any?>()
                    map["verify_code"] = "123456"
                    return Result.success200ToJSON(Gson().toJson(map), "验证码发送成功")
                }
               1->{

                   val userInfo = mainService.userService.queryUserByPhone(user.userPhone!!)

                   if (userInfo === null) return Result.failure300ToJSON("该用户未注册!!!")

                   val map = hashMapOf<String, Any?>()
                   map["verify_code"] = "123456"
                   return Result.success200ToJSON(Gson().toJson(map), "验证码发送成功")
               }

               else ->{
                   val userInfo = mainService.userService.queryUserByPhone(user.userPhone!!)

                   if (userInfo === null) return Result.failure300ToJSON("该用户未注册!!!")

                   val map = hashMapOf<String, Any?>()
                   map["verify_code"] = "123456"
                   return Result.success200ToJSON(Gson().toJson(map), "验证码发送成功")
               }
            }


        }

        return Result.failure300ToJSON("获取验证码失败")

    }

}