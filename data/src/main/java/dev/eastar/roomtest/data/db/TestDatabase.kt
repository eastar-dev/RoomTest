package dev.eastar.roomtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
