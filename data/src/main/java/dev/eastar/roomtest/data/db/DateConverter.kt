package dev.eastar.roomtest.data.db

import android.os.SystemClock
import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

object DateConverter {

    @TypeConverter
    fun toDate(dateText: String): LocalDate {
        Date().time
        SimpleDateFormat()
        return LocalDate.parse(dateText)
    }

    @TypeConverter
    fun fromDate(date: LocalDate): String {
        return date.toString()
    }
}
