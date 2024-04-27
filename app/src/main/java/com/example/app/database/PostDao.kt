package com.example.app.database

// PostDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.model.Post

@Dao
interface PostDao {
    @Insert
    fun insert(post: Post)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<Post>
}
