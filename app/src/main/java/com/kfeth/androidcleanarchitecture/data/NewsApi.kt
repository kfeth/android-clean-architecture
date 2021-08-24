package com.kfeth.androidcleanarchitecture.data

import com.kfeth.androidcleanarchitecture.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsApi {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = BuildConfig.NEWS_API_KEY
    }

    @Headers("X-Api-Key: $API_KEY")
    @GET("top-headlines?country=ie")
    suspend fun getBreakingNews(): NewsResponse
}