package com.wzj.blog.myblog.controller.upload

import com.wzj.blog.myblog.result.Result.Companion.failure300
import com.wzj.blog.myblog.result.Result.Companion.success200
import java.util.HashMap
import com.google.gson.Gson
import com.wzj.blog.myblog.config.rsaUtil.annotation.Decrypt
import com.wzj.blog.myblog.config.rsaUtil.annotation.EnableSecurity
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.entity.ImageEntity
import com.wzj.blog.myblog.entity.apk.ApkEnt
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.uploadImage.UploadImageUtil2
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.lang.Exception
import java.util.logging.Logger.getLogger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@Slf4j
class FileUpload {
    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(FileUpload::class.java)

    /**
     * 下载
     */
    @RequestMapping(value = ["/downLoad"], method = [RequestMethod.GET])
    @Decrypt(required = false)
    @CrossOrigin
    fun downLoad(
        req: HttpServletRequest?,
        res: HttpServletResponse
    ): String {
        val apkEnt = ApkEnt()
        apkEnt.version_id = 100
        apkEnt.file_name = "" ?:"blog.apk"
        apkEnt.del_flag = false
      val ent =   mainService.apkService.query(apkEnt)
//        if (mlist.size<=0) return failure300("暂无下载内容")
//        val ent = mlist[0]
        if (ent==null)  return failure300("暂无下载内容")
        if (ent.id<=0)  return failure300("暂无下载内容")
        val reMap: MutableMap<String, Any> = HashMap()

        val filepath = ent.url
        val os: OutputStream? = null
        var `is`: InputStream? = null
        try {
        // 清空输出流
            res.reset()
            res.characterEncoding = "utf-8"
            res.contentType = "application/octet-stream"
            res.setHeader("Content-Disposition",
                "attachment;filename=" + String((ent.file_name?:"blog.apk").toByteArray(charset("UTF-8")), Charsets.ISO_8859_1))
            // 读取流
            val f = File(filepath)
            `is` = FileInputStream(f)
            if (`is` == null) {
                reMap["msg"] = "下载附件失败"
                return failure300("下载附件失败,error:")
            }
            val sout = res.outputStream
            var bis: BufferedInputStream? = null
            var bos: BufferedOutputStream = res.outputStream.buffered()
            bis = BufferedInputStream(`is`)
//            var  bos = BufferedOutputStream(sout)
            val buff = ByteArray(2048)
            var bytesRead: Int
            while (-1 != bis.read(buff, 0, buff.size).also { bytesRead = it }) {
                bos.write(buff, 0, bytesRead)
            }
            bos.flush()
            bos.close()
            bis.close()
            `is`.close()
            os!!.close()
        } catch (e: Exception) {
            reMap["msg"] = "下载附件失败,error:" + e.message
            return failure300("下载附件失败,error:" + e.message)
        }
        return success200(Gson().toJson(reMap))
    }


//    /**
//     * 上传APK
//     * @param file 文件
//     */
//    @RequestMapping("/uploadApk")
//    @CrossOrigin
//    @ResponseBody
//    fun uploadPicture(
//        @RequestParam(value = "file", required = false) file: MultipartFile,
//        @RequestParam(value = "data", required = false) data: String?
//    ): BaseData<Any?> {
//        val apkEnt = Gson().fromJson(data, ApkEnt::class.java)
//        if (apkEnt.version_id<=0)  return Result1.failure300("缺少版本号!!!")
//        //保存图片及图片相关信息
//        UploadImageUtil2.getIncense().uploadFile(1, file)
//        if (UploadImageUtil2.getIncense().getFilePath().isNullOrBlank()) return Result1.failure300("图片上传失败!!!")
//
//        val apk = ApkEnt()
//        apkEnt.file_name = UploadImageUtil2.getIncense().getFileName()
//        apkEnt.url = UploadImageUtil2.getIncense().getFilePath()
//
//        val insertImage = mainService.apkService.addApk(apk)
//        if (insertImage==null||insertImage.id<=0) {
//            logger.info("上传失败")
//            return Result1.failure300("上传失败!!")
//        }
//        return Result1.success200("上传成功!!")
//    }
}