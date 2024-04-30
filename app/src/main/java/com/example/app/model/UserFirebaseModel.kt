package com.example.app.model

import android.util.Log
import android.widget.Toast
import com.example.app.MainActivity.Companion.TAG
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.AuthResult



class UserFirebaseModel {

    private val db = Firebase.firestore
//    auth = Firebase.auth


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



//    fun signIn(email: String, password: String) {
//        val auth = FirebaseAuth.getInstance()
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task: Task<AuthResult> ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    val user = auth.currentUser
//                  //   updateUI(user)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    // Log.w(TAG, "signInWithEmail:failure", task.exception)
//                    // Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                    // updateUI(null)
//                }
//            }
//    }

//    fun signIn(email: String, password: String) {
//
//    }




    }

