package com.wzj.blog.myblog.util.uploadImage

import com.wzj.blog.myblog.returnUtil.GetResultData
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.ClassUtils
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.servlet.http.HttpServletRequest


/**
 * 图片下载或上传 工具类
 */
open class UploadImageUtil {
    //文件存储路径
    var fileimagePath: String? = null
        var cuchunPath: String = "/src/main/resources/static/upload/images/"
//    var cuchunPath: String = "${System.getProperty("user.dir")}/upload/images/"
    var ImageFileName: String? = null
    var ImageFileSuffix: String? = null
    var ImageFileTime: String? = null

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


    /**
     * 从前端传过来的图片
     * MultipartFile
     *
     */
    fun uploadPicture(file: MultipartFile, filePath: String?, request: HttpServletRequest): String? {
        try {
            val path1 = ClassUtils.getDefaultClassLoader()?.getResource("static")?.path
            val fileName: String? = file.originalFilename //获取文件名及后缀
            if (fileName.isNullOrBlank()) return null
            if (!filePath.isNullOrBlank()) {
                this.fileimagePath = filePath
            }
            val session = request.scheme
            val serverName = request.serverName
            val serverPort = request.serverPort
            val contextPath = request.contextPath
            val returnUrl = "$session://$serverName:$serverPort$contextPath"
            val realPath =System.getProperty("user.dir")
            //文件后缀
            val fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length)
            //拼接文件名 日期+随机数+文件后缀
            val fileTime = TimeUtil.getTime()
            val upFileName = fileTime + "_" + TimeUtil.getRandom() + fileF
            //文件名
            ImageFileName = upFileName.substring(0, upFileName.lastIndexOf("."))
            //文件后缀
            ImageFileSuffix = fileF.substring(fileF.lastIndexOf(".") + 1, fileF.length)
            //文件时间
            ImageFileTime = fileTime
            val file1 = File("$realPath$cuchunPath")
//            //判断文件是否创建 为创建则创建
            if (!file1.exists() && !file1.isDirectory) {
                file1.mkdirs()
            }
            val path = "$returnUrl$realPath$cuchunPath$upFileName"
            GetResultData.log(path)
            val urlpath = "$returnUrl/$upFileName"
            val `in` = BufferedInputStream(file.inputStream) //原先图片所在路径
            val out = BufferedOutputStream(FileOutputStream(file1.path + "/" + upFileName)) //你要保存在哪个目录下面
            var i: Int
            while (`in`.read().also { i = it } != -1) {
                out.write(i)
            }
            out.flush()
            out.close()
            `in`.close()
            val file2 = File(path1+"/"+upFileName)
            if (!file2.exists() && !file2.isDirectory) {
                file2.mkdirs()
            }
            file.transferTo(file2)
            return urlpath
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }


}