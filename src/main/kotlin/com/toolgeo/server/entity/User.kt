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
    var avatarUrl: String? = null
    var nickName: String? = null


    @TableLogic
    var deleted: Boolean? = null

    var audited: Boolean? = null

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
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
