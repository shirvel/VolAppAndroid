package com.example.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import android.widget.Toast

class LoginFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null

//    private val auth = FirebaseAuth.getInstance()
//    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        setUpUI(view)
        return view
    }

    private fun setUpUI(view: View) {
        emailTextField = view.findViewById(R.id.editTextEmail)
        passwordTextField = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.btnLogin)
        registerButton = view.findViewById(R.id.btnRegister)

        clickLoginButton()
        clickRegisterButton()
    }

    private fun clickLoginButton() {
        loginButton?.setOnClickListener {
            val email = emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()

            findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)

//            // Firebase authentication
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        // Check if the user exists in Firestore
//                        val currentUser = auth.currentUser
//                        currentUser?.let { user ->
//                            firestore.collection("users").document(user.uid)
//                                .get()
//                                .addOnSuccessListener { document ->
//                                    if (document.exists()) {
//                                        // User exists, navigate to allPosts fragment
//                                        findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)
//                                    } else {
//                                        // User does not exist
//                                        showPopupMessage("You don't have an account")
//                                    }
//                                }
//                                .addOnFailureListener { e ->
//                                    // Handle Firestore query failure
//                                    Log.e(TAG, "Error getting user document", e)
//                                    showPopupMessage("Error: ${e.message}")
//                                }
//                        }
//                    } else {
//                        // Handle authentication failure
//                        showPopupMessage("Authentication failed: ${task.exception?.message}")
//                    }
//                }

        }
    }

    private fun clickRegisterButton() {
        registerButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

//    private fun showPopupMessage(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//
//    companion object {
//        private const val TAG = "LoginFragment"
//    }
}
