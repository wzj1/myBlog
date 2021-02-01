package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.entity.LanelsEntity
import com.wzj.blog.myblog.entity.SortsEntity
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.SeesionUtil
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
 * 分类表
 */
@Controller
@Slf4j
open class SortsController {

    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(SortsController::class.java)

    @RequestMapping("/addSorts")
    @CrossOrigin
    @ResponseBody
    fun addSorts(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-addSorts",data)
        if (CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val sortsEntity = CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (sortsEntity==null)  return Result.failure300("参数不能为空!")

        if (sortsEntity.sortName.isNullOrBlank()) return Result.failure300("分类名称不能为空!")

        val sorts = mainService.sortsService.addSorts(sortsEntity.sortName, sortsEntity.sortAlias, sortsEntity.sortDescription,sortsEntity.parentSortid)
        if (sorts<=0)  return Result.failure300("添加失败!")
        logger.info("LanelsController-addSorts",sorts)
        return Result.success200("添加成功")
    }

    @RequestMapping("/findSortid")
    @CrossOrigin
    @ResponseBody
    fun findSortid(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-findSortid",data)
        if (CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val sortsEntity = CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (sortsEntity==null)  return Result.failure300("参数不能为空!")

        if (sortsEntity.sortid==0) return Result.failure300("缺少参数分类ID!")

        val sortid = mainService.lanelsService.queryById(sortsEntity.sortid)
        logger.info("LanelsController-findSortid",sortid)
        return Result.success200(Gson().toJson(sortid),"查询成功")
    }

    @RequestMapping("/deleteSortid")
    @CrossOrigin
    @ResponseBody
    fun deleteLanels(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-deleteLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val sortsEntity = CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (sortsEntity==null)  return Result.failure300("参数不能为空!")

        if (sortsEntity.sortid==0) return Result.failure300("缺少参数分类ID!")

        val sortsSize = mainService.sortsService.queryById(sortsEntity.sortid)
        //先查 后删除
        if (sortsSize.sortid==0) return Result.failure300("删除失败,没有找到标签!!")

        val sorts = mainService.sortsService.deleteById(sortsSize.sortid)
        logger.info("LanelsController-deleteLanels",sorts)
        if (sorts <=0) return Result.failure300("删除失败!")

        return Result.success200(Gson().toJson(sorts),"删除成功")
    }


    @RequestMapping("/findSortslsList")
    @CrossOrigin
    @ResponseBody
    fun findSortslsList():String{
        val sortsList = mainService.sortsService.queryforList()
        logger.info("LanelsController-findSortslsList",sortsList)
        return Result.success200(Gson().toJson(sortsList),"查询成功")
    }


    @RequestMapping("/upSortsls")
    @CrossOrigin
    @ResponseBody
    fun upLanels(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-UpLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val sortsEntity = CheckReceivedDataUtil.JsonToClass<SortsEntity>(SortsEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (sortsEntity==null)  return Result.failure300("参数不能为空!")

        if (sortsEntity.sortid==0) return Result.failure300("缺少参数分类ID!")

        val sorts = mainService.sortsService.queryById(sortsEntity.sortid)
        if (sortsEntity.sortName.isNullOrBlank()){
            sortsEntity.sortName = sorts.sortName
        }

        if (sortsEntity.sortAlias.isNullOrBlank()){
            sortsEntity.sortAlias = sorts.sortAlias
        }

        if (sortsEntity.sortDescription.isNullOrBlank()){
            sortsEntity.sortDescription = sorts.sortDescription
        }
        if (sortsEntity.parentSortid==0){
            sortsEntity.parentSortid = sorts.parentSortid
        }

        val sortsStatus = mainService.sortsService.upSorts(sortsEntity.sortName,sortsEntity.sortAlias,sortsEntity.sortDescription,sortsEntity.parentSortid,sortsEntity.sortid)
        if (sortsStatus<=0) return Result.failure300("修改失败!")
        return Result.success200("修改成功")
    }





}