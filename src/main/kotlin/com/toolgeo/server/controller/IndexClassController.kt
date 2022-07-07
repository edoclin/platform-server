package com.toolgeo.server.controller;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.toolgeo.server.entity.IndexClass
import com.toolgeo.server.service.IIndexClassService
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.CmsIndexClass
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengsx
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/server/indexClass")
class IndexClassController {
    @Resource
    lateinit var iIndexClassService: IIndexClassService

    @GetMapping("/cms/size")
    fun getIndexClassSize(): ResultBean {
        return ResultUtil.ok(iIndexClassService.count())
    }

    @PostMapping("cms")
    fun postCmsIndexClass(@RequestBody indexClass: IndexClass): ResultBean {
        val check = iIndexClassService.getOne(Wrappers.query<IndexClass?>().eq("class_name", indexClass.className))
        if (BeanUtil.isNotEmpty(check)) {
            return ResultUtil.error("分类已存在")
        }
        if (iIndexClassService.save(indexClass)) {
            return ResultUtil.ok("保存成功")
        }
        return ResultUtil.error("参数错误")
    }

    @GetMapping("cms")
    fun listCms(): ResultBean {
        return listIndexClass()
    }

    @GetMapping("app")
    fun listApp(): ResultBean {
        return listIndexClass()
    }

    fun listIndexClass(): ResultBean {
        fun transformToCmsIndexClass() = { entity: IndexClass ->
            val cmsIndexClass = CmsIndexClass()
            BeanUtil.copyProperties(entity, cmsIndexClass)
            cmsIndexClass.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(entity.updatedTime)
            cmsIndexClass.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(entity.updatedTime)
            cmsIndexClass
        }
        val result = mutableListOf<CmsIndexClass>()
        iIndexClassService.list(Wrappers.query<IndexClass?>().orderByAsc("class_index")).mapTo(result, transformToCmsIndexClass())
        return ResultUtil.ok(result)
    }

    @PutMapping("/cms")
    fun putIndexClass(@RequestBody indexClass: IndexClass): ResultBean {

        if (StrUtil.isNotBlank(indexClass.classId)) {
            if (iIndexClassService.updateById(indexClass)) {
                return ResultUtil.ok("更新成功");
            }
        }
        return ResultUtil.error("参数错误")
    }

    @DeleteMapping("/cms/{classId}")
    fun deleteIndexClass(@PathVariable classId: String): ResultBean {
        if (StrUtil.isNotBlank(classId)) {
            if (iIndexClassService.removeById(classId)) {
                return ResultUtil.ok("删除成功")
            }
        }
        return ResultUtil.error("参数错误")
    }
}
