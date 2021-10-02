package dev.eastar.roomtest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "USERS")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val level: Level,
    val date: LocalDate = LocalDate.now(),
    // val location: String?,
    // val photo: String?,
)

enum class Level {
    Level1, Level2, Level3,
}