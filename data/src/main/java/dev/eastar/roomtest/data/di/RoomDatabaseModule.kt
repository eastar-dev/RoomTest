package dev.eastar.roomtest.data.di

import android.content.Context
import androidx.room.Room
import dev.eastar.roomtest.data.db.UserRoomDatabase
import dev.eastar.roomtest.data.db.migration.MIGRATION_1_2
import dev.eastar.roomtest.data.db.migration.MIGRATION_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): UserRoomDatabase {
        return Room.databaseBuilder(context, UserRoomDatabase::class.java, "emotion-db")
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }
}
