package com.example.app.model
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.app.database.AppLocalDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.logging.Handler

class PostListModel private constructor() {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = FirebaseModel()
    companion object {
        val instance: PostListModel = PostListModel()
    }
    interface GetAllPostsListener {
        fun onComplete(post: List<Post>)
    }
    fun getAllPosts(callback: (List<Post>) -> Unit) {
        executor.execute {
            val posts = database.postDao().getAllPosts()
            mainHandler.post{
                callback(posts)
            }
        }
    }

    fun addPost(post: Post, callback: () -> Unit)
    {
        executor.execute{
            database.postDao().insert(post)
            mainHandler.post{
                callback()
            }
        }
    }
}