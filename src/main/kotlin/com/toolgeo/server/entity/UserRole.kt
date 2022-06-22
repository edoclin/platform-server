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
@TableName("t_user_role")
class UserRole : Serializable {

    @TableId(value = "user_role_id", type = IdType.ASSIGN_UUID)
    var userRoleId: String? = null

    var userId: String? = null

    var roleId: String? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "UserRole{" +
        "userRoleId=" + userRoleId +
        ", userId=" + userId +
        ", roleId=" + roleId +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
