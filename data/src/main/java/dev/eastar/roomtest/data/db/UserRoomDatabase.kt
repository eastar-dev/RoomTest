package dev.eastar.roomtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.eastar.roomtest.data.db.DateConverter
import dev.eastar.roomtest.data.db.UserDao
import dev.eastar.roomtest.data.db.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = true)
@TypeConverters(DateConverter::class)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
