package com.kfeth.androidcleanarchitecture.data

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val imageUrl: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
)

fun Article.mapToEntity() =
    ArticleEntity(
        url = url,
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content
    )