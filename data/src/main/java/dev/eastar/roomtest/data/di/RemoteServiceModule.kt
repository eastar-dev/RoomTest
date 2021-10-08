package dev.eastar.roomtest.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.eastar.roomtest.data.source.RemoteService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteServiceModule {

    @Singleton
    @Provides
    fun provideRemoteService(): RemoteService = RemoteService()
}
