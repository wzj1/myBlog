package com.wzj.blog.myblog.controller

import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.config.rsaUtil.annotation.Decrypt
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.ArticlesEntity
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
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
 * 发表博文
 */
@Controller
@Slf4j
open class ArticlesController {
    @Autowired
    lateinit var mainService: MainService
    var logger: Logger = LoggerFactory.getLogger(ArticlesController::class.java)


    @RequestMapping("/insertArticles")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun addArticles(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val articles = CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data)
        logger.info(articles.toString())
        if (articles ==null) return Result1.failure300("参数错误!!!")

        if (articles.userid==0) return Result1.failure300("缺少用户Id!!!")
        if (articles.articleTitle.isNullOrBlank())  return Result1.failure300("标题不能为空!!!")
        if (articles.articleContent.isNullOrBlank()) return Result1.failure300("内容不能为空!!!")

        val insertArticles = mainService.articlesService.insertArticles(articles)
        if (insertArticles<=0) return Result1.failure300("添加博文失败!!!")
        return Result1.success200("添加博文成功")
    }


    /**
     * 添加博文 浏览量
     */
    @RequestMapping("/addViews")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun addViews(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val articles = CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data)

        logger.info("addViews",articles.toString())
        if (articles ==null) return Result1.failure300("参数错误!!!")

        if (articles.articleid==0) return Result1.failure300("缺少博文Id!!!")

        if (articles.articleViews==0)  return Result1.failure300("浏览量不能为空!!!")

        val insertArticles = mainService.articlesService.insertViews(articles.articleid,articles.articleViews)
        if (insertArticles<=0) return Result1.failure300("添加浏览量失败!!!")
        return Result1.success200("添加浏览量成功")
    }

    /**
     * 添加博文 评论总数
     */
    @RequestMapping("/addCommentCount")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun addCount(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val articles = CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data)

        logger.info("传递过来的addCount参数",articles.toString())
        if (articles ==null) return Result1.failure300("参数错误!!!")

        if (articles.articleid==0) return Result1.failure300("缺少博文Id!!!")

        if (articles.articleCommentCount==0)  return Result1.failure300("评论总数异常!!!")
        val queryById = mainService.articlesService.queryById(articles.articleid)
        logger.info("查询的数据",queryById.toString())
        if (queryById.articleCommentCount<=0&&articles.articleCommentCount>articles.articleCommentCount) return Result1.failure300("articleid需大于评论总数!!!")

        val insertArticles = mainService.articlesService.insertViews(articles.articleid,articles.articleViews)
        if (insertArticles<=0) return Result1.failure300("添加评论总数失败!!!")
        return Result1.success200("添加评论总数成功")
    }


    /**
     * 修改博文
     */
    @RequestMapping("/UpContent")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun UpContent(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val articles = CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data)

        logger.info("传递过来的UpContent参数",articles.toString())
        if (articles ==null) return Result1.failure300("参数错误!!!")

        if (articles.articleid==0) return Result1.failure300("缺少博文Id!!!")
        if (articles.articleTitle.isNullOrBlank()) return Result1.failure300("缺少博文标题!!!")
        if (articles.articleContent.isNullOrBlank()) return Result1.failure300("缺少博文内容!!!")
        //如果日期传过来了 则使用，否则使用当前日期
        if (articles.articleDate.isNullOrBlank()){
            var time  = TimeUtil.getDate("yyyy-MM-dd HH:mm:ss")
            articles.articleDate = time
        }
        //只修改标题与内容
        val insertArticles = mainService.articlesService.updateTitelContent(articles)
        if (insertArticles<=0) return Result1.failure300("修改失败!!!")
        return Result1.success200("修改成功")
    }


    /**
     * 修改博文
     */
    @RequestMapping("/UpAttribute")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun UpAttribute(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val articles = CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data)

        logger.info("传递过来的UpAttribute参数",articles.toString())
        if (articles ==null) return Result1.failure300("参数错误!!!")

        if (articles.articleid==0) return Result1.failure300("缺少博文Id!!!")
        if (articles.userid==0) return Result1.failure300("缺少用户Id!!!")
        if (articles.articleTitle.isNullOrBlank()) return Result1.failure300("缺少博文标题!!!")
        if (articles.articleContent.isNullOrBlank()) return Result1.failure300("缺少博文内容!!!")
        //如果日期传过来了 则使用，否则使用当前日期
        if (articles.articleDate.isNullOrBlank()){
            var time  = TimeUtil.getDate("yyyy-MM-dd HH:mm:ss")
            articles.articleDate = time
        }
        //全量修改
        val insertArticles = mainService.articlesService.updateArticles(articles)
        if (insertArticles<=0) return Result1.failure300("修改失败!!!")
        return Result1.success200("修改成功")
    }


    /**
     * 修改博文
     */
    @RequestMapping("/deleteAttribute")
    @CrossOrigin
    @Decrypt
    @Encrypt
    @ResponseBody
    fun deleteAttribute(@ModelAttribute("data") data: String?, request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val articles = CheckReceivedDataUtil.JsonToClass<ArticlesEntity>(ArticlesEntity::class.java, data)

        logger.info("传递过来的UpAttribute参数",articles.toString())
        if (articles ==null) return Result1.failure300("参数错误!!!")

        if (articles.articleid==0) return Result1.failure300("缺少博文Id!!!")

        //单条删除
        val insertArticles = mainService.articlesService.deleteById(articles.articleid)
        if (insertArticles<=0) return Result1.failure300("删除失败!!!")
        return Result1.success200("删除成功")
    }





}