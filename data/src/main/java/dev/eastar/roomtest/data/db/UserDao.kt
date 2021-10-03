package dev.eastar.roomtest.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(entity: UserEntity): Long

    @Delete
    suspend fun deleteUser(entity: UserEntity): Int

    @Update
    suspend fun updateUser(entity: UserEntity): Int

    @Query("SELECT * FROM USERS WHERE id = :id")
    suspend fun getUser(id: Long): UserEntity?

    @Query("SELECT * FROM USERS")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM USERS WHERE level = :level")
    fun getUserLevel(level: Level): Flow<List<UserEntity>>
}
