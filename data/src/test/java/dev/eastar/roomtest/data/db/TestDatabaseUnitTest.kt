package dev.eastar.roomtest.data.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.time.LocalDateTime

@Config(sdk = [30])
@RunWith(RobolectricTestRunner::class)
class TestDatabaseUnitTest {
    private lateinit var userDao: UserDao
    private lateinit var db: TestDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TestDatabase::class.java
        ).build()
        userDao = db.getUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        //givens
        val user = UserEntity(0, "hello", Level.Level1, LocalDateTime.now().withNano(0))

        //when
        val id = userDao.insertUser(user)
        val actual = userDao.getUser(id[0])

        //then
        Truth.assertThat(actual).let {
            it.isNotNull()
            it.isEqualTo(user.copy(id = id[0]))
        }
    }
}