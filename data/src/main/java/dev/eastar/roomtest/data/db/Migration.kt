package dev.eastar.roomtest.data.db

import androidx.core.database.getStringOrNull
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.Instant
import java.time.ZoneId

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE UserEntity ADD COLUMN location TEXT")
        database.execSQL("ALTER TABLE UserEntity ADD COLUMN photo TEXT")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """CREATE TABLE IF NOT EXISTS `UserEntity3` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `level` TEXT NOT NULL,
                `date` TEXT NOT NULL,
                `location` TEXT,
                `photo` TEXT)"""
        )
        val cursor =
            database.query(
                """SELECT
                      id,
                      name,
                      level,
                      date,
                      location,
                      photo
                      FROM UserEntity"""
            )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val level = cursor.getInt(2)
            val date = cursor.getLong(3)
            val location = cursor.getStringOrNull(4)
            val photo = cursor.getStringOrNull(5)

            database.execSQL(
                """INSERT INTO UserEntity3 (id, name, level, date, location, photo )
                     VALUES (
                     $id,
                     $level,
                     '$name',
                     '${Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()}',
                     ${location?.let { "'$location'" } ?: "NULL"},
                     ${photo?.let { "'$location'" } ?: "NULL"}
                    );"""
            )
        }
        database.execSQL("DROP TABLE UserEntity")
        database.execSQL("ALTER TABLE UserEntity3 RENAME TO UserEntity")
    }
}
