package com.toolgeo.server.util.date

import cn.hutool.core.date.DateUtil
import cn.hutool.core.util.ObjectUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    fun formatLocalDateTime_yyyy_MM_dd_HH_mm(dateTime: LocalDateTime?): String? {
        return if (ObjectUtil.isNull(dateTime)) {
            "无"
        } else dateTime?.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分"))
    }

    fun formatLocalDateTime_yyyy_MM_dd(dateTime: LocalDateTime?): String? {
        return if (ObjectUtil.isNull(dateTime)) {
            "无"
        } else dateTime?.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
    }

    fun formatLocalDateTime_yyyy_MM_dd(dateTime: Date?): String? {
        return if (ObjectUtil.isNull(dateTime)) {
            "无"
        } else DateUtil.toLocalDateTime(dateTime).format(DateTimeFormatter.ofPattern("yyyy年MM月dd日"))
    }
}