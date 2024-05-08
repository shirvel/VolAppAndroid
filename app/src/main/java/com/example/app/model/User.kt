package com.example.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    var email: String,
    var password: String,
    var name: String,
//    var image: String,
)

