package com.domikado.bit.external.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object DateUtil {

    const val PATTERN_ISO_DATE = "yyyy-MM-dd"
    const val PATTERN_ISO_DATE_TIME = "yyyy-MM-dd HH:mm:ss"

    fun stringToDate(source: String): LocalDate {
        return LocalDate.parse(source)
    }

    fun stringToDate(source: String, pattern: String): LocalDate {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(pattern))
    }

    fun getDateTimeNow(): LocalDate {
        return LocalDate.now()
    }

    fun getNowEpochMilli(): Long {
        val now = Instant.now()
        return now.toEpochMilli()
    }

    fun isTimeAutomatic(c: Context): Boolean {
        return Settings.Global.getInt(
            c.contentResolver,
            Settings.Global.AUTO_TIME,
            0
        ) == 1
    }

    fun openDateTimeSetting(activity: Activity, RC: Int) {
        activity.startActivityForResult(Intent(Settings.ACTION_DATE_SETTINGS), RC)
    }
}