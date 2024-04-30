package com.example.app.model
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.app.database.AppLocalDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.logging.Handler

class UserListModel private constructor() {
    private val database = AppLocalDatabase.db
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())
    companion object {
        val instance: UserListModel = UserListModel()
    }
    interface GetAllUsersListener {
        fun onComplete(user: List<User>)
    }
    //fun getAllUsers(callback: (List<User>) -> Unit) {
     //   executor.execute {
     //       val posts = database.userDao().getAllUsers()
         //   mainHandler.post{
           //     callback(posts)
           // }
        //}
    //}

    fun addUser(user: User, callback: () -> Unit)
    {
        executor.execute{
            database.userDao().insert(user)
            mainHandler.post{
                callback()
            }
        }
    }
}