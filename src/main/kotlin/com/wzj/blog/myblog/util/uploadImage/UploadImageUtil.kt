package com.wzj.blog.myblog.util.uploadImage

import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import org.springframework.web.multipart.MultipartFile
import java.io.*
import javax.servlet.http.HttpServletRequest


/**
 * 图片下载或上传 工具类
 */
open class UploadImageUtil {
    //Liunx 文件储存文件夹路径
    val filedfPath:String ="/var/image/"
    //文件上传默认路径
    var filePath:String = "${filedfPath}upload"
    var ImageFileName: String? = null
    var ImageFileSuffix: String? = null
    var ImageFileTime: String? = null
    var image_https="https://www.wzjlb.com.cn"
    var image_prot="8443"


    /**
     * 获取文件路径
     */
    fun getFileName(): String? = ImageFileName

    /**
     * 获取 文件后缀
     */
    fun getFileSuffix(): String? = ImageFileSuffix

    /**
     * 获取文件存储时间
     */
    fun getFileTime(): String? = ImageFileTime


    fun getLiunxFile(filePath: String):String{
        val sb = StringBuilder(File.separator)
        val path = sb.append("var").append(File.separator).append("java").append(File.separator).append("upload").append(File.separator).append("images").toString()

       //所创建文件目录
        val f = File(path)
        if (!f.exists()) {
            f.mkdirs() //创建目录
        }

        //创建文件
        val file = File(path, filePath)
        if (!file.exists()) { //surround with try/catch
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return file.path
    }


    /**
     * 上传文件
     * @param file 文件
     * @param request 暂时用不上
     */
    fun uploadPicture(file: MultipartFile, request: HttpServletRequest):String{

        return uploadPicture(file)
    }


    /**
     * 上传文件
     * @param file 文件
     * @param path 自定义文件夹名  例如 :/PDFName
     */
    fun uploadPicture(file: MultipartFile,path:String?,fileName:String):String{
        var path1 = path
        if (path1.isNullOrBlank()) path1= filePath else path
        return UploadFile(file, path1,false, fileName)
    }


    /**
     * 上传文件
     * @param file 文件
     * 使用默认上传地址
     */
    fun uploadPicture(file: MultipartFile):String{
        return UploadFile(file, filePath,false, file.originalFilename!!)
    }

    fun UploadFile(file: MultipartFile,filePath: String,isFileName:Boolean,fileName: String):String{

        // 获取上传的位置（存放图片的文件夹）,如果不存在，创建文件夹
        val fileParent = File(filePath)
        if (!fileParent.exists()) {
            fileParent.mkdirs()
        }

        //文件后缀
        val fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length)
        var fileTime = ""
        var upFileName=""
        var urlpath =""
        if (isFileName){
            upFileName =fileName
        }else {
            //拼接文件名 日期+随机数+文件后缀
            fileTime=  TimeUtil.getTime()
            upFileName=  "image_" + fileTime + "_" + TimeUtil.getRandom() + fileF
        }
        urlpath =  "$image_https:$image_prot/$upFileName"
        val newFile = File("$filePath/", upFileName)
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
        }

        //文件名
        ImageFileName = upFileName.substring(0, upFileName.lastIndexOf("."))
        //文件后缀
        ImageFileSuffix = fileF.substring(fileF.lastIndexOf(".") + 1, fileF.length)
        //文件时间
        ImageFileTime = fileTime

        Result.log("保存到数据库中的路径： $urlpath")
        return urlpath

    }




}