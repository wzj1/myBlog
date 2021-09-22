package com.wzj.blog.myblog.util.uploadImage

import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import org.springframework.web.multipart.MultipartFile
import java.io.*
import javax.servlet.http.HttpServletRequest


/**
 * 图片下载或上传 工具类
 */
class UploadImageUtil2 {
    private var callBack: UploadImageUtil2.ImageCallBack? =null


    //上传的图片路径
    private var imagePath:String?=null

    //上传的图片名称
    private var imageName:String?=null

    //后缀名
    private var imageFileSuffix:String? =null

    //时间
    private var imageFileTime: String? = null
    //上传后完整路径
    private var urlpath: String? = null

    companion object{

        private var uploadImageUtil:UploadImageUtil2? =null




        //拼接后的域名或本地IP地址加端口
        private var imageIp:String = "${Constant.image_IP}:${Constant.image_prot}"


        fun getIncense():UploadImageUtil2{

            if (uploadImageUtil==null) {

                synchronized(UploadImageUtil2::class.java){
                    if (uploadImageUtil==null) {
                        uploadImageUtil = UploadImageUtil2()
                    }

                }
            }

            return uploadImageUtil!!
        }

    }


    fun setImagePath(path:String):UploadImageUtil2{
        this.imagePath = "${Constant.imagePath}$path"
        return this
    }


    fun setImageName(name:String):UploadImageUtil2{
        this.imageName = name
        return this
    }


    fun setImageFileSuffix(suffix:String):UploadImageUtil2{
        this.imageFileSuffix = suffix
        return this
    }


    fun setImageFileTime(time:String):UploadImageUtil2{
        this.imageFileTime = time
        return this
    }

    fun setCallBack(callBack: UploadImageUtil2.ImageCallBack):UploadImageUtil2{
        this.callBack = callBack
        return this
    }


    fun getFileName(): String? = imageName

    fun getFilePath(): String? = urlpath

    /**
     * 获取 文件后缀
     */
    fun getFileSuffix(): String? = imageFileSuffix

    /**
     * 获取文件存储时间
     */
    fun getFileTime(): String? = imageFileTime




    //上传多个文件
    fun uploadFile(imageType:Int,fileList: MutableList<MultipartFile>){

        for (file in fileList.iterator()){
            UploadFile2(imageType,file)
        }

    }

    //上传单个
    fun uploadFile(imageType:Int,file: MultipartFile){

        UploadFile2(imageType,file)

    }


    fun UploadFile2(imageType:Int,file: MultipartFile){

        var isFileName: Boolean = false

        if (!imageName.isNullOrEmpty()) isFileName= true

        if (imagePath.isNullOrEmpty()) imagePath =Constant.imagePath

        val path = "$imageIp/${
            when(imageType){
                0->"upload/"
                1->"apk"
                else -> "photo/"
            }
        }"

        // 获取上传的位置（存放图片的文件夹）,如果不存在，创建文件夹
        val fileParent = File(imagePath)
        if (!fileParent.exists()) {
            fileParent.mkdirs()

        }


        var fileTime: String = ""

        //拼接文件名 日期+随机数+文件后缀
        fileTime = TimeUtil.getDate("yyyy-MM-dd HH:mm:ss")
        val fileNameTime = TimeUtil.getDate("yyyyMMddHHmmssSSS")
        if (imageName.isNullOrEmpty()) {
            imageName = when(imageType){
                0->"photo_" + fileNameTime + "_" + TimeUtil.getRandom()
                else->"image_" + fileNameTime + "_" + TimeUtil.getRandom()
            }

        }

        if (imageFileSuffix.isNullOrEmpty()) imageFileSuffix = "jpg"


        val newFile = File("$imagePath", "$imageName.$imageFileSuffix")
        // 如果不存在，创建一个副本
        if (!newFile.exists()) {
            newFile.createNewFile()
        }

        Result.log("上传到副本的文件： ${newFile.path}")
        // 将io上传到副本中
        try {
            file.transferTo(newFile)
        } catch (e: Exception) {
            e.printStackTrace()
            callBack?.onFailure(-1001,"复制文件失败")
        }

        //文件时间
        imageFileTime = fileTime
        urlpath = "$path$imageName.$imageFileSuffix"
        Result.log("保存到数据库中的路径： $urlpath")

        callBack?.onSuccessful(urlpath.toString())

    }


    interface ImageCallBack{
        fun onSuccessful(path: String)
        fun onFailure(code:Int ,msg:String)
    }

}