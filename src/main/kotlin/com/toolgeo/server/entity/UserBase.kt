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
 * @since 2022-07-03
 */
@TableName("t_user_base")
class UserBase : Serializable {

    @TableId(value = "user_base_id", type = IdType.ASSIGN_UUID)
    var userBaseId: String? = null

    var baseId: String? = null

    var userId: String? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "UserBase{" +
        "userBaseId=" + userBaseId +
        ", baseId=" + baseId +
        ", userId=" + userId +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
