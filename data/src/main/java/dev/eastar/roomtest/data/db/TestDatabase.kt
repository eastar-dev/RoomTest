package dev.eastar.roomtest.data.db

import android.log.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [UserEntity::class],
    autoMigrations = [AutoMigration(from = 1, to = 2, spec = TestDatabase.AutoMigrationSpec_1_2::class)],
    version = 2,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class TestDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    @DeleteColumn(tableName = "USERS", columnName = "date")
    internal class AutoMigrationSpec_1_2 : AutoMigrationSpec {
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            Log.e("Invoked once auto migration is done")
        }
    }
}
