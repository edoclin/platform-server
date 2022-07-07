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
 * @since 2022-07-04
 */
@TableName("t_live")
class Live : Serializable {

    @TableId(value = "live_id", type = IdType.ASSIGN_UUID)
    var liveId: String? = null

    var liveCoverImg: String? = null

    var liveTitle: String? = null

    var livePushStreamName: String? = null

    var liveIntroduction: String? = null

    var liveDesc: String? = null

    var startTime: LocalDateTime? = null
    var realStartTime: LocalDateTime? = null

    var endTime: LocalDateTime? = null
    var realEndTime: LocalDateTime? = null


    @TableLogic
    var deleted: Boolean? = null
    var finished: Boolean? = null
    var recorded: Boolean? = null
    var showed: Boolean? = null
    var liveVideoUrl: String? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "Live{" +
        "liveId=" + liveId +
        ", liveCoverImg=" + liveCoverImg +
        ", liveTitle=" + liveTitle +
        ", livePushStreamName=" + livePushStreamName +
        ", liveIntroduction=" + liveIntroduction +
        ", liveDesc=" + liveDesc +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
