package com.kfeth.template.api

import com.google.gson.annotations.SerializedName
import com.kfeth.template.data.ArticleEntity

data class NewsResponse(
    val articles: List<ArticleResponse>
)

data class ArticleResponse(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    @SerializedName("urlToImage") val imageUrl: String,
    val publishedAt: String,
    val content: String
)

data class Source(
    val name: String
)

fun ArticleResponse.mapToEntity() =
    ArticleEntity(
        url = url,
        author = author,
        source = source.name,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content
    )
