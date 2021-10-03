package dev.eastar.roomtest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

@Entity(tableName = "USERS")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val level: Level,
    val date: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        val RANDOM: UserEntity
            get() = UserEntity(
                0,
                ('a'..'z').shuffled().take(Random.nextInt('z' - 'a')).toCharArray().joinToString(""),
                Level.values().random(),
                LocalDateTime.now(),
            )
    }
}

enum class Level {
    Level1, Level2, Level3,
}

