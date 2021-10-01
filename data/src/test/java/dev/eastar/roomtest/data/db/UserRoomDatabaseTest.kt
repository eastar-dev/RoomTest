package dev.eastar.roomtest.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import dev.eastar.roomtest.data.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// @RunWith(RobolectricTestRunner::class)
class UserRoomDatabaseUnitTest {

    private lateinit var db: TestDatabase

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), TestDatabase::class.java)
            .build()
    }

    @After
    fun cleanUp() {
        db.close()
    }

    @Test
    fun insertTest() = runBlocking {
    }

    @Test
    fun updateTest() = runBlocking {
    }

    @Test
    fun deleteTest() = runBlocking {
    }
}
