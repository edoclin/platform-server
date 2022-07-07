package com.toolgeo.server.entity

import com.baomidou.mybatisplus.annotation.*
import java.io.Serializable
import java.time.LocalDateTime

/**
 * <p>
 * 
 * </p>
 *
 * @author fengsx
 * @since 2022-06-27
 */
@TableName("t_INDEX_class")
class IndexClass : Serializable {

    @TableId(value = "class_id", type = IdType.ASSIGN_UUID)
    var classId: String? = null

    var className: String? = null

    var classIntroduction: String? = null

    var bgColor: String? = null

    var classIndex: Int? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "IndexClass{" +
        "classId=" + classId +
        ", className=" + className +
        ", classIntroduction=" + classIntroduction +
        ", bgColor=" + bgColor +
        ", classIndex=" + classIndex +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
