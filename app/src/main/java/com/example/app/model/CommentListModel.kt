package com.example.app.model


import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.app.database.AppLocalDatabase
import java.util.concurrent.Executors
import com.example.app.Modules.Comments.CommentViewModel


class CommentListModel  private constructor(){
    enum class LoadingState {
        LOADING,
        LOADED
    }
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    private val firebaseModel = CommentFirebaseModel()
    private val comments: LiveData<MutableList<Comment>>? = null
    val commentsListLoadingState: MutableLiveData<CommentListModel.LoadingState> = MutableLiveData(
        CommentListModel.LoadingState.LOADED)
    companion object {
        val instance: CommentListModel = CommentListModel()
    }
    interface GetAllCommentsListener {
        fun onComplete(comments: List<Comment>)
    }
    fun getAllCommants() :LiveData<MutableList<Comment>> {
        refreshgetAllComments()
        return comments ?: database.commentDao().getAllComments()
    }
    fun refreshgetAllComments() {

        val lastUpdated: Long = Comment.lastUpdated

        firebaseModel.getAllComments(lastUpdated){ list->
            Log.i("TAG","firebase returned ${list.size}, lastupdated: $lastUpdated")
            executor.execute{
                var time = lastUpdated
                for (comment in list){
                    database.commentDao().insert(comment)
                    comment.lastUpdated?.let {
                        if (time < it)
                            time = comment.lastUpdated ?: System.currentTimeMillis()
                    }
                }
                Comment.lastUpdated = time
                commentsListLoadingState.postValue(CommentListModel.LoadingState.LOADED)
            }
        }
    }

    fun addComment(comment: Comment, callback: () -> Unit)
    {
        firebaseModel.addComment(comment){
            refreshgetAllComments()
            callback()
        }

    }
}