package com.domikado.bit.utility

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object DateUtil {

    const val PATTERN_ISO_DATE = "yyyy-MM-dd"

    fun stringToDate(source: String): LocalDate {
        return LocalDate.parse(source)
    }

    fun stringToDate(source: String, pattern: String): LocalDate {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(pattern))
    }
}