package dev.eastar.roomtest.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.eastar.roomtest.data.db.TestDatabase
import dev.eastar.roomtest.data.repository.UserRepository
import dev.eastar.roomtest.data.source.RemoteService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        database: TestDatabase,
        remoteService: RemoteService,
    ): UserRepository = UserRepository(database, remoteService)
}
