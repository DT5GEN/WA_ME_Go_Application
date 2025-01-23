package com.dt5gen.wamegoapplication.di

import android.content.Context
import com.dt5gen.wamegoapplication.data.ClipboardRepository
import com.dt5gen.wamegoapplication.domain.ClipboardUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideClipboardRepository(@ApplicationContext context: Context): ClipboardRepository {
        return ClipboardRepository(context)
    }

    @Provides
    fun provideClipboardUseCase(repository: ClipboardRepository): ClipboardUseCase {
        return ClipboardUseCase(repository)
    }
}
