package com.example.app.model
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.app.database.AppLocalDatabase
import java.util.concurrent.Executors

class PostListModel private constructor() {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = PostFirebaseModel()
    companion object {
        val instance: PostListModel = PostListModel()
    }
    interface GetAllPostsListener {
        fun onComplete(post: List<Post>)
    }
    fun getAllPosts(callback: (List<Post>) -> Unit) {
        firebaseModel.getAllPosts(callback)
        //executor.execute {
        //    val posts = database.postDao().getAllPosts()
        //    mainHandler.post{
         //       callback(posts)
    }

    fun addPost(post: Post, callback: () -> Unit)
    {
        firebaseModel.addPost(post, callback)
        //executor.execute{
        //    database.postDao().insert(post)
        //    mainHandler.post{
        //        callback()
         //   }

    }
}