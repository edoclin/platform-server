package com.toolgeo.server.controller;

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.toolgeo.server.entity.TrainCourse
import com.toolgeo.server.service.ITrainCourseService
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.AppTrainCourse
import com.toolgeo.server.view.AppTrainCourseDetail
import com.toolgeo.server.view.CmsTrainCourse
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
@RequestMapping("/server/trainCourse")
class TrainCourseController {
    @Resource
    lateinit var iTrainCourseService: ITrainCourseService

    @PostMapping("/cms")
    fun postTrainCourse(@RequestBody trainCourse: TrainCourse): ResultBean {
        if (BeanUtil.isNotEmpty(trainCourse)) {
            if (trainCourse.showed == true) {
                val update = iTrainCourseService.getOne(
                    Wrappers.query<TrainCourse?>()
                        .eq("showed", true)
                        .eq("train_course_index", trainCourse.trainCourseIndex)
                )
                if (BeanUtil.isNotEmpty(update)) {
                    update.showed = false
                    iTrainCourseService.updateById(update)
                }
            }
            try {
                iTrainCourseService.save(trainCourse)
            } catch (e: Exception) {
                return ResultUtil.error("参数冲突")
            }

            return ResultUtil.ok("添加成功")

        }
        return ResultUtil.error("参数错误")
    }

    @GetMapping("/cms")
    fun listTrainCourse(): ResultBean {
        val result = mutableListOf<CmsTrainCourse>()
        iTrainCourseService.list().mapTo(result) {
            val cmsTrainCourse = CmsTrainCourse()
            BeanUtil.copyProperties(it, cmsTrainCourse)
            cmsTrainCourse.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.updatedTime)
            cmsTrainCourse.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.createdTime)
            cmsTrainCourse
        }
        return ResultUtil.ok(result)
    }

    @DeleteMapping("/cms/{trainCourseId}")
    fun deleteTrainCourse(@PathVariable trainCourseId: String): ResultBean {
        if (StrUtil.isNotBlank(trainCourseId)) {
            if (iTrainCourseService.removeById(trainCourseId)) {
                return ResultUtil.ok("删除成功")
            }
        }
        return ResultUtil.error("参数错误")
    }

    @PutMapping("/cms")
    fun updateTrainCourse(@RequestBody trainCourse: TrainCourse): ResultBean {
        if (BeanUtil.isNotEmpty(trainCourse)) {
            if (iTrainCourseService.updateById(trainCourse)) {
                return ResultUtil.ok("更新成功");
            }
        }
        return ResultUtil.error("参数错误")
    }

    @GetMapping("/app/{classId}/{current}/{index}")
    fun getTrainCourseByClassId(@PathVariable classId: String, @PathVariable current: Long, @PathVariable index: Boolean = true): ResultBean {
        val result = mutableListOf<AppTrainCourse>()
        if (StrUtil.isNotBlank(classId)) {
            var page = Page<TrainCourse>(current, 2)
            var wrapper = Wrappers.query<TrainCourse?>().eq("class_id", classId)
            if (index) {
                wrapper = wrapper.eq("showed", true).orderByAsc("train_course_index")
                iTrainCourseService.page(page, wrapper).records.mapTo(result) {
                    val item = AppTrainCourse()
                    BeanUtil.copyProperties(it, item)
                    item
                }
            } else{
                wrapper = wrapper.eq("showed", false).orderByAsc("updated_time")
                page = Page<TrainCourse>(current, 10)
                iTrainCourseService.page(page, wrapper).records.mapTo(result) {
                    val item = AppTrainCourse()
                    BeanUtil.copyProperties(it, item)
                    item
                }
            }

        }
        return ResultUtil.ok(result)
    }


    @GetMapping("/app/detail/{trainCourseId}")
    fun getTrainCourseById(@PathVariable trainCourseId: String): ResultBean {

        if (StrUtil.isNotBlank(trainCourseId)) {
            val course = iTrainCourseService.getById(trainCourseId)
            if (BeanUtil.isNotEmpty(course)) {

                val detail = AppTrainCourseDetail()
                BeanUtil.copyProperties(course, detail)
                detail.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(course.createdTime)
                detail.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(course.updatedTime)

                return ResultUtil.ok(detail)
            }
        }
        return ResultUtil.error("课程编号非法")
    }
}
