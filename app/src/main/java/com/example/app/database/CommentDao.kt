package com.example.app.database

// PostDao.kt
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app.model.Comment

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg comment: Comment)

    @Query("SELECT * FROM comments")
    fun getAllComments(): LiveData<MutableList<Comment>>

    @Delete
    fun delete(comment: Comment)
    @Query("SELECT * FROM comments WHERE postId = :postId")
    fun getCommentsByPostId(postId: String): LiveData<MutableList<Comment>>

}
