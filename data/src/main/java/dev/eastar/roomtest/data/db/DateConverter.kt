package dev.eastar.roomtest.data.db

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

object DateConverter {
    @TypeConverter
    fun Long.toLocalDateTime(): LocalDateTime {
        return Instant.ofEpochMilli(this).atOffset(ZoneOffset.UTC).toLocalDateTime()
    }

    @TypeConverter
    fun LocalDateTime.toMilli(): Long {
        return runCatching {
            atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
        }.getOrDefault(0L)
    }
}
