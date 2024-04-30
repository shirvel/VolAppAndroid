package com.example.app.database

// PostDao.kt
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg post: Post)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): List<Post>

    @Delete
    fun delete(post: Post)
}
