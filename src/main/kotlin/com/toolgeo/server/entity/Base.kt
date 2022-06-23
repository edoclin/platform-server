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
 * @since 2022-06-22
 */
@TableName("t_base")
class Base : Serializable {

    @TableId(value = "base_id", type = IdType.ASSIGN_UUID)
    var baseId: String? = null

    var baseName: String? = null

    var baseIndex: Int? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "Base{" +
        "baseId=" + baseId +
        ", baseName=" + baseName +
        ", baseIndex=" + baseIndex +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
