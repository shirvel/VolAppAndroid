package com.example.app.model

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserFirebaseModel {

    private val db = Firebase.firestore

    fun addUser(view: View, user: User, callback: () -> Unit) {
        // Check if the email already exists
        db.collection("users").whereEqualTo("email", user.email).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    // Email doesn't exist, proceed with adding the user
                    val newUser = hashMapOf(
                        "userId" to user.userId,
                        "email" to user.email,
                        "imageUrl" to user.imageUrl
                    )

                    db.collection("users").document(user.email).set(newUser)
                        .addOnSuccessListener {
                            callback()
                        }
                        .addOnFailureListener { exception ->
                            displayErrorMessage(view, exception.message ?: "Unknown error")
                        }
                } else {
                    // Email already exists, handle accordingly
                    displayErrorMessage(view, "Email already exists")
                }
            }
            .addOnFailureListener { exception ->
                displayErrorMessage(view, exception.message ?: "Unknown error")
            }
    }

    fun checkCredentials(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Query Firestore to check if email and password match
        db.collection("users")
            .whereEqualTo("email", email)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Matching credentials found
                    onSuccess.invoke()
                } else {
                    onFailure.invoke("Invalid email or password")
                }
            }
            .addOnFailureListener { e ->
                onFailure.invoke("Error: ${e.message ?: "Unknown error"}")
            }
    }

    fun signIn(view: View, email: String, password: String, callback: () -> Unit) {
        // Check if credentials are valid
        checkCredentials(email, password, {
            // If credentials are valid, perform success action
            callback.invoke()
        }, { errorMessage ->
            // If credentials are invalid, show error message using a popup (Toast)
            val context = view.context
            val toast = Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
            toast.show()

            // Dismiss the toast after 5 seconds
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                toast.cancel()
            }, 5000)
        })
    }

    private fun displayErrorMessage(view: View, message: String) {
        val context = view.context
        // Display a popup with the error message
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.show()

        // Dismiss the toast after 5 seconds
        val handler = Handler()
        handler.postDelayed({ toast.cancel() }, 5000)
    }
}
