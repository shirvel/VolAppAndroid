package com.example.app.model
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey val commentId: String,
    val writer: String,
    val content: String,
    val postId: String){
    companion object {
        fun fromJSON(json: Map<String, Any>): Comment{
            val writer = json["writer"] as? String?: ""
            val content = json["content"] as? String?: ""
            val postId = json["postId"] as? String?: ""
            return Comment("",writer,content,postId)
        }
    }
    val json: Map<String, Any>
        get() {
            return  hashMapOf(
                "postId" to postId,
                "writer" to writer,
                "content" to content,
            )
        }
}

