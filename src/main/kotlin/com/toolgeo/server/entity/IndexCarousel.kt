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
@TableName("t_INDEX_carousel")
class IndexCarousel : Serializable {

    @TableId(value = "carousel_id", type = IdType.ASSIGN_UUID)
    var carouselId: String? = null

    var trainId: String? = null

    var carouselPlaceholder: String? = null

    var carouselImgUrl: String? = null

    var showed: Boolean? = null

    var carouselIndex: Int? = null

    @TableLogic
    var deleted: Boolean? = null

    @TableField(fill = FieldFill.UPDATE)
    var updatedTime: LocalDateTime? = null

    @TableField(fill = FieldFill.INSERT)
    var createdTime: LocalDateTime? = null

    override fun toString(): String {
        return "IndexCarousel{" +
        "carouselId=" + carouselId +
        ", trainId=" + trainId +
        ", carouselPlaceholder=" + carouselPlaceholder +
        ", carouselImgUrl=" + carouselImgUrl +
        ", showed=" + showed +
        ", carouselIndex=" + carouselIndex +
        ", deleted=" + deleted +
        ", updatedTime=" + updatedTime +
        ", createdTime=" + createdTime +
        "}"
    }
}
