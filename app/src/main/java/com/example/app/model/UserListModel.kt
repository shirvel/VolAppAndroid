package com.example.app.model

import android.os.Looper
import android.view.View
import androidx.core.os.HandlerCompat
import androidx.lifecycle.LiveData
import java.util.concurrent.Executor
import java.util.concurrent.Executors

public class UserListModel private constructor() {
    val users: MutableList<User> = ArrayList()

    private val userFirebaseModel = UserFirebaseModel()
    //TODO: need to create the executer? (Treads)
    private var executor = Executors.newSingleThreadExecutor()
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: UserListModel = UserListModel()
    }

    fun getUserImageByEmail(email: String, onSuccess: (String?) -> Unit, onFailure: (String) -> Unit) {
        return userFirebaseModel.getUserImageByEmail(email,onSuccess, onFailure)
    }

    fun addUser(view: View, user: User, callback: () -> Unit) {
        userFirebaseModel.addUser(view, user, callback)

    }

    fun signIn(view: View, email: String, password: String, callback: () -> Unit) {
        userFirebaseModel.signIn(view, email, password, callback)
    }

}
