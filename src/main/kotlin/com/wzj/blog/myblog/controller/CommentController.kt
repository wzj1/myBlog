package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.entity.CommentEntity
import com.wzj.blog.myblog.entity.LanelsEntity
import com.wzj.blog.myblog.result.Result
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import com.wzj.blog.myblog.util.SeesionUtil
import com.wzj.blog.myblog.util.timeUtil.TimeUtil
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
 * 评论
 */
@Controller
@Slf4j
open class CommentController {

    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(CommentController::class.java)

    @RequestMapping("/addComment")
    @CrossOrigin
    @ResponseBody
    fun addComment(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-addLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val commentEntity = CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (commentEntity==null)  return Result.failure300("参数不能为空!")
        if (commentEntity.userid==0) return Result.failure300("缺少用户ID!")
        if (commentEntity.articleid == 0) return Result.failure300("缺少博文ID!")
        if (commentEntity.commentContent.isNullOrBlank()) return Result.failure300("评论内容不能为空!")
            commentEntity.commentDate = TimeUtil.getDate("yyyy-MM-dd HH:mm:ss")
        val size = mainService.commentService.addComment(commentEntity)
        if (size<=0)  return Result.failure300("评论失败!")
        logger.info("LanelsController-addLanels",commentEntity)
        return Result.success200("评论成功")
    }

    @RequestMapping("/findComment")
    @CrossOrigin
    @ResponseBody
    fun findComment(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-findLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val commentEntity = CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (commentEntity==null)  return Result.failure300("参数不能为空!")

        if (commentEntity.commentid==0) return Result.failure300("缺少评论ID!")

        val comment = mainService.commentService.queryById(commentEntity.commentid)
        logger.info("LanelsController-findLanels",comment)
        return Result.success200(Gson().toJson(comment),"查询成功")
    }

    @RequestMapping("/deleteComment")
    @CrossOrigin
    @ResponseBody
    fun deleteComment(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-deleteLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val commentEntity = CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (commentEntity==null)  return Result.failure300("参数不能为空!")

        if (commentEntity.commentid==0) return Result.failure300("缺少评论ID!")

        val comments = mainService.commentService.queryById(commentEntity.commentid)
        //先查 后删除
        if (comments.commentid==0) return Result.failure300("删除失败,没有该评论!!")

        val comment = mainService.lanelsService.deleteById(comments.commentid)
        logger.info("LanelsController-deleteLanels",comment)
        if (comment <=0) return Result.failure300("删除失败!")

        return Result.success200(Gson().toJson(comment),"删除成功")
    }


    @RequestMapping("/findComment")
    @CrossOrigin
    @ResponseBody
    fun findComment():String{
        val addLanels = mainService.commentService.queryforList()
        logger.info("LanelsController-findLanelsList",addLanels)
        return Result.success200(Gson().toJson(addLanels),"查询成功")
    }


    @RequestMapping("/upComment")
    @CrossOrigin
    @ResponseBody
    fun upComment(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-UpLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val comment = CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (comment==null)  return Result.failure300("参数不能为空!")

        if (comment.commentid==0) return Result.failure300("缺少参数评论ID!")
        if (comment.commentContent.isNullOrBlank()) return Result.failure300("评论内容不能为空!")
        if (comment.userid==0) return Result.failure300("缺少参数用户ID!")

        val comments = mainService.commentService.queryById(comment.commentid)
        //先查 后删除
        if (comments.commentid==0) return Result.failure300("修改失败，该评论不存在!!")

        //点赞数
        if (comment.commentLikeCount==0){
            comment.commentLikeCount = comments.commentLikeCount
        }

        //博文ID
        if (comment.articleid==0){
            comment.articleid = comments.articleid
        }

        //父评论ID
        if (comment.parentCommentid==0){
            comment.parentCommentid = comments.parentCommentid
        }

        //父评论ID
            comment.commentDate = TimeUtil.getDate("yyyy-MM-dd HH:mm:ss")

        val addLanels1 = mainService.commentService.upComment(comment)
        if (addLanels1<=0) return Result.failure300("修改失败!")
        return Result.success200("修改成功")
    }


    /**
     * 修改点赞数
     */
    @RequestMapping("/upCommentCount")
    @CrossOrigin
    @ResponseBody
    fun upCommentCount(@ModelAttribute("data") data: String?, request : HttpServletRequest):String{
        logger.info("LanelsController-UpLanels",data)
        if (CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java,data)==null) return Result.failure300("格式错误!!!")
        val comment = CheckReceivedDataUtil.JsonToClass<CommentEntity>(CommentEntity::class.java, data)
        val sessionUserId = SeesionUtil.getSessionUserId(request) ?: return Result.failure(Constant.ERROR_CLEAR, "登陆状态失效,请重新登陆!")
        if (comment==null)  return Result.failure300("参数不能为空!")

        if (comment.commentid==0) return Result.failure300("缺少参数评论ID!")

        val comments = mainService.commentService.queryById(comment.commentid)
        //先查 后删除
        if (comments.commentid==0) return Result.failure300("修改失败，该评论不存在!!")

        val addLanels1 = mainService.commentService.upLikeCount(comments.commentLikeCount+1,comment.commentid)
        if (addLanels1<=0) return Result.failure300("修改失败!")
        return Result.success200("修改成功")
    }





}