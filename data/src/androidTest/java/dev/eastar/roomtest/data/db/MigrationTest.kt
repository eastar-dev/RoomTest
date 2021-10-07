package dev.eastar.roomtest.data.db

import androidx.room.testing.MigrationTestHelper
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test

class MigrationTest {
    companion object {
        const val TEST_DB = "migration-test"
    }

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        TestDatabase::class.java,
    )

    @Test
    fun migration_1_2() {
        //given
        val name = "eastar"
        val level = "Level1"
        helper.createDatabase(TEST_DB, 1).use {
            it.execSQL("INSERT INTO USERS ( name,level,date ) VALUES ('$name', '$level' ,0);")
        }
        //when
        helper.runMigrationsAndValidate(TEST_DB, 2, true).use {
            //then
            val cursor = it.query("SELECT name, location FROM USERS")
            cursor.moveToFirst()
            val actualName = cursor.getString(0)//name
            val actualLocation = cursor.getString(1)//location
            cursor.close()
            assertThat(actualName).isEqualTo(name)
            assertThat(actualLocation).isNull()
        }
    }
}


