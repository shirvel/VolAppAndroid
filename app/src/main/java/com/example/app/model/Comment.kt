package com.example.app.model
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true) val commentId: Int = 0,
    val writer: String,
    val content: String,
    val postId: Int
)

