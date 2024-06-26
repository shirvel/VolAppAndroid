package com.example.app.database

// PostDao.kt
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.model.Comment
import com.example.app.model.Post

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg post: Post)

    @Query("SELECT * FROM posts")
    fun getAllPosts(): LiveData<MutableList<Post>>

    @Query("DELETE FROM posts WHERE postId = :postId")
    fun deletePost(postId: String)
    @Delete
    fun delete(post: Post)
    @Query("SELECT * FROM posts WHERE postId = :postId")
    fun getPostById(postId: String): LiveData<Post>
    @Query("SELECT * FROM posts WHERE writer = :writer")
    fun getAllPostsByUser(writer: String):LiveData<MutableList<Post>>

}
