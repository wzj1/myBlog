package com.wzj.blog.myblog.listener

import com.wzj.blog.myblog.config.Constant
import com.wzj.blog.myblog.service.UserFriends.UserFriendsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.servlet.annotation.WebListener
import javax.servlet.http.*


/**
 * 监听Session 创建以及失效后处理逻辑
 */
@WebListener
open class SessionListener : EventListener, HttpSessionListener, HttpSessionAttributeListener {
    @Autowired
    lateinit var userFriendsService: UserFriendsService
    val logger: Logger = LoggerFactory.getLogger(SessionListener::class.java)

    override fun sessionCreated(se: HttpSessionEvent?) {
        isSessionOrUserId(0,se)
    }

    override fun attributeAdded(se: HttpSessionBindingEvent?) {
        if (se!=null){
            val session = se.session
            val userId = session.getAttribute(Constant.USER_ID).toString()

            if (userId.isNullOrBlank()) return

        }
    }


    override fun sessionDestroyed(se: HttpSessionEvent?) {
        isSessionOrUserId(2,se)
    }

    /**
     * @param type 0  1 2失效并删除
     */
    fun isSessionOrUserId(type: Int, se: HttpSessionEvent?){

        if (se!=null) {
            val session: HttpSession = se.session
            when(type){
                //创建Session
                0 -> {
                    logger.info("---sessionCreated----")
                    val application = session.servletContext
                    // 在application范围由一个HashSet集保存所有的session
                    var sessions = HashSet<Any?>()
                    val attr= application.getAttribute(Constant.USER_ID)
                    if (attr!=null) {
                        sessions = attr as HashSet<Any?>
                    }
                    if (sessions == null) {
                        sessions = HashSet<Any?>()
                        application.setAttribute(Constant.USER_ID, sessions)
                    }
                    // 新创建的session均添加到HashSet集中
                    sessions.add(session)
                }
                
                1 -> {

                }
                //session 失效并删除
                2 -> {
                    logger.info("---sessionDestroyed----")
                    val userId = session.getAttribute(Constant.USER_ID).toString()
                    logger.info("deletedSessionId: " + session.id)
                    println(session.creationTime)
                    println(session.lastAccessedTime)
                    //判断 session 中userId 是否失效
                    if (userId == Constant.USER_ID) {
                        //获取session中用户ID
                        val userId = session.getValue(Constant.USER_ID).toString()
                        //判断userId 是否为空 不为空则修改当前用户登录状态为离线状态
                        if (!userId.isNullOrBlank()) {
                            userFriendsService.updateFriendsStatus(userId.toInt(), 3)
                        }
                    }

                    val application = session.servletContext

                    if (application!=null) {
                        val sessions = application.getAttribute(Constant.USER_ID) as HashSet<*>
                        // 销毁的session均从HashSet集中移除
                        sessions.remove(session)
                    }
                }
            }


        }

    }

}