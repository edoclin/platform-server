package com.toolgeo.server.controller;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.collection.CollUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.crypto.digest.MD5
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.toolgeo.server.entity.Live
import com.toolgeo.server.entity.User
import com.toolgeo.server.service.ILiveService
import com.toolgeo.server.service.IUserService
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.AppLive
import com.toolgeo.server.view.CmsLive
import com.toolgeo.server.view.LiveInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.annotation.Resource

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengsx
 * @since 2022-07-04
 */
@RestController
@RequestMapping("/server/live")
class LiveController {

    @Value("\${tencent.live.primary-key}")
    lateinit var primaryKey: String

    @Value("\${tencent.live.secondary-key}")
    lateinit var secondaryKey: String

    @Value("\${tencent.live.valid-time}")
    var validTime: Number = 0


    @Value("\${tencent.live.push.primary-key}")
    lateinit var pushPrimaryKey: String

    @Value("\${tencent.live.push.secondary-key}")
    lateinit var pushSecondaryKey: String

    @Resource
    lateinit var iLiveService: ILiveService

    @Resource
    lateinit var iUserService: IUserService

    @PostMapping("/cms")
    fun postLiveCms(@RequestBody live: Live): ResultBean {
        if (BeanUtil.isNotEmpty(live)) {
            live.startTime = live.startTime?.plusHours(8)
            live.endTime = live.endTime?.plusHours(8)

            if (CollUtil.isNotEmpty(
                    iLiveService.list(
                        Wrappers.query<Live?>()
                            .ge("start_time", live.startTime)
                            .le("start_time", live.endTime)
                            .or()
                            .le("start_time", live.startTime)
                            .ge("end_time", live.endTime)
                            .or()
                            .ge("end_time", live.startTime)
                            .le("end_time", live.endTime)
                    )
                )
            ) {
                return ResultUtil.error("直播时间冲突")
            }
            if (iLiveService.save(live)) {
                return ResultUtil.ok("添加成功")
            }
        }
        return ResultUtil.error()
    }

    @GetMapping("/cms")
    fun listLiveCms(): ResultBean {
        val result = mutableListOf<CmsLive>()
        iLiveService.list().mapTo(result) {
            val cmsLive = CmsLive()
            BeanUtil.copyProperties(it, cmsLive)
            cmsLive.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.createdTime)
            cmsLive.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.updatedTime)
            cmsLive.startTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.startTime)
            cmsLive.endTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.endTime)
            if (LocalDateTime.now().isBefore(it.startTime)) {
                cmsLive.status = "未开始"
            } else if (LocalDateTime.now().isAfter(it.startTime) and LocalDateTime.now().isBefore(it.endTime)) {
                cmsLive.status = "直播中"
            } else if (LocalDateTime.now().isAfter(it.endTime)) {
                cmsLive.status = "已结束"
            }

            if (LocalDateTime.now().isAfter(it.realStartTime) and (it.finished == false)) {
                cmsLive.status = "直播中"
            }
            if (it.finished == true) {
                cmsLive.status = "已结束"
            }
            cmsLive
        }
        return ResultUtil.ok(result)
    }


    @GetMapping("/app/{current}/{index}")
    fun listLiveApp(@PathVariable current: Long, @PathVariable index: Boolean): ResultBean {
        val result = mutableListOf<AppLive>()
        var page: IPage<Live>
        var query = Wrappers.query<Live>()
        if (index) {
            page = Page(current, 2)
            query = query.eq("showed", true)
        } else {
            page = Page(current, 10)
        }
        iLiveService.page(page, query).records.mapTo(result) {
            val appLive = AppLive()
            BeanUtil.copyProperties(it, appLive)
            appLive.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.createdTime)
            appLive.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.updatedTime)
            appLive.startTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.startTime)
            appLive.endTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.endTime)
            appLive.streamName = it.livePushStreamName
            if (LocalDateTime.now().isBefore(it.startTime)) {
                appLive.status = "未开始"
            } else if (LocalDateTime.now().isAfter(it.startTime) and LocalDateTime.now().isBefore(it.endTime)) {
                appLive.status = "直播中"
            } else if (LocalDateTime.now().isAfter(it.endTime)) {
                appLive.status = "已结束"
            }

            if (BeanUtil.isNotEmpty(it.realStartTime) && LocalDateTime.now()
                    .isAfter(it?.realStartTime) && (it.finished == false)
            ) {
                appLive.status = "直播中"
            }
            if (it.finished == true) {
                appLive.status = "已结束"
            }
            appLive
        }
        return ResultUtil.ok(result)
    }

    @GetMapping("/cms/will-start")
    fun listLiveWillStart(): ResultBean {
        val result = mutableListOf<CmsLive>()

        iLiveService.list(
            Wrappers.query<Live?>()
                .eq("finished", false)
                .gt("start_time", LocalDateTime.now())
        ).mapTo(result) {
            val cmsLive = CmsLive()
            cmsLive.liveId = it.liveId
            cmsLive.liveTitle = it.liveTitle
            cmsLive.livePushStreamName = it.livePushStreamName
            cmsLive
        }
        return ResultUtil.ok(result)
    }

    @GetMapping("/cms/push/{liveId}")
    fun generateTxSecretPush(@PathVariable liveId: String): ResultBean {
        if (StrUtil.isNotBlank(liveId)) {
            val currentTime = (System.currentTimeMillis() / 1000 + 60 * 60 * 24).toString(16)
            val byId = iLiveService.getById(liveId)
            if (BeanUtil.isNotEmpty(byId)) {
                val data =
                    "${if (StrUtil.isNotBlank(pushPrimaryKey)) pushPrimaryKey else pushSecondaryKey}${byId.livePushStreamName}${currentTime}"
                val txSecret = MD5.create().digestHex(data)
                val liveInfo = LiveInfo()
                liveInfo.txSecret = txSecret
                liveInfo.txTime = currentTime
                return ResultUtil.ok(liveInfo)
            }
        }
        return ResultUtil.error()
    }

    @GetMapping("/cms/live/{liveId}/{openId}")
    fun generateTxSecretLive(@PathVariable liveId: String, @PathVariable openId: String): ResultBean {
        if (StrUtil.isNotBlank(liveId) && BeanUtil.isNotEmpty(
                iUserService.getOne(
                    Wrappers.query<User?>().eq("wechat_uid", openId)
                )
            )
        ) {
            var currentTime = (System.currentTimeMillis() / 1000 + 60 * 60 * 24).toString(16)
            currentTime = "62C5A4B2"
            val byId = iLiveService.getById(liveId)
            if (BeanUtil.isNotEmpty(byId)) {
                val data =
                    "${if (StrUtil.isNotBlank(primaryKey)) primaryKey else secondaryKey}${byId.livePushStreamName}${currentTime}"
                val txSecret = MD5.create()
                    .digestHex(data)
                val liveInfo = LiveInfo()
                liveInfo.txSecret = txSecret
                liveInfo.txTime = currentTime
                return ResultUtil.ok(liveInfo)
            }
        }
        return ResultUtil.error()
    }
}
