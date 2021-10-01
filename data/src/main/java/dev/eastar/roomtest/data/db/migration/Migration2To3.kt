package dev.eastar.roomtest.data.db.migration

import androidx.core.database.getStringOrNull
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.Instant
import java.time.ZoneId

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """CREATE TABLE IF NOT EXISTS `UserEntity3` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `level` TEXT NOT NULL,
                `date` TEXT NOT NULL,
                `desc` TEXT NOT NULL,
                `location` TEXT,
                `photo` TEXT)"""
        )
        val cursor =
            database.query(
                """SELECT
                      id,
                      level,
                      date,
                      desc,
                      location,
                      photo
                      FROM UserEntity""")

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val level = cursor.getInt(1)
            val date = cursor.getLong(2)
            val desc = cursor.getString(3)
            val location = cursor.getStringOrNull(4)
            val photo = cursor.getStringOrNull(5)

            database.execSQL(
                """INSERT INTO UserEntity3 (id, level, date, desc, location, photo )
                     VALUES (
                     $id,
                     $level,
                     '${Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()}',
                     '$desc',
                     ${location?.let { "'$location'" } ?: "NULL"},
                     ${photo?.let { "'$location'" } ?: "NULL"}
                    );"""
            )
        }
        database.execSQL("DROP TABLE UserEntity")
        database.execSQL("ALTER TABLE UserEntity3 RENAME TO UserEntity")
    }
}
