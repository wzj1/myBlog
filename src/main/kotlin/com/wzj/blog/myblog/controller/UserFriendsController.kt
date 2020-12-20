package com.wzj.blog.myblog.controller

import com.google.gson.Gson
import com.wzj.blog.myblog.entity.ResultData
import com.wzj.blog.myblog.entity.UserFriendsEntity
import com.wzj.blog.myblog.entity.UserInfo
import com.wzj.blog.myblog.returnUtil.GetResultData
import com.wzj.blog.myblog.service.UserFriends.UserFriendsService
import com.wzj.blog.myblog.util.CheckReceivedDataUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/userFriends")
class UserFriendsController {

    @Autowired
    lateinit var userFriendsService: UserFriendsService

    /**
     *添加好友
     */
    @ResponseBody
    @PostMapping(value = ["/add"], consumes = ["application/json"])
    fun add(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas

        val friendsEntity: UserFriendsEntity = Gson().fromJson(data!!.data, UserFriendsEntity::class.java)

        if (friendsEntity.userFriendsId<=0) return GetResultData.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return GetResultData.failure300("用户ID不能为空!!!")
        if (friendsEntity.userId==friendsEntity.userFriendsId) return GetResultData.failure300("不能添加自己为好友!!!")
        val mlist = userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
        if (mlist.size>0){
            return GetResultData.failure300("已经是您的好友了!!!")
        }

        val insertFriends = userFriendsService.insertFriends(friendsEntity)
        if (insertFriends<=0){
            return GetResultData.failure300("添加好友失败!!!")
        }

         return GetResultData.success200("添加好友成功!!!")
    }

    /**
     *修改好友 状态 以及 好友备注
     */
    @ResponseBody
    @PostMapping(value = ["/update"], consumes = ["application/json"])
    fun update(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas
        val friendsEntity: UserFriendsEntity = Gson().fromJson(data!!.data, UserFriendsEntity::class.java)

        if (friendsEntity.userFriendsId<=0) return GetResultData.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return GetResultData.failure300("用户ID不能为空!!!")
        if (friendsEntity.userId==friendsEntity.userFriendsId) return GetResultData.failure300("不能修改自己为好友!!!")
        val mlist = userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
        if (mlist.size<=0){
            return GetResultData.failure300("修改失败,不是您的好友!!!")
        }
        val userFriendsEntity = mlist[0]
        if (friendsEntity.userNote.isNullOrBlank()){
            friendsEntity.userNote =userFriendsEntity.userNote
        }

        if (friendsEntity.userStatus<=0){
            friendsEntity.userStatus =userFriendsEntity.userStatus
        }

        val insertFriends = userFriendsService.updateFriends(friendsEntity)
        if (insertFriends<=0){
            return GetResultData.failure300("修改好友失败!!!")
        }
         return GetResultData.success200("修改好友成功!!!")
    }


    /**
     *修改好友 状态 以及 好友备注
     */
    @ResponseBody
    @PostMapping(value = ["/findFriends"], consumes = ["application/json"])
    fun findFriends(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas
        val friendsEntity: UserFriendsEntity = Gson().fromJson(data!!.data, UserFriendsEntity::class.java)

        if (friendsEntity.userFriendsId<=0) return GetResultData.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return GetResultData.failure300("用户ID不能为空!!!")
        val mlist = userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
         return GetResultData.success200(Gson().toJson(mlist),"查询好友成功!!!")
    }




    /**
     *删除好友
     * @param friendsInfo json  必传 好友ID 用户ID
     */
    @ResponseBody
    @PostMapping(value = ["/deleteFriends"], consumes = ["application/json"])
    fun deleteFriends(@RequestBody data: ResultData<String>?):String{
        //判断参数
        val datas = CheckReceivedDataUtil.IsCheckReceivedDataNull(data)
        if (!datas.isNullOrBlank()) return datas
        val friendsEntity: UserFriendsEntity = Gson().fromJson(data!!.data, UserFriendsEntity::class.java)

        if (friendsEntity.userFriendsId<=0) return GetResultData.failure300("好友ID不能为空!!!")
        if (friendsEntity.userId<=0) return GetResultData.failure300("用户ID不能为空!!!")
        val mlist = userFriendsService.queryFriendsByIdAndUserId(friendsEntity.userId, friendsEntity.userFriendsId)
        if (mlist.size<=0){
            return GetResultData.failure300("删除失败,无此好友!!")
        }
        val deleteFriendsList = userFriendsService.deleteFriendsById(friendsEntity.userId, friendsEntity.userFriendsId)
        if (deleteFriendsList<=0){
            return GetResultData.failure300("删除失败")
        }
        return GetResultData.success200("删除成功好友成功!!!")
    }








}