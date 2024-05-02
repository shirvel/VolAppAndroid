package com.example.app.database

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
    fun getAllComments(): List<Comment>

    @Delete
    fun delete(comment: Comment)
}
