package dev.eastar.roomtest.data.db

import static com.google.common.truth.Truth.assertThat
import static com.google.common.truth.Truth.assertWithMessage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class MigrationUnitTest {
    companion object {
        const val TEST_DB = "migration-test"
    }

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TestDatabase::class.java,
    )

    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        helper.createDatabase(TEST_DB, 1).use { }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TestDatabase::class.java,
            TEST_DB
        ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build().apply {
            openHelper.writableDatabase
            close()
        }
    }

    @Test
    fun migration_1_2() {
        helper.createDatabase(TEST_DB, 1).use {
            it.execSQL("INSERT INTO EmotionalEntity ( emotion,date,reason ) VALUES ('SADNESS',0,'It''s rain');")
        }
        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2).use { }
    }

    @Test
    fun migration_2_3() {
        helper.createDatabase(TEST_DB, 2).use {
            it.execSQL("INSERT INTO EmotionalEntity ( emotion,date,reason ) VALUES ('SADNESS',0,'rain');")
        }
        helper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_2_3).use { }
    }
}


