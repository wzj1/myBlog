package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.entity.LanelsEntity
import com.wzj.blog.myblog.config.rsaUtil.annotation.Decrypt
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletRequest

/**
 * 标签表
 */
@Controller
@Slf4j
open class LanelsController {

    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(LanelsController::class.java)


    @RequestMapping("/addLanels")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun addLanels(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?>{
        logger.info("LanelsController-addLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val imageEntity = CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java, data)
        if (imageEntity==null)  return Result1.failure300("参数不能为空!")

        if (imageEntity.labelName.isNullOrBlank()) return Result1.failure300("标签名称不能为空!")

        val addLanels = mainService.lanelsService.addLanels(imageEntity.labelName, imageEntity.labelAlias, imageEntity.labelDescription)
        if (addLanels<=0)  return Result1.failure300("添加标签失败!")
        logger.info("LanelsController-addLanels",addLanels)
        return Result1.success200("添加成功")
    }

    @RequestMapping("/findLanels")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun findLanels(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?>{
        logger.info("LanelsController-findLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val imageEntity = CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java, data)
        if (imageEntity==null)  return Result1.failure300("参数不能为空!")

        if (imageEntity.labelId==0) return Result1.failure300("缺少参数标签ID!")

        val addLanels = mainService.lanelsService.queryById(imageEntity.labelId)
        logger.info("LanelsController-findLanels",addLanels)
        return Result1.success200(Gson().toJson(addLanels),"查询成功")
    }

    @RequestMapping("/deleteLanels")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun deleteLanels(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?>{
        logger.info("LanelsController-deleteLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val imageEntity = CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java, data)
        if (imageEntity==null)  return Result1.failure300("参数不能为空!")

        if (imageEntity.labelId==0) return Result1.failure300("缺少参数标签ID!")

        val lanelsEntity = mainService.lanelsService.queryById(imageEntity.labelId)
        //先查 后删除
        if (lanelsEntity.labelId==0) return Result1.failure300("删除失败,没有找到标签!!")

        val addLanels = mainService.lanelsService.deleteById(imageEntity.labelId)
        logger.info("LanelsController-deleteLanels",addLanels)
        if (addLanels <=0) return Result1.failure300("删除失败!")

        return Result1.success200(Gson().toJson(addLanels),"删除成功")
    }


    @RequestMapping("/findLanelsList")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun findLanelsList():BaseData<Any?>{
        val addLanels = mainService.lanelsService.queryByLanels()
        logger.info("LanelsController-findLanelsList",addLanels)
        return Result1.success200(Gson().toJson(addLanels),"查询成功")
    }


    @RequestMapping("/upLanels")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun upLanels(@ModelAttribute("data") data: String?, request : HttpServletRequest): BaseData<Any?> {
        logger.info("LanelsController-UpLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val imageEntity = CheckReceivedDataUtil.JsonToClass<LanelsEntity>(LanelsEntity::class.java, data)
        if (imageEntity==null)  return Result1.failure300("参数不能为空!")

        if (imageEntity.labelId==0) return Result1.failure300("缺少参数标签ID!")
        val addLanels = mainService.lanelsService.queryById(imageEntity.labelId)
        if (imageEntity.labelName.isNullOrBlank()){
            imageEntity.labelName = addLanels.labelName
        }

        if (imageEntity.labelAlias.isNullOrBlank()){
            imageEntity.labelAlias = addLanels.labelAlias
        }

        if (imageEntity.labelDescription.isNullOrBlank()){
            imageEntity.labelDescription = addLanels.labelDescription
        }

        val addLanels1 = mainService.lanelsService.upLanels(imageEntity.labelId,imageEntity.labelName,imageEntity.labelAlias,imageEntity.labelDescription)
        if (addLanels1<=0) return Result1.failure300("修改失败!")
        return Result1.success200("修改成功")
    }





}