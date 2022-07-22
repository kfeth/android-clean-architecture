package com.kfeth.template.di

import com.kfeth.template.data.NewsRepository
import com.kfeth.template.data.OfflineFirstNewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsNewsRepository(
        // TODO Use [OnlineOnlyNewsRepository] if required
        newsRepository: OfflineFirstNewsRepository
    ): NewsRepository
}
