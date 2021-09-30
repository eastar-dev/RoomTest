package dev.eastar.roomtest.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(entity: UserEntity): Long

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    fun getUser(id: Long): UserEntity?

    @Delete
    fun deleteUser(entity: UserEntity)
}
