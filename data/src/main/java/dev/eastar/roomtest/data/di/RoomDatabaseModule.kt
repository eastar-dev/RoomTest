package dev.eastar.roomtest.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.eastar.roomtest.data.db.TestDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TestDatabase = Room.databaseBuilder(context, TestDatabase::class.java, "user-db")
        .build()

    @Singleton
    @Provides
    fun provideDao(
        database: TestDatabase
    ) = database.getUserDao()
}
