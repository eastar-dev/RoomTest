package dev.eastar.roomtest.data.db

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateConverter {
    @TypeConverter
    fun text2LocalDateTime(localDateTimeText: String): LocalDateTime {
        return LocalDateTime.parse(localDateTimeText)
    }

    @TypeConverter
    fun localDateTime2Text(localDateTime: LocalDateTime): String {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}
