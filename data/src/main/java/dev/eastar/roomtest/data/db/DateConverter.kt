package dev.eastar.roomtest.data.db

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

object DateConverter {
    @TypeConverter
    fun String.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.parse(this)
    }

    @TypeConverter
    fun LocalDateTime.fromLocalDateTime(): String {
        return toString()
    }
}
