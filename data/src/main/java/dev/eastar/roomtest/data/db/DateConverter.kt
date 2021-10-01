package dev.eastar.roomtest.data.db

import androidx.room.TypeConverter
import java.time.LocalDate

object DateConverter {

    @TypeConverter
    fun toDate(dateText: String): LocalDate {
        return LocalDate.parse(dateText)
    }

    @TypeConverter
    fun fromDate(date: LocalDate): String {
        return date.toString()
    }
}
