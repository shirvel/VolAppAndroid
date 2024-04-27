package com.example.app.database

// PostDao.kt
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.app.model.User

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users")
    fun getAllPosts(): List<User>
}
