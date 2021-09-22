package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wzj.blog.myblog.config.rsaUtil.annotation.Decrypt
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.entity.LoginEntity
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.*
import com.wzj.blog.myblog.util.key.RSAUtil1
import com.wzj.blog.myblog.util.phoneUtils.PhoneUtils
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import com.wzj.blog.myblog.util.uploadImage.UploadImageUtil2
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
@CrossOrigin
@Slf4j
open class UserController {

    protected var logger: Logger = LoggerFactory.getLogger(LoginController::class.java)
    @Autowired
    protected lateinit var mainService: MainService

    /**
     * 注册 用户
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/user/registered"])
    fun registered(@RequestBody data: String?, request: HttpServletRequest): BaseData<Any?> {
        if (data.isNullOrBlank())  return Result1.failure300("数据异常")
        val userInfo = Gson().fromJson(data,LoginEntity::class.java)

        if (userInfo?.userName.isNullOrBlank()) return Result1.failure300("用户名不能为空!")
        if (userInfo?.userPwd.isNullOrBlank()) return Result1.failure300("用户名密码不能为空!")
        if (userInfo?.userPwd?.length!! < 6) return Result1.failure300("用户名密码不能少于6位!")
        val password =   DesUtil.encrypt(userInfo.userPwd)
        //通过用户名查询用户是否存在
        val findByName = findByName(userInfo.userName!!, 0)
        //如果存在 则提示用户已注册
        if (findByName.size > 0) {
            return Result1.failure300("该用户已注册!!")
        }
        userInfo.userPwd = password
        //注册时间
        userInfo.userRegisrAtionTime = TimeUtil.getTime()

        //注册用户
        val insertUser = mainService.userService.insertUser(userInfo)

        if (insertUser <= 0) return Result1.failure300("注册失败,请重试!")

        return Result1.success200("注册成功!")
    }

    /**
     * 手机号注册用户
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/user/registeredPhone"])
    fun registeredPhone(
        @RequestParam(value = "file", required = false) file: MultipartFile,
        @ModelAttribute("data") data: String?,
        request: HttpServletRequest,
    ):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java, data)

        if (userInfo?.userName.isNullOrBlank()) return Result1.failure300("用户名不能为空!")
        if (userInfo?.userPwd.isNullOrBlank()) return Result1.failure300("用户名密码不能为空!")
        if (userInfo?.userPwd?.length!! < 6) return Result1.failure300("用户名密码不能少于6位!")
        //通过用户名查询用户是否存在
        val findByName = findByName(userInfo.userName!!, 0)
        //如果存在 则提示用户已注册
        if (findByName.size > 0) {
            return Result1.failure300("该用户已注册!!")
        }

        if (!file.isEmpty) {
            try {
                //保存图片及图片相关信息
                UploadImageUtil2.getIncense().uploadFile(0, file)
                if (UploadImageUtil2.getIncense().getFilePath().isNullOrBlank()) return Result1.failure300("图片上传失败!!!")
                val image = ImageEntity()
                image.image_name = UploadImageUtil2.getIncense().getFileName()
                image.image_suffix = UploadImageUtil2.getIncense().getFileSuffix()
                image.image_data = UploadImageUtil2.getIncense().getFileTime()
                image.image_path = UploadImageUtil2.getIncense().getFilePath()
                image.user_id = userInfo.userId
                image.image_type = 0

                var insertImage: Int = 0
                val images = mainService.imageService.queryByUserId(userInfo.userId)
                if (images.size <= 0) {
                    insertImage = mainService.imageService.insertImage(image)
                } else {
                    insertImage = mainService.imageService.updateImage(image)
                }

                if (insertImage <= 0) {
                  logger.info("上传失败")
                } else {
                    if (!image.image_path.isNullOrBlank()) {
                        //上传头像
                        userInfo.userProFilePhoto = image.image_path
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
        if (insertUser <= 0) return Result1.failure300("注册失败,请重试!")
        return Result1.success200("注册成功!")
    }

    /**
     * 通过用户名查询用户
     * @param userName 用户名
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/user/findUserName"])
    fun findUserName(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java, data)

        if (userInfo?.userName.isNullOrBlank()) return Result1.failure300("用户名不能为空!")
        val queryUserByName = findByName(userInfo?.userName!!, 0)
        if (queryUserByName.size <= 0) {
            return Result1.failure300("该用户暂未注册!!")
        }
        return Result1.success200(Gson().toJson(queryUserByName), "注册成功!")
    }


    /**
     * 通过用户ID查询用户
     * @param userId 用户名ID
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/user/findUserId"])
    fun findUserId(@RequestBody data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (data.isNullOrBlank())  return Result1.failure300("数据异常")
        val userInfo = Gson().fromJson(data,LoginEntity::class.java)

        if (userInfo?.userId!! <= 0) return Result1.failure300("用户名ID不能为空!")
        val queryUserByName = mainService.userService.queryUserById(userInfo.userId)
        if (queryUserByName ==null) {
            return Result1.failure300("该用户暂未注册!!")
        }
        return Result1.success200(queryUserByName, "查询成功!")
    }


    /**
     * 修改用户信息
     * @param userInfo 用户名修改信息  JSON
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/user/upUserInfo"])
    fun updateUserInfo(@RequestBody data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (data.isNullOrBlank())  return Result1.failure300("数据异常")
        val userInfo = Gson().fromJson(data,LoginEntity::class.java)

        if (userInfo == null) return Result1.failure300("用户不存在!")

        if (userInfo.userName.isNullOrBlank() && userInfo.userId == 0) {
            return Result1.failure300("用户名或用户ID不能为空!")
        }

        var mlist: MutableList<LoginEntity>? = null
        //查询用户是否存在
        if (!userInfo.userName.isNullOrBlank()) {
            mlist = findByName(userInfo.userName!!, 0)
        } else {
            mlist = findByName(userInfo.userId.toString(), 1)
        }
        //不存在则 修改失败 返回
        if (mlist.size <= 0) return Result1.failure300("修改失败，未查询到用户信息!")
        //当用户存在 判断是否上传头像，如未上传 跳过 反之上传
        val userInfo1 = mlist[0]

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
            return Result1.failure300("修改失败!!")
        }
        return Result1.success200("修改成功!")
    }


    /**
     * 删除用户信息
     * @param userId 用户ID删除
     * @param userName 用户名删除
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/user/dlUserInfo"])
    fun deleteUserInfo(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val userInfo = CheckReceivedDataUtil.JsonToClass<LoginEntity>(LoginEntity::class.java, data)
        if (userInfo == null) return Result1.failure300("用户不存在!")

        var mlist: MutableList<LoginEntity>? = null
        var type: Int = 0
        if (userInfo.userId == 0) {
            if (userInfo.userName.isNullOrBlank()) {
                return Result1.failure300("删除失败,用户名或用户ID不能为空!!")
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
                1 -> if (mainService.userService.deleteUserByName(userInfo.userName!!) > 0) return Result1.success200("删除成功!") else return Result1.failure300(
                    "删除失败!")
                0 -> if (mainService.userService.deleteUserById(userInfo.userId) > 0) return Result1.success200("删除成功!") else return Result1.failure300(
                    "删除失败!")
                else -> Result1.failure300("删除失败!")
            }
        }
        return Result1.failure300("删除失败!")
    }

    /**
     * 修改用户密码
     * @param userInfo 用户名修改信息  JSON
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/user/upPwd"])
    fun updatePwd(@RequestBody data: String?, request: HttpServletRequest):BaseData<Any?> {
        logger.info("这是原数据 \r\n  $data  ")
        val Result1Data = CheckReceivedDataUtil.JsonToClass1<BaseData<LoginEntity>>(data)
        if (Result1Data === null) return Result1.failure300("参数缺失 data 参数")
        if (Result1Data.data === null) return Result1.failure300("参数缺失 data 参数")
        logger.info("这是原data数据 \r\n  $Result1Data.data  ")
        val userInfo = Result1Data.data
        logger.info("这是原data数据 \r\n  ${userInfo.toString()}  ")

        if (userInfo == null) return Result1.failure300("用户不存在!")

        if (userInfo.userName.isNullOrBlank()) {
            if (userInfo.userId == 0) {
                return Result1.failure300("用户名或用户名ID不能为空!")
            }
        }

        if (userInfo.userPwd.isNullOrBlank()) return Result1.failure300("用户名密码不能为空!")
        if (userInfo.userPwd?.length!! < 6) return Result1.failure300("用户名密码不能少于6位!")
        if (userInfo.newUserPwd.isNullOrBlank()) return Result1.failure300("新用户名密码不能为空!")
        if (userInfo.newUserPwd?.length!! < 6) return Result1.failure300("新用户名密码不能少于6位!")
        val mlist = when (userInfo.userPwdType) {
            0 -> findByName(userInfo.userName!!, 0)
            1 -> findByName(userInfo.userId.toString(), 1)
            else -> arrayListOf<LoginEntity>()
        }
        logger.info("查询的用户信息 \r\n  $mlist  ")
        if (mlist.size <= 0) return Result1.failure300("修改失败,原用户名或原用户ID错误!!")
        //验证密码是否正确
        if (userInfo.userPwdType == 0) {
            if (mlist[0].userPwd != userInfo.userPwd) return Result1.failure300("原密码验证失败!")
        }

        return when (userInfo.userPwdType) {
            0 -> if (mainService.userService.updateUserPwdByName(userInfo.userName!!,
                    userInfo.newUserPwd!!) > 0
            ) return Result1.success200("修改密码成功!") else return Result1.failure300("修改密码失败!")
            1 -> if (mainService.userService.updateUserPwdById(userInfo.userId,
                    userInfo.newUserPwd!!) > 0
            ) return Result1.success200("修改密码成功!") else return Result1.failure300("修改密码失败!")
            else -> Result1.failure300("修改密码失败!")
        }
    }


    /**
     * 查询用户  公共方法
     * @param type 0 用户名 1 用户ID
     */
    fun findByName(userName: String, type: Int): MutableList<LoginEntity> {
        val mlist: MutableList<LoginEntity> = arrayListOf()
        return when (type) {
            0 -> mainService.userService.queryUserByName(userName)
            1 -> {
                val queryUserById = mainService.userService.queryUserById(userName.toInt())
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
    @PostMapping(value = ["/user/getVerifyCode"])
    fun getVerifyCode(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {
        logger.info("获取短信验证码   \r\n   $data")
        if (data != null) {
            val Result1Data = CheckReceivedDataUtil.JsonToClass1<BaseData<String>>(data)

            if (Result1Data === null) return Result1.failure300("参数缺失 data 参数")
            if (Result1Data.data.isNullOrBlank()) return Result1.failure300("参数缺失 data 参数")
            logger.info("这是原data数据 \r\n  ${Result1Data.data}  ")

            val user = Gson().fromJson<LoginEntity>(Result1Data.data, object : TypeToken<LoginEntity>() {}.type)
            logger.info("这是原data数据 \r\n  ${user.toString()}  ")

            val isPhoen = PhoneUtils.isPhoneLegal(user.userPhone)

            if (!isPhoen) return Result1.failure300("手机号格式不正确")

            val userInfo = mainService.userService.queryUserByPhone(user.userPhone!!)

            if (userInfo === null) return Result1.failure300("该用户未注册!!!")

            val map = hashMapOf<String, Any?>()
            map["verify_code"] = "123456"
            return Result1.success200(Gson().toJson(map), "验证码发送成功")
        }

        return Result1.failure300("获取验证码失败")


    }

}