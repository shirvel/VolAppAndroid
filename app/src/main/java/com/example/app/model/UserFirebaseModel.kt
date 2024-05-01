package com.example.app.model

import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class UserFirebaseModel {

    private val db = Firebase.firestore


//    fun addUser(user: User, callback: () -> Unit) {
//
//        val newUser = hashMapOf(
//            "userId" to user.userId,
//            "email" to user.email,
//            "password" to user.password,
//            "name" to user.name
//        )
//
//        db.collection("users").document(user.email).set(newUser).addOnSuccessListener {
//            callback()
//        }
//
//    }


    fun addUser(view: View, user: User, callback: () -> Unit) {
        // Check if the email already exists
        db.collection("users").whereEqualTo("email", user.email).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    // Email doesn't exist, proceed with adding the user
                    val newUser = hashMapOf(
                        "userId" to user.userId,
                        "email" to user.email,
                        "password" to user.password,
                        "name" to user.name
                    )

                    db.collection("users").document(user.email).set(newUser)
                        .addOnSuccessListener {
                            callback()
                        }
                        .addOnFailureListener { exception ->
                            // Handle failure
                            displayErrorMessage(view, exception.message ?: "Unknown error")
                        }
                } else {
                    // Email already exists, handle accordingly
                    displayErrorMessage(view,"Email already exists")
                }
            }
            .addOnFailureListener { exception ->
                // Handle failure
                displayErrorMessage(view, exception.message ?: "Unknown error")
            }
    }

    private fun displayErrorMessage(view: View, message: String) {
        val context = view.context // Access the context from the view
        // Display a popup with the error message
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.show()

        // Dismiss the toast after 5 seconds
        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 5000)
    }



}

