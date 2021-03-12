package com.wzj.blog.myblog.service

import com.wzj.blog.myblog.service.imageService.ImageService
import com.wzj.blog.myblog.service.userFriends.UserFriendsService
import com.wzj.blog.myblog.service.userService.UserService
import com.wzj.blog.myblog.service.articles.ArticlesService
import com.wzj.blog.myblog.service.setting.aticleLable.ArticleLableService
import com.wzj.blog.myblog.service.setting.atitleSort.ArtitleSortService
import com.wzj.blog.myblog.service.comment.CommentService
import com.wzj.blog.myblog.service.lanels.LanelsService
import com.wzj.blog.myblog.service.sorts.SortsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * 公共表操作类
 * 统一管理表
 */
@Service
open class MainService {

    /**
     * 用户表
     */
    @Autowired
    lateinit var userService: UserService

    /**
     * 好友表
     */
    @Autowired
    lateinit var userFriendsService: UserFriendsService

    /**
     * 图片表
     */
    @Autowired
    lateinit var imageService: ImageService


    /**
     * 发表博文
     */
    @Autowired
    lateinit var articlesService: ArticlesService


    /**
     * 发表评论
     */
    @Autowired
    lateinit var commentService: CommentService



    /**
     * 设置文章标签表
     */
    @Autowired
    lateinit var articleLableService: ArticleLableService



    /**
     * 设置文章分类
     */
    @Autowired
    lateinit var artitleSortService: ArtitleSortService


    /**
     * 标签表
     */
    @Autowired
    lateinit var lanelsService: LanelsService



    /**
     * 分类表
     */
    @Autowired
    lateinit var sortsService: SortsService






}