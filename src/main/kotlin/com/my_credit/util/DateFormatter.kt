package com.my_credit.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    val defaultTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    fun toString(date: Date): String = kotlin.runCatching {
        SimpleDateFormat(defaultTimePattern).format(date)
    }.getOrNull().orEmpty()

    @JvmName("toStringNullable")
    fun toString(date: Date?): String? = date?.run {
        kotlin.runCatching {
            SimpleDateFormat(defaultTimePattern).format(this)
        }.getOrNull().orEmpty()
    }

    fun fromString(string: String): Date = kotlin.runCatching {
        SimpleDateFormat(defaultTimePattern).parse(string)
    }.getOrNull() ?: Date()

    @JvmName("fromStringNullable")
    fun fromString(string: String?): Date? = string?.run {
        kotlin.runCatching {
            SimpleDateFormat(defaultTimePattern).parse(this)
        }.getOrNull() ?: Date()
    }

}