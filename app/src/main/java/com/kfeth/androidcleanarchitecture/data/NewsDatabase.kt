package com.kfeth.androidcleanarchitecture.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    companion object {
        const val NAME = "news-db"
    }

    abstract fun newsDao(): NewsDao
}