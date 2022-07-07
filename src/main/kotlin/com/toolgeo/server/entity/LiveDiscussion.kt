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
 * @since 2022-07-04
 */
@TableName("t_live_discussion")
class LiveDiscussion : Serializable {

    @TableId(value = "live_discussion_id", type = IdType.ASSIGN_UUID)
    var liveDiscussionId: String? = null

    var message: String? = null

    var openId: String? = null

    var liveId: String? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "LiveDiscussion{" +
        "liveDiscussionId=" + liveDiscussionId +
        ", message=" + message +
        ", openId=" + openId +
        ", liveId=" + liveId +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
