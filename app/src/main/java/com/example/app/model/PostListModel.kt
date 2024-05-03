package com.example.app.model
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app.database.AppLocalDatabase
import java.util.concurrent.Executors

class PostListModel private constructor() {

    enum class LoadingState {
        LOADING,
        LOADED
    }
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = PostFirebaseModel()
    private val posts: LiveData<MutableList<Post>>? = null
    val postsListLoadingState: MutableLiveData<LoadingState> = MutableLiveData(LoadingState.LOADED)
    companion object {
        val instance: PostListModel = PostListModel()
    }
    interface GetAllPostsListener {
        fun onComplete(post: List<Post>)

    }
    fun getAllPosts() :LiveData<MutableList<Post>> {
        refreshgetAllPosts()
        return posts ?: database.postDao().getAllPosts()
    }
    fun refreshgetAllPosts() {

        val lastUpdated: Long = Post.lastUpdated

        firebaseModel.getAllPosts(lastUpdated){ list->
            Log.i("TAG","firebase returned ${list.size}, lastupdated: $lastUpdated")
            executor.execute{
                var time = lastUpdated
                for (post in list){
                   database.postDao().insert(post)
                   post.lastUpdated?.let {
                       if (time < it)
                           time = post.lastUpdated ?: System.currentTimeMillis()
                   }
                }
                Post.lastUpdated = time
                postsListLoadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    fun addPost(post: Post, callback: () -> Unit)
    {
        firebaseModel.addPost(post){
            refreshgetAllPosts()
            callback()
        }

    }
}