package dev.eastar.roomtest.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(entity: UserEntity): Long

    @Query("SELECT * FROM USERS WHERE id = :id")
    fun getUser(id: Long): UserEntity?

    @Query("SELECT * FROM USERS")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Delete
    fun deleteUser(entity: UserEntity)

    @Query("SELECT * FROM USERS WHERE level = :level")
    fun getUserLevel(level: Level): LiveData<List<UserEntity>>
}

@Dao
abstract class UsersDao {
    @Query("SELECT * FROM USERS WHERE level = :level")
    abstract fun getUser(level: Level): Flow<UserEntity>

    fun getUserDistinctUntilChanged(level: Level) =
        getUser(level).distinctUntilChanged()
}