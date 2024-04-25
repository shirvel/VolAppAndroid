package com.example.app.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val writer: String,
    var content: String,
    var image: String,
    var isLiked: Boolean
)

