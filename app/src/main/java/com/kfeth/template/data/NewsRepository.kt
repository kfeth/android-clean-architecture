package com.kfeth.template.data

import com.kfeth.template.util.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getArticle(articleId: String): Flow<Article>

    suspend fun getLatestNewsStream(): Flow<Result<List<Article>>>
}