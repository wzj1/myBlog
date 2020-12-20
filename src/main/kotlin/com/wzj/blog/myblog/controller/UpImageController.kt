package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.returnUtil.GetResultData
import com.wzj.blog.myblog.service.ImageService.ImageService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.uploadImage.UploadImageUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/image")
class UpImageController {
    @Autowired
    lateinit var imageService: ImageService

    /**
     * 上传图片
     * @param file 图片文件
     * @param userid 用户ID
     * @param image_type 头像类型  0 头像  1 其他
     */
    @ResponseBody
//    @PostMapping(value = ["/uploadImg"], consumes = ["application/json"])
    @PostMapping(value = ["/uploadImg"], consumes = ["application/json","multipart/form-data;charset=UTF-8"])

    fun uploadPicture(@RequestParam(value="file",required=false) file: MultipartFile,@RequestBody data: ResultData<String>?, request: HttpServletRequest, response: HttpServletResponse):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val imageEntity = Gson().fromJson(data!!.data, ImageEntity::class.java)
        if (imageEntity.user_id<=0) return GetResultData.failure300("缺少userId!!!")
        if (imageEntity.image_type<=0) return GetResultData.failure300("缺少image_type类型!!!")
        val uploadUtil  = UploadImageUtil()
        val path = uploadUtil.uploadPicture(file,null,request)
        if (path.isNullOrBlank()) return GetResultData.failure300("图片上传失败!!!")
       val image = ImageEntity()
        image.image_name=uploadUtil.getFileName()
        image.image_suffix=uploadUtil.getFileSuffix()
        image.image_data=uploadUtil.getFileTime()
        image.image_path=path
        image.user_id=imageEntity.user_id
        image.image_type=imageEntity.image_type
        val insertImage = imageService.insertImage(image)
        if (insertImage<=0) {
            GetResultData.log("上传失败")
            return GetResultData.failure300("图片上传失败!!")
        }
        return GetResultData.success200("插入成功!!")
    }

    /**
     * 通过ImageId 查找图片
     */
    @ResponseBody
    @PostMapping(value = ["/findImg"], consumes = ["application/json"])

    fun findImg(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas
        val imageEntity = Gson().fromJson(data!!.data, ImageEntity::class.java)
        if (imageEntity.image_id<=0) return GetResultData.failure300("缺少image_id!!!")

        val insertImage = imageService.queryById(imageEntity.image_id)
        if (insertImage==null) {
            GetResultData.log("查询失败")
            return GetResultData.failure300("无图片资源！！")
        }
        return GetResultData.success200(Gson().toJson(insertImage),"查询成功!!")
    }

    /**
     * 修改图片
     * @param file 修改的图片文件
     * @param imageData 修改的参数 json格式
     */
    @ResponseBody
    @PostMapping(value = ["/updateImg"], consumes = ["application/json","multipart/form-data;charset=UTF-8"])

    fun updateImg(@RequestParam(value = "file", required = false) file: MultipartFile, @RequestBody data: ResultData<String>?, request: HttpServletRequest, response: HttpServletResponse): String {
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val image = Gson().fromJson(data!!.data, ImageEntity::class.java)
        if (image.image_id<=0) return GetResultData.failure300("缺少image_id!!!")
        val uploadUtil  = UploadImageUtil()
        val path = uploadUtil.uploadPicture(file, null, request)
        if (path.isNullOrBlank()) return GetResultData.failure300("图片上传失败!!!")
        image.image_name = uploadUtil.getFileName()
        image.image_suffix = uploadUtil.getFileSuffix()
        image.image_data = uploadUtil.getFileTime()
        image.image_path = path
       val count = imageService.queryById(image.image_id)
        if (count==null) return GetResultData.failure300("修改失败，资源不存在!!!")

        val insertImage = imageService.updateImage(image)
        if (insertImage <= 0) {
            GetResultData.log("上传失败")
            return GetResultData.failure300("修改失败,图片上传失败!!")
        }
        return GetResultData.success200(Gson().toJson(insertImage), "修改成功!!")
    }


    /**
     * 查询所有图片资源
     */
    @ResponseBody
    @PostMapping(value = ["/findImgAll"], consumes = ["application/json"])
    fun findImgAll():String{

        val insertImage = imageService.queryforList()
        if (insertImage.size==0) {
            GetResultData.log("查询失败")
            return GetResultData.failure300("无图片资源！！")
        }
        return GetResultData.success200(Gson().toJson(insertImage),"查询成功!!")
    }

    /**
     * 查询用户下所有图片资源
     * @param userid 用户ID
     */
    @ResponseBody
//    @RequestMapping("/findUserImg")
    @PostMapping(value = ["/findUserImg"], consumes = ["application/json"])
    fun findUserImg(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val image = Gson().fromJson(data!!.data, ImageEntity::class.java)
        if (image.user_id<=0) return GetResultData.failure300("缺少userId!!!")

        val insertImage = imageService.queryByUserId(image.user_id)
        if (insertImage.size==0) {
            GetResultData.log("查询失败")
            return GetResultData.failure300("无图片资源！！")
        }
        return GetResultData.success200(Gson().toJson(insertImage),"查询成功!!")
    }

    /**
     * 通过imageId 删除
     * @param imageId 图片ID
     */
    @ResponseBody
    @PostMapping(value = ["/deleteImg"], consumes = ["application/json"])
    fun deleteImg(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val image = Gson().fromJson(data!!.data, ImageEntity::class.java)
        if (image.image_id<=0) return GetResultData.failure300("缺少image_id!!!")
        val insertImage = imageService.deleteById(image.image_id)
        if (insertImage<=0) {
            GetResultData.log("删除失败")
            return GetResultData.failure300("无图片资源！！")
        }
        return GetResultData.success200(Gson().toJson(insertImage),"删除成功!!")
    }

    /**
     * 删除用户下所有图片资源
     * @param userid 用户ID
     */
    @ResponseBody
    @PostMapping(value = ["/deleteUserImg"], consumes = ["application/json"])
    fun deleteUserImg(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val image = Gson().fromJson(data!!.data, ImageEntity::class.java)
        if (image.user_id<=0) return GetResultData.failure300("缺少userId!!!")
        val insertImage = imageService.deleteByUserId(image.user_id)
        if (insertImage<=0) {
            GetResultData.log("删除失败")
            return GetResultData.failure300("无图片资源！！")
        }
        return GetResultData.success200(Gson().toJson(insertImage),"删除成功!!")
    }


}