package com.wzj.blog.myblog.controller

import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.service.MainService
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



@Controller
@CrossOrigin
@RequestMapping("/image")
@Slf4j
class ImageController {
    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(ImageController::class.java)
    private var uploadStatus:MutableList<Int> = arrayListOf()

    /**
     * 上传头像
     */
    @ResponseBody
    @PostMapping("/addImage")
    fun addimage(@RequestParam(value="file",required=false) file: Array<MultipartFile>?, request: HttpServletRequest?): JSONObject {

        try {

            val userId = request?.getParameter("userId")
            val type = request?.getParameter("imageType")
            if (userId?.length != 8) return Result.failure300ToJSON("缺少userid")
            if (type.isNullOrBlank()) return Result.failure300ToJSON("缺少文件类型")
            val userInfo = mainService.userService.queryUserById(userId.toInt())
            if (userInfo == null) return Result.failure300ToJSON("用户不存在")
            val uploadUtil = UploadImageUtil()

           val path = uploadUtil.uploadPicture(type, file!![0], "${uploadUtil.filePath}$userId/")
            val image = ImageEntity()
            image.image_name = uploadUtil.getFileName()
            image.image_suffix = uploadUtil.getFileSuffix()
            image.image_data = uploadUtil.getFileTime()
            image.image_path = path
            image.user_id = userInfo.userId
            image.image_type = if (type.isNullOrBlank()) 0 else type.toInt()

            val insertImage = mainService.imageService.insertImage(image)


            return if (insertImage <= 0) {
                Result.failure300ToJSON("上传失败")
            } else {

                if (!path.isNullOrBlank()) {
                    //上传头像
                    userInfo.userProFilePhoto = path
                    mainService.userService.updateUserImagePathById(userInfo)
                }

                Result.success200ToJSON("图片上传成功")
            }

        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure300ToJSON("上传文件失败!!!")
        }

//        return Result.failure300ToJSON("缺少必要参数")

    }
}