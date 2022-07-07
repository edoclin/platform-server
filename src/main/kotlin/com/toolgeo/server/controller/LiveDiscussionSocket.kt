package com.toolgeo.server.controller;

import cn.hutool.db.nosql.redis.RedisDS
import cn.hutool.json.JSONUtil
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.slf4j.Slf4j.Companion.log
import com.toolgeo.server.view.AppUserInfo
import com.toolgeo.server.view.LiveDiscussionData
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import java.time.LocalDateTime
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@Component
@ServerEndpoint("/ws/live/discuss/{openId}/{liveId}")
class LiveDiscussionSocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    var session: Session? = null

    companion object {
        @JvmStatic
        val webSockets: CopyOnWriteArraySet<LiveDiscussionSocket> = CopyOnWriteArraySet()

        @JvmStatic
        val sessionPool: HashMap<String, Session> = HashMap()

        @JvmStatic
        val jedis: Jedis = RedisDS.create().jedis
    }

    @OnOpen
    fun onOpen(session: Session, @PathParam(value = "openId") openId: String) {
        try {
            this.session = session
            webSockets.add(this)
            sessionPool[openId] = session
            log.info("【websocket消息】有新的连接，总数为:" + webSockets.size)
            log.info("【websocket消息】有新的连接，openId: $openId")
        } catch (_: Exception) {
        }
    }

    @OnClose
    fun onClose() {
        try {
            webSockets.remove(this)
            log.info("【websocket消息】连接断开，总数为:" + webSockets.size)
        } catch (_: Exception) {
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param session
     */
    @OnMessage
    fun onMessage(message: String, @PathParam(value = "openId") openId: String, @PathParam(value = "liveId") liveId: String) {
        val result = LiveDiscussionData()
        result.openId = openId
        result.content = message
        result.onlineCount = webSockets.size
        result.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(LocalDateTime.now())
        jedis.lpush(liveId, JSONUtil.toJsonStr(result))

        val appUserInfo = JSONUtil.toBean(jedis.get(openId), AppUserInfo::class.java)

        result.nickName = appUserInfo.nickName
        result.avatarUrl = appUserInfo.avatarUrl
        sendAllMessage(JSONUtil.toJsonStr(result))

    }

    /** 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    fun onError(session: Session?, error: Throwable) {
        log.error("用户错误:" + this.session?.id + ",原因:" + error.message)
        error.printStackTrace()
    }


    // 广播消息
    fun sendAllMessage(message: String) {
        for (webSocket in webSockets) {
            try {
                if (webSocket.session?.isOpen == true) {
                    webSocket.session?.asyncRemote?.sendText(message)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }
}
