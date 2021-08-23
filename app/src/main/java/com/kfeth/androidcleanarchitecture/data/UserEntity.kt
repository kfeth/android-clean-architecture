package com.kfeth.androidcleanarchitecture.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    val email: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)