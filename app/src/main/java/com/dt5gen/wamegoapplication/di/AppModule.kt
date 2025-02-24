package com.dt5gen.wamegoapplication.di

import android.content.Context
import androidx.room.Room
import com.dt5gen.wamegoapplication.data.ClipboardRepository
import com.dt5gen.wamegoapplication.data.HistoryDao
import com.dt5gen.wamegoapplication.data.HistoryDatabase
import com.dt5gen.wamegoapplication.domain.ClipboardUseCase
import com.dt5gen.wamegoapplication.domain.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // ✅ Теперь Hilt знает, как передавать `Context` 8 (915) 444 -11-44
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HistoryDatabase::class.java,
            "history_database"
        )
            .fallbackToDestructiveMigration() // ✅ Если можно удалить старые данные
            .build()
    }

    @Provides
    fun provideHistoryDao(database: HistoryDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(historyDao: HistoryDao): HistoryRepository {
        return HistoryRepository(historyDao)
    }

    @Provides
    @Singleton
    fun provideClipboardRepository(@ApplicationContext context: Context): ClipboardRepository {
        return ClipboardRepository(context)
    }

    @Provides
    @Singleton
    fun provideClipboardUseCase(repository: ClipboardRepository): ClipboardUseCase {
        return ClipboardUseCase(repository)
    }
}
