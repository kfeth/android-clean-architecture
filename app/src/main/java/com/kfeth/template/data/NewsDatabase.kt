package com.kfeth.template.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    companion object {
        const val NAME = "news.db"
        const val TABLE_NAME_ARTICLE = "article"
    }

    abstract fun newsDao(): NewsDao
}