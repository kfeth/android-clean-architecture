package com.kfeth.template.di

import android.content.Context
import androidx.room.Room
import com.kfeth.template.data.NewsDao
import com.kfeth.template.data.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDao(database: NewsDatabase): NewsDao = database.newsDao()

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext appContext: Context): NewsDatabase =
        Room.databaseBuilder(appContext, NewsDatabase::class.java, NewsDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()
}
