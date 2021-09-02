package com.kfeth.template.api

import com.google.gson.annotations.SerializedName
import com.kfeth.template.data.Article

data class NewsResponse(
    @SerializedName("articles") val articles: List<ArticleResponse>
)

data class ArticleResponse(
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val imageUrl: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
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
