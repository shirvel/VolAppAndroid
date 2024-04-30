package com.example.app.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.app.Modules.Comments.Comments
import com.example.app.database.AppLocalDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.logging.Handler

class CommentListModel  private constructor(){
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    companion object {
        val instance: CommentListModel = CommentListModel()
    }
    interface GetAllCommentsListener {
        fun onComplete(comments: List<Comments>)
    }
    fun getAllComments(callback: (List<Comment>) -> Unit) {
        executor.execute {
            val comments = database.commentDao().getAllComments()
            mainHandler.post{
                callback(comments)
            }
        }
    }

    fun addComment(comment: Comment, callback: () -> Unit)
    {
        executor.execute{
            database.commentDao().insert(comment)
            mainHandler.post{
                callback()
            }
        }
    }
}