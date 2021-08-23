package com.kfeth.androidcleanarchitecture.data

import retrofit2.http.GET

interface UsersApi {

    @GET("api?results=10")
    suspend fun getUsers(): UsersResponse
}