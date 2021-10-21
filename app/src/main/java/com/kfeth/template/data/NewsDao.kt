package com.kfeth.template.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM article")
    fun getAllArticles(): Flow<List<Article>>

    @Query("DELETE FROM article")
    suspend fun deleteAllArticles()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<Article>)

    @Query("SELECT * FROM article WHERE url = :articleId")
    fun getArticle(articleId: String): Flow<Article>
}
