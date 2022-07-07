package com.toolgeo.server.controller;

import cn.hutool.core.bean.BeanUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.toolgeo.server.entity.IndexCarousel
import com.toolgeo.server.service.IIndexCarouselService
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.AppIndexCarousel
import com.toolgeo.server.view.CmsIndexCarousel
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/server/indexCarousel")
class IndexCarouselController {
    @Resource
    lateinit var iIndexCarouselService: IIndexCarouselService

    @PostMapping("/cms")
    fun postIndexCarousel(@RequestBody indexCarousel: IndexCarousel): ResultBean {
        if (BeanUtil.isNotEmpty(indexCarousel)) {
            val list = iIndexCarouselService.list(
                Wrappers.query<IndexCarousel?>().eq("showed", true).orderByAsc("updated_time")
            )
            if (list.size == 5) {
                list[0].showed = false
                iIndexCarouselService.updateById(list[0])
            }
            indexCarousel.showed = true
            indexCarousel.carouselImgUrl = "https://" + indexCarousel.carouselImgUrl
            if (iIndexCarouselService.save(indexCarousel)) {
                return ResultUtil.ok("添加成功")
            }
        }
        return ResultUtil.error("参数错误")
    }

    @GetMapping("/cms")
    fun listIndexCarouselCms(): ResultBean {
        val result = mutableListOf<CmsIndexCarousel>()
        iIndexCarouselService.list().mapTo(result) {
            val item = CmsIndexCarousel()
            BeanUtil.copyProperties(it, item)
            item.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.updatedTime)
            item.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.createdTime)
            item
        }
        return ResultUtil.ok(result)
    }

    @GetMapping("/app")
    fun listIndexCarouselApp(): ResultBean {
        val result = mutableListOf<AppIndexCarousel>()
        iIndexCarouselService.list(Wrappers.query<IndexCarousel?>().eq("showed", true)).mapTo(result) {
            val item = AppIndexCarousel()
            BeanUtil.copyProperties(it, item)
            item.trainCourseId = it.trainId
            item.title = it.carouselPlaceholder
            item
        }
        return ResultUtil.ok(result)
    }
}
