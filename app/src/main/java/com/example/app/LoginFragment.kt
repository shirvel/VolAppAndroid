package com.example.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.app.MainActivity.Companion.TAG
import com.example.app.model.UserListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null

//    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

//        // Initialize Firebase Auth
//        auth = Firebase.auth

        setUpUI(view)
        return view
    }

    private fun setUpUI(view: View) {

        onStart()

        emailTextField = view.findViewById(R.id.editTextEmail)
        passwordTextField = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.btnLogin)
        registerButton = view.findViewById(R.id.btnRegister)

        clickLoginButton(view)
        clickRegisterButton()
    }

    private fun clickLoginButton(view: View) {
        loginButton?.setOnClickListener {
            val email = emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()

            UserListModel.instance.signIn(view, email, password) {

                val userEmail = email
             //   val user = auth.currentUser
                updateUI(view, userEmail)

                findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)


            }
        }
    }

    private fun clickRegisterButton() {
        registerButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//          //  reload()
//        }
//    }



//    private fun updateUI(view: View, user: FirebaseUser?) {
//        if (user != null) {
//            // User is signed in, show popup message with the user's name
//            val message = "Welcome, ${user.displayName ?: "User"}!" // If user's display name is available, show it, otherwise use "User"
//            val snackbar = Snackbar.make(
////                findViewById(android.R.id.content), // Pass your root view here
//                view,
//                message,
//                Snackbar.LENGTH_LONG
//            )
//            snackbar.duration = 5000 // 5 seconds
//            snackbar.show()
//        } else {
//            // User is signed out or authentication failed, update UI accordingly
//            // For example, you can display an error message or clear any displayed user information
//            // Here, I'm just logging a message
//            Log.d(TAG, "User is signed out or authentication failed")
//        }


    private fun updateUI(view: View, email: String) {
        if (email != null) {
            // User is signed in, show popup message with the user's name
            val message = "Welcome, ${email ?: "User"}!" // If user's display name is available, show it, otherwise use "User"
            val snackbar = Snackbar.make(
//                findViewById(android.R.id.content), // Pass your root view here
                view,
                message,
                Snackbar.LENGTH_LONG
            )
            snackbar.duration = 5000 // 5 seconds
            snackbar.show()
        } else {
            // User is signed out or authentication failed, update UI accordingly
            // For example, you can display an error message or clear any displayed user information
            // Here, I'm just logging a message
            Log.d(TAG, "User is signed out or authentication failed")


        }


        }
}
