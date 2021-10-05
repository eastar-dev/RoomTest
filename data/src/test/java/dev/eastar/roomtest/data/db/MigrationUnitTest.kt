package dev.eastar.roomtest.data.db

import androidx.core.database.getStringOrNull
import androidx.room.Room
import dev.eastar.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(sdk = [30])
@RunWith(RobolectricTestRunner::class)
class MigrationUnitTest {
    companion object {
        const val TEST_DB = "migration-test"
    }

    @get:Rule
    val helper: MigrationTestHelper =
        MigrationTestHelper(
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
        //given
        helper.createDatabase(TEST_DB, 1).use {
            it.execSQL("INSERT INTO USERS ( name,level,date ) VALUES ('eastar', 'Level1' ,0);")
        }
        //when
        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2).use {
            //then
            val cursor = it.query("SELECT COUNT(*) FROM USERS")
            cursor.moveToFirst()
            val actual = cursor.getLong(0)
            cursor.close()
            assertThat(actual).isEqualTo(1)
        }
    }

    @Test
    fun migration_2_3() {
        //given
        helper.createDatabase(TEST_DB, 2).use {
            /** 0 => UTC 1970-01-01 00:00:00.000 */
            it.execSQL("INSERT INTO USERS ( name,level,date,location,photo ) VALUES ('eastar', 'Level1' ,0,NULL,NULL);")
        }
        //when
        helper.runMigrationsAndValidate(TEST_DB, 3, true, MIGRATION_2_3).use {
            //then
            val cursor = it.query("SELECT id,name,level,date,location,photo FROM USERS")
            cursor.moveToFirst()
            val id = cursor.getLong(0)//id
            val date = cursor.getString(3)//date
            val location = cursor.getStringOrNull(4)//location
            cursor.close()
            assertThat(id).isEqualTo(1)
            assertThat(DateConverter.text2LocalDateTime(date))
                .isEqualTo(DateConverter.text2LocalDateTime("1970-01-01T00:00:00.000"))
            assertThat(location).isNull()
        }
    }
}


