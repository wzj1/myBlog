package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.uploadImage.UploadImageUtil2
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

/**
 * 上传图片
 */
@Controller
@RequestMapping("/image")
class UpImageController {
    @Autowired
    lateinit var mainService: MainService

    var logger: Logger = LoggerFactory.getLogger(UpImageController::class.java)



    /**
     * 上传图片
     * @param file 图片文件
     * @param userid 用户ID
     * @param image_type 头像类型  0 头像  1 其他
     */
    @RequestMapping("/uploadImg")
    @CrossOrigin
    @Encrypt
    @ResponseBody
    fun uploadPicture(
        @RequestParam(value = "file", required = false) file: MultipartFile,
        @RequestParam(value = "data", required = false) data: String?,
        request: HttpServletRequest,
    ): BaseData<Any?> {
        val imageEntity = Gson().fromJson(data, ImageEntity::class.java)
        if (imageEntity == null) return Result1.failure300("用户不存在!")
        if (imageEntity.user_id <= 0) return Result1.failure300("缺少userId!!!")
        if (imageEntity.image_type < 0) return Result1.failure300("缺少image_type类型!!!")
        //保存图片及图片相关信息
        UploadImageUtil2.getIncense().uploadFile(imageEntity.image_type, file)
        if (UploadImageUtil2.getIncense().getFileUrl().isNullOrBlank()) return Result1.failure300("图片上传失败!!!")
        val image = ImageEntity()
        image.image_name = UploadImageUtil2.getIncense().getFileName()
        image.image_suffix = UploadImageUtil2.getIncense().getFileSuffix()
        image.image_data = UploadImageUtil2.getIncense().getFileTime()
        image.image_path = UploadImageUtil2.getIncense().getFileUrl()
        image.user_id = imageEntity.user_id
        image.image_type = imageEntity.image_type
        val insertImage = mainService.imageService.insertImage(image)
        if (insertImage <= 0) {

            logger.info("上传失败")
            return Result1.failure300("图片上传失败!!")
        }
        return Result1.success200("上传成功!!")
    }

    /**
     * 通过ImageId 查找图片
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/findImg"])
    fun findImg(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {

        if (CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val imageEntity = CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java, data)
        if (imageEntity == null) return Result1.failure300("用户不存在!")

        if (imageEntity.image_id <= 0) return Result1.failure300("缺少image_id!!!")

        val insertImage = mainService.imageService.queryById(imageEntity.image_id)
        if (insertImage == null) {
            logger.info("查询失败")
            return Result1.failure300("无图片资源！！")
        }
        return Result1.success200(Gson().toJson(insertImage), "查询成功!!")
    }

    /**
     * 修改图片
     * @param file 修改的图片文件
     * @param imageData 修改的参数 json格式
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/updateImg"])
    fun updateImg(
        @RequestParam(value = "file", required = false) file: MultipartFile,
        @ModelAttribute("data") data: String?,
        request: HttpServletRequest,
    ):BaseData<Any?> {

        if (CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val image = CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java, data)
        if (image == null) return Result1.failure300("用户不存在!")

        if (image.image_id <= 0) return Result1.failure300("缺少image_id!!!")
        //保存图片及图片相关信息
        UploadImageUtil2.getIncense().uploadFile(image.image_type, file)
        if (UploadImageUtil2.getIncense().getFileUrl().isNullOrBlank()) return Result1.failure300("图片上传失败!!!")
        val images = ImageEntity()
        images.image_name = UploadImageUtil2.getIncense().getFileName()
        images.image_suffix = UploadImageUtil2.getIncense().getFileSuffix()
        images.image_data = UploadImageUtil2.getIncense().getFileTime()
        images.image_path = UploadImageUtil2.getIncense().getFileUrl()
        images.user_id = image.user_id
        images.image_type = image.image_type
        val count = mainService.imageService.queryById(image.image_id)
        if (count == null) return Result1.failure300("修改失败，资源不存在!!!")
        val insertImage = mainService.imageService.updateImage(image)
        if (insertImage <= 0) {
            logger.info("上传失败")
            return Result1.failure300("修改失败,图片上传失败!!")
        }
        return Result1.success200(Gson().toJson(insertImage), "修改成功!!")
    }


    /**
     * 查询所有图片资源
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/findImgAll"])
    fun findImgAll():BaseData<Any?> {

        val insertImage = mainService.imageService.queryforList()
        if (insertImage.size == 0) {
            logger.info("查询失败")
            return Result1.failure300("无图片资源！！")
        }
        return Result1.success200(Gson().toJson(insertImage), "查询成功!!")
    }

    /**
     * 查询用户下所有图片资源
     * @param userid 用户ID
     */
    @ResponseBody
    @CrossOrigin
    @RequestMapping("/findUserImg")
    fun findUserImg(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val image = CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java, data)
        if (image == null) return Result1.failure300("用户不存在!")

        if (image.user_id <= 0) return Result1.failure300("缺少userId!!!")

        val insertImage = mainService.imageService.queryByUserId(image.user_id)
        if (insertImage.size == 0) {
            logger.info("查询失败")
            return Result1.failure300("无图片资源！！")
        }
        return Result1.success200(Gson().toJson(insertImage), "查询成功!!")
    }

    /**
     * 通过imageId 删除
     * @param imageId 图片ID
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/deleteImg"])
    fun deleteImg(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val image = CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java, data)
        if (image == null) return Result1.failure300("用户不存在!")

        if (image.image_id <= 0) return Result1.failure300("缺少image_id!!!")
        val insertImage = mainService.imageService.deleteById(image.image_id)
        if (insertImage < 0) {
            logger.info("删除失败")
            return Result1.failure300("无图片资源！！")
        }
        return Result1.success200(Gson().toJson(insertImage), "删除成功!!")
    }

    /**
     * 删除用户下所有图片资源
     * @param userid 用户ID
     */
    @ResponseBody
    @CrossOrigin
    @PostMapping(value = ["/deleteUserImg"])
    fun deleteUserImg(@ModelAttribute("data") data: String?, request: HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java,
                data) == null
        ) return Result1.failure300("格式错误!!!")
        val image = CheckReceivedDataUtil.JsonToClass<ImageEntity>(ImageEntity::class.java, data)
        if (image == null) return Result1.failure300("用户不存在!")

        if (image.user_id <= 0) return Result1.failure300("缺少userId!!!")
        val insertImage = mainService.imageService.deleteByUserId(image.user_id)
        if (insertImage <= 0) {
            logger.info("删除失败")
            return Result1.failure300("无图片资源！！")
        }
        return Result1.success200(Gson().toJson(insertImage), "删除成功!!")
    }


}