package com.example.app.model
import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true) val commentId: Long = 0,
    val writer: String,
    val content: String,
    val postId: String,
    var lastUpdated: Long? = null ) {
    companion object {
        var lastUpdated: Long
            get(){
                return MyApplication.Globals
                    .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.getLong(GET_LAST_UPDATED,0) ?: 0
            }
            set(value){
                MyApplication.Globals
                    ?.appContext
                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)?.edit()
                    ?.putLong(GET_LAST_UPDATED, value)?.apply()
            }
        var LAST_UPDATED = "lastUpdated"
        var GET_LAST_UPDATED = "get_last_updated"

        fun fromJSON(json: Map<String, Any>): Comment{
            val writer = json["writer"] as? String?: ""
            val content = json["content"] as? String?: ""
            val postId = json["postId"] as? String?: ""
            val comment = Comment(0L,writer, content, postId)
            val timeStamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
            timeStamp?.let {
                comment.lastUpdated = it.seconds
            }
            return comment
        }
    }
    val json: Map<String, Any>
        get() {
            return  hashMapOf(
                // "postId" to postId,
                "writer" to writer,
                "content" to content,
                "postId" to postId,
                LAST_UPDATED to FieldValue.serverTimestamp()
            )
        }
}


