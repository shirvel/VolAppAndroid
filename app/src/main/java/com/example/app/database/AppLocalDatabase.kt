package com.example.app.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app.database.PostDao
import com.example.app.database.CommentDao
import com.example.app.database.UserDao
import com.example.app.model.User
import com.example.app.model.Post
import com.example.app.model.Comment
import com.example.app.base.MyApplication
    @Database(entities = [Post::class, Comment::class, User::class], version = 5)
    abstract  class AppLocalDbRepository : RoomDatabase() {
        abstract fun userDao() : UserDao
        abstract fun postDao() : PostDao
        abstract fun commentDao() : CommentDao
    }


object AppLocalDatabase {
    val db: AppLocalDbRepository by lazy {
        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")
        Room.databaseBuilder(
            context,
            AppLocalDbRepository::class.java,
            "dbFileName.db"
        )
            .fallbackToDestructiveMigration() //remove from prod
            .build()
    }
}