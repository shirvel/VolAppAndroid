package com.example.app.database

// PostDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.model.Comment

@Dao
interface CommentDao {
    @Insert
    fun insert(comment: Comment)

    @Query("SELECT * FROM comments")
    fun getAllPosts(): List<Comment>
}
