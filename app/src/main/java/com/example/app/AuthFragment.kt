package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // No need to inflate any layout here
        val view =  inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Retrieve email and password from arguments
        val email = arguments?.getString("email") ?: ""
        val password = arguments?.getString("password") ?: ""

        // Call createAccount function with email and password
        createAccount(email, password)
    }

    public fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                 //   Log.i(TAG, "createUserWithEmail:success")
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    onSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message ?: "Authentication failed.",
                        Toast.LENGTH_LONG,
                    ).show()

                    onFail(task.exception?.message)
                }
            }
        // [END create_user_with_email]
    }

    public fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    onSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        // [END sign_in_with_email]
    }

    public fun sendEmailVerification() {
        // [START send_email_verification]
        val user = auth.currentUser!!
        user.sendEmailVerification()
            .addOnCompleteListener(requireActivity()) { task ->
                // Email Verification sent
            }
        // [END send_email_verification]
    }

    fun onFail(message: String?) {
        findNavController().navigate(R.id.action_auth_failed)
    }
    public fun onSuccess(user: FirebaseUser?) {
        findNavController().navigate(R.id.action_auth_success)
    }

    companion object {
        private const val TAG = "Auth-123"
    }
}
