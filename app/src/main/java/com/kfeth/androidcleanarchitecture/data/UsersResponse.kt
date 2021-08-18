package com.kfeth.androidcleanarchitecture.data

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("results") val users: List<User>
)

data class User(
    @SerializedName("email") val email: String
)
