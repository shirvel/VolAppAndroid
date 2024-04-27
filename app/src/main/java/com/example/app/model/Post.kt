package com.example.app.model

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val postId: Int = 0,
    val writer: String,
    var title: String,
    var content: String,
    var image: String,
    var isLiked: Boolean
)

