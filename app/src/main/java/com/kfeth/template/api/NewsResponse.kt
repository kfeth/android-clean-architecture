package com.kfeth.template.api

import com.google.gson.annotations.SerializedName
import com.kfeth.template.data.Article

data class NewsResponse(
    val articles: List<ArticleResponse>
)

data class ArticleResponse(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    @SerializedName("urlToImage") val imageUrl: String,
    val publishedAt: String,
    val content: String
)

fun ArticleResponse.mapToEntity() =
    Article(
        url = url,
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content
    )
