package com.kfeth.template.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Transaction
    suspend fun deleteAndInsert(news: List<ArticleEntity>) {
        deleteAllArticles()
        insertOrIgnoreNews(news)
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreNews(movies: List<ArticleEntity>): List<Long>

    @Query("SELECT * FROM article")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<ArticleEntity>)

    @Query("SELECT * FROM article WHERE url = :articleId")
    fun getArticle(articleId: String): Flow<ArticleEntity>
}
