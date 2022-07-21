package com.kfeth.template.data

import com.kfeth.template.api.ArticleResponse

fun ArticleResponse.asEntityModel() = ArticleEntity(
    url = url,
    author = author,
    source = source.name,
    title = title,
    description = description.orEmpty(),
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    content = content
)

fun ArticleResponse.asExternalModel() = Article(
    url = url,
    author = author.orEmpty(),
    title = title,
    imageUrl = imageUrl,
    source = source.name,
    content = content.orEmpty()
)

fun ArticleEntity.asExternalModel() = Article(
    url = url,
    author = author.orEmpty(),
    title = title,
    imageUrl = imageUrl,
    source = source,
    content = content.orEmpty()
)
