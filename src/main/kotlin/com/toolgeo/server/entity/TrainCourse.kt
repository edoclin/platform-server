package com.toolgeo.server.entity

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableField
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableLogic
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.time.LocalDateTime

/**
 * <p>
 * 
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@TableName("t_train_course")
class TrainCourse : Serializable {

    @TableId(value = "train_course_id", type = IdType.ASSIGN_UUID)
    var trainCourseId: String? = null

    var trainCourseName: String? = null

    var trainCourseVideo: String? = null

    var trainCourseIntroduction: String? = null

    var showed: Boolean? = null

    var trainCourseIndex: Int? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "TrainCourse{" +
        "trainCourseId=" + trainCourseId +
        ", trainCourseName=" + trainCourseName +
        ", trainCourseVideo=" + trainCourseVideo +
        ", trainCourseIntroduction=" + trainCourseIntroduction +
        ", showed=" + showed +
        ", trainCourseIndex=" + trainCourseIndex +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
