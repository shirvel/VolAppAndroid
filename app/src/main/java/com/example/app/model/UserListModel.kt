package com.example.app.model

import android.os.Looper
import androidx.core.os.HandlerCompat

public class UserListModel private constructor() {
    val users: MutableList<User> = ArrayList()

    private val userFirebaseModel = UserFirebaseModel()
    //TODO: need to create the executer? (Treads)
    private var mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val instance: UserListModel = UserListModel()
    }



    fun addUser(user: User, callback: () -> Unit) {
        userFirebaseModel.addUser(user, callback)

    }
}
