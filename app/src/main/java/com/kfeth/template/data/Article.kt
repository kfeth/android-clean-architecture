package com.kfeth.template.data

data class Article(
    val url: String,
    val author: String,
    val title: String,
    val imageUrl: String?,
    val source: String,
    val content: String
)