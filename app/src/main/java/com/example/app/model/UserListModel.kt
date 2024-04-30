package com.example.app.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
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



    fun addUser(user: User, callback: () -> Unit) {
        userFirebaseModel.addUser(user, callback)

    }

//    fun signIn(email: String, password: String) {
//    //    Firebase.auth.signInWithEmailAndPassword(email, password).await()
//        userFirebaseModel.signIn(email, password)
//    }


}
