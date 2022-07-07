package com.toolgeo.server.controller;

import cn.hutool.core.bean.BeanUtil
import com.toolgeo.server.entity.Base
import com.toolgeo.server.service.IBaseService
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.AppBase
import com.toolgeo.server.view.CmsBase
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
@RequestMapping("/server/base")
class BaseController {
    @Resource
    lateinit var iBaseService: IBaseService

    @PostMapping("/cms")
    fun postBase(@RequestBody base: Base): ResultBean {
        if (BeanUtil.isNotEmpty(base)) {
            if (iBaseService.save(base)) {
                return ResultUtil.ok()
            }
        }
        return ResultUtil.error()
    }

    @GetMapping("/cms")
    fun listBase(): ResultBean {
        val result = mutableListOf<CmsBase>()

        iBaseService.list().mapTo(result) {
            val item = CmsBase()
            BeanUtil.copyProperties(it, item)
            item.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.updatedTime)
            item.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.createdTime)
            item
        }
        return ResultUtil.ok(result)
    }

    @GetMapping("/app")
    fun listBaseApp(): ResultBean {
        val result = mutableListOf<AppBase>()
        iBaseService.list().mapTo(result) {
            val item = AppBase()
            item.name = it.baseName
            item.baseId = it.baseId
            item
        }
        return ResultUtil.ok(result)
    }
}
