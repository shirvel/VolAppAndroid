package com.example.app.model
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
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

    fun getAllConnectedUserPosts() :LiveData<MutableList<Post>> {
        // TODO: Implement this the right way from the DB by new Query
        refreshgetAllPosts()
        return posts ?: database.postDao().getAllPosts()
    }
    fun getPostById(postId: String): LiveData<Post> {
        return database.postDao().getPostById(postId)
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
    fun deletePost(postId: String, callback: () -> Unit)
    {
        firebaseModel.deletePost(postId){
            // Set up an observer for changes in the local posts
            val localPostsObserver = Observer<List<Post>> { localPosts ->
                localPosts?.let {
                    // Get all post IDs from the local Room database
                    val localPostIds = localPosts.map { it.postId }

                    // Check if the deleted post ID exists in the local database
                    if (localPostIds.contains(postId)) {
                        // Delete the post from the local database
                        executor.execute {
                            val deletedPost = localPosts.find { it.postId == postId }
                            deletedPost?.let {
                                database.postDao().delete(deletedPost)
                            }
                        }
                    }
                }
            }

            // Observe changes in the local posts
            database.postDao().getAllPosts().observeForever(localPostsObserver)
            callback()
        }
    }
}