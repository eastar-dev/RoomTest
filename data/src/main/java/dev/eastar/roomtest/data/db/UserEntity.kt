package dev.eastar.roomtest.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import kotlin.random.Random

@Entity(tableName = "USERS")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    // val level: Level,
    // val date: LocalDateTime = LocalDateTime.now().withNano(0),
    val location: String? = "geo:${Random.latitude},${Random.longitude}",
    val photo: String? = "https://picsum.photos/id/${Random.nextInt(100)}/100/100",
) {
    companion object {
        val RANDOM: UserEntity
            get() = UserEntity(
                0,
                ('a'..'z').shuffled().take(Random.nextInt(4, 'z' - 'a')).toCharArray().joinToString(""),
                // Level.values().random(),
                // LocalDateTime.now().withNano(0),
            )
    }
}

enum class Level {
    Level1, Level2, Level3,
}

private val Random.Default.latitude: String
    get() = nextDouble(-90.0, +90.0).format(4)

private val Random.Default.longitude: String
    get() = nextDouble(-90.0, +90.0).format(4)

fun Double.format(digits: Int) = "%.${digits}f".format(this)