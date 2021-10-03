package dev.eastar.roomtest.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE USERS ADD COLUMN location TEXT")
        database.execSQL("ALTER TABLE USERS ADD COLUMN photo TEXT")
    }
}
