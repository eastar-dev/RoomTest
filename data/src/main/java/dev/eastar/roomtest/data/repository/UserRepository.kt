package dev.eastar.roomtest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.eastar.roomtest.data.db.TestDatabase
import dev.eastar.roomtest.data.db.UserEntity
import dev.eastar.roomtest.data.source.RemoteService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias SomeKeyword = Any

class UserRepository @Inject constructor(
    private val database: TestDatabase,
    private val remoteService: RemoteService
) {
    val userDao = database.getUserDao()

    @OptIn(ExperimentalPagingApi::class)
    fun getUser(key: SomeKeyword): Flow<PagingData<UserEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = UserRemoteMediator(key, database, remoteService)
        ) {
            userDao.getUsersPagingSource()
        }.flow
    }

    suspend fun insertUser(item: UserEntity): LongArray {
        return database.getUserDao().insertUser(item)
    }

    fun getAllUsers(): Flow<List<UserEntity>> {
        return database.getUserDao().getAllUsers()
    }
}