package dev.eastar.roomtest.data.db

import androidx.core.database.getStringOrNull
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.Instant
import java.time.ZoneOffset

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        /** copy form data/schemas/dev.eastar.roomtest.data.db.TestDatabase/3.json */
        val tempTable =
            """CREATE TABLE IF NOT EXISTS `USERS_3` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `level` TEXT NOT NULL, `date` TEXT NOT NULL, `location` TEXT, `photo` TEXT)"""
        database.execSQL(tempTable)
        val cursor =
            database.query("""SELECT id, name, level, date, location, photo FROM USERS""")

        while (cursor.moveToNext()) {
            val id = cursor.getLong(0)
            val name = cursor.getString(1)
            val level = cursor.getString(2)
            val date = cursor.getLong(3)
            val location = cursor.getStringOrNull(4)
            val photo = cursor.getStringOrNull(5)

            database.execSQL(
                """INSERT INTO USERS_3 (id, name, level, date, location, photo )
                     VALUES (
                     $id,
                     '$name',
                     '$level',
                     '${Instant.ofEpochMilli(date).atOffset(ZoneOffset.UTC).toLocalDateTime()}',
                     ${location?.let { "'$location'" }},
                     ${photo?.let { "'$photo'" }}
                    );"""
            )
        }
        database.execSQL("DROP TABLE USERS")
        database.execSQL("ALTER TABLE USERS_3 RENAME TO USERS")
    }
}
