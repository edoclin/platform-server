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
@TableName("t_theme")
class Theme : Serializable {

    @TableId(value = "theme_id", type = IdType.ASSIGN_UUID)
    var themeId: String? = null

    var themeName: String? = null

    var viewName: String? = null

    var relatedCount: Int? = null

    var showed: Boolean? = null

    var themeIndex: Int? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "Theme{" +
        "themeId=" + themeId +
        ", themeName=" + themeName +
        ", viewName=" + viewName +
        ", relatedCount=" + relatedCount +
        ", showed=" + showed +
        ", themeIndex=" + themeIndex +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
