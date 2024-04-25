package com.example.app.database

// PostDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.Model.Post

@Dao
interface PostDao {
    @Insert
    fun insert(post: Post)

    @Query("SELECT * FROM post")
    fun getAllPosts(): List<Post>
}
