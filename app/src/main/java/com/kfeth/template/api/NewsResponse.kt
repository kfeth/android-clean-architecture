package com.kfeth.template.api

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val articles: List<ArticleResponse>
)

data class ArticleResponse(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    val publishedAt: String,
    val content: String?
)

data class Source(
    val name: String
)