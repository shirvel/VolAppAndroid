package com.example.app.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirebaseModel {

    private val db = Firebase.firestore


    fun addUser(user: User, callback: () -> Unit) {

        val newUser = hashMapOf(
            "userId" to user.userId,
            "email" to user.email,
            "password" to user.password,
            "name" to user.name
        )

        db.collection("users").document(user.email).set(newUser).addOnSuccessListener {
            callback()
        }

    }
    }

