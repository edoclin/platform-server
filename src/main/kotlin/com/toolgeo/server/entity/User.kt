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
@TableName("t_user")
class User : Serializable {

    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    var userId: String? = null

    var username: String? = null

    var password: String? = null

    var wechatUid: String? = null

    var name: String? = null

    var male: Boolean? = null

    var idNumber: String? = null

    var mobile: String? = null

    var baseId: String? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "User{" +
        "userId=" + userId +
        ", username=" + username +
        ", password=" + password +
        ", wechatUid=" + wechatUid +
        ", name=" + name +
        ", male=" + male +
        ", idNumber=" + idNumber +
        ", mobile=" + mobile +
        ", baseId=" + baseId +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
