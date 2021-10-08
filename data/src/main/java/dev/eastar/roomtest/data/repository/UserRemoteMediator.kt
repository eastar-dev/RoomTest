package dev.eastar.roomtest.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.eastar.roomtest.data.db.TestDatabase
import dev.eastar.roomtest.data.db.UserEntity
import dev.eastar.roomtest.data.source.RemoteService
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val key: SomeKeyword,
    private val database: TestDatabase,
    private val remoteService: RemoteService
) : RemoteMediator<Int, UserEntity>() {
    private val userDao = database.getUserDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return kotlin.runCatching {
            if (loadType == LoadType.PREPEND)
                return MediatorResult.Success(true)

            val users = remoteService.getUsers()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) userDao.clearUser()
                userDao.insertUser(*users)
            }

            MediatorResult.Success(users.isNullOrEmpty())
        }.getOrElse {
            MediatorResult.Error(it)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)
        return if (System.currentTimeMillis() - userDao.lastUpdated() >= cacheTimeout)
            InitializeAction.SKIP_INITIAL_REFRESH
        else
            InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}