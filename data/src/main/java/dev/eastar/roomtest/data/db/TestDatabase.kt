package dev.eastar.roomtest.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [UserEntity::class],
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    version = 2,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
