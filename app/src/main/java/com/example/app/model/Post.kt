package com.example.app.model

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app.base.MyApplication
import android.content.Context
import android.location.Address
import androidx.room.ColumnInfo
import com.google.firebase.firestore.FieldValue
import com.google.firebase.Timestamp
import java.util.UUID

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey
    val postId: String, // Generating random UUID as postId
    var title: String,
    var writer: String,
    var content: String,
    var image: String,
    var address: String,
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

        fun fromJSON(json: Map<String, Any>): Post{
            val title = json["title"] as? String?: ""
            val content = json["content"] as? String?: ""
            val address = json["address"] as? String?: ""
            //val post = Post(title, "", content, "", false, address)
            val image = json["image"] as? String?: ""
            val postId = json["postId"] as? String?: ""
            val writer = json["writer"] as? String?: ""
            val post = Post(postId,title, writer, content, image ,address)
            val timeStamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
            timeStamp?.let {
                post.lastUpdated = it.seconds
            }
            return post
        }
    }
    val json: Map<String, Any>
        get() {
            return  hashMapOf(
                "postId" to postId,
                "title" to title,
                "writer" to writer,
                "content" to content,
                "image" to image,
                "address" to address,
                LAST_UPDATED to FieldValue.serverTimestamp()
            )
        }
}