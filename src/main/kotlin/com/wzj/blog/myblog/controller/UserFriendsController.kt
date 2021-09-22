package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.config.rsaUtil.annotation.Decrypt
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt
import com.wzj.blog.myblog.entity.BaseData
import com.wzj.blog.myblog.entity.UserFriendsEntity
import com.wzj.blog.myblog.result.Result1
import com.wzj.blog.myblog.service.MainService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * 好友表
 */
@Controller
@RequestMapping("/userFriends")
class UserFriendsController {

    @Autowired
    lateinit var mainService: MainService

    /**
     *添加好友
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/addFriends"])
    fun add(@ModelAttribute("data") data: String?,request : HttpServletRequest): BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val friendsEntity = CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java, data)
        if (friendsEntity==null)  return Result1.failure300("用户不存在!")


        if (friendsEntity.userFriendsId<=0) return Result1.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return Result1.failure300("用户ID不能为空!!!")
        if (friendsEntity.userId==friendsEntity.userFriendsId) return Result1.failure300("不能添加自己为好友!!!")
        val mlist = mainService.userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
        if (mlist.size>0){
            return Result1.failure300("已经是您的好友了!!!")
        }

        val insertFriends = mainService.userFriendsService.insertFriends(friendsEntity)
        if (insertFriends<=0){
            return Result1.failure300("添加好友失败!!!")
        }

         return Result1.success200("添加好友成功!!!")
    }

    /**
     *修改好友 状态 以及 好友备注
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/upFriends"])
    fun update(@ModelAttribute("data") data: String?,request : HttpServletRequest):BaseData<Any?>{
        if (CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val friendsEntity = CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java, data)
        if (friendsEntity==null)  return Result1.failure300("用户不存在!")

        if (friendsEntity.userFriendsId<=0) return Result1.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return Result1.failure300("用户ID不能为空!!!")
        if (friendsEntity.userId==friendsEntity.userFriendsId) return Result1.failure300("不能修改自己为好友!!!")
        val mlist = mainService.userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
        if (mlist.size<=0){
            return Result1.failure300("修改失败,不是您的好友!!!")
        }
        val userFriendsEntity = mlist[0]
        if (friendsEntity.userNote.isNullOrBlank()){
            friendsEntity.userNote =userFriendsEntity.userNote
        }

        if (friendsEntity.userStatus<=0){
            friendsEntity.userStatus =userFriendsEntity.userStatus
        }

        val insertFriends = mainService.userFriendsService.updateFriends(friendsEntity)
        if (insertFriends<=0){
            return Result1.failure300("修改好友失败!!!")
        }
         return Result1.success200("修改好友成功!!!")
    }


    /**
     *修改好友 状态 以及 好友备注
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/findFriends"])
    fun findFriends(@ModelAttribute("data") data: String?,request : HttpServletRequest):BaseData<Any?>{
        if (CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java,data)==null) return Result1.failure300("格式错误!!!")
        val friendsEntity = CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java, data)
        if (friendsEntity==null)  return Result1.failure300("用户不存在!")

        if (friendsEntity.userFriendsId<=0) return Result1.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return Result1.failure300("用户ID不能为空!!!")
        val mlist = mainService.userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
         return Result1.success200(Gson().toJson(mlist),"查询好友成功!!!")
    }




    /**
     *删除好友
     * @param friendsInfo json  必传 好友ID 用户ID
     */
    @ResponseBody
    @CrossOrigin
    @Decrypt
    @Encrypt
    @PostMapping(value = ["/dlFriends"])
    fun deleteFriends(@ModelAttribute("data") data: String?,request : HttpServletRequest):BaseData<Any?> {
        if (CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java, data) == null) return Result1.failure300("格式错误!!!")
        val friendsEntity = CheckReceivedDataUtil.JsonToClass<UserFriendsEntity>(UserFriendsEntity::class.java, data)
        if (friendsEntity == null) return Result1.failure300("用户不存在!")

        if (friendsEntity.userFriendsId <= 0) return Result1.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId <= 0) return Result1.failure300("用户ID不能为空!!!")
        val mlist = mainService.userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
        if (mlist.size <= 0) {
            return Result1.failure300("删除失败,无此好友!!")
        }
        val deleteFriendsList = mainService.userFriendsService.deleteFriendsById(friendsEntity.userId, friendsEntity.userFriendsId)
        if (deleteFriendsList <= 0) {
            return Result1.failure300("删除失败")
        }
        return Result1.success200("删除成功好友成功!!!")


    }




}