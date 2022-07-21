package com.kfeth.template.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = NewsDatabase.TABLE_NAME_ARTICLE)
data class ArticleEntity(
    @PrimaryKey val url: String,
    val author: String?,
    val source: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val publishedAt: String,
    val content: String?
)
