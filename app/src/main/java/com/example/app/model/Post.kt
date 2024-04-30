package com.example.app.model

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey val postId: String,
    val writer: String,
    var title: String,
    var content: String,
    var image: String,
    var isLiked: Boolean) {
        companion object {
            fun fromJSON(json: Map<String, Any>): Post{
                val title = json["title"] as? String?: ""
                val content = json["content"] as? String?: ""
                return Post("0", "", title , content, "", false)
            }
        }
    val json: Map<String, Any>
        get() {
            return  hashMapOf(
                "postId" to postId,
                "writer" to writer,
                "title" to title,
                "content" to content,
                "image" to image,
                "isLiked" to isLiked,
            )
        }
    }


